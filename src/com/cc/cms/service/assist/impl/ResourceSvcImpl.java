
package com.cc.cms.service.assist.impl;

import static com.cc.cms.web.FrontUtils.RES_EXP;
import static com.cc.common.Constants.SPT;
import static com.cc.common.Constants.UTF8;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cc.cms.entity.main.Site;
import com.cc.cms.service.assist.IResourceSvc;
import com.cc.cms.web.FrontUtils;
import com.cc.common.util.FileWrap;
import com.cc.common.util.FileWrap.FileComparator;
import com.cc.common.util.Zipper.FileEntry;
import com.cc.common.web.springmvc.RealPathResolver;

@Service
public class ResourceSvcImpl implements IResourceSvc {
	private static final Logger	log	= LoggerFactory.getLogger(ResourceSvcImpl.class);

	@Override
	public List<FileWrap> listFile(String path, boolean dirAndEditable) {
		File parent = new File(realPathResolver.get(path));
		if (parent.exists()) {
			File[] files;
			if (dirAndEditable) {
				files = parent.listFiles(filter);
			} else {
				files = parent.listFiles();
			}
			Arrays.sort(files, new FileComparator());
			List<FileWrap> list = new ArrayList<FileWrap>(files.length);
			for (File f : files) {
				list.add(new FileWrap(f, realPathResolver.get("")));
			}
			return list;
		} else {
			return new ArrayList<FileWrap>(0);
		}
	}

	@Override
	public boolean createDir(String path, String dirName) {
		File parent = new File(realPathResolver.get(path));
		parent.mkdirs();
		File dir = new File(parent, dirName);
		return dir.mkdir();
	}

	@Override
	public void saveFile(String path, MultipartFile file) throws IllegalStateException, IOException {
		File dest = new File(realPathResolver.get(path), file.getOriginalFilename());
		file.transferTo(dest);
	}

	@Override
	public void createFile(String path, String filename, String data) throws IOException {
		File parent = new File(realPathResolver.get(path));
		parent.mkdirs();
		File file = new File(parent, filename);
		FileUtils.writeStringToFile(file, data, UTF8);
	}

	@Override
	public String readFile(String name) throws IOException {
		File file = new File(realPathResolver.get(name));
		return FileUtils.readFileToString(file, UTF8);
	}

	@Override
	public void updateFile(String name, String data) throws IOException {
		File file = new File(realPathResolver.get(name));
		FileUtils.writeStringToFile(file, data, UTF8);
	}

	@Override
	public int delete(String[] names) {
		int count = 0;
		File f;
		for (String name : names) {
			f = new File(realPathResolver.get(name));
			if (FileUtils.deleteQuietly(f)) {
				count++;
			}
		}
		return count;
	}

	@Override
	public void rename(String origName, String destName) {
		File orig = new File(realPathResolver.get(origName));
		File dest = new File(realPathResolver.get(destName));
		orig.renameTo(dest);
	}

	@Override
	public void copyTplAndRes(Site from, Site to) throws IOException {
		String fromSol = from.getTplPath();
		String toSol = to.getTplPath();
		File tplFrom = new File(realPathResolver.get(from.getTplPath()), fromSol);
		File tplTo = new File(realPathResolver.get(to.getTplPath()), toSol);
		FileUtils.copyDirectory(tplFrom, tplTo);
		File resFrom = new File(realPathResolver.get(from.getResPath()), fromSol);
		if (resFrom.exists()) {
			File resTo = new File(realPathResolver.get(to.getResPath()), toSol);
			FileUtils.copyDirectory(resFrom, resTo);
		}
	}

	@Override
	public void delTplAndRes(Site site) throws IOException {
		File tpl = new File(realPathResolver.get(site.getTplPath()));
		File res = new File(realPathResolver.get(site.getResPath()));
		FileUtils.deleteDirectory(tpl);
		FileUtils.deleteDirectory(res);
	}

	@Override
	public String[] getSolutions(String path) {
		File tpl = new File(realPathResolver.get(path));
		return tpl.list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return dir.isDirectory();
			}
		});
	}

	@Override
	public List<FileEntry> export(Site site, String solution) {
		List<FileEntry> fileEntrys = new ArrayList<FileEntry>();
		File tpl = new File(realPathResolver.get(site.getTplPath()), solution);
		fileEntrys.add(new FileEntry("", "", tpl));
		File res = new File(realPathResolver.get(site.getResPath()), solution);
		if (res.exists()) {
			for (File r : res.listFiles()) {
				fileEntrys.add(new FileEntry(FrontUtils.RES_EXP, r));
			}
		}
		return fileEntrys;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void imoport(File file, Site site) throws IOException {
		String resRoot = site.getResPath();
		String tplRoot = site.getTplPath();
		// 用默认编码或UTF-8编码解压会乱码！windows7的原因吗？
		ZipFile zip = new ZipFile(file, "GBK");
		ZipEntry entry;
		String name;
		String filename;
		File outFile;
		File pfile;
		byte[] buf = new byte[1024];
		int len;
		InputStream is = null;
		OutputStream os = null;
		String solution = null;

		Enumeration<ZipEntry> en = zip.getEntries();
		while (en.hasMoreElements()) {
			name = en.nextElement().getName();
			if (!name.startsWith(RES_EXP)) {
				solution = name.substring(0, name.indexOf("/"));
				break;
			}
		}
		if (solution == null) {
			return;
		}
		en = zip.getEntries();
		while (en.hasMoreElements()) {
			entry = en.nextElement();
			if (!entry.isDirectory()) {
				name = entry.getName();
				log.debug("unzip file：{}", name);
				// 模板还是资源
				if (name.startsWith(RES_EXP)) {
					filename = resRoot + "/" + solution + name.substring(RES_EXP.length());
				} else {
					filename = tplRoot + SPT + name;
				}
				log.debug("解压地址：{}", filename);
				outFile = new File(realPathResolver.get(filename));
				pfile = outFile.getParentFile();
				if (!pfile.exists()) {
					pfile.mkdirs();
				}
				try {
					is = zip.getInputStream(entry);
					os = new FileOutputStream(outFile);
					while ((len = is.read(buf)) != -1) {
						os.write(buf, 0, len);
					}
				} finally {
					if (is != null) {
						is.close();
						is = null;
					}
					if (os != null) {
						os.close();
						os = null;
					}
				}
			}
		}
	}

	// 文件夹和可编辑文件则显示
	private FileFilter			filter	= new FileFilter() {
											@Override
											public boolean accept(File file) {
												return file.isDirectory() || FileWrap.editableExt(FilenameUtils.getExtension(file.getName()));
											}
										};

	private RealPathResolver	realPathResolver;

	@Autowired
	public void setRealPathResolver(RealPathResolver realPathResolver) {
		this.realPathResolver = realPathResolver;
	}
}

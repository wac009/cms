
package com.cc.cms.action.admin.main;

import java.io.*;
import java.util.*;

import org.apache.commons.io.*;
import org.json.*;
import org.slf4j.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;

import static com.cc.cms.Constants.*;
import com.cc.cms.action.*;
import com.cc.common.util.*;

/** @author wangcheng */
@SuppressWarnings({"rawtypes", "unchecked" })
@Scope("prototype")
@Controller("web.action.admin.resourceAct")
public class ResourceAct extends CmsAct {

	private static final long	serialVersionUID	= -8938189477618848582L;
	private static final Logger	log					= LoggerFactory.getLogger(ResourceAct.class);
	private FileWrap			treeRoot;
	private List<FileWrap>		subDir;
	private String				relPath;
	private String				resName;
	private String				resContent;
	private String				parentPath;
	private String				origName;
	private String				dirName;
	private File[]				resFile;
	private String[]			resFileContentType;
	private String[]			resFileFileName;

	@Override
	public String left() {
		String resPath = contextPvd.getAppRealPath(getWeb().getResRootBuf().toString());
		treeRoot = new FileWrap(new File(resPath), resPath, new FileFilter() {

			@Override
			public boolean accept(File f) {
				if (f.isDirectory() || f.getName().endsWith(".js") || f.getName().endsWith(".css") || f.getName().endsWith(".html") || f.getName().endsWith(".txt")) {
					return true;
				} else {
					return false;
				}
			}
		});
		return LEFT;
	}

	@Override
	public String list() {
		if (relPath == null) {
			relPath = "";
		}
		String path = contextPvd.getAppRealPath(getWeb().getResRootBuf().append(relPath).toString());
		File dir = new File(path);
		subDir = new FileWrap(dir).getChild();
		if (relPath.indexOf("\\") != -1) {
			relPath = relPath.replace("\\", "\\\\");
		}
		return LIST;
	}

	private void editFile(String path) {
		File res = new File(path);
		resName = res.getName();
		parentPath = relPath.substring(0, relPath.lastIndexOf(FILE_SPT));
		try {
			resContent = FileUtils.readFileToString(res, UTF8);
		} catch (IOException e) {
			log.error("读取模板文件失败", e);
			addActionError("读取模板文件失败!");
		}
	}

	@Override
	public String edit() {
		String path = contextPvd.getAppRealPath(getWeb().getResRootBuf().append(relPath).toString());
		editFile(path);
		return EDIT;
	}

	public String update() throws JSONException {
		String path = contextPvd.getAppRealPath(getWeb().getResRootBuf().append(relPath).toString());
		if (updateFile(path)) {
			log.debug("修改文件成功");
			return JSON;
		}
		log.debug("修改文件失败");
		return JSON;
	}

	private boolean updateFile(String path) {
		File res = new File(path);
		File newFile = res;
		if (!res.getName().equals(resName)) {
			newFile = new File(res.getParent() + FILE_SPT + resName);
			res.renameTo(newFile);
		}
		try {
			FileUtils.writeStringToFile(newFile, resContent, UTF8);
			getJsonRoot().put("success", true);
			getJsonRoot().put("msg", "保存成功");
			log.debug("写文件成功");
			return true;
		} catch (IOException e) {
			log.error("写文件失败", e);
			getJsonRoot().put("success", false);
			getJsonRoot().put("msg", "写文件失败！");
			return false;
		}
	}

	public String delete() {
		String path = contextPvd.getAppRealPath(getWeb().getResRootBuf().append(relPath).append(SPT).append(resName).toString());
		File tpl = new File(path);
		if (FileUtils.deleteQuietly(tpl)) {
			addActionMessage("删除成功！");
		} else {
			addActionError("删除失败！");
		}
		return list();
	}

	private void renameFile(String path) {
		File res = new File(path);
		if (!origName.equals(resName)) {
			boolean b = res.renameTo(new File(res.getParent() + FILE_SPT + resName));
			if (!b) {
				getJsonRoot().put("success", false);
				log.error("重命名失败");
			} else {
				getJsonRoot().put("success", true);
				log.debug("重命名成功");
			}
		} else {
			log.debug("名称没有改变");
			getJsonRoot().put("success", null);
		}
	}

	public String rename() {
		String path = contextPvd.getAppRealPath(getWeb().getResRootBuf().append(relPath).append(SPT).append(origName).toString());
		renameFile(path);
		return JSON;
	}

	@Override
	public String add() {
		return ADD;
	}

	public String save() {
		String path = contextPvd.getAppRealPath(getWeb().getResRootBuf().toString());
		if (saveFile(path)) {
			addActionMessage("添加资源文件成功");
			log.debug("添加资源文件成功");
			return list();
		}
		addActionMessage("添加资源文件失败");
		log.debug("添加资源文件失败");
		return INPUT;
	}

	private boolean saveFile(String path) {
		File res = new File(path + relPath + FILE_SPT + resName);
		try {
			FileUtils.writeStringToFile(res, resContent, UTF8);
			return true;
		} catch (IOException e) {
			log.error("写入资源文件失败！", e);
			addActionError("写入资源文件失败！");
			return false;
		}
	}

	public String createDir() {
		String path = contextPvd.getAppRealPath(getWeb().getResRootBuf().append(relPath).append(SPT).append(dirName).toString());
		File dir = new File(path);
		dir.mkdir();
		addActionMessage("成功创建文件夹");
		return list();
	}

	public String upload() {
		return "upload";
	}

	public String uploadSubmit() {
		String path = contextPvd.getAppRealPath(getWeb().getResRootBuf().append(relPath).append(SPT).toString());
		if (resFile != null) {
			try {
				for (int i = 0; i < resFile.length; i++) {
					FileUtils.copyFile(resFile[i], new File(path + FILE_SPT + resFileFileName[i]));
				}
				addActionMessage("上传成功！");
				log.debug("上传成功！");
			} catch (IOException e) {
				addActionError("上传失败！");
				log.error("上传失败！" + e.getMessage());
			}
		}
		return list();
	}

	public FileWrap getTreeRoot() {
		return treeRoot;
	}

	public void setTreeRoot(FileWrap treeRoot) {
		this.treeRoot = treeRoot;
	}

	public List<FileWrap> getSubDir() {
		return subDir;
	}

	public void setSubDir(List<FileWrap> subDir) {
		this.subDir = subDir;
	}

	public String getRelPath() {
		return relPath;
	}

	public void setRelPath(String relPath) {
		this.relPath = relPath;
	}

	public String getResName() {
		return resName;
	}

	public void setResName(String resName) {
		this.resName = resName;
	}

	public String getResContent() {
		return resContent;
	}

	public void setResContent(String resContent) {
		this.resContent = resContent;
	}

	public String getParentPath() {
		return parentPath;
	}

	public void setParentPath(String parentPath) {
		this.parentPath = parentPath;
	}

	public String getOrigName() {
		return origName;
	}

	public void setOrigName(String origName) {
		this.origName = origName;
	}

	public String getDirName() {
		return dirName;
	}

	public void setDirName(String dirName) {
		this.dirName = dirName;
	}

	public File[] getResFile() {
		return resFile;
	}

	public void setResFile(File[] resFile) {
		this.resFile = resFile;
	}

	public String[] getResFileContentType() {
		return resFileContentType;
	}

	public void setResFileContentType(String[] resFileContentType) {
		this.resFileContentType = resFileContentType;
	}

	public String[] getResFileFileName() {
		return resFileFileName;
	}

	public void setResFileFileName(String[] resFileFileName) {
		this.resFileFileName = resFileFileName;
	}
}


package com.cc.common.util;

import java.io.*;
import java.util.*;

import org.apache.commons.io.*;

import com.cc.common.image.*;

public class FileWrap {
	/** 可编辑的后缀名 */
	public static final String[]	EDITABLE_EXT	= new String[] { "html", "htm", "css", "js", "txt" };
	private File					file;
	private String					rootName;
	private FileFilter				filter;
	private List<FileWrap>			child;
	private String					filename;

	/** 构造器
	 * 
	 * @param file 待包装的文件 */
	public FileWrap(File file) {
		this(file, null);
	}

	/** 构造器
	 * 
	 * @param file 待包装的文件
	 * @param rootPath 根路径，用于计算相对路径 */
	public FileWrap(File file, String rootName) {
		this(file, rootName, null);
	}

	/** 构造器
	 * 
	 * @param file 待包装的文件
	 * @param rootPath 根路径，用于计算相对路径
	 * @param filter 文件过滤器，应用于所有子文件 */
	public FileWrap(File file, String rootName, FileFilter filter) {
		this.file = file;
		this.rootName = rootName;
		this.filter = filter;
	}

	/** 是否允许编辑
	 * 
	 * @param ext 文件扩展名
	 * @return */
	public static boolean editableExt(String ext) {
		ext = ext.toLowerCase(Locale.ENGLISH);
		for (String s : EDITABLE_EXT) {
			if (s.equals(ext)) {
				return true;
			}
		}
		return false;
	}

	public void setChild(List<FileWrap> child) {
		this.child = child;
	}

	// public FileWrap(File file, String rootName, )
	public java.sql.Timestamp getLastModified() {
		return new java.sql.Timestamp(file.lastModified());
	}

	public long getSize() {
		return file.length() / 1024L + 1;
	}

	public String getName() {
		return file.getName();
	}

	public String getTreeName() {
		return getName();
	}

	public boolean getIsDirectory() {
		return file.isDirectory();
	}
	
	/** 获得文件路径，不包含文件名的路径。
	 * 
	 * @return */
	public String getPath() {
		String name = getName();
		return name.substring(0, name.lastIndexOf('/'));
	}
	/** 获得文件名，不包含路径的文件名。 如没有指定名称，则返回文件自身的名称。
	 * 
	 * @return */
	public String getFilename() {
		return filename != null ? filename : file.getName();
	}

	/** 扩展名
	 * 
	 * @return */
	public String getExtension() {
		return FilenameUtils.getExtension(file.getName());
	}

	public String getRelPath() {
		String path = getFile().getAbsolutePath();
		int index = path.indexOf(rootName) + rootName.length();
		String relPath = path.substring(index);
		return relPath;
	}

	public String getType() {
		if (file.isDirectory()) {
			return "文件夹";
		} else {
			String fname = file.getName();
			String ext = fname.substring(fname.lastIndexOf(".") + 1);
			return ext + " 文件";
		}
	}

	public String getIco() {
		if (file.isDirectory()) {
			return "folder.gif";
		}
		String fname = file.getName();
		int extIndex = fname.lastIndexOf(".");
		if (extIndex == -1) {
			return "unknow.gif";
		}
		String ext = fname.substring(extIndex);
		if (ext.equalsIgnoreCase(".jpg") || ext.equalsIgnoreCase(".jpeg")) {
			return "jpg.gif";
		} else if (ext.equalsIgnoreCase(".gif")) {
			return "gif.gif";
		} else if (ext.equalsIgnoreCase(".html") || ext.equalsIgnoreCase(".htm")) {
			return "html.gif";
		} else if (ext.equalsIgnoreCase(".swf")) {
			return "swf.gif";
		} else if (ext.equalsIgnoreCase(".txt")) {
			return "txt.gif";
		} else {
			return "unknow.gif";
		}
	}

	public List<FileWrap> getChild() {
		if (this.child != null) {
			return this.child;
		}
		File[] files;
		if (filter == null) {
			files = getFile().listFiles();
		} else {
			files = getFile().listFiles(filter);
		}
		List<FileWrap> list = new ArrayList<FileWrap>();
		if (files != null) {
			Arrays.sort(files, new FileComparator());
			for (File f : files) {
				FileWrap fw = new FileWrap(f, rootName, filter);
				list.add(fw);
			}
		}
		return list;
	}
	
	/** 是否图片
	 * 
	 * @return */
	public boolean isImage() {
		if (isDirectory()) {
			return false;
		}
		String ext = getExtension();
		return ImageUtils.isValidImageExt(ext);
	}

	/** 是否可编辑
	 * 
	 * @return */
	public boolean isEditable() {
		if (isDirectory()) {
			return false;
		}
		String ext = getExtension().toLowerCase();
		for (String s : EDITABLE_EXT) {
			if (s.equals(ext)) {
				return true;
			}
		}
		return false;
	}

	/** 是否目录
	 * 
	 * @return */
	public boolean isDirectory() {
		return file.isDirectory();
	}

	/** 是否文件
	 * 
	 * @return */
	public boolean isFile() {
		return file.isFile();
	}

	/** 设置待包装的文件
	 * 
	 * @param file */
	public void setFile(File file) {
		this.file = file;
	}

	/** 指定名称
	 * 
	 * @param name */
	public void setFilename(String filename) {
		this.filename = filename;
	}

	public boolean isTreeLeaf() {
		return getFile().isFile();
	}

	public File getFile() {
		return this.file;
	}

	public static class FileComparator implements Comparator<File> {
		@Override
		public int compare(File o1, File o2) {
			if (o1.isDirectory() && !o2.isDirectory()) {
				return -1;
			} else if (!o1.isDirectory() && o2.isDirectory()) {
				return 1;
			} else {
				return o1.compareTo(o2);
			}
		}
	}
}


package com.cc.common.util;

import static com.cc.common.Constants.*;
import java.io.*;
import java.util.*;
import org.apache.commons.lang.*;
import org.slf4j.*;

/**
 * 上传规则定义类。 在上传之前将上传规则对象保存在session中，之后编辑器或其他上传对象将根据上传规则上传文件。
 * 编辑器浏览服务器的根路径：rootPath。模板制作时需要指定根路径，以便上传图片。 定义上传路径
 */
public class UploadRule implements java.io.Serializable {

	private static final long		serialVersionUID	= 1L;
	private static final Logger		log					= LoggerFactory.getLogger(UploadRule.class);
	/** 在session中的key */
	public static final String		KEY					= "_upload_rule";
	/** 已经上传到图片 */
	private Map<String, UploadFile>	uploadFiles;
	/** 编辑器浏览服务器的根路径，也是上传的根路径 */
	private String					rootPath;
	private String					pathPrefix;
	/** 是否生成文件名 */
	private boolean					isGenName			= true;
	/** 是否区分文件类型（用于编辑器浏览服务器时使用） */
	private boolean					hasType				= true;
	/** 是否需要清理 */
	private boolean					needClear			= true;
	/** 是否允许浏览文件 */
	private boolean					allowFileBrowsing	= true;
	/** 是否允许上传文件 */
	private boolean					allowUpload			= true;
	/** 允许上传的大小。0不允许上传，-1不受限制 */
	private int						allowSize			= -1;
	/** 已上传大小 */
	private int						uploadSize			= 0;

	public static void main(String[] args) {
		UploadRule rule = new UploadRule("", "", true);
		System.out.println(rule.getPathName("", "jpg", "img"));
	}

	/**
	 * 构造器
	 * 
	 * @param rootPath
	 *            根路径。浏览服务器的根路径。上传时可能需要再加上类别路径
	 * @param pathPrefix
	 *            路径前缀
	 * @param isGenName
	 *            是否创建随机文件名
	 * @param hasType
	 *            是否区分文件类别。模板制作时不需要，其他情况下需要
	 * @param needClear
	 *            是否需要清除未使用的上传文件
	 */
	public UploadRule(String rootPath, String pathPrefix, boolean isGenName, boolean hasType, boolean needClear) {
		this.rootPath = rootPath;
		this.pathPrefix = pathPrefix;
		this.isGenName = isGenName;
		this.hasType = hasType;
		this.needClear = needClear;
	}

	/**
	 * 构造器
	 * 
	 * @param rootPath
	 *            根路径。浏览服务器的根路径。上传时可能需要再加上类别路径
	 * @param isGenName
	 *            是否创建随机文件名
	 * @param hasType
	 *            是否区分文件类别。模板制作时不需要，其他情况下需要
	 */
	public UploadRule(String rootPath, String pathPrefix, boolean isGenName, boolean hasType) {
		this(rootPath, pathPrefix, isGenName, hasType, true);
	}

	/**
	 * 构造器
	 * 
	 * @param rootPath
	 *            根路径。浏览服务器的根路径。上传时可能需要再加上类别路径
	 * @param isGenName
	 *            是否创建随机文件名
	 */
	public UploadRule(String rootPath, String pathPrefix, boolean isGenName) {
		this(rootPath, pathPrefix, isGenName, true, true);
	}

	/**
	 * 构造器
	 * 
	 * @param rootPath
	 *            根路径。浏览服务器的根路径。上传时可能需要再加上类别路径
	 */
	public UploadRule(String rootPath, String pathPrefix) {
		this(rootPath, pathPrefix, true, true, true);
	}

	/**
	 * 构造器
	 * 
	 * @param rootPath
	 *            根路径。浏览服务器的根路径。上传时可能需要再加上类别路径
	 */
	public UploadRule(String rootPath) {
		this(rootPath, null, true, true, true);
	}

	/** 获得文件全名 目录前缀/年+季度/月+日/文件名.suffix */
	public String getPathName(String fileName, String suffix) {
		return getPathName(fileName, suffix, null, 2);
	}

	public String getPathName(String fileName, String suffix, String uploadType) {
		return getPathName(fileName, suffix, uploadType, 2);
	}

	public String getPathName(String fileName, String suffix, String uploadType, Integer pathType) {
		StringBuilder sb = new StringBuilder();
		switch (pathType) {
			case 1:
				sb = new StringBuilder(genFilePath1());
				break;
			case 2:
				sb = new StringBuilder(genFilePath2());
				break;
			case 3:
				sb = new StringBuilder(genFilePath3());
				break;
			default:
				sb = new StringBuilder(genFilePath1());
				break;
		}
		if (uploadType != null) {
			sb.append(uploadType).append(SPT);
		}
		if (isGenName) {
			sb.append(genFileName());
		} else {
			sb.append(fileName);
		}
		sb.append('.').append(suffix);
		return sb.toString();
	}

	/** 按当前日期生产路径：/2008/，/年/ */
	public static String genFilePath1() {
		StringBuilder sb = new StringBuilder();
		Calendar cal = Calendar.getInstance();
		sb.append(SPT).append(cal.get(Calendar.YEAR)).append(SPT);
		return sb.toString();
	}

	/** 按当前日期生产路径：/2008_2/，/年_季/ */
	public static String genFilePath2() {
		StringBuilder sb = new StringBuilder();
		Calendar cal = Calendar.getInstance();
		sb.append(SPT).append(cal.get(Calendar.YEAR)).append('_').append(cal.get((Calendar.MONTH)) / 3 + 1).append(SPT);
		return sb.toString();
	}

	/** 按当前日期生产路径：/2008_2/5_20/，/年_季/月_日/ */
	public static String genFilePath3() {
		StringBuilder sb = new StringBuilder();
		Calendar cal = Calendar.getInstance();
		sb.append(SPT).append(cal.get(Calendar.YEAR)).append('_').append(cal.get((Calendar.MONTH)) / 3 + 1).append(SPT).append(cal.get(Calendar.MONTH) + 1).append('_').append(cal.get(Calendar.DAY_OF_MONTH)).append(SPT);
		return sb.toString();
	}

	/** 获得文件名 4位随机数加上当前时间 */
	public static String genFileName() {
		String name = StrUtils.longToN36(System.currentTimeMillis());
		return RandomStringUtils.random(4, StrUtils.N36_CHARS) + name;
	}

	public void addUploadFile(String origName, String fileName, String realPath, long size) {
		if (uploadFiles == null) {
			uploadFiles = new HashMap<String, UploadFile>();
		}
		uploadFiles.put(fileName, new UploadFile(origName, fileName, realPath, size));
	}

	public void removeUploadFile(String fileName) {
		if (uploadFiles != null) {
			uploadFiles.remove(fileName);
		}
	}

	public Map<String, UploadFile> getUploadFiles() {
		return uploadFiles;
	}

	public void clearUploadFile() {
		if (uploadFiles != null && needClear) {
			for (UploadFile uf : uploadFiles.values()) {
				File file = new File(uf.getRealPath());
				if (file.delete()) {
					log.debug("删除未被使用的文件：{}", file.getName());
				} else {
					log.warn("删除文件失败：{}", file.getName());
				}
			}
			uploadFiles.clear();
		}
	}

	public boolean isGenName() {
		return isGenName;
	}

	public void setGenName(boolean isGenName) {
		this.isGenName = isGenName;
	}

	public String getRootPath() {
		return rootPath;
	}

	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}

	public boolean isHasType() {
		return hasType;
	}

	public void setHasType(boolean hasType) {
		this.hasType = hasType;
	}

	public boolean isNeedClear() {
		return needClear;
	}

	public void setNeedClear(boolean needClear) {
		this.needClear = needClear;
	}

	public static class UploadFile implements java.io.Serializable {

		private static final long	serialVersionUID	= 1L;
		private String				origName;
		private String				fileName;
		private String				realPath;
		private long				size;

		public UploadFile() {}

		public UploadFile(String origName, String fileName, String realPath, long size) {
			this.origName = origName;
			this.fileName = fileName;
			this.realPath = realPath;
			this.size = size;
		}

		public String getRelPath(String pathRoot) {
			String real = getRealPath();
			real = StringUtils.replace(real, pathRoot, "");
			real = StringUtils.replace(real, File.separator, "/");
			return real;
		}

		public String getOrigName() {
			return origName;
		}

		public void setOrigName(String origName) {
			this.origName = origName;
		}

		public String getFileName() {
			return fileName;
		}

		public void setFileName(String fileName) {
			this.fileName = fileName;
		}

		public String getRealPath() {
			return realPath;
		}

		public void setRealPath(String realPath) {
			this.realPath = realPath;
		}

		public long getSize() {
			return size;
		}

		public void setSize(long size) {
			this.size = size;
		}
	}

	public String getPathPrefix() {
		return pathPrefix;
	}

	public void setPathPrefix(String pathPrefix) {
		this.pathPrefix = pathPrefix;
	}

	public boolean isAllowFileBrowsing() {
		return allowFileBrowsing;
	}

	public void setAllowFileBrowsing(boolean allowFileBrowsing) {
		this.allowFileBrowsing = allowFileBrowsing;
	}

	public int getUploadSize() {
		return uploadSize;
	}

	public void setUploadSize(int uploadSize) {
		this.uploadSize = uploadSize;
	}

	public boolean isAllowUpload() {
		return allowUpload;
	}

	public void setAllowUpload(boolean allowUpload) {
		this.allowUpload = allowUpload;
	}

	public int getAllowSize() {
		return allowSize;
	}

	public void setAllowSize(int allowSize) {
		this.allowSize = allowSize;
	}
}

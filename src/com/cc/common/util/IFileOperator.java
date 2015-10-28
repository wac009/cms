package com.cc.common.util;

import java.io.File;

public interface IFileOperator {
	/**
	 * 写文件
	 * 
	 * @param path
	 *        文件路径（包括文件名）
	 * @param content
	 *        内容
	 */
	public void writeFile(String path, String name, String content);
	/**
	 * 读取文件
	 * 
	 * @param path
	 *        文件路径（包括文件名）
	 * @return
	 */
	public String readFile(String fileName);
	/**
	 * 删除文件
	 * 
	 * @param path
	 *        文件路径（包括文件名）
	 * @return 是否删除成功
	 */
	public boolean deleteFile(File file);
	public boolean copyFile(File src, File dist);
	public boolean copy(File src, File dist);
	public boolean writeFile(String url, File dist);
	public String fileName(String resUrl);
}

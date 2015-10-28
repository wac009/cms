
package com.cc.common.upload;

import java.io.*;

import javax.servlet.*;

import org.springframework.web.multipart.*;

/**
 * @author wangcheng
 */
public interface IFileRepository {

	public abstract String storeByExt(String path, String ext, MultipartFile file) throws IOException;

	public abstract String storeByFilename(String filename, MultipartFile file) throws IOException;

	public abstract String storeByExt(String path, String ext, File file) throws IOException;

	public abstract String storeByFilename(String filename, File file) throws IOException;

	public abstract File retrieve(String name);

	public abstract void setServletContext(ServletContext servletContext);
}
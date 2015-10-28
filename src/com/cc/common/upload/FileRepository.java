
package com.cc.common.upload;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

/** 本地文件存储
 * 
 * @author wangcheng */
@Service
@Transactional
public class FileRepository implements ServletContextAware, IFileRepository {
	private Logger	log	= LoggerFactory.getLogger(FileRepository.class);

	@Override
	public String storeByExt(String path, String ext, MultipartFile file) throws IOException {
		String filename = UploadUtils.generateFilename(path, ext);
		File dest = new File(ctx.getRealPath(filename));
		dest = UploadUtils.getUniqueFile(dest);
		store(file, dest);
		return filename;
	}

	@Override
	public String storeByFilename(String filename, MultipartFile file) throws IOException {
		File dest = new File(ctx.getRealPath(filename));
		store(file, dest);
		return filename;
	}

	@Override
	public String storeByExt(String path, String ext, File file) throws IOException {
		String filename = UploadUtils.generateFilename(path, ext);
		File dest = new File(ctx.getRealPath(filename));
		dest = UploadUtils.getUniqueFile(dest);
		store(file, dest);
		return filename;
	}

	@Override
	public String storeByFilename(String filename, File file) throws IOException {
		File dest = new File(ctx.getRealPath(filename));
		store(file, dest);
		return filename;
	}

	private void store(MultipartFile file, File dest) throws IOException {
		try {
			UploadUtils.checkDirAndCreate(dest.getParentFile());
			file.transferTo(dest);
		} catch (IOException e) {
			log.error("Transfer file error when upload file", e);
			throw e;
		}
	}

	private void store(File file, File dest) throws IOException {
		try {
			UploadUtils.checkDirAndCreate(dest.getParentFile());
			FileUtils.copyFile(file, dest);
		} catch (IOException e) {
			log.error("Transfer file error when upload file", e);
			throw e;
		}
	}

	@Override
	public File retrieve(String name) {
		return new File(ctx.getRealPath(name));
	}

	private ServletContext	ctx;

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.ctx = servletContext;
	}
}

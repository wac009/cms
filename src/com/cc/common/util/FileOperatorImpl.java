package com.cc.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Calendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FileOperatorImpl implements IFileOperator {
	private static final Log	log		= LogFactory.getLog(FileOperatorImpl.class);
	public static int			BF_SIZE	= 2048;

	@Override
	public String readFile(String path) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(path));
			try {
				StringBuilder builder = new StringBuilder();
				char[] ch = new char[BF_SIZE];
				for (int i = 0; (i = reader.read(ch)) != -1;) {
					builder.append(ch, 0, i);
				}
				return builder.toString();
			} finally {
				reader.close();
			}
		} catch (FileNotFoundException e) {
			log.warn("文件未找到！：" + path, e);
		} catch (IOException e) {
			log.warn("IO异常！：" + path, e);
		}
		return "";
	}
	@Override
	public void writeFile(String path, String name, String content) {
		if (content == null) {
			content = "";
		}
		try {
			File f = new File(path);
			if (!f.exists()) {
				f.mkdirs();
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(path + name));
			try {
				writer.write(content);
			} finally {
				writer.close();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	public boolean writeFile(String url, File dist) {
		try {
			writeFile(new URL(url).openStream(), dist);
			return true;
		} catch (IOException e) {
			log.warn("无法下载图片！", e);
			return false;
		}
	}
	@Override
	public boolean deleteFile(File file) {
		if (file.isDirectory()) {
			for (File f : file.listFiles()) {
				deleteFile(f);
			}
			file.delete();
		} else {
			file.delete();
		}
		return file.delete();
	}
	@Override
	public boolean copy(File src, File dist) {
		boolean isSuccess = false;
		if (src.exists()) {
			if (src.isFile()) {
				isSuccess = copyFile(src, dist);
			} else {
				// 如果目标路径在源路径里，则不也许拷贝
				if (dist.getPath().indexOf(src.getPath()) != -1) {
					return false;
				}
				dist.mkdirs();
				File[] fs = src.listFiles();
				for (File f : fs) {
					copy(f, new File(dist.getPath() + File.separator + f.getName()));
				}
				isSuccess = true;
			}
		}
		return isSuccess;
	}
	@Override
	public boolean copyFile(File src, File dist) {
		if (src.exists() && src.isFile()) {
			try {
				writeFile(new FileInputStream(src), dist);
				return true;
			} catch (FileNotFoundException e) {
				log.warn("复制文件时，找不到文件", e);
			}
		}
		return false;
	}
	public boolean writeFile(InputStream sour, File dist) {
		try {
			File p = dist.getParentFile();
			if (p != null && !p.exists()) {
				p.mkdirs();
			}
			BufferedInputStream is = null;
			BufferedOutputStream os = null;
			byte[] bf = new byte[BF_SIZE];
			try {
				is = new BufferedInputStream(sour);
				os = new BufferedOutputStream(new FileOutputStream(dist));
				for (int i = -1; (i = is.read(bf)) != -1;) {
					os.write(bf, 0, i);
				}
				return true;
			} finally {
				if (is != null) {
					is.close();
				}
				if (os != null) {
					os.close();
				}
			}
		} catch (FileNotFoundException e) {
			log.warn("复制文件时，找不到文件", e);
		} catch (IOException e) {
			log.warn("复制文件时，io异常", e);
		}
		return false;
	}
	@Override
	public String fileName(String resUrl) {
		String datepath = Calendar.getInstance().get(Calendar.YEAR) + "" + Calendar.getInstance().get(Calendar.MONTH) + ""
				+ Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		resUrl = datepath + resUrl;
		return resUrl;
	}
}

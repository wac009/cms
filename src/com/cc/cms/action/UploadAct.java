
package com.cc.cms.action;

import java.awt.image.*;
import java.io.*;

import javax.imageio.*;

import net.fckeditor.tool.*;

import org.apache.commons.io.*;
import org.apache.commons.lang.*;
import org.slf4j.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;

import com.cc.common.util.*;
import com.cc.cms.Constants;

/** @author wangcheng */
@SuppressWarnings({ "rawtypes" })
@Scope("prototype")
@Controller("web.action.admin.fileUploadAct")
public class UploadAct extends CmsAct {

	private static final long	serialVersionUID	= 6433835418407711784L;
	private static final Logger	log					= LoggerFactory.getLogger(UploadAct.class);
	private File				uploadFile;
	private String				uploadFileContentType;
	private String				uploadFileFileName;
	private String				uploadPath;
	private int					uploadNum			= 1;
	private String				error;
	private int					uploadRuleId;
	private String				name;
	private String				suffix;
	// 图片处理部分
	private String				imgSrcRoot;
	private String				imgSrcPath;
	private boolean				isZoom				= false;
	private int					zoomWidth;
	private int					zoomHeight;
	private int					reMinWidth;
	private int					reMinHeight;
	private float				imgScale;
	private int					imgWidth;
	private int					imgHeight;
	private int					imgTop;
	private int					imgLeft;

	/** 上传图片 */
	public String imgUpload() {
		// 进入上传页面
		log.info("上传图片：" + uploadFileFileName);
		UploadRule rule = (UploadRule) contextPvd.getSessionAttr(UploadRule.KEY + uploadRuleId);
		if (checkUploadFile(rule) != null) {
			return SUCCESS;
		}
		uploadPath = rule.getPathName(name, suffix, Constants.UPLOAD_IMAGE);
		String imgRelPath = rule.getRootPath() + uploadPath;
		String realPath = contextPvd.getAppRealPath(imgRelPath);
		try {
			File toSave;
			if (isZoom) {
				long start = System.currentTimeMillis();
				toSave = new File(realPath);
				ImageScale.resizeFix(uploadFile, toSave, zoomWidth, zoomHeight);
				long end = System.currentTimeMillis();
				log.info("上传并压缩图片：{}；压缩耗时：{}ms。", realPath, end - start);
			} else {
				toSave = new File(realPath);
				FileUtils.copyFile(uploadFile, toSave);
				log.info("上传图片成功：{}", realPath);
			}
			if (rule.isNeedClear()) {
				rule.addUploadFile(UtilsFile.sanitizeFileName(uploadFileFileName), toSave.getName(), toSave.getAbsolutePath(), toSave.length());
			}
			return SUCCESS;
		} catch (IOException e) {
			error = "上传图片失败！信息:" + e.getMessage();
			log.error("上传图片失败！", e);
			return SUCCESS;
		}
	}

	/** 打开图片剪切器 */
	public String imgAreaSelect() {
		return SUCCESS;
	}

	/** 剪裁图片 */
	public String imgCut() {
		String real = contextPvd.getAppRealPath(getWeb().getUploadPath() + imgSrcPath);
		try {
			File srcFile = new File(real);
			BufferedImage bufImg = ImageIO.read(srcFile);
			// 需要剪裁图片
			if (imgWidth > 0) {
				bufImg = bufImg.getSubimage(getLen(imgTop), getLen(imgLeft), getLen(imgWidth), getLen(imgHeight));
			}
			ImageScale.resizeFix(bufImg, srcFile, reMinWidth, reMinHeight);
			log.info("图片剪裁成功：{}", real);
			return SUCCESS;
		} catch (IOException e) {
			error = "剪裁图片失败!";
			log.error("剪裁图片出错", e);
			return SUCCESS;
		}
	}

	public String attachmentUpload() {
		// 进入上传页面
		log.info("上传attachment：" + uploadFileFileName);
		UploadRule rule = (UploadRule) contextPvd.getSessionAttr(UploadRule.KEY + uploadRuleId);
		if (checkUploadFile(rule) != null) {
			return SUCCESS;
		}
		uploadPath = rule.getPathName(name, suffix, Constants.UPLOAD_FILE);
		String attachRelPath = rule.getRootPath() + uploadPath;
		String realPath = contextPvd.getAppRealPath(attachRelPath);
		try {
			File toSave = new File(realPath);
			FileUtils.copyFile(uploadFile, toSave);
			log.info("上传attachment成功：{}", realPath);
			if (rule.isNeedClear()) {
				rule.addUploadFile(UtilsFile.sanitizeFileName(uploadFileFileName), toSave.getName(), toSave.getAbsolutePath(), toSave.length());
			}
			return SUCCESS;
		} catch (IOException e) {
			error = "上传attachment失败！信息:" + e.getMessage();
			log.error("上传attachment失败！", e);
			return SUCCESS;
		}
	}

	public String mediaUpload() {
		// 进入上传页面
		log.info("上传media：" + uploadFileFileName);
		UploadRule rule = (UploadRule) contextPvd.getSessionAttr(UploadRule.KEY + uploadRuleId);
		if (checkUploadFile(rule) != null) {
			return SUCCESS;
		}
		uploadPath = rule.getPathName(name, suffix, Constants.UPLOAD_MEDIA);
		String attachRelPath = rule.getRootPath() + uploadPath;
		String realPath = contextPvd.getAppRealPath(attachRelPath);
		try {
			File toSave = new File(realPath);
			FileUtils.copyFile(uploadFile, toSave);
			log.info("上传media文件成功：{}", realPath);
			if (rule.isNeedClear()) {
				rule.addUploadFile(UtilsFile.sanitizeFileName(uploadFileFileName), toSave.getName(), toSave.getAbsolutePath(), toSave.length());
			}
			return SUCCESS;
		} catch (IOException e) {
			error = "上传media失败！信息:" + e.getMessage();
			log.error("上传media失败！", e);
			return SUCCESS;
		}
	}

	public String flashUpload() {
		// 进入上传页面
		log.info("上传flash：" + uploadFileFileName);
		UploadRule rule = (UploadRule) contextPvd.getSessionAttr(UploadRule.KEY + uploadRuleId);
		if (checkUploadFile(rule) != null) {
			return SUCCESS;
		}
		uploadPath = rule.getPathName(name, suffix, Constants.UPLOAD_MEDIA);
		String attachRelPath = rule.getRootPath() + uploadPath;
		String realPath = contextPvd.getAppRealPath(attachRelPath);
		try {
			File toSave = new File(realPath);
			FileUtils.copyFile(uploadFile, toSave);
			log.info("上传flash成功：{}", realPath);
			if (rule.isNeedClear()) {
				rule.addUploadFile(UtilsFile.sanitizeFileName(uploadFileFileName), toSave.getName(), toSave.getAbsolutePath(), toSave.length());
			}
			return SUCCESS;
		} catch (IOException e) {
			error = "上传flash失败！信息:" + e.getMessage();
			log.error("上传flash失败！", e);
			return SUCCESS;
		}
	}

	private int getLen(int len) {
		return Math.round(len / imgScale);
	}

	/** 检查上传文件合法性 */
	private String checkUploadFile(UploadRule rule) {
		if (uploadFileFileName == null) {
			error = "上传失败！没有找到上传文件";
			log.info(error);
			return SUCCESS;
		}
		if (rule == null) {
			error = "上传失败！未找到上传规则！";
			log.info(error);
			return SUCCESS;
		}
		int suffixIndex = uploadFileFileName.lastIndexOf('.');
		if (suffixIndex == -1) {
			error = "上传失败！文件没有后缀名，不允许上传！";
			log.info(error);
			return SUCCESS;
		}
		name = uploadFileFileName.substring(0, suffixIndex);
		suffix = uploadFileFileName.substring(suffixIndex + 1).toLowerCase();
		if (StringUtils.isBlank(name)) {
			error = "上传失败！该文件没有文件名，不允许上传！";
			log.info(error);
			return SUCCESS;
		}
		if (!rule.isGenName() && StrUtils.countCn(name) > name.length()) {
			error = "上传失败！该文件名包含中文，不允许上传！";
			log.info(error);
			return SUCCESS;
		}
		return null;
	}

	public File getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(File uploadFile) {
		this.uploadFile = uploadFile;
	}

	public String getUploadFileContentType() {
		return uploadFileContentType;
	}

	public void setUploadFileContentType(String uploadFileContentType) {
		this.uploadFileContentType = uploadFileContentType;
	}

	public String getUploadFileFileName() {
		return uploadFileFileName;
	}

	public void setUploadFileFileName(String uploadFileFileName) {
		this.uploadFileFileName = uploadFileFileName;
	}

	public String getUploadPath() {
		return uploadPath;
	}

	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}

	public int getUploadNum() {
		return uploadNum;
	}

	public void setUploadNum(int uploadNum) {
		this.uploadNum = uploadNum;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	@Override
	public int getUploadRuleId() {
		return uploadRuleId;
	}

	@Override
	public void setUploadRuleId(int uploadRuleId) {
		this.uploadRuleId = uploadRuleId;
	}

	public String getImgSrcRoot() {
		return imgSrcRoot;
	}

	public void setImgSrcRoot(String imgSrcRoot) {
		this.imgSrcRoot = imgSrcRoot;
	}

	public String getImgSrcPath() {
		return imgSrcPath;
	}

	public void setImgSrcPath(String imgSrcPath) {
		this.imgSrcPath = imgSrcPath;
	}

	public boolean isZoom() {
		return isZoom;
	}

	public void setZoom(boolean isZoom) {
		this.isZoom = isZoom;
	}

	public int getZoomWidth() {
		return zoomWidth;
	}

	public void setZoomWidth(int zoomWidth) {
		this.zoomWidth = zoomWidth;
	}

	public int getZoomHeight() {
		return zoomHeight;
	}

	public void setZoomHeight(int zoomHeight) {
		this.zoomHeight = zoomHeight;
	}

	public int getReMinWidth() {
		return reMinWidth;
	}

	public void setReMinWidth(int reMinWidth) {
		this.reMinWidth = reMinWidth;
	}

	public int getReMinHeight() {
		return reMinHeight;
	}

	public void setReMinHeight(int reMinHeight) {
		this.reMinHeight = reMinHeight;
	}

	public float getImgScale() {
		return imgScale;
	}

	public void setImgScale(float imgScale) {
		this.imgScale = imgScale;
	}

	public int getImgWidth() {
		return imgWidth;
	}

	public void setImgWidth(int imgWidth) {
		this.imgWidth = imgWidth;
	}

	public int getImgHeight() {
		return imgHeight;
	}

	public void setImgHeight(int imgHeight) {
		this.imgHeight = imgHeight;
	}

	public int getImgTop() {
		return imgTop;
	}

	public void setImgTop(int imgTop) {
		this.imgTop = imgTop;
	}

	public int getImgLeft() {
		return imgLeft;
	}

	public void setImgLeft(int imgLeft) {
		this.imgLeft = imgLeft;
	}
}


package com.cc.cms.action.member;

import static com.cc.cms.Constants.TPLDIR_MEMBER;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cc.cms.entity.main.MemberConfig;
import com.cc.cms.entity.main.Site;
import com.cc.cms.entity.main.User;
import com.cc.cms.web.CmsUtils;
import com.cc.cms.web.FrontUtils;
import com.cc.common.image.ImageScale;
import com.cc.common.upload.IFileRepository;
import com.cc.core.entity.Ftp;
import com.cc.core.service.IDbFileSvc;

/** @author wangcheng */
@Controller
public class ImageCutAct {

	@Autowired
	private ImageScale			imageScale;
	@Autowired
	private IFileRepository		fileRepository;
	@Autowired
	private IDbFileSvc			dbFileMng;
	private static final Logger	log					= LoggerFactory.getLogger(ImageCutAct.class);
	/**
	 * 图片选择页面
	 */
	public static final String	IMAGE_SELECT_RESULT	= "tpl.image_area_select";
	/**
	 * 图片裁剪完成页面
	 */
	public static final String	IMAGE_CUTED			= "tpl.image_cuted";
	/**
	 * 错误信息参数
	 */
	public static final String	ERROR				= "error";

	@RequestMapping("/member/v_image_area_select.jspx")
	public String imageAreaSelect(String uploadBase, String imgSrcPath, Integer zoomWidth, Integer zoomHeight, Integer uploadNum, HttpServletRequest request,
			ModelMap model) {
		model.addAttribute("uploadBase", uploadBase);
		model.addAttribute("imgSrcPath", imgSrcPath);
		model.addAttribute("zoomWidth", zoomWidth);
		model.addAttribute("zoomHeight", zoomHeight);
		model.addAttribute("uploadNum", uploadNum);
		Site site = CmsUtils.getSite(request);
		User user = CmsUtils.getUser(request);
		FrontUtils.frontData(request, model, site);
		MemberConfig mcfg = site.getConfig().getMemberConfig();
		if (!mcfg.isMemberOn()) {
			return FrontUtils.showMessage(request, model, "member.memberClose");
		}
		if (user == null) {
			return FrontUtils.showLogin(request, model, site);
		}
		return FrontUtils.getTplPath(request, site.getTplPath(), TPLDIR_MEMBER, IMAGE_SELECT_RESULT);
	}

	@RequestMapping("/member/o_image_cut.jspx")
	public String imageCut(String imgSrcPath, Integer imgTop, Integer imgLeft, Integer imgWidth, Integer imgHeight, Integer reMinWidth, Integer reMinHeight,
			Float imgScale, Integer uploadNum, HttpServletRequest request, ModelMap model) {
		Site site = CmsUtils.getSite(request);
		try {
			if (imgWidth > 0) {
				if (site.getConfig().getUploadToDb()) {
					String dbFilePath = site.getConfig().getDbFileUri();
					imgSrcPath = imgSrcPath.substring(dbFilePath.length());
					File file = dbFileMng.retrieve(imgSrcPath);
					imageScale.resizeFix(file, file, reMinWidth, reMinHeight, getLen(imgTop, imgScale), getLen(imgLeft, imgScale), getLen(imgWidth, imgScale), getLen(imgHeight, imgScale));
					dbFileMng.restore(imgSrcPath, file);
				} else if (site.getUploadFtp() != null) {
					Ftp ftp = site.getUploadFtp();
					String ftpUrl = ftp.getUrl();
					imgSrcPath = imgSrcPath.substring(ftpUrl.length());
					File file = ftp.retrieve(imgSrcPath);
					imageScale.resizeFix(file, file, reMinWidth, reMinHeight, getLen(imgTop, imgScale), getLen(imgLeft, imgScale), getLen(imgWidth, imgScale), getLen(imgHeight, imgScale));
					ftp.restore(imgSrcPath, file);
				} else {
					String ctx = request.getContextPath();
					imgSrcPath = imgSrcPath.substring(ctx.length());
					File file = fileRepository.retrieve(imgSrcPath);
					imageScale.resizeFix(file, file, reMinWidth, reMinHeight, getLen(imgTop, imgScale), getLen(imgLeft, imgScale), getLen(imgWidth, imgScale), getLen(imgHeight, imgScale));
				}
			} else {
				if (site.getConfig().getUploadToDb()) {
					String dbFilePath = site.getConfig().getDbFileUri();
					imgSrcPath = imgSrcPath.substring(dbFilePath.length());
					File file = dbFileMng.retrieve(imgSrcPath);
					imageScale.resizeFix(file, file, reMinWidth, reMinHeight);
					dbFileMng.restore(imgSrcPath, file);
				} else if (site.getUploadFtp() != null) {
					Ftp ftp = site.getUploadFtp();
					String ftpUrl = ftp.getUrl();
					imgSrcPath = imgSrcPath.substring(ftpUrl.length());
					File file = ftp.retrieve(imgSrcPath);
					imageScale.resizeFix(file, file, reMinWidth, reMinHeight);
					ftp.restore(imgSrcPath, file);
				} else {
					String ctx = request.getContextPath();
					imgSrcPath = imgSrcPath.substring(ctx.length());
					File file = fileRepository.retrieve(imgSrcPath);
					imageScale.resizeFix(file, file, reMinWidth, reMinHeight);
				}
			}
			model.addAttribute("uploadNum", uploadNum);
		} catch (Exception e) {
			log.error("cut image error", e);
			model.addAttribute(ERROR, e.getMessage());
		}
		return FrontUtils.getTplPath(request, site.getTplPath(), TPLDIR_MEMBER, IMAGE_CUTED);
	}

	private int getLen(int len, float imgScale) {
		return Math.round(len / imgScale);
	}
}


package com.cc.cms.action.front;

import static com.cc.cms.Constants.*;

import java.util.*;

import javax.servlet.http.*;

import org.apache.commons.lang.*;
import org.json.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

import com.cc.cms.entity.assist.*;
import com.cc.cms.entity.main.*;
import com.cc.cms.service.main.*;
import com.cc.cms.web.*;
import com.cc.common.web.*;
import com.cc.common.web.session.*;
import com.octo.captcha.service.*;
import com.octo.captcha.service.image.*;

/** @author wangcheng */
@Controller
public class CommentAct {

	private static final Logger	log				= LoggerFactory.getLogger(CommentAct.class);
	public static final String	COMMENT_PAGE	= "tpl.commentPage";
	public static final String	COMMENT_LIST	= "tpl.commentList";
	@Autowired
	private ICommentSvc			cmsCommentMng;
	@Autowired
	private IContentSvc			contentMng;
	@Autowired
	private ISessionProvider		session;
	@Autowired
	private ImageCaptchaService	imageCaptchaService;

	@RequestMapping(value = "/comment*.jspx", method = RequestMethod.GET)
	public String page(Integer contentId, Integer pageNo, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Site site = CmsUtils.getSite(request);
		Content content = contentMng.findById(contentId);
		if (content == null) {
			return FrontUtils.showMessage(request, model, "comment.contentNotFound");
		}
		if (content.getChannel().getCommentControl() == ChannelExt.COMMENT_OFF) {
			return FrontUtils.showMessage(request, model, "comment.closed");
		}
		// 将request中所有参数保存至model中。
		model.putAll(RequestUtils.getQueryParams(request));
		FrontUtils.frontData(request, model, site);
		FrontUtils.frontPageData(request, model);
		model.addAttribute("content", content);
		return FrontUtils.getTplPath(request, site.getTplPath(), TPLDIR_SPECIAL, COMMENT_PAGE);
	}

	@RequestMapping(value = "/comment_list.jspx")
	public String list(Integer siteId, Integer contentId, Integer greatTo, Integer recommend, Integer checked, Integer orderBy, Integer count,
			HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		if (count == null || count <= 0 || count > 200) {
			count = 200;
		}
		boolean desc, rec;
		if (orderBy == null || orderBy == 0) {
			desc = true;
		} else {
			desc = false;
		}
		if (recommend == null || recommend == 0) {
			rec = false;
		} else {
			rec = true;
		}
		Boolean chk;
		if (checked != null) {
			chk = checked != 0;
		} else {
			chk = null;
		}
		List<Comment> list = cmsCommentMng.getListForTag(siteId, contentId, greatTo, chk, rec, desc, count);
		// 将request中所有参数
		model.putAll(RequestUtils.getQueryParams(request));
		model.addAttribute("list", list);
		Site site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		return FrontUtils.getTplPath(request, site.getTplPath(), TPLDIR_CSI, COMMENT_LIST);
	}

	@RequestMapping(value = "/comment.jspx", method = RequestMethod.POST)
	public void submit(Integer contentId, String text, String captcha, HttpServletRequest request, HttpServletResponse response, ModelMap model)
			throws JSONException {
		Site site = CmsUtils.getSite(request);
		User user = CmsUtils.getUser(request);
		JSONObject json = new JSONObject();
		if (contentId == null) {
			json.put("success", false);
			json.put("status", 100);
			ResponseUtils.renderJson(response, json.toString());
			return;
		}
		if (StringUtils.isBlank(text)) {
			json.put("success", false);
			json.put("status", 101);
			ResponseUtils.renderJson(response, json.toString());
			return;
		}
		if (user == null || user.getGroup().getNeedCaptcha()) {
			// 验证码错误
			try {
				if (!imageCaptchaService.validateResponseForID(session.getSessionId(request, response), captcha)) {
					json.put("success", false);
					json.put("status", 1);
					ResponseUtils.renderJson(response, json.toString());
					return;
				}
			} catch (CaptchaServiceException e) {
				json.put("success", false);
				json.put("status", 1);
				log.warn("", e);
				ResponseUtils.renderJson(response, json.toString());
				return;
			}
		}
		Content content = contentMng.findById(contentId);
		if (content == null) {
			// 内容不存在
			json.put("success", false);
			json.put("status", 2);
		} else if (content.getChannel().getCommentControl() == ChannelExt.COMMENT_OFF) {
			// 评论关闭
			json.put("success", false);
			json.put("status", 3);
		} else if (content.getChannel().getCommentControl() == ChannelExt.COMMENT_LOGIN && user == null) {
			// 需要登录才能评论
			json.put("success", false);
			json.put("status", 4);
		} else {
			boolean checked = false;
			Integer userId = null;
			if (user != null) {
				checked = !user.getGroup().getNeedCheck();
				userId = user.getId();
			}
			cmsCommentMng.comment(text, RequestUtils.getIpAddr(request), contentId, site.getId(), userId, checked, false);
			json.put("success", true);
			json.put("status", 0);
		}
		ResponseUtils.renderJson(response, json.toString());
	}

	@RequestMapping(value = "/comment_up.jspx")
	public void up(Integer contentId, HttpServletRequest request, HttpServletResponse response) {
		if (exist(contentId)) {
			cmsCommentMng.ups(contentId);
			ResponseUtils.renderJson(response, "true");
		} else {
			ResponseUtils.renderJson(response, "false");
		}
	}

	@RequestMapping(value = "/comment_down.jspx")
	public void down(Integer contentId, HttpServletRequest request, HttpServletResponse response) {
		if (exist(contentId)) {
			cmsCommentMng.downs(contentId);
			ResponseUtils.renderJson(response, "true");
		} else {
			ResponseUtils.renderJson(response, "false");
		}
	}

	private boolean exist(Integer id) {
		if (id == null) {
			return false;
		}
		Content content = contentMng.findById(id);
		return content != null;
	}
}


package com.cc.cms.action.front;

import static com.cc.cms.Constants.*;

import javax.servlet.http.*;

import org.json.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

import com.cc.cms.entity.assist.*;
import com.cc.cms.entity.main.*;
import com.cc.cms.service.assist.*;
import com.cc.cms.web.*;
import com.cc.common.web.*;
import com.cc.common.web.session.*;
import com.octo.captcha.service.*;
import com.octo.captcha.service.image.*;

/** @author wangcheng */
@Controller
public class GuestbookAct {

	private static final Logger	log					= LoggerFactory.getLogger(GuestbookAct.class);
	public static final String	GUESTBOOK_INDEX		= "tpl.guestbookIndex";
	public static final String	GUESTBOOK_CTG		= "tpl.guestbookCtg";
	public static final String	GUESTBOOK_DETAIL	= "tpl.guestbookDetail";
	@Autowired
	private IGuestbookCtgSvc	cmsGuestbookCtgMng;
	@Autowired
	private IGuestbookSvc		cmsGuestbookMng;
	@Autowired
	private ISessionProvider		session;
	@Autowired
	private ImageCaptchaService	imageCaptchaService;

	/**
	 * 留言板首页或类别页
	 * 
	 * @param ctgId
	 *            留言类别
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/guestbook*.jspx", method = RequestMethod.GET)
	public String index(Integer ctgId, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Site site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		FrontUtils.frontPageData(request, model);
		GuestbookCtg ctg = null;
		if (ctgId != null) {
			ctg = cmsGuestbookCtgMng.findById(ctgId);
		}
		if (ctg == null) {
			// 留言板首页
			return FrontUtils.getTplPath(request, site.getTplPath(), TPLDIR_SPECIAL, GUESTBOOK_INDEX);
		} else {
			// 留言板类别页
			model.addAttribute("ctg", ctg);
			return FrontUtils.getTplPath(request, site.getTplPath(), TPLDIR_SPECIAL, GUESTBOOK_CTG);
		}
	}

	@RequestMapping(value = "/guestbook/{id}.jspx", method = RequestMethod.GET)
	public String detail(@PathVariable Integer id, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Site site = CmsUtils.getSite(request);
		Guestbook guestbook = null;
		if (id != null) {
			guestbook = cmsGuestbookMng.findById(id);
		}
		model.addAttribute("guestbook", guestbook);
		FrontUtils.frontData(request, model, site);
		return FrontUtils.getTplPath(request, site.getTplPath(), TPLDIR_SPECIAL, GUESTBOOK_DETAIL);
	}

	/**
	 * 提交留言。ajax提交。
	 * 
	 * @param contentId
	 * @param pageNo
	 * @param request
	 * @param response
	 * @param model
	 * @throws JSONException
	 */
	@RequestMapping(value = "/guestbook.jspx", method = RequestMethod.POST)
	public void submit(Integer siteId, Integer ctgId, String title, String content, String email, String phone, String qq, String captcha,
			HttpServletRequest request, HttpServletResponse response, ModelMap model) throws JSONException {
		Site site = CmsUtils.getSite(request);
		User member = CmsUtils.getUser(request);
		if (siteId == null) {
			siteId = site.getId();
		}
		JSONObject json = new JSONObject();
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
			ResponseUtils.renderJson(response, json.toString());
			log.warn("", e);
			return;
		}
		String ip = RequestUtils.getIpAddr(request);
		cmsGuestbookMng.save(member, siteId, ctgId, ip, title, content, email, phone, qq);
		json.put("success", true);
		json.put("status", 0);
		ResponseUtils.renderJson(response, json.toString());
	}
}

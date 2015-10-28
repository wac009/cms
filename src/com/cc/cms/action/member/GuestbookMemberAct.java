
package com.cc.cms.action.member;

import static com.cc.common.page.SimplePage.*;
import static com.cc.cms.Constants.*;

import javax.servlet.http.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

import com.cc.cms.entity.assist.*;
import com.cc.cms.entity.main.*;
import com.cc.cms.service.assist.*;
import com.cc.cms.web.*;
import com.cc.common.page.*;
import com.cc.common.web.*;

/**
 * 会员中心获取留言Action
 * 
 * @author wangcheng
 */
@Controller
public class GuestbookMemberAct {

	@Autowired
	private IGuestbookSvc		guestbookMng;
	public static final String	GUESTBOOK_LIST		= "tpl.guestBookLists";
	public static final String	GUESTBOOK_DETAIL	= "tpl.guestBookDetail";
	public static final String	GUESTBOOK_REPLAY	= "tpl.guestBookReplay";

	/**
	 * 我的留言 如果没有登录则跳转到登陆页
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/member/myguestbook.jspx")
	public String myguestbook(Integer pageNo, Integer ctgId, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Site site = CmsUtils.getSite(request);
		User user = CmsUtils.getUser(request);
		FrontUtils.frontData(request, model, site);
		MemberConfig mcfg = site.getConfig().getMemberConfig();
		// 没有开启会员功能
		if (!mcfg.isMemberOn()) {
			return FrontUtils.showMessage(request, model, "member.memberClose");
		}
		if (user == null) {
			return FrontUtils.showLogin(request, model, site);
		}
		Pagination pagination = guestbookMng.getPage(site.getId(), ctgId, user.getId(), null, null, true, false, cpn(pageNo), CookieUtils.getPageSize(request));
		model.addAttribute("pagination", pagination);
		return FrontUtils.getTplPath(request, site.getTplPath(), TPLDIR_GUESTBOOK, GUESTBOOK_LIST);
	}

	/**
	 * 留言详细
	 */
	@RequestMapping(value = "/member/guestbook_detail.jspx")
	public String guestbook_detail(Integer id, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Site site = CmsUtils.getSite(request);
		User user = CmsUtils.getUser(request);
		FrontUtils.frontData(request, model, site);
		MemberConfig mcfg = site.getConfig().getMemberConfig();
		// 没有开启会员功能
		if (!mcfg.isMemberOn()) {
			return FrontUtils.showMessage(request, model, "member.memberClose");
		}
		if (user == null) {
			return FrontUtils.showLogin(request, model, site);
		}
		Guestbook guestbook = guestbookMng.findById(id);
		model.addAttribute("guestbook", guestbook);
		return FrontUtils.getTplPath(request, site.getTplPath(), TPLDIR_GUESTBOOK, GUESTBOOK_DETAIL);
	}

	/**
	 * 查看留言回复
	 */
	@RequestMapping(value = "/member/guestbook_replay.jspx")
	public String guestbook_replay(Integer id, String nextUrl, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Site site = CmsUtils.getSite(request);
		User user = CmsUtils.getUser(request);
		FrontUtils.frontData(request, model, site);
		MemberConfig mcfg = site.getConfig().getMemberConfig();
		// 没有开启会员功能
		if (!mcfg.isMemberOn()) {
			return FrontUtils.showMessage(request, model, "member.memberClose");
		}
		if (user == null) {
			return FrontUtils.showLogin(request, model, site);
		}
		Guestbook guestbook = guestbookMng.findById(id);
		if (!guestbook.getMember().equals(user)) {
			WebErrors errors = WebErrors.create(request);
			errors.addErrorCode("error.noPermissionsView");
			return FrontUtils.showError(request, response, model, errors);
		}
		model.addAttribute("guestbook", guestbook);
		return FrontUtils.getTplPath(request, site.getTplPath(), TPLDIR_GUESTBOOK, GUESTBOOK_REPLAY);
	}
}

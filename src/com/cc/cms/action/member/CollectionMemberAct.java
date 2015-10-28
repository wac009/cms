
package com.cc.cms.action.member;

import static com.cc.common.page.SimplePage.*;
import static com.cc.cms.Constants.*;

import javax.servlet.http.*;

import org.apache.commons.lang.*;
import org.json.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

import com.cc.cms.entity.main.*;
import com.cc.cms.service.main.*;
import com.cc.cms.web.*;
import com.cc.common.page.*;
import com.cc.common.web.*;

/**
 * 收藏信息Action
 * 
 * @author wangcheng
 */
@Controller
public class CollectionMemberAct {

	@Autowired
	private IContentSvc			contentMng;
	@Autowired
	private IUserSvc			userMng;
	public static final String	COLLECTION_LIST	= "tpl.collectionList";

	/**
	 * 我的收藏信息 如果没有登录则跳转到登陆页
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/member/collection_list.jspx")
	public String collection_list(String queryTitle, Integer queryChannelId, Integer pageNo, HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
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
		Pagination p = contentMng.getPageForCollection(site.getId(), user.getId(), cpn(pageNo), CookieUtils.getPageSize(request));
		model.addAttribute("pagination", p);
		if (!StringUtils.isBlank(queryTitle)) {
			model.addAttribute("queryTitle", queryTitle);
		}
		if (queryChannelId != null) {
			model.addAttribute("queryChannelId", queryChannelId);
		}
		return FrontUtils.getTplPath(request, site.getTplPath(), TPLDIR_MEMBER, COLLECTION_LIST);
	}

	@RequestMapping(value = "/member/collect.jspx")
	public void collect(Integer cId, Integer operate, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws JSONException {
		User user = CmsUtils.getUser(request);
		JSONObject object = new JSONObject();
		if (user == null) {
			object.put("result", false);
		} else {
			object.put("result", true);
			userMng.updateUserConllection(user, cId, operate);
		}
		ResponseUtils.renderJson(response, object.toString());
	}

	@RequestMapping(value = "/member/collect_exist.jspx")
	public void collect_exist(Integer cId, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws JSONException {
		Site site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		User user = CmsUtils.getUser(request);
		JSONObject object = new JSONObject();
		FrontUtils.frontData(request, model, site);
		if (user == null) {
			object.put("result", false);
		} else {
			if (user.getCollectContents().contains(contentMng.findById(cId))) {
				object.put("result", true);
			} else {
				object.put("result", false);
			}
		}
		ResponseUtils.renderJson(response, object.toString());
	}
}

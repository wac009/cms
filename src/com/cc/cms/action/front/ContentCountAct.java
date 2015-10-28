
package com.cc.cms.action.front;

import javax.servlet.http.*;

import org.json.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import com.cc.cms.service.main.*;
import com.cc.common.web.*;

/** @author wangcheng */
@Controller
public class ContentCountAct {

	@Autowired
	private IContentCountCache	contentCountCache;
	@Autowired
	private IContentCountSvc	contentCountMng;

	@RequestMapping(value = "/content_view.jspx", method = RequestMethod.GET)
	public void contentView(Integer contentId, HttpServletRequest request, HttpServletResponse response) throws JSONException {
		if (contentId == null) {
			ResponseUtils.renderJson(response, "[]");
			return;
		}
		int[] counts = contentCountCache.viewAndGet(contentId);
		String json;
		if (counts != null) {
			json = new JSONArray(counts).toString();
			ResponseUtils.renderJson(response, json);
		} else {
			ResponseUtils.renderJson(response, "[]");
		}
	}

	@RequestMapping(value = "/content_up.jspx", method = RequestMethod.GET)
	public void contentUp(Integer contentId, HttpServletRequest request, HttpServletResponse response) throws JSONException {
		if (contentId == null) {
			ResponseUtils.renderJson(response, "false");
		} else {
			contentCountMng.contentUp(contentId);
			ResponseUtils.renderJson(response, "true");
		}
	}

	@RequestMapping(value = "/content_down.jspx", method = RequestMethod.GET)
	public void contentDown(Integer contentId, HttpServletRequest request, HttpServletResponse response) throws JSONException {
		if (contentId == null) {
			ResponseUtils.renderJson(response, "false");
		} else {
			contentCountMng.contentDown(contentId);
			ResponseUtils.renderJson(response, "true");
		}
	}
}

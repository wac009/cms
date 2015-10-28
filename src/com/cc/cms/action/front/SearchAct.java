
package com.cc.cms.action.front;

import static com.cc.cms.Constants.*;

import javax.servlet.http.*;

import org.apache.commons.lang.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

import com.cc.cms.entity.main.*;
import com.cc.cms.web.*;
import com.cc.common.web.*;

/** @author wangcheng */
@Controller
public class SearchAct {

	public static final String	SEARCH_INPUT	= "tpl.searchInput";
	public static final String	SEARCH_RESULT	= "tpl.searchResult";

	@RequestMapping(value = "/search*.jspx", method = RequestMethod.GET)
	public String index(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Site site = CmsUtils.getSite(request);
		// 将request中所有参数保存至model中。
		model.putAll(RequestUtils.getQueryParams(request));
		FrontUtils.frontData(request, model, site);
		FrontUtils.frontPageData(request, model);
		String q = RequestUtils.getQueryParam(request, "q");
		String channelId = RequestUtils.getQueryParam(request, "channelId");
		if (StringUtils.isBlank(q) && StringUtils.isBlank(channelId)) {
			model.remove("q");
			model.remove("channelId");
			return FrontUtils.getTplPath(request, site.getTplPath(), TPLDIR_SPECIAL, SEARCH_INPUT);
		} else {
			return FrontUtils.getTplPath(request, site.getTplPath(), TPLDIR_SPECIAL, SEARCH_RESULT);
		}
	}
}

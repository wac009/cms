
package com.cc.cms.action.front;

import static com.cc.cms.Constants.*;

import javax.servlet.http.*;

import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

import com.cc.cms.entity.main.*;
import com.cc.cms.web.*;

/** @author wangcheng */
@Controller
public class RssAct {

	public static final String	RSS_TPL	= "tpl.rss";

	@RequestMapping(value = "/rss.jspx", method = RequestMethod.GET)
	public String rss(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		response.setContentType("text/xml;charset=UTF-8");
		Site site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		return FrontUtils.getTplPath(request, site.getTplPath(), TPLDIR_SPECIAL, RSS_TPL);
	}
}

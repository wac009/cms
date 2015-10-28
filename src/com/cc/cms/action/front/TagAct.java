
package com.cc.cms.action.front;

import static com.cc.cms.Constants.*;

import javax.servlet.http.*;

import org.apache.commons.lang.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

import com.cc.cms.entity.main.*;
import com.cc.cms.service.main.*;
import com.cc.cms.web.*;

/** @author wangcheng */
@Controller
public class TagAct {

	public static final String	TAGS_INDEX	= "tpl.tagIndex";
	public static final String	TAGS_DETAIL	= "tpl.tagDetail";
	@Autowired
	private IContentTagSvc		contentTagMng;

	@RequestMapping(value = "/tag*.jspx", method = RequestMethod.GET)
	public String index(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Site site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		FrontUtils.frontPageData(request, model);
		return FrontUtils.getTplPath(request, site.getTplPath(), TPLDIR_SPECIAL, TAGS_INDEX);
	}

	@RequestMapping(value = "/tag/{path}.jspx", method = RequestMethod.GET)
	public String tags(@PathVariable String path, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Site site = CmsUtils.getSite(request);
		if (StringUtils.isBlank(path)) {
			return FrontUtils.pageNotFound(request, response, model);
		}
		int index = path.indexOf("_");
		int pageNo, id;
		try {
			if (index != -1) {
				id = Integer.valueOf(path.substring(0, index));
				pageNo = Integer.valueOf(path.substring(index + 1, path.length()));
			} else {
				id = Integer.valueOf(path);
				pageNo = 1;
			}
		} catch (NumberFormatException e) {
			return FrontUtils.pageNotFound(request, response, model);
		}
		ContentTag tag = contentTagMng.findById(id);
		if (tag == null) {
			return FrontUtils.pageNotFound(request, response, model);
		}
		model.addAttribute("tag", tag);
		model.addAttribute(FrontUtils.PAGE_NO, pageNo);
		FrontUtils.frontData(request, model, site);
		FrontUtils.frontPageData(request, model);
		return FrontUtils.getTplPath(request, site.getTplPath(), TPLDIR_SPECIAL, TAGS_DETAIL);
	}
}

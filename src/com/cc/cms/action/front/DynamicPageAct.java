package com.cc.cms.action.front;

import static com.cc.cms.Constants.*;

import javax.servlet.http.*;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

import com.cc.cms.entity.main.*;
import com.cc.cms.service.assist.*;
import com.cc.cms.service.main.*;
import com.cc.cms.web.*;
import com.cc.common.page.*;
import com.cc.core.web.*;
import com.cc.core.web.URLHelper.*;

/** @author wangcheng */
@Controller
public class DynamicPageAct {
	private static final Logger log = LoggerFactory.getLogger(DynamicPageAct.class);
	@Autowired
	private IChannelSvc channelMng;
	@Autowired
	private IContentSvc contentMng;
	@Autowired
	private IKeywordSvc cmsKeywordMng;
	/**
	 * 首页模板名称
	 */
	public static final String TPL_INDEX = "tpl.index";

	/**
	 * TOMCAT的默认路径
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(HttpServletRequest request, ModelMap model) {
		Site site = CmsUtils.getSite(request);
		if (null == site) {
			return "/error/website_not_found.html";
		}
		if (null != site && site.getClose()) {
			return "/error/website_closed.html";
		}
		if (null != site.getStaticIndex() && site.getStaticIndex()) {
			return SPT + site.getStaticDir() + "/index" + site.getStaticSuffix();
		}
		FrontUtils.frontData(request, model, site);
		return FrontUtils.getTplPath(request, site.getTplPath(), TPLDIR_INDEX, TPL_INDEX);
	}
	
	/**
	 * WEBLOGIC的默认路径
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/index.jhtml", method = RequestMethod.GET)
	public String indexForWeblogic(HttpServletRequest request, ModelMap model) {
		return index(request, model);
	}

	@RequestMapping(value = "/", method = RequestMethod.HEAD)
	public String indexHead(HttpServletRequest request, ModelMap model) {
		return index(request, model);
	}

	@RequestMapping(value = "/**/*.*", method = RequestMethod.HEAD)
	public String dynamicHead(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		return dynamic(request, response, model);
	}

	/**
	 * 动态页入口
	 */
	@RequestMapping(value = "/**/*.*", method = RequestMethod.GET)
	public String dynamic(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		// 尽量不要携带太多参数，多使用标签获取数据。
		// 目前已知的需要携带翻页信息。
		// 获得页号和翻页信息吧。
		int pageNo = URLHelper.getPageNo(request);
		String[] params = URLHelper.getParams(request);
		PageInfo info = URLHelper.getPageInfo(request);
		String[] paths = URLHelper.getPaths(request);
		int len = paths.length;
		if (len == 1) {
			// 单页
			return channel(paths[0], pageNo, params, info, request, response, model);
		} else if (len == 2) {
			if (paths[1].equals(INDEX)) {
				// 栏目页
				return channel(paths[0], pageNo, params, info, request, response, model);
			} else {
				// 内容页
				try {
					Integer id = Integer.parseInt(paths[1]);
					return content(id, pageNo, params, info, request, response, model);
				} catch (NumberFormatException e) {
					log.debug("Content id must String: {}", paths[1]);
					return FrontUtils.pageNotFound(request, response, model);
				}
			}
		} else {
			log.debug("Illegal path length: {}, paths: {}", len, paths);
			return FrontUtils.pageNotFound(request, response, model);
		}
	}

	public String channel(String path, int pageNo, String[] params, PageInfo info, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Site site = CmsUtils.getSite(request);
		if (null == site) {
			return "/website_not_found.html";
		}
		if (null != site && site.getClose()) {
			return "/website_closed.html";
		}
		Channel channel = channelMng.findByPathForTag(path, site.getId());
		if (channel == null) {
			log.debug("Channel path not found: {}", path);
			return FrontUtils.pageNotFound(request, response, model);
		}
		model.addAttribute("channel", channel);
		FrontUtils.frontData(request, model, site);
		FrontUtils.frontPageData(request, model);
		return channel.getTplChannelOrDef();
	}

	public String content(Integer id, int pageNo, String[] params, PageInfo info, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Site site = CmsUtils.getSite(request);
		if (null == site) {
			return "/website_not_found.html";
		}
		if (null != site && site.getClose()) {
			return "/website_closed.html";
		}
		Content content = contentMng.findById(id);
		if (content == null) {
			log.debug("Content id not found: {}", id);
			return FrontUtils.pageNotFound(request, response, model);
		}
		String txt = content.getTxtByNo(pageNo);
		// 内容加上关键字
		txt = cmsKeywordMng.attachKeyword(site.getId(), txt);
		IPaginable pagination = new SimplePage(pageNo, 1, content.getPageCount());
		model.addAttribute("pagination", pagination);
		FrontUtils.frontPageData(request, model);
		model.addAttribute("content", content);
		model.addAttribute("channel", content.getChannel());
		model.addAttribute("title", content.getTitleByNo(pageNo));
		model.addAttribute("txt", txt);
		model.addAttribute("pic", content.getPictureByNo(pageNo));
		FrontUtils.frontData(request, model, site);
		return content.getTplContentOrDef();
	}
}

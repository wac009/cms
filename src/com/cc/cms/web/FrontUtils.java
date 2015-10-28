
package com.cc.cms.web;

import static com.cc.cms.Constants.*;
import static com.cc.common.web.filter.ProcessTimeFilter.*;
import static com.cc.common.web.freemarker.DirectiveUtils.*;
import static com.cc.core.action.front.LoginAct.*;

import java.io.*;
import java.util.*;

import javax.servlet.http.*;

import org.apache.commons.lang.*;
import org.springframework.beans.propertyeditors.*;
import org.springframework.context.*;

import com.cc.cms.entity.main.*;
import com.cc.common.web.*;
import com.cc.common.web.freemarker.*;
import com.cc.common.web.springmvc.*;
import com.cc.core.web.*;
import com.cc.core.web.URLHelper.PageInfo;

import freemarker.core.*;
import freemarker.template.*;

/**
 * 前台工具类
 * 
 * @author wangcheng
 */
public class FrontUtils {

	/** 页面没有找到 */
	public static final String	PAGE_NOT_FOUND		= "tpl.pageNotFound";
	/** 操作成功页面 */
	public static final String	SUCCESS_PAGE		= "tpl.successPage";
	/** 操作失败页面 */
	public static final String	ERROR_PAGE			= "tpl.errorPage";
	/** 信息提示页面 */
	public static final String	MESSAGE_PAGE		= "tpl.messagePage";
	/** 系统资源路径 */
	public static final String	RES_SYS				= "resSys";
	/** 模板资源路径 */
	public static final String	RES_TPL				= "resTpl";
	/** 前台资源路径 */
	public static final String	RES					= "res";
	/** 模板资源表达式 */
	public static final String	RES_EXP				= "${res}";
	/** 部署路径 */
	public static final String	BASE				= "base";
	/** 站点 */
	public static final String	SITE				= "site";
	/** 用户 */
	public static final String	USER				= "user";
	/** 页码 */
	public static final String	PAGE_NO				= "pageNo";
	/** 总条数 */
	public static final String	COUNT				= "count";
	/** 起始条数 */
	public static final String	FIRST				= "first";
	/** 页面完整地址 */
	public static final String	LOCATION			= "location";
	/** 页面翻页地址 */
	public static final String	HREF				= "href";
	/** href前半部（相对于分页） */
	public static final String	HREF_FORMER			= "hrefFormer";
	/** href后半部（相对于分页） */
	public static final String	HREF_LATTER			= "hrefLatter";
	/** 传入参数，列表样式。 */
	public static final String	PARAM_STYLE_LIST	= "styleList";
	/** 传入参数，系统预定义翻页。 */
	public static final String	PARAM_SYS_PAGE		= "sysPage";
	/** 传入参数，用户自定义翻页。 */
	public static final String	PARAM_USER_PAGE		= "userPage";
	/** 返回页面 */
	public static final String	RETURN_URL			= "returnUrl";
	/** 国际化参数 */
	public static final String	ARGS				= "args";

	/**
	 * 获得模板路径。将对模板文件名称进行本地化处理。
	 * 
	 * @param request
	 * @param solution
	 *            方案路径
	 * @param dir
	 *            模板目录。不本地化处理。
	 * @param name
	 *            模板名称。本地化处理。
	 * @return
	 */
	public static String getTplPath(HttpServletRequest request, String solution, String dir, String name) {
		return solution + "/" + dir + "/" + MessageResolver.getMessage(request, name) + TPL_SUFFIX;
	}

	/**
	 * 获得模板路径。将对模板文件名称进行本地化处理。
	 * 
	 * @param messageSource
	 * @param lang
	 *            本地化语言
	 * @param solution
	 *            方案路径
	 * @param dir
	 *            模板目录。不本地化处理。
	 * @param name
	 *            模板名称。本地化处理。
	 * @return
	 */
	public static String getTplPath(MessageSource messageSource, String lang, String solution, String dir, String name) {
		LocaleEditor localeEditor = new LocaleEditor();
		localeEditor.setAsText(lang);
		Locale locale = (Locale) localeEditor.getValue();
		return solution + "/" + dir + "/" + messageSource.getMessage(name, null, locale) + TPL_SUFFIX;
	}

	/**
	 * 获得模板路径。不对模板文件进行本地化处理。
	 * 
	 * @param solution
	 *            方案路径
	 * @param dir
	 *            模板目录。不本地化处理。
	 * @param name
	 *            模板名称。不本地化处理。
	 * @return
	 */
	public static String getTplPath(String solution, String dir, String name) {
		return solution + "/" + dir + "/" + name + TPL_SUFFIX;
	}

	/**
	 * 页面没有找到
	 * 
	 * @param request
	 * @param response
	 * @return 返回“页面没有找到”的模板
	 */
	public static String pageNotFound(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		Site site = CmsUtils.getSite(request);
		frontData(request, model, site);
		return getTplPath(request, site.getTplPath(), TPLDIR_COMMON, PAGE_NOT_FOUND);
	}

	/**
	 * 成功提示页面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	public static String showSuccess(HttpServletRequest request, Map<String, Object> model, String nextUrl) {
		Site site = CmsUtils.getSite(request);
		frontData(request, model, site);
		if (!StringUtils.isBlank(nextUrl)) {
			model.put("nextUrl", nextUrl);
		}
		return getTplPath(request, site.getTplPath(), TPLDIR_COMMON, SUCCESS_PAGE);
	}

	/**
	 * 错误提示页面
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	public static String showError(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model, WebErrors errors) {
		Site site = CmsUtils.getSite(request);
		frontData(request, model, site);
		errors.toModel(model);
		return getTplPath(request, site.getTplPath(), TPLDIR_COMMON, ERROR_PAGE);
	}

	/**
	 * 信息提示页面
	 * 
	 * @param request
	 * @param model
	 * @param message
	 *            进行国际化处理
	 * @return
	 */
	public static String showMessage(HttpServletRequest request, Map<String, Object> model, String message, String... args) {
		Site site = CmsUtils.getSite(request);
		frontData(request, model, site);
		model.put(MANAGE, message);
		if (args != null) {
			model.put(ARGS, args);
		}
		return getTplPath(request, site.getTplPath(), TPLDIR_COMMON, MESSAGE_PAGE);
	}

	/**
	 * 显示登录页面
	 * 
	 * @param request
	 * @param model
	 * @param site
	 * @param message
	 * @return
	 */
	public static String showLogin(HttpServletRequest request, Map<String, Object> model, Site site, String message) {
		if (!StringUtils.isBlank(message)) {
			model.put(MANAGE, message);
		}
		StringBuilder buff = new StringBuilder("redirect:");
		buff.append(site.getLoginUrl()).append("?");
		buff.append(RETURN_URL).append("=");
		buff.append(RequestUtils.getLocation(request));
		if (!StringUtils.isBlank(site.getProcessUrl())) {
			buff.append("&").append(PROCESS_URL).append(site.getProcessUrl());
		}
		return buff.toString();
	}

	/**
	 * 显示登录页面
	 * 
	 * @param request
	 * @param model
	 * @param site
	 * @return
	 */
	public static String showLogin(HttpServletRequest request, Map<String, Object> model, Site site) {
		return showLogin(request, model, site, "true");
	}

	/**
	 * 为前台模板设置公用数据
	 * 
	 * @param request
	 * @param model
	 */
	public static void frontData(HttpServletRequest request, Map<String, Object> map, Site site) {
		User user = CmsUtils.getUser(request);
		String location = RequestUtils.getLocation(request);
		Long startTime = (Long) request.getAttribute(START_TIME);
		frontData(map, site, user, location, startTime);
	}

	public static void frontData(Map<String, Object> map, Site site, User user, String location, Long startTime) {
//		if (startTime != null) {
//			map.put(START_TIME, startTime);
//		}
//		if (user != null) {
//			map.put(USER, user);
//		}
		map.put(SITE, site);
//		String ctx = site.getContextPath() == null ? "" : site.getContextPath();
		
		String ctx="";
		map.put(BASE, ctx);
		map.put(RES_SYS, ctx + RES_SYS);
		String resTpl = ctx + RES_TLP + SPT + site.getTemplate().getResPath();
		String res = ctx + RES_FRONT;
		// resTpl路径需要去除第一个字符'/'
		if (resTpl.startsWith("//")) {
			map.put(RES_TPL, resTpl.substring(1));
		} else {
			map.put(RES_TPL, resTpl);
		}
		// res路径需要去除第一个字符'/'
		if (res.startsWith("//")) {
			map.put(RES, res.substring(1));
		} else {
			map.put(RES, res);
		}
		map.put(LOCATION, location);
	}

	public static void putLocation(Map<String, Object> map, String location) {
		map.put(LOCATION, location);
	}

	/**
	 * 为前台模板设置分页相关数据
	 * 
	 * @param request
	 * @param map
	 */
	public static void frontPageData(HttpServletRequest request, Map<String, Object> map) {
		int pageNo = URLHelper.getPageNo(request);
		PageInfo info = URLHelper.getPageInfo(request);
		String href = info.getHref();
		String hrefFormer = info.getHrefFormer();
		String hrefLatter = info.getHrefLatter();
		frontPageData(pageNo, href, hrefFormer, hrefLatter, map);
	}

	/**
	 * 为前台模板设置分页相关数据
	 * 
	 * @param pageNo
	 * @param href
	 * @param urlFormer
	 * @param urlLatter
	 * @param map
	 */
	public static void frontPageData(int pageNo, String href, String hrefFormer, String hrefLatter, Map<String, Object> map) {
		map.put(PAGE_NO, pageNo);
		map.put(HREF, href);
		map.put(HREF_FORMER, hrefFormer);
		map.put(HREF_LATTER, hrefLatter);
	}

	/**
	 * 标签中获得站点
	 * 
	 * @param env
	 * @return
	 * @throws TemplateModelException
	 */
	public static Site getSite(Environment env) throws TemplateModelException {
		TemplateModel model = env.getGlobalVariable(SITE);
		if (model instanceof AdapterTemplateModel) {
			return (Site) ((AdapterTemplateModel) model).getAdaptedObject(Site.class);
		} else {
			throw new TemplateModelException("'" + SITE + "' not found in DataModel");
		}
	}

	/**
	 * 标签中获得页码
	 * 
	 * @param env
	 * @return
	 * @throws TemplateException
	 */
	public static int getPageNo(Environment env) throws TemplateException {
		TemplateModel pageNo = env.getGlobalVariable(PAGE_NO);
		if (pageNo instanceof TemplateNumberModel) {
			return ((TemplateNumberModel) pageNo).getAsNumber().intValue();
		} else {
			throw new TemplateModelException("'" + PAGE_NO + "' not found in DataModel.");
		}
	}

	public static int getFirst(Map<String, TemplateModel> params) throws TemplateException {
		Integer first = DirectiveUtils.getInt(FIRST, params);
		if (first == null || first <= 0) {
			return 0;
		} else {
			return first - 1;
		}
	}

	/**
	 * 标签参数中获得条数。
	 * 
	 * @param params
	 * @return 如果不存在，或者小于等于0，或者大于5000则返回5000；否则返回条数。
	 * @throws TemplateException
	 */
	public static int getCount(Map<String, TemplateModel> params) throws TemplateException {
		Integer count = DirectiveUtils.getInt(COUNT, params);
		if (count == null || count <= 0 || count >= 5000) {
			return 5000;
		} else {
			return count;
		}
	}

	public static void includePagination(Site site, Map<String, TemplateModel> params, Environment env) throws TemplateException, IOException {
		String sysPage = DirectiveUtils.getString(PARAM_SYS_PAGE, params);
		String userPage = DirectiveUtils.getString(PARAM_USER_PAGE, params);
		if (!StringUtils.isBlank(sysPage)) {
			String tpl = TPL_STYLE_PAGE_CHANNEL + sysPage + TPL_SUFFIX;
			env.include(tpl, UTF8, true);
		} else if (!StringUtils.isBlank(userPage)) {
			String tpl = getTplPath(site.getTplPath(), TPLDIR_STYLE_LIST, userPage);
			env.include(tpl, UTF8, true);
		} else {
			// 没有包含分页
		}
	}

	/**
	 * 标签中包含页面
	 * 
	 * @param tplName
	 * @param site
	 * @param params
	 * @param env
	 * @throws IOException
	 * @throws TemplateException
	 */
	public static void includeTpl(String tplName, Site site, Map<String, TemplateModel> params, Environment env) throws IOException, TemplateException {
		String subTpl = DirectiveUtils.getString(PARAM_TPL_SUB, params);
		String tpl;
		if (StringUtils.isBlank(subTpl)) {
			tpl = getTplPath(site.getTplPath(), TPLDIR_TAG, tplName);
		} else {
			tpl = getTplPath(site.getTplPath(), TPLDIR_TAG, tplName + "_" + subTpl);
		}
		env.include(tpl, UTF8, true);
	}

	/**
	 * 标签中包含用户预定义列表样式模板
	 * 
	 * @param listStyle
	 * @param site
	 * @param env
	 * @throws IOException
	 * @throws TemplateException
	 */
	public static void includeTpl(String listStyle, Site site, Environment env) throws IOException, TemplateException {
		String tpl = getTplPath(site.getTplPath(), TPLDIR_STYLE_LIST, listStyle);
		env.include(tpl, UTF8, true);
	}
}


package com.cc.cms.action.directive;

import static com.cc.common.web.freemarker.DirectiveUtils.*;
import static freemarker.template.ObjectWrapper.*;

import java.io.*;
import java.util.*;

import org.springframework.beans.factory.annotation.*;

import com.cc.cms.entity.assist.*;
import com.cc.cms.service.assist.*;
import com.cc.cms.web.*;
import com.cc.common.web.freemarker.*;

import freemarker.core.*;
import freemarker.template.*;

/**
 * 友情链接类别列表标签
 * 
 * @author wangcheng
 */
public class FriendlinkListDirective implements TemplateDirectiveModel {

	@Autowired
	private IFriendlinkSvc		cmsFriendlinkMng;
	/**
	 * 输入参数，站点ID。
	 */
	public static final String	PARAM_SITE_ID	= "siteId";
	/**
	 * 输入参数，类别ID。
	 */
	public static final String	PARAM_CTG_ID	= "ctgId";
	/**
	 * 输入参数，是否显示。
	 */
	public static final String	PARAM_ENABLED	= "enabled";

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		Integer siteId = getSiteId(params);
		if (siteId == null) {
			siteId = FrontUtils.getSite(env).getId();
		}
		Integer ctgId = getCtgId(params);
		Boolean enabled = getEnabled(params);
		if (enabled == null) {
			enabled = true;
		}
		List<Friendlink> list = cmsFriendlinkMng.getList(siteId, ctgId, enabled);
		Map<String, TemplateModel> paramWrap = new HashMap<String, TemplateModel>(params);
		paramWrap.put(OUT_LIST, DEFAULT_WRAPPER.wrap(list));
		Map<String, TemplateModel> origMap = DirectiveUtils.addParamsToVariable(env, paramWrap);
		body.render(env.getOut());
		DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
	}

	private Integer getSiteId(Map<String, TemplateModel> params) throws TemplateException {
		return DirectiveUtils.getInt(PARAM_SITE_ID, params);
	}

	private Integer getCtgId(Map<String, TemplateModel> params) throws TemplateException {
		return DirectiveUtils.getInt(PARAM_CTG_ID, params);
	}

	private Boolean getEnabled(Map<String, TemplateModel> params) throws TemplateException {
		return DirectiveUtils.getBool(PARAM_ENABLED, params);
	}
}

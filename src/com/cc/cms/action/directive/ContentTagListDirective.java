
package com.cc.cms.action.directive;

import static com.cc.cms.web.FrontUtils.*;
import static com.cc.common.web.freemarker.DirectiveUtils.*;
import static com.cc.cms.Constants.*;
import static freemarker.template.ObjectWrapper.*;

import java.io.*;
import java.util.*;

import org.apache.commons.lang.*;
import org.springframework.beans.factory.annotation.*;

import com.cc.cms.entity.main.*;
import com.cc.cms.service.main.*;
import com.cc.cms.web.*;
import com.cc.common.web.freemarker.*;
import com.cc.common.web.freemarker.DirectiveUtils.InvokeType;

import freemarker.core.*;
import freemarker.template.*;

/** TAG列表标签
 * 
 * @author wangcheng */
public class ContentTagListDirective implements TemplateDirectiveModel {

	@Autowired
	private IContentTagSvc		contentTagMng;
	/** 模板名称 */
	public static final String	TPL_NAME	= "tag_list";

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		Site site = FrontUtils.getSite(env);
		List<ContentTag> list = contentTagMng.getListForTag(FrontUtils.getCount(params));
		Map<String, TemplateModel> paramWrap = new HashMap<String, TemplateModel>(params);
		paramWrap.put(OUT_LIST, DEFAULT_WRAPPER.wrap(list));
		Map<String, TemplateModel> origMap = DirectiveUtils.addParamsToVariable(env, paramWrap);
		InvokeType type = DirectiveUtils.getInvokeType(params);
		String listStyle = DirectiveUtils.getString(PARAM_STYLE_LIST, params);
		if (InvokeType.sysDefined == type) {
			if (StringUtils.isBlank(listStyle)) {
				throw new ParamsRequiredException(PARAM_STYLE_LIST);
			}
			env.include(TPL_STYLE_LIST + listStyle + TPL_SUFFIX, UTF8, true);
		} else if (InvokeType.userDefined == type) {
			if (StringUtils.isBlank(listStyle)) {
				throw new ParamsRequiredException(PARAM_STYLE_LIST);
			}
			FrontUtils.includeTpl(TPL_STYLE_LIST, site, env);
		} else if (InvokeType.custom == type) {
			FrontUtils.includeTpl(TPL_NAME, site, params, env);
		} else if (InvokeType.body == type) {
			body.render(env.getOut());
		} else {
			throw new RuntimeException("invoke type not handled: " + type);
		}
		DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
	}
}

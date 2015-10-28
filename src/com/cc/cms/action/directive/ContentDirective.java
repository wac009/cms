
package com.cc.cms.action.directive;

import static com.cc.common.web.freemarker.DirectiveUtils.*;
import static freemarker.template.ObjectWrapper.*;

import java.io.*;
import java.util.*;

import org.springframework.beans.factory.annotation.*;

import com.cc.cms.entity.main.*;
import com.cc.cms.service.main.*;
import com.cc.cms.web.*;
import com.cc.common.web.freemarker.*;

import freemarker.core.*;
import freemarker.template.*;

/**
 * 内容对象标签
 * 
 * @author wangcheng
 */
public class ContentDirective implements TemplateDirectiveModel {

	@Autowired
	private IContentSvc			contentMng;
	/**
	 * 输入参数，栏目ID。
	 */
	public static final String	PARAM_ID			= "id";
	/**
	 * 输入参数，下一篇。
	 */
	public static final String	PRAMA_NEXT			= "next";
	/**
	 * 输入参数，栏目ID。
	 */
	public static final String	PARAM_CHANNEL_ID	= "channelId";

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		Integer id = getId(params);
		Boolean next = DirectiveUtils.getBool(PRAMA_NEXT, params);
		Content content;
		if (next == null) {
			content = contentMng.findById(id);
		} else {
			Site site = FrontUtils.getSite(env);
			Integer channelId = DirectiveUtils.getInt(PARAM_CHANNEL_ID, params);
			content = contentMng.getSide(id, site.getId(), channelId, next);
		}
		Map<String, TemplateModel> paramWrap = new HashMap<String, TemplateModel>(params);
		paramWrap.put(OUT_BEAN, DEFAULT_WRAPPER.wrap(content));
		Map<String, TemplateModel> origMap = DirectiveUtils.addParamsToVariable(env, paramWrap);
		body.render(env.getOut());
		DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
	}

	private Integer getId(Map<String, TemplateModel> params) throws TemplateException {
		Integer id = DirectiveUtils.getInt(PARAM_ID, params);
		if (id != null) {
			return id;
		} else {
			throw new ParamsRequiredException(PARAM_ID);
		}
	}
}

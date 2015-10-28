package com.cc.common.web.freemarker;

import java.io.*;
import java.util.*;
import com.cc.common.util.*;
import freemarker.core.*;
import freemarker.template.*;

/**
 * HTML文本提取并截断
 * 
 * 需要拦截器com.cc.common.web.ProcessTimeFilter支持
 * 
 * @author wangcheng
 * 
 */
public class HtmlCutDirective implements TemplateDirectiveModel {
	public static final String PARAM_S = "s";
	public static final String PARAM_LEN = "len";
	public static final String PARAM_APPEND = "append";

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		String s = DirectiveUtils.getString(PARAM_S, params);
		Integer len = DirectiveUtils.getInt(PARAM_LEN, params);
		String append = DirectiveUtils.getString(PARAM_APPEND, params);
		if (s != null) {
			Writer out = env.getOut();
			if (len != null) {
				out.append(StrUtils.htmlCut(s, len, append));
			} else {
				out.append(s);
			}
		}
	}
}

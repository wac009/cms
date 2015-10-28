
package com.cc.cms.action.directive;

import static com.cc.cms.web.FrontUtils.*;
import static com.cc.cms.Constants.*;

import java.io.*;
import java.util.*;

import org.apache.commons.lang.*;

import com.cc.cms.entity.main.*;
import com.cc.cms.web.*;
import com.cc.common.web.freemarker.*;

import freemarker.core.*;
import freemarker.template.*;

/**
 * 翻页包含标签
 * 
 * @author wangcheng
 */
public class PaginationDirective implements TemplateDirectiveModel {

	/**
	 * 是否为内容分页。1：内容分页；0：栏目分页。默认栏目分页。
	 */
	public static final String	PARAM_CONTENT	= "content";

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		Site site = FrontUtils.getSite(env);
		String content = DirectiveUtils.getString(PARAM_CONTENT, params);
		if ("1".equals(content)) {
			String sysPage = DirectiveUtils.getString(PARAM_SYS_PAGE, params);
			String userPage = DirectiveUtils.getString(PARAM_USER_PAGE, params);
			if (!StringUtils.isBlank(sysPage)) {
				String tpl = TPL_STYLE_PAGE_CONTENT + sysPage + TPL_SUFFIX;
				env.include(tpl, UTF8, true);
			} else if (!StringUtils.isBlank(userPage)) {
				String tpl = getTplPath(site.getTplPath(), TPLDIR_STYLE_PAGE, userPage);
				env.include(tpl, UTF8, true);
			} else {
				// 没有包含分页
			}
		} else {
			FrontUtils.includePagination(site, params, env);
		}
	}
}

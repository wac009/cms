
package com.cc.cms.action.directive;

import static com.cc.common.web.freemarker.DirectiveUtils.*;
import static freemarker.template.ObjectWrapper.*;

import java.io.*;
import java.util.*;

import org.springframework.beans.factory.annotation.*;

import com.cc.cms.entity.assist.*;
import com.cc.cms.service.assist.*;
import com.cc.common.web.freemarker.*;

import freemarker.core.*;
import freemarker.template.*;

/**
 * 广告对象标签
 * 
 * @author wangcheng
 */
public class AdvertisingDirective implements TemplateDirectiveModel {

	/**
	 * 输入参数，广告ID。
	 */
	public static final String	PARAM_ID	= "id";
	@Autowired
	private IAdvertisingSvc		cmsAdvertisingMng;

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		Integer id = DirectiveUtils.getInt(PARAM_ID, params);
		Advertising ad = null;
		if (id != null) {
			ad = cmsAdvertisingMng.findById(id);
		}
		Map<String, TemplateModel> paramWrap = new HashMap<String, TemplateModel>(params);
		paramWrap.put(OUT_BEAN, DEFAULT_WRAPPER.wrap(ad));
		Map<String, TemplateModel> origMap = DirectiveUtils.addParamsToVariable(env, paramWrap);
		body.render(env.getOut());
		DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
	}
}

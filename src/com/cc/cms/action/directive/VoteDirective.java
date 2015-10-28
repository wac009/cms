
package com.cc.cms.action.directive;

import static com.cc.common.web.freemarker.DirectiveUtils.*;
import static freemarker.template.ObjectWrapper.*;

import java.io.*;
import java.util.*;

import org.springframework.beans.factory.annotation.*;

import com.cc.cms.entity.assist.*;
import com.cc.cms.entity.main.*;
import com.cc.cms.service.assist.*;
import com.cc.cms.web.*;
import com.cc.common.web.freemarker.*;

import freemarker.core.*;
import freemarker.template.*;

/**
 * 投票标签
 * 
 * @author wangcheng
 */
public class VoteDirective implements TemplateDirectiveModel {

	@Autowired
	private IVoteTopicSvc		cmsVoteTopicMng;
	/**
	 * 输入参数，投票ID。可以为空，为空则获取站点的默认投票。
	 */
	public static final String	PARAM_ID		= "id";
	/**
	 * 输入参数，站点ID。默认为当前站点。
	 */
	public static final String	PARAM_SITE_ID	= "siteId";

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		Site site = FrontUtils.getSite(env);
		VoteTopic vote;
		Integer id = getId(params);
		if (id != null) {
			vote = cmsVoteTopicMng.findById(id);
		} else {
			Integer siteId = getSiteId(params);
			if (siteId == null) {
				siteId = site.getId();
			}
			vote = cmsVoteTopicMng.getDefTopic(siteId);
		}
		Map<String, TemplateModel> paramWrap = new HashMap<String, TemplateModel>(params);
		paramWrap.put(OUT_BEAN, DEFAULT_WRAPPER.wrap(vote));
		Map<String, TemplateModel> origMap = DirectiveUtils.addParamsToVariable(env, paramWrap);
		body.render(env.getOut());
		DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
	}

	private Integer getId(Map<String, TemplateModel> params) throws TemplateException {
		return DirectiveUtils.getInt(PARAM_ID, params);
	}

	private Integer getSiteId(Map<String, TemplateModel> params) throws TemplateException {
		return DirectiveUtils.getInt(PARAM_SITE_ID, params);
	}
}

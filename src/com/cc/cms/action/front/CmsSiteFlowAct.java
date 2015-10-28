
package com.cc.cms.action.front;

import javax.servlet.http.*;

import org.apache.commons.lang.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import com.cc.cms.entity.main.*;
import com.cc.cms.service.main.*;
import com.cc.cms.web.*;
import com.cc.common.web.*;

/** @author wangcheng */
@Controller
public class CmsSiteFlowAct {

	@Autowired
	private ISiteFlowCache	cmsSiteFlowCache;

	@RequestMapping("/flow_statistic.jspx")
	public void flowStatistic(HttpServletRequest request, HttpServletResponse response, String page, String referer) {
		ResponseUtils.renderJson(response, "true");
		if (!StringUtils.isBlank(page)) {
			String ip = RequestUtils.getIpAddr(request);
			Site site = CmsUtils.getSite(request);
			String sessionId = RequestUtils.getRequestedSessionId(request);
			cmsSiteFlowCache.flow(site, ip, sessionId, page, referer);
			ResponseUtils.renderJson(response, "true");
		} else {
			ResponseUtils.renderJson(response, "false");
		}
	}
}

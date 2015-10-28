package com.cc.cms.service.main;

import com.cc.cms.entity.main.*;


/**
 * 站点流量缓存接口
 */
public interface ISiteFlowCache {
	public void flow(Site site, String ip, String sessionId, String page, String referrer);
}

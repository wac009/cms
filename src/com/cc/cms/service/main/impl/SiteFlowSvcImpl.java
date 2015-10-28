
package com.cc.cms.service.main.impl;

import java.sql.*;
import java.util.*;
import java.util.Date;

import net.sf.ehcache.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import com.cc.cms.dao.assist.*;
import com.cc.cms.entity.assist.*;
import com.cc.cms.entity.main.*;
import com.cc.cms.service.main.*;
import com.cc.cms.statistic.*;
import com.cc.common.util.*;
import com.cc.core.service.impl.*;

@Service
@Transactional
public class SiteFlowSvcImpl extends CoreSvcImpl<SiteFlow> implements ISiteFlowSvc {

	@Autowired
	public void setDao(ISiteFlowDao dao) {
		super.setDao(dao);
	}

	@Override
	public ISiteFlowDao getDao() {
		return (ISiteFlowDao) super.getDao();
	}

	@Override
	public SiteFlow save(Site site, String ip, String page, String sessionId) {
		SiteFlow siteFlow = new SiteFlow();
		Date now = new Timestamp(System.currentTimeMillis());
		siteFlow.setSite(site);
		siteFlow.setAccessIp(ip);
		siteFlow.setAccessPage(page);
		siteFlow.setAccessTime(now);
		siteFlow.setAccessDate(DateFormatUtils.formatDate(now));
		siteFlow.setSessionId(sessionId);
		return getDao().save(siteFlow);
	}

	@Override
	@Transactional(readOnly = true)
	public SiteFlow findUniqueByProperties(Integer siteId, String accessDate, String sessionId, String page) {
		return getDao().findUniqueByProperties(siteId, accessDate, sessionId, page);
	}

	@Override
	@SuppressWarnings("unchecked")
	public int freshCacheToDB(Ehcache cache) {
		int count = 0;
		List<FlowBean> list = cache.getKeys();
		for (FlowBean bean : list) {
			Element element = cache.get(bean);
			if (element == null) {
				return count;
			}
			SiteFlow siteFlow = (SiteFlow) element.getValue();
			if (siteFlow.getId() == null && siteFlow.getSessionId() != null) {
				getDao().save(siteFlow);
			}
		}
		return count;
	}
}

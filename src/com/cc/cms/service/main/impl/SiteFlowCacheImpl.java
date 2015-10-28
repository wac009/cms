
package com.cc.cms.service.main.impl;

import static com.cc.common.util.ParseURLKeyword.*;

import java.sql.*;
import java.util.Date;

import net.sf.ehcache.*;

import org.apache.commons.lang.*;
import org.hibernate.*;
import org.slf4j.*;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import com.cc.cms.entity.assist.*;
import com.cc.cms.entity.main.*;
import com.cc.cms.service.main.*;
import com.cc.cms.statistic.*;
import com.cc.common.ipseek.*;
import com.cc.common.util.*;

@Service
@Transactional
public class SiteFlowCacheImpl implements ISiteFlowCache, DisposableBean {
	private Logger	log			= LoggerFactory.getLogger(SiteFlowCacheImpl.class);
	// 间隔时间
	private int		interval	= 1 * 30 * 1000;									// 30秒
	// 最后刷新时间
	private long	refreshTime	= System.currentTimeMillis();

	/** 刷新间隔时间
	 * 
	 * @param interval 单位秒 */
	public void setInterval(int interval) {
		this.interval = interval * 1000;
	}

	@Autowired
	private ISiteFlowSvc	manager;
	@Autowired
	private IPSeeker		ipSeeker;

	@Autowired
	@Qualifier("siteFlow")
	private net.sf.ehcache.Cache			cache;

	@Override
	public void flow(Site site, String ip, String sessionId, String page, String referer) {
		SiteFlow siteFlow = create(site, ip, sessionId, page, referer);
		FlowBean flowBean = new FlowBean(siteFlow.getAccessDate(), sessionId, page);
		if (cache.get(flowBean) == null) {
			SiteFlow bean = null;
			try {
				bean = manager.findUniqueByProperties(site.getId(), siteFlow.getAccessDate(), sessionId, page);
			} catch (HibernateException e) {
				cache.remove(flowBean);
			}
			if (bean != null) {
				cache.put(new Element(flowBean, bean));
			} else {
				cache.put(new Element(flowBean, siteFlow));
			}
		}
		refreshToDB();
	}

	private SiteFlow create(Site site, String ip, String sessionId, String page, String referer) {
		SiteFlow bean = new SiteFlow();
		Date now = new Timestamp(System.currentTimeMillis());
		bean.setSite(site);
		bean.setAccessIp(ip);
		bean.setAccessPage(page);
		bean.setAccessTime(now);
		bean.setAccessDate(DateFormatUtils.formatDate(now));
		bean.setSessionId(sessionId);
		bean.setRefererPage(referer);
		bean.setRefererWebSite(getRefererWebSite(referer));
		bean.setArea(ipSeeker.getIPLocation(ip).getCountry());
		bean.setRefererKeyword(getKeyword(referer));
		return bean;
	}

	private void refreshToDB() {
		long time = System.currentTimeMillis();
		if (time > refreshTime + interval) {
			refreshTime = time;
			int count = manager.freshCacheToDB(cache);
			// 清除缓存
			cache.removeAll();
			log.info("refresh cache flows to DB: {}", count);
		}
	}

	/** 销毁BEAN时，缓存入库。 */
	@Override
	public void destroy() throws Exception {
		int count = manager.freshCacheToDB(cache);
		log.info("Bean destroy.refresh cache flows to DB: {}", count);
	}

	private static String getRefererWebSite(String referer) {
		if (StringUtils.isBlank(referer)) {
			return "";
		}
		int start = 0, i = 0, count = 3;
		while (i < count && start != -1) {
			start = referer.indexOf('/', start + 1);
			i++;
		}
		if (start <= 0) {
			throw new IllegalStateException("referer website uri not like 'http://.../...' pattern: " + referer);
		}
		return referer.substring(0, start);
	}

}

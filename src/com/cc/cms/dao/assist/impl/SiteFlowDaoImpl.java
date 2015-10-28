
package com.cc.cms.dao.assist.impl;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import com.cc.cms.dao.CmsDaoImpl;
import com.cc.cms.dao.assist.ISiteFlowDao;
import com.cc.cms.entity.assist.SiteFlow;

/** @author wangcheng */
@Repository
public class SiteFlowDaoImpl extends CmsDaoImpl<SiteFlow> implements ISiteFlowDao {

	@Override
	public SiteFlow save(SiteFlow siteFlow) {
		getSession().save(siteFlow);
		return siteFlow;
	}

	@Override
	public SiteFlow findUniqueByProperties(Integer siteId, String accessDate, String sessionId, String accessPage) {
		String hql = "from SiteFlow bean where bean.site.id=:siteId and bean.accessDate=:accessDate and bean.sessionId=:sessionId and bean.accessPage=:accessPage";
		Query query = getSession().createQuery(hql);
		query.setParameter("siteId", siteId);
		query.setParameter("accessDate", accessDate);
		query.setParameter("sessionId", sessionId);
		query.setParameter("accessPage", accessPage);
		query.setMaxResults(1);
		query.setCacheable(true);
		return (SiteFlow) query.uniqueResult();
	}

	protected Class<SiteFlow> getEntityClass() {
		return SiteFlow.class;
	}
}

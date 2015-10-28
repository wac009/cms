package com.cc.cms.dao;

import java.io.Serializable;

import com.cc.common.orm.hibernate3.Finder;
import com.cc.common.util.TimeRange;
import com.cc.core.dao.impl.CoreDaoImpl;

public class CmsDaoImpl<T extends Serializable> extends CoreDaoImpl<T> implements ICmsDao<T> {
	protected int getTotalCount(String hql, Integer siteId) {
		Finder f = createCacheableFinder(hql);
		f.setParam("siteId", siteId);
		return find(f).size();
	}

	protected long byTimeRange(Finder f) {
		return (Long) find(f).iterator().next();
	}

	protected long byTimeRange(Finder f, Integer siteId, String property, TimeRange timeRange) {
		f.append(" where bean.site.id=:siteId").setParam("siteId", siteId);
		if (timeRange != null) {
			f.append(" and bean." + property + " >= :begin");
			f.append(" and bean." + property + " < :end");
			f.setParam("begin", timeRange.getBegin());
			f.setParam("end", timeRange.getEnd());
		}
		return (Long) find(f).iterator().next();
	}
	
	@Override
	public long getCountByTimeRange(Integer siteId, TimeRange timeRange) {
		return 0;
	}

}

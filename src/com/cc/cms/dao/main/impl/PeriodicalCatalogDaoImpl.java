package com.cc.cms.dao.main.impl;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.cc.cms.dao.CmsDaoImpl;
import com.cc.cms.dao.main.IPeriodicalCatalogDao;
import com.cc.cms.entity.main.PeriodicalCatalog;

@SuppressWarnings("rawtypes")
@Repository
public class PeriodicalCatalogDaoImpl extends CmsDaoImpl<PeriodicalCatalog> implements IPeriodicalCatalogDao {
	@Override
	public PeriodicalCatalog getPrev(PeriodicalCatalog bean) {
		String hql = "from PeriodicalCatalog p where p.priority<? order by p.priority desc";
		List list = createQuery(hql, bean.getPriority()).setFirstResult(0).setMaxResults(1).list();
		if (list == null || list.size() == 0) {
			return null;
		}
		return (PeriodicalCatalog) list.get(0);
	}
	@Override
	public PeriodicalCatalog getNext(PeriodicalCatalog bean) {
		String hql = "from PeriodicalCatalog p where p.priority>? order by p.priority asc";
		List list = createQuery(hql, bean.getPriority()).setFirstResult(0).setMaxResults(1).list();
		if (list == null || list.size() == 0) {
			return null;
		}
		return (PeriodicalCatalog) list.get(0);
	}
	@Override
	public Integer getMaxPriority() {
		Integer c = (Integer) createQuery("select max(p.priority) from PeriodicalCatalog p ").uniqueResult();
		if (c == null)
			c = 0;
		return c;
	}
}

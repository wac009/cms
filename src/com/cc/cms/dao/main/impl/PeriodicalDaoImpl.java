package com.cc.cms.dao.main.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cc.cms.dao.CmsDaoImpl;
import com.cc.cms.dao.main.IPeriodicalDao;
import com.cc.cms.entity.main.Periodical;
import com.cc.common.orm.hibernate3.Finder;

@SuppressWarnings({"unchecked","rawtypes"})
@Repository
public class PeriodicalDaoImpl extends CmsDaoImpl<Periodical> implements IPeriodicalDao {
	@Override
	public List<Periodical> findByPublication(Integer publicationId) {
		String hql = "from Periodical p where p.disabled=true and p.lock=false and p.publication.id=:pubId order by p.priority desc";
		Finder finder = Finder.create(hql).setParam("pubId", publicationId);
		return find(finder);
	}
	@Override
	public Periodical getPrev(Periodical bean) {
		String hql = "from Periodical p where p.priority<? and p.publication.id=? order by p.priority desc";
		List list = createQuery(hql, bean.getPriority(), bean.getPublication().getId()).setFirstResult(0).setMaxResults(1).list();
		if (list == null || list.size() == 0) {
			return null;
		}
		return (Periodical) list.get(0);
	}
	@Override
	public Periodical getNext(Periodical bean) {
		String hql = "from Periodical p where p.priority>? and p.publication.id=? order by p.priority asc";
		List list = createQuery(hql, bean.getPriority(), bean.getPublication().getId()).setFirstResult(0).setMaxResults(1).list();
		if (list == null || list.size() == 0) {
			return null;
		}
		return (Periodical) list.get(0);
	}
	@Override
	public Integer getMaxPriority() {
		Integer c = (Integer) createQuery("select max(p.priority) from Periodical p ").uniqueResult();
		if (c == null)
			c = 0;
		return c;
	}
	@Override
	public List<String> getYearList() {
		String hql = "select p.year as year from Periodical p group by p.year";
		return find(hql);
	}
	@Override
	public void clearCurrent(Integer publicationId) {
		String hql = "update Periodical p set current=false where p.publication.id=:pubid and current=true";
		getSession().createQuery(hql).setInteger("pubid", publicationId).executeUpdate();
	}
}

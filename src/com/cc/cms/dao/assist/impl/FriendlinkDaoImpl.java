/** @author wangcheng */

package com.cc.cms.dao.assist.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cc.cms.dao.CmsDaoImpl;
import com.cc.cms.dao.assist.IFriendlinkDao;
import com.cc.cms.entity.assist.Friendlink;
import com.cc.common.orm.hibernate3.Finder;

@Repository
public class FriendlinkDaoImpl extends CmsDaoImpl<Friendlink> implements IFriendlinkDao {

	@Override
	public List<Friendlink> getList(Integer siteId, Integer ctgId, Boolean enabled) {
		Finder f = Finder.create("from Friendlink bean where 1=1");
		if (enabled != null) {
			f.append(" and bean.enabled=:enabled");
			f.setParam("enabled", enabled);
		}
		if (siteId != null) {
			f.append(" and bean.site.id=:siteId");
			f.setParam("siteId", siteId);
		}
		if (ctgId != null) {
			f.append(" and bean.category.id=:ctgId");
			f.setParam("ctgId", ctgId);
		}
		f.append(" order by bean.priority asc");
		return find(f);
	}

	@Override
	public int countByCtgId(Integer ctgId) {
		String hql = "select count(*) from Friendlink bean where bean.category.id=:ctgId";
		return ((Number) getSession().createQuery(hql).setParameter("ctgId", ctgId).iterate().next()).intValue();
	}

	@Override
	public Friendlink findById(Integer id) {
		Friendlink entity = get(id);
		return entity;
	}

	@Override
	public Friendlink save(Friendlink bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
	public Friendlink deleteById(Integer id) {
		Friendlink entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public Friendlink getPrev(Friendlink bean) {
		String hql = "from Friendlink c where c.priority<? order by c.priority desc";
		List list = createQuery(hql, bean.getPriority()).setFirstResult(0).setMaxResults(1).list();
		if (list == null || list.size() == 0) {
			return null;
		}
		return (Friendlink) list.get(0);
	}

	@Override
	@SuppressWarnings("rawtypes")
	public Friendlink getNext(Friendlink bean) {
		String hql = "from Friendlink c where c.priority>? order by c.priority asc";
		List list = createQuery(hql, bean.getPriority()).setFirstResult(0).setMaxResults(1).list();
		if (list == null || list.size() == 0) {
			return null;
		}
		return (Friendlink) list.get(0);
	}

	@Override
	public Integer getMaxPriority() {
		Integer c = (Integer) createQuery("select max(c.priority) from Friendlink c ").uniqueResult();
		if (c == null)
			c = 0;
		return c;
	}
}
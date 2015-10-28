/** @author wangcheng */

package com.cc.cms.dao.assist.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cc.cms.dao.CmsDaoImpl;
import com.cc.cms.dao.assist.IFriendlinkCtgDao;
import com.cc.cms.entity.assist.FriendlinkCtg;
import com.cc.common.orm.hibernate3.Finder;

@Repository
public class FriendlinkCtgDaoImpl extends CmsDaoImpl<FriendlinkCtg> implements IFriendlinkCtgDao {

	@Override
	public List<FriendlinkCtg> getList(Integer siteId) {
		Finder f = Finder.create("from FriendlinkCtg bean");
		if (siteId != null) {
			f.append(" where bean.site.id=:siteId");
			f.setParam("siteId", siteId);
		}
		f.append(" order by bean.priority asc");
		return find(f);
	}

	@Override
	public int countBySiteId(Integer siteId) {
		String hql = "select count(*) from FriendlinkCtg bean where bean.site.id=:siteId";
		return ((Number) getSession().createQuery(hql).setParameter("siteId", siteId).iterate().next()).intValue();
	}

	@Override
	public FriendlinkCtg findById(Integer id) {
		FriendlinkCtg entity = get(id);
		return entity;
	}

	@Override
	public FriendlinkCtg save(FriendlinkCtg bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
	public FriendlinkCtg deleteById(Integer id) {
		FriendlinkCtg entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public FriendlinkCtg getPrev(FriendlinkCtg bean) {
		String hql = "from FriendlinkCtg c where c.priority<? order by c.priority desc";
		List list = createQuery(hql, bean.getPriority()).setFirstResult(0).setMaxResults(1).list();
		if (list == null || list.size() == 0) {
			return null;
		}
		return (FriendlinkCtg) list.get(0);
	}

	@Override
	@SuppressWarnings("rawtypes")
	public FriendlinkCtg getNext(FriendlinkCtg bean) {
		String hql = "from FriendlinkCtg c where c.priority>? order by c.priority asc";
		List list = createQuery(hql, bean.getPriority()).setFirstResult(0).setMaxResults(1).list();
		if (list == null || list.size() == 0) {
			return null;
		}
		return (FriendlinkCtg) list.get(0);
	}

	@Override
	public Integer getMaxPriority() {
		Integer c = (Integer) createQuery("select max(c.priority) from FriendlinkCtg c ").uniqueResult();
		if (c == null)
			c = 0;
		return c;
	}
}
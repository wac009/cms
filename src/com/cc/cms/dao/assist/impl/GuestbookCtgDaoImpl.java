/** @author wangcheng */

package com.cc.cms.dao.assist.impl;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.cc.cms.dao.CmsDaoImpl;
import com.cc.cms.dao.assist.IGuestbookCtgDao;
import com.cc.cms.entity.assist.GuestbookCtg;

@Repository
public class GuestbookCtgDaoImpl extends CmsDaoImpl<GuestbookCtg> implements IGuestbookCtgDao {

	@Override
	@SuppressWarnings("unchecked")
	public List<GuestbookCtg> getList(Integer siteId) {
		String hql = "from GuestbookCtg bean" + " where bean.site.id=? order by bean.priority asc";
		return find(hql, siteId);
	}

	@Override
	public GuestbookCtg findById(Integer id) {
		GuestbookCtg entity = get(id);
		return entity;
	}

	@Override
	public GuestbookCtg save(GuestbookCtg bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
	public GuestbookCtg deleteById(Integer id) {
		GuestbookCtg entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public GuestbookCtg getPrev(GuestbookCtg bean) {
		String hql = "from GuestbookCtg c where c.priority<? order by c.priority desc";
		List list = createQuery(hql, bean.getPriority()).setFirstResult(0).setMaxResults(1).list();
		if (list == null || list.size() == 0) {
			return null;
		}
		return (GuestbookCtg) list.get(0);
	}

	@Override
	@SuppressWarnings("rawtypes")
	public GuestbookCtg getNext(GuestbookCtg bean) {
		String hql = "from GuestbookCtg c where c.priority>? order by c.priority asc";
		List list = createQuery(hql, bean.getPriority()).setFirstResult(0).setMaxResults(1).list();
		if (list == null || list.size() == 0) {
			return null;
		}
		return (GuestbookCtg) list.get(0);
	}

	@Override
	public Integer getMaxPriority() {
		Integer c = (Integer) createQuery("select max(c.priority) from GuestbookCtg c ").uniqueResult();
		if (c == null)
			c = 0;
		return c;
	}
}
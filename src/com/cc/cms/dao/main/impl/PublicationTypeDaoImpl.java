package com.cc.cms.dao.main.impl;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.cc.cms.dao.CmsDaoImpl;
import com.cc.cms.dao.main.IPublicationTypeDao;
import com.cc.cms.entity.main.PublicationType;

@SuppressWarnings("rawtypes")
@Repository
public class PublicationTypeDaoImpl extends CmsDaoImpl<PublicationType> implements IPublicationTypeDao {
	@Override
	public PublicationType getPrev(PublicationType bean) {
		String hql = "from PublicationType p where p.priority<? order by p.priority desc";
		List list = createQuery(hql, bean.getPriority()).setFirstResult(0).setMaxResults(1).list();
		if (list == null || list.size() == 0) {
			return null;
		}
		return (PublicationType) list.get(0);
	}
	@Override
	public PublicationType getNext(PublicationType bean) {
		String hql = "from PublicationType p where p.priority>? order by p.priority asc";
		List list = createQuery(hql, bean.getPriority()).setFirstResult(0).setMaxResults(1).list();
		if (list == null || list.size() == 0) {
			return null;
		}
		return (PublicationType) list.get(0);
	}
	@Override
	public Integer getMaxPriority() {
		Integer c = (Integer) createQuery("select max(p.priority) from PublicationType p ").uniqueResult();
		if (c == null)
			c = 0;
		return c;
	}
}

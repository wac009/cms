package com.cc.cms.dao.main.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cc.cms.dao.CmsDaoImpl;
import com.cc.cms.dao.main.IPublicationDao;
import com.cc.cms.entity.main.Publication;
import com.cc.common.orm.hibernate3.Finder;
import com.cc.common.page.Pagination;

@SuppressWarnings({"unchecked","rawtypes"})
@Repository
public class PublicationDaoImpl extends CmsDaoImpl<Publication> implements IPublicationDao {
	@Override
	public Pagination getForTag(Integer webId, int orderBy, boolean isPage, int firstResult, int pageNo, int pageSize) {
		Finder f = Finder.create("select bean from Publication bean where disabled=false");
		f.append(" and bean.website.id=:webId").setParam("webId", webId);
		appendOrder(f, orderBy);
		if (isPage) {
			return find(f, pageNo, pageSize);
		} else {
			f.setFirstResult(firstResult);
			f.setMaxResults(pageSize);
			List<Publication> list = find(f);
			return new Pagination(pageNo, list.size(), pageSize, list);
		}
	}
	private void appendOrder(Finder f, int orderBy) {
		switch (orderBy) {
			case 1:
				f.append(" order by id desc");
				break;
			case 2:
				f.append(" order by id asc");
				break;
			case 3:
				f.append(" order by bean.addTime desc");
				break;
			case 4:
				f.append(" order by bean.addTime asc");
				break;
			default:
				f.append(" order by bean.priority asc");
		}
	}
	@Override
	public List<Publication> findAll(Integer webId) {
		String hql = "from Publication p where p.website.id=? order by p.priority asc";
		return find(hql, webId);
	}
	@Override
	public Publication getPrev(Publication bean) {
		String hql = "from Publication p where p.priority<? order by p.priority desc";
		List list = createQuery(hql, bean.getPriority()).setFirstResult(0).setMaxResults(1).list();
		if (list == null || list.size() == 0) {
			return null;
		}
		return (Publication) list.get(0);
	}
	@Override
	public Publication getNext(Publication bean) {
		String hql = "from Publication p where p.priority>? order by p.priority asc";
		List list = createQuery(hql, bean.getPriority()).setFirstResult(0).setMaxResults(1).list();
		if (list == null || list.size() == 0) {
			return null;
		}
		return (Publication) list.get(0);
	}
	@Override
	public Integer getMaxPriority() {
		Integer c = (Integer) createQuery("select max(p.priority) from Publication p ").uniqueResult();
		if (c == null)
			c = 0;
		return c;
	}
}

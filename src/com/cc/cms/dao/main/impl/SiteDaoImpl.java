
package com.cc.cms.dao.main.impl;

import java.util.List;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import com.cc.cms.dao.CmsDaoImpl;
import com.cc.cms.dao.main.ISiteDao;
import com.cc.cms.entity.main.Site;

/** @author wangcheng */
@SuppressWarnings("unchecked")
@Repository
public class SiteDaoImpl extends CmsDaoImpl<Site> implements ISiteDao {
	@Override
	public int siteCount(boolean cacheable) {
		String hql = "select count(*) from Site bean";
		return ((Number) getSession().createQuery(hql).setCacheable(cacheable).iterate().next()).intValue();
	}

	@Override
	public List<Site> getList(boolean cacheable) {
		String hql = "from Site bean order by bean.id asc";
		return getSession().createQuery(hql).setCacheable(cacheable).list();
	}

	@Override
	public Site findByDomain(String domain, boolean cacheable) {
		String hql = "from Site bean where domain=:domain";
		Query query = getSession().createQuery(hql).setString("domain", domain);
		query.setCacheable(cacheable);
		return (Site) query.uniqueResult();
	}

	@Override
	public List<Site> getListByUser(Integer userId) {
		String hql = "select web from User user" + " inner join user.website web" + " where user.id=?";
		return find(hql, userId);
	}

	@Override
	public List<Site> getListForUpdate(Integer webId) {
		String hql = "select bean from Site bean,Site parent" + " where (bean.lft<parent.lft or bean.rgt >parent.rgt)" + " and parent.id=?";
		return find(hql, webId);
	}

	@Override
	public List<Site> getListByAdmin(Integer adminId) {
		String hql = "select web from User u" + " inner join u.site web" + " where u.id=?";
		return find(hql, adminId);
	}
	
	@Override
	@SuppressWarnings("rawtypes")
	public Site getPrev(Site bean) {
		String hql = "from Site c where c.priority<? and c.parent=? order by c.priority desc";
		if (bean.getParent() == null) {
			hql = "from Site c where c.priority<? and c.parent is null order by c.priority desc";
			List list = createQuery(hql, bean.getPriority()).setFirstResult(0).setMaxResults(1).list();
			if (list == null || list.size() == 0) {
				return null;
			}
			return (Site) list.get(0);
		}
		List list = createQuery(hql, bean.getPriority(), bean.getParent()).setFirstResult(0).setMaxResults(1).list();
		if (list == null || list.size() == 0) {
			return null;
		}
		return (Site) list.get(0);
	}
	
	@Override
	@SuppressWarnings("rawtypes")
	public Site getNext(Site bean) {
		String hql = "from Site c where c.priority>? and c.parent=? order by c.priority asc";
		if (bean.getParent() == null) {
			hql = "from Site c where c.priority>? and c.parent is null order by c.priority desc";
			List list = createQuery(hql, bean.getPriority()).setFirstResult(0).setMaxResults(1).list();
			if (list == null || list.size() == 0) {
				return null;
			}
			return (Site) list.get(0);
		}
		List list = createQuery(hql, bean.getPriority(), bean.getParent()).setFirstResult(0).setMaxResults(1).list();
		if (list == null || list.size() == 0) {
			return null;
		}
		return (Site) list.get(0);
	}
	@Override
	public Integer getMaxPriority() {
		Integer c = (Integer) createQuery("select max(c.priority) from Site c ").uniqueResult();
		if (c == null)
			c = 0;
		return c;
	}
}

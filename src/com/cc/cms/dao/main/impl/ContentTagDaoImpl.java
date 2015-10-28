
package com.cc.cms.dao.main.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.cc.cms.dao.CmsDaoImpl;
import com.cc.cms.dao.main.IContentTagDao;
import com.cc.cms.entity.main.ContentTag;
import com.cc.common.orm.hibernate3.Finder;
import com.cc.common.page.Pagination;

/** @author wangcheng */
@Repository
public class ContentTagDaoImpl extends CmsDaoImpl<ContentTag> implements IContentTagDao {
	@Override
	@SuppressWarnings("unchecked")
	public List<ContentTag> getList(Integer count, boolean cacheable) {
		String hql = "from ContentTag bean order by bean.count desc";
		Query query = getSession().createQuery(hql);
		if (count != null) {
			query.setMaxResults(count);
		}
		query.setCacheable(cacheable);
		return query.list();
	}

	@Override
	public Pagination getPage(String name, int pageNo, int pageSize, boolean cacheable) {
		Finder f = Finder.create("from ContentTag bean");
		if (!StringUtils.isBlank(name)) {
			f.append(" where bean.name like :name");
			f.setParam("name", "%" + name + "%");
		}
		f.append(" order by bean.count desc");
		f.setCacheable(cacheable);
		return find(f, pageNo, pageSize);
	}

	@Override
	public ContentTag findByName(String name, boolean cacheable) {
		String hql = "from ContentTag bean where bean.name=:name";
		return (ContentTag) getSession().createQuery(hql).setParameter("name", name).setCacheable(cacheable).uniqueResult();
	}

	@Override
	public int deleteContentRef(Integer id) {
		Query query = getSession().getNamedQuery("ContentTag.deleteContentRef");
		return query.setParameter(0, id).executeUpdate();
	}

	@Override
	public int countContentRef(Integer id) {
		Query query = getSession().getNamedQuery("ContentTag.countContentRef");
		return ((Number) (query.setParameter(0, id).list().iterator().next())).intValue();
	}

}

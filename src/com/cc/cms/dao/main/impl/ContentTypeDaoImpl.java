
package com.cc.cms.dao.main.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.cc.cms.dao.CmsDaoImpl;
import com.cc.cms.dao.main.IContentTypeDao;
import com.cc.cms.entity.main.ContentType;
import com.cc.common.orm.hibernate3.Finder;

/** @author wangcheng */
@Repository
public class ContentTypeDaoImpl extends CmsDaoImpl<ContentType> implements IContentTypeDao {
	@Override
	public List<ContentType> getList(boolean containDisabled) {
		Finder f = Finder.create("from ContentType bean");
		if (!containDisabled) {
			f.append(" where bean.disabled=false");
		}
		f.append(" order by bean.id asc");
		return find(f);
	}

	@Override
	public ContentType getDef() {
		String hql = "from ContentType bean" + " where bean.disabled=false order by bean.id asc";
		Query query = getSession().createQuery(hql).setMaxResults(1);
		List<?> list = query.list();
		if (list.size() > 0) {
			return (ContentType) list.get(0);
		} else {
			return null;
		}
	}
}

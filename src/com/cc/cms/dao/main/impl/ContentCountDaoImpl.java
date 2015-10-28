
package com.cc.cms.dao.main.impl;

import java.util.List;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import com.cc.cms.dao.CmsDaoImpl;
import com.cc.cms.dao.main.IContentCountDao;
import com.cc.cms.entity.main.ContentCount;

@Repository
public class ContentCountDaoImpl extends CmsDaoImpl<ContentCount> implements IContentCountDao {
	@Override
	@SuppressWarnings("unchecked")
	public int freshCacheToDB(Ehcache cache) {
		List<Integer> keys = cache.getKeys();
		if (keys.size() <= 0) {
			return 0;
		}
		Element e;
		Integer views;
		int i = 0;
		String hql = "update ContentCount bean" + " set bean.views=bean.views+:views" + ",bean.viewsMonth=bean.viewsMonth+:views"
				+ ",bean.viewsWeek=bean.viewsWeek+:views" + ",bean.viewsDay=bean.viewsDay+:views" + " where bean.id=:id";
		Query query = getSession().createQuery(hql);
		for (Integer id : keys) {
			e = cache.get(id);
			if (e != null) {
				views = (Integer) e.getValue();
				if (views != null) {
					query.setParameter("views", views);
					query.setParameter("id", id);
					i += query.executeUpdate();
				}
			}
		}
		return i;
	}

	@Override
	public int clearCount(boolean week, boolean month) {
		StringBuilder hql = new StringBuilder("update ContentCount bean");
		hql.append(" set bean.viewsDay=0,commentsDay=0,upsDay=0");
		if (week) {
			hql.append(",bean.viewsWeek=0,commentsWeek=0,upsWeek=0");
		}
		if (month) {
			hql.append(",bean.viewsMonth=0,commentsMonth=0,upsMonth=0");
		}
		return getSession().createQuery(hql.toString()).executeUpdate();
	}

	@Override
	public int copyCount() {
		String hql = "update Content a set" + " a.viewsDay=(select b.viewsDay from ContentCount b where a.id=b.id)"
				+ ",a.commentsDay=(select b.commentsDay from ContentCount b where a.id=b.id)"
				+ ",a.downloadsDay=(select b.downloadsDay from ContentCount b where a.id=b.id)"
				+ ",a.upsDay=(select b.upsDay from ContentCount b where a.id=b.id)";
		return getSession().createQuery(hql).executeUpdate();
	}
}

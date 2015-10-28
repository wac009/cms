
package com.cc.cms.dao.main.impl;

import java.util.Date;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.cc.cms.dao.CmsDaoImpl;
import com.cc.cms.dao.main.ILogDao;
import com.cc.cms.entity.main.Log;
import com.cc.common.orm.hibernate3.Finder;

/**
 * @author wangcheng
 */
@Repository
public class LogDaoImpl extends CmsDaoImpl<Log> implements ILogDao {

	@Override
	public int deleteBatch(Integer category, Integer siteId, Date before) {
		Finder f = Finder.create("delete Log bean where 1=1");
		if (category != null) {
			f.append(" and bean.category=:category");
			f.setParam("category", category);
		}
		if (siteId != null) {
			f.append(" and bean.site.id=:siteId");
			f.setParam("siteId", siteId);
		}
		if (before != null) {
			f.append(" and bean.time<=:before");
			f.setParam("before", before);
		}
		Query q = f.createQuery(getSession());
		return q.executeUpdate();
	}
}

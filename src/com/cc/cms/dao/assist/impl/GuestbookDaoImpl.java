/** @author wangcheng */

package com.cc.cms.dao.assist.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cc.cms.dao.CmsDaoImpl;
import com.cc.cms.dao.assist.IGuestbookDao;
import com.cc.cms.entity.assist.Guestbook;
import com.cc.common.orm.hibernate3.Finder;
import com.cc.common.page.Pagination;
import com.cc.common.util.TimeRange;

@Repository
public class GuestbookDaoImpl extends CmsDaoImpl<Guestbook> implements IGuestbookDao {

	@Override
	public Pagination getPage(Integer siteId, Integer ctgId, Integer userId, Boolean recommend, Boolean checked, boolean asc, boolean cacheable, int pageNo,
			int pageSize) {
		Finder f = createFinder(siteId, ctgId, userId, recommend, checked, asc, cacheable);
		return find(f, pageNo, pageSize);
	}

	@Override
	public List<Guestbook> getList(Integer siteId, Integer ctgId, Boolean recommend, Boolean checked, boolean desc, boolean cacheable, int first, int max) {
		Finder f = createFinder(siteId, ctgId, null, recommend, checked, desc, cacheable);
		f.setFirstResult(first);
		f.setMaxResults(max);
		return find(f);
	}

	private Finder createFinder(Integer siteId, Integer ctgId, Integer userId, Boolean recommend, Boolean checked, boolean desc, boolean cacheable) {
		Finder f = Finder.create("from Guestbook bean where 1=1");
		if (siteId != null) {
			f.append(" and bean.site.id=:siteId");
			f.setParam("siteId", siteId);
		}
		if (ctgId != null) {
			f.append(" and bean.ctg.id=:ctgId");
			f.setParam("ctgId", ctgId);
		}
		if (userId != null) {
			f.append(" and bean.member.id=:userId");
			f.setParam("userId", userId);
		}
		if (recommend != null) {
			f.append(" and bean.recommend=:recommend");
			f.setParam("recommend", recommend);
		}
		if (checked != null) {
			f.append(" and bean.checked=:checked");
			f.setParam("checked", checked);
		}
		if (desc) {
			f.append(" order by bean.id desc");
		} else {
			f.append(" order by bean.id asc");
		}
		f.setCacheable(cacheable);
		return f;
	}

	@Override
	public Guestbook findById(Integer id) {
		Guestbook entity = get(id);
		return entity;
	}

	@Override
	public Guestbook save(Guestbook bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
	public Guestbook deleteById(Integer id) {
		Guestbook entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	@Override
	public long getCountByTimeRange(Integer siteId, TimeRange timeRange) {
		Finder f = createCacheableFinder("select count(*) from Guestbook bean");
		f.append(" where bean.site.id=:siteId").setParam("siteId", siteId);
		if (timeRange != null) {
			f.append(" and bean.createTime >= :begin");
			f.append(" and bean.createTime < :end");
			f.setParam("begin", timeRange.getBegin());
			f.setParam("end", timeRange.getEnd());
		}
		return byTimeRange(f);
	}

	@Override
	public long getCheckedCountByTimeRange(Integer siteId, TimeRange timeRange) {
		Finder f = createCacheableFinder("select count(*) from Guestbook bean");
		f.append(" where bean.site.id=:siteId").setParam("siteId", siteId);
		if (timeRange != null) {
			f.append(" and bean.createTime >= :begin");
			f.append(" and bean.createTime < :end");
			f.setParam("begin", timeRange.getBegin());
			f.setParam("end", timeRange.getEnd());
		}
		f.append(" and bean.checked=true");
		return byTimeRange(f);
	}
}
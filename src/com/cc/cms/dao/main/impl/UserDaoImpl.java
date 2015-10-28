
package com.cc.cms.dao.main.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.cc.cms.dao.CmsDaoImpl;
import com.cc.cms.dao.main.IUserDao;
import com.cc.cms.entity.main.User;
import com.cc.common.orm.hibernate3.Finder;
import com.cc.common.util.TimeRange;

/**
 * @author wangcheng
 */
@Repository
public class UserDaoImpl extends CmsDaoImpl<User> implements IUserDao {

	@Override
	public List<User> getList(String username, String email, Integer siteId, Integer groupId, Boolean disabled, Boolean admin, Integer rank) {
		Finder f = Finder.create("select bean from User bean");
		if (siteId != null) {
			f.append(" join bean.userSites userSite");
			f.append(" where userSite.site.id=:siteId");
			f.setParam("siteId", siteId);
		} else {
			f.append(" where 1=1");
		}
		if (!StringUtils.isBlank(username)) {
			f.append(" and bean.username like :username");
			f.setParam("username", "%" + username + "%");
		}
		if (!StringUtils.isBlank(email)) {
			f.append(" and bean.email like :email");
			f.setParam("email", "%" + email + "%");
		}
		if (groupId != null) {
			f.append(" and bean.group.id=:groupId");
			f.setParam("groupId", groupId);
		}
		if (disabled != null) {
			f.append(" and bean.disabled=:disabled");
			f.setParam("disabled", disabled);
		}
		if (admin != null) {
			f.append(" and bean.admin=:admin");
			f.setParam("admin", admin);
		}
		if (rank != null) {
			f.append(" and bean.rank<=:rank");
			f.setParam("rank", rank);
		}
		f.append(" order by bean.id desc");
		return find(f);
	}

	@Override
	public List<User> getAdminList(Integer siteId, Boolean allChannel, Boolean disabled, Integer rank) {
		Finder f = Finder.create("select bean from User");
		f.append(" bean join bean.userSites us");
		f.append(" where us.site.id=:siteId");
		f.setParam("siteId", siteId);
		if (allChannel != null) {
			f.append(" and us.allChannel=:allChannel");
			f.setParam("allChannel", allChannel);
		}
		if (disabled != null) {
			f.append(" and bean.disabled=:disabled");
			f.setParam("disabled", disabled);
		}
		if (rank != null) {
			f.append(" and bean.rank<=:rank");
			f.setParam("rank", rank);
		}
		f.append(" and bean.admin=true");
		f.append(" order by bean.id asc");
		return find(f);
	}

	@Override
	public User findByUsername(String username) {
		return findUniqueByProperty("username", username);
	}

	@Override
	public int countByUsername(String username) {
		String hql = "select count(*) from User bean where bean.username=:username";
		Query query = getSession().createQuery(hql);
		query.setParameter("username", username);
		return ((Number) query.iterate().next()).intValue();
	}

	@Override
	public int countMemberByUsername(String username) {
		String hql = "select count(*) from User bean where bean.username=:username and bean.admin=false";
		Query query = getSession().createQuery(hql);
		query.setParameter("username", username);
		return ((Number) query.iterate().next()).intValue();
	}

	@Override
	public int countByEmail(String email) {
		String hql = "select count(*) from User bean where bean.email=:email";
		Query query = getSession().createQuery(hql);
		query.setParameter("email", email);
		return ((Number) query.iterate().next()).intValue();
	}
	
	@Override
	public long getCountByTimeRange(Integer siteId, TimeRange timeRange) {
		Finder f = createCacheableFinder("select count(*) from User bean");
		return byTimeRange(f, siteId,"registerTime", timeRange);
	}
	
	@Override
	protected long byTimeRange(Finder f, Integer siteId, String property, TimeRange timeRange) {
		f.append(" join bean.userSites us");
		f.append(" where us.site.id=:siteId");
		f.setParam("siteId", siteId);
		if (timeRange != null) {
			f.append(" and bean." + property + " >= :begin");
			f.append(" and bean." + property + " < :end");
			f.setParam("begin", timeRange.getBegin());
			f.setParam("end", timeRange.getEnd());
		}
		return byTimeRange(f);
	}
}

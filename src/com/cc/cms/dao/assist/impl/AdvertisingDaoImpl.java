/** @author wangcheng */

package com.cc.cms.dao.assist.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cc.cms.dao.CmsDaoImpl;
import com.cc.cms.dao.assist.IAdvertisingDao;
import com.cc.cms.entity.assist.Advertising;
import com.cc.common.orm.hibernate3.Finder;
import com.cc.common.page.Pagination;

@Repository
public class AdvertisingDaoImpl extends CmsDaoImpl<Advertising> implements IAdvertisingDao {

	@Override
	public Pagination getPage(Integer siteId, Integer adspaceId, Boolean enabled, int pageNo, int pageSize) {
		Finder f = Finder.create("from Advertising bean where 1=1");
		if (siteId != null && adspaceId == null) {
			f.append(" and bean.site.id=:siteId");
			f.setParam("siteId", siteId);
		} else if (adspaceId != null) {
			f.append(" and bean.adspace.id=:adspaceId");
			f.setParam("adspaceId", adspaceId);
		}
		if (enabled != null) {
			f.append(" and bean.enabled=:enabled");
			f.setParam("enabled", enabled);
		}
		f.append(" order by bean.id desc");
		return find(f, pageNo, pageSize);
	}

	@Override
	public List<Advertising> getList(Integer adspaceId, Boolean enabled) {
		Finder f = Finder.create("from Advertising bean where 1=1");
		if (adspaceId != null) {
			f.append(" and bean.adspace.id=:adspaceId");
			f.setParam("adspaceId", adspaceId);
		}
		if (enabled != null) {
			f.append(" and bean.enabled=:enabled");
			f.setParam("enabled", enabled);
		}
		return find(f);
	}

	@Override
	public Advertising findById(Integer id) {
		Advertising entity = get(id);
		return entity;
	}

	@Override
	public Advertising save(Advertising bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
	public Advertising deleteById(Integer id) {
		Advertising entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}
}
/** @author wangcheng */

package com.cc.cms.dao.assist.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cc.cms.dao.CmsDaoImpl;
import com.cc.cms.dao.assist.IAdvertisingSpaceDao;
import com.cc.cms.entity.assist.AdvertisingSpace;
import com.cc.common.orm.hibernate3.Finder;

@Repository
public class AdvertisingSpaceDaoImpl extends CmsDaoImpl<AdvertisingSpace> implements IAdvertisingSpaceDao {

	@Override
	public List<AdvertisingSpace> getList(Integer siteId) {
		Finder f = Finder.create("from AdvertisingSpace bean");
		if (siteId != null) {
			f.append(" where bean.site.id=:siteId");
			f.setParam("siteId", siteId);
		}
		return find(f);
	}

	@Override
	public AdvertisingSpace findById(Integer id) {
		AdvertisingSpace entity = get(id);
		return entity;
	}

	@Override
	public AdvertisingSpace save(AdvertisingSpace bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
	public AdvertisingSpace deleteById(Integer id) {
		AdvertisingSpace entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}
}
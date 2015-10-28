
package com.cc.cms.service.assist.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cc.cms.dao.assist.IAdvertisingSpaceDao;
import com.cc.cms.entity.assist.AdvertisingSpace;
import com.cc.cms.service.CmsSvcImpl;
import com.cc.cms.service.assist.IAdvertisingSpaceSvc;
import com.cc.common.orm.Updater;

@Service
@Transactional
public class AdvertisingSpaceSvcImpl extends CmsSvcImpl<AdvertisingSpace> implements IAdvertisingSpaceSvc {

	@Autowired
	public void setDao(IAdvertisingSpaceDao dao) {
		super.setDao(dao);
	}

	@Override
	public IAdvertisingSpaceDao getDao() {
		return (IAdvertisingSpaceDao) super.getDao();
	}

	@Override
	@Transactional(readOnly = true)
	public List<AdvertisingSpace> getList(Integer siteId) {
		return getDao().getList(siteId);
	}

	@Override
	@Transactional(readOnly = true)
	public AdvertisingSpace findById(Integer id) {
		AdvertisingSpace entity = getDao().findById(id);
		return entity;
	}

	@Override
	public AdvertisingSpace save(AdvertisingSpace bean) {
		getDao().save(bean);
		return bean;
	}

	@Override
	public AdvertisingSpace update(AdvertisingSpace bean) {
		Updater<AdvertisingSpace> updater = new Updater<AdvertisingSpace>(bean);
		bean = (AdvertisingSpace) getDao().updateByUpdater(updater);
		return bean;
	}

	@Override
	public AdvertisingSpace deleteById(Integer id) {
		AdvertisingSpace bean = getDao().deleteById(id);
		return bean;
	}

	@Override
	public AdvertisingSpace[] deleteByIds(Integer[] ids) {
		AdvertisingSpace[] beans = new AdvertisingSpace[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

}
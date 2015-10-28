
package com.cc.cms.service.assist.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cc.cms.dao.assist.IAcquisitionTempDao;
import com.cc.cms.entity.assist.AcquisitionTemp;
import com.cc.cms.service.CmsSvcImpl;
import com.cc.cms.service.assist.IAcquisitionTempSvc;
import com.cc.common.orm.Updater;

@Service
@Transactional
public class AcquisitionTempSvcImpl extends CmsSvcImpl<AcquisitionTemp> implements IAcquisitionTempSvc {

	@Autowired
	public void setDao(IAcquisitionTempDao dao) {
		super.setDao(dao);
	}

	@Override
	public IAcquisitionTempDao getDao() {
		return (IAcquisitionTempDao) super.getDao();
	}

	@Override
	@Transactional(readOnly = true)
	public List<AcquisitionTemp> getList(Integer siteId) {
		return getDao().getList(siteId);
	}

	@Override
	@Transactional(readOnly = true)
	public AcquisitionTemp findById(Integer id) {
		AcquisitionTemp entity = getDao().findById(id);
		return entity;
	}

	@Override
	public AcquisitionTemp save(AcquisitionTemp bean) {
		clear(bean.getSite().getId(), bean.getChannelUrl());
		getDao().save(bean);
		return bean;
	}

	@Override
	public AcquisitionTemp update(AcquisitionTemp bean) {
		Updater<AcquisitionTemp> updater = new Updater<AcquisitionTemp>(bean);
		bean = (AcquisitionTemp) getDao().updateByUpdater(updater);
		return bean;
	}

	@Override
	public AcquisitionTemp deleteById(Integer id) {
		AcquisitionTemp bean = getDao().deleteById(id);
		return bean;
	}

	@Override
	public AcquisitionTemp[] deleteByIds(Integer[] ids) {
		AcquisitionTemp[] beans = new AcquisitionTemp[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	@Override
	public Integer getPercent(Integer siteId) {
		return getDao().getPercent(siteId);
	}

	@Override
	public void clear(Integer siteId) {
		getDao().clear(siteId, null);
	}

	@Override
	public void clear(Integer siteId, String channelUrl) {
		getDao().clear(siteId, channelUrl);
	}
}
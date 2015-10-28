
package com.cc.cms.service.assist.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cc.cms.dao.assist.IAcquisitionHistoryDao;
import com.cc.cms.entity.assist.AcquisitionHistory;
import com.cc.cms.service.CmsSvcImpl;
import com.cc.cms.service.assist.IAcquisitionHistorySvc;
import com.cc.common.orm.Updater;
import com.cc.common.page.Pagination;

@Service
@Transactional
public class AcquisitionHistorySvcImpl extends CmsSvcImpl<AcquisitionHistory> implements IAcquisitionHistorySvc {

	@Autowired
	public void setDao(IAcquisitionHistoryDao dao) {
		super.setDao(dao);
	}

	@Override
	public IAcquisitionHistoryDao getDao() {
		return (IAcquisitionHistoryDao) super.getDao();
	}

	@Override
	@Transactional(readOnly = true)
	public List<AcquisitionHistory> getList(Integer siteId, Integer acquId) {
		return getDao().getList(siteId, acquId);
	}

	@Override
	@Transactional(readOnly = true)
	public Pagination getPage(Integer siteId, Integer acquId, Integer pageNo, Integer pageSize) {
		return getDao().getPage(siteId, acquId, pageNo, pageSize);
	}

	@Override
	@Transactional(readOnly = true)
	public AcquisitionHistory findById(Integer id) {
		AcquisitionHistory entity = getDao().findById(id);
		return entity;
	}

	@Override
	public AcquisitionHistory save(AcquisitionHistory bean) {
		getDao().save(bean);
		return bean;
	}

	@Override
	public AcquisitionHistory update(AcquisitionHistory bean) {
		Updater<AcquisitionHistory> updater = new Updater<AcquisitionHistory>(bean);
		bean = (AcquisitionHistory) getDao().updateByUpdater(updater);
		return bean;
	}

	@Override
	public AcquisitionHistory deleteById(Integer id) {
		AcquisitionHistory bean = getDao().deleteById(id);
		return bean;
	}

	@Override
	public AcquisitionHistory[] deleteByIds(Integer[] ids) {
		AcquisitionHistory[] beans = new AcquisitionHistory[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	@Override
	public Boolean checkExistByProperties(Boolean title, String value) {
		return getDao().checkExistByProperties(title, value);
	}
}
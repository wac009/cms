
package com.cc.cms.service.main.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cc.cms.dao.main.IPeriodicalCatalogDao;
import com.cc.cms.entity.main.PeriodicalCatalog;
import com.cc.cms.service.CmsSvcImpl;
import com.cc.cms.service.main.IPeriodicalCatalogSvc;
import com.cc.common.orm.Updater;

@Service
@Transactional
public class PeriodicalCatalogSvcImpl extends CmsSvcImpl<PeriodicalCatalog> implements IPeriodicalCatalogSvc {

	@Autowired
	public void setPeriodicalCatalogDao(IPeriodicalCatalogDao dao) {
		super.setDao(dao);
	}

	@Override
	protected IPeriodicalCatalogDao getDao() {
		return (IPeriodicalCatalogDao) super.getDao();
	}

	@Override
	public PeriodicalCatalog savePeriodicalCatalog(PeriodicalCatalog bean) {
		initBean(bean);
		return saveAndRefresh(bean);
	}

	@Override
	public PeriodicalCatalog updatePeriodicalCatalog(PeriodicalCatalog bean) {
		adjustBean(bean);
		return (PeriodicalCatalog) update(bean);
	}

	private void adjustBean(PeriodicalCatalog bean) {
		if (bean.getDisabled() == null) {
			bean.setDisabled(false);
		}
		if (bean.getCommon() == null) {
			bean.setCommon(false);
		}
		if (bean.getHasContent() == null) {
			bean.setHasContent(false);
		}
	}

	private void initBean(PeriodicalCatalog bean) {
		adjustBean(bean);
		bean.setPriority(getDao().getMaxPriority() + 1);
	}

	@Override
	public boolean isUp(PeriodicalCatalog bean) {
		if (getPrev(bean) == null) {
			return false;
		}
		return true;
	}

	@Override
	public boolean isDown(PeriodicalCatalog bean) {
		if (getNext(bean) == null) {
			return false;
		}
		return true;
	}

	@Override
	public PeriodicalCatalog up(Integer id) {
		PeriodicalCatalog bean = getDao().get(id);
		Integer oPriority = bean.getPriority();
		PeriodicalCatalog beanPre = getPrev(bean);
		bean.setPriority(beanPre.getPriority());
		beanPre.setPriority(oPriority);
		updateByUpdater(Updater.create(bean));
		updateByUpdater(Updater.create(beanPre));
		return bean;
	}

	@Override
	public PeriodicalCatalog down(Integer id) {
		PeriodicalCatalog bean = findById(id);
		Integer oPriority = bean.getPriority();
		PeriodicalCatalog beanNext = getNext(bean);
		bean.setPriority(beanNext.getPriority());
		beanNext.setPriority(oPriority);
		updateByUpdater(Updater.create(bean));
		updateByUpdater(Updater.create(beanNext));
		return bean;
	}

	@Override
	public PeriodicalCatalog getPrev(PeriodicalCatalog bean) {
		return getDao().getPrev(bean);
	}

	@Override
	public PeriodicalCatalog getNext(PeriodicalCatalog bean) {
		return getDao().getNext(bean);
	}
}


package com.cc.cms.service.assist.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cc.cms.dao.assist.IGuestbookCtgDao;
import com.cc.cms.entity.assist.GuestbookCtg;
import com.cc.cms.service.CmsSvcImpl;
import com.cc.cms.service.assist.IGuestbookCtgSvc;
import com.cc.common.orm.Updater;

@Service
@Transactional
public class GuestbookCtgSvcImpl extends CmsSvcImpl<GuestbookCtg> implements IGuestbookCtgSvc {

	@Autowired
	public void setDao(IGuestbookCtgDao dao) {
		super.setDao(dao);
	}

	@Override
	public IGuestbookCtgDao getDao() {
		return (IGuestbookCtgDao) super.getDao();
	}

	@Override
	@Transactional(readOnly = true)
	public List<GuestbookCtg> getList(Integer siteId) {
		return getDao().getList(siteId);
	}

	@Override
	@Transactional(readOnly = true)
	public GuestbookCtg findById(Integer id) {
		GuestbookCtg entity = getDao().findById(id);
		return entity;
	}

	@Override
	public GuestbookCtg save(GuestbookCtg bean) {
		bean.setPriority(getDao().getMaxPriority() + 1);
		getDao().save(bean);
		return bean;
	}

	@Override
	public GuestbookCtg update(GuestbookCtg bean) {
		Updater<GuestbookCtg> updater = new Updater<GuestbookCtg>(bean);
		bean = (GuestbookCtg) getDao().updateByUpdater(updater);
		return bean;
	}

	@Override
	public GuestbookCtg deleteById(Integer id) {
		GuestbookCtg bean = getDao().deleteById(id);
		return bean;
	}

	@Override
	public GuestbookCtg[] deleteByIds(Integer[] ids) {
		GuestbookCtg[] beans = new GuestbookCtg[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	/** 排序 */
	@Override
	public boolean isUp(GuestbookCtg bean) {
		if (getPrev(bean) == null) {
			return false;
		}
		return true;
	}

	@Override
	public boolean isDown(GuestbookCtg bean) {
		if (getNext(bean) == null) {
			return false;
		}
		return true;
	}

	@Override
	public GuestbookCtg up(Integer id) {
		GuestbookCtg bean = findById(id);
		Integer oPriority = bean.getPriority();
		GuestbookCtg beanPre = getDao().getPrev(bean);
		//
		bean.setPriority(beanPre.getPriority());
		beanPre.setPriority(oPriority);
		updateByUpdater(Updater.create(bean));
		updateByUpdater(Updater.create(beanPre));
		return bean;
	}

	@Override
	public GuestbookCtg down(Integer id) {
		GuestbookCtg bean = findById(id);
		Integer oPriority = bean.getPriority();
		GuestbookCtg beanNext = getDao().getNext(bean);
		//
		bean.setPriority(beanNext.getPriority());
		beanNext.setPriority(oPriority);
		updateByUpdater(Updater.create(bean));
		updateByUpdater(Updater.create(beanNext));
		return bean;
	}

	@Override
	public GuestbookCtg getPrev(GuestbookCtg bean) {
		return getDao().getPrev(bean);
	}

	@Override
	public GuestbookCtg getNext(GuestbookCtg bean) {
		return getDao().getNext(bean);
	}
}
package com.cc.cms.service.main.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cc.cms.dao.main.IPublicationDao;
import com.cc.cms.entity.main.Publication;
import com.cc.cms.service.CmsSvcImpl;
import com.cc.cms.service.main.IPublicationSvc;
import com.cc.common.orm.Updater;
import com.cc.common.page.Pagination;

@Service
@Transactional
public class PublicationSvcImpl extends CmsSvcImpl<Publication> implements IPublicationSvc {
	@Autowired
	public void setPublicationDao(IPublicationDao dao) {
		super.setDao(dao);
	}
	@Override
	protected IPublicationDao getDao() {
		return (IPublicationDao) super.getDao();
	}
	@Override
	public Pagination getForTag(Integer webId, int orderBy, boolean isPage, int firstResult, int pageNo, int pageSize) {
		return getDao().getForTag(webId, orderBy, isPage, firstResult, pageNo, pageSize);
	}
	@Override
	public List<Publication> findAll(Integer webId) {
		return getDao().findAll(webId);
	}
	@Override
	public Publication savePublication(Publication bean) {
		initBean(bean);
		return save(bean);
	}
	private void initBean(Publication bean) {
		if (bean.getDisabled() == null) {
			bean.setDisabled(false);
		}
		bean.setPriority(getDao().getMaxPriority() + 1);
	}
	@Override
	public boolean isUp(Publication bean) {
		if (getPrev(bean) == null) {
			return false;
		}
		return true;
	}
	@Override
	public boolean isDown(Publication bean) {
		if (getNext(bean) == null) {
			return false;
		}
		return true;
	}
	@Override
	public Publication up(Integer id) {
		Publication bean = getDao().get(id);
		Integer oPriority = bean.getPriority();
		Publication beanPre = getPrev(bean);
		bean.setPriority(beanPre.getPriority());
		beanPre.setPriority(oPriority);
		updateByUpdater(Updater.create(bean));
		updateByUpdater(Updater.create(beanPre));
		return bean;
	}
	@Override
	public Publication down(Integer id) {
		Publication bean = findById(id);
		Integer oPriority = bean.getPriority();
		Publication beanNext = getNext(bean);
		bean.setPriority(beanNext.getPriority());
		beanNext.setPriority(oPriority);
		updateByUpdater(Updater.create(bean));
		updateByUpdater(Updater.create(beanNext));
		return bean;
	}
	@Override
	public Publication getPrev(Publication bean) {
		return getDao().getPrev(bean);
	}
	@Override
	public Publication getNext(Publication bean) {
		return getDao().getNext(bean);
	}
}

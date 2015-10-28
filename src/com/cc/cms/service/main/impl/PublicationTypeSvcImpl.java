
package com.cc.cms.service.main.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cc.cms.dao.main.IPublicationTypeDao;
import com.cc.cms.entity.main.PublicationType;
import com.cc.cms.service.CmsSvcImpl;
import com.cc.cms.service.main.IPublicationTypeSvc;
import com.cc.common.orm.Updater;

@Service
@Transactional
public class PublicationTypeSvcImpl extends CmsSvcImpl<PublicationType> implements IPublicationTypeSvc {

	@Autowired
	public void setPublicationTypeDao(IPublicationTypeDao dao) {
		super.setDao(dao);
	}

	@Override
	protected IPublicationTypeDao getDao() {
		return (IPublicationTypeDao) super.getDao();
	}

	@Override
	public PublicationType savePublicationType(PublicationType bean) {
		initBean(bean);
		return save(bean);
	}

	private void initBean(PublicationType bean) {
		if (bean.getDisabled() == null) {
			bean.setDisabled(false);
		}
		bean.setPriority(getDao().getMaxPriority() + 1);
	}

	@Override
	public boolean isUp(PublicationType bean) {
		if (getPrev(bean) == null) {
			return false;
		}
		return true;
	}

	@Override
	public boolean isDown(PublicationType bean) {
		if (getNext(bean) == null) {
			return false;
		}
		return true;
	}

	@Override
	public PublicationType up(Integer id) {
		PublicationType bean = getDao().get(id);
		Integer oPriority = bean.getPriority();
		PublicationType beanPre = getPrev(bean);
		bean.setPriority(beanPre.getPriority());
		beanPre.setPriority(oPriority);
		updateByUpdater(Updater.create(bean));
		updateByUpdater(Updater.create(beanPre));
		return bean;
	}

	@Override
	public PublicationType down(Integer id) {
		PublicationType bean = findById(id);
		Integer oPriority = bean.getPriority();
		PublicationType beanNext = getNext(bean);
		bean.setPriority(beanNext.getPriority());
		beanNext.setPriority(oPriority);
		updateByUpdater(Updater.create(bean));
		updateByUpdater(Updater.create(beanNext));
		return bean;
	}

	@Override
	public PublicationType getPrev(PublicationType bean) {
		return getDao().getPrev(bean);
	}

	@Override
	public PublicationType getNext(PublicationType bean) {
		return getDao().getNext(bean);
	}
}

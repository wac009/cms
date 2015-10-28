
package com.cc.cms.service.main.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cc.cms.dao.main.IPeriodicalDao;
import com.cc.cms.entity.main.Periodical;
import com.cc.cms.entity.main.PeriodicalAttachment;
import com.cc.cms.service.CmsSvcImpl;
import com.cc.cms.service.main.IPeriodicalSvc;
import com.cc.common.orm.Updater;
import com.cc.common.orm.Updater.UpdateMode;

@Service
@Transactional
public class PeriodicalSvcImpl extends CmsSvcImpl<Periodical> implements IPeriodicalSvc {
	@Autowired
	public void setPeriodicalDao(IPeriodicalDao dao) {
		super.setDao(dao);
	}

	@Override
	protected IPeriodicalDao getDao() {
		return (IPeriodicalDao) super.getDao();
	}

	@Override
	public List<Periodical> findByPublication(Integer publicationId) {
		return null;
	}

	@Override
	public List<String> getYearList() {
		return getDao().getYearList();
	}

	@Override
	public Periodical savePeriodical(Periodical bean, String attachmentPath, String attachmentName, String attachmentFilename) {
		initBean(bean);
		PeriodicalAttachment attachment = new PeriodicalAttachment();
		attachment.setCount(0);
		attachment.setFilename(attachmentFilename);
		attachment.setName(attachmentName);
		attachment.setPath(attachmentPath);
		bean.setAttachment(attachment);
		return saveAndRefresh(bean);
	}

	@Override
	public Periodical updatePeriodical(Periodical bean, String attachmentPath, String attachmentName, String attachmentFilename) {
		adjustBean(bean);
		Periodical orgin = findById(bean.getId());
		bean.setPriority(orgin.getPriority());
		PeriodicalAttachment attachment = new PeriodicalAttachment();
		attachment.setFilename(attachmentFilename);
		attachment.setName(attachmentName);
		attachment.setPath(attachmentPath);
		bean.setAttachment(attachment);
		return (Periodical) updateByUpdater(Updater.create(bean, UpdateMode.MAX));
	}

	private void adjustBean(Periodical bean) {
		if (bean.getDisabled() == null) {
			bean.setDisabled(false);
		}
		if (bean.getCurrent() == null) {
			bean.setCurrent(false);
		}
		if (bean.getCurrent()) {
			clearCurrent(bean.getPublication().getId());
		}
		if (bean.getLock() == null) {
			bean.setLock(false);
		}
	}

	private void initBean(Periodical bean) {
		adjustBean(bean);
		bean.setPriority(getDao().getMaxPriority() + 1);
	}

	public void clearCurrent(Integer publicationId) {
		getDao().clearCurrent(publicationId);
	}

	@Override
	public boolean isUp(Periodical bean) {
		if (getPrev(bean) == null) {
			return false;
		}
		return true;
	}

	@Override
	public boolean isDown(Periodical bean) {
		if (getNext(bean) == null) {
			return false;
		}
		return true;
	}

	@Override
	public Periodical up(Integer id) {
		Periodical bean = getDao().get(id);
		Integer oPriority = bean.getPriority();
		Periodical beanPre = getPrev(bean);
		bean.setPriority(beanPre.getPriority());
		beanPre.setPriority(oPriority);
		updateByUpdater(Updater.create(bean));
		updateByUpdater(Updater.create(beanPre));
		return bean;
	}

	@Override
	public Periodical down(Integer id) {
		Periodical bean = findById(id);
		Integer oPriority = bean.getPriority();
		Periodical beanNext = getNext(bean);
		bean.setPriority(beanNext.getPriority());
		beanNext.setPriority(oPriority);
		updateByUpdater(Updater.create(bean));
		updateByUpdater(Updater.create(beanNext));
		return bean;
	}

	@Override
	public Periodical getPrev(Periodical bean) {
		return getDao().getPrev(bean);
	}

	@Override
	public Periodical getNext(Periodical bean) {
		return getDao().getNext(bean);
	}
}

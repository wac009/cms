
package com.cc.cms.service.assist.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cc.cms.dao.assist.IFriendlinkCtgDao;
import com.cc.cms.entity.assist.FriendlinkCtg;
import com.cc.cms.service.CmsSvcImpl;
import com.cc.cms.service.assist.IFriendlinkCtgSvc;
import com.cc.common.orm.Updater;

@Service
@Transactional
public class FriendlinkCtgSvcImpl extends CmsSvcImpl<FriendlinkCtg> implements IFriendlinkCtgSvc {

	@Autowired
	public void setDao(IFriendlinkCtgDao dao) {
		super.setDao(dao);
	}

	@Override
	public IFriendlinkCtgDao getDao() {
		return (IFriendlinkCtgDao) super.getDao();
	}

	@Override
	@Transactional(readOnly = true)
	public List<FriendlinkCtg> getList(Integer siteId) {
		return getDao().getList(siteId);
	}

	@Override
	public int countBySiteId(Integer siteId) {
		return getDao().countBySiteId(siteId);
	}

	@Override
	@Transactional(readOnly = true)
	public FriendlinkCtg findById(Integer id) {
		FriendlinkCtg entity = getDao().findById(id);
		return entity;
	}

	@Override
	public FriendlinkCtg save(FriendlinkCtg bean) {
		bean.setPriority(getDao().getMaxPriority()+1);
		getDao().save(bean);
		return bean;
	}

	@Override
	public FriendlinkCtg update(FriendlinkCtg bean) {
		Updater<FriendlinkCtg> updater = new Updater<FriendlinkCtg>(bean);
		bean = (FriendlinkCtg) getDao().updateByUpdater(updater);
		return bean;
	}

	@Override
	public void updateFriendlinkCtgs(Integer[] ids, String[] names, Integer[] priorities) {
		if (ids == null || ids.length == 0) {
			return;
		}
		FriendlinkCtg ctg;
		for (int i = 0; i < ids.length; i++) {
			ctg = getDao().findById(ids[i]);
			ctg.setName(names[i]);
			ctg.setPriority(priorities[i]);
		}
	}

	@Override
	public FriendlinkCtg deleteById(Integer id) {
		FriendlinkCtg bean = getDao().deleteById(id);
		return bean;
	}

	@Override
	public FriendlinkCtg[] deleteByIds(Integer[] ids) {
		FriendlinkCtg[] beans = new FriendlinkCtg[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}
	
	/** 排序 */
	@Override
	public boolean isUp(FriendlinkCtg bean) {
		if (getPrev(bean) == null) {
			return false;
		}
		return true;
	}

	@Override
	public boolean isDown(FriendlinkCtg bean) {
		if (getNext(bean) == null) {
			return false;
		}
		return true;
	}

	@Override
	public FriendlinkCtg up(Integer id) {
		FriendlinkCtg bean = findById(id);
		Integer oPriority = bean.getPriority();
		FriendlinkCtg beanPre = getDao().getPrev(bean);
		//
		bean.setPriority(beanPre.getPriority());
		beanPre.setPriority(oPriority);
		updateByUpdater(Updater.create(bean));
		updateByUpdater(Updater.create(beanPre));
		return bean;
	}

	@Override
	public FriendlinkCtg down(Integer id) {
		FriendlinkCtg bean = findById(id);
		Integer oPriority = bean.getPriority();
		FriendlinkCtg beanNext = getDao().getNext(bean);
		//
		bean.setPriority(beanNext.getPriority());
		beanNext.setPriority(oPriority);
		updateByUpdater(Updater.create(bean));
		updateByUpdater(Updater.create(beanNext));
		return bean;
	}

	@Override
	public FriendlinkCtg getPrev(FriendlinkCtg bean) {
		return getDao().getPrev(bean);
	}

	@Override
	public FriendlinkCtg getNext(FriendlinkCtg bean) {
		return getDao().getNext(bean);
	}
}
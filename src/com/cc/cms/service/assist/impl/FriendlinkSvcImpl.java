
package com.cc.cms.service.assist.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cc.cms.dao.assist.IFriendlinkDao;
import com.cc.cms.entity.assist.Friendlink;
import com.cc.cms.service.CmsSvcImpl;
import com.cc.cms.service.assist.IFriendlinkCtgSvc;
import com.cc.cms.service.assist.IFriendlinkSvc;
import com.cc.common.orm.Updater;

@Service
@Transactional
public class FriendlinkSvcImpl extends CmsSvcImpl<Friendlink> implements IFriendlinkSvc {

	@Autowired
	private IFriendlinkCtgSvc	cmsFriendlinkCtgMng;

	@Autowired
	public void setDao(IFriendlinkDao dao) {
		super.setDao(dao);
	}

	@Override
	public IFriendlinkDao getDao() {
		return (IFriendlinkDao) super.getDao();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Friendlink> getList(Integer siteId, Integer ctgId, Boolean enabled) {
		List<Friendlink> list = getDao().getList(siteId, ctgId, enabled);
		return list;
	}

	@Override
	@Transactional(readOnly = true)
	public int countByCtgId(Integer ctgId) {
		return getDao().countByCtgId(ctgId);
	}

	@Override
	@Transactional(readOnly = true)
	public Friendlink findById(Integer id) {
		Friendlink entity = getDao().findById(id);
		return entity;
	}

	@Override
	public int updateViews(Integer id) {
		Friendlink link = findById(id);
		if (link != null) {
			link.setViews(link.getViews() + 1);
		}
		return link.getViews();
	}

	@Override
	public Friendlink save(Friendlink bean, Integer ctgId) {
		bean.setCategory(cmsFriendlinkCtgMng.findById(ctgId));
		bean.init();
		bean.setPriority(getDao().getMaxPriority() + 1);
		getDao().save(bean);
		return bean;
	}

	@Override
	public Friendlink update(Friendlink bean, Integer ctgId) {
		Updater<Friendlink> updater = new Updater<Friendlink>(bean);
		bean = (Friendlink) getDao().updateByUpdater(updater);
		if (ctgId != null) {
			bean.setCategory(cmsFriendlinkCtgMng.findById(ctgId));
		}
		bean.blankToNull();
		return bean;
	}

	@Override
	public void updatePriority(Integer[] ids, Integer[] priorities) {
		if (ids == null || priorities == null || ids.length <= 0 || ids.length != priorities.length) {
			return;
		}
		Friendlink link;
		for (int i = 0, len = ids.length; i < len; i++) {
			link = findById(ids[i]);
			link.setPriority(priorities[i]);
		}
	}

	@Override
	public Friendlink deleteById(Integer id) {
		Friendlink bean = getDao().deleteById(id);
		return bean;
	}

	@Override
	public Friendlink[] deleteByIds(Integer[] ids) {
		Friendlink[] beans = new Friendlink[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	/** 排序 */
	@Override
	public boolean isUp(Friendlink bean) {
		if (getPrev(bean) == null) {
			return false;
		}
		return true;
	}

	@Override
	public boolean isDown(Friendlink bean) {
		if (getNext(bean) == null) {
			return false;
		}
		return true;
	}

	@Override
	public Friendlink up(Integer id) {
		Friendlink bean = findById(id);
		Integer oPriority = bean.getPriority();
		Friendlink beanPre = getDao().getPrev(bean);
		//
		bean.setPriority(beanPre.getPriority());
		beanPre.setPriority(oPriority);
		updateByUpdater(Updater.create(bean));
		updateByUpdater(Updater.create(beanPre));
		return bean;
	}

	@Override
	public Friendlink down(Integer id) {
		Friendlink bean = findById(id);
		Integer oPriority = bean.getPriority();
		Friendlink beanNext = getDao().getNext(bean);
		//
		bean.setPriority(beanNext.getPriority());
		beanNext.setPriority(oPriority);
		updateByUpdater(Updater.create(bean));
		updateByUpdater(Updater.create(beanNext));
		return bean;
	}

	@Override
	public Friendlink getPrev(Friendlink bean) {
		return getDao().getPrev(bean);
	}

	@Override
	public Friendlink getNext(Friendlink bean) {
		return getDao().getNext(bean);
	}
}

package com.cc.cms.service.main.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cc.cms.dao.main.IUserSiteDao;
import com.cc.cms.entity.main.Site;
import com.cc.cms.entity.main.User;
import com.cc.cms.entity.main.UserSite;
import com.cc.cms.service.CmsSvcImpl;
import com.cc.cms.service.main.IUserSiteSvc;
import com.cc.cms.service.main.IUserSvc;
import com.cc.cms.service.main.IWebsiteSvc;
import com.cc.common.orm.Updater;

/**
 * @author wangcheng
 */
@Service
@Transactional
public class UserSiteSvcImpl extends CmsSvcImpl<UserSite> implements IUserSiteSvc {

	@Autowired
	public void setDao(IUserSiteDao dao) {
		super.setDao(dao);
	}

	@Override
	public IUserSiteDao getDao() {
		return (IUserSiteDao) super.getDao();
	}

	@Autowired
	private IWebsiteSvc	websiteSvc;
	@Autowired
	private IUserSvc	userSvc;

	@Override
	public UserSite save(Site site, User user, Byte step, Boolean allChannel) {
		UserSite bean = new UserSite();
		bean.setSite(site);
		bean.setUser(user);
		bean.setCheckStep(step);
		bean.setAllChannel(allChannel);
		bean.init();
		getDao().save(bean);
		return bean;
	}

	public UserSite update(UserSite bean) {
		Updater<UserSite> updater = new Updater<UserSite>(bean);
		bean = (UserSite) getDao().updateByUpdater(updater);
		return bean;
	}

	@Override
	public void updateByUser(User user, Integer siteId, Byte step, Boolean allChannel) {
		user = userSvc.findById(user.getId());
		Set<UserSite> uss = user.getUserSites();
		if (siteId == null || step == null || allChannel == null) {
			return;
		}
		// 只更新单站点信息
		for (UserSite us : uss) {
			if (siteId.equals(us.getSite().getId())) {
				us.setCheckStep(step);
				us.setAllChannel(allChannel);
			}
		}
	}

	@Override
	public void updateByUser(User user, Integer[] siteIds, Byte[] steps, Boolean[] allChannels) {
		Set<UserSite> uss = user.getUserSites();
		// 全删
		if (siteIds == null) {
			user.getUserSites().clear();
			for (UserSite us : uss) {
				getDao().delete(us);
			}
			return;
		}
		// 先删除、更新
		Set<UserSite> toDel = new HashSet<UserSite>();
		boolean contains;
		int i;
		for (UserSite us : uss) {
			contains = false;
			for (i = 0; i < siteIds.length; i++) {
				if (siteIds[i].equals(us.getSite().getId())) {
					contains = true;
					break;
				}
			}
			if (contains) {
				us.setCheckStep(steps[i]);
				us.setAllChannel(allChannels[i]);
			} else {
				toDel.add(us);
			}
		}
		delete(toDel, uss);
		// 再增加
		i = 0;
		Set<UserSite> toSave = new HashSet<UserSite>();
		for (Integer sid : siteIds) {
			contains = false;
			for (UserSite us : uss) {
				if (us.getSite().getId().equals(sid)) {
					contains = true;
					break;
				}
			}
			if (!contains) {
				toSave.add(save(websiteSvc.findById(sid), user, steps[i], allChannels[i]));
			}
			i++;
		}
		uss.addAll(toSave);
	}

	private void delete(Collection<UserSite> coll, Set<UserSite> set) {
		if (coll == null) {
			return;
		}
		for (UserSite us : coll) {
			getDao().delete(us);
			set.remove(us);
		}
	}

	@Override
	public int deleteBySiteId(Integer siteId) {
		return getDao().deleteBySiteId(siteId);
	}
}

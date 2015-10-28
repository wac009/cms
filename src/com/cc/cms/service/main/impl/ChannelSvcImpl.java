
package com.cc.cms.service.main.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cc.cms.dao.main.IChannelDao;
import com.cc.cms.entity.main.Channel;
import com.cc.cms.entity.main.Group;
import com.cc.cms.entity.main.User;
import com.cc.cms.entity.main.UserSite;
import com.cc.cms.service.CmsSvcImpl;
import com.cc.cms.service.main.IChannelDeleteChecker;
import com.cc.cms.service.main.IChannelExtSvc;
import com.cc.cms.service.main.IChannelSvc;
import com.cc.cms.service.main.IChannelTxtSvc;
import com.cc.cms.service.main.IGroupSvc;
import com.cc.cms.service.main.IModelSvc;
import com.cc.cms.service.main.IUserSvc;
import com.cc.common.orm.Updater;
import com.cc.common.page.Pagination;

/** @author wangcheng */
@Service
@Transactional
public class ChannelSvcImpl extends CmsSvcImpl<Channel> implements IChannelSvc {

	@Autowired
	public void setDao(IChannelDao dao) {
		super.setDao(dao);
	}

	@Override
	public IChannelDao getDao() {
		return (IChannelDao) super.getDao();
	}

	@Autowired
	public IUserSvc						userSvc;
	@Autowired
	private IGroupSvc					groupSvc;
	@Autowired
	private IModelSvc					modelSvc;
	@Autowired
	private IChannelExtSvc				channelExtSvc;
	@Autowired
	private IChannelTxtSvc				channelTxtSvc;
	private List<IChannelDeleteChecker>	deleteCheckerList;

	@Override
	public List<Channel> findAll(Integer webId) {
		return getDao().findAll(webId);
	}

	@Override
	public List<Channel> getTopList(Integer siteId, boolean hasContentOnly) {
		return getDao().getTopList(siteId, hasContentOnly, false, false);
	}

	@Override
	public List<Channel> getTopListByRigth(Integer userId, Integer siteId, boolean hasContentOnly) {
		User user = userSvc.findById(userId);
		if (user.isSuper()) {
			return getDao().getTopListByRigth(null, siteId, hasContentOnly);
		}
		UserSite us = user.getUserSite(siteId);
		if (us != null && us.getAllChannel()) {
			return getTopList(siteId, hasContentOnly);
		} else {
			return getDao().getTopListByRigth(userId, siteId, hasContentOnly);
		}
	}

	@Override
	public List<Channel> getTopListForTag(Integer siteId, boolean hasContentOnly) {
		return getDao().getTopList(siteId, hasContentOnly, true, true);
	}

	@Override
	@Transactional(readOnly = true)
	public Pagination getTopPageForTag(Integer siteId, boolean hasContentOnly, int pageNo, int pageSize) {
		return getDao().getTopPage(siteId, hasContentOnly, false, false, pageNo, pageSize);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Channel> getChildList(Integer parentId, boolean hasContentOnly) {
		return getDao().getChildList(parentId, hasContentOnly, false, false);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Channel> getChildListByRight(Integer userId, Integer siteId, Integer parentId, boolean hasContentOnly) {
		User user = userSvc.findById(userId);
		UserSite us = user.getUserSite(siteId);
		if (us.getAllChannel()) {
			return getChildList(parentId, hasContentOnly);
		} else {
			return getDao().getChildListByRight(userId, parentId, hasContentOnly);
		}
	}

	@Override
	public List<Channel> getChildListForTag(Integer parentId, boolean hasContentOnly) {
		return getDao().getChildList(parentId, hasContentOnly, true, true);
	}

	@Override
	@Transactional(readOnly = true)
	public Pagination getChildPageForTag(Integer parentId, boolean hasContentOnly, int pageNo, int pageSize) {
		return getDao().getChildPage(parentId, hasContentOnly, true, true, pageNo, pageSize);
	}

	@Transactional(readOnly = true)
	public Channel findById(Integer id) {
		Channel entity = getDao().get(id);
		return entity;
	}

	@Override
	@Transactional(readOnly = true)
	public Channel findByPath(String path, Integer siteId) {
		return getDao().findByPath(path, siteId, false);
	}

	@Override
	@Transactional(readOnly = true)
	public Channel findByPathForTag(String path, Integer siteId) {
		return getDao().findByPath(path, siteId, true);
	}

	@Override
	public Channel save(Channel bean, List<Group> viewGroups, List<Group> contriGroups, List<User> users) {
		bean.setModel(modelSvc.findById(bean.getModel().getId()));
		bean.getExt().setHasContent(bean.getModel().getHasContent());
		bean.init();
		bean.setPriority(getDao().getMaxPriority() + 1);
		getDao().save(bean);
		channelExtSvc.save(bean.getExt(), bean);
		channelTxtSvc.save(bean.getChannelTxt(), bean);
		if (users != null && users.size() > 0) {
			for (User user : users) {
				bean.addToUsers(user);
			}
		}
		if (viewGroups != null && viewGroups.size() > 0) {
			for (Group group : viewGroups) {
				bean.addToViewGroups(groupSvc.findById(group.getId()));
			}
		}
		if (contriGroups != null && contriGroups.size() > 0) {
			for (Group group : contriGroups) {
				bean.addToContriGroups(groupSvc.findById(group.getId()));
			}
		}
		return bean;
	}

	@Override
	public Channel update(Channel bean, List<Group> viewGroups, List<Group> contriGroups, List<User> users, Map<String, String> attr) {
		// 更新主表
		updateDefault(bean);
		// 更新扩展表
		channelExtSvc.updateDefault(bean.getExt());
		// 更新文本表
		if (bean.getChannelTxt() != null && bean.getChannelTxt().getId() != null) {
			channelTxtSvc.updateDefault(bean.getChannelTxt());
		}else {
			channelTxtSvc.save(bean.getChannelTxt(), bean);
		}
		// 更新属性表
		Map<String, String> attrOrig = bean.getAttr();
		if (attrOrig != null) {
			attrOrig.clear();
			attrOrig.putAll(attr);
		}
		bean = findById(bean.getId());
		// 更新浏览会员组关联
		if (bean.getViewGroups() != null) {
			for (Group g : bean.getViewGroups()) {
				g.getViewChannels().remove(bean);
			}
			bean.getViewGroups().clear();
		}
		if (viewGroups != null && viewGroups.size() > 0) {
			for (Group group : viewGroups) {
				bean.addToViewGroups(groupSvc.findById(group.getId()));
			}
		}
		// 更新投稿会员组关联
		if (bean.getContriGroups() != null) {
			for (Group g : bean.getContriGroups()) {
				g.getContriChannels().remove(bean);
			}
			bean.getContriGroups().clear();
		}
		if (contriGroups != null && contriGroups.size() > 0) {
			for (Group group : contriGroups) {
				bean.addToContriGroups(groupSvc.findById(group.getId()));
			}
		}
		// 更新管理员关联
		if (bean.getUsers() != null) {
			for (User u : bean.getUsers()) {
				u.getChannels().remove(bean);
			}
			bean.getUsers().clear();
		}
		if (users != null && users.size() > 0) {
			for (User user : users) {
				bean.addToUsers(user);
			}
		}
		return bean;
	}

	public Channel deleteById(Integer id) {
		Channel entity = getDao().get(id);
		for (Group group : entity.getViewGroups()) {
			group.getViewChannels().remove(entity);
		}
		for (Group group : entity.getContriGroups()) {
			group.getContriChannels().remove(entity);
		}
		entity = getDao().deleteById(id);
		return entity;
	}

	public Channel[] deleteByIds(Integer[] ids) {
		Channel[] beans = new Channel[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	public String checkDelete(Integer id) {
		String msg = null;
		for (IChannelDeleteChecker checker : deleteCheckerList) {
			msg = checker.checkForChannelDelete(id);
			if (msg != null) {
				return msg;
			}
		}
		return msg;
	}

	@Override
	public boolean isChild(Integer pid, Integer cid) {
		return getDao().isChild(pid, cid);
	}

	/** 排序 */
	@Override
	public boolean isUp(Channel bean) {
		if (getPrev(bean) == null) {
			return false;
		}
		return true;
	}

	@Override
	public boolean isDown(Channel bean) {
		if (getNext(bean) == null) {
			return false;
		}
		return true;
	}

	@Override
	public Channel up(Integer id) {
		Channel bean = findById(id);
		Integer oPriority = bean.getPriority();
		Channel beanPre = getDao().getPrev(bean);
		//
		bean.setPriority(beanPre.getPriority());
		beanPre.setPriority(oPriority);
		updateByUpdater(Updater.create(bean));
		updateByUpdater(Updater.create(beanPre));
		return bean;
	}

	@Override
	public Channel down(Integer id) {
		Channel bean = findById(id);
		Integer oPriority = bean.getPriority();
		Channel beanNext = getDao().getNext(bean);
		//
		bean.setPriority(beanNext.getPriority());
		beanNext.setPriority(oPriority);
		updateByUpdater(Updater.create(bean));
		updateByUpdater(Updater.create(beanNext));
		return bean;
	}

	@Override
	public Channel getPrev(Channel bean) {
		return getDao().getPrev(bean);
	}

	@Override
	public Channel getNext(Channel bean) {
		return getDao().getNext(bean);
	}

	@Override
	public List<Channel> getListForUpdate(Integer chnId) {
		return getDao().getChnlsAndExclude(chnId);
	}
}

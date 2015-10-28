
package com.cc.cms.service.main.impl;

import static com.cc.cms.statistic.Statistic.THISMONTH;
import static com.cc.cms.statistic.Statistic.THISWEEK;
import static com.cc.cms.statistic.Statistic.THISYEAR;
import static com.cc.cms.statistic.Statistic.TODAY;
import static com.cc.cms.statistic.Statistic.YESTERDAY;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cc.cms.dao.main.IUserDao;
import com.cc.cms.entity.main.Channel;
import com.cc.cms.entity.main.Group;
import com.cc.cms.entity.main.Site;
import com.cc.cms.entity.main.User;
import com.cc.cms.entity.main.UserExt;
import com.cc.cms.service.CmsSvcImpl;
import com.cc.cms.service.main.IChannelSvc;
import com.cc.cms.service.main.IContentSvc;
import com.cc.cms.service.main.IGroupSvc;
import com.cc.cms.service.main.IPermissionSvc;
import com.cc.cms.service.main.IRoleSvc;
import com.cc.cms.service.main.IUserExtSvc;
import com.cc.cms.service.main.IUserSiteSvc;
import com.cc.cms.service.main.IUserSvc;
import com.cc.cms.service.main.IWebsiteSvc;
import com.cc.cms.statistic.Statistic;
import com.cc.common.email.EmailSender;
import com.cc.common.email.MessageTemplate;
import com.cc.common.orm.Updater;
import com.cc.common.util.TimeRange;
import com.cc.core.entity.UnifiedUser;
import com.cc.core.service.IUnifiedUserSvc;

/** @author wangcheng */
@Service
@Transactional
public class UserSvcImpl extends CmsSvcImpl<User> implements IUserSvc {

	@Autowired
	public void setDao(IUserDao dao) {
		super.setDao(dao);
	}

	@Override
	public IUserDao getDao() {
		return (IUserDao) super.getDao();
	}

	@Autowired
	private IWebsiteSvc		websiteSvc;
	@Autowired
	private IRoleSvc		roleSvc;
	@Autowired
	private IPermissionSvc	permissionSvc;
	@Autowired
	private IUnifiedUserSvc	unifiedUserSvc;
	@Autowired
	private IUserExtSvc		userExtSvc;
	@Autowired
	private IUserSiteSvc	userSiteSvc;
	@Autowired
	private IGroupSvc		groupSvc;
	@Autowired
	private IContentSvc		contentSvc;
	@Autowired
	private IChannelSvc		channelSvc;
	@Autowired
	@Qualifier("adminRights")
	private Cache			adminRightsCache;

	@Override
	@Transactional(readOnly = true)
	public List<User> getList(String username, String email, Integer siteId, Integer groupId, Boolean disabled, Boolean admin, Integer rank) {
		return getDao().getList(username, email, siteId, groupId, disabled, admin, rank);
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> getAdminList(Integer siteId, Boolean allChannel, Boolean disabled, Integer rank) {
		return getDao().getAdminList(siteId, allChannel, disabled, rank);
	}

	@Transactional(readOnly = true)
	public User findById(Integer id) {
		User entity = getDao().get(id);
		return entity;
	}

	@Override
	@Transactional(readOnly = true)
	public User findByUsername(String username) {
		User entity = getDao().findByUsername(username);
		return entity;
	}

	@Override
	public User registerMember(String username, String email, String password, String ip, Integer groupId, UserExt userExt) {
		return registerMember(username, email, password, ip, groupId, userExt, true, null, null);
	}

	@Override
	public User registerMember(String username, String email, String password, String ip, Integer groupId, UserExt userExt, Boolean activation,
			EmailSender sender, MessageTemplate msgTpl) {
		UnifiedUser unifiedUser = unifiedUserSvc.save(username, email, password, ip, activation, sender, msgTpl);
		User user = new User();
		user.forMember(unifiedUser);
		Group group = null;
		if (groupId != null) {
			group = groupSvc.findById(groupId);
		} else {
			group = groupSvc.getRegDef();
		}
		if (group == null) {
			throw new RuntimeException("register default member group not found!");
		}
		user.setGroup(group);
		user.init();
		getDao().save(user);
		userExtSvc.save(userExt, user);
		return user;
	}

	@Override
	public void updateLoginInfo(Integer userId, String ip) {
		Date now = new Timestamp(System.currentTimeMillis());
		User user = findById(userId);
		if (user != null) {
			if (user.getLoginCount() == null || user.getLoginCount() < 0) {
				user.setLoginCount(0);
			}
			user.setLoginCount(user.getLoginCount() + 1);
			user.setLastLoginTime(user.getCurrentLoginTime());
			user.setLastLoginIp(user.getCurrentLoginIp());
			user.setCurrentLoginTime(now);
			user.setCurrentLoginIp(ip);
		}
	}

	@Override
	public void updateUploadSize(Integer userId, Integer size) {
		User user = findById(userId);
		user.setUploadTotal(user.getUploadTotal() + size);
		if (user.getUploadDate() != null) {
			if (User.isToday(user.getUploadDate())) {
				size += user.getUploadSize();
			}
		}
		user.setUploadDate(new java.sql.Date(System.currentTimeMillis()));
		user.setUploadSize(size);
	}

	@Override
	public boolean isPasswordValid(Integer id, String password) {
		return unifiedUserSvc.isPasswordValid(id, password);
	}

	@Override
	public void updatePwdEmail(Integer id, String password, String email) {
		User user = findById(id);
		if (!StringUtils.isBlank(email)) {
			user.setEmail(email);
		} else {
			user.setEmail(null);
		}
		unifiedUserSvc.update(id, password, email);
	}

	@Override
	public User saveAdmin(User user, String password, String ip, Integer siteId, Integer groupId, Byte step, Boolean allChannels) {
		UnifiedUser unifiedUser = unifiedUserSvc.save(user.getUsername(), user.getEmail(), password, ip);
		user.forAdmin(unifiedUser);
		Group group = null;
		if (groupId != null) {
			group = groupSvc.findById(groupId);
		} else {
			group = groupSvc.getRegDef();
		}
		if (group == null) {
			throw new RuntimeException("register default member group not setted!");
		}
		user.setGroup(group);
		user.init();
		getDao().save(user);
		if (user.getExt() == null) {
			user.setExt(new UserExt());
		}
		userExtSvc.save(user.getExt(), user);
		if (siteId != null) {
			Site site;
			site = websiteSvc.findById(siteId);
			userSiteSvc.save(site, user, step, allChannels);
		}
		return user;
	}

	@Override
	public void addSiteToUser(User user, Site site, Byte checkStep) {
		userSiteSvc.save(site, user, checkStep, true);
	}

	@Override
	public User updateAdmin(User user, String password, Integer siteId, Integer groupId, Byte step, Boolean allChannels) {
		user = updateAdmin(user, password, groupId, allChannels);
		// 更新所属站点
		userSiteSvc.updateByUser(user, siteId, step, allChannels);
		return user;
	}

	private User updateAdmin(User user, String password, Integer groupId, Boolean allChannels) {
		// Updater<User> updater = new Updater<User>(user);
		// updater.include("email");
		// user = (User) getDao().updateByUpdater(updater);
		user.setGroup(groupSvc.findById(groupId));
		updateDefault(user);
		userExtSvc.update(user.getExt(), user);
		// 更新角色
		Integer[] roleIds = user.getRoleIds();
		user.getRoles().clear();
		if (roleIds != null) {
			for (Integer rid : roleIds) {
				user.addToRoles(roleSvc.findById(rid));
			}
		}
		// 更新栏目权限
		Integer[] channelIds = user.getChannelIds();
		Set<Channel> channels = user.getChannels();
		// 清除
		if (channels != null) {
			for (Channel channel : channels) {
				channel = channelSvc.findById(channel.getId());
				channel.getUsers().remove(user);
			}
			user.getChannels().clear();
		}
		// 添加
		if (!allChannels && channelIds != null) {
			Channel channel;
			for (Integer cid : channelIds) {
				channel = channelSvc.findById(cid);
				channel.addToUsers(user);
			}
		}
		unifiedUserSvc.update(user.getId(), password, user.getEmail());
		return user;
	}

	@Override
	public User updateMember(User user, String password) {
		// User entity = findById(id);
		// if (!StringUtils.isBlank(email)) {
		// entity.setEmail(email);
		// }
		// if (isDisabled != null) {
		// entity.setDisabled(isDisabled);
		// }
		// if (groupId != null) {
		// entity.setGroup(groupSvc.findById(groupId));
		// }
		// userExtSvc.update(ext, entity);
		// unifiedUserSvc.update(id, password, email);
		// return entity;
		return null;
	}

	@Override
	public User updateUserConllection(User user, Integer cid, Integer operate) {
		Updater<User> updater = new Updater<User>(user);
		user = (User) getDao().updateByUpdater(updater);
		if (operate.equals(1)) {
			user.addToCollection(contentSvc.findById(cid));
		}// 取消收藏
		else if (operate.equals(0)) {
			user.delFromCollection(contentSvc.findById(cid));
		}
		return user;
	}

	@Override
	public User deleteById(Integer id) {
		unifiedUserSvc.deleteById(id);
		User bean = getDao().deleteById(id);
		// 删除收藏信息
		bean.clearCollection();
		return bean;
	}

	@Override
	public User[] deleteByIds(Integer[] ids) {
		User[] beans = new User[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	@Override
	public boolean usernameNotExist(String username) {
		return getDao().countByUsername(username) <= 0;
	}

	@Override
	public boolean usernameNotExistInMember(String username) {
		return getDao().countMemberByUsername(username) <= 0;
	}

	@Override
	public boolean emailNotExist(String email) {
		return getDao().countByEmail(email) <= 0;
	}

	// 根据ID获取管理员的所有权限
	@Override
	@SuppressWarnings("unchecked")
	public Set<String> getRights(Integer uid) {
		// 从缓存中获取，若缓存空就查询数据库并加入缓存
		Element e = adminRightsCache.get(uid);
		if (e != null) {
			return (Set<String>) e.getObjectValue();
		} else {
			Set<String> rights = permissionSvc.getItems(uid);
			if (!rights.isEmpty()) {
				adminRightsCache.put(new Element(uid, rights));
			} else {
				rights = null;
			}
			return rights;
		}
	}

	@Override
	public void loadRightsToCache(Integer uid) {
		adminRightsCache.remove(uid);
		Set<String> rights = permissionSvc.getItems(uid);
		if (!rights.isEmpty()) {
			adminRightsCache.put(new Element(uid, rights));
		}
	}

	@Override
	public void handleRightsChange(Integer uid) {
		loadRightsToCache(uid);
	}

	@Override
	public Map<String, List<Statistic>> getWelcomeData(Integer siteId) {
		Map<String, List<Statistic>> map = new HashMap<String, List<Statistic>>();
		map.put("today", getListByTimeRange(siteId, getTimeRange(TODAY)));
		map.put("yesterday", getListByTimeRange(siteId, getTimeRange(YESTERDAY)));
		map.put("thisweek", getListByTimeRange(siteId, getTimeRange(THISWEEK)));
		map.put("thismonth", getListByTimeRange(siteId, getTimeRange(THISMONTH)));
		map.put("thisyear", getListByTimeRange(siteId, getTimeRange(THISYEAR)));
		map.put("total", getListByTimeRange(siteId, getTimeRange(-1)));
		return map;
	}

	private List<Statistic> getListByTimeRange(Integer siteId, TimeRange timeRange) {
		List<Statistic> list = new ArrayList<Statistic>();
		list.add(new Statistic(getCountByTimeRange(siteId, timeRange)));
		return list;
	}

	private long getCountByTimeRange(Integer siteId, TimeRange timeRange) {
		return getDao().getCountByTimeRange(siteId, timeRange);
	}

	@Override
	public boolean deleteBySite(Integer id) {
		try {
			List<User> users = getAdminList(id, null, null, null);
			for (Iterator<User> iterator = users.iterator(); iterator.hasNext();) {
				delete(iterator.next());
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}
}


package com.cc.cms.entity.main;

import java.util.*;

import com.cc.cms.entity.main.base.*;
import com.cc.core.entity.*;

public class User extends BaseUser {

	private static final long	serialVersionUID	= 1L;
	public static final String	USER_KEY			= "_user_key";
	public static final String	ADMIN_KEY			= "_admin_key";
	public static final String	ADMIN_INFO			= "_admin_info";
	public static final String	RIGHTS_KEY			= "_rights_key";
	public static final String	QUICKMENUS			= "quickMenus";

	public Byte getCheckStep(Integer siteId) {
		UserSite us = getUserSite(siteId);
		if (us != null) {
			return getUserSite(siteId).getCheckStep();
		} else {
			return null;
		}
	}

	public String getRealname() {
		UserExt ext = getUserExt();
		if (ext != null) {
			return ext.getRealname();
		} else {
			return null;
		}
	}

	public Integer getGender() {
		UserExt ext = getUserExt();
		if (ext != null) {
			return ext.getGender();
		} else {
			return null;
		}
	}

	public Date getBirthday() {
		UserExt ext = getUserExt();
		if (ext != null) {
			return ext.getBirthday();
		} else {
			return null;
		}
	}

	public String getIntro() {
		UserExt ext = getUserExt();
		if (ext != null) {
			return ext.getIntro();
		} else {
			return null;
		}
	}

	public String getAddress() {
		UserExt ext = getUserExt();
		if (ext != null) {
			return ext.getAddress();
		} else {
			return null;
		}
	}

	public String getZip() {
		UserExt ext = getUserExt();
		if (ext != null) {
			return ext.getZip();
		} else {
			return null;
		}
	}

	public String getQq() {
		UserExt ext = getUserExt();
		if (ext != null) {
			return ext.getQq();
		} else {
			return null;
		}
	}

	public String getMsn() {
		UserExt ext = getUserExt();
		if (ext != null) {
			return ext.getMsn();
		} else {
			return null;
		}
	}

	public String getTel() {
		UserExt ext = getUserExt();
		if (ext != null) {
			return ext.getTel();
		} else {
			return null;
		}
	}

	public String getFax() {
		UserExt ext = getUserExt();
		if (ext != null) {
			return ext.getFax();
		} else {
			return null;
		}
	}

	public String getMobile() {
		UserExt ext = getUserExt();
		if (ext != null) {
			return ext.getMobile();
		} else {
			return null;
		}
	}

	public String getUserImg() {
		UserExt ext = getUserExt();
		if (ext != null) {
			return ext.getUserImg();
		} else {
			return null;
		}
	}

	public String getSignature() {
		UserExt ext = getUserExt();
		if (ext != null) {
			return ext.getSignature();
		} else {
			return null;
		}
	}

	public UserExt getUserExt() {
		UserExt ext = getExt();
		if (ext != null) {
			return ext;
		} else {
			return null;
		}
	}

	public UserSite getUserSite(Integer siteId) {
		Set<UserSite> set = getUserSites();
		for (UserSite us : set) {
			if (us.getSite().getId().equals(siteId)) {
				return us;
			}
		}
		return null;
	}

	public Set<Channel> getChannels(Integer siteId) {
		Set<Channel> set = getChannels();
		Set<Channel> results = new HashSet<Channel>();
		for (Channel c : set) {
			if (c.getSite().getId().equals(siteId)) {
				results.add(c);
			}
		}
		return results;
	}

	public Integer[] getChannelIds() {
		Set<Channel> channels = getChannels();
		return Channel.fetchIds(channels);
	}

	public Set<Integer> getChannelIds(Integer siteId) {
		Set<Channel> channels = getChannels();
		Set<Integer> ids = new HashSet<Integer>();
		for (Channel c : channels) {
			if (c.getSite().getId().equals(siteId)) {
				ids.add(c.getId());
			}
		}
		return ids;
	}

	public Integer[] getRoleIds() {
		Set<Role> roles = getRoles();
		return Role.fetchIds(roles);
	}

	public Integer[] getSiteIds() {
		Set<Site> sites = getSites();
		return Site.fetchIds(sites);
	}

	public void addToRoles(Role role) {
		if (role == null) {
			return;
		}
		Set<Role> set = getRoles();
		if (set == null) {
			set = new HashSet<Role>();
			setRoles(set);
		}
		set.add(role);
	}

	public void addToChannels(Channel channel) {
		if (channel == null) {
			return;
		}
		Set<Channel> set = getChannels();
		if (set == null) {
			set = new HashSet<Channel>();
			setChannels(set);
		}
		set.add(channel);
	}

	public void addToCollection(Content content) {
		if (content == null) {
			return;
		}
		Set<Content> set = getCollectContents();
		if (set == null) {
			set = new HashSet<Content>();
			setCollectContents(set);
		}
		set.add(content);
	}

	public void delFromCollection(Content content) {
		if (content == null) {
			return;
		}
		Set<Content> set = getCollectContents();
		if (set == null) {
			return;
		} else {
			set.remove(content);
		}
	}

	public void clearCollection() {
		getCollectContents().clear();
	}

	public Set<Site> getSites() {
		Set<UserSite> userSites = getUserSites();
		Set<Site> sites = new HashSet<Site>(userSites.size());
		for (UserSite us : userSites) {
			sites.add(us.getSite());
		}
		return sites;
	}

	public boolean isSuper() {
		Set<Role> roles = getRoles();
		if (roles == null) {
			return false;
		}
		for (Role role : getRoles()) {
			if (role.getSuper()) {
				return true;
			}
		}
		return false;
	}

	public Set<Permission> getPermissions() {
		Set<Role> roles = getRoles();
		if (roles == null) {
			return null;
		}
		Set<Permission> allPerms = new HashSet<Permission>();
		for (Role role : getRoles()) {
			for (Permission permission : role.getPermissions()) {
				allPerms.add(permission);
			}
		}
		return allPerms;
	}

	/**
	 * 是否允许上传，根据每日限额
	 * 
	 * @param size
	 * @return
	 */
	public boolean isAllowPerDay(int size) {
		int allowPerDay = getGroup().getAllowPerDay();
		if (allowPerDay == 0) {
			return true;
		}
		if (getUploadDate() != null) {
			if (isToday(getUploadDate())) {
				size += getUploadSize();
			}
		}
		return allowPerDay >= size;
	}

	/**
	 * 是否允许上传，根据文件大小
	 * 
	 * @param size
	 *            文件大小，单位KB
	 * @return
	 */
	public boolean isAllowMaxFile(int size) {
		int allowPerFile = getGroup().getAllowMaxFile();
		if (allowPerFile == 0) {
			return true;
		} else {
			return allowPerFile >= size;
		}
	}

	/**
	 * 是否允许上传后缀
	 * 
	 * @param ext
	 * @return
	 */
	public boolean isAllowSuffix(String ext) {
		return getGroup().isAllowSuffix(ext);
	}

	public void forMember(UnifiedUser u) {
		forUser(u);
		setAdmin(false);
		setRank(0);
		setViewonlyAdmin(false);
		setSelfAdmin(false);
	}

	public void forAdmin(UnifiedUser u) {
		forUser(u);
	}

	public void forUser(UnifiedUser u) {
		setId(u.getId());
		setUsername(u.getUsername());
		setEmail(u.getEmail());
		setRegisterIp(u.getRegisterIp());
		setRegisterTime(u.getRegisterTime());
		setLastLoginIp(u.getLastLoginIp());
		setLastLoginTime(u.getLastLoginTime());
		setCurrentLoginIp(u.getCurrentLoginIp());
		setCurrentLoginTime(u.getCurrentLoginTime());
		setLoginCount(0);
	}

	public void init() {
		if (getUploadTotal() == null) {
			setUploadTotal(0L);
		}
		if (getUploadSize() == null) {
			setUploadSize(0);
		}
		if (getUploadDate() == null) {
			setUploadDate(new java.sql.Date(System.currentTimeMillis()));
		}
		if (getAdmin() == null) {
			setAdmin(false);
		}
		if (getRank() == null) {
			setRank(0);
		}
		if (getViewonlyAdmin() == null) {
			setViewonlyAdmin(false);
		}
		if (getSelfAdmin() == null) {
			setSelfAdmin(false);
		}
		if (getDelete() == null) {
			setDelete(false);
		}
		if (getDisabled() == null) {
			setDisabled(false);
		}
	}

	public static Integer[] fetchIds(Collection<User> users) {
		if (users == null) {
			return null;
		}
		Integer[] ids = new Integer[users.size()];
		int i = 0;
		for (User u : users) {
			ids[i++] = u.getId();
		}
		return ids;
	}

	/** 用于排列顺序。此处优先级为0，则按ID升序排。 */
	public Number getPriority() {
		return 0;
	}

	/**
	 * 是否是今天。根据System.currentTimeMillis() / 1000 / 60 / 60 / 24计算。
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isToday(Date date) {
		long day = date.getTime() / 1000 / 60 / 60 / 24;
		long currentDay = System.currentTimeMillis() / 1000 / 60 / 60 / 24;
		return day == currentDay;
	}

	/* [CONSTRUCTOR MARKER BEGIN] */
	public User() {
		super();
	}

	/** Constructor for primary key */
	public User(java.lang.Integer id) {
		super(id);
	}

	/** Constructor for required fields */
	public User(java.lang.Integer id, com.cc.cms.entity.main.Group group, java.lang.String username, java.util.Date registerTime, java.lang.String registerIp,
			java.lang.Integer loginCount, java.lang.Integer rank, java.lang.Long uploadTotal, java.lang.Integer uploadSize, java.lang.Boolean admin,
			java.lang.Boolean viewonlyAdmin, java.lang.Boolean selfAdmin, java.lang.Boolean disabled) {
		super(id, group, username, registerTime, registerIp, loginCount, rank, uploadTotal, uploadSize, admin, viewonlyAdmin, selfAdmin, disabled);
	}
	/* [CONSTRUCTOR MARKER END] */
}
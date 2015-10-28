
package com.cc.cms.entity.main.base;

import java.io.Serializable;

/**
 * This is an object that contains data related to the cms_user table. Do not modify this class
 * because it will be overwritten if the configuration file related to this class is modified.
 * 
 * @hibernate.class table="cms_user"
 */
public abstract class BaseUser implements Serializable {

	private static final long	serialVersionUID		= 7417896538104019446L;
	public static String		REF						= "User";
	public static String		PROP_REGISTER_TIME		= "registerTime";
	public static String		PROP_LOGIN_COUNT		= "loginCount";
	public static String		PROP_SELF_ADMIN			= "selfAdmin";
	public static String		PROP_UPLOAD_TOTAL		= "uploadTotal";
	public static String		PROP_LAST_LOGIN_IP		= "lastLoginIp";
	public static String		PROP_DISABLED			= "disabled";
	public static String		PROP_LAST_LOGIN_TIME	= "lastLoginTime";
	public static String		PROP_UPLOAD_DATE		= "uploadDate";
	public static String		PROP_GROUP				= "group";
	public static String		PROP_EMAIL				= "email";
	public static String		PROP_UPLOAD_SIZE		= "uploadSize";
	public static String		PROP_RANK				= "rank";
	public static String		PROP_VIEWONLY_ADMIN		= "viewonlyAdmin";
	public static String		PROP_ADMIN				= "admin";
	public static String		PROP_ID					= "id";
	public static String		PROP_REGISTER_IP		= "registerIp";
	public static String		PROP_USERNAME			= "username";
	public static String		PROP_USER_NAME			= "userName";

	// constructors
	public BaseUser() {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseUser(java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseUser(java.lang.Integer id, com.cc.cms.entity.main.Group group, java.lang.String username, java.util.Date registerTime,
			java.lang.String registerIp, java.lang.Integer loginCount, java.lang.Integer rank, java.lang.Long uploadTotal, java.lang.Integer uploadSize,
			java.lang.Boolean admin, java.lang.Boolean viewonlyAdmin, java.lang.Boolean selfAdmin, java.lang.Boolean disabled) {
		this.setId(id);
		this.setGroup(group);
		this.setUsername(username);
		this.setRegisterTime(registerTime);
		this.setRegisterIp(registerIp);
		this.setLoginCount(loginCount);
		this.setRank(rank);
		this.setUploadTotal(uploadTotal);
		this.setUploadSize(uploadSize);
		this.setAdmin(admin);
		this.setViewonlyAdmin(viewonlyAdmin);
		this.setSelfAdmin(selfAdmin);
		this.setDisabled(disabled);
		initialize();
	}

	protected void initialize() {}

	private int															hashCode	= Integer.MIN_VALUE;
	// primary key
	private java.lang.Integer											id;
	// fields
	private java.lang.String											username;
	private java.lang.String											email;
	private java.util.Date												registerTime;
	private java.lang.String											registerIp;
	private java.util.Date												lastLoginTime;
	private java.lang.String											lastLoginIp;
	private java.util.Date												currentLoginTime;
	private java.lang.String											currentLoginIp;
	private java.lang.Integer											loginCount;
	private java.lang.Integer											rank;
	private java.lang.Long												uploadTotal;
	private java.lang.Integer											uploadSize;
	private java.util.Date												uploadDate;
	private java.lang.Boolean											admin;
	private java.lang.Boolean											viewonlyAdmin;
	private java.lang.Boolean											selfAdmin;
	private java.lang.Boolean											disabled;
	private java.lang.Boolean											delete;
	// many to one
	private com.cc.cms.entity.main.Group								group;
	// one to one
	private com.cc.cms.entity.main.UserExt								ext;
	// collections
	private java.util.Set<com.cc.cms.entity.main.UserSite>				userSites;
	private java.util.Set<com.cc.cms.entity.main.Role>					roles;
	private java.util.Set<com.cc.cms.entity.main.Channel>				channels;
	private java.util.Set<com.cc.cms.entity.main.Content>				collectContents;
	private java.util.Set<com.cc.cms.entity.assist.Message>			sendMessages;
	private java.util.Set<com.cc.cms.entity.assist.Message>			receivMessages;
	private java.util.Set<com.cc.cms.entity.assist.ReceiverMessage>	sendReceiverMessages;
	private java.util.Set<com.cc.cms.entity.assist.ReceiverMessage>	receivReceiverMessages;

	/**
	 * Return the unique identifier of this class
	 * 
	 * @hibernate.id generator-class="assigned" column="user_id"
	 */
	public java.lang.Integer getId() {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * 
	 * @param id
	 *            the new ID
	 */
	public void setId(java.lang.Integer id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}

	/**
	 * Return the value associated with the column: username
	 */
	public java.lang.String getUsername() {
		return username;
	}

	/**
	 * Set the value related to the column: username
	 * 
	 * @param username
	 *            the username value
	 */
	public void setUsername(java.lang.String username) {
		this.username = username;
	}

	/**
	 * Return the value associated with the column: email
	 */
	public java.lang.String getEmail() {
		return email;
	}

	/**
	 * Set the value related to the column: email
	 * 
	 * @param email
	 *            the email value
	 */
	public void setEmail(java.lang.String email) {
		this.email = email;
	}

	/**
	 * Return the value associated with the column: register_time
	 */
	public java.util.Date getRegisterTime() {
		return registerTime;
	}

	/**
	 * Set the value related to the column: register_time
	 * 
	 * @param registerTime
	 *            the register_time value
	 */
	public void setRegisterTime(java.util.Date registerTime) {
		this.registerTime = registerTime;
	}

	/**
	 * Return the value associated with the column: register_ip
	 */
	public java.lang.String getRegisterIp() {
		return registerIp;
	}

	/**
	 * Set the value related to the column: register_ip
	 * 
	 * @param registerIp
	 *            the register_ip value
	 */
	public void setRegisterIp(java.lang.String registerIp) {
		this.registerIp = registerIp;
	}

	/**
	 * Return the value associated with the column: last_login_time
	 */
	public java.util.Date getLastLoginTime() {
		return lastLoginTime;
	}

	/**
	 * Set the value related to the column: last_login_time
	 * 
	 * @param lastLoginTime
	 *            the last_login_time value
	 */
	public void setLastLoginTime(java.util.Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	/**
	 * Return the value associated with the column: last_login_ip
	 */
	public java.lang.String getLastLoginIp() {
		return lastLoginIp;
	}

	/**
	 * Set the value related to the column: last_login_ip
	 * 
	 * @param lastLoginIp
	 *            the last_login_ip value
	 */
	public void setLastLoginIp(java.lang.String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	/**
	 * Return the value associated with the column: login_count
	 */
	public java.lang.Integer getLoginCount() {
		return loginCount;
	}

	/**
	 * Set the value related to the column: login_count
	 * 
	 * @param loginCount
	 *            the login_count value
	 */
	public void setLoginCount(java.lang.Integer loginCount) {
		this.loginCount = loginCount;
	}

	/**
	 * Return the value associated with the column: rank
	 */
	public java.lang.Integer getRank() {
		return rank;
	}

	/**
	 * Set the value related to the column: rank
	 * 
	 * @param rank
	 *            the rank value
	 */
	public void setRank(java.lang.Integer rank) {
		this.rank = rank;
	}

	/**
	 * Return the value associated with the column: upload_total
	 */
	public java.lang.Long getUploadTotal() {
		return uploadTotal;
	}

	/**
	 * Set the value related to the column: upload_total
	 * 
	 * @param uploadTotal
	 *            the upload_total value
	 */
	public void setUploadTotal(java.lang.Long uploadTotal) {
		this.uploadTotal = uploadTotal;
	}

	/**
	 * Return the value associated with the column: upload_size
	 */
	public java.lang.Integer getUploadSize() {
		return uploadSize;
	}

	/**
	 * Set the value related to the column: upload_size
	 * 
	 * @param uploadSize
	 *            the upload_size value
	 */
	public void setUploadSize(java.lang.Integer uploadSize) {
		this.uploadSize = uploadSize;
	}

	/**
	 * Return the value associated with the column: upload_date
	 */
	public java.util.Date getUploadDate() {
		return uploadDate;
	}

	/**
	 * Set the value related to the column: upload_date
	 * 
	 * @param uploadDate
	 *            the upload_date value
	 */
	public void setUploadDate(java.util.Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	/**
	 * Return the value associated with the column: is_admin
	 */
	public java.lang.Boolean getAdmin() {
		return admin;
	}

	/**
	 * Set the value related to the column: is_admin
	 * 
	 * @param admin
	 *            the is_admin value
	 */
	public void setAdmin(java.lang.Boolean admin) {
		this.admin = admin;
	}

	/**
	 * Return the value associated with the column: is_viewonly_admin
	 */
	public java.lang.Boolean getViewonlyAdmin() {
		return viewonlyAdmin;
	}

	/**
	 * Set the value related to the column: is_viewonly_admin
	 * 
	 * @param viewonlyAdmin
	 *            the is_viewonly_admin value
	 */
	public void setViewonlyAdmin(java.lang.Boolean viewonlyAdmin) {
		this.viewonlyAdmin = viewonlyAdmin;
	}

	/**
	 * Return the value associated with the column: is_self_admin
	 */
	public java.lang.Boolean getSelfAdmin() {
		return selfAdmin;
	}

	/**
	 * Set the value related to the column: is_self_admin
	 * 
	 * @param selfAdmin
	 *            the is_self_admin value
	 */
	public void setSelfAdmin(java.lang.Boolean selfAdmin) {
		this.selfAdmin = selfAdmin;
	}

	/**
	 * Return the value associated with the column: is_disabled
	 */
	public java.lang.Boolean getDisabled() {
		return disabled;
	}

	/**
	 * Set the value related to the column: is_disabled
	 * 
	 * @param disabled
	 *            the is_disabled value
	 */
	public void setDisabled(java.lang.Boolean disabled) {
		this.disabled = disabled;
	}

	public java.util.Date getCurrentLoginTime() {
		return currentLoginTime;
	}

	public void setCurrentLoginTime(java.util.Date currentLoginTime) {
		this.currentLoginTime = currentLoginTime;
	}

	public java.lang.String getCurrentLoginIp() {
		return currentLoginIp;
	}

	public void setCurrentLoginIp(java.lang.String currentLoginIp) {
		this.currentLoginIp = currentLoginIp;
	}

	public java.lang.Boolean getDelete() {
		return delete;
	}

	public void setDelete(java.lang.Boolean delete) {
		this.delete = delete;
	}

	/**
	 * Return the value associated with the column: group_id
	 */
	public com.cc.cms.entity.main.Group getGroup() {
		return group;
	}

	/**
	 * Set the value related to the column: group_id
	 * 
	 * @param group
	 *            the group_id value
	 */
	public void setGroup(com.cc.cms.entity.main.Group group) {
		this.group = group;
	}

	public com.cc.cms.entity.main.UserExt getExt() {
		return ext;
	}

	public void setExt(com.cc.cms.entity.main.UserExt ext) {
		this.ext = ext;
	}

	/**
	 * Return the value associated with the column: userSites
	 */
	public java.util.Set<com.cc.cms.entity.main.UserSite> getUserSites() {
		return userSites;
	}

	/**
	 * Set the value related to the column: userSites
	 * 
	 * @param userSites
	 *            the userSites value
	 */
	public void setUserSites(java.util.Set<com.cc.cms.entity.main.UserSite> userSites) {
		this.userSites = userSites;
	}

	/**
	 * Return the value associated with the column: roles
	 */
	public java.util.Set<com.cc.cms.entity.main.Role> getRoles() {
		return roles;
	}

	/**
	 * Set the value related to the column: roles
	 * 
	 * @param roles
	 *            the roles value
	 */
	public void setRoles(java.util.Set<com.cc.cms.entity.main.Role> roles) {
		this.roles = roles;
	}

	/**
	 * Return the value associated with the column: channels
	 */
	public java.util.Set<com.cc.cms.entity.main.Channel> getChannels() {
		return channels;
	}

	/**
	 * Set the value related to the column: channels
	 * 
	 * @param channels
	 *            the channels value
	 */
	public void setChannels(java.util.Set<com.cc.cms.entity.main.Channel> channels) {
		this.channels = channels;
	}

	public java.util.Set<com.cc.cms.entity.main.Content> getCollectContents() {
		return collectContents;
	}

	public void setCollectContents(java.util.Set<com.cc.cms.entity.main.Content> collectContents) {
		this.collectContents = collectContents;
	}

	public java.util.Set<com.cc.cms.entity.assist.Message> getSendMessages() {
		return sendMessages;
	}

	public void setSendMessages(java.util.Set<com.cc.cms.entity.assist.Message> sendMessages) {
		this.sendMessages = sendMessages;
	}

	public java.util.Set<com.cc.cms.entity.assist.Message> getReceivMessages() {
		return receivMessages;
	}

	public void setReceivMessages(java.util.Set<com.cc.cms.entity.assist.Message> receivMessages) {
		this.receivMessages = receivMessages;
	}

	public java.util.Set<com.cc.cms.entity.assist.ReceiverMessage> getSendReceiverMessages() {
		return sendReceiverMessages;
	}

	public void setSendReceiverMessages(java.util.Set<com.cc.cms.entity.assist.ReceiverMessage> sendReceiverMessages) {
		this.sendReceiverMessages = sendReceiverMessages;
	}

	public java.util.Set<com.cc.cms.entity.assist.ReceiverMessage> getReceivReceiverMessages() {
		return receivReceiverMessages;
	}

	public void setReceivReceiverMessages(java.util.Set<com.cc.cms.entity.assist.ReceiverMessage> receivReceiverMessages) {
		this.receivReceiverMessages = receivReceiverMessages;
	}

	@Override
	public boolean equals(Object obj) {
		if (null == obj)
			return false;
		if (!(obj instanceof com.cc.cms.entity.main.User))
			return false;
		else {
			com.cc.cms.entity.main.User user = (com.cc.cms.entity.main.User) obj;
			if (null == this.getId() || null == user.getId())
				return false;
			else
				return (this.getId().equals(user.getId()));
		}
	}

	@Override
	public int hashCode() {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getId())
				return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}

	@Override
	public String toString() {
		return super.toString();
	}
}
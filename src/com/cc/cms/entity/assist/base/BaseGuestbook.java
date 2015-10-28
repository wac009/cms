package com.cc.cms.entity.assist.base;

import java.io.Serializable;

/**
 * This is an object that contains data related to the cms_guestbook table. Do not modify this class
 * because it will be overwritten if the configuration file related to this class is modified.
 * 
 * @hibernate.class table="cms_guestbook"
 */
public abstract class BaseGuestbook implements Serializable {
	private static final long	serialVersionUID	= -4970328246891534344L;
	public static String		REF					= "Guestbook";
	public static String		PROP_RECOMMEND		= "recommend";
	public static String		PROP_SITE			= "site";
	public static String		PROP_REPLAY_TIME	= "replayTime";
	public static String		PROP_CREATE_TIME	= "createTime";
	public static String		PROP_IP				= "ip";
	public static String		PROP_CHECKED		= "checked";
	public static String		PROP_EXT			= "ext";
	public static String		PROP_CTG			= "ctg";
	public static String		PROP_ADMIN			= "admin";
	public static String		PROP_MEMBER			= "member";
	public static String		PROP_ID				= "id";

	// constructors
	public BaseGuestbook() {
		initialize();
	}
	/**
	 * Constructor for primary key
	 */
	public BaseGuestbook(java.lang.Integer id) {
		this.setId(id);
		initialize();
	}
	/**
	 * Constructor for required fields
	 */
	public BaseGuestbook(java.lang.Integer id, com.cc.cms.entity.main.Site site, com.cc.cms.entity.assist.GuestbookCtg ctg,
			java.lang.String ip, java.util.Date createTime, java.lang.Boolean checked, java.lang.Boolean recommend) {
		this.setId(id);
		this.setSite(site);
		this.setCtg(ctg);
		this.setIp(ip);
		this.setCreateTime(createTime);
		this.setChecked(checked);
		this.setRecommend(recommend);
		initialize();
	}
	protected void initialize() {}

	private int											hashCode	= Integer.MIN_VALUE;
	// primary key
	private java.lang.Integer							id;
	// fields
	private java.lang.String							ip;
	private java.util.Date								createTime;
	private java.util.Date								replayTime;
	private java.lang.Boolean							checked;
	private java.lang.Boolean							recommend;
	// one to one
	private com.cc.cms.entity.assist.GuestbookExt	ext;
	// many to one
	private com.cc.cms.entity.main.User				member;
	private com.cc.cms.entity.main.User				admin;
	private com.cc.cms.entity.main.Site				site;
	private com.cc.cms.entity.assist.GuestbookCtg	ctg;

	/**
	 * Return the unique identifier of this class
	 * 
	 * @hibernate.id generator-class="identity" column="guestbook_id"
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
	 * Return the value associated with the column: ip
	 */
	public java.lang.String getIp() {
		return ip;
	}
	/**
	 * Set the value related to the column: ip
	 * 
	 * @param ip
	 *            the ip value
	 */
	public void setIp(java.lang.String ip) {
		this.ip = ip;
	}
	/**
	 * Return the value associated with the column: create_time
	 */
	public java.util.Date getCreateTime() {
		return createTime;
	}
	/**
	 * Set the value related to the column: create_time
	 * 
	 * @param createTime
	 *            the create_time value
	 */
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * Return the value associated with the column: replay_time
	 */
	public java.util.Date getReplayTime() {
		return replayTime;
	}
	/**
	 * Set the value related to the column: replay_time
	 * 
	 * @param replayTime
	 *            the replay_time value
	 */
	public void setReplayTime(java.util.Date replayTime) {
		this.replayTime = replayTime;
	}
	/**
	 * Return the value associated with the column: is_checked
	 */
	public java.lang.Boolean getChecked() {
		return checked;
	}
	/**
	 * Set the value related to the column: is_checked
	 * 
	 * @param checked
	 *            the is_checked value
	 */
	public void setChecked(java.lang.Boolean checked) {
		this.checked = checked;
	}
	/**
	 * Return the value associated with the column: is_recommend
	 */
	public java.lang.Boolean getRecommend() {
		return recommend;
	}
	/**
	 * Set the value related to the column: is_recommend
	 * 
	 * @param recommend
	 *            the is_recommend value
	 */
	public void setRecommend(java.lang.Boolean recommend) {
		this.recommend = recommend;
	}
	/**
	 * Return the value associated with the column: ext
	 */
	public com.cc.cms.entity.assist.GuestbookExt getExt() {
		return ext;
	}
	/**
	 * Set the value related to the column: ext
	 * 
	 * @param ext
	 *            the ext value
	 */
	public void setExt(com.cc.cms.entity.assist.GuestbookExt ext) {
		this.ext = ext;
	}
	/**
	 * Return the value associated with the column: member_id
	 */
	public com.cc.cms.entity.main.User getMember() {
		return member;
	}
	/**
	 * Set the value related to the column: member_id
	 * 
	 * @param member
	 *            the member_id value
	 */
	public void setMember(com.cc.cms.entity.main.User member) {
		this.member = member;
	}
	/**
	 * Return the value associated with the column: admin_id
	 */
	public com.cc.cms.entity.main.User getAdmin() {
		return admin;
	}
	/**
	 * Set the value related to the column: admin_id
	 * 
	 * @param admin
	 *            the admin_id value
	 */
	public void setAdmin(com.cc.cms.entity.main.User admin) {
		this.admin = admin;
	}
	/**
	 * Return the value associated with the column: site_id
	 */
	public com.cc.cms.entity.main.Site getSite() {
		return site;
	}
	/**
	 * Set the value related to the column: site_id
	 * 
	 * @param site
	 *            the site_id value
	 */
	public void setSite(com.cc.cms.entity.main.Site site) {
		this.site = site;
	}
	/**
	 * Return the value associated with the column: guestbookctg_id
	 */
	public com.cc.cms.entity.assist.GuestbookCtg getCtg() {
		return ctg;
	}
	/**
	 * Set the value related to the column: guestbookctg_id
	 * 
	 * @param ctg
	 *            the guestbookctg_id value
	 */
	public void setCtg(com.cc.cms.entity.assist.GuestbookCtg ctg) {
		this.ctg = ctg;
	}
	@Override
	public boolean equals(Object obj) {
		if (null == obj)
			return false;
		if (!(obj instanceof com.cc.cms.entity.assist.Guestbook))
			return false;
		else {
			com.cc.cms.entity.assist.Guestbook guestbook = (com.cc.cms.entity.assist.Guestbook) obj;
			if (null == this.getId() || null == guestbook.getId())
				return false;
			else
				return (this.getId().equals(guestbook.getId()));
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
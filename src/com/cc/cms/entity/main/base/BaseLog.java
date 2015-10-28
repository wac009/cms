
package com.cc.cms.entity.main.base;

import java.io.Serializable;

/**
 * This is an object that contains data related to the cms_log table. Do not modify this class
 * because it will be overwritten if the configuration file related to this class is modified.
 * 
 * @hibernate.class table="cms_log"
 */
public abstract class BaseLog implements Serializable {

	private static final long	serialVersionUID	= 2917208749457897600L;
	public static String		REF					= "Log";
	public static String		PROP_TIME			= "time";
	public static String		PROP_SITE			= "site";
	public static String		PROP_USER			= "user";
	public static String		PROP_URL			= "url";
	public static String		PROP_IP				= "ip";
	public static String		PROP_CATEGORY		= "category";
	public static String		PROP_TITLE			= "title";
	public static String		PROP_CONTENT		= "content";
	public static String		PROP_ID				= "id";

	// constructors
	public BaseLog() {
		initialize();
	}

	/** Constructor for primary key */
	public BaseLog(java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	/** Constructor for required fields */
	public BaseLog(java.lang.Integer id, java.lang.Integer category, java.util.Date time) {
		this.setId(id);
		this.setCategory(category);
		this.setLogtime(time);
		initialize();
	}

	protected void initialize() {}

	private int							hashCode	= Integer.MIN_VALUE;
	// primary key
	private java.lang.Integer			id;
	// fields
	private java.lang.Integer			category;
	private java.util.Date				logtime;
	private java.lang.String			ip;
	private java.lang.String			url;
	private java.lang.String			title;
	private java.lang.String			content;
	// many to one
	private com.cc.cms.entity.main.User	user;
	private com.cc.cms.entity.main.Site	site;

	/**
	 * Return the unique identifier of this class
	 * 
	 * @hibernate.id generator-class="identity" column="log_id"
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

	/** Return the value associated with the column: category */
	public java.lang.Integer getCategory() {
		return category;
	}

	/**
	 * Set the value related to the column: category
	 * 
	 * @param category
	 *            the category value
	 */
	public void setCategory(java.lang.Integer category) {
		this.category = category;
	}

	public java.util.Date getLogtime() {
		return logtime;
	}

	public void setLogtime(java.util.Date logtime) {
		this.logtime = logtime;
	}

	/** Return the value associated with the column: ip */
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

	/** Return the value associated with the column: url */
	public java.lang.String getUrl() {
		return url;
	}

	/**
	 * Set the value related to the column: url
	 * 
	 * @param url
	 *            the url value
	 */
	public void setUrl(java.lang.String url) {
		this.url = url;
	}

	/** Return the value associated with the column: title */
	public java.lang.String getTitle() {
		return title;
	}

	/**
	 * Set the value related to the column: title
	 * 
	 * @param title
	 *            the title value
	 */
	public void setTitle(java.lang.String title) {
		this.title = title;
	}

	/** Return the value associated with the column: content */
	public java.lang.String getContent() {
		return content;
	}

	/**
	 * Set the value related to the column: content
	 * 
	 * @param content
	 *            the content value
	 */
	public void setContent(java.lang.String content) {
		this.content = content;
	}

	/** Return the value associated with the column: user_id */
	public com.cc.cms.entity.main.User getUser() {
		return user;
	}

	/**
	 * Set the value related to the column: user_id
	 * 
	 * @param user
	 *            the user_id value
	 */
	public void setUser(com.cc.cms.entity.main.User user) {
		this.user = user;
	}

	/** Return the value associated with the column: site_id */
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

	@Override
	public boolean equals(Object obj) {
		if (null == obj)
			return false;
		if (!(obj instanceof com.cc.cms.entity.main.Log))
			return false;
		else {
			com.cc.cms.entity.main.Log log = (com.cc.cms.entity.main.Log) obj;
			if (null == this.getId() || null == log.getId())
				return false;
			else
				return (this.getId().equals(log.getId()));
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
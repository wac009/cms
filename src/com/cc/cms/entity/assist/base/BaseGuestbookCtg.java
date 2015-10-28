package com.cc.cms.entity.assist.base;

import java.io.Serializable;

/**
 * This is an object that contains data related to the cms_guestbook_ctg table. Do not modify this
 * class because it will be overwritten if the configuration file related to this class is modified.
 * 
 * @hibernate.class table="cms_guestbook_ctg"
 */
public abstract class BaseGuestbookCtg implements Serializable {
	private static final long	serialVersionUID	= 2823249420448737203L;
	public static String		REF					= "GuestbookCtg";
	public static String		PROP_DESCRIPTION	= "description";
	public static String		PROP_SITE			= "site";
	public static String		PROP_PRIORITY		= "priority";
	public static String		PROP_NAME			= "name";
	public static String		PROP_ID				= "id";

	// constructors
	public BaseGuestbookCtg() {
		initialize();
	}
	/**
	 * Constructor for primary key
	 */
	public BaseGuestbookCtg(java.lang.Integer id) {
		this.setId(id);
		initialize();
	}
	/**
	 * Constructor for required fields
	 */
	public BaseGuestbookCtg(java.lang.Integer id, com.cc.cms.entity.main.Site site, java.lang.String name, java.lang.Integer priority) {
		this.setId(id);
		this.setSite(site);
		this.setName(name);
		this.setPriority(priority);
		initialize();
	}
	protected void initialize() {}

	private int								hashCode	= Integer.MIN_VALUE;
	// primary key
	private java.lang.Integer				id;
	// fields
	private java.lang.String				name;
	private java.lang.Integer				priority;
	private java.lang.String				description;
	// many to one
	private com.cc.cms.entity.main.Site	site;

	/**
	 * Return the unique identifier of this class
	 * 
	 * @hibernate.id generator-class="identity" column="guestbookctg_id"
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
	 * Return the value associated with the column: ctg_name
	 */
	public java.lang.String getName() {
		return name;
	}
	/**
	 * Set the value related to the column: ctg_name
	 * 
	 * @param name
	 *            the ctg_name value
	 */
	public void setName(java.lang.String name) {
		this.name = name;
	}
	/**
	 * Return the value associated with the column: priority
	 */
	public java.lang.Integer getPriority() {
		return priority;
	}
	/**
	 * Set the value related to the column: priority
	 * 
	 * @param priority
	 *            the priority value
	 */
	public void setPriority(java.lang.Integer priority) {
		this.priority = priority;
	}
	/**
	 * Return the value associated with the column: description
	 */
	public java.lang.String getDescription() {
		return description;
	}
	/**
	 * Set the value related to the column: description
	 * 
	 * @param description
	 *            the description value
	 */
	public void setDescription(java.lang.String description) {
		this.description = description;
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
	@Override
	public boolean equals(Object obj) {
		if (null == obj)
			return false;
		if (!(obj instanceof com.cc.cms.entity.assist.GuestbookCtg))
			return false;
		else {
			com.cc.cms.entity.assist.GuestbookCtg guestbookCtg = (com.cc.cms.entity.assist.GuestbookCtg) obj;
			if (null == this.getId() || null == guestbookCtg.getId())
				return false;
			else
				return (this.getId().equals(guestbookCtg.getId()));
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
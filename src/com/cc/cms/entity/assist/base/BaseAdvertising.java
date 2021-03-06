
package com.cc.cms.entity.assist.base;

import java.io.Serializable;

/**
 * This is an object that contains data related to the cms_advertising table. Do not modify this
 * class because it will be overwritten if the configuration file related to this class is modified.
 * 
 * @hibernate.class table="cms_advertising"
 */
public abstract class BaseAdvertising implements Serializable {

	private static final long	serialVersionUID	= 2168806789706115085L;
	public static String		REF					= "Advertising";
	public static String		PROP_END_TIME		= "endTime";
	public static String		PROP_START_TIME		= "startTime";
	public static String		PROP_WEIGHT			= "weight";
	public static String		PROP_SITE			= "site";
	public static String		PROP_ENABLED		= "enabled";
	public static String		PROP_CODE			= "code";
	public static String		PROP_CATEGORY		= "category";
	public static String		PROP_ADSPACE		= "adspace";
	public static String		PROP_NAME			= "name";
	public static String		PROP_ID				= "id";
	public static String		PROP_CLICK_COUNT	= "clickCount";
	public static String		PROP_DISPLAY_COUNT	= "displayCount";

	// constructors
	public BaseAdvertising() {
		initialize();
	}

	/** Constructor for primary key */
	public BaseAdvertising(java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	/** Constructor for required fields */
	public BaseAdvertising(java.lang.Integer id, com.cc.cms.entity.assist.AdvertisingSpace adspace, com.cc.cms.entity.main.Site site, java.lang.String name,
			java.lang.String category, java.lang.Integer weight, java.lang.Long displayCount, java.lang.Long clickCount, java.lang.Boolean enabled) {
		this.setId(id);
		this.setAdspace(adspace);
		this.setSite(site);
		this.setName(name);
		this.setCategory(category);
		this.setWeight(weight);
		this.setDisplayCount(displayCount);
		this.setClickCount(clickCount);
		this.setEnabled(enabled);
		initialize();
	}

	protected void initialize() {}

	private int													hashCode	= Integer.MIN_VALUE;
	// primary key
	private java.lang.Integer									id;
	// fields
	private java.lang.String									name;
	private java.lang.String									category;
	private java.lang.String									code;
	private java.lang.String									customer;
	private java.lang.Integer									weight;
	private java.lang.Long										displayCount;
	private java.lang.Long										clickCount;
	private java.util.Date										startTime;
	private java.util.Date										endTime;
	private java.lang.Boolean									enabled;
	private java.util.Date										addTime;
	// many to one
	private com.cc.cms.entity.assist.AdvertisingSpace			adspace;
	private com.cc.cms.entity.main.Site							site;
	private com.cc.cms.entity.main.User							user;
	// collections
	private java.util.Map<java.lang.String, java.lang.String>	attr;

	/**
	 * Return the unique identifier of this class
	 * 
	 * @hibernate.id generator-class="identity" column="advertising_id"
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

	/** Return the value associated with the column: ad_name */
	public java.lang.String getName() {
		return name;
	}

	/**
	 * Set the value related to the column: ad_name
	 * 
	 * @param name
	 *            the ad_name value
	 */
	public void setName(java.lang.String name) {
		this.name = name;
	}

	/** Return the value associated with the column: category */
	public java.lang.String getCategory() {
		return category;
	}

	/**
	 * Set the value related to the column: category
	 * 
	 * @param category
	 *            the category value
	 */
	public void setCategory(java.lang.String category) {
		this.category = category;
	}

	/** Return the value associated with the column: ad_code */
	public java.lang.String getCode() {
		return code;
	}

	/**
	 * Set the value related to the column: ad_code
	 * 
	 * @param code
	 *            the ad_code value
	 */
	public void setCode(java.lang.String code) {
		this.code = code;
	}

	/** Return the value associated with the column: ad_weight */
	public java.lang.Integer getWeight() {
		return weight;
	}

	/**
	 * Set the value related to the column: ad_weight
	 * 
	 * @param weight
	 *            the ad_weight value
	 */
	public void setWeight(java.lang.Integer weight) {
		this.weight = weight;
	}

	/** Return the value associated with the column: display_count */
	public java.lang.Long getDisplayCount() {
		return displayCount;
	}

	/**
	 * Set the value related to the column: display_count
	 * 
	 * @param displayCount
	 *            the display_count value
	 */
	public void setDisplayCount(java.lang.Long displayCount) {
		this.displayCount = displayCount;
	}

	/** Return the value associated with the column: click_count */
	public java.lang.Long getClickCount() {
		return clickCount;
	}

	/**
	 * Set the value related to the column: click_count
	 * 
	 * @param clickCount
	 *            the click_count value
	 */
	public void setClickCount(java.lang.Long clickCount) {
		this.clickCount = clickCount;
	}

	/** Return the value associated with the column: start_time */
	public java.util.Date getStartTime() {
		return startTime;
	}

	/**
	 * Set the value related to the column: start_time
	 * 
	 * @param startTime
	 *            the start_time value
	 */
	public void setStartTime(java.util.Date startTime) {
		this.startTime = startTime;
	}

	/** Return the value associated with the column: end_time */
	public java.util.Date getEndTime() {
		return endTime;
	}

	/**
	 * Set the value related to the column: end_time
	 * 
	 * @param endTime
	 *            the end_time value
	 */
	public void setEndTime(java.util.Date endTime) {
		this.endTime = endTime;
	}

	/** Return the value associated with the column: is_enabled */
	public java.lang.Boolean getEnabled() {
		return enabled;
	}

	/**
	 * Set the value related to the column: is_enabled
	 * 
	 * @param enabled
	 *            the is_enabled value
	 */
	public void setEnabled(java.lang.Boolean enabled) {
		this.enabled = enabled;
	}

	public java.util.Date getAddTime() {
		return addTime;
	}

	public void setAddTime(java.util.Date addTime) {
		this.addTime = addTime;
	}

	public java.lang.String getCustomer() {
		return customer;
	}

	public void setCustomer(java.lang.String customer) {
		this.customer = customer;
	}

	/** Return the value associated with the column: adspace_id */
	public com.cc.cms.entity.assist.AdvertisingSpace getAdspace() {
		return adspace;
	}

	/**
	 * Set the value related to the column: adspace_id
	 * 
	 * @param adspace
	 *            the adspace_id value
	 */
	public void setAdspace(com.cc.cms.entity.assist.AdvertisingSpace adspace) {
		this.adspace = adspace;
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

	public com.cc.cms.entity.main.User getUser() {
		return user;
	}

	public void setUser(com.cc.cms.entity.main.User user) {
		this.user = user;
	}

	/** Return the value associated with the column: attr */
	public java.util.Map<java.lang.String, java.lang.String> getAttr() {
		return attr;
	}

	/**
	 * Set the value related to the column: attr
	 * 
	 * @param attr
	 *            the attr value
	 */
	public void setAttr(java.util.Map<java.lang.String, java.lang.String> attr) {
		this.attr = attr;
	}

	@Override
	public boolean equals(Object obj) {
		if (null == obj)
			return false;
		if (!(obj instanceof com.cc.cms.entity.assist.Advertising))
			return false;
		else {
			com.cc.cms.entity.assist.Advertising cmsAdvertising = (com.cc.cms.entity.assist.Advertising) obj;
			if (null == this.getId() || null == cmsAdvertising.getId())
				return false;
			else
				return (this.getId().equals(cmsAdvertising.getId()));
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
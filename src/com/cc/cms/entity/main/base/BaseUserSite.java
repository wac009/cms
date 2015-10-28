package com.cc.cms.entity.main.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the cms_user_site table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="cms_user_site"
 */

public abstract class BaseUserSite  implements Serializable {

	private static final long	serialVersionUID	= 3933714206667180614L;
	public static String REF = "UserSite";
	public static String PROP_ALL_CHANNEL = "allChannel";
	public static String PROP_SITE = "site";
	public static String PROP_USER = "user";
	public static String PROP_CHECK_STEP = "checkStep";
	public static String PROP_ID = "id";


	// constructors
	public BaseUserSite () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseUserSite (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseUserSite (
		java.lang.Integer id,
		com.cc.cms.entity.main.User user,
		com.cc.cms.entity.main.Site site,
		java.lang.Byte checkStep,
		java.lang.Boolean allChannel) {

		this.setId(id);
		this.setUser(user);
		this.setSite(site);
		this.setCheckStep(checkStep);
		this.setAllChannel(allChannel);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.Byte checkStep;
	private java.lang.Boolean allChannel;

	// many to one
	private com.cc.cms.entity.main.User user;
	private com.cc.cms.entity.main.Site site;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="usersite_id"
     */
	public java.lang.Integer getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (java.lang.Integer id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: check_step
	 */
	public java.lang.Byte getCheckStep () {
		return checkStep;
	}

	/**
	 * Set the value related to the column: check_step
	 * @param checkStep the check_step value
	 */
	public void setCheckStep (java.lang.Byte checkStep) {
		this.checkStep = checkStep;
	}


	/**
	 * Return the value associated with the column: is_all_channel
	 */
	public java.lang.Boolean getAllChannel () {
		return allChannel;
	}

	/**
	 * Set the value related to the column: is_all_channel
	 * @param allChannel the is_all_channel value
	 */
	public void setAllChannel (java.lang.Boolean allChannel) {
		this.allChannel = allChannel;
	}


	/**
	 * Return the value associated with the column: user_id
	 */
	public com.cc.cms.entity.main.User getUser () {
		return user;
	}

	/**
	 * Set the value related to the column: user_id
	 * @param user the user_id value
	 */
	public void setUser (com.cc.cms.entity.main.User user) {
		this.user = user;
	}


	/**
	 * Return the value associated with the column: site_id
	 */
	public com.cc.cms.entity.main.Site getSite () {
		return site;
	}

	/**
	 * Set the value related to the column: site_id
	 * @param site the site_id value
	 */
	public void setSite (com.cc.cms.entity.main.Site site) {
		this.site = site;
	}



	@Override
	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.cc.cms.entity.main.UserSite)) return false;
		else {
			com.cc.cms.entity.main.UserSite userSite = (com.cc.cms.entity.main.UserSite) obj;
			if (null == this.getId() || null == userSite.getId()) return false;
			else return (this.getId().equals(userSite.getId()));
		}
	}

	@Override
	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getId()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}


	@Override
	public String toString () {
		return super.toString();
	}


}
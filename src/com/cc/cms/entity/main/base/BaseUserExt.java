
package com.cc.cms.entity.main.base;

import java.io.*;

/**
 * This is an object that contains data related to the cms_user_ext table. Do not modify this class
 * because it will be overwritten if the configuration file related to this class is modified.
 * 
 * @hibernate.class table="cms_user_ext"
 */
public abstract class BaseUserExt implements Serializable {

	private static final long	serialVersionUID	= 7138171432035894287L;
	public static String		REF					= "UserExt";
	public static String		PROP_MSN			= "msn";
	public static String		PROP_BIRTHDAY		= "birthday";
	public static String		PROP_GENDER			= "gender";
	public static String		PROP_MOBILE			= "mobile";
	public static String		PROP_COMEFROM		= "comefrom";
	public static String		PROP_USER			= "user";
	public static String		PROP_INTRO			= "intro";
	public static String		PROP_REALNAME		= "realname";
	public static String		PROP_QQ				= "qq";
	public static String		PROP_ID				= "id";
	public static String		PROP_PHONE			= "phone";

	// constructors
	public BaseUserExt() {
		initialize();
	}

	/** Constructor for primary key */
	public BaseUserExt(java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	protected void initialize() {}

	private int							hashCode	= Integer.MIN_VALUE;
	// primary key
	private java.lang.Integer			id;
	// fields
	private java.lang.String			realname;
	private java.lang.Integer			gender;
	private java.util.Date				birthday;
	private java.lang.String			intro;
	private java.lang.String			address;
	private java.lang.String			zip;
	private java.lang.String			qq;
	private java.lang.String			msn;
	private java.lang.String			tel;
	private java.lang.String			fax;
	private java.lang.String			mobile;
	private java.lang.String			userImg;
	private java.lang.String			signature;
	// one to one
	private com.cc.cms.entity.main.User	user;

	/**
	 * Return the unique identifier of this class
	 * 
	 * @hibernate.id generator-class="foreign" column="user_id"
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

	/** Return the value associated with the column: realname */
	public java.lang.String getRealname() {
		return realname;
	}

	/**
	 * Set the value related to the column: realname
	 * 
	 * @param realname
	 *            the realname value
	 */
	public void setRealname(java.lang.String realname) {
		this.realname = realname;
	}

	public java.lang.Integer getGender() {
		return gender;
	}

	public void setGender(java.lang.Integer gender) {
		this.gender = gender;
	}

	/** Return the value associated with the column: birthday */
	public java.util.Date getBirthday() {
		return birthday;
	}

	/**
	 * Set the value related to the column: birthday
	 * 
	 * @param birthday
	 *            the birthday value
	 */
	public void setBirthday(java.util.Date birthday) {
		this.birthday = birthday;
	}

	/** Return the value associated with the column: intro */
	public java.lang.String getIntro() {
		return intro;
	}

	/**
	 * Set the value related to the column: intro
	 * 
	 * @param intro
	 *            the intro value
	 */
	public void setIntro(java.lang.String intro) {
		this.intro = intro;
	}

	/** Return the value associated with the column: qq */
	public java.lang.String getQq() {
		return qq;
	}

	/**
	 * Set the value related to the column: qq
	 * 
	 * @param qq
	 *            the qq value
	 */
	public void setQq(java.lang.String qq) {
		this.qq = qq;
	}

	/** Return the value associated with the column: msn */
	public java.lang.String getMsn() {
		return msn;
	}

	/**
	 * Set the value related to the column: msn
	 * 
	 * @param msn
	 *            the msn value
	 */
	public void setMsn(java.lang.String msn) {
		this.msn = msn;
	}

	public java.lang.String getAddress() {
		return address;
	}

	public void setAddress(java.lang.String address) {
		this.address = address;
	}

	public java.lang.String getTel() {
		return tel;
	}

	public void setTel(java.lang.String tel) {
		this.tel = tel;
	}

	/** Return the value associated with the column: mobile */
	public java.lang.String getMobile() {
		return mobile;
	}

	/**
	 * Set the value related to the column: mobile
	 * 
	 * @param mobile
	 *            the mobile value
	 */
	public void setMobile(java.lang.String mobile) {
		this.mobile = mobile;
	}

	public java.lang.String getUserImg() {
		return userImg;
	}

	public void setUserImg(java.lang.String userImg) {
		this.userImg = userImg;
	}

	public java.lang.String getSignature() {
		return signature;
	}

	public void setSignature(java.lang.String signature) {
		this.signature = signature;
	}

	public java.lang.String getFax() {
		return fax;
	}

	public void setFax(java.lang.String fax) {
		this.fax = fax;
	}

	public java.lang.String getZip() {
		return zip;
	}

	public void setZip(java.lang.String zip) {
		this.zip = zip;
	}

	/** Return the value associated with the column: user */
	public com.cc.cms.entity.main.User getUser() {
		return user;
	}

	/**
	 * Set the value related to the column: user
	 * 
	 * @param user
	 *            the user value
	 */
	public void setUser(com.cc.cms.entity.main.User user) {
		this.user = user;
	}

	@Override
	public boolean equals(Object obj) {
		if (null == obj)
			return false;
		if (!(obj instanceof com.cc.cms.entity.main.UserExt))
			return false;
		else {
			com.cc.cms.entity.main.UserExt userExt = (com.cc.cms.entity.main.UserExt) obj;
			if (null == this.getId() || null == userExt.getId())
				return false;
			else
				return (this.getId().equals(userExt.getId()));
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
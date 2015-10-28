package com.cc.cms.entity.assist.base;

import java.io.Serializable;

/**
 * This is an object that contains data related to the cms_sensitivity table. Do not modify this
 * class because it will be overwritten if the configuration file related to this class is modified.
 * 
 * @hibernate.class table="cms_sensitivity"
 */
public abstract class BaseSensitivity implements Serializable {
	private static final long	serialVersionUID	= 953934913115919625L;
	public static String		REF					= "Sensitivity";
	public static String		PROP_SEARCH			= "search";
	public static String		PROP_ID				= "id";
	public static String		PROP_REPLACEMENT	= "replacement";

	// constructors
	public BaseSensitivity() {
		initialize();
	}
	/**
	 * Constructor for primary key
	 */
	public BaseSensitivity(java.lang.Integer id) {
		this.setId(id);
		initialize();
	}
	/**
	 * Constructor for required fields
	 */
	public BaseSensitivity(java.lang.Integer id, java.lang.String search, java.lang.String replacement) {
		this.setId(id);
		this.setSearch(search);
		this.setReplacement(replacement);
		initialize();
	}
	protected void initialize() {}

	private int					hashCode	= Integer.MIN_VALUE;
	// primary key
	private java.lang.Integer	id;
	// fields
	private java.lang.String	search;
	private java.lang.String	replacement;

	/**
	 * Return the unique identifier of this class
	 * 
	 * @hibernate.id generator-class="identity" column="sensitivity_id"
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
	 * Return the value associated with the column: search
	 */
	public java.lang.String getSearch() {
		return search;
	}
	/**
	 * Set the value related to the column: search
	 * 
	 * @param search
	 *            the search value
	 */
	public void setSearch(java.lang.String search) {
		this.search = search;
	}
	/**
	 * Return the value associated with the column: replacement
	 */
	public java.lang.String getReplacement() {
		return replacement;
	}
	/**
	 * Set the value related to the column: replacement
	 * 
	 * @param replacement
	 *            the replacement value
	 */
	public void setReplacement(java.lang.String replacement) {
		this.replacement = replacement;
	}
	@Override
	public boolean equals(Object obj) {
		if (null == obj)
			return false;
		if (!(obj instanceof com.cc.cms.entity.assist.Sensitivity))
			return false;
		else {
			com.cc.cms.entity.assist.Sensitivity sensitivity = (com.cc.cms.entity.assist.Sensitivity) obj;
			if (null == this.getId() || null == sensitivity.getId())
				return false;
			else
				return (this.getId().equals(sensitivity.getId()));
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
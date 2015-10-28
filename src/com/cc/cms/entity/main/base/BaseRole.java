package com.cc.cms.entity.main.base;

import java.io.Serializable;

/**
 * This is an object that contains data related to the cms_role table. Do not modify this class
 * because it will be overwritten if the configuration file related to this class is modified.
 * 
 * @hibernate.class table="cms_role"
 */
public abstract class BaseRole implements Serializable {
	private static final long	serialVersionUID	= -7800366972457239680L;
	public static String		REF					= "Role";
	public static String		PROP_SITE			= "site";
	public static String		PROP_SUPER			= "super";
	public static String		PROP_PRIORITY		= "priority";
	public static String		PROP_NAME			= "name";
	public static String		PROP_ID				= "id";

	// constructors
	public BaseRole() {
		initialize();
	}
	/** Constructor for primary key */
	public BaseRole(java.lang.Integer id) {
		this.setId(id);
		initialize();
	}
	/** Constructor for required fields */
	public BaseRole(java.lang.Integer id, java.lang.String name, java.lang.Integer priority, java.lang.Boolean m_super) {
		this.setId(id);
		this.setName(name);
		this.setPriority(priority);
		this.setSuper(m_super);
		initialize();
	}
	protected void initialize() {}

	private int													hashCode	= Integer.MIN_VALUE;
	// primary key
	private java.lang.Integer									id;
	// fields
	private java.lang.String									name;
	private java.lang.String									description;
	private java.lang.Integer									priority;
	private java.lang.Boolean									m_super;
	// collections
	private java.util.Set<com.cc.cms.entity.main.Permission>	permissions;
	private java.util.Set<com.cc.cms.entity.main.Site>		sites;
	private java.util.Set<com.cc.cms.entity.main.User>		users;

	/**
	 * Return the unique identifier of this class
	 * 
	 * @hibernate.id generator-class="identity" column="role_id"
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
	/** Return the value associated with the column: role_name */
	public java.lang.String getName() {
		return name;
	}
	/**
	 * Set the value related to the column: role_name
	 * 
	 * @param name
	 *            the role_name value
	 */
	public void setName(java.lang.String name) {
		this.name = name;
	}
	/** Return the value associated with the column: priority */
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
	public java.lang.String getDescription() {
		return description;
	}
	public void setDescription(java.lang.String description) {
		this.description = description;
	}
	/** Return the value associated with the column: is_super */
	public java.lang.Boolean getSuper() {
		return m_super;
	}
	/**
	 * Set the value related to the column: is_super
	 * 
	 * @param m_super
	 *            the is_super value
	 */
	public void setSuper(java.lang.Boolean m_super) {
		this.m_super = m_super;
	}
	public java.util.Set<com.cc.cms.entity.main.Permission> getPermissions() {
		return permissions;
	}
	public void setPermissions(java.util.Set<com.cc.cms.entity.main.Permission> permissions) {
		this.permissions = permissions;
	}
	public java.util.Set<com.cc.cms.entity.main.Site> getSites() {
		return sites;
	}
	public void setSites(java.util.Set<com.cc.cms.entity.main.Site> sites) {
		this.sites = sites;
	}
	public java.util.Set<com.cc.cms.entity.main.User> getUsers() {
		return users;
	}
	public void setUsers(java.util.Set<com.cc.cms.entity.main.User> users) {
		this.users = users;
	}
	@Override
	public boolean equals(Object obj) {
		if (null == obj)
			return false;
		if (!(obj instanceof com.cc.cms.entity.main.Role))
			return false;
		else {
			com.cc.cms.entity.main.Role role = (com.cc.cms.entity.main.Role) obj;
			if (null == this.getId() || null == role.getId())
				return false;
			else
				return (this.getId().equals(role.getId()));
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
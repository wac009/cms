
package com.cc.cms.entity.main.base;

import java.io.*;

/** This is an object that contains data related to the cms_channel table. Do not modify this class because it will be overwritten if the configuration
 * file related to this class is modified.
 * 
 * @hibernate.class table="cms_channel" */
public abstract class BaseChannel implements Serializable {

	private static final long	serialVersionUID	= 8792909854994517677L;
	public static String		REF					= "Channel";
	public static String		PROP_MODEL			= "model";
	public static String		PROP_CHANNEL_EXT	= "channelExt";
	public static String		PROP_RGT			= "rgt";
	public static String		PROP_SITE			= "site";
	public static String		PROP_LFT			= "lft";
	public static String		PROP_PARENT			= "parent";
	public static String		PROP_PATH			= "path";
	public static String		PROP_PRIORITY		= "priority";

	// constructors
	public BaseChannel() {
		initialize();
	}

	/** Constructor for primary key */
	public BaseChannel(java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	/** Constructor for required fields */
	public BaseChannel(java.lang.Integer id, com.cc.cms.entity.main.Site site, com.cc.cms.entity.main.Model model, java.lang.Integer lft,
			java.lang.Integer rgt, java.lang.Integer priority) {
		this.setId(id);
		this.setSite(site);
		this.setModel(model);
		this.setLft(lft);
		this.setRgt(rgt);
		this.setPriority(priority);
		initialize();
	}

	protected void initialize() {}

	private int													hashCode	= Integer.MIN_VALUE;
	// primary key
	private java.lang.Integer									id;
	// fields
	private java.lang.String									path;
	private java.lang.Integer									lft;
	private java.lang.Integer									rgt;
	private java.lang.Integer									priority;
	// one to one
	private com.cc.cms.entity.main.ChannelExt					ext;
	private com.cc.cms.entity.main.ChannelTxt					channelTxt;
	// many to one
	private com.cc.cms.entity.main.Site							site;
	private com.cc.cms.entity.main.Model						model;
	private com.cc.cms.entity.main.Channel						parent;
	// collections
	private java.util.Set<com.cc.cms.entity.main.Channel>		child;
	private java.util.Set<com.cc.cms.entity.main.Group>			viewGroups;
	private java.util.Set<com.cc.cms.entity.main.Group>			contriGroups;
	private java.util.Set<com.cc.cms.entity.main.User>			users;
	private java.util.Map<java.lang.String, java.lang.String>	attr;

	/** Return the unique identifier of this class
	 * 
	 * @hibernate.id generator-class="identity" column="channel_id" */
	public java.lang.Integer getId() {
		return id;
	}

	/** Set the unique identifier of this class
	 * 
	 * @param id the new ID */
	public void setId(java.lang.Integer id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}

	/** Return the value associated with the column: channel_path */
	public java.lang.String getPath() {
		return path;
	}

	/** Set the value related to the column: channel_path
	 * 
	 * @param path the channel_path value */
	public void setPath(java.lang.String path) {
		this.path = path;
	}

	/** Return the value associated with the column: lft */
	public java.lang.Integer getLft() {
		return lft;
	}

	/** Set the value related to the column: lft
	 * 
	 * @param lft the lft value */
	public void setLft(java.lang.Integer lft) {
		this.lft = lft;
	}

	/** Return the value associated with the column: rgt */
	public java.lang.Integer getRgt() {
		return rgt;
	}

	/** Set the value related to the column: rgt
	 * 
	 * @param rgt the rgt value */
	public void setRgt(java.lang.Integer rgt) {
		this.rgt = rgt;
	}

	/** Return the value associated with the column: priority */
	public java.lang.Integer getPriority() {
		return priority;
	}

	/** Set the value related to the column: priority
	 * 
	 * @param priority the priority value */
	public void setPriority(java.lang.Integer priority) {
		this.priority = priority;
	}

	/** Return the value associated with the column: channelExt */
	public com.cc.cms.entity.main.ChannelExt getExt() {
		return ext;
	}

	/** Set the value related to the column: channelExt
	 * 
	 * @param channelExt the channelExt value */
	public void setExt(com.cc.cms.entity.main.ChannelExt channelExt) {
		this.ext = channelExt;
	}

	/** Return the value associated with the column: site_id */
	public com.cc.cms.entity.main.Site getSite() {
		return site;
	}

	/** Set the value related to the column: site_id
	 * 
	 * @param site the site_id value */
	public void setSite(com.cc.cms.entity.main.Site site) {
		this.site = site;
	}

	/** Return the value associated with the column: model_id */
	public com.cc.cms.entity.main.Model getModel() {
		return model;
	}

	/** Set the value related to the column: model_id
	 * 
	 * @param model the model_id value */
	public void setModel(com.cc.cms.entity.main.Model model) {
		this.model = model;
	}

	/** Return the value associated with the column: parent_id */
	public com.cc.cms.entity.main.Channel getParent() {
		return parent;
	}

	/** Set the value related to the column: parent_id
	 * 
	 * @param parent the parent_id value */
	public void setParent(com.cc.cms.entity.main.Channel parent) {
		this.parent = parent;
	}

	/** Return the value associated with the column: child */
	public java.util.Set<com.cc.cms.entity.main.Channel> getChild() {
		return child;
	}

	/** Set the value related to the column: child
	 * 
	 * @param child the child value */
	public void setChild(java.util.Set<com.cc.cms.entity.main.Channel> child) {
		this.child = child;
	}

	/** Return the value associated with the column: viewGroups */
	public java.util.Set<com.cc.cms.entity.main.Group> getViewGroups() {
		return viewGroups;
	}

	/** Set the value related to the column: viewGroups
	 * 
	 * @param viewGroups the viewGroups value */
	public void setViewGroups(java.util.Set<com.cc.cms.entity.main.Group> viewGroups) {
		this.viewGroups = viewGroups;
	}

	/** Return the value associated with the column: contriGroups */
	public java.util.Set<com.cc.cms.entity.main.Group> getContriGroups() {
		return contriGroups;
	}

	/** Set the value related to the column: contriGroups
	 * 
	 * @param contriGroups the contriGroups value */
	public void setContriGroups(java.util.Set<com.cc.cms.entity.main.Group> contriGroups) {
		this.contriGroups = contriGroups;
	}

	/** Return the value associated with the column: users */
	public java.util.Set<com.cc.cms.entity.main.User> getUsers() {
		return users;
	}

	/** Set the value related to the column: users
	 * 
	 * @param users the users value */
	public void setUsers(java.util.Set<com.cc.cms.entity.main.User> users) {
		this.users = users;
	}

	/** Return the value associated with the column: channelTxtSet */
	public com.cc.cms.entity.main.ChannelTxt getChannelTxt() {
		return channelTxt;
	}

	/** Set the value related to the column: channelTxtSet
	 * 
	 * @param channelTxtSet the channelTxtSet value */
	public void setChannelTxt(com.cc.cms.entity.main.ChannelTxt channelTxt) {
		this.channelTxt = channelTxt;
	}

	/** Return the value associated with the column: attr */
	public java.util.Map<java.lang.String, java.lang.String> getAttr() {
		return attr;
	}

	/** Set the value related to the column: attr
	 * 
	 * @param attr the attr value */
	public void setAttr(java.util.Map<java.lang.String, java.lang.String> attr) {
		this.attr = attr;
	}

	@Override
	public boolean equals(Object obj) {
		if (null == obj)
			return false;
		if (!(obj instanceof com.cc.cms.entity.main.Channel))
			return false;
		else {
			com.cc.cms.entity.main.Channel channel = (com.cc.cms.entity.main.Channel) obj;
			if (null == this.getId() || null == channel.getId())
				return false;
			else
				return (this.getId().equals(channel.getId()));
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
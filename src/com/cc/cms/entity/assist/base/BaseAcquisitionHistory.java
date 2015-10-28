package com.cc.cms.entity.assist.base;

import java.io.Serializable;

/**
 * This is an object that contains data related to the cms_acquisition_history table. Do not modify
 * this class because it will be overwritten if the configuration file related to this class is
 * modified.
 * 
 * @hibernate.class table="cms_acquisition_history"
 */
public abstract class BaseAcquisitionHistory implements Serializable {
	private static final long	serialVersionUID	= 170046328598423006L;
	public static String		REF					= "AcquisitionHistory";
	public static String		PROP_ACQUISITION	= "acquisition";
	public static String		PROP_DESCRIPTION	= "description";
	public static String		PROP_CONTENT_URL	= "contentUrl";
	public static String		PROP_ID				= "id";
	public static String		PROP_CONTENT		= "content";
	public static String		PROP_CHANNEL_URL	= "channelUrl";
	public static String		PROP_TITLE			= "title";

	// constructors
	public BaseAcquisitionHistory() {
		initialize();
	}
	/** Constructor for primary key */
	public BaseAcquisitionHistory(java.lang.Integer id) {
		this.setId(id);
		initialize();
	}
	/** Constructor for required fields */
	public BaseAcquisitionHistory(java.lang.Integer id, java.lang.String channelUrl, java.lang.String contentUrl) {
		this.setId(id);
		this.setChannelUrl(channelUrl);
		this.setContentUrl(contentUrl);
		initialize();
	}
	protected void initialize() {}

	private int										hashCode	= Integer.MIN_VALUE;
	// primary key
	private java.lang.Integer						id;
	// fields
	private java.lang.String						channelUrl;
	private java.lang.String						contentUrl;
	private java.lang.String						title;
	private java.lang.String						description;
	// many to one
	private com.cc.cms.entity.assist.Acquisition	acquisition;
	private com.cc.cms.entity.main.Content			content;

	/**
	 * Return the unique identifier of this class
	 * 
	 * @hibernate.id generator-class="identity" column="history_id"
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
	/** Return the value associated with the column: channel_url */
	public java.lang.String getChannelUrl() {
		return channelUrl;
	}
	/**
	 * Set the value related to the column: channel_url
	 * 
	 * @param channelUrl
	 *            the channel_url value
	 */
	public void setChannelUrl(java.lang.String channelUrl) {
		this.channelUrl = channelUrl;
	}
	/** Return the value associated with the column: content_url */
	public java.lang.String getContentUrl() {
		return contentUrl;
	}
	/**
	 * Set the value related to the column: content_url
	 * 
	 * @param contentUrl
	 *            the content_url value
	 */
	public void setContentUrl(java.lang.String contentUrl) {
		this.contentUrl = contentUrl;
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
	/** Return the value associated with the column: description */
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
	/** Return the value associated with the column: acquisition_id */
	public com.cc.cms.entity.assist.Acquisition getAcquisition() {
		return acquisition;
	}
	/**
	 * Set the value related to the column: acquisition_id
	 * 
	 * @param acquisition
	 *            the acquisition_id value
	 */
	public void setAcquisition(com.cc.cms.entity.assist.Acquisition acquisition) {
		this.acquisition = acquisition;
	}
	/** Return the value associated with the column: content_id */
	public com.cc.cms.entity.main.Content getContent() {
		return content;
	}
	/**
	 * Set the value related to the column: content_id
	 * 
	 * @param content
	 *            the content_id value
	 */
	public void setContent(com.cc.cms.entity.main.Content content) {
		this.content = content;
	}
	@Override
	public boolean equals(Object obj) {
		if (null == obj)
			return false;
		if (!(obj instanceof com.cc.cms.entity.assist.AcquisitionHistory))
			return false;
		else {
			com.cc.cms.entity.assist.AcquisitionHistory acquisitionHistory = (com.cc.cms.entity.assist.AcquisitionHistory) obj;
			if (null == this.getId() || null == acquisitionHistory.getId())
				return false;
			else
				return (this.getId().equals(acquisitionHistory.getId()));
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
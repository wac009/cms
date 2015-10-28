
package com.cc.cms.entity.main.base;

import java.io.Serializable;

/**
 * This is an object that contains data related to the cms_topic table. Do not modify this class
 * because it will be overwritten if the configuration file related to this class is modified.
 * 
 * @hibernate.class table="cms_topic"
 */
public abstract class BaseTopic implements Serializable {

	private static final long	serialVersionUID	= -8451639759118105109L;
	public static String		REF					= "Topic";
	public static String		PROP_RECOMMEND		= "recommend";
	public static String		PROP_DESCRIPTION	= "description";
	public static String		PROP_TITLE_IMG		= "titleImg";
	public static String		PROP_SHORT_NAME		= "shortName";
	public static String		PROP_KEYWORDS		= "keywords";
	public static String		PROP_CHANNEL		= "channel";
	public static String		PROP_PRIORITY		= "priority";
	public static String		PROP_NAME			= "name";
	public static String		PROP_ID				= "id";
	public static String		PROP_CONTENT_IMG	= "contentImg";

	// constructors
	public BaseTopic() {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTopic(java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseTopic(java.lang.Integer id, java.lang.String name, java.lang.Integer priority, java.lang.Boolean recommend) {
		this.setId(id);
		this.setName(name);
		this.setPriority(priority);
		this.setRecommend(recommend);
		initialize();
	}

	protected void initialize() {}

	private int								hashCode	= Integer.MIN_VALUE;
	// primary key
	private java.lang.Integer				id;
	// fields
	private java.lang.String				name;
	private java.lang.String				shortName;
	private java.lang.String				keywords;
	private java.lang.String				description;
	private java.lang.String				titleImg;
	private java.lang.String				contentImg;
	private java.lang.String				tplContent;
	private java.util.Date					createTime;
	private java.lang.Integer				priority;
	private java.lang.Boolean				disabled;
	private java.lang.Boolean				recommend;
	// many to one
	private com.cc.cms.entity.main.Site		site;
	private com.cc.cms.entity.main.User		user;
	private com.cc.cms.entity.main.Template	template;
	private com.cc.cms.entity.main.Channel	channel;

	/**
	 * Return the unique identifier of this class
	 * 
	 * @hibernate.id generator-class="identity" column="topic_id"
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
	 * Return the value associated with the column: topic_name
	 */
	public java.lang.String getName() {
		return name;
	}

	/**
	 * Set the value related to the column: topic_name
	 * 
	 * @param name
	 *            the topic_name value
	 */
	public void setName(java.lang.String name) {
		this.name = name;
	}

	/**
	 * Return the value associated with the column: short_name
	 */
	public java.lang.String getShortName() {
		return shortName;
	}

	/**
	 * Set the value related to the column: short_name
	 * 
	 * @param shortName
	 *            the short_name value
	 */
	public void setShortName(java.lang.String shortName) {
		this.shortName = shortName;
	}

	/**
	 * Return the value associated with the column: keywords
	 */
	public java.lang.String getKeywords() {
		return keywords;
	}

	/**
	 * Set the value related to the column: keywords
	 * 
	 * @param keywords
	 *            the keywords value
	 */
	public void setKeywords(java.lang.String keywords) {
		this.keywords = keywords;
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
	 * Return the value associated with the column: title_img
	 */
	public java.lang.String getTitleImg() {
		return titleImg;
	}

	/**
	 * Set the value related to the column: title_img
	 * 
	 * @param titleImg
	 *            the title_img value
	 */
	public void setTitleImg(java.lang.String titleImg) {
		this.titleImg = titleImg;
	}

	/**
	 * Return the value associated with the column: content_img
	 */
	public java.lang.String getContentImg() {
		return contentImg;
	}

	/**
	 * Set the value related to the column: content_img
	 * 
	 * @param contentImg
	 *            the content_img value
	 */
	public void setContentImg(java.lang.String contentImg) {
		this.contentImg = contentImg;
	}

	/**
	 * Return the value associated with the column: tpl_content
	 */
	public java.lang.String getTplContent() {
		return tplContent;
	}

	/**
	 * Set the value related to the column: tpl_content
	 * 
	 * @param tplContent
	 *            the tpl_content value
	 */
	public void setTplContent(java.lang.String tplContent) {
		this.tplContent = tplContent;
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

	public java.lang.Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(java.lang.Boolean disabled) {
		this.disabled = disabled;
	}

	public com.cc.cms.entity.main.Site getSite() {
		return site;
	}

	public void setSite(com.cc.cms.entity.main.Site site) {
		this.site = site;
	}

	public com.cc.cms.entity.main.User getUser() {
		return user;
	}

	public void setUser(com.cc.cms.entity.main.User user) {
		this.user = user;
	}

	public com.cc.cms.entity.main.Template getTemplate() {
		return template;
	}

	public void setTemplate(com.cc.cms.entity.main.Template template) {
		this.template = template;
	}

	public com.cc.cms.entity.main.Channel getChannel() {
		return channel;
	}

	public void setChannel(com.cc.cms.entity.main.Channel channel) {
		this.channel = channel;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public boolean equals(Object obj) {
		if (null == obj)
			return false;
		if (!(obj instanceof com.cc.cms.entity.main.Topic))
			return false;
		else {
			com.cc.cms.entity.main.Topic topic = (com.cc.cms.entity.main.Topic) obj;
			if (null == this.getId() || null == topic.getId())
				return false;
			else
				return (this.getId().equals(topic.getId()));
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
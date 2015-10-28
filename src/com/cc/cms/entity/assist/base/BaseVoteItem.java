
package com.cc.cms.entity.assist.base;

import java.io.Serializable;

/**
 * This is an object that contains data related to the cms_vote_item table. Do not modify this class
 * because it will be overwritten if the configuration file related to this class is modified.
 * 
 * @hibernate.class table="cms_vote_item"
 */
public abstract class BaseVoteItem implements Serializable {

	private static final long	serialVersionUID	= -4258811912451128276L;
	public static String		REF					= "VoteItem";
	public static String		PROP_TOPIC			= "topic";
	public static String		PROP_PRIORITY		= "priority";
	public static String		PROP_TITLE			= "title";
	public static String		PROP_VOTE_COUNT		= "voteCount";
	public static String		PROP_ID				= "id";

	// constructors
	public BaseVoteItem() {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseVoteItem(java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseVoteItem(java.lang.Integer id, com.cc.cms.entity.assist.VoteTopic topic, java.lang.String title, java.lang.Integer voteCount,
			java.lang.Integer priority) {
		this.setId(id);
		this.setTopic(topic);
		this.setTitle(title);
		this.setVoteCount(voteCount);
		this.setPriority(priority);
		initialize();
	}

	protected void initialize() {}

	private int									hashCode	= Integer.MIN_VALUE;
	// primary key
	private java.lang.Integer					id;
	// fields
	private java.lang.String					title;
	private java.lang.Integer					voteCount;
	private java.lang.Integer					priority;
	// many to one
	private com.cc.cms.entity.assist.VoteTopic	topic;

	/**
	 * Return the unique identifier of this class
	 * 
	 * @hibernate.id generator-class="identity" column="voteitem_id"
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
	 * Return the value associated with the column: title
	 */
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

	/**
	 * Return the value associated with the column: vote_count
	 */
	public java.lang.Integer getVoteCount() {
		return voteCount;
	}

	/**
	 * Set the value related to the column: vote_count
	 * 
	 * @param voteCount
	 *            the vote_count value
	 */
	public void setVoteCount(java.lang.Integer voteCount) {
		this.voteCount = voteCount;
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
	 * Return the value associated with the column: votetopic_id
	 */
	public com.cc.cms.entity.assist.VoteTopic getTopic() {
		return topic;
	}

	/**
	 * Set the value related to the column: votetopic_id
	 * 
	 * @param topic
	 *            the votetopic_id value
	 */
	public void setTopic(com.cc.cms.entity.assist.VoteTopic topic) {
		this.topic = topic;
	}

	@Override
	public boolean equals(Object obj) {
		if (null == obj)
			return false;
		if (!(obj instanceof com.cc.cms.entity.assist.VoteItem))
			return false;
		else {
			com.cc.cms.entity.assist.VoteItem voteItem = (com.cc.cms.entity.assist.VoteItem) obj;
			if (null == this.getId() || null == voteItem.getId())
				return false;
			else
				return (this.getId().equals(voteItem.getId()));
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
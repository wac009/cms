package com.cc.cms.entity.assist.base;

import java.io.Serializable;

/**
 * This is an object that contains data related to the cms_message table. Do not modify this class
 * because it will be overwritten if the configuration file related to this class is modified.
 * 
 * @hibernate.class table="cms_message"
 */
public abstract class BaseMessage implements Serializable {
	private static final long	serialVersionUID		= -7273115516171857961L;
	public static String		REF						= "Message";
	public static String		PROP_MSG_STATUS			= "msgStatus";
	public static String		PROP_SITE				= "site";
	public static String		PROP_MSG_SEND_USER		= "msgSendUser";
	public static String		PROP_MSG_CONTENT		= "msgContent";
	public static String		PROP_MSG_BOX			= "msgBox";
	public static String		PROP_SEND_TIME			= "sendTime";
	public static String		PROP_ID					= "id";
	public static String		PROP_MSG_RECEIVER_USER	= "msgReceiverUser";
	public static String		PROP_MSG_TITLE			= "msgTitle";

	// constructors
	public BaseMessage() {
		initialize();
	}
	/**
	 * Constructor for primary key
	 */
	public BaseMessage(java.lang.Integer id) {
		this.setId(id);
		initialize();
	}
	/**
	 * Constructor for required fields
	 */
	public BaseMessage(java.lang.Integer id, com.cc.cms.entity.main.User msgReceiverUser, com.cc.cms.entity.main.User msgSendUser,
			com.cc.cms.entity.main.Site site, java.lang.String msgTitle, java.lang.Boolean msgStatus, java.lang.Integer msgBox) {
		this.setId(id);
		this.setMsgReceiverUser(msgReceiverUser);
		this.setMsgSendUser(msgSendUser);
		this.setSite(site);
		this.setMsgTitle(msgTitle);
		this.setMsgStatus(msgStatus);
		this.setMsgBox(msgBox);
		initialize();
	}
	protected void initialize() {}

	private int															hashCode	= Integer.MIN_VALUE;
	// primary key
	private java.lang.Integer											id;
	// fields
	private java.lang.String											msgTitle;
	private java.lang.String											msgContent;
	private java.util.Date												sendTime;
	private java.lang.Boolean											msgStatus;
	private java.lang.Integer											msgBox;
	// many to one
	private com.cc.cms.entity.main.User								msgReceiverUser;
	private com.cc.cms.entity.main.User								msgSendUser;
	private com.cc.cms.entity.main.Site								site;
	// collections
	private java.util.Set<com.cc.cms.entity.assist.ReceiverMessage>	receiverMsgs;

	/**
	 * Return the unique identifier of this class
	 * 
	 * @hibernate.id generator-class="identity" column="msg_id"
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
	 * Return the value associated with the column: msg_title
	 */
	public java.lang.String getMsgTitle() {
		return msgTitle;
	}
	/**
	 * Set the value related to the column: msg_title
	 * 
	 * @param msgTitle
	 *            the msg_title value
	 */
	public void setMsgTitle(java.lang.String msgTitle) {
		this.msgTitle = msgTitle;
	}
	/**
	 * Return the value associated with the column: msg_content
	 */
	public java.lang.String getMsgContent() {
		return msgContent;
	}
	/**
	 * Set the value related to the column: msg_content
	 * 
	 * @param msgContent
	 *            the msg_content value
	 */
	public void setMsgContent(java.lang.String msgContent) {
		this.msgContent = msgContent;
	}
	/**
	 * Return the value associated with the column: send_time
	 */
	public java.util.Date getSendTime() {
		return sendTime;
	}
	/**
	 * Set the value related to the column: send_time
	 * 
	 * @param sendTime
	 *            the send_time value
	 */
	public void setSendTime(java.util.Date sendTime) {
		this.sendTime = sendTime;
	}
	/**
	 * Return the value associated with the column: msg_status
	 */
	public java.lang.Boolean getMsgStatus() {
		return msgStatus;
	}
	/**
	 * Set the value related to the column: msg_status
	 * 
	 * @param msgStatus
	 *            the msg_status value
	 */
	public void setMsgStatus(java.lang.Boolean msgStatus) {
		this.msgStatus = msgStatus;
	}
	/**
	 * Return the value associated with the column: msg_box
	 */
	public java.lang.Integer getMsgBox() {
		return msgBox;
	}
	/**
	 * Set the value related to the column: msg_box
	 * 
	 * @param msgBox
	 *            the msg_box value
	 */
	public void setMsgBox(java.lang.Integer msgBox) {
		this.msgBox = msgBox;
	}
	/**
	 * Return the value associated with the column: msg_receiver_user
	 */
	public com.cc.cms.entity.main.User getMsgReceiverUser() {
		return msgReceiverUser;
	}
	/**
	 * Set the value related to the column: msg_receiver_user
	 * 
	 * @param msgReceiverUser
	 *            the msg_receiver_user value
	 */
	public void setMsgReceiverUser(com.cc.cms.entity.main.User msgReceiverUser) {
		this.msgReceiverUser = msgReceiverUser;
	}
	/**
	 * Return the value associated with the column: msg_send_user
	 */
	public com.cc.cms.entity.main.User getMsgSendUser() {
		return msgSendUser;
	}
	/**
	 * Set the value related to the column: msg_send_user
	 * 
	 * @param msgSendUser
	 *            the msg_send_user value
	 */
	public void setMsgSendUser(com.cc.cms.entity.main.User msgSendUser) {
		this.msgSendUser = msgSendUser;
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
	public java.util.Set<com.cc.cms.entity.assist.ReceiverMessage> getReceiverMsgs() {
		return receiverMsgs;
	}
	public void setReceiverMsgs(java.util.Set<com.cc.cms.entity.assist.ReceiverMessage> receiverMsgs) {
		this.receiverMsgs = receiverMsgs;
	}
	@Override
	public boolean equals(Object obj) {
		if (null == obj)
			return false;
		if (!(obj instanceof com.cc.cms.entity.assist.Message))
			return false;
		else {
			com.cc.cms.entity.assist.Message message = (com.cc.cms.entity.assist.Message) obj;
			if (null == this.getId() || null == message.getId())
				return false;
			else
				return (this.getId().equals(message.getId()));
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
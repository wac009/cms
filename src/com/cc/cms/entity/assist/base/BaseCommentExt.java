package com.cc.cms.entity.assist.base;

import java.io.Serializable;

/**
 * This is an object that contains data related to the cms_comment_ext table. Do not modify this
 * class because it will be overwritten if the configuration file related to this class is modified.
 * 
 * @hibernate.class table="cms_comment_ext"
 */
public abstract class BaseCommentExt implements Serializable {
	private static final long	serialVersionUID	= 8685982596638859106L;
	public static String		REF					= "CommentExt";
	public static String		PROP_COMMENT		= "comment";
	public static String		PROP_IP				= "ip";
	public static String		PROP_TEXT			= "text";
	public static String		PROP_REPLY			= "reply";
	public static String		PROP_ID				= "id";

	// constructors
	public BaseCommentExt() {
		initialize();
	}
	/**
	 * Constructor for primary key
	 */
	public BaseCommentExt(java.lang.Integer id) {
		this.setId(id);
		initialize();
	}
	protected void initialize() {}

	private int									hashCode	= Integer.MIN_VALUE;
	// primary key
	private java.lang.Integer					id;
	// fields
	private java.lang.String					ip;
	private java.lang.String					text;
	private java.lang.String					reply;
	// one to one
	private com.cc.cms.entity.assist.Comment	comment;

	/**
	 * Return the unique identifier of this class
	 * 
	 * @hibernate.id generator-class="foreign" column="comment_id"
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
	 * Return the value associated with the column: ip
	 */
	public java.lang.String getIp() {
		return ip;
	}
	/**
	 * Set the value related to the column: ip
	 * 
	 * @param ip
	 *            the ip value
	 */
	public void setIp(java.lang.String ip) {
		this.ip = ip;
	}
	/**
	 * Return the value associated with the column: text
	 */
	public java.lang.String getText() {
		return text;
	}
	/**
	 * Set the value related to the column: text
	 * 
	 * @param text
	 *            the text value
	 */
	public void setText(java.lang.String text) {
		this.text = text;
	}
	/**
	 * Return the value associated with the column: reply
	 */
	public java.lang.String getReply() {
		return reply;
	}
	/**
	 * Set the value related to the column: reply
	 * 
	 * @param reply
	 *            the reply value
	 */
	public void setReply(java.lang.String reply) {
		this.reply = reply;
	}
	/**
	 * Return the value associated with the column: comment
	 */
	public com.cc.cms.entity.assist.Comment getComment() {
		return comment;
	}
	/**
	 * Set the value related to the column: comment
	 * 
	 * @param comment
	 *            the comment value
	 */
	public void setComment(com.cc.cms.entity.assist.Comment comment) {
		this.comment = comment;
	}
	@Override
	public boolean equals(Object obj) {
		if (null == obj)
			return false;
		if (!(obj instanceof com.cc.cms.entity.assist.CommentExt))
			return false;
		else {
			com.cc.cms.entity.assist.CommentExt commentExt = (com.cc.cms.entity.assist.CommentExt) obj;
			if (null == this.getId() || null == commentExt.getId())
				return false;
			else
				return (this.getId().equals(commentExt.getId()));
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
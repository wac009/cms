package com.cc.cms.entity.assist;

import java.sql.Timestamp;

import com.cc.cms.entity.assist.base.*;
import com.cc.common.util.StrUtils;

public class Guestbook extends BaseGuestbook {
	private static final long serialVersionUID = 1L;

	public String getTitleHtml() {
		return StrUtils.txt2htm(getTitle());
	}

	public String getContentHtml() {
		return StrUtils.txt2htm(getContent());
	}

	public String getReplyHtml() {
		return StrUtils.txt2htm(getReply());
	}

	public String getTitle() {
		GuestbookExt ext = getExt();
		if (ext != null) {
			return ext.getTitle();
		} else {
			return null;
		}
	}

	public String getContent() {
		GuestbookExt ext = getExt();
		if (ext != null) {
			return ext.getContent();
		} else {
			return null;
		}
	}

	public String getReply() {
		GuestbookExt ext = getExt();
		if (ext != null) {
			return ext.getReply();
		} else {
			return null;
		}
	}

	public String getEmail() {
		GuestbookExt ext = getExt();
		if (ext != null) {
			return ext.getEmail();
		} else {
			return null;
		}
	}

	public String getPhone() {
		GuestbookExt ext = getExt();
		if (ext != null) {
			return ext.getPhone();
		} else {
			return null;
		}
	}

	public String getQq() {
		GuestbookExt ext = getExt();
		if (ext != null) {
			return ext.getQq();
		} else {
			return null;
		}
	}

	public void init() {
		if (getChecked() == null) {
			setChecked(false);
		}
		if (getRecommend() == null) {
			setRecommend(false);
		}
		if (getCreateTime() == null) {
			setCreateTime(new Timestamp(System.currentTimeMillis()));
		}
	}

	/* [CONSTRUCTOR MARKER BEGIN] */
	public Guestbook () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public Guestbook (java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public Guestbook (
		java.lang.Integer id,
		com.cc.cms.entity.main.Site site,
		com.cc.cms.entity.assist.GuestbookCtg ctg,
		java.lang.String ip,
		java.util.Date createTime,
		java.lang.Boolean checked,
		java.lang.Boolean recommend) {

		super (
			id,
			site,
			ctg,
			ip,
			createTime,
			checked,
			recommend);
	}

	/* [CONSTRUCTOR MARKER END] */

}
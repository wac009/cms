
package com.cc.cms.entity.assist;

import java.sql.Timestamp;

import com.cc.cms.entity.assist.base.*;
import com.cc.common.util.StrUtils;

public class Comment extends BaseComment {
	private static final long	serialVersionUID	= 1L;

	public String getText() {
		return getCommentExt().getText();
	}

	public String getTextHtml() {
		return StrUtils.txt2htm(getText());
	}

	public String getReply() {
		return getCommentExt().getReply();
	}

	public String getReplayHtml() {
		return StrUtils.txt2htm(getReply());
	}

	public String getIp() {
		return getCommentExt().getIp();
	}

	public void init() {
		short zero = 0;
		if (getDowns() == null) {
			setDowns(zero);
		}
		if (getUps() == null) {
			setUps(zero);
		}
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
	public Comment() {
		super();
	}

	/** Constructor for primary key */
	public Comment(java.lang.Integer id) {
		super(id);
	}

	/** Constructor for required fields */
	public Comment(java.lang.Integer id, com.cc.cms.entity.main.Content content, com.cc.cms.entity.main.Site site, java.util.Date createTime,
			java.lang.Short ups, java.lang.Short downs, java.lang.Boolean recommend, java.lang.Boolean checked) {

		super(id, content, site, createTime, ups, downs, recommend, checked);
	}

	/* [CONSTRUCTOR MARKER END] */

}
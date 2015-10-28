package com.cc.cms.entity.assist;

import com.cc.cms.entity.assist.base.*;
import com.cc.common.util.StrUtils;



public class Message extends BaseMessage {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public Message () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public Message (java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public Message (
		java.lang.Integer id,
		com.cc.cms.entity.main.User msgReceiverUser,
		com.cc.cms.entity.main.User msgSendUser,
		com.cc.cms.entity.main.Site site,
		java.lang.String msgTitle,
		java.lang.Boolean msgStatus,
		java.lang.Integer msgBox) {

		super (
			id,
			msgReceiverUser,
			msgSendUser,
			site,
			msgTitle,
			msgStatus,
			msgBox);
	}
	public String getTitleHtml() {
		return StrUtils.txt2htm(getMsgTitle());
	}
	public String getContentHtml() {
		return StrUtils.txt2htm(getMsgContent());
	}


/*[CONSTRUCTOR MARKER END]*/


}
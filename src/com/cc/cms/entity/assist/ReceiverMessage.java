package com.cc.cms.entity.assist;

import com.cc.cms.entity.assist.base.*;
import com.cc.common.util.StrUtils;

public class ReceiverMessage extends BaseReceiverMessage {
	private static final long serialVersionUID = 1L;

	/* [CONSTRUCTOR MARKER BEGIN] */
	public ReceiverMessage() {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public ReceiverMessage(java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public ReceiverMessage(java.lang.Integer id,
			com.cc.cms.entity.main.User msgReceiverUser,
			com.cc.cms.entity.main.User msgSendUser,
			com.cc.cms.entity.main.Site site, java.lang.String msgTitle,
			java.lang.String msgContent, java.util.Date sendTime,
			boolean msgStatus, java.lang.Integer msgBox) {

		super(id, msgReceiverUser, msgSendUser, site, msgTitle, msgContent,
				sendTime, msgStatus, msgBox);
	}

	public ReceiverMessage(Message message) {
		super(message.getId(), message.getMsgReceiverUser(), message
				.getMsgSendUser(), message.getSite(), message.getMsgTitle(),
				message.getMsgContent(), message.getSendTime(), message
						.getMsgStatus(), message.getMsgBox());
	}
	public String getTitleHtml() {
		return StrUtils.txt2htm(getMsgTitle());
	}
	public String getContentHtml() {
		return StrUtils.txt2htm(getMsgContent());
	}

	/* [CONSTRUCTOR MARKER END] */

}

package com.cc.cms.entity.assist;

import com.cc.cms.entity.assist.base.*;

public class VoteTopic extends BaseVoteTopic {

	private static final long	serialVersionUID	= 1L;

	public void init() {
		if (getTotalCount() == null) {
			setTotalCount(0);
		}
		if (getMultiSelect() == null) {
			setMultiSelect(1);
		}
		if (getDef() == null) {
			setDef(false);
		}
		if (getDisabled() == null) {
			setDisabled(false);
		}
		if (getRestrictMember() == null) {
			setRestrictMember(false);
		}
		if (getRestrictIp() == null) {
			setRestrictIp(false);
		}
		if (getRestrictCookie() == null) {
			setRestrictCookie(true);
		}
	}

	/* [CONSTRUCTOR MARKER BEGIN] */
	public VoteTopic() {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public VoteTopic(java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public VoteTopic(java.lang.Integer id, com.cc.cms.entity.main.Site site, java.lang.String title, java.lang.Integer totalCount,
			java.lang.Integer multiSelect, java.lang.Boolean restrictMember, java.lang.Boolean restrictIp, java.lang.Boolean restrictCookie,
			java.lang.Boolean disabled, java.lang.Boolean def) {
		super(id, site, title, totalCount, multiSelect, restrictMember, restrictIp, restrictCookie, disabled, def);
	}
	/* [CONSTRUCTOR MARKER END] */
}
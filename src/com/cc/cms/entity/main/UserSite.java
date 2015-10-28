
package com.cc.cms.entity.main;

import com.cc.cms.entity.main.base.*;

public class UserSite extends BaseUserSite {

	private static final long	serialVersionUID	= 1L;

	/* [CONSTRUCTOR MARKER BEGIN] */
	public UserSite() {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public UserSite(java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public UserSite(java.lang.Integer id, com.cc.cms.entity.main.User user, com.cc.cms.entity.main.Site site, java.lang.Byte checkStep,
			java.lang.Boolean allChannel) {
		super(id, user, site, checkStep, allChannel);
	}

	public void init() {
		if (getCheckStep() == null) {
			setCheckStep(new Byte("0"));
		}
		if (getAllChannel() == null) {
			setAllChannel(false);
		}
	}
	/* [CONSTRUCTOR MARKER END] */
}
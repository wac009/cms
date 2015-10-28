package com.cc.cms.entity.assist;

import org.apache.commons.lang.StringUtils;

import com.cc.cms.entity.assist.base.*;

public class Friendlink extends BaseFriendlink {
	private static final long serialVersionUID = 1L;

	public void init() {
		if (getPriority() == null) {
			setPriority(10);
		}
		if (getViews() == null) {
			setViews(0);
		}
		if (getEnabled() == null) {
			setEnabled(true);
		}
		blankToNull();
	}

	public void blankToNull() {
		if (StringUtils.isBlank(getLogo())) {
			setLogo(null);
		}
	}

	/* [CONSTRUCTOR MARKER BEGIN] */
	public Friendlink() {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public Friendlink(java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public Friendlink(java.lang.Integer id,
			com.cc.cms.entity.assist.FriendlinkCtg category,
			com.cc.cms.entity.main.Site site, java.lang.String name,
			java.lang.String domain, java.lang.Integer views,
			java.lang.Integer priority, java.lang.Boolean enabled) {

		super(id, category, site, name, domain, views, priority, enabled);
	}

	/* [CONSTRUCTOR MARKER END] */

}
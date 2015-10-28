package com.cc.cms.entity.assist;

import com.cc.cms.entity.assist.base.*;



public class FriendlinkCtg extends BaseFriendlinkCtg {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public FriendlinkCtg () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public FriendlinkCtg (java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public FriendlinkCtg (
		java.lang.Integer id,
		com.cc.cms.entity.main.Site site,
		java.lang.String name,
		java.lang.Integer priority) {

		super (
			id,
			site,
			name,
			priority);
	}

/*[CONSTRUCTOR MARKER END]*/


}
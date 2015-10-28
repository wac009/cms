package com.cc.cms.entity.assist;

import com.cc.cms.entity.assist.base.*;



public class GuestbookCtg extends BaseGuestbookCtg {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public GuestbookCtg () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public GuestbookCtg (java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public GuestbookCtg (
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
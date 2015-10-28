
package com.cc.cms.entity.main;

import com.cc.cms.entity.main.base.*;

public class Log extends BaseLog {
	private static final long	serialVersionUID	= 1L;
	public static final int		LOGIN_SUCCESS		= 1;
	public static final int		LOGIN_FAILURE		= 2;
	public static final int		OPERATING			= 3;

	/* [CONSTRUCTOR MARKER BEGIN] */
	public Log() {
		super();
	}

	/** Constructor for primary key */
	public Log(java.lang.Integer id) {
		super(id);
	}

	/** Constructor for required fields */
	public Log(java.lang.Integer id, java.lang.Integer category, java.util.Date time) {

		super(id, category, time);
	}

	/* [CONSTRUCTOR MARKER END] */

}
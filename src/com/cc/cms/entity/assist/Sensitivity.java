package com.cc.cms.entity.assist;

import com.cc.cms.entity.assist.base.*;



public class Sensitivity extends BaseSensitivity {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public Sensitivity () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public Sensitivity (java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public Sensitivity (
		java.lang.Integer id,
		java.lang.String search,
		java.lang.String replacement) {

		super (
			id,
			search,
			replacement);
	}

/*[CONSTRUCTOR MARKER END]*/


}
package com.cc.cms.entity.main;

import com.cc.cms.entity.main.base.*;



public class SiteModel extends BaseSiteModel {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public SiteModel () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public SiteModel (java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public SiteModel (
		java.lang.Integer id,
		java.lang.String field,
		java.lang.String label,
		java.lang.Integer priority) {

		super (
			id,
			field,
			label,
			priority);
	}

/*[CONSTRUCTOR MARKER END]*/


}
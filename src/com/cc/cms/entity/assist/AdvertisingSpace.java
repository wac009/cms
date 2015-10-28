package com.cc.cms.entity.assist;

import com.cc.cms.entity.assist.base.*;



public class AdvertisingSpace extends BaseAdvertisingSpace {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public AdvertisingSpace () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public AdvertisingSpace (java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public AdvertisingSpace (
		java.lang.Integer id,
		com.cc.cms.entity.main.Site site,
		java.lang.String name,
		java.lang.Boolean enabled) {

		super (
			id,
			site,
			name,
			enabled);
	}

/*[CONSTRUCTOR MARKER END]*/


}
package com.cc.cms.entity.assist;

import com.cc.cms.entity.assist.base.*;



public class SiteFlow extends BaseSiteFlow {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public SiteFlow () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public SiteFlow (java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public SiteFlow (
		java.lang.Integer id,
		com.cc.cms.entity.main.Site site,
		java.lang.String accessIp,
		java.lang.String accessDate,
		java.lang.String accessPage,
		java.lang.String sessionId) {

		super (
			id,
			site,
			accessIp,
			accessDate,
			accessPage,
			sessionId);
	}

/*[CONSTRUCTOR MARKER END]*/


}
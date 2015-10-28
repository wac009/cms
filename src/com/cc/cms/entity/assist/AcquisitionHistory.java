package com.cc.cms.entity.assist;

import com.cc.cms.entity.assist.base.*;



public class AcquisitionHistory extends BaseAcquisitionHistory {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public AcquisitionHistory () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public AcquisitionHistory (java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public AcquisitionHistory (
		java.lang.Integer id,
		java.lang.String channelUrl,
		java.lang.String contentUrl) {

		super (
			id,
			channelUrl,
			contentUrl);
	}

/*[CONSTRUCTOR MARKER END]*/


}
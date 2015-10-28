package com.cc.cms.entity.assist;

import com.cc.cms.entity.assist.base.*;



public class AcquisitionTemp extends BaseAcquisitionTemp {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public AcquisitionTemp () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public AcquisitionTemp (java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public AcquisitionTemp (
		java.lang.Integer id,
		java.lang.String channelUrl,
		java.lang.String contentUrl,
		java.lang.Integer percent,
		java.lang.String description,
		java.lang.Integer seq) {

		super (
			id,
			channelUrl,
			contentUrl,
			percent,
			description,
			seq);
	}

/*[CONSTRUCTOR MARKER END]*/


}
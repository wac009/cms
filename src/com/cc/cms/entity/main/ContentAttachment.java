package com.cc.cms.entity.main;

import com.cc.cms.entity.main.base.*;

public class ContentAttachment extends BaseAttachment {
	private static final long serialVersionUID = 1L;

	/* [CONSTRUCTOR MARKER BEGIN] */
	public ContentAttachment () {
		super();
	}

	/**
	 * Constructor for required fields
	 */
	public ContentAttachment (
		java.lang.String path,
		java.lang.String name,
		java.lang.Integer count) {

		super (
			path,
			name,
			count);
	}

	/* [CONSTRUCTOR MARKER END] */

}
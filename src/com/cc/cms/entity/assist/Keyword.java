package com.cc.cms.entity.assist;

import com.cc.cms.entity.assist.base.*;

public class Keyword extends BaseKeyword {
	private static final long serialVersionUID = 1L;

	public void init() {
		if (getDisabled() == null) {
			setDisabled(false);
		}
	}

	/* [CONSTRUCTOR MARKER BEGIN] */
	public Keyword () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public Keyword (java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public Keyword (
		java.lang.Integer id,
		java.lang.String name,
		java.lang.String url,
		java.lang.Boolean disabled) {

		super (
			id,
			name,
			url,
			disabled);
	}

	/* [CONSTRUCTOR MARKER END] */

}
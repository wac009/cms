
package com.cc.cms.entity.assist;

import com.cc.cms.entity.assist.base.*;

public class VoteRecord extends BaseVoteRecord {

	private static final long	serialVersionUID	= 1L;

	/* [CONSTRUCTOR MARKER BEGIN] */
	public VoteRecord() {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public VoteRecord(java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public VoteRecord(java.lang.Integer id, com.cc.cms.entity.assist.VoteTopic topic, java.util.Date time, java.lang.String ip, java.lang.String cookie) {
		super(id, topic, time, ip, cookie);
	}
	/* [CONSTRUCTOR MARKER END] */
}

package com.cc.cms.entity.assist;

import com.cc.cms.entity.assist.base.*;
import com.cc.common.orm.IPriorityInterface;

public class VoteItem extends BaseVoteItem implements IPriorityInterface {

	private static final long	serialVersionUID	= 1L;

	public int getPercent() {
		Integer totalCount = getTopic().getTotalCount();
		if (totalCount != null && totalCount != 0) {
			return (getVoteCount() * 100) / totalCount;
		} else {
			return 0;
		}
	}

	public void init() {
		if (getPriority() == null) {
			setPriority(10);
		}
		if (getVoteCount() == null) {
			setVoteCount(0);
		}
	}

	/* [CONSTRUCTOR MARKER BEGIN] */
	public VoteItem() {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public VoteItem(java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public VoteItem(java.lang.Integer id, com.cc.cms.entity.assist.VoteTopic topic, java.lang.String title, java.lang.Integer voteCount,
			java.lang.Integer priority) {
		super(id, topic, title, voteCount, priority);
	}
	/* [CONSTRUCTOR MARKER END] */
}
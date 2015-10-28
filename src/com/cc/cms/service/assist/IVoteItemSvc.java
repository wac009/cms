
package com.cc.cms.service.assist;

import java.util.Collection;

import com.cc.cms.entity.assist.VoteItem;
import com.cc.cms.entity.assist.VoteTopic;
import com.cc.cms.service.ICmsSvc;

public interface IVoteItemSvc extends ICmsSvc<VoteItem> {

	public Collection<VoteItem> save(Collection<VoteItem> items, VoteTopic topic);

	public Collection<VoteItem> update(Collection<VoteItem> items, VoteTopic topic);
}
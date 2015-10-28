/** @author wangcheng */

package com.cc.cms.dao.assist.impl;

import org.springframework.stereotype.Repository;
import com.cc.cms.dao.CmsDaoImpl;
import com.cc.cms.dao.assist.IVoteItemDao;
import com.cc.cms.entity.assist.VoteItem;

@Repository
public class VoteItemDaoImpl extends CmsDaoImpl<VoteItem> implements IVoteItemDao {}

package com.cc.cms.action.admin.assist;

import java.util.*;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;

import com.cc.cms.action.*;
import com.cc.cms.entity.assist.*;
import com.cc.cms.service.assist.*;

/** @author wangcheng */
@SuppressWarnings({ "rawtypes", "serial" })
@Scope("prototype")
@Controller("web.action.admin.voteResultAct")
public class VoteResultAct extends CmsAct {

	private static final Logger	log			= LoggerFactory.getLogger(VoteResultAct.class);
	@Autowired
	private IVoteTopicSvc		voteTopicSvc;
	private VoteTopic			voteTopic;
	private Integer				topicId;
	/** 查询条件 */
	private List<String>		property	= new ArrayList<String>();
	private List<Object>		value		= new ArrayList<Object>();

	private void initList() {
		property.add("site.id");
		value.add(getWebId());
		pagination = voteTopicSvc.findByProperty(pageNo, getCookieCount(), property, value);
	}

	@Override
	public String list() {
		if (topicId == null) {
			initList();
			log.info("所有投票");
		} else {
			voteTopic = voteTopicSvc.findById(topicId);
		}
		return LIST;
	}

	public VoteTopic getVoteTopic() {
		return voteTopic;
	}

	public void setVoteTopic(VoteTopic voteTopic) {
		this.voteTopic = voteTopic;
	}

	public Integer getTopicId() {
		return topicId;
	}

	public void setTopicId(Integer topicId) {
		this.topicId = topicId;
	}
}

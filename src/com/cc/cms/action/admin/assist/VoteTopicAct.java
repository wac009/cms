
package com.cc.cms.action.admin.assist;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;

import com.cc.cms.action.CmsAct;
import com.cc.cms.entity.assist.VoteItem;
import com.cc.cms.entity.assist.VoteTopic;
import com.cc.cms.service.assist.IVoteTopicSvc;
import com.cc.common.orm.PriorityComparator;
import com.cc.common.util.ComUtils;

/** @author wangcheng */
@SuppressWarnings({ "rawtypes", "serial" })
@Scope("prototype")
@Controller("web.action.admin.voteAct")
public class VoteTopicAct extends CmsAct {

	private static final Logger	log			= LoggerFactory.getLogger(VoteTopicAct.class);
	@Autowired
	private IVoteTopicSvc		voteTopicSvc;
	private VoteTopic			voteTopic;
	private List<VoteItem>		voteItems;
	private String				startTime;
	private String				endTime;
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
		initList();
		return LIST;
	}

	public String save() {
		handleTime();
		Set<VoteItem> items = notEmptyItems();
		voteTopicSvc.saveTopic(voteTopic,items);
		addActionMessage("添加成功");
		return list();
	}

	private void initEdit() {
		voteTopic = voteTopicSvc.findById(id);
		setStartTime(ComUtils.formatDate(voteTopic.getStartTime(), 1));
		setEndTime(ComUtils.formatDate(voteTopic.getEndTime(), 1));
	}

	@Override
	public String edit() {
		initEdit();
		return EDIT;
	}

	public String update() {
		handleTime();
		Set<VoteItem> items = notEmptyItems();
		voteTopicSvc.updateTopic(voteTopic, items);
		return list();
	}

	public String delete() {
		try {
			for (VoteTopic topic : voteTopicSvc.deleteById(ids)) {
				log.info("删除投票成功:{}", topic.getTitle());
			}
			addActionMessage("删除成功");
		} catch (DataIntegrityViolationException e) {
			addActionError("记录已被引用，不能删除!");
			log.error("删除失败,记录被引用");
		}
		return list();
	}

	public String disable() {
		voteTopic = voteTopicSvc.findById(id);
		voteTopic.setDisabled(true);
		voteTopicSvc.update(voteTopic);
		addActionMessage("禁用成功");
		return list();
	}

	public String enable() {
		voteTopic = voteTopicSvc.findById(id);
		voteTopic.setDisabled(false);
		voteTopicSvc.update(voteTopic);
		addActionMessage("启用成功");
		return list();
	}

	private void handleTime() {
		// 如果没有输入发布时间，则取系统时间；
		if (!ComUtils.nullOrBlank(startTime)) {
			voteTopic.setStartTime(ComUtils.parseString2Datetime(startTime));
		}
		if (!ComUtils.nullOrBlank(endTime)) {
			voteTopic.setEndTime(ComUtils.parseString2Datetime(endTime));
		}
	}

	private boolean vldExist(Integer id) {
		VoteTopic entity = voteTopicSvc.findById(id);
		if (entity == null) {
			addActionError("数据不存在：" + id);
			return true;
		}
		return false;
	}

	public boolean validateDelete() {
		if (hasErrors()) {
			initList();
			return true;
		}
		if (vldBatch()) {
			log.error("删除ID不能为空");
			initList();
			return true;
		}
		for (Integer cid : ids) {
			if (vldExist(cid)) {
				initList();
				return true;
			}
		}
		return false;
	}

	public boolean validateSave() {
		if (hasErrors()) {
			log.error("添加时出现action/field错误");
			addActionError("添加时出现错误");
			return true;
		}
		if (hasErrors()) {
			return true;
		}
		return false;
	}

	public boolean validateUpdate() {
		if (hasErrors()) {
			log.error("修改时出现action/field错误");
			addActionError("修改时出现错误");
			return true;
		}
		return false;
	}

	/**
	 * 去除name、id为空的对象，并将id为空的对象返回
	 */
	private Set<VoteItem> notEmptyItems() {
		Set<VoteItem> items = new TreeSet<VoteItem>(new PriorityComparator());
		if (voteItems == null) {
			log.error("投票项不能为空！");
			addActionError("投票项不能为空！");
		}
		// 去除标题为空的投票选项
		for (VoteItem it : voteItems) {
			if (it != null && !StringUtils.isBlank(it.getTitle())) {
				it.setTopic(voteTopic);
				items.add(it);
			}
		}
		if (items.size() <= 0) {
			log.error("投票项不能为空！");
			addActionError("投票项不能为空！");
		}
		return items;
	}

	public VoteTopic getVoteTopic() {
		return voteTopic;
	}

	public void setVoteTopic(VoteTopic voteTopic) {
		this.voteTopic = voteTopic;
	}

	public List<VoteItem> getVoteItems() {
		return voteItems;
	}

	public void setVoteItems(List<VoteItem> voteItems) {
		this.voteItems = voteItems;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
}

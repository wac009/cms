
package com.cc.cms.action.admin.main;

import java.util.List;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;

import com.cc.cms.action.CmsAct;
import com.cc.cms.entity.main.Channel;
import com.cc.cms.entity.main.Template;
import com.cc.cms.entity.main.Topic;
import com.cc.cms.service.main.IChannelSvc;
import com.cc.cms.service.main.ITemplateSvc;
import com.cc.cms.service.main.ITopicSvc;
import com.cc.common.orm.hibernate3.OrderBy;
import com.cc.common.util.SelectTreeUtils;

/** @author wangcheng */
@SuppressWarnings({ "unchecked", "rawtypes" })
@Scope("prototype")
@Controller("web.action.admin.topicAct")
public class TopicAct extends CmsAct {

	private static final long	serialVersionUID	= -8222140231052317692L;
	private static final Logger	log					= LoggerFactory.getLogger(TopicAct.class);
	@Autowired
	private ITopicSvc			topicSvc;
	@Autowired
	private ITemplateSvc		templateSvc;
	@Autowired
	private IChannelSvc			channelSvc;
	private List<Template>		templateList;
	private List<Channel>		channelList;
	private Topic				topic;

	@Override
	public String list() {
		pagination = topicSvc.findAll(pageNo, getCookieCount(), OrderBy.desc("priority"));
		return LIST;
	}

	@Override
	public String add() {
		initTemplateList();
		initChannelList();
		addUploadRule();
		return ADD;
	}

	public String save() {
		topic.setSite(getWeb());
		topic.setUser(getUser());
		topicSvc.save(topic);
		return list();
	}

	@Override
	public String edit() {
		initTemplateList();
		initChannelList();
		addUploadRule();
		topic = topicSvc.findById(id);
		return EDIT;
	}

	public String update() {
		topicSvc.updateDefault(topic);
		return list();  
	}
	
	public String enable(){
		topic = topicSvc.findById(id);
		topic.setDisabled(false);
		topicSvc.update(topic);
		addActionMessage("成功启用");
		return list();
	}
	public String disable(){
		topic = topicSvc.findById(id);
		topic.setDisabled(true);
		topicSvc.update(topic);
		addActionMessage("成功禁用");
		return list();
	}

	public String delete() {
		try {
			for (Topic bean : topicSvc.deleteById(ids)) {
				log.info("删除成功:{}", bean.getName());
			}
			addActionMessage("成功删除 ");
		} catch (DataIntegrityViolationException e) {
			addActionError("记录已被引用，不能删除!");
			log.error("删除败,记录被引用");
		}
		setId(null);
		return list();
	}

	public boolean validateDelete() {
		if (vldBatch()) {
			log.error("删除ID不能为空");
			addActionMessage("删除ID不能为空");
			return true;
		}
		return false;
	}

	private void initTemplateList() {
		templateList = templateSvc.findAll();
	}

	private void initChannelList() {
		channelList = SelectTreeUtils.webTree(SelectTreeUtils.handleTreeChild(channelSvc.getTopListByRigth(getAdminId(), getWebId(), false)));
		if (channelList == null || channelList.size() == 0) {
			Channel root = new Channel();
			root.setSelectTree("栏目列表为空");
			channelList.add(0, root);
		} else if (channelList.size() > 1) {
			Channel root = new Channel();
			root.setSelectTree("请选择栏目");
			channelList.add(0, root);
		}
	}

	/** 排序 是否可向上移动 */
	public boolean isUp(Topic bean) {
		return topicSvc.isUp(bean);
	}

	public boolean isDown(Topic bean) {
		return topicSvc.isDown(bean);
	}

	public String up() {
		topicSvc.up(id);
		addActionMessage("排序成功");
		setId(null);
		return list();
	}

	public boolean validateUp() {
		if (hasErrors()) {
			log.error("发生action/field错误");
			addActionError("发生错误");
			return true;
		}
		if (!isUp(topicSvc.findById(id))) {
			addActionError("不能向上移动");
			return true;
		}
		return false;
	}

	public String down() {
		topicSvc.down(id);
		addActionMessage("排序成功");
		setId(null);
		return list();
	}

	public boolean validateDown() {
		if (hasErrors()) {
			log.error("发生action/field错误");
			addActionError("发生错误");
			return true;
		}
		if (!isDown(topicSvc.findById(id))) {
			addActionError("不能向下移动");
			return true;
		}
		return false;
	}

	public List<Template> getTemplateList() {
		return templateList;
	}

	public void setTemplateList(List<Template> templateList) {
		this.templateList = templateList;
	}

	public List<Channel> getChannelList() {
		return channelList;
	}

	public void setChannelList(List<Channel> channelList) {
		this.channelList = channelList;
	}

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}
}

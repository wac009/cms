
package com.cc.cms.action.admin.main;

import java.io.*;
import java.util.*;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;

import com.cc.cms.action.*;
import com.cc.cms.entity.main.*;
import com.cc.cms.service.main.*;
import com.cc.cms.staticpage.service.*;
import com.cc.common.util.*;

/** @author wangcheng */
@SuppressWarnings({ "unchecked", "rawtypes" })
@Scope("prototype")
@Controller("web.action.admin.contentAct")
public class ContentAct extends CmsAct {

	private static final long	serialVersionUID	= 1965970090563134944L;
	public static final Logger	log					= LoggerFactory.getLogger(ContentAct.class);
	@Autowired
	private IContentSvc			contentSvc;
	@Autowired
	private IChannelSvc			channelSvc;
	@Autowired
	private IContentTypeSvc		contentTypeSvc;
	@Autowired
	private IStaticPageSvc		staticSvc;
	@Autowired
	private IGroupSvc			groupSvc;
	@Autowired
	private ITopicSvc			topicSvc;
	@Autowired
	private IUserSvc			userSvc;
	private Content				content;
	private List<Channel>		channelList;
	private List<ContentType>	contentTypeList;
	private List<ContentType>	queryTypeList;
	private List<Topic>			topicList;
	private List<Topic>			topics;
	private List<Integer>		channelIds;
	private List<Group>			groups;
	private List<Group>			viewGroups;
	private List<User>			userList;
	private Channel				channelRoot;
	private FileWrap			tplFileRoot;
	private int					topLevel;
	private Integer				typeId;
	private String				releaseTime;
	private String				tagStr;
	private String[]			picPaths;
	private String[]			picDescs;
	private String[]			attachmentPaths;
	private String[]			attachmentNames;
	private String[]			attachmentFilenames;
	/** 查询条件 */
	private List<String>		property			= new ArrayList<String>();
	private List<String>		ops					= new ArrayList<String>();
	private List<Object>		value				= new ArrayList<Object>();
	private String				queryTitle;
	private String				queryStatus;
	private Integer				queryUser;
	private Integer				queryType;
	private Integer				queryChannel;
	private Boolean				selfChannel;
	private int					queryOrderBy;

	private void initList() {
		// 按title查询
		property.add("contentExt.title");
		ops.add("like");
		value.add(queryTitle);
		// 只管理自己数据的管理
		if (getUser().getSelfAdmin()) {
			property.add("user.id");
			ops.add("=");
			value.add(getAdminId());
		}
		// 按用户查询
		property.add("user.id");
		ops.add("=");
		value.add(queryUser);
		// 只取当前站点信息
		property.add("site.id");
		ops.add("=");
		value.add(getWebId());
		// 按类型查
		property.add("contentType.id");
		ops.add("=");
		value.add(queryType);
		// 按状态查询
		property.add("status");
		ops.add("=");
		if (queryStatus != null && !"all".equals(queryStatus)) {
			value.add(Byte.parseByte(queryStatus));
		} else {
			byte btwo = 2;
			value.add(btwo);
			setQueryStatus("2");
		}
		initQueryTypeList();
		initChannelList();
		initUserList();
		pagination = contentSvc.getRightContent(pageNo, getCookieCount(), queryChannel, selfChannel, property, ops, value, queryOrderBy);
	}

	@Override
	public String list() {
		initList();
		return LIST;
	}

	public void initAdd() {
		initChnlRoot();
		initChannelList();
		initContentTypeList();
		initTpl();
		initGroup();
		initTopicList();
		addUploadRule();
	}

	@Override
	public String add() {
		initAdd();
		return ADD;
	}

	public String save() {
		String[] tagArr = StrUtils.splitAndTrim(tagStr, ",", ",");
		handleReleaseTime();
		content.setSite(getWeb());
		contentSvc.save(content, content.getContentExt(), content.getContentTxt(), channelIds, topics, viewGroups, tagArr, attachmentPaths, attachmentNames, attachmentFilenames, picPaths, picDescs, getUser(), false);
		addActionMessage("添加成功！");
		return list();
	}

	public boolean validateSave() {
		if (!contentSvc.checkTitle(content.getContentExt().getTitle(), content)) {
			log.error("标题重复！");
			addActionError("标题重复！对不起，网站中已有相同标题的文章存在，请检查后重新提交。");
			initAdd();
			return true;
		}
		return false;
	}

	public void initEdit() {
		initChnlRoot();
		initChannelList();
		initContentTypeList();
		initTpl();
		initGroup();
		initTopicList();
		addUploadRule();
		content = contentSvc.findById(id);
		setReleaseTime(ComUtils.formatDate(content.getReleaseDate(), 1));
		topics = new ArrayList<Topic>(content.getTopics());
		viewGroups = new ArrayList<Group>(content.getViewGroups());
	}

	@Override
	public String edit() {
		initEdit();
		return EDIT;
	}

	public String update() {
		String[] tagArr = StrUtils.splitAndTrim(tagStr, ",", ",");
		handleReleaseTime();
		contentSvc.update(content, content.getContentExt(), content.getContentTxt(), channelIds, topics, viewGroups, tagArr, attachmentPaths, attachmentNames, attachmentFilenames, picPaths, picDescs, getUser(), false);
		addActionMessage("修改成功！");
		return list();
	}

	public String delete() {
		Content[] beans;
		if (getWeb().getResycleOn()) {
			beans = contentSvc.cycle(ids);
			for (Content content : beans) {
				log.info("delete to cycel, content id={}", content.getId());
			}
			addActionMessage("成功删除到回收站");
		} else {
			beans = contentSvc.deleteByIds(ids);
			for (Content content : beans) {
				log.info("delete content id={}", content.getId());
			}
			addActionMessage("成功删除");
		}
		return list();
	}

	public boolean validateDelete() {
		if (vldBatch()) {
			log.error("删除ID不能为空");
			addActionMessage("删除ID不能为空");
			initList();
			return true;
		}
		return false;
	}

	/** 排序 */
	public String up() {
		contentSvc.up(id);
		addActionMessage("排序成功");
		setId(null);
		return list();
	}

	public boolean validateUp() {
		if (hasErrors()) {
			log.error("发生action/field错误");
			initList();
		}
		return false;
	}

	public String down() {
		contentSvc.down(id);
		addActionMessage("排序成功");
		setId(null);
		return list();
	}

	private void handleReleaseTime() {
		// 如果没有输入发布时间，则取系统时间；
		if (ComUtils.nullOrBlank(releaseTime)) {
			content.getContentExt().setReleaseDate(ComUtils.now());
		} else {
			content.getContentExt().setReleaseDate(ComUtils.parseString2Datetime(releaseTime));
		}
	}

	public String changeType() {
		ContentType type = contentTypeSvc.findById(typeId);
		addJson("type", type);
		return JSON;
	}

	private void initUserList() {
		userList = userSvc.getAdminList(getWebId(), null, null, null);
		if (userList == null || userList.size() == 0) {
			User user = new User();
			user.setUsername("用户列表为空");
			userList.add(0, user);
		} else {
			User user = new User();
			user.setUsername("全部用户");
			userList.add(0, user);
		}
	}

	private void initChnlRoot() {
		channelRoot = new Channel();
		channelRoot.setName("根目录");
		channelRoot.setTreeLeaf(false);
		channelRoot.setChild(new LinkedHashSet<Channel>(channelSvc.getTopListByRigth(getAdminId(), getWebId(), false)));
	}

	private void initTopicList() {
		topicList = topicSvc.findAll();
	}

	private void initTpl() {
		String path = contextPvd.getAppRealPath(getWeb().getTplPath());
		tplFileRoot = new FileWrap(new File(path), path, new FileFilter() {

			@Override
			public boolean accept(File f) {
				if (f.getName().startsWith(".") || f.getName().startsWith("$")) {
					return false;
				} else {
					return true;
				}
			}
		});
	}

	private void initGroup() {
		groups = groupSvc.findAll();
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

	private void initContentTypeList() {
		contentTypeList = contentTypeSvc.getEnableList();
	}

	private void initQueryTypeList() {
		queryTypeList = contentTypeSvc.getEnableList();
		if (queryTypeList == null || queryTypeList.size() == 0) {
			ContentType root = new ContentType();
			root.setName("类型为空");
			queryTypeList.add(0, root);
		} else {
			ContentType root = new ContentType();
			root.setName("全部类型");
			queryTypeList.add(0, root);
		}
	}

	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}

	public List<Channel> getChannelList() {
		return channelList;
	}

	public void setChannelList(List<Channel> channelList) {
		this.channelList = channelList;
	}

	public List<ContentType> getContentTypeList() {
		return contentTypeList;
	}

	public void setContentTypeList(List<ContentType> contentTypeList) {
		this.contentTypeList = contentTypeList;
	}

	public List<Integer> getChannelIds() {
		return channelIds;
	}

	public void setChannelIds(List<Integer> channelIds) {
		this.channelIds = channelIds;
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public List<Topic> getTopicList() {
		return topicList;
	}

	public void setTopicList(List<Topic> topicList) {
		this.topicList = topicList;
	}

	public List<Group> getViewGroups() {
		return viewGroups;
	}

	public void setViewGroups(List<Group> viewGroups) {
		this.viewGroups = viewGroups;
	}

	public List<Topic> getTopics() {
		return topics;
	}

	public void setTopics(List<Topic> topics) {
		this.topics = topics;
	}

	public Channel getChannelRoot() {
		return channelRoot;
	}

	public void setChannelRoot(Channel channelRoot) {
		this.channelRoot = channelRoot;
	}

	public FileWrap getTplFileRoot() {
		return tplFileRoot;
	}

	public void setTplFileRoot(FileWrap tplFileRoot) {
		this.tplFileRoot = tplFileRoot;
	}

	public int getTopLevel() {
		return topLevel;
	}

	public void setTopLevel(int topLevel) {
		this.topLevel = topLevel;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public String getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(String releaseTime) {
		this.releaseTime = releaseTime;
	}

	public String getTagStr() {
		return tagStr;
	}

	public void setTagStr(String tagStr) {
		this.tagStr = tagStr;
	}

	public String[] getPicPaths() {
		return picPaths;
	}

	public void setPicPaths(String[] picPaths) {
		this.picPaths = picPaths;
	}

	public String[] getPicDescs() {
		return picDescs;
	}

	public void setPicDescs(String[] picDescs) {
		this.picDescs = picDescs;
	}

	public String[] getAttachmentPaths() {
		return attachmentPaths;
	}

	public void setAttachmentPaths(String[] attachmentPaths) {
		this.attachmentPaths = attachmentPaths;
	}

	public String[] getAttachmentNames() {
		return attachmentNames;
	}

	public void setAttachmentNames(String[] attachmentNames) {
		this.attachmentNames = attachmentNames;
	}

	public String[] getAttachmentFilenames() {
		return attachmentFilenames;
	}

	public void setAttachmentFilenames(String[] attachmentFilenames) {
		this.attachmentFilenames = attachmentFilenames;
	}

	public List<String> getProperty() {
		return property;
	}

	public void setProperty(List<String> property) {
		this.property = property;
	}

	public List<String> getOps() {
		return ops;
	}

	public void setOps(List<String> ops) {
		this.ops = ops;
	}

	public List<Object> getValue() {
		return value;
	}

	public void setValue(List<Object> value) {
		this.value = value;
	}

	public String getQueryTitle() {
		return queryTitle;
	}

	public void setQueryTitle(String queryTitle) {
		this.queryTitle = queryTitle;
	}

	public Integer getQueryType() {
		return queryType;
	}

	public void setQueryType(Integer queryType) {
		this.queryType = queryType;
	}

	public Integer getQueryChannel() {
		return queryChannel;
	}

	public void setQueryChannel(Integer queryChannel) {
		this.queryChannel = queryChannel;
	}

	public int getQueryOrderBy() {
		return queryOrderBy;
	}

	public void setQueryOrderBy(int queryOrderBy) {
		this.queryOrderBy = queryOrderBy;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public Integer getQueryUser() {
		return queryUser;
	}

	public void setQueryUser(Integer queryUser) {
		this.queryUser = queryUser;
	}

	public List<ContentType> getQueryTypeList() {
		return queryTypeList;
	}

	public void setQueryTypeList(List<ContentType> queryTypeList) {
		this.queryTypeList = queryTypeList;
	}

	public String getQueryStatus() {
		return queryStatus;
	}

	public void setQueryStatus(String queryStatus) {
		this.queryStatus = queryStatus;
	}

	public Boolean getSelfChannel() {
		return selfChannel;
	}

	public void setSelfChannel(Boolean selfChannel) {
		this.selfChannel = selfChannel;
	}
}

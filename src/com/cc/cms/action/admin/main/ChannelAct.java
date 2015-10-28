
package com.cc.cms.action.admin.main;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;

import com.cc.cms.action.CmsAct;
import com.cc.cms.entity.main.Channel;
import com.cc.cms.entity.main.Group;
import com.cc.cms.entity.main.Model;
import com.cc.cms.entity.main.User;
import com.cc.cms.service.main.IChannelSvc;
import com.cc.cms.service.main.IGroupSvc;
import com.cc.cms.service.main.IModelSvc;
import com.cc.cms.service.main.IUserSvc;
import com.cc.common.orm.ISelectTree;
import com.cc.common.util.FileWrap;
import com.cc.common.util.SelectTreeUtils;
import com.cc.common.web.RequestUtils;

/** @author wangcheng */
@SuppressWarnings({ "unchecked", "rawtypes" })
@Scope("prototype")
@Controller("web.action.admin.channelAct")
public class ChannelAct extends CmsAct {

	private static final long	serialVersionUID	= 7450112803799098236L;
	public static final Logger	log					= LoggerFactory.getLogger(ChannelAct.class);
	@Autowired
	private IChannelSvc			channelSvc;
	@Autowired
	private IModelSvc			modelSvc;
	@Autowired
	private IGroupSvc			groupSvc;
	@Autowired
	private IUserSvc			userSvc;
	private List<User>			userList;
	private List<User>			users;
	private List<Group>			groups;
	private List<Group>			viewGroups;
	private List<Group>			contriGroups;
	private List<Channel>		channelList;
	private Channel				channel;
	private Channel				parent;
	private List<Model>			models;
	private Model				model;
	private FileWrap			tplFileRoot;

	@Override
	public String left() {
		Channel root = new Channel();
		root.setName("根目录");
		root.setTreeLeaf(false);
		root.setChild(new LinkedHashSet<Channel>(channelSvc.getTopList(getWebId(), false)));
		setLeftMenu(root);
		setLeftMenu_url("channel_edit");
		setLeftMenu_durl("channel_list");
		return LEFT;
	}

	@Override
	public String list() {
		initList();
		return LIST;
	}

	@Override
	public String add() {
		initTpl();
		initParent();
		initModes();
		initGroup();
		initUser();
		initChannelList();
		addUploadRule();
		return ADD;
	}

	public String save() {
		channel.setSite(getWeb());
		channelSvc.save(channel, viewGroups, contriGroups, users);
		log.info("添加成功！");
		logSvc.operating("添加栏目", "添加栏目成功" + channel.getName(), getIp(), getUri(), getWeb(), getAdmin());
		addActionMessage("添加成功！");
		return list();
	}

	@Override
	public String edit() {
		initTpl();
		initGroup();
		initModes();
		initUser();
		initChannelList();
		addUploadRule();
		channel = channelSvc.findById(id);
		users = new ArrayList<User>(channel.getUsers());
		viewGroups = new ArrayList<Group>(channel.getViewGroups());
		contriGroups = new ArrayList<Group>(channel.getContriGroups());
		return EDIT;
	}

	public String update() {
		Map<String, String> attr = RequestUtils.getRequestMap(contextPvd.getRequest(), "attr_");
		channelSvc.update(channel, viewGroups, contriGroups, users, attr);
		log.info("修改成功！");
		logSvc.operating("修改栏目", "修改栏目成功" + channel.getName(), getIp(), getUri(), getWeb(), getAdmin());
		addActionMessage("修改成功！");
		setId(null);
		return list();
	}

	public String delete() {
		try {
			for (Channel channel : channelSvc.deleteById(ids)) {
				log.info("删除成功:{}", channel.getName());
				logOperating("删除栏目", "删除栏目成功" + channel.getName());
			}
			addActionMessage("成功删除 ");
		} catch (DataIntegrityViolationException e) {
			addActionError("记录已被引用，不能删除!");
			log.error("删除失败,记录被引用");
		}
		setId(null);
		return list();
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

	public boolean validateDelete() {
		if (vldBatch()) {
			log.error("删除ID不能为空");
			addActionMessage("删除ID不能为空");
			initList();
			return true;
		}
		return false;
	}

	public String disable() {
		channel = channelSvc.findById(id);
		channel.getExt().setDisabled(true);
		channelSvc.update(channel);
		addActionMessage("成功关闭栏目");
		setId(null);
		return list();
	}

	public String enable() {
		channel = channelSvc.findById(id);
		channel.getExt().setDisabled(false);
		channelSvc.update(channel);
		addActionMessage("成功开启栏目");
		setId(null);
		return list();
	}

	public String openDisplay() {
		channel = channelSvc.findById(id);
		channel.getExt().setDisplay(true);
		channelSvc.update(channel);
		addActionMessage("成功设置栏目显示");
		setId(null);
		return list();
	}

	public String closeDisplay() {
		channel = channelSvc.findById(id);
		channel.getExt().setDisplay(false);
		channelSvc.update(channel);
		addActionMessage("成功设置栏目不显示");
		setId(null);
		return list();
	}

	/**
	 * 排序 是否可向上移动
	 * 
	 * @param id
	 * @return
	 */
	public boolean isUp(Channel bean) {
		return channelSvc.isUp(bean);
	}

	public boolean isDown(Channel bean) {
		return channelSvc.isDown(bean);
	}

	public String up() {
		channelSvc.up(id);
		addActionMessage("排序成功");
		setId(null);
		return list();
	}

	public boolean validateUp() {
		if (hasErrors()) {
			log.error("发生action/field错误");
			initList();
			addActionError("发生错误");
			return true;
		}
		if (!isUp(channelSvc.findById(id))) {
			addActionError("不能向上移动");
			return true;
		}
		return false;
	}

	public String down() {
		channelSvc.down(id);
		addActionMessage("排序成功");
		setId(null);
		return list();
	}

	public boolean validateDown() {
		if (hasErrors()) {
			log.error("发生action/field错误");
			initList();
			addActionError("发生错误");
			return true;
		}
		if (!isDown(channelSvc.findById(id))) {
			addActionError("不能向下移动");
			return true;
		}
		return false;
	}

	private void initList() {
		if (id == null) {
			list = channelSvc.getTopList(getWebId(), false);
		} else {
			list = new ArrayList<ISelectTree>(channelSvc.findById(id).getChild());
		}
	}

	private void initModes() {
		if (id == null) {
			models = modelSvc.findAll();
		} else {
			model = modelSvc.findById(channelSvc.findById(id).getModel().getId());
		}
	}

	private void initParent() {
		if (id != null) {
			parent = channelSvc.findById(id);
		}
	}

	private void initGroup() {
		groups = groupSvc.findAll();
	}

	private void initUser() {
		if (parent != null) {
			userList = new ArrayList<User>(parent.getUsers());
		} else {
			userList = userSvc.getAdminList(getWebId(), null, null, null);
		}
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

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public Channel getParent() {
		return parent;
	}

	public void setParent(Channel parent) {
		this.parent = parent;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<Group> getViewGroups() {
		return viewGroups;
	}

	public void setViewGroups(List<Group> viewGroups) {
		this.viewGroups = viewGroups;
	}

	public List<Group> getContriGroups() {
		return contriGroups;
	}

	public void setContriGroups(List<Group> contriGroups) {
		this.contriGroups = contriGroups;
	}

	public List<Model> getModels() {
		return models;
	}

	public void setModels(List<Model> models) {
		this.models = models;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public FileWrap getTplFileRoot() {
		return tplFileRoot;
	}

	public void setTplFileRoot(FileWrap tplFileRoot) {
		this.tplFileRoot = tplFileRoot;
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public List<Channel> getChannelList() {
		return channelList;
	}

	public void setChannelList(List<Channel> channelList) {
		this.channelList = channelList;
	}
}

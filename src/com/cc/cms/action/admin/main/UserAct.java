
package com.cc.cms.action.admin.main;

import java.util.*;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.dao.*;
import org.springframework.stereotype.*;

import com.cc.cms.action.*;
import com.cc.cms.entity.main.*;
import com.cc.cms.entity.main.Role;
import com.cc.cms.service.main.*;
import com.cc.common.util.*;
import com.cc.core.entity.*;

/** @author wangcheng */
@SuppressWarnings({ "unchecked", "rawtypes" })
@Scope("prototype")
@Controller("web.action.admin.userAct")
public class UserAct extends CmsAct {

	private static final long	serialVersionUID	= 4123696011221231014L;
	private static final Logger	log					= LoggerFactory.getLogger(UserAct.class);
	@Autowired
	private IChannelSvc			channelSvc;
	@Autowired
	private IGroupSvc			groupSvc;
	private List<User>			userList;
	private List<Channel>		channelList;
	private List<Channel>		channels;
	private List<Role>			roleList			= new ArrayList<Role>(0);
	private List<Role>			roles;
	private List<Group>			groupList;
	private User				user;
	private UserExt				ext;
	private UnifiedUser			unifiedUser;
	private String				password;
	private Channel				channelRoot;
	private Boolean				allChannel;
	private Byte				checkStep;
	private Integer				groupId;
	private String				birthday;
	/** 查询条件 */
	private List<String>		property			= new ArrayList<String>();
	private List<String>		ops					= new ArrayList<String>();
	private List<Object>		value				= new ArrayList<Object>();
	private String				queryUsername;
	private String				queryName;
	private String				queryEmail;
	private String				queryTime;
	private String				queryTimeOps;
	private Integer				queryAtt;

	@Override
	public String list() {
		// 用户名查询
		property.add("username");
		ops.add("like");
		value.add(queryUsername);
		// 姓名
		property.add("ext.realname");
		ops.add("like");
		value.add(queryName);
		// Email查询
		property.add("email");
		ops.add("like");
		value.add(queryEmail);
		// 注册时间查询
		if (queryTime != null) {
			property.add("registerTime");
			ops.add(queryTimeOps);
			value.add(ComUtils.parseString2Datetime(queryTime));
		}
		// 属性查询
		if (queryAtt == null) {
			queryAtt = 0;
		}
		switch (queryAtt) {
			case 1:
				property.add("admin");
				ops.add("=");
				value.add(true);
				break;
			case 2:
				property.add("viewonlyAdmin");
				ops.add("=");
				value.add(true);
				break;
			case 3:
				property.add("selfAdmin");
				ops.add("=");
				value.add(true);
				break;
			case 4:
				property.add("disabled");
				ops.add("=");
				value.add(true);
				break;
			case 5:
				property.add("delete");
				ops.add("=");
				value.add(true);
				break;
			default:
				break;
		}
		if (isSearch(value)) {
			pagination = userSvc.findByProperty(pageNo, getCookieCount(), property, ops, value);
		} else {
			pagination = userSvc.findAll(pageNo, getCookieCount());
		}
		return LIST;
	}

	@Override
	public String add() {
		initChannelRoot();
		initRoleList();
		initGroupList();
		addUploadRule();
		return ADD;
	}

	public String save() {
		if (allChannel) {
			user.setChannels(null);
		} else if (channels != null && channels.size() > 0) {
			user.setChannels(new HashSet<Channel>(channels));
		}
		if (roles != null && roles.size() > 0) {
			user.setRoles(new HashSet<Role>(roles));
		}
		handleTime();
		userSvc.saveAdmin(user, password, getIp(), getWebId(), groupId, checkStep, allChannel);
		addActionMessage("添加成功");
		logOperating("添加用户", "添加用户成功" + user.getUsername());
		permissionSvc.loadAllToCache();
		return list();
	}

	@Override
	public String edit() {
		initChannelRoot();
		initRoleList();
		initGroupList();
		addUploadRule();
		user = userSvc.findById(id);
		roles = new ArrayList<Role>(user.getRoles());
		UserSite userSite = user.getUserSite(getWebId());
		if (userSite.getAllChannel()) {
			allChannel = true;
		} else {
			allChannel = false;
			channels = new ArrayList<Channel>(user.getChannels());
		}
		checkStep = userSite.getCheckStep();
		setBirthday(ComUtils.formatDate(user.getExt().getBirthday(), 2));
		groupId = user.getGroup().getId();
		return EDIT;
	}

	public String update() {
		if (channels != null && channels.size() > 0) {
			user.setChannels(new HashSet<Channel>(channels));
		}
		if (roles != null && roles.size() > 0) {
			user.setRoles(new HashSet<Role>(roles));
		}
		handleTime();
		if (allChannel == null) {
			allChannel = false;
		}
		userSvc.updateAdmin(user, password, getWebId(), groupId, checkStep, allChannel);
		addActionMessage("修改成功");
		logOperating("修改用户", "修改用户成功" + user.getUsername());
		permissionSvc.loadAllToCache();
		return list();
	}

	@SuppressWarnings("unused")
	public String delete() {
		vldBatch();
		try {
			for (User u : userSvc.deleteByIds(ids)) {
				log.info("删除成功");
			}
			addActionMessage("成功删除 ");
		} catch (DataIntegrityViolationException e) {
			addActionError("记录已被引用，不能删除!");
			log.error("删除失败,记录被引用");
		}
		setId(null);
		return list();
	}

	/** 处理时间 */
	private void handleTime() {
		if (!ComUtils.nullOrBlank(birthday)) {
			user.getExt().setBirthday(ComUtils.parseString2Date(birthday));
		}
	}

	/** 显示角色列表 */
	private void initRoleList() {
		Site site = getWeb();
		for (Role role : site.getRoles()) {
			roleList.add(role);
		}
	}

	private void initChannelRoot() {
		channelRoot = new Channel();
		channelRoot.setName("根目录");
		channelRoot.setTreeLeaf(false);
		channelRoot.setChild(new LinkedHashSet<Channel>(channelSvc.getTopList(getWebId(), false)));
	}

	private void initGroupList() {
		groupList = groupSvc.findAll();
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public List<Channel> getChannelList() {
		return channelList;
	}

	public void setChannelList(List<Channel> channelList) {
		this.channelList = channelList;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	@Override
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Channel getChannelRoot() {
		return channelRoot;
	}

	public void setChannelRoot(Channel channelRoot) {
		this.channelRoot = channelRoot;
	}

	public String getQueryUsername() {
		return queryUsername;
	}

	public void setQueryUsername(String queryUsername) {
		this.queryUsername = queryUsername;
	}

	public String getQueryEmail() {
		return queryEmail;
	}

	public void setQueryEmail(String queryEmail) {
		this.queryEmail = queryEmail;
	}

	public String getQueryTime() {
		return queryTime;
	}

	public void setQueryTime(String queryTime) {
		this.queryTime = queryTime;
	}

	public String getQueryTimeOps() {
		return queryTimeOps;
	}

	public void setQueryTimeOps(String queryTimeOps) {
		this.queryTimeOps = queryTimeOps;
	}

	public String getQueryName() {
		return queryName;
	}

	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}

	public Integer getQueryAtt() {
		return queryAtt;
	}

	public void setQueryAtt(Integer queryAtt) {
		this.queryAtt = queryAtt;
	}

	public List<Channel> getChannels() {
		return channels;
	}

	public void setChannels(List<Channel> channels) {
		this.channels = channels;
	}

	public UnifiedUser getUnifiedUser() {
		return unifiedUser;
	}

	public void setUnifiedUser(UnifiedUser unifiedUser) {
		this.unifiedUser = unifiedUser;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<Group> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<Group> groupList) {
		this.groupList = groupList;
	}

	public Boolean getAllChannel() {
		return allChannel;
	}

	public void setAllChannel(Boolean allChannel) {
		this.allChannel = allChannel;
	}

	public Byte getCheckStep() {
		return checkStep;
	}

	public void setCheckStep(Byte checkStep) {
		this.checkStep = checkStep;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public UserExt getExt() {
		return ext;
	}

	public void setExt(UserExt ext) {
		this.ext = ext;
	}
}

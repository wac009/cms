
package com.cc.cms.action.admin.assist;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;

import com.cc.cms.action.*;
import com.cc.cms.entity.assist.*;
import com.cc.cms.service.assist.*;
import com.cc.common.orm.hibernate3.*;

/**
 * @author wangcheng
 */
@SuppressWarnings({ "rawtypes", "serial" })
@Scope("prototype")
@Controller("web.action.admin.friendlinkAct")
public class FriendlinkAct extends CmsAct {

	@Autowired
	private IFriendlinkCtgSvc	friendlinkCtgSvc;
	@Autowired
	private IFriendlinkSvc		friendlinkSvc;
	private Friendlink			friendlink;
	private List<FriendlinkCtg>	ctgs;
	private Integer				queryCtgId;
	/** 查询条件 */
	private List<String>		property	= new ArrayList<String>();
	private List<Object>		value		= new ArrayList<Object>();

	@Override
	public String list() {
		property.add("site.id");
		value.add(getWebId());
		if (queryCtgId != null) {
			property.add("category.id");
			value.add(queryCtgId);
		}
		initCtgs();
		list = friendlinkSvc.findByProperty(property, value, OrderBy.asc("priority"));
		return LIST;
	}

	@Override
	public String add() {
		addUploadRule();
		initCtgs();
		return ADD;
	}

	public String save() {
		friendlink.setSite(getWeb());
		friendlinkSvc.save(friendlink, friendlink.getCategory().getId());
		return list();
	}

	@Override
	public String edit() {
		addUploadRule();
		initCtgs();
		friendlink = friendlinkSvc.findById(id);
		return EDIT;
	}

	public String update() {
		friendlinkSvc.updateDefault(friendlink);
		return list();
	}

	public String delete() {
		vldBatch();
		friendlinkSvc.deleteByIds(ids);
		return list();
	}

	public String disable() {
		friendlink = friendlinkSvc.findById(id);
		friendlink.setEnabled(false);
		friendlinkSvc.update(friendlink);
		addActionMessage("禁用成功");
		return list();
	}

	public String enable() {
		friendlink = friendlinkSvc.findById(id);
		friendlink.setEnabled(true);
		friendlinkSvc.update(friendlink);
		addActionMessage("启用成功");
		return list();
	}

	/**
	 * 排序 是否可向上移动
	 */
	public boolean isUp(Friendlink bean) {
		return friendlinkSvc.isUp(bean);
	}

	public boolean isDown(Friendlink bean) {
		return friendlinkSvc.isDown(bean);
	}

	public String up() {
		friendlinkSvc.up(id);
		addActionMessage("排序成功");
		setId(null);
		return list();
	}

	public String down() {
		friendlinkSvc.down(id);
		addActionMessage("排序成功");
		setId(null);
		return list();
	}

	private void initCtgs() {
		ctgs = friendlinkCtgSvc.findByProperty("site.id", getWebId(), OrderBy.desc("priority"));
	}

	public Friendlink getFriendlink() {
		return friendlink;
	}

	public void setFriendlink(Friendlink friendlink) {
		this.friendlink = friendlink;
	}

	public List<FriendlinkCtg> getCtgs() {
		return ctgs;
	}

	public void setCtgs(List<FriendlinkCtg> ctgs) {
		this.ctgs = ctgs;
	}

	public Integer getQueryCtgId() {
		return queryCtgId;
	}

	public void setQueryCtgId(Integer queryCtgId) {
		this.queryCtgId = queryCtgId;
	}
}

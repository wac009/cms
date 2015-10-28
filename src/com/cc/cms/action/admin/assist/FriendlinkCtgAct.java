
package com.cc.cms.action.admin.assist;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.cc.cms.action.CmsAct;
import com.cc.cms.entity.assist.FriendlinkCtg;
import com.cc.cms.service.assist.IFriendlinkCtgSvc;
import com.cc.common.orm.hibernate3.OrderBy;

/**
 * @author wangcheng
 */
@SuppressWarnings({ "rawtypes", "serial" })
@Scope("prototype")
@Controller("web.action.admin.friendlinkCtgAct")
public class FriendlinkCtgAct extends CmsAct {

	private static final Logger	log			= LoggerFactory.getLogger(FriendlinkCtgAct.class);
	@Autowired
	private IFriendlinkCtgSvc	friendlinkCtgSvc;
	private FriendlinkCtg		friendlinkCtg;
	private String				name;

	@Override
	public String list() {
		list = friendlinkCtgSvc.findByProperty("site.id", getWebId(), OrderBy.asc("priority"));
		return LIST;
	}

	public String save() {
		friendlinkCtg = new FriendlinkCtg();
		friendlinkCtg.setName(name);
		friendlinkCtg.setSite(getWeb());
		friendlinkCtgSvc.save(friendlinkCtg);
		return list();
	}

	@SuppressWarnings("unchecked")
	public String rename() {
		friendlinkCtg = friendlinkCtgSvc.findById(id);
		friendlinkCtg.setName(name);
		friendlinkCtg = friendlinkCtgSvc.update(friendlinkCtg);
		log.info("重命名成功");
		getJsonRoot().put("success", true);
		return JSON;
	}

	public String delete() {
		vldBatch();
		friendlinkCtgSvc.deleteByIds(ids);
		return list();
	}

	/**
	 * 排序 是否可向上移动
	 */
	public boolean isUp(FriendlinkCtg bean) {
		return friendlinkCtgSvc.isUp(bean);
	}

	public boolean isDown(FriendlinkCtg bean) {
		return friendlinkCtgSvc.isDown(bean);
	}

	public String up() {
		friendlinkCtgSvc.up(id);
		addActionMessage("排序成功");
		setId(null);
		return list();
	}

	public String down() {
		friendlinkCtgSvc.down(id);
		addActionMessage("排序成功");
		setId(null);
		return list();
	}

	public FriendlinkCtg getFriendlinkCtg() {
		return friendlinkCtg;
	}

	public void setFriendlinkCtg(FriendlinkCtg friendlinkCtg) {
		this.friendlinkCtg = friendlinkCtg;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

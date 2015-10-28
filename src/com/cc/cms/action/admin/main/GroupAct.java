
package com.cc.cms.action.admin.main;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;

import com.cc.cms.action.*;
import com.cc.cms.entity.main.*;
import com.cc.cms.service.main.*;

/** @author wangcheng */
@SuppressWarnings({ "rawtypes", "serial" })
@Scope("prototype")
@Controller("web.action.admin.groupAct")
public class GroupAct extends CmsAct {

	public static final Logger	log	= LoggerFactory.getLogger(GroupAct.class);
	@Autowired
	private IGroupSvc	groupSvc;
	private Group		group;

	@Override
	public String list() {
		list = groupSvc.findAll();
		return LIST;
	}

	@Override
	public String add() {
		return ADD;
	}

	public String save() {
		groupSvc.save(group);
		addActionMessage("添加成功");
		return list();
	}

	@Override
	public String edit() {
		group = groupSvc.findById(id);
		return EDIT;
	}

	public String update() {
		groupSvc.updateDefault(group);
		addActionMessage("修改成功");
		return list();
	}

	public String delete() {
		vldBatch();
		groupSvc.deleteById(ids);
		addActionMessage("删除成功");
		return list();
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}
}

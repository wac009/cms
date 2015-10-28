
package com.cc.cms.action.admin.main;

import java.util.HashSet;
import java.util.List;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;

import com.cc.cms.action.CmsAct;
import com.cc.cms.entity.main.Permission;
import com.cc.cms.entity.main.Role;
import com.cc.cms.entity.main.Site;
import com.cc.cms.service.main.IPermissionSvc;
import com.cc.cms.service.main.IRoleSvc;
import com.cc.common.orm.hibernate3.OrderBy;

/** @author wangcheng */
@SuppressWarnings("rawtypes")
@Scope("prototype")
@Controller("web.action.admin.roleAct")
public class RoleAct extends CmsAct {

	private static final long	serialVersionUID	= 6357336858579346854L;
	private static final Logger	log					= LoggerFactory.getLogger(RoleAct.class);
	@Autowired
	private IRoleSvc			roleSvc;
	@Autowired
	private IPermissionSvc		permissionSvc;
	private List<Permission>	permissions;
	private Permission			root;
	private Role				role;

	@Override
	public String list() {
		list = roleSvc.findAll(new OrderBy[] { OrderBy.asc("id") });
		return LIST;
	}

	@Override
	public String add() {
		root = permissionSvc.getRoot();
		return ADD;
	}

	public String save() {
		if (permissions != null && permissions.size() > 0) {
			role.setPermissions(new HashSet<Permission>(permissions));
		}
		role.setSites(new HashSet<Site>(websiteSvc.findAll()));
		roleSvc.save(role);
		addActionMessage("添加成功");
		websiteSvc.loadAllToCache();
		return list();
	}

	@Override
	public String edit() {
		role = roleSvc.findById(id);
		root = permissionSvc.getRoot();
		return EDIT;
	}

	public String update() {
		if (permissions != null && permissions.size() > 0) {
			role.setPermissions(new HashSet<Permission>(permissions));
		}
		roleSvc.updateDefault(role);
		addActionMessage("更新成功");
		websiteSvc.loadAllToCache();
		return list();
	}

	public String delete() {
		vldBatch();
		try {
			for (Role role : roleSvc.deleteById(ids)) {
				log.info("删除成功:{}", role.getName());
			}
			addActionMessage("成功删除 ");
		} catch (DataIntegrityViolationException e) {
			addActionError("记录已被引用，不能删除!");
			log.error("删除失败,记录被引用");
		}
		setId(null);
		websiteSvc.loadAllToCache();
		return list();
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	public Permission getRoot() {
		return root;
	}

	public void setRoot(Permission root) {
		this.root = root;
	}
}

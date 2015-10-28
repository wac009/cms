
package com.cc.cms.action.admin.main;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;

import com.cc.cms.action.CmsAct;
import com.cc.cms.entity.main.Permission;
import com.cc.cms.entity.main.Role;
import com.cc.cms.service.main.IRoleSvc;
import com.cc.common.orm.ISelectTree;
import com.cc.common.util.SelectTreeUtils;

/** @author wangcheng */
/**
 * @author wangcheng
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@Scope("prototype")
@Controller("web.action.admin.permissionAct")
public class PermissionAct extends CmsAct {

	private static final long	serialVersionUID	= -6802927050654163132L;
	private static final Logger	log					= LoggerFactory.getLogger(PermissionAct.class);
	@Autowired
	private IRoleSvc			roleSvc;
	private Permission			permission;
	private List<Permission>	parentList;
	private List<Role>			roleList;
	private List<Role>			roles;

	@Override
	public String left() {
		Permission root = permissionSvc.getRoot();
		setLeftMenu(root);
		setLeftMenuType("tree");
		setLeftNav("菜单管理");
		return LEFT;
	}

	private void initList() {
		if (id == null) {
			list = SelectTreeUtils.webTree(SelectTreeUtils.handleTreeChild(permissionSvc.getAll()));
		} else {
			list = SelectTreeUtils.webTree(SelectTreeUtils.handleTreeChild(new ArrayList<ISelectTree>(permissionSvc.findById(id).getChild())));
		}
	}

	@Override
	public String list() {
		initList();
		return LIST;
	}

	private void handleParent(Permission parent) {
		if (parent != null) {
			roleList = new ArrayList<Role>(parent.getRoles());
			if (permission != null && permission.getRoles() != null) {
				roles = new ArrayList<Role>(permission.getRoles());
			}
		} else {
			roleList = roleSvc.findAll();
			if (permission != null && permission.getRoles() != null) {
				roles = new ArrayList<Role>(permission.getRoles());
			}
		}
	}

	private void initAdd() {
		permission = new Permission();
		if (id != null) {
			permission.setParent(permissionSvc.findById(id));
		}
		parentList = SelectTreeUtils.webTree(SelectTreeUtils.handleTreeChild(permissionSvc.getAll()));
		handleParent(permission.getParent());
	}

	@Override
	public String add() {
		initAdd();
		return ADD;
	}

	public String save() {
		permissionSvc.save(permission,roles);
		permissionSvc.handleChange();
		logSvc.operating("添加菜单", "添加菜单成功", getIp(), getUri(), getWeb(), getUser());
		return redirect("permission_list.jspa");
	}

	private void initEdit() {
		permission = permissionSvc.findById(id);
		handleParent(permission.getParent());
		parentList = SelectTreeUtils.webTree(SelectTreeUtils.handleTreeChild(permissionSvc.getListForUpdate(id)));
		if (permission.getParent() == null) {
			parentList = SelectTreeUtils.webTree(SelectTreeUtils.handleTreeChild(new ArrayList<Permission>()));
		}
	}

	@Override
	public String edit() {
		initEdit();
		return EDIT;
	}

	public String update() {
		permissionSvc.update(permission,roles);
		permissionSvc.handleChange();
		logSvc.operating("修改菜单", "修改菜单成功", getIp(), getUri(), getWeb(), getUser());
		addActionMessage("修改成功");
		setId(null);
		return list();
	}

	public String delete() {
		try {
			for (Permission f : permissionSvc.deleteById(ids)) {
				log.info("成功删除:{}", f.getName());
			}
			addActionMessage("删除成功");
			permissionSvc.handleChange();
		} catch (DataIntegrityViolationException e) {
			addActionError("记录被引用，不能删除!");
			log.info("删除失败，记录被引用");
		}
		return redirect("permission_list.jspa");
	}

	/**
	 * 排序 是否可向上移动
	 */
	public boolean isUp(Permission permission) {
		return permissionSvc.isUp(permission);
	}

	public boolean isDown(Permission permission) {
		return permissionSvc.isDown(permission);
	}

	public String up() {
		permissionSvc.up(id);
		addActionMessage("排序成功!");
		permissionSvc.handleChange();
		setId(null);
		return list();
	}

	public boolean validateUp() {
		if (hasErrors()) {
			log.error("发生action/field错误");
			return true;
		}
		if (permissionSvc.getPrev(permissionSvc.findById(id)) == null) {
			addActionError("顺序不能超出父栏目");
			return true;
		}
		return false;
	}

	public String down() {
		permissionSvc.down(id);
		addActionMessage("排序成功!");
		permissionSvc.handleChange();
		setId(null);
		return list();
	}

	public boolean validateDown() {
		if (hasErrors()) {
			log.error("发生action/field错误");
			return true;
		}
		if (permissionSvc.getNext(permissionSvc.findById(id)) == null) {
			addActionError("顺序不能超出父栏目");
			return true;
		}
		return false;
	}

	public boolean validateSave() {
		if (hasErrors()) {
			log.error("发生action/field错误");
			initAdd();
			return true;
		}
		return false;
	}

	public boolean validateUpdate() {
		if (hasErrors()) {
			initEdit();
			log.error("发生action/field错误");
			return true;
		}
		return false;
	}

	public boolean validateDelete() {
		if (hasErrors()) {
			log.error("发生action/field错误");
			initList();
			return true;
		}
		if (vldBatch()) {
			initList();
			return true;
		}
		List<Permission> permissions = new ArrayList<Permission>();
		for (Integer id : ids) {
			Permission entity = permissionSvc.findById(id);
			if (vldExist(entity)) {
				initList();
				return true;
			}
			permissions.add(entity);
		}
		for (Permission permission : permissions) {
			if (permission.getChild() != null && permission.getChild().size() > 0) {
				addActionError("要删除项目有子项目，请先删除子项目");
				initList();
				return true;
			}
		}
		return false;
	}

	private boolean vldExist(Permission entity) {
		if (entity == null) {
			addActionError("记录不存在：" + id);
			log.error("记录不存在：{}", id);
			return true;
		}
		return false;
	}

	public Permission getPermission() {
		return permission;
	}

	public void setPermission(Permission permission) {
		this.permission = permission;
	}

	public List<Permission> getParentList() {
		return parentList;
	}

	public void setParentList(List<Permission> parentList) {
		this.parentList = parentList;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
}

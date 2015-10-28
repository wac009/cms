
package com.cc.cms.service.main.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.cc.cms.dao.main.IPermissionDao;
import com.cc.cms.entity.main.Permission;
import com.cc.cms.entity.main.Role;
import com.cc.cms.entity.main.User;
import com.cc.cms.service.CmsSvcImpl;
import com.cc.cms.service.main.IPermissionSvc;
import com.cc.common.orm.Updater;

/** @author wangcheng */
@SuppressWarnings({ "unchecked", "rawtypes" })
@Service
@Transactional
public class PermissionSvcImpl extends CmsSvcImpl<Permission> implements IPermissionSvc {

	@Autowired
	@Qualifier("permission")
	private Cache	permissionCache;

	@Autowired
	public void setDao(IPermissionDao dao) {
		super.setDao(dao);
	}

	@Override
	protected IPermissionDao getDao() {
		return (IPermissionDao) super.getDao();
	}

	@Override
	public Permission findById(Serializable id) {
		Assert.notNull(id);
		for (Permission permission : getAll()) {
			if (id.equals(permission.getId())) {
				return permission;
			}
		}
		return super.findById(id);
	}

	@Override
	public void handleChange() {
		loadAllToCache();
	}

	@Override
	public void loadAllToCache() {
		permissionCache.removeAll();
		permissionCache.put(new Element("permission", getAllFromDB()));
	}

	public List<Permission> getAllFromDB() {
		return getDao().findAll();
	}

	@Override
	public List<Permission> getAll() {
		// 从缓存中获取，若缓存空就查询数据库并加入缓存
		Element e = permissionCache.get("permission");
		if (e != null) {
			return (List<Permission>) e.getObjectValue();
		} else {
			List<Permission> permissions = getAllFromDB();
			permissionCache.put(new Element("permission", permissions));
			return permissions;
		}
	}

	@Override
	public List<Permission> getPermissions(Integer uid) {
		List<Permission> permissions = new ArrayList<Permission>();
		for (Permission permission : getAll()) {
			for (User admin : permission.getUsers()) {
				if (uid.equals(admin.getId())) {
					permissions.add(permission);
				}
			}
		}
		return permissions;
	}

	@Override
	public List<Permission> getQuickMenus(Integer uid) {
		List<Permission> perms = new ArrayList<Permission>();
		for (Permission p : getAll()) {
			for (User user : p.getUsers()) {
				if (uid.equals(user.getId()) && p.getIsQuick()) {
					perms.add(p);
				}
			}
		}
		return perms;
	}

	@Override
	public Permission getPermission(String url, Integer uid) {
		Assert.notNull(url);
		List<Permission> permissions = getPermissions(uid);
		for (Permission permission : permissions) {
			if (url.equals(permission.getUrl())) {
				if (permission.getChild() != null && permission.getChild().size() > 0) {
					filterChild(permissions, permission);
				}
				return permission;
			}
		}
		return null;
	}

	@Override
	public Set<String> getItems(Integer uid) {
		Set<String> funcItemSet = new HashSet<String>();
		String url = null;
		String funcs = null;
		String[] func = null;
		for (Permission permission : getPermissions(uid)) {
			url = permission.getUrl();
			if (!StringUtils.isBlank(url)) {
				funcItemSet.add(url);
			}
			funcs = permission.getFuncs();
			if (!StringUtils.isBlank(funcs)) {
				func = funcs.split(Permission.FUNC_SPLIT);
				for (String funcItem : func) {
					funcItemSet.add(funcItem);
				}
			}
		}
		return funcItemSet;
	}

	@Override
	public List<Permission> getAllRoots() {
		List<Permission> roots = new ArrayList<Permission>();
		for (Permission permission : getAll()) {
			if (permission.getParent() == null) {
				roots.add(permission);
			}
		}
		return roots;
	}

	@Override
	public List<Permission> getRoots(Integer uid) {
		return filterPermissions(getPermissions(uid), getAllRoots());
	}

	@Override
	public Permission getRoot() {
		return getAllRoots().get(0);
	}

	@Override
	public Permission getRoot(Integer uid) {
		return getRoots(uid).get(0);
	}

	@Override
	public List<Permission> getListForUpdate(Integer funId) {
		Permission permission = findById(funId);
		List<Permission> permissions = new ArrayList<Permission>();
		for (Permission fun : getAll()) {
			if (fun.getLft() < permission.getLft() && fun.getRgt() > permission.getRgt()) {
				permissions.add(fun);
			}
		}
		for (Permission fun : permissions) {
			if (fun.getChild() != null) {
				handleChildForUpdate(fun, permission);
			}
		}
		return permissions;
	}

	private void handleChildForUpdate(Permission bean, Permission permission) {
		for (Permission child : bean.getChild()) {
			if (child.getId().equals(permission.getId())) {
				bean.getTreeChild().remove(child);
			} else {
				if (child.getChild() != null) {
					handleChildForUpdate(child, permission);
				}
			}
		}
	}

	@Override
	public List<Permission> getChild(Integer pid) {
		List<Permission> childs = new ArrayList<Permission>();
		for (Permission permission : getAll()) {
			if (permission.getId().equals(pid)) {
				for (Permission child : permission.getChild()) {
					childs.add(child);
				}
			}
		}
		return childs;
	}

	@Override
	public List<Permission> getQuick() {
		return null;
	}

	@Override
	public List<Permission> filterPermissions(List<Permission> filter, List<Permission> src) {
		Assert.notNull(src);
		Assert.notNull(filter);
		List<Permission> filteredSrc = src;
		for (Iterator iterator = src.iterator(); iterator.hasNext();) {
			Permission permission = (Permission) iterator.next();
			if (!filter.contains(permission)) {
				iterator.remove();
				filteredSrc.remove(permission);
			} else if (permission.getChild() != null && permission.getChild().size() > 0) {
				filterChild(filter, permission);
			}
		}
		loadAllToCache();
		return filteredSrc;
	}

	private Permission filterChild(List<Permission> filter, Permission permission) {
		for (Iterator iterator = permission.getChild().iterator(); iterator.hasNext();) {
			Permission child = (Permission) iterator.next();
			if (!filter.contains(child)) {
				iterator.remove();
				permission.getChild().remove(child);
			} else if (child.getChild() != null && child.getChild().size() > 0) {
				permission = filterChild(filter, child);
			}
		}
		return permission;
	}
	
	@Override
	public Permission filterMenuPermission(Permission permission) {
		if (permission==null) {
			return null;
		}
		if (!permission.getIsMenu()) {
			return null;
		}
		if (permission.getChild()!=null&&permission.getChild().size()>0) {
			permission=filterMenuChild(permission);
		}
		return permission;
	}
	
	private Permission filterMenuChild(Permission permission) {
		for (Iterator iterator = permission.getChild().iterator(); iterator.hasNext();) {
			Permission child = (Permission) iterator.next();
			if (!child.getIsMenu()) {
				iterator.remove();
				permission.getChild().remove(child);
			} else if (child.getChild() != null && child.getChild().size() > 0) {
				permission = filterMenuChild(child);
			}
		}
		return permission;
	}
	

	@Override
	public boolean isUp(Permission bean) {
		if (getPrev(bean) == null) {
			return false;
		}
		return true;
	}

	@Override
	public boolean isDown(Permission bean) {
		if (getNext(bean) == null) {
			return false;
		}
		return true;
	}

	@Override
	public Permission up(Integer id) {
		Permission bean = findById(id);
		Integer oPriority = bean.getPriority();
		Permission beanPre = getPrev(bean);
		bean.setPriority(beanPre.getPriority());
		beanPre.setPriority(oPriority);
		updateByUpdater(Updater.create(bean));
		updateByUpdater(Updater.create(beanPre));
		return bean;
	}

	@Override
	public Permission down(Integer id) {
		Permission bean = findById(id);
		Integer oPriority = bean.getPriority();
		Permission beanNext = getNext(bean);
		bean.setPriority(beanNext.getPriority());
		beanNext.setPriority(oPriority);
		updateByUpdater(Updater.create(bean));
		updateByUpdater(Updater.create(beanNext));
		return bean;
	}

	@Override
	public Permission getPrev(Permission bean) {
		Permission beanPrev = new Permission();
		for (Permission permission : getAll()) {
			if (permission.getParent() != null && permission.getParent().getId() == bean.getParent().getId()
					&& permission.getPriority() < bean.getPriority()) {
				if ((beanPrev.getPriority() == null) || (beanPrev.getPriority() != null && beanPrev.getPriority() < permission.getPriority())) {
					beanPrev = permission;
				}
			}
		}
		return beanPrev.getId() == null ? null : beanPrev;
	}

	@Override
	public Permission getNext(Permission bean) {
		Permission beanNext = new Permission();
		for (Permission permission : getAll()) {
			if (permission.getParent() != null && permission.getParent().getId() == bean.getParent().getId()
					&& permission.getPriority() > bean.getPriority()) {
				if ((beanNext.getPriority() == null) || (beanNext.getPriority() != null && beanNext.getPriority() > permission.getPriority())) {
					beanNext = permission;
				}
			}
		}
		return beanNext.getId() == null ? null : beanNext;
	}

	@Override
	public Permission save(Permission bean, List<Role> roles) {
		bean.setPriority(getDao().getMaxPriority() + 1);
		bean.init();
		handleRole(bean, roles);
		save(bean);
		return bean;
	}

	@Override
	public Permission update(Permission bean, List<Role> roles) {
		bean.init();
		Updater<Permission> updater = new Updater<Permission>(bean);
		updater.include("roles");
		handleRole(bean, roles);
		updateByUpdater(updater);
		return bean;
	}

	private void handleRole(Permission bean, List<Role> roles) {
		if (roles != null) {
			bean.setRoles(new HashSet<Role>(roles));
		}
	}
}

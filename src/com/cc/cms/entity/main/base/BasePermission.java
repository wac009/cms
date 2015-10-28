package com.cc.cms.entity.main.base;

import java.util.*;

import com.cc.cms.entity.main.*;

/**
 * Permission entity. @author MyEclipse Persistence Tools edit by wangcheng
 */
public class BasePermission implements java.io.Serializable {
	private static final long	serialVersionUID	= -8499987359732381668L;
	// Fields
	private Integer				id;
	private Permission		parent;
	private String				name;
	private String				url;
	private Integer				lft;
	private Integer				rgt;
	private String				funcs;
	private String				description;
	private Integer				priority;
	private Boolean				isMenu;
	private Boolean				isQuick;
	private Set<Permission>	child;
	private Set<Role>		roles;

	// Constructors
	/** default constructor */
	public BasePermission() {}
	/** id constructor */
	public BasePermission(Integer id) {
		this.id = id;
	}
	/** minimal constructor */
	public BasePermission(Integer priority, Boolean isMenu) {
		this.priority = priority;
		this.isMenu = isMenu;
	}
	/** full constructor */
	public BasePermission(Permission parent, String name, String url, Integer lft, Integer rgt, String funcs, String description,
			Integer priority, Boolean isMenu, Boolean isQuick, Set<Permission> child, Set<Role> roles) {
		this.parent = parent;
		this.name = name;
		this.url = url;
		this.lft = lft;
		this.rgt = rgt;
		this.funcs = funcs;
		this.description = description;
		this.priority = priority;
		this.isMenu = isMenu;
		this.isQuick = isQuick;
		this.child = child;
		this.roles = roles;
	}
	// Property accessors
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getLft() {
		return lft;
	}
	public void setLft(Integer lft) {
		this.lft = lft;
	}
	public Integer getRgt() {
		return rgt;
	}
	public void setRgt(Integer rgt) {
		this.rgt = rgt;
	}
	public String getFuncs() {
		return funcs;
	}
	public void setFuncs(String funcs) {
		this.funcs = funcs;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public Boolean getIsMenu() {
		return isMenu;
	}
	public void setIsMenu(Boolean isMenu) {
		this.isMenu = isMenu;
	}
	public Boolean getIsQuick() {
		if (isQuick == null) {
			isQuick = false;
		}
		return isQuick;
	}
	public void setIsQuick(Boolean isQuick) {
		this.isQuick = isQuick;
	}
	public Permission getParent() {
		return parent;
	}
	public void setParent(Permission parent) {
		this.parent = parent;
	}
	public Set<Permission> getChild() {
		return child;
	}
	public void setChild(Set<Permission> child) {
		this.child = child;
	}
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
}

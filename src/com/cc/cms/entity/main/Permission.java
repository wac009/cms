
package com.cc.cms.entity.main;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.cc.cms.entity.main.base.BasePermission;
import com.cc.common.orm.IHibernateTree;
import com.cc.common.orm.IPriorityInterface;
import com.cc.common.orm.ISelectTree;

/**
 * @author wangcheng
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class Permission extends BasePermission implements ISelectTree, IHibernateTree<Integer>, IPriorityInterface {

	private static final long	serialVersionUID	= 7730513695646613520L;
	/**
	 * 功能集的分隔符
	 */
	public static final String	FUNC_SPLIT			= "@";
	/**
	 * 下拉列表树
	 */
	private String				selectTree;
	/** 树子节点 */
	private Set<Permission>		treeChild;
	
	public void init() {
		if (getIsMenu()==null) {
			setIsMenu(false);
		}
		if (getIsQuick()==null) {
			setIsQuick(false);
		}
	}

	/** 获取菜单的上下级链表 **/
	public List<Permission> getNodeList() {
		LinkedList list = new LinkedList();
		Permission node = this;
		while (node != null) {
			list.addFirst(node);
			node = node.getParent();
		}
		return list;
	}

	@Override
	public String getSelectTree() {
		return selectTree;
	}

	@Override
	public String getTreeName() {
		return getName();
	}

	@Override
	public Permission getTreeParent() {
		return getParent();
	}

	@Override
	public void setSelectTree(String selectTree) {
		this.selectTree = selectTree;
	}

	@Override
	public Set<? extends ISelectTree> getTreeChild() {
		if (treeChild != null) {
			return treeChild;
		} else {
			return getChild();
		}
	}

	@Override
	public Integer getParentId() {
		Permission parent = getParent();
		if (parent == null) {
			return null;
		} else {
			return parent.getId();
		}
	}

	@Override
	public String getTreeCondition() {
		// return "bean.site.id=" + getSite().getId();
		return null;
	}

	@Override
	public void setTreeChild(Set treeChild) {
		this.treeChild = treeChild;
	}

	@Override
	public Set<? extends ISelectTree> getTreeChildRaw() {
		return treeChild;
	}

	/**
	 * @see HibernateTree#getLftName()
	 */
	@Override
	public String getLftName() {
		return DEF_LEFT_NAME;
	}

	/**
	 * @see HibernateTree#getParentName()
	 */
	@Override
	public String getParentName() {
		return DEF_PARENT_NAME;
	}

	/**
	 * @see HibernateTree#getRgtName()
	 */
	@Override
	public String getRgtName() {
		return DEF_RIGHT_NAME;
	}

	public void addToChild(Permission permission) {
		if (null == getChild())
			setChild(new TreeSet<Permission>());
		getChild().add(permission);
	}

	public Set<User> getUsers() {
		Set<User> users = new HashSet();
		for (Role role : getRoles()) {
			for (User user : role.getUsers()) {
				users.add(user);
			}
		}
		return users;
	}
}

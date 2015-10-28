package com.cc.cms.entity.main;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.cc.cms.entity.main.base.BasePeriodicalCatalog;
import com.cc.common.orm.IPriorityInterface;
import com.cc.common.orm.ISelectTree;

/**
 * @author wangcheng
 */
@SuppressWarnings({"unchecked","rawtypes"})
public class PeriodicalCatalog extends BasePeriodicalCatalog implements ISelectTree, IPriorityInterface {
	private static final long			serialVersionUID	= -239558209761379174L;
	/** 下拉列表树 */
	private String						selectTree;
	private Set<? extends ISelectTree>	treeChild;

	/** 获取栏目的上下级链表 **/
	public List<Object> getNodeList() {
		LinkedList list = new LinkedList();
		PeriodicalCatalog node = this;
		if (this != null) {
			list.addFirst(node);
		}
		if (this.getPeriodical() != null) {
			list.addFirst(this.getPeriodical());
		}
		if (this.getPeriodical().getPublication() != null) {
			list.addFirst(this.getPeriodical().getPublication());
		}
		return list;
	}
	@Override
	public String getSelectTree() {
		return selectTree;
	}
	@Override
	public Set<? extends ISelectTree> getTreeChild() {
		if (treeChild != null) {
			return treeChild;
		}
		return null;
	}
	@Override
	public void setTreeChild(Set treeChild) {}
	@Override
	public Set<? extends ISelectTree> getTreeChildRaw() {
		return null;
	}
	public Set<PeriodicalCatalog> getChild() {
		return null;
	}
	@Override
	public String getTreeName() {
		return getName();
	}
	@Override
	public ISelectTree getTreeParent() {
		return null;
	}
	@Override
	public void setSelectTree(String selectTree) {
		this.selectTree = selectTree;
	}
	public String getTreeCondition() {
		return null;
	}
	public boolean isTreeLeaf() {
		return true;
	}
	public Long getParentId() {
		return null;
	}
	public Integer getLft() {
		return null;
	}
	public Integer getRgt() {
		return null;
	}
	public void setLft(Integer lft) {}
	public void setRgt(Integer rgt) {}
}

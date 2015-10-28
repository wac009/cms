package com.cc.common.orm;

import java.util.Set;

/**
 * 下拉列表框树
 */
@SuppressWarnings("rawtypes")
public interface ISelectTree {
	/**
	 * 获得树ID
	 */
	public Integer getId();

	/**
	 * 获得父节点
	 */
	public ISelectTree getTreeParent();

	/**
	 * 获得原节点名称
	 */
	public String getTreeName();

	/**
	 * 获得下拉列表树名称
	 */
	public String getSelectTree();

	/**
	 * 设置下拉列表树名称
	 */
	public void setSelectTree(String selectTree);

	/**
	 * 获得树的子节点。可以被处理，比如为null时，可以直接调用getChild()
	 */
	public Set<? extends ISelectTree> getTreeChild();

	/**
	 * 未处理的子节点。如果没有调用setTreeChild，则该方法返回null。
	 */
	public Set<? extends ISelectTree> getTreeChildRaw();

	/**
	 * 设置树的子节点
	 * 
	 * @param treeChild
	 */
	public void setTreeChild(Set treeChild);
}

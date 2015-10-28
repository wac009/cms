
package com.cc.cms.entity.main;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.cc.cms.entity.main.base.BasePublication;
import com.cc.common.orm.IPriorityInterface;
import com.cc.common.orm.ISelectTree;
import com.cc.common.util.StrUtils;

@SuppressWarnings("rawtypes")
public class Publication extends BasePublication implements ISelectTree, IPriorityInterface {

	private static final long			serialVersionUID	= 5472510701198994894L;
	/** 下拉列表树 */
	private String						selectTree;
	/** div树，是否为叶子节点 */
	private Boolean						treeLeaf;
	private Set<? extends ISelectTree>	treeChild;

	public String getTitleImgUrl() {
		String img = getImgPath();
		if (StringUtils.isBlank(img)) {
			// TODO 链接到图片默认的提示图片
			return "";
		} else {
			return getWebsite().getUploadPath() + img;
		}
	}

	public String tit(int len) {
		String s = getName();
		if (StringUtils.isBlank(s)) {
			return "";
		} else {
			return StrUtils.getCn(s, len);
		}
	}

	public Periodical getCurrent() {
		for (Iterator iterator = getPeriodicals().iterator(); iterator.hasNext();) {
			Periodical periodical = (Periodical) iterator.next();
			if (periodical.getCurrent() != null && periodical.getCurrent()) {
				return periodical;
			}
		}
		return null;
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

	public Set<Periodical> getChild() {
		Set<Periodical> periodicals = new LinkedHashSet<Periodical>();
		for (Iterator iterator = getPeriodicals().iterator(); iterator.hasNext();) {
			Periodical periodical = (Periodical) iterator.next();
			if (periodical.getLock() == null || !periodical.getLock()) {
				periodicals.add(periodical);
			}
		}
		return periodicals;
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
		return " b.website.id=" + getWebsite().getId();
	}

	public boolean isTreeLeaf() {
		if (treeLeaf != null) {
			return treeLeaf;
		}
		return false;
	}

	public void setTreeLeaf(Boolean treeLeaf) {
		this.treeLeaf = treeLeaf;
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

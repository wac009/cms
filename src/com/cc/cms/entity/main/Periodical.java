
package com.cc.cms.entity.main;

import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.cc.cms.entity.main.base.BasePeriodical;
import com.cc.common.orm.IPriorityInterface;
import com.cc.common.orm.ISelectTree;

/**
 * @author wangcheng
 */
@SuppressWarnings("rawtypes")
public class Periodical extends BasePeriodical implements ISelectTree, IPriorityInterface {

	private static final long			serialVersionUID	= 5705514641680707514L;
	/** 下拉列表树 */
	private String						selectTree;
	/** div树，是否为叶子节点 */
	private Boolean						treeLeaf;
	private Set<? extends ISelectTree>	treeChild;
	private String						name;

	public String getTitleImgUrl() {
		String img = getImgPath();
		if (StringUtils.isBlank(img)) {
			// TODO 链接到图片默认的提示图片
			return "";
		} else {
			return getPublication().getWebsite().getUploadPath() + img;
		}
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

	@Override
	public String getTreeName() {
		return getYear() + "年第" + getYearPeriod() + "期 &nbsp;总第" + getTotalPeriod() + "期";
	}

	public String getName() {
		if (name == null) {
			return getPublication().getName() + " " + getYear() + "年第" + getYearPeriod() + "期";
		}
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

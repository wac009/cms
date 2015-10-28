package com.cc.cms.entity.main;

import static com.cc.cms.Constants.RES;
import static com.cc.cms.Constants.TPL_BASE;
import static com.cc.cms.Constants.UPLOAD_PATH;
import static com.cc.common.Constants.SPT;

import java.util.Collection;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.cc.cms.entity.main.base.BaseSite;
import com.cc.common.orm.IHibernateTree;
import com.cc.common.orm.ISelectTree;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class Site extends BaseSite implements ISelectTree, IHibernateTree<Integer> {
	private static final long serialVersionUID = 1L;
	/** 下拉列表树 */
	private String selectTree;
	/** 树子节点 */
	private Set<Site> treeChild;

	/**
	 * 获得站点url
	 * 
	 * @return
	 */
	public String getUrl() {
		if (getStaticIndex()) {
			return getUrlStatic();
		} else {
			return getUrlDynamic();
		}
	}

	/** 获得站点别名数组 */
	public String[] getAlias() {
		return StringUtils.split(getDomainAlias(), ',');
	}

	/**
	 * 获得完整路径。在给其他网站提供客户端包含时也可以使用。
	 * 
	 * @return
	 */
	public String getUrlWhole() {
		if (getStaticIndex()) {
			return getUrlBuffer(false, true, false).append("/").toString();
		} else {
			return getUrlBuffer(true, true, false).append("/").toString();
		}
	}

	public String getUrlDynamic() {
		return getUrlBuffer(true, null, false).append("/").toString();
	}

	public String getUrlStatic() {
		return getUrlBuffer(false, null, true).append("/").toString();
	}

	public StringBuilder getUrlBuffer(boolean dynamic, Boolean whole, boolean forIndex) {
		boolean relative = whole != null ? !whole : getRelativePath();
		String ctx = getContextPath();
		StringBuilder url = new StringBuilder();
		if (!relative) {
			url.append(getProtocol()).append(getDomain());
			if (getPort() != null) {
				url.append(":").append(getPort());
			}
		}
		if (!StringUtils.isBlank(ctx)) {
			url.append(ctx);
		}
		if (dynamic) {
			String servlet = getServletPoint();
			if (!StringUtils.isBlank(servlet)) {
				url.append(servlet);
			}
		} else {
			if (!forIndex) {
				String staticDir = getStaticDir();
				if (!StringUtils.isBlank(staticDir)) {
					url.append(staticDir);
				}
			}
		}
		return url;
	}

	/**
	 * 获得模板路径。 模板路径创建规则：/WEB-INF/tpl/+域名首字母分类/+域名第一部分/+模板名称
	 * 如：/WEB-INF/tpl/w/www/default
	 */
	public String getTplPath() {
		String ctg = getDomain().substring(0, 1);
		String ctg2 = getDomain().substring(0, getDomain().indexOf("."));
		return TPL_BASE + SPT + ctg + SPT + ctg2 + SPT + getTemplate().getResPath();
	}

	/** 获得模板相对路径。如：/WEB-INF/t */
	public StringBuilder getTplRoot() {
		StringBuilder sb = new StringBuilder();
		return sb.append(TPL_BASE);
	}

	/** 获得资源相对根路径。如：/r */
	public StringBuilder getResRootBuf() {
		StringBuilder sb = new StringBuilder();
		sb.append(RES);
		return sb;
	}

	/** 获得资源路径。如：/res/w */
	@Override
	public String getResPath() {
		StringBuilder sb = new StringBuilder();
		sb.append("http://").append(getDomain());
		if (getPort() != null && !getPort().equals(80)) {
			sb.append(":").append(getPort());
		}
		return sb.toString() + UPLOAD_PATH;
	}

	/** 获得上传路径。如：/res/w */
	public String getUploadPath() {
		return UPLOAD_PATH;
	}

	/**
	 * 获得上传访问前缀。 根据配置识别上传至数据、FTP和本地
	 * 
	 * @return
	 */
	public String getUploadBase() {
		Config config = getConfig();
		String ctx = config.getContextPath();
		if (config.getUploadToDb()) {
			if (!StringUtils.isBlank(ctx)) {
				return ctx + config.getDbFileUri();
			} else {
				return config.getDbFileUri();
			}
		} else if (getUploadFtp() != null) {
			return getUploadFtp().getUrl();
		} else {
			if (!StringUtils.isBlank(ctx)) {
				return ctx;
			} else {
				return "";
			}
		}
	}

	/**
	 * 获得模板绝对路径。如：d:/cnca/WEB-INF/tpl/
	 * 
	 * @param realRoot
	 */
	public StringBuilder getTplRootReal(String realRoot) {
		StringBuilder sb = new StringBuilder(realRoot);
		sb.append(getTplRoot());
		return sb;
	}

	public String getServletPoint() {
		Config config = getConfig();
		if (config != null) {
			return config.getServletPoint();
		} else {
			return null;
		}
	}

	public String getContextPath() {
		Config config = getConfig();
		if (config != null) {
			return config.getContextPath();
		} else {
			return null;
		}
	}

	public Integer getPort() {
		Config config = getConfig();
		if (config != null) {
			return config.getPort();
		} else {
			return null;
		}
	}

	public String getDefImg() {
		Config config = getConfig();
		if (config != null) {
			return config.getDefImg();
		} else {
			return null;
		}
	}

	public String getLoginUrl() {
		Config config = getConfig();
		if (config != null) {
			return config.getLoginUrl();
		} else {
			return null;
		}
	}

	public String getProcessUrl() {
		Config config = getConfig();
		if (config != null) {
			return config.getProcessUrl();
		} else {
			return null;
		}
	}

	public int getUsernameMinLen() {
		return getConfig().getMemberConfig().getUsernameMinLen();
	}

	public int getPasswordMinLen() {
		return getConfig().getMemberConfig().getPasswordMinLen();
	}

	public static Integer[] fetchIds(Collection<Site> sites) {
		if (sites == null) {
			return null;
		}
		Integer[] ids = new Integer[sites.size()];
		int i = 0;
		for (Site s : sites) {
			ids[i++] = s.getId();
		}
		return ids;
	}

	public void init() {
		if (StringUtils.isBlank(getProtocol())) {
			setProtocol("http://");
		}
		if (getFinalStep() == null) {
			byte step = 2;
			setFinalStep(step);
		}
		if (getIndexToRoot() == null) {
			setIndexToRoot(false);
		}
	}

	/* [CONSTRUCTOR MARKER BEGIN] */
	public Site() {
		super();
	}

	/** Constructor for primary key */
	public Site(java.lang.Integer id) {
		super(id);
	}

	@Override
	public String getTreeName() {
		return getName();
	}

	@Override
	public String getSelectTree() {
		return selectTree;
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
	public Set<? extends ISelectTree> getTreeChildRaw() {
		return treeChild;
	}

	@Override
	public ISelectTree getTreeParent() {
		return getParent();
	}

	/** @see HibernateTree#getParentId() */
	@Override
	public Integer getParentId() {
		Site parent = getParent();
		if (parent == null) {
			return null;
		} else {
			return parent.getId();
		}
	}

	@Override
	public void setTreeChild(Set treeChild) {
		this.treeChild = treeChild;
	}

	/**
	 * 每个站点各自维护独立的树结构
	 * 
	 * @see HibernateTree#getTreeCondition()
	 */
	@Override
	public String getTreeCondition() {
		return "bean.parent.id=" + getId();
	}

	/** @see HibernateTree#getLftName() */
	@Override
	public String getLftName() {
		return DEF_LEFT_NAME;
	}

	/** @see HibernateTree#getParentName() */
	@Override
	public String getParentName() {
		return DEF_PARENT_NAME;
	}

	/** @see HibernateTree#getRgtName() */
	@Override
	public String getRgtName() {
		return DEF_RIGHT_NAME;
	}

	/** Constructor for required fields */
	public Site(java.lang.Integer id, com.cc.cms.entity.main.Config config, java.lang.String domain, java.lang.String name, java.lang.String protocol, java.lang.String dynamicSuffix,
			java.lang.String staticSuffix, java.lang.Boolean indexToRoot, java.lang.Boolean staticIndex, java.lang.String localeAdmin, java.lang.String localeFront, java.lang.Byte finalStep,
			java.lang.Byte afterCheck, java.lang.Boolean relativePath, java.lang.Boolean resycleOn, java.lang.Boolean close, java.lang.Integer rank, java.lang.Integer left, java.lang.Integer right,
			java.lang.String resPath, java.util.Date createTime) {
		super(id, config, domain, name, protocol, dynamicSuffix, staticSuffix, indexToRoot, staticIndex, localeAdmin, localeFront, finalStep, afterCheck, relativePath, resycleOn, close, rank, left,
				right, resPath, createTime);
	}
	/* [CONSTRUCTOR MARKER END] */
}
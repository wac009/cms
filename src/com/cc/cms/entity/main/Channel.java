
package com.cc.cms.entity.main;

import static com.cc.cms.Constants.INDEX;
import static com.cc.common.Constants.SPT;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;

import com.cc.cms.entity.main.base.BaseChannel;
import com.cc.common.orm.IHibernateTree;
import com.cc.common.orm.IPriorityInterface;
import com.cc.common.orm.ISelectTree;
import com.cc.common.orm.PriorityComparator;
import com.cc.common.web.staticpage.StaticPageUtils;

/** 栏目实体类
 * 
 * @author wangcheng */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class Channel extends BaseChannel implements ISelectTree, IHibernateTree<Integer>, IPriorityInterface, Cloneable {

	private static final long	serialVersionUID	= 1L;
	private Set<Channel>		treeChild;
	/** 下拉列表树 */
	private String				selectTree;
	/** div树，是否为叶子节点 */
	private Boolean				treeLeaf;
	/** 上传栏目图片相对地址 */
	public static final String	UPLOAD_PATH			= SPT + "channel";
	/** 在附件表中的类别 */
	public static final String	ATTACHMENT_CTG		= "栏目";
	private String				name;

	/* [CONSTRUCTOR MARKER BEGIN] */
	public Channel() {
		super();
	}

	/** Constructor for primary key */
	public Channel(java.lang.Integer id) {
		super(id);
	}

	/** Constructor for required fields */
	public Channel(java.lang.Integer id, com.cc.cms.entity.main.Site site, com.cc.cms.entity.main.Model model, java.lang.Integer lft,
			java.lang.Integer rgt, java.lang.Integer priority) {
		super(id, site, model, lft, rgt, priority);
	}

	/* [CONSTRUCTOR MARKER END] */
	/** 审核后内容修改方式
	 * 
	 * @author wangcheng */
	public static enum AfterCheckEnum {
		/** 不能修改，不能删除。 */
		CANNOT_UPDATE,
		/** 可以修改，可以删除。 修改后文章的审核级别将退回到修改人级别的状态。如果修改人的级别高于当前文章的审核级别，那么文章审核级别将保持不变。 */
		BACK_UPDATE,
		/** 可以修改，可以删除。 修改后文章保持原状态。 */
		KEEP_UPDATE
	}

	/** 获得URL地址
	 * 
	 * @return */
	public String getUrl() {
		if (!StringUtils.isBlank(getLink())) {
			return getLink();
		}
		if (getStaticChannel()) {
			return getUrlStatic(false, 1);
		} else {
			return getUrlDynamic(null);
		}
	}

	public String getUrlWhole() {
		if (!StringUtils.isBlank(getLink())) {
			return getLink();
		}
		if (getStaticChannel()) {
			return getUrlStatic(true, 1);
		} else {
			return getUrlDynamic(true);
		}
	}

	/** 获得静态URL地址
	 * 
	 * @return */
	public String getUrlStatic() {
		return getUrlStatic(null, 1);
	}

	public String getUrlStatic(int pageNo) {
		return getUrlStatic(null, pageNo);
	}

	public String getUrlStatic(Boolean whole, int pageNo) {
		if (!StringUtils.isBlank(getLink())) {
			return getLink();
		}
		Site site = getSite();
		StringBuilder url = site.getUrlBuffer(false, whole, false);
		String filename = getStaticFilenameByRule();
		if (!StringUtils.isBlank(filename)) {
			if (pageNo > 1) {
				int index = filename.indexOf(".", filename.lastIndexOf("/"));
				if (index != -1) {
					url.append(filename.substring(0, index));
					url.append("_").append(pageNo);
					url.append(filename.substring(index));
				} else {
					url.append("_").append(pageNo);
				}
			} else {
				if (getAccessByDir()) {
					url.append(filename.substring(0, filename.lastIndexOf("/") + 1));
				} else {
					url.append(filename);
				}
			}
		} else {
			// 默认静态页面访问路径
			url.append(SPT).append(getPath());
			if (pageNo > 1) {
				url.append("_").append(pageNo);
				url.append(site.getStaticSuffix());
			} else {
				if (getHasContent()) {
					url.append(SPT);
				} else {
					url.append(site.getStaticSuffix());
				}
			}
		}
		return url.toString();
	}

	public String getStaticFilename(int pageNo) {
		Site site = getSite();
		StringBuilder url = new StringBuilder();
		String staticDir = site.getStaticDir();
		if (!StringUtils.isBlank(staticDir)) {
			url.append(staticDir);
		}
		String filename = getStaticFilenameByRule();
		if (!StringUtils.isBlank(filename)) {
			int index = filename.indexOf(".", filename.lastIndexOf("/"));
			if (pageNo > 1) {
				if (index != -1) {
					url.append(filename.substring(0, index)).append("_").append(pageNo).append(filename.substring(index));
				} else {
					url.append(filename).append("_").append(pageNo);
				}
			} else {
				url.append(filename);
			}
		} else {
			// 默认静态页面访问路径
			url.append(SPT).append(getPath());
			String suffix = site.getStaticSuffix();
			if (getHasContent()) {
				url.append(SPT).append(INDEX);
				if (pageNo > 1) {
					url.append("_").append(pageNo);
				}
				url.append(suffix);
			} else {
				if (pageNo > 1) {
					url.append("_").append(pageNo);
				}
				url.append(suffix);
			}
		}
		return url.toString();
	}

	public String getStaticFilenameByRule() {
		String rule = getChannelRule();
		if (StringUtils.isBlank(rule)) {
			return null;
		}
		Model model = getModel();
		String url = StaticPageUtils.staticUrlRule(rule, model.getId(), model.getPath(), getId(), getPath(), null, null);
		return url;
	}

	/** 获得动态URL地址
	 * 
	 * @return */
	public String getUrlDynamic() {
		return getUrlDynamic(null);
	}

	public String getUrlDynamic(Boolean whole) {
		if (!StringUtils.isBlank(getLink())) {
			return getLink();
		}
		Site site = getSite();
		StringBuilder url = site.getUrlBuffer(true, whole, false);
		url.append(SPT).append(getPath());
		if (getHasContent()) {
			url.append(SPT).append(INDEX);
		}
		url.append(site.getDynamicSuffix());
		return url.toString();
	}

	/** 获得节点列表。从父节点到自身。
	 * 
	 * @return */
	public List<Channel> getNodeList() {
		LinkedList<Channel> list = new LinkedList<Channel>();
		Channel node = this;
		while (node != null) {
			list.addFirst(node);
			node = node.getParent();
		}
		return list;
	}

	/** 获得节点列表ID。从父节点到自身。
	 * 
	 * @return */
	public Integer[] getNodeIds() {
		List<Channel> channels = getNodeList();
		Integer[] ids = new Integer[channels.size()];
		int i = 0;
		for (Channel c : channels) {
			ids[i++] = c.getId();
		}
		return ids;
	}

	/** 获得深度
	 * 
	 * @return 第一层为0，第二层为1，以此类推。 */
	public int getDeep() {
		int deep = 0;
		Channel parent = getParent();
		while (parent != null) {
			deep++;
			parent = parent.getParent();
		}
		return deep;
	}

	/** 获得栏目终审级别
	 * 
	 * @return */
	public Byte getFinalStepExtends() {
		Byte step = getFinalStep();
		if (null==step) {
			return 2;
		} else {
			return step;
		}
	}

	public Byte getAfterCheck() {
		ChannelExt ext = getExt();
		if (ext != null) {
			return ext.getAfterCheck();
		} else {
			return null;
		}
	}

	/** 获得审核后修改方式的枚举值。如果该值为null则取父级栏目，父栏目为null则取站点相关设置。
	 * 
	 * @return */
	public AfterCheckEnum getAfterCheckEnum() {
		Byte after = getExt().getAfterCheck();
		Channel channel = getParent();
		// 如果为null，则查找父栏目。
		while (after == null && channel != null) {
			after = channel.getAfterCheck();
			channel = channel.getParent();
		}
		// 如果依然为null，则查找站点设置
		if (after == null) {
			after = getSite().getAfterCheck();
		}
		if (after == 1) {
			return AfterCheckEnum.CANNOT_UPDATE;
		} else if (after == 2) {
			return AfterCheckEnum.BACK_UPDATE;
		} else if (after == 3) {
			return AfterCheckEnum.KEEP_UPDATE;
		} else {
			// 默认为不可改、不可删
			return AfterCheckEnum.CANNOT_UPDATE;
		}
	}

	/** 获得列表用于下拉选择。条件：有内容的栏目。
	 * 
	 * @return */
	public List<Channel> getListForSelect(Set<Channel> rights, boolean hasContentOnly) {
		return getListForSelect(rights, null, hasContentOnly);
	}

	public List<Channel> getListForSelect(Set<Channel> rights, Channel exclude, boolean hasContentOnly) {
		List<Channel> list = new ArrayList<Channel>((getRgt() - getLft()) / 2);
		addChildToList(list, this, rights, exclude, hasContentOnly);
		return list;
	}

	/** 获得列表用于下拉选择。条件：有内容的栏目。
	 * 
	 * @param topList 顶级栏目
	 * @return */
	public static List<Channel> getListForSelect(List<Channel> topList, Set<Channel> rights, boolean hasContentOnly) {
		return getListForSelect(topList, rights, null, hasContentOnly);
	}

	public static List<Channel> getListForSelect(List<Channel> topList, Set<Channel> rights, Channel exclude, boolean hasContentOnly) {
		List<Channel> list = new ArrayList<Channel>();
		for (Channel c : topList) {
			addChildToList(list, c, rights, exclude, hasContentOnly);
		}
		return list;
	}

	/** 递归将子栏目加入列表。条件：有内容的栏目。
	 * 
	 * @param list 栏目容器
	 * @param channel 待添加的栏目，且递归添加子栏目
	 * @param rights 有权限的栏目，为null不控制权限。 */
	private static void addChildToList(List<Channel> list, Channel channel, Set<Channel> rights, Channel exclude, boolean hasContentOnly) {
		if ((rights != null && !rights.contains(channel)) || (exclude != null && exclude.equals(channel))) {
			return;
		}
		list.add(channel);
		Set<Channel> child = channel.getChild();
		for (Channel c : child) {
			if (hasContentOnly) {
				if (c.getHasContent()) {
					addChildToList(list, c, rights, exclude, hasContentOnly);
				}
			} else {
				addChildToList(list, c, rights, exclude, hasContentOnly);
			}
		}
	}

	public String getTplChannelOrDef() {
		String tpl = getTplChannel();
		if (!StringUtils.isBlank(tpl)) {
//			return "/WEB-INF/tpl/w/www/default/"+tpl;
			String xx= getSite().getTplPath()+tpl;
			return xx;
		} else {
			String sol = getSite().getTplPath();
			return getModel().getTplChannel(sol, true);
		}
	}

	public String getTplContentOrDef() {
		String tpl = getTplContent();
		if (!StringUtils.isBlank(tpl)) {
			return tpl;
		} else {
			String sol = getSite().getTplPath();
			return getModel().getTplContent(sol, true);
		}
	}

	public Integer[] getUserIds() {
		Set<User> users = getUsers();
		return User.fetchIds(users);
	}

	public void addToViewGroups(Group group) {
		Set<Group> groups = getViewGroups();
		if (groups == null) {
			groups = new TreeSet<Group>(new PriorityComparator());
			setViewGroups(groups);
		}
		groups.add(group);
		group.getViewChannels().add(this);
	}

	public void addToContriGroups(Group group) {
		Set<Group> groups = getContriGroups();
		if (groups == null) {
			groups = new TreeSet<Group>(new PriorityComparator());
			setContriGroups(groups);
		}
		groups.add(group);
		group.getContriChannels().add(this);
	}

	public void addToUsers(User user) {
		Set<User> set = getUsers();
		if (set == null) {
			set = new HashSet<User>();
			setUsers(set);
		}
		set.add(user);
		user.addToChannels(this);
	}

	public void init() {
		if (getDisplay() == null) {
			getExt().setDisplay(true);
		}
		if (getDisabled() == null) {
			getExt().setDisabled(false);
		}
	}

	public Boolean getStaticChannel() {
		ChannelExt ext = getExt();
		if (ext != null) {
			return ext.getStaticChannel();
		} else {
			return null;
		}
	}

	public Boolean getStaticContent() {
		ChannelExt ext = getExt();
		if (ext != null) {
			return ext.getStaticContent();
		} else {
			return null;
		}
	}

	public Boolean getAccessByDir() {
		ChannelExt ext = getExt();
		if (ext != null) {
			return ext.getAccessByDir();
		} else {
			return null;
		}
	}

	public Boolean getListChild() {
		ChannelExt ext = getExt();
		if (ext != null) {
			return ext.getListChild();
		} else {
			return null;
		}
	}

	public Integer getPageSize() {
		ChannelExt ext = getExt();
		if (ext != null) {
			return ext.getPageSize();
		} else {
			return null;
		}
	}

	public String getChannelRule() {
		ChannelExt ext = getExt();
		if (ext != null) {
			return ext.getChannelRule();
		} else {
			return null;
		}
	}

	public String getContentRule() {
		ChannelExt ext = getExt();
		if (ext != null) {
			return ext.getContentRule();
		} else {
			return null;
		}
	}

	public Byte getFinalStep() {
		ChannelExt ext = getExt();
		if (ext != null) {
			return ext.getFinalStep();
		} else {
			return null;
		}
	}

	public String getLink() {
		ChannelExt ext = getExt();
		if (ext != null) {
			return ext.getLink();
		} else {
			return null;
		}
	}

	public String getTplChannel() {
		ChannelExt ext = getExt();
		if (ext != null) {
			return ext.getTplChannel();
		} else {
			return null;
		}
	}

	public String getTplContent() {
		ChannelExt ext = getExt();
		if (ext != null) {
			return ext.getTplContent();
		} else {
			return null;
		}
	}

	public Boolean getHasTitleImg() {
		ChannelExt ext = getExt();
		if (ext != null) {
			return ext.getHasTitleImg();
		} else {
			return null;
		}
	}

	public Boolean getHasContentImg() {
		ChannelExt ext = getExt();
		if (ext != null) {
			return ext.getHasContentImg();
		} else {
			return null;
		}
	}

	public Integer getTitleImgWidth() {
		ChannelExt ext = getExt();
		if (ext != null) {
			return ext.getTitleImgWidth();
		} else {
			return null;
		}
	}

	public Integer getTitleImgHeight() {
		ChannelExt ext = getExt();
		if (ext != null) {
			return ext.getTitleImgHeight();
		} else {
			return null;
		}
	}

	public Integer getContentImgWidth() {
		ChannelExt ext = getExt();
		if (ext != null) {
			return ext.getContentImgWidth();
		} else {
			return null;
		}
	}

	public Integer getContentImgHeight() {
		ChannelExt ext = getExt();
		if (ext != null) {
			return ext.getContentImgHeight();
		} else {
			return null;
		}
	}

	public String getTitleImg() {
		ChannelExt ext = getExt();
		if (ext != null) {
			return getSite().getResPath() + SPT + ext.getTitleImg();
		} else {
			return null;
		}
	}

	public String getContentImg() {
		ChannelExt ext = getExt();
		if (ext != null) {
			return ext.getContentImg();
		} else {
			return null;
		}
	}

	public String getTitle() {
		ChannelExt ext = getExt();
		if (ext != null) {
			return ext.getTitle();
		} else {
			return null;
		}
	}

	public String getKeywords() {
		ChannelExt ext = getExt();
		if (ext != null) {
			return ext.getKeywords();
		} else {
			return null;
		}
	}

	public String getDescription() {
		ChannelExt ext = getExt();
		if (ext != null) {
			return ext.getDescription();
		} else {
			return null;
		}
	}

	public Integer getCommentControl() {
		ChannelExt ext = getExt();
		if (ext != null) {
			return ext.getCommentControl();
		} else {
			return null;
		}
	}

	public Boolean getAllowUpdown() {
		ChannelExt ext = getExt();
		if (ext != null) {
			return ext.getAllowUpdown();
		} else {
			return null;
		}
	}

	public Boolean getBlank() {
		ChannelExt ext = getExt();
		if (ext != null) {
			return ext.getBlank();
		} else {
			return null;
		}
	}

	public Boolean getDisabled() {
		ChannelExt ext = getExt();
		if (ext != null) {
			return ext.getDisabled();
		} else {
			return null;
		}
	}

	public Boolean getHasContent() {
		ChannelExt ext = getExt();
		if (ext != null) {
			return ext.getHasContent();
		} else {
			return null;
		}
	}

	public Boolean getDisplay() {
		ChannelExt ext = getExt();
		if (ext != null) {
			return ext.getDisplay();
		} else {
			return null;
		}
	}

	/** 获得栏目内容
	 * 
	 * @return */
	public String getTxt() {
		ChannelTxt txt = getChannelTxt();
		if (txt != null) {
			return txt.getTxt();
		} else {
			return null;
		}
	}

	/** 获得栏目内容1
	 * 
	 * @return */
	public String getTxt1() {
		ChannelTxt txt = getChannelTxt();
		if (txt != null) {
			return txt.getTxt1();
		} else {
			return null;
		}
	}

	/** 获得栏目内容2
	 * 
	 * @return */
	public String getTxt2() {
		ChannelTxt txt = getChannelTxt();
		if (txt != null) {
			return txt.getTxt2();
		} else {
			return null;
		}
	}

	/** 获得栏目内容3
	 * 
	 * @return */
	public String getTxt3() {
		ChannelTxt txt = getChannelTxt();
		if (txt != null) {
			return txt.getTxt3();
		} else {
			return null;
		}
	}

	/** 每个站点各自维护独立的树结构
	 * 
	 * @see HibernateTree#getTreeCondition() */
	@Override
	public String getTreeCondition() {
		return "bean.site.id=" + getSite().getId();
	}

	/** @see HibernateTree#getParentId() */
	@Override
	public Integer getParentId() {
		Channel parent = getParent();
		if (parent != null) {
			return parent.getId();
		} else {
			return null;
		}
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

	public static Integer[] fetchIds(Collection<Channel> channels) {
		if (channels == null) {
			return null;
		}
		Integer[] ids = new Integer[channels.size()];
		int i = 0;
		for (Channel c : channels) {
			ids[i++] = c.getId();
		}
		return ids;
	}

	@Override
	public String getSelectTree() {
		return selectTree;
	}

	@Override
	public void setSelectTree(String selectTree) {
		this.selectTree = selectTree;
	}

	public String getName() {
		ChannelExt ext = getExt();
		if (ext != null) {
			return ext.getName();
		} else {
			return name;
		}
	}

	public void setName(String name) {
		ChannelExt ext = getExt();
		if (ext != null) {
			ext.setName(name);
		} else {
			this.name = name;
		}
	}

	@Override
	public String getTreeName() {
		ChannelExt ext = getExt();
		if (ext != null) {
			return ext.getName();
		} else {
			return name;
		}
	}

	@Override
	public Set<Channel> getTreeChild() {
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
	public Channel getTreeParent() {
		return getParent();
	}

	public boolean isTreeLeaf() {
		if (treeLeaf != null) {
			return treeLeaf;
		}
		Set<Channel> child = getChild();
		if (child != null && child.size() > 0) {
			return false;
		} else {
			return true;
		}
	}

	public void setTreeLeaf(Boolean treeLeaf) {
		this.treeLeaf = treeLeaf;
	}

	@Override
	public void setTreeChild(Set treeChild) {
		this.treeChild = treeChild;
	}

	@Override
	public Channel clone() {
		Channel channel;
		try {
			channel = (Channel) super.clone();
			if (hasChild()) {
				channel.setChild(new TreeSet<Channel>(new PriorityComparator()));
				for (Channel child : getChild()) {
					channel.getChild().add(child.clone());
				}
			}
			return channel;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/** 判断是否有子栏目 */
	public boolean hasChild() {
		if (getChild() != null && getChild().size() > 0) {
			return true;
		}
		return false;
	}
}

package com.cc.cms.service.main;

import java.util.List;
import java.util.Map;

import com.cc.cms.entity.main.Channel;
import com.cc.cms.entity.main.Group;
import com.cc.cms.entity.main.User;
import com.cc.cms.service.ICmsSvc;
import com.cc.common.page.Pagination;

/** @author wangcheng */
public interface IChannelSvc extends ICmsSvc<Channel> {

	public List<Channel> findAll(Integer webId);

	/**
	 * 获得顶级栏目
	 * 
	 * @param siteId
	 *            站点ID
	 * @param hasContentOnly
	 *            是否只获得有内容的栏目
	 * @return
	 */
	public List<Channel> getTopList(Integer siteId, boolean hasContentOnly);

	public List<Channel> getTopListByRigth(Integer userId, Integer siteId, boolean hasContentOnly);

	public List<Channel> getTopListForTag(Integer siteId, boolean hasContentOnly);

	public Pagination getTopPageForTag(Integer siteId, boolean hasContentOnly, int pageNo, int pageSize);

	public List<Channel> getChildList(Integer parentId, boolean hasContentOnly);

	public List<Channel> getChildListByRight(Integer userId, Integer siteId, Integer parentId, boolean hasContentOnly);

	public List<Channel> getChildListForTag(Integer parentId, boolean hasContentOnly);

	public Pagination getChildPageForTag(Integer parentId, boolean hasContentOnly, int pageNo, int pageSize);

	public Channel findByPath(String path, Integer siteId);

	public Channel findByPathForTag(String path, Integer siteId);

	public Channel save(Channel bean, List<Group> viewGroups, List<Group> contriGroups, List<User> users);

	public Channel update(Channel bean, List<Group> viewGroups, List<Group> contriGroups, List<User> users, Map<String, String> attr);

	/** 排序 检测是否可移动 */
	public boolean isUp(Channel bean);

	public boolean isDown(Channel bean);

	/**
	 * 排序
	 * 
	 * @return 排序后的对象
	 */
	public Channel up(Integer id);

	public Channel down(Integer id);

	public Channel getPrev(Channel bean);

	public Channel getNext(Channel bean);

	public boolean isChild(Integer pid, Integer cid);

	public List<Channel> getListForUpdate(Integer chnId);
}

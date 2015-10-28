
package com.cc.cms.dao.main;

import java.util.List;
import com.cc.cms.dao.ICmsDao;
import com.cc.cms.entity.main.Channel;
import com.cc.common.page.Pagination;

/**
 * @author wangcheng
 */
public interface IChannelDao extends ICmsDao<Channel> {

	public List<Channel> findAll(Integer webId);

	public List<Channel> getTopList(Integer siteId, boolean hasContentOnly, boolean displayOnly, boolean cacheable);

	public Pagination getTopPage(Integer siteId, boolean hasContentOnly, boolean displayOnly, boolean cacheable, int pageNo, int pageSize);

	public List<Channel> getTopListByRigth(Integer userId, Integer siteId, boolean hasContentOnly);

	public List<Channel> getChildList(Integer parentId, boolean hasContentOnly, boolean displayOnly, boolean cacheable);

	public Pagination getChildPage(Integer parentId, boolean hasContentOnly, boolean displayOnly, boolean cacheable, int pageNo, int pageSize);

	public List<Channel> getChildListByRight(Integer userId, Integer parentId, boolean hasContentOnly);

	public Channel findByPath(String path, Integer siteId, boolean cacheable);

	public Channel getPrev(Channel bean);

	public Channel getNext(Channel bean);

	public Integer getMaxPriority();

	public boolean isChild(Integer pid, Integer cid);

	public List<Channel> getChnlsAndExclude(Integer excludeNode);
}

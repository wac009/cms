
package com.cc.cms.dao.main;

import java.util.List;
import com.cc.cms.dao.ICmsDao;
import com.cc.cms.entity.main.Topic;
import com.cc.common.page.Pagination;

public interface ITopicDao extends ICmsDao<Topic> {
	public List<Topic> getList(Integer channelId, boolean recommend, Integer count, boolean cacheable);

	public Pagination getPage(Integer channelId, boolean recommend, int pageNo, int pageSize, boolean cacheable);

	public List<Topic> getGlobalTopicList();

	public List<Topic> getListByChannelId(Integer channelId);

	public List<Topic> getListByChannelIds(Integer[] channelIds);

	public int deleteContentRef(Integer id);

	public int countByChannelId(Integer channelId);
	
	public Topic getPrev(Topic bean);

	public Topic getNext(Topic bean);

	public Integer getMaxPriority();
}
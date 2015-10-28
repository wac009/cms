
package com.cc.cms.service.main;

import java.util.List;

import com.cc.cms.entity.main.Topic;
import com.cc.cms.service.ICmsSvc;
import com.cc.common.page.Pagination;

public interface ITopicSvc extends ICmsSvc<Topic> {

	public List<Topic> getListForTag(Integer channelId, boolean recommend, Integer count);

	public Pagination getPageForTag(Integer channelId, boolean recommend, int pageNo, int pageSize);

	public Pagination getPage(int pageNo, int pageSize);

	public List<Topic> getListByChannel(Integer channelId);

	@Override
	public Topic save(Topic bean);

	public Topic update(Topic bean);

	/** 排序 检测是否可移动
	 * 
	 * @return 排序后的对象 */
	public boolean isUp(Topic bean);

	public boolean isDown(Topic bean);

	/** 排序
	 * 
	 * @return 排序后的对象 */
	public Topic up(Integer id);

	public Topic down(Integer id);

	public Topic getPrev(Topic bean);

	public Topic getNext(Topic bean);
}
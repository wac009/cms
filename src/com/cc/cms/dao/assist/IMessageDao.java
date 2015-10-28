/** @author wangcheng */

package com.cc.cms.dao.assist;

import java.util.Date;
import java.util.List;
import com.cc.cms.dao.ICmsDao;
import com.cc.cms.entity.assist.Message;
import com.cc.common.page.Pagination;
import com.cc.common.util.TimeRange;

/**
 * @author wangcheng
 */
public interface IMessageDao extends ICmsDao<Message> {

	public Pagination getPage(Integer siteId, Integer sendUserId, Integer receiverUserId, String title, Date sendBeginTime, Date sendEndTime, Boolean status,
			Integer box, Boolean cacheable, int pageNo, int pageSize);

	public List<Message> getList(Integer siteId, Integer sendUserId, Integer receiverUserId, String title, Date sendBeginTime, Date sendEndTime,
			Boolean status, Integer box, Boolean cacheable);

	public Integer getCount(Integer siteId, Integer sendUserId, Integer receiverUserId, Boolean status, Integer box);

	public Message findById(Integer id);

	@Override
	public Message save(Message bean);

	public Message update(Message bean);

	public Message deleteById(Integer id);

	public Message[] deleteByIds(Integer[] ids);

	public long getDraftCountByTimeRange(Integer siteId, TimeRange timeRange);
}

package com.cc.cms.service.assist;

import java.util.Date;
import java.util.List;

import com.cc.cms.entity.assist.ReceiverMessage;
import com.cc.cms.service.ICmsSvc;
import com.cc.common.page.Pagination;
import com.cc.common.util.TimeRange;

/**
 * @author wangcheng
 */
public interface IReceiverMessageSvc extends ICmsSvc<ReceiverMessage> {

	public Pagination getPage(Integer siteId, Integer sendUserId, Integer receiverUserId, String title, Date sendBeginTime, Date sendEndTime, Boolean status,
			Integer box, Boolean cacheable, int pageNo, int pageSize);

	@SuppressWarnings("rawtypes")
	public List getList(Integer siteId, Integer sendUserId, Integer receiverUserId, String title, Date sendBeginTime, Date sendEndTime, Boolean status,
			Integer box, Boolean cacheable);

	public Integer getCount(Integer siteId, Integer sendUserId, Integer receiverUserId, Boolean status, Integer box);

	public ReceiverMessage findById(Integer id);

	@Override
	public ReceiverMessage save(ReceiverMessage bean);

	public ReceiverMessage update(ReceiverMessage bean);

	public ReceiverMessage deleteById(Integer id);

	public ReceiverMessage[] deleteByIds(Integer[] ids);
	
	public long getCountByTimeRange(Integer siteId, TimeRange timeRange) ;
}

package com.cc.cms.dao.assist;

import java.util.Date;
import java.util.List;
import com.cc.cms.dao.ICmsDao;
import com.cc.cms.entity.assist.ReceiverMessage;
import com.cc.common.page.Pagination;

/** @author wangcheng */
public interface IReceiverMessageDao  extends ICmsDao<ReceiverMessage>{

	public Pagination getPage(Integer siteId, Integer sendUserId, Integer receiverUserId, String title, Date sendBeginTime, Date sendEndTime,
			Boolean status, Integer box, Boolean cacheable, int pageNo, int pageSize);

	@SuppressWarnings("rawtypes")
	public List getList(Integer siteId, Integer sendUserId, Integer receiverUserId, String title, Date sendBeginTime, Date sendEndTime,
			Boolean status, Integer box, Boolean cacheable);
	
	public Integer getCount(Integer siteId, Integer sendUserId, Integer receiverUserId, Boolean status, Integer box);

	public ReceiverMessage findById(Integer id);

	@Override
	public ReceiverMessage save(ReceiverMessage bean);

	public ReceiverMessage update(ReceiverMessage bean);

	public ReceiverMessage deleteById(Integer id);

	public ReceiverMessage[] deleteByIds(Integer[] ids);
}
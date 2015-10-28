
package com.cc.cms.service.assist;

import java.util.Date;
import java.util.List;

import com.cc.cms.entity.assist.Message;
import com.cc.cms.service.ICmsSvc;
import com.cc.common.page.Pagination;

/**
 * @author wangcheng
 */
public interface IMessageSvc extends ICmsSvc<Message> {

	public Pagination getPage(Integer siteId, Integer sendUserId, Integer receiverUserId, String title, Date sendBeginTime, Date sendEndTime, Boolean status,
			Integer box, Boolean cacheable, int pageNo, int pageSize);
	
	public List<Message> getList(Integer siteId, Integer sendUserId, Integer receiverUserId, String title, Date sendBeginTime, Date sendEndTime,
			Boolean status, Integer box, Boolean cacheable) ;
	
	public Integer getCount(Integer siteId, Integer sendUserId, Integer receiverUserId, Boolean status, Integer box);

	public Message findById(Integer id);

	@Override
	public Message save(Message bean);

	public Message update(Message bean);

	public Message deleteById(Integer id);

	public Message[] deleteByIds(Integer[] ids);
}
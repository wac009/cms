
package com.cc.cms.service.assist.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cc.cms.dao.assist.IReceiverMessageDao;
import com.cc.cms.entity.assist.ReceiverMessage;
import com.cc.cms.service.CmsSvcImpl;
import com.cc.cms.service.assist.IReceiverMessageSvc;
import com.cc.common.page.Pagination;
import com.cc.common.util.TimeRange;

/** @author wangcheng */
@Service
@Transactional
public class ReceiverMessageSvcImpl extends CmsSvcImpl<ReceiverMessage> implements IReceiverMessageSvc {

	@Autowired
	public void setDao(IReceiverMessageDao dao) {
		super.setDao(dao);
	}

	@Override
	public IReceiverMessageDao getDao() {
		return (IReceiverMessageDao) super.getDao();
	}

	@Override
	public Pagination getPage(Integer siteId, Integer sendUserId, Integer receiverUserId, String title, Date sendBeginTime, Date sendEndTime, Boolean status,
			Integer box, Boolean cacheable, int pageNo, int pageSize) {
		return getDao().getPage(siteId, sendUserId, receiverUserId, title, sendBeginTime, sendEndTime, status, box, cacheable, pageNo, pageSize);
	}

	@Override
	@SuppressWarnings("rawtypes")
	public List getList(Integer siteId, Integer sendUserId, Integer receiverUserId, String title, Date sendBeginTime, Date sendEndTime, Boolean status,
			Integer box, Boolean cacheable) {
		return getDao().getList(siteId, sendUserId, receiverUserId, title, sendBeginTime, sendEndTime, status, box, cacheable);
	}
	
	@Override
	public Integer getCount(Integer siteId, Integer sendUserId, Integer receiverUserId, Boolean status, Integer box) {
		return getDao().getCount(siteId, sendUserId, receiverUserId, status, box);
	}

	@Override
	public ReceiverMessage findById(Integer id) {
		return getDao().findById(id);
	}

	@Override
	public ReceiverMessage save(ReceiverMessage bean) {
		return getDao().save(bean);
	}

	@Override
	public ReceiverMessage update(ReceiverMessage bean) {
		return getDao().update(bean);
	}

	@Override
	public ReceiverMessage deleteById(Integer id) {
		return getDao().deleteById(id);
	}

	@Override
	public ReceiverMessage[] deleteByIds(Integer[] ids) {
		return getDao().deleteByIds(ids);
	}
	
	@Override
	public long getCountByTimeRange(Integer siteId, TimeRange timeRange) {
		return getDao().getCountByTimeRange(siteId, timeRange);
	}
}

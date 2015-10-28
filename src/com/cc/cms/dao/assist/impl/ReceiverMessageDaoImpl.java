
package com.cc.cms.dao.assist.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.cc.cms.dao.CmsDaoImpl;
import com.cc.cms.dao.assist.IReceiverMessageDao;
import com.cc.cms.entity.assist.ReceiverMessage;
import com.cc.common.orm.hibernate3.Finder;
import com.cc.common.page.Pagination;
import com.cc.common.util.TimeRange;

/**
 * @author wangcheng
 */
@Repository
public class ReceiverMessageDaoImpl extends CmsDaoImpl<ReceiverMessage> implements IReceiverMessageDao {

	@Override
	public Pagination getPage(Integer siteId, Integer sendUserId, Integer receiverUserId, String title, Date sendBeginTime, Date sendEndTime, Boolean status,
			Integer box, Boolean cacheable, int pageNo, int pageSize) {
		String hql = " select msg from ReceiverMessage msg where 1=1 ";
		Finder finder = Finder.create(hql);
		if (siteId != null) {
			finder.append(" and msg.site.id=:siteId").setParam("siteId", siteId);
		}
		// 垃圾箱
		if (sendUserId != null && receiverUserId != null) {
			finder.append(" and ((msg.msgReceiverUser.id=:receiverUserId  and msg.msgBox =:box) or (msg.msgSendUser.id=:sendUserId  and msg.msgBox =:box) )").setParam("sendUserId", sendUserId).setParam("receiverUserId", receiverUserId).setParam("box", box);
		} else {
			if (sendUserId != null) {
				finder.append(" and msg.msgSendUser.id=:sendUserId").setParam("sendUserId", sendUserId);
			}
			if (receiverUserId != null) {
				finder.append(" and msg.msgReceiverUser.id=:receiverUserId").setParam("receiverUserId", receiverUserId);
			}
			if (box != null) {
				finder.append(" and msg.msgBox =:box").setParam("box", box);
			}
		}
		if (StringUtils.isNotBlank(title)) {
			finder.append(" and msg.msgTitle like:title").setParam("title", "%" + title + "%");
		}
		if (sendBeginTime != null) {
			finder.append(" and msg.sendTime >=:sendBeginTime").setParam("sendBeginTime", sendBeginTime);
		}
		if (sendEndTime != null) {
			finder.append(" and msg.sendTime <=:sendEndTime").setParam("sendEndTime", sendEndTime);
		}
		if (status != null) {
			if (status) {
				finder.append(" and msg.msgStatus =true");
			} else {
				finder.append(" and msg.msgStatus =false");
			}
		}
		finder.append(" order by msg.id desc");
		return find(finder, pageNo, pageSize);
	}

	@Override
	public Integer getCount(Integer siteId, Integer sendUserId, Integer receiverUserId, Boolean status, Integer box) {
		String hql = " select msg from ReceiverMessage msg where 1=1 ";
		Finder finder = Finder.create(hql);
		if (siteId != null) {
			finder.append(" and msg.site.id=:siteId").setParam("siteId", siteId);
		}
		if (sendUserId != null && receiverUserId != null) {
			finder.append(" and (msg.msgSendUser.id=:sendUserId or msg.msgReceiverUser.id=:receiverUserId)").setParam("sendUserId", sendUserId).setParam("receiverUserId", receiverUserId);
		} else {
			if (sendUserId != null) {
				finder.append(" and msg.msgSendUser.id=:sendUserId").setParam("sendUserId", sendUserId);
			}
			if (receiverUserId != null) {
				finder.append(" and msg.msgReceiverUser.id=:receiverUserId").setParam("receiverUserId", receiverUserId);
			}
		}
		if (status != null) {
			if (status) {
				finder.append(" and msg.msgStatus =true");
			} else {
				finder.append(" and msg.msgStatus =false");
			}
		}
		if (box != null) {
			finder.append(" and msg.msgBox =:box").setParam("box", box);
		}
		return find(finder).size();
	}
	
	@Override
	@SuppressWarnings("rawtypes")
	public List getList(Integer siteId, Integer sendUserId, Integer receiverUserId, String title, Date sendBeginTime, Date sendEndTime, Boolean status,
			Integer box, Boolean cacheable) {
		String hql = " select msg from ReceiverMessage msg where 1=1  ";
		Finder finder = Finder.create(hql);
		if (siteId != null) {
			finder.append(" and msg.site.id=:siteId").setParam("siteId", siteId);
		}
		// 垃圾箱
		if (sendUserId != null && receiverUserId != null) {
			finder.append(" and ((msg.msgReceiverUser.id=:receiverUserId  and msg.msgBox =:box) or (msg.msgSendUser.id=:sendUserId  and msg.msgBox =:box) )").setParam("sendUserId", sendUserId).setParam("receiverUserId", receiverUserId).setParam("box", box);
		} else {
			if (sendUserId != null) {
				finder.append(" and msg.msgSendUser.id=:sendUserId").setParam("sendUserId", sendUserId);
			}
			if (receiverUserId != null) {
				finder.append(" and msg.msgReceiverUser.id=:receiverUserId").setParam("receiverUserId", receiverUserId);
			}
			if (box != null) {
				finder.append(" and msg.msgBox =:box").setParam("box", box);
			}
		}
		if (StringUtils.isNotBlank(title)) {
			finder.append(" and msg.msgTitle like:title").setParam("title", "%" + title + "%");
		}
		if (sendBeginTime != null) {
			finder.append(" and msg.sendTime >=:sendBeginTime").setParam("sendBeginTime", sendBeginTime);
		}
		if (sendEndTime != null) {
			finder.append(" and msg.sendTime <=:sendEndTime").setParam("sendEndTime", sendEndTime);
		}
		if (status != null) {
			if (status) {
				finder.append(" and msg.msgStatus =true");
			} else {
				finder.append(" and msg.msgStatus =false");
			}
		}
		finder.append(" order by msg.id desc");
		return find(finder);
	}

	@Override
	public ReceiverMessage findById(Integer id) {
		return super.get(id);
	}

	@Override
	public ReceiverMessage save(ReceiverMessage bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
	public ReceiverMessage update(ReceiverMessage bean) {
		getSession().update(bean);
		return bean;
	}

	@Override
	public ReceiverMessage deleteById(Integer id) {
		ReceiverMessage entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	@Override
	public ReceiverMessage[] deleteByIds(Integer[] ids) {
		ReceiverMessage[] messages = new ReceiverMessage[ids.length];
		for (int i = 0; i < ids.length; i++) {
			messages[i] = get(ids[i]);
			deleteById(ids[i]);
		}
		return messages;
	}
	
	@Override
	public long getCountByTimeRange(Integer siteId, TimeRange timeRange) {
		Finder f = createCacheableFinder("select count(*) from ReceiverMessage bean");
		f.append(" where bean.site.id=:siteId").setParam("siteId", siteId);
		if (timeRange != null) {
			f.append(" and bean.sendTime >= :begin");
			f.append(" and bean.sendTime < :end");
			f.setParam("begin", timeRange.getBegin());
			f.setParam("end", timeRange.getEnd());
		}
		f.append(" and bean.msgBox=0");
		return byTimeRange(f);
	}
}

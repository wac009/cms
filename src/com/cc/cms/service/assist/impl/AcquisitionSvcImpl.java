
package com.cc.cms.service.assist.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cc.cms.dao.assist.IAcquisitionDao;
import com.cc.cms.entity.assist.Acquisition;
import com.cc.cms.entity.assist.Acquisition.AcquisitionResultType;
import com.cc.cms.entity.assist.AcquisitionHistory;
import com.cc.cms.entity.assist.AcquisitionTemp;
import com.cc.cms.entity.main.Content;
import com.cc.cms.entity.main.ContentExt;
import com.cc.cms.entity.main.ContentTxt;
import com.cc.cms.service.CmsSvcImpl;
import com.cc.cms.service.assist.IAcquisitionSvc;
import com.cc.cms.service.main.IChannelDeleteChecker;
import com.cc.cms.service.main.IChannelSvc;
import com.cc.cms.service.main.IContentSvc;
import com.cc.cms.service.main.IContentTypeSvc;
import com.cc.cms.service.main.IUserSvc;
import com.cc.cms.service.main.IWebsiteSvc;
import com.cc.common.orm.Updater;

@Service
@Transactional
public class AcquisitionSvcImpl extends CmsSvcImpl<Acquisition> implements IAcquisitionSvc, IChannelDeleteChecker {

	@Autowired
	private IChannelSvc		channelMng;
	@Autowired
	private IContentSvc		contentMng;
	@Autowired
	private IContentTypeSvc	contentTypeMng;
	@Autowired
	private IWebsiteSvc		cmsSiteMng;
	@Autowired
	private IUserSvc		cmsUserMng;

	@Autowired
	public void setDao(IAcquisitionDao dao) {
		super.setDao(dao);
	}

	@Override
	public IAcquisitionDao getDao() {
		return (IAcquisitionDao) super.getDao();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Acquisition> getList(Integer siteId) {
		return getDao().getList(siteId);
	}

	@Override
	@Transactional
	public Acquisition findById(Integer id) {
		Acquisition entity = getDao().findById(id);
		return entity;
	}

	@Override
	public void stop(Integer id) {
		Acquisition acqu = findById(id);
		if (acqu == null) {
			return;
		}
		if (acqu.getStatus() == Acquisition.START) {
			acqu.setStatus(Acquisition.STOP);
		} else if (acqu.getStatus() == Acquisition.PAUSE) {
			acqu.setCurrNum(0);
			acqu.setCurrItem(0);
			acqu.setTotalItem(0);
		}
	}

	@Override
	public void pause(Integer id) {
		Acquisition acqu = findById(id);
		if (acqu == null) {
			return;
		}
		if (acqu.getStatus() == Acquisition.START) {
			acqu.setStatus(Acquisition.PAUSE);
		}
	}

	@Override
	public Acquisition start(Integer id) {
		Acquisition acqu = findById(id);
		if (acqu == null) {
			return acqu;
		}
		acqu.setStatus(Acquisition.START);
		acqu.setStartTime(new Date());
		acqu.setEndTime(null);
		if (acqu.getCurrNum() <= 0) {
			acqu.setCurrNum(1);
		}
		if (acqu.getCurrItem() <= 0) {
			acqu.setCurrItem(1);
		}
		acqu.setTotalItem(0);
		return acqu;
	}

	@Override
	public void end(Integer id) {
		Acquisition acqu = findById(id);
		if (acqu == null) {
			return;
		}
		acqu.setStatus(Acquisition.STOP);
		acqu.setEndTime(new Date());
		acqu.setCurrNum(0);
		acqu.setCurrItem(0);
		acqu.setTotalItem(0);
		acqu.setTotalItem(0);
	}

	@Override
	public boolean isNeedBreak(Integer id, int currNum, int currItem, int totalItem) {
		Acquisition acqu = findById(id);
		if (acqu == null) {
			return true;
		} else if (acqu.isPuase()) {
			acqu.setCurrNum(currNum);
			acqu.setCurrItem(currItem);
			acqu.setTotalItem(totalItem);
			acqu.setEndTime(new Date());
			return true;
		} else if (acqu.isStop()) {
			acqu.setCurrNum(0);
			acqu.setCurrItem(0);
			acqu.setTotalItem(0);
			acqu.setEndTime(new Date());
			return true;
		} else {
			acqu.setCurrNum(currNum);
			acqu.setCurrItem(currItem);
			acqu.setTotalItem(totalItem);
			return false;
		}
	}

	@Override
	public Acquisition save(Acquisition bean, Integer channelId, Integer typeId, Integer userId, Integer siteId) {
		bean.setChannel(channelMng.findById(channelId));
		bean.setType(contentTypeMng.findById(typeId));
		bean.setUser(cmsUserMng.findById(userId));
		bean.setSite(cmsSiteMng.findById(siteId));
		bean.init();
		getDao().save(bean);
		return bean;
	}

	@Override
	public Acquisition update(Acquisition bean, Integer channelId, Integer typeId) {
		Updater<Acquisition> updater = new Updater<Acquisition>(bean);
		bean = (Acquisition) getDao().updateByUpdater(updater);
		bean.setChannel(channelMng.findById(channelId));
		bean.setType(contentTypeMng.findById(typeId));
		return bean;
	}

	@Override
	public Acquisition deleteById(Integer id) {
		Acquisition bean = getDao().deleteById(id);
		return bean;
	}

	@Override
	public Acquisition[] deleteByIds(Integer[] ids) {
		Acquisition[] beans = new Acquisition[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	@Override
	public Content saveContent(String title, String txt, Integer acquId, AcquisitionResultType resultType, AcquisitionTemp temp,
			AcquisitionHistory history) {
		Acquisition acqu = findById(acquId);
		Content c = new Content();
		c.setAcquisition(true);
		c.setStatus(Byte.parseByte("4"));
		c.setSite(acqu.getSite());
		c.setChannel(acqu.getChannel());
		c.setContentType(acqu.getType());
		ContentExt cext = new ContentExt();
		ContentTxt ctxt = new ContentTxt();
		cext.setTitle(title);
		ctxt.setTxt(txt);
		Content content=contentMng.save(c, cext, ctxt, null, null, null, null, null, null, null, null, null, acqu.getUser(), false);
		history.setTitle(title);
		history.setContent(content);
		history.setDescription(resultType.name());
		temp.setTitle(title);
		temp.setDescription(resultType.name());
		return content;
	}

	@Override
	public String checkForChannelDelete(Integer channelId) {
		if (getDao().countByChannelId(channelId) > 0) {
			return "Acquisition.error.cannotDeleteChannel";
		} else {
			return null;
		}
	}

	@Override
	public Acquisition getStarted(Integer siteId) {
		return getDao().getStarted(siteId);
	}

	@Override
	public Integer hasStarted(Integer siteId) {
		return getStarted(siteId) == null ? 0 : getMaxQueue(siteId) + 1;
	}

	@Override
	public Integer getMaxQueue(Integer siteId) {
		return getDao().getMaxQueue(siteId);
	}

	@Override
	public void addToQueue(Integer[] ids, Integer queueNum) {
		for (Integer id : ids) {
			Acquisition acqu = findById(id);
			if (acqu.getStatus() == Acquisition.START || acqu.getQueue() > 0) {
				continue;
			}
			acqu.setQueue(queueNum++);
		}
	}

	@Override
	public void cancel(Integer siteId, Integer id) {
		Acquisition acqu = findById(id);
		Integer queue = acqu.getQueue();
		for (Acquisition c : getLargerQueues(siteId, queue)) {
			c.setQueue(c.getQueue() - 1);
		}
		acqu.setQueue(0);
	}

	@Override
	public List<Acquisition> getLargerQueues(Integer siteId, Integer queueNum) {
		return getDao().getLargerQueues(siteId, queueNum);
	}

	@Override
	public Acquisition popAcquFromQueue(Integer siteId) {
		Acquisition acquisition = getDao().popAcquFromQueue(siteId);
		if (acquisition != null) {
			Integer id = acquisition.getId();
			cancel(siteId, id);
		}
		return acquisition;
	}
}
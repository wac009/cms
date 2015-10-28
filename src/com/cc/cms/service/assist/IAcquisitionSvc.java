
package com.cc.cms.service.assist;

import java.util.List;

import com.cc.cms.entity.assist.Acquisition;
import com.cc.cms.entity.assist.Acquisition.AcquisitionResultType;
import com.cc.cms.entity.assist.AcquisitionHistory;
import com.cc.cms.entity.assist.AcquisitionTemp;
import com.cc.cms.entity.main.Content;
import com.cc.cms.service.ICmsSvc;

public interface IAcquisitionSvc extends ICmsSvc<Acquisition> {

	public List<Acquisition> getList(Integer siteId);

	public Acquisition findById(Integer id);

	public void stop(Integer id);

	public void pause(Integer id);

	public Acquisition start(Integer id);

	public void end(Integer id);

	public boolean isNeedBreak(Integer id, int currNum, int currItem, int totalItem);

	public Acquisition save(Acquisition bean, Integer channelId, Integer typeId, Integer userId, Integer siteId);

	public Acquisition update(Acquisition bean, Integer channelId, Integer typeId);

	public Acquisition deleteById(Integer id);

	public Acquisition[] deleteByIds(Integer[] ids);

	public Content saveContent(String title, String txt, Integer acquId, AcquisitionResultType resultType, AcquisitionTemp temp, AcquisitionHistory history);

	public Acquisition getStarted(Integer siteId);

	public Integer getMaxQueue(Integer siteId);

	public Integer hasStarted(Integer siteId);

	public void addToQueue(Integer[] ids, Integer queueNum);

	public void cancel(Integer siteId, Integer id);

	public List<Acquisition> getLargerQueues(Integer siteId, Integer queueNum);

	public Acquisition popAcquFromQueue(Integer siteId);
}

package com.cc.cms.dao.assist;

/** @author wangcheng */
import java.util.List;
import com.cc.cms.dao.ICmsDao;
import com.cc.cms.entity.assist.Acquisition;

public interface IAcquisitionDao extends ICmsDao<Acquisition> {

	public List<Acquisition> getList(Integer siteId);

	public Acquisition findById(Integer id);

	@Override
	public Acquisition save(Acquisition bean);

	public Acquisition deleteById(Integer id);

	public int countByChannelId(Integer channelId);

	public Acquisition getStarted(Integer siteId);

	public Integer getMaxQueue(Integer siteId);

	public List<Acquisition> getLargerQueues(Integer siteId, Integer queueNum);

	public Acquisition popAcquFromQueue(Integer siteId);
}
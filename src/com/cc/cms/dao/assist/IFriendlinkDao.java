
package com.cc.cms.dao.assist;

/** @author wangcheng */
import java.util.List;
import com.cc.cms.dao.ICmsDao;
import com.cc.cms.entity.assist.Friendlink;

public interface IFriendlinkDao extends ICmsDao<Friendlink> {
	public List<Friendlink> getList(Integer siteId, Integer ctgId, Boolean enabled);

	public int countByCtgId(Integer ctgId);

	public Friendlink findById(Integer id);

	@Override
	public Friendlink save(Friendlink bean);

	public Friendlink deleteById(Integer id);
	
	public Friendlink getPrev(Friendlink bean);

	public Friendlink getNext(Friendlink bean);

	public Integer getMaxPriority();
}
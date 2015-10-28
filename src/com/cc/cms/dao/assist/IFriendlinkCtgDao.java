
package com.cc.cms.dao.assist;

/** @author wangcheng */
import java.util.List;
import com.cc.cms.dao.ICmsDao;
import com.cc.cms.entity.assist.FriendlinkCtg;

public interface IFriendlinkCtgDao extends ICmsDao<FriendlinkCtg> {

	public List<FriendlinkCtg> getList(Integer siteId);

	public int countBySiteId(Integer siteId);

	public FriendlinkCtg findById(Integer id);

	@Override
	public FriendlinkCtg save(FriendlinkCtg bean);

	public FriendlinkCtg deleteById(Integer id);

	public FriendlinkCtg getPrev(FriendlinkCtg bean);

	public FriendlinkCtg getNext(FriendlinkCtg bean);

	public Integer getMaxPriority();
}
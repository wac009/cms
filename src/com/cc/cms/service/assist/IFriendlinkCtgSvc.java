
package com.cc.cms.service.assist;

import java.util.List;

import com.cc.cms.entity.assist.FriendlinkCtg;
import com.cc.cms.service.ICmsSvc;

public interface IFriendlinkCtgSvc extends ICmsSvc<FriendlinkCtg> {

	public List<FriendlinkCtg> getList(Integer siteId);

	public int countBySiteId(Integer siteId);

	public FriendlinkCtg findById(Integer id);

	@Override
	public FriendlinkCtg save(FriendlinkCtg bean);

	public FriendlinkCtg update(FriendlinkCtg bean);

	public void updateFriendlinkCtgs(Integer[] ids, String[] names, Integer[] priorities);

	public FriendlinkCtg deleteById(Integer id);

	public FriendlinkCtg[] deleteByIds(Integer[] ids);

	/**
	 * 排序
	 */
	public boolean isUp(FriendlinkCtg bean);

	public boolean isDown(FriendlinkCtg bean);

	public FriendlinkCtg up(Integer id);

	public FriendlinkCtg down(Integer id);

	public FriendlinkCtg getPrev(FriendlinkCtg bean);

	public FriendlinkCtg getNext(FriendlinkCtg bean);
}
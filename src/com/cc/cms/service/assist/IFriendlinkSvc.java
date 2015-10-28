
package com.cc.cms.service.assist;

import java.util.List;

import com.cc.cms.entity.assist.Friendlink;
import com.cc.cms.service.ICmsSvc;

public interface IFriendlinkSvc extends ICmsSvc<Friendlink> {

	public List<Friendlink> getList(Integer siteId, Integer ctgId, Boolean enabled);

	public int countByCtgId(Integer ctgId);

	public Friendlink findById(Integer id);

	public int updateViews(Integer id);

	public Friendlink save(Friendlink bean, Integer ctgId);

	public Friendlink update(Friendlink bean, Integer ctgId);

	public void updatePriority(Integer[] ids, Integer[] priorities);

	public Friendlink deleteById(Integer id);

	public Friendlink[] deleteByIds(Integer[] ids);

	/** 排序 检测是否可移动 */
	public boolean isUp(Friendlink bean);

	public boolean isDown(Friendlink bean);

	/**
	 * 排序
	 * 
	 * @return 排序后的对象
	 */
	public Friendlink up(Integer id);

	public Friendlink down(Integer id);

	public Friendlink getPrev(Friendlink bean);

	public Friendlink getNext(Friendlink bean);
}
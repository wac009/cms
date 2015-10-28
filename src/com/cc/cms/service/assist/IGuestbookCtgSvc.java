
package com.cc.cms.service.assist;

import java.util.List;

import com.cc.cms.entity.assist.GuestbookCtg;
import com.cc.cms.service.ICmsSvc;

public interface IGuestbookCtgSvc extends ICmsSvc<GuestbookCtg> {

	public List<GuestbookCtg> getList(Integer siteId);

	public GuestbookCtg findById(Integer id);

	@Override
	public GuestbookCtg save(GuestbookCtg bean);

	public GuestbookCtg update(GuestbookCtg bean);

	public GuestbookCtg deleteById(Integer id);

	public GuestbookCtg[] deleteByIds(Integer[] ids);

	/**
	 * 排序
	 */
	public boolean isUp(GuestbookCtg bean);

	public boolean isDown(GuestbookCtg bean);

	public GuestbookCtg up(Integer id);

	public GuestbookCtg down(Integer id);

	public GuestbookCtg getPrev(GuestbookCtg bean);

	public GuestbookCtg getNext(GuestbookCtg bean);
}
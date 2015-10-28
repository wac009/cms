
package com.cc.cms.dao.assist;

/** @author wangcheng */
import java.util.List;
import com.cc.cms.dao.ICmsDao;
import com.cc.cms.entity.assist.GuestbookCtg;

public interface IGuestbookCtgDao extends ICmsDao<GuestbookCtg> {

	public List<GuestbookCtg> getList(Integer siteId);

	public GuestbookCtg findById(Integer id);

	@Override
	public GuestbookCtg save(GuestbookCtg bean);

	public GuestbookCtg deleteById(Integer id);

	public GuestbookCtg getPrev(GuestbookCtg bean);

	public GuestbookCtg getNext(GuestbookCtg bean);

	public Integer getMaxPriority();
}
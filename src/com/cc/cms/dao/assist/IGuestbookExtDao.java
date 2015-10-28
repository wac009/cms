/** @author wangcheng */

package com.cc.cms.dao.assist;

import com.cc.cms.dao.ICmsDao;
import com.cc.cms.entity.assist.GuestbookExt;

public interface IGuestbookExtDao extends ICmsDao<GuestbookExt> {

	public GuestbookExt findById(Integer id);

	@Override
	public GuestbookExt save(GuestbookExt bean);

	public GuestbookExt deleteById(Integer id);
}
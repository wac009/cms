
package com.cc.cms.service.assist;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.cc.cms.entity.assist.Guestbook;
import com.cc.cms.entity.assist.GuestbookExt;
import com.cc.cms.entity.main.User;
import com.cc.cms.service.ICmsSvc;
import com.cc.common.page.Pagination;

public interface IGuestbookSvc extends ICmsSvc<Guestbook> {

	public Pagination getPage(Integer siteId, Integer ctgId, Integer userId, Boolean recommend, Boolean checked, boolean desc, boolean cacheable, int pageNo,
			int pageSize);

	@Transactional(readOnly = true)
	public List<Guestbook> getList(Integer siteId, Integer ctgId, Boolean recommend, Boolean checked, boolean desc, boolean cacheable, int first, int max);

	public Guestbook findById(Integer id);

	public Guestbook save(Guestbook bean, GuestbookExt ext, Integer ctgId, String ip);

	public Guestbook save(User member, Integer siteId, Integer ctgId, String ip, String title, String content, String email, String phone, String qq);

	public Guestbook update(Guestbook bean, GuestbookExt ext, Integer ctgId);

	public Guestbook deleteById(Integer id);

	public Guestbook[] deleteByIds(Integer[] ids);
}
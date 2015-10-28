
package com.cc.cms.service.assist;

import com.cc.cms.entity.assist.Guestbook;
import com.cc.cms.entity.assist.GuestbookExt;
import com.cc.cms.service.ICmsSvc;

public interface IGuestbookExtSvc extends ICmsSvc<GuestbookExt> {

	public GuestbookExt save(GuestbookExt ext, Guestbook guestbook);

	public GuestbookExt update(GuestbookExt ext);
}
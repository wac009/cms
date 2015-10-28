
package com.cc.cms.action.admin.assist;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.cc.cms.action.CmsAct;
import com.cc.cms.entity.assist.Guestbook;
import com.cc.cms.entity.assist.GuestbookCtg;
import com.cc.cms.service.assist.IGuestbookCtgSvc;
import com.cc.cms.service.assist.IGuestbookSvc;
import com.cc.common.orm.hibernate3.OrderBy;

/**
 * @author wangcheng
 */
@SuppressWarnings({ "rawtypes", "serial" })
@Scope("prototype")
@Controller("web.action.admin.guestbookAct")
public class GuestbookAct extends CmsAct {

	private static final Logger	log			= LoggerFactory.getLogger(GuestbookAct.class);
	@Autowired
	private IGuestbookSvc		guestbookSvc;
	@Autowired
	private IGuestbookCtgSvc	guestbookCtgSvc;
	private List<GuestbookCtg>	guestbookCtgs;
	private Guestbook			guestbook;
	/** 查询条件 */
	private List<String>		property	= new ArrayList<String>();
	private List<Object>		value		= new ArrayList<Object>();

	@Override
	public String list() {
		property.add("site.id");
		value.add(getWebId());
		pagination = guestbookSvc.findByProperty(pageNo, getCookieCount(), property, value, OrderBy.desc("id"));
		return LIST;
	}

	@Override
	public String add() {
		initCtgs();
		return ADD;
	}

	public String save() {
		guestbook.setSite(getWeb());
		guestbookSvc.save(guestbook, guestbook.getExt(), guestbook.getCtg().getId(), getIp());
		log.info("添加成功");
		return list();
	}

	@Override
	public String edit() {
		initCtgs();
		guestbook = guestbookSvc.findById(id);
		return EDIT;
	}

	public String update() {
		guestbookSvc.updateDefault(guestbook);
		return list();
	}
	
	public String delete() {
		vldBatch();
		guestbookSvc.deleteByIds(ids);
		return list();
	}

	private void initCtgs() {
		property.add("site.id");
		value.add(getWebId());
		guestbookCtgs = guestbookCtgSvc.findByProperty(property, value);
	}

	public Guestbook getGuestbook() {
		return guestbook;
	}

	public void setGuestbook(Guestbook guestbook) {
		this.guestbook = guestbook;
	}

	public List<GuestbookCtg> getGuestbookCtgs() {
		return guestbookCtgs;
	}

	public void setGuestbookCtgs(List<GuestbookCtg> guestbookCtgs) {
		this.guestbookCtgs = guestbookCtgs;
	}
}

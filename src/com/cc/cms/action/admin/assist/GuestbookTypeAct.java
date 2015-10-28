
package com.cc.cms.action.admin.assist;

import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;

import com.cc.cms.action.*;
import com.cc.cms.entity.assist.*;
import com.cc.cms.service.assist.*;

/**
 * @author wangcheng
 */
@SuppressWarnings({ "rawtypes", "serial" })
@Scope("prototype")
@Controller("web.action.admin.guestbookTypeAct")
public class GuestbookTypeAct extends CmsAct {

	@Autowired
	private IGuestbookCtgSvc	guestbookCtgSvc;
	private GuestbookCtg		guestbookType;

	@Override
	public String list() {
		list = guestbookCtgSvc.findAll();
		return LIST;
	}

	@Override
	public String add() {
		return ADD;
	}

	public String save() {
		guestbookType.setSite(getWeb());
		guestbookCtgSvc.save(guestbookType);
		return list();
	}

	@Override
	public String edit() {
		guestbookType = guestbookCtgSvc.findById(id);
		return EDIT;
	}

	public String update() {
		guestbookCtgSvc.updateDefault(guestbookType);
		return list();
	}

	public String delete() {
		vldBatch();
		guestbookCtgSvc.deleteByIds(ids);
		return list();
	}

	/**
	 * 排序 是否可向上移动
	 */
	public boolean isUp(GuestbookCtg bean) {
		return guestbookCtgSvc.isUp(bean);
	}

	public boolean isDown(GuestbookCtg bean) {
		return guestbookCtgSvc.isDown(bean);
	}

	public String up() {
		guestbookCtgSvc.up(id);
		addActionMessage("排序成功");
		setId(null);
		return list();
	}

	public String down() {
		guestbookCtgSvc.down(id);
		addActionMessage("排序成功");
		setId(null);
		return list();
	}

	public GuestbookCtg getGuestbookType() {
		return guestbookType;
	}

	public void setGuestbookType(GuestbookCtg guestbookType) {
		this.guestbookType = guestbookType;
	}
}

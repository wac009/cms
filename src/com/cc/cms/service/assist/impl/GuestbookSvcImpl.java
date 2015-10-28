
package com.cc.cms.service.assist.impl;

import static com.cc.cms.statistic.Statistic.THISMONTH;
import static com.cc.cms.statistic.Statistic.THISWEEK;
import static com.cc.cms.statistic.Statistic.THISYEAR;
import static com.cc.cms.statistic.Statistic.TODAY;
import static com.cc.cms.statistic.Statistic.YESTERDAY;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cc.cms.dao.assist.IGuestbookDao;
import com.cc.cms.entity.assist.Guestbook;
import com.cc.cms.entity.assist.GuestbookExt;
import com.cc.cms.entity.main.User;
import com.cc.cms.service.CmsSvcImpl;
import com.cc.cms.service.assist.IGuestbookCtgSvc;
import com.cc.cms.service.assist.IGuestbookExtSvc;
import com.cc.cms.service.assist.IGuestbookSvc;
import com.cc.cms.service.main.IWebsiteSvc;
import com.cc.cms.statistic.Statistic;
import com.cc.common.orm.Updater;
import com.cc.common.page.Pagination;
import com.cc.common.util.TimeRange;

@Service
@Transactional
public class GuestbookSvcImpl extends CmsSvcImpl<Guestbook> implements IGuestbookSvc {

	@Autowired
	private IGuestbookCtgSvc	cmsGuestbookCtgMng;
	@Autowired
	private IGuestbookExtSvc	cmsGuestbookExtMng;
	@Autowired
	private IWebsiteSvc			cmsSiteMng;

	@Autowired
	public void setDao(IGuestbookDao dao) {
		super.setDao(dao);
	}

	@Override
	public IGuestbookDao getDao() {
		return (IGuestbookDao) super.getDao();
	}

	@Override
	@Transactional(readOnly = true)
	public Pagination getPage(Integer siteId, Integer ctgId, Integer userId, Boolean recommend, Boolean checked, boolean desc, boolean cacheable, int pageNo,
			int pageSize) {
		return getDao().getPage(siteId, ctgId, userId, recommend, checked, desc, cacheable, pageNo, pageSize);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Guestbook> getList(Integer siteId, Integer ctgId, Boolean recommend, Boolean checked, boolean desc, boolean cacheable, int first, int max) {
		return getDao().getList(siteId, ctgId, recommend, checked, desc, cacheable, first, max);
	}

	@Override
	@Transactional(readOnly = true)
	public Guestbook findById(Integer id) {
		Guestbook entity = getDao().findById(id);
		return entity;
	}

	@Override
	public Guestbook save(Guestbook bean, GuestbookExt ext, Integer ctgId, String ip) {
		bean.setCtg(cmsGuestbookCtgMng.findById(ctgId));
		bean.setIp(ip);
		bean.setCreateTime(new Timestamp(System.currentTimeMillis()));
		bean.init();
		getDao().save(bean);
		cmsGuestbookExtMng.save(ext, bean);
		return bean;
	}

	@Override
	public Guestbook save(User member, Integer siteId, Integer ctgId, String ip, String title, String content, String email, String phone, String qq) {
		Guestbook guestbook = new Guestbook();
		guestbook.setMember(member);
		guestbook.setSite(cmsSiteMng.findById(siteId));
		guestbook.setIp(ip);
		GuestbookExt ext = new GuestbookExt();
		ext.setTitle(title);
		ext.setContent(content);
		ext.setEmail(email);
		ext.setPhone(phone);
		ext.setQq(qq);
		return save(guestbook, ext, ctgId, ip);
	}

	@Override
	public Guestbook update(Guestbook bean, GuestbookExt ext, Integer ctgId) {
		Updater<Guestbook> updater = new Updater<Guestbook>(bean);
		bean = (Guestbook) getDao().updateByUpdater(updater);
		bean.setCtg(cmsGuestbookCtgMng.findById(ctgId));
		cmsGuestbookExtMng.update(ext);
		return bean;
	}

	@Override
	public Guestbook deleteById(Integer id) {
		Guestbook bean = getDao().deleteById(id);
		return bean;
	}

	@Override
	public Guestbook[] deleteByIds(Integer[] ids) {
		Guestbook[] beans = new Guestbook[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	@Override
	public Map<String, List<Statistic>> getWelcomeData(Integer siteId) {
		Map<String, List<Statistic>> map = new HashMap<String, List<Statistic>>();
		map.put("today", getListByTimeRange(siteId, getTimeRange(TODAY)));
		map.put("yesterday", getListByTimeRange(siteId, getTimeRange(YESTERDAY)));
		map.put("thisweek", getListByTimeRange(siteId, getTimeRange(THISWEEK)));
		map.put("thismonth", getListByTimeRange(siteId, getTimeRange(THISMONTH)));
		map.put("thisyear", getListByTimeRange(siteId, getTimeRange(THISYEAR)));
		map.put("total", getListByTimeRange(siteId, getTimeRange(-1)));
		return map;
	}

	private List<Statistic> getListByTimeRange(Integer siteId, TimeRange timeRange) {
		List<Statistic> list = new ArrayList<Statistic>();
		list.add(new Statistic(getCountByTimeRange(siteId, timeRange)));
		list.add(new Statistic(getCheckedCountByTimeRange(siteId, timeRange)));
		return list;
	}

	private long getCountByTimeRange(Integer siteId, TimeRange timeRange) {
		return getDao().getCountByTimeRange(siteId, timeRange);
	}

	private long getCheckedCountByTimeRange(Integer siteId, TimeRange timeRange) {
		return getDao().getCheckedCountByTimeRange(siteId, timeRange);
	}
}
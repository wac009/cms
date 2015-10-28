
package com.cc.cms.service.main.impl;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cc.cms.dao.main.ILogDao;
import com.cc.cms.entity.main.Log;
import com.cc.cms.entity.main.Site;
import com.cc.cms.entity.main.User;
import com.cc.cms.service.CmsSvcImpl;
import com.cc.cms.service.main.ILogSvc;

/**
 * @author wangcheng
 */
@Service
@Transactional
public class LogSvcImpl extends CmsSvcImpl<Log> implements ILogSvc {
	@Autowired
	public void setDao(ILogDao dao) {
		super.setDao(dao);
	}

	@Override
	protected ILogDao getDao() {
		return (ILogDao) super.getDao();
	}
	public Log save(Integer category, Site site, User user, String url, String ip, Date date, String title, String content) {
		Log log = new Log();
		log.setSite(site);
		log.setUser(user);
		log.setCategory(category);
		log.setIp(ip);
		log.setLogtime(date);
		log.setUrl(url);
		log.setTitle(title);
		log.setContent(content);
		save(log);
		return log;
	}

	@Override
	public Log loginSuccess(User user, String title, String ip, String uri) {
		Date date = new Date();
		Log log = save(Log.LOGIN_SUCCESS, null, user, uri, ip, date, title, null);
		return log;
	}

	@Override
	public Log loginFailure(String title, String content, String ip, String uri) {
		Date date = new Date();
		Log log = save(Log.LOGIN_FAILURE, null, null, uri, ip, date, title, content);
		return log;
	}

	@Override
	public Log operating(String title, String content, String ip, String uri, Site site, User user) {
		Date date = new Date();
		Log log = save(Log.OPERATING, site, user, uri, ip, date, title, content);
		return log;
	}
	@Override
	public int deleteBatch(Integer category, Integer siteId, Integer days) {
		Date date = null;
		if (days != null && days > 0) {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, -days);
			date = cal.getTime();
		}
		return getDao().deleteBatch(category, siteId, date);
	}
}

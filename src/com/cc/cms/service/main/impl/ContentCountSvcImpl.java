
package com.cc.cms.service.main.impl;

import java.util.Calendar;
import java.util.Date;

import net.sf.ehcache.Ehcache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cc.cms.dao.main.IContentCountDao;
import com.cc.cms.entity.main.Config;
import com.cc.cms.entity.main.Content;
import com.cc.cms.entity.main.ContentCount;
import com.cc.cms.service.CmsSvcImpl;
import com.cc.cms.service.main.IConfigSvc;
import com.cc.cms.service.main.IContentCountSvc;

@Service
@Transactional
public class ContentCountSvcImpl extends CmsSvcImpl<ContentCount> implements IContentCountSvc {

	@Autowired
	public void setDao(IContentCountDao dao) {
		super.setDao(dao);
	}

	@Override
	protected IContentCountDao getDao() {
		return (IContentCountDao) super.getDao();
	}

	// 间隔时间
	private int			interval	= 60 * 60 * 1000;	// 一小时
	private IConfigSvc	configSvc;

	@Override
	public int contentUp(Integer id) {
		ContentCount c = getDao().get(id);
		if (c == null) {
			return 0;
		}
		int count = c.getUps() + 1;
		c.setUps(count);
		c.setUpsMonth(c.getUpsMonth() + 1);
		c.setUpsWeek((short) (c.getUpsWeek() + 1));
		c.setUpsDay((short) (c.getUpsDay() + 1));
		return count;
	}

	@Override
	public int contentDown(Integer id) {
		ContentCount c = getDao().get(id);
		if (c == null) {
			return 0;
		}
		int count = c.getDowns() + 1;
		c.setDowns(count);
		return count;
	}

	@Override
	public void downloadCount(Integer contentId) {
		ContentCount c = findById(contentId);
		c.setDownloads(c.getDownloads() + 1);
		c.setDownloadsMonth(c.getDownloadsMonth() + 1);
		c.setDownloadsWeek((short) (c.getCommentsWeek() + 1));
		c.setDownloadsDay((short) (c.getDownloadsDay() + 1));

		c.setDownloads(10);
		c.setDownloadsMonth(10);
		c.setDownloadsWeek((short) (10));
		c.setDownloadsDay((short) (10));
	}

	@Override
	public void commentCount(Integer contentId) {
		ContentCount c = findById(contentId);
		c.setComments(c.getComments() + 1);
		c.setCommentsMonth(c.getCommentsMonth() + 1);
		c.setCommentsWeek((short) (c.getCommentsWeek() + 1));
		c.setCommentsDay((short) (c.getCommentsDay() + 1));
	}

	@Override
	public int freshCacheToDB(Ehcache cache) {
		Config config = configSvc.get();
		clearCount(config);
		int count = getDao().freshCacheToDB(cache);
		copyCount(config);
		return count;
	}

	private int clearCount(Config config) {
		Calendar curr = Calendar.getInstance();
		Calendar last = Calendar.getInstance();
		last.setTime(config.getCountClearTime());
		int currDay = curr.get(Calendar.DAY_OF_YEAR);
		int lastDay = last.get(Calendar.DAY_OF_YEAR);
		if (currDay != lastDay) {
			int currWeek = curr.get(Calendar.WEEK_OF_YEAR);
			int lastWeek = last.get(Calendar.WEEK_OF_YEAR);
			int currMonth = curr.get(Calendar.MONTH);
			int lastMonth = last.get(Calendar.MONTH);
			configSvc.updateCountClearTime(curr.getTime());
			return getDao().clearCount(currWeek != lastWeek, currMonth != lastMonth);
		} else {
			return 0;
		}
	}

	private int copyCount(Config config) {
		long curr = System.currentTimeMillis();
		long last = config.getCountCopyTime().getTime();
		if (curr > interval + last) {
			configSvc.updateCountCopyTime(new Date(curr));
			return getDao().copyCount();
		} else {
			return 0;
		}
	}

	@Override
	public ContentCount save(ContentCount count, Content content) {
		count.setContent(content);
		count.init();
		getDao().save(count);
		content.setContentCount(count);
		return count;
	}
}

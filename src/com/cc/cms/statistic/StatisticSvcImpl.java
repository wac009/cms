
package com.cc.cms.statistic;

import static com.cc.cms.statistic.Statistic.AVGVIEWS;
import static com.cc.cms.statistic.Statistic.COMMENT;
import static com.cc.cms.statistic.Statistic.CONTENT;
import static com.cc.cms.statistic.Statistic.GUESTBOOK;
import static com.cc.cms.statistic.Statistic.JOIN;
import static com.cc.cms.statistic.Statistic.MEMBER;
import static com.cc.cms.statistic.Statistic.PV;
import static com.cc.cms.statistic.Statistic.THISMONTH;
import static com.cc.cms.statistic.Statistic.THISWEEK;
import static com.cc.cms.statistic.Statistic.THISYEAR;
import static com.cc.cms.statistic.Statistic.TIMEPATTERN;
import static com.cc.cms.statistic.Statistic.TODAY;
import static com.cc.cms.statistic.Statistic.UNIQUEIP;
import static com.cc.cms.statistic.Statistic.UNIQUEVISITOR;
import static com.cc.cms.statistic.Statistic.YESTERDAY;
import static com.cc.common.util.ArithmeticUtils.dividend;
import static java.util.Calendar.DATE;
import static java.util.Calendar.DAY_OF_WEEK;
import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cc.cms.statistic.Statistic.StatisticModel;
import com.cc.common.page.Pagination;
import com.cc.common.util.DateFormatUtils;
import com.cc.common.util.TimeRange;

@Service
@Transactional(readOnly = true)
public class StatisticSvcImpl implements IStatisticSvc {

	@Autowired
	private IStatisticDao	dao;

	@Override
	public List<Statistic> statisticByModel(int type, StatisticModel statisticModel, Integer year, Integer month, Integer day, Map<String, Object> restrictions) {
		Calendar calendar;
		if (month == null) {
			month = 0;
		} else {
			month = month - 1;
		}
		if (day == null) {
			day = 1;
		}
		if (year == null) {
			calendar = new GregorianCalendar();
		} else {
			calendar = new GregorianCalendar(year, month, day);
		}
		return statisticByModel(type, statisticModel, calendar, restrictions);
	}

	private List<Statistic> statisticByModel(int type, StatisticModel statisticModel, Calendar calendar, Map<String, Object> restrictions) {
		switch (statisticModel) {
			case day: {
				return statisticByDay(type, calendar, restrictions);
			}
			case week: {
				return statisticByWeek(type, calendar, restrictions);
			}
			case month: {
				return statisticByMonth(type, calendar, restrictions);
			}
			case year: {
				return statisticByYear(type, calendar, restrictions);
			}
		}
		return new ArrayList<Statistic>();
	}

	private List<Statistic> statisticByDay(int type, Calendar calendar, Map<String, Object> restrictions) {
		calendar = clearTime(calendar);
		List<Statistic> list = new ArrayList<Statistic>();
		long total = 0, count = 0;
		Date begin, end;
		Calendar clone = (Calendar) calendar.clone();
		total = statistic(type, getTimeRange(TODAY, clone), restrictions);
		for (int i = 0; i < 24; i++) {
			calendar.set(HOUR_OF_DAY, i);
			begin = calendar.getTime();
			calendar.set(HOUR_OF_DAY, i + 1);
			end = calendar.getTime();
			count = statistic(type, TimeRange.getInstance(begin, end), restrictions);
			Statistic bean = new Statistic(format(i), count, total);
			list.add(bean);
		}
		return list;
	}

	private List<Statistic> statisticByWeek(int type, Calendar calendar, Map<String, Object> restrictions) {
		calendar = clearTime(calendar);
		flush(calendar);
		List<Statistic> list = new ArrayList<Statistic>();
		long total = 0, count = 0;
		Date begin, end;
		Calendar clone = (Calendar) calendar.clone();
		total = statistic(type, getTimeRange(THISWEEK, clone), restrictions);
		for (int i = 1; i <= 7; i++) {
			calendar.set(DAY_OF_WEEK, i);
			begin = calendar.getTime();
			if (i == 7) {
				calendar.add(DAY_OF_WEEK, 1);
			} else {
				calendar.set(DAY_OF_WEEK, i + 1);
			}
			end = calendar.getTime();
			count = statistic(type, TimeRange.getInstance(begin, end), restrictions);
			Statistic bean = new Statistic(String.valueOf(i), count, total);
			list.add(bean);
		}
		return list;
	}

	private List<Statistic> statisticByMonth(int type, Calendar calendar, Map<String, Object> restrictions) {
		List<Statistic> list = new ArrayList<Statistic>();
		int year = getYear(calendar);
		int month = getMonth(calendar);
		long total = 0, count = 0;
		int day = 1, days;
		Date begin, end;
		calendar = new GregorianCalendar(year, month, day);
		total = statistic(type, getTimeRange(THISMONTH, (Calendar) calendar.clone()), restrictions);
		Calendar clone = (Calendar) calendar.clone();
		clone.set(MONTH, month + 1);
		end = clone.getTime();
		clone.add(DATE, -1);
		days = getDay(clone);
		for (int i = 1; i <= days; i++) {
			calendar.set(DATE, i);
			begin = calendar.getTime();
			calendar.set(DATE, i + 1);
			end = calendar.getTime();
			count = statistic(type, TimeRange.getInstance(begin, end), restrictions);
			Statistic bean = new Statistic(String.valueOf(i), count, total);
			list.add(bean);
		}
		return list;
	}

	private List<Statistic> statisticByYear(int type, Calendar calendar, Map<String, Object> restrictions) {
		List<Statistic> list = new ArrayList<Statistic>();
		int year = getYear(calendar);
		long total = 0, count = 0;
		int day = 1, month = 0;
		Date begin, end;
		calendar = new GregorianCalendar(year, month, day);
		Calendar clone = (Calendar) calendar.clone();
		total = statistic(type, getTimeRange(THISYEAR, clone), restrictions);
		for (int i = 0; i < 12; i++) {
			calendar.set(MONTH, i);
			begin = calendar.getTime();
			calendar.set(MONTH, i + 1);
			end = calendar.getTime();
			count = statistic(type, TimeRange.getInstance(begin, end), restrictions);
			Statistic bean = new Statistic(String.valueOf(i + 1), count, total);
			list.add(bean);
		}
		return list;
	}

	private long statistic(int type, TimeRange timeRange, Map<String, Object> restrictions) {
		switch (type) {
			case MEMBER: {
				return dao.memberStatistic(timeRange);
			}
			case CONTENT: {
				return dao.contentStatistic(timeRange, restrictions);
			}
			case COMMENT: {
				return dao.commentStatistic(timeRange, restrictions);
			}
			case GUESTBOOK: {
				return dao.guestbookStatistic(timeRange, restrictions);
			}
		}
		return 0;
	}

	private List<Statistic> pvStatistic(Integer siteId) {
		List<Statistic> list = new ArrayList<Statistic>();
		Date begin;
		long count;
		Calendar calendar = new GregorianCalendar();
		calendar = clearTime(calendar);
		begin = calendar.getTime();
		count = dao.getPvCountByTimeRange(siteId, getTimeRange(TODAY));
		Statistic today = new Statistic("今日", count);
		today.setVice(DateFormatUtils.formatDate(begin));
		list.add(today);
		calendar.add(DATE, -1);
		begin = calendar.getTime();
		count = dao.getPvCountByTimeRange(siteId, getTimeRange(YESTERDAY));
		Statistic yesterday = new Statistic("昨日", count);
		yesterday.setVice(DateFormatUtils.formatDate(begin));
		list.add(yesterday);
		count = avg(dao.getPvCountByGroup(siteId));
		Statistic avg = new Statistic("每日平均", count);
		list.add(avg);
		Object[] objs = max(dao.getPvCountByGroup(siteId));
		count = (Integer) objs[0];
		Statistic max = new Statistic("历史最高", count);
		max.setVice((String) objs[1]);
		list.add(max);
		count = dao.getPvCount(siteId);
		Statistic total = new Statistic("历史累计", count);
		list.add(total);
		return list;
	}

	private List<Statistic> uniqueIpStatistic(Integer siteId) {
		List<Statistic> list = new ArrayList<Statistic>();
		Date begin;
		long count;
		Calendar calendar = new GregorianCalendar();
		calendar = clearTime(calendar);
		begin = calendar.getTime();
		count = dao.getUniqueIpCountByTimeRange(siteId, getTimeRange(TODAY));
		Statistic today = new Statistic("今日", count);
		today.setVice(DateFormatUtils.formatDate(begin));
		list.add(today);
		calendar.add(DATE, -1);
		begin = calendar.getTime();
		count = dao.getUniqueIpCountByTimeRange(siteId, getTimeRange(YESTERDAY));
		Statistic yesterday = new Statistic("昨日", count);
		yesterday.setVice(DateFormatUtils.formatDate(begin));
		list.add(yesterday);
		count = avg(dao.getUniqueIpCountByGroup(siteId));
		Statistic avg = new Statistic("每日平均", count);
		list.add(avg);
		Object[] objs = max(dao.getUniqueIpCountByGroup(siteId));
		count = (Integer) objs[0];
		Statistic max = new Statistic("历史最高", count);
		max.setVice((String) objs[1]);
		list.add(max);
		count = dao.getUniqueIpCount(siteId);
		Statistic total = new Statistic("历史累计", count);
		list.add(total);
		return list;
	}

	private List<Statistic> uniqueVisitorStatistic(Integer siteId) {
		List<Statistic> list = new ArrayList<Statistic>();
		Date begin;
		long count;
		Calendar calendar = new GregorianCalendar();
		calendar = clearTime(calendar);
		begin = calendar.getTime();
		count = dao.getUniqueVisitorCountByTimeRange(siteId, getTimeRange(TODAY));
		Statistic today = new Statistic("今日", count);
		today.setVice(DateFormatUtils.formatDate(begin));
		list.add(today);
		calendar.add(DATE, -1);
		begin = calendar.getTime();
		count = dao.getUniqueVisitorCountByTimeRange(siteId, getTimeRange(YESTERDAY));
		Statistic yesterday = new Statistic("昨日", count);
		yesterday.setVice(DateFormatUtils.formatDate(begin));
		list.add(yesterday);
		count = avg(dao.getUniqueVisitorCountByGroup(siteId));
		Statistic avg = new Statistic("每日平均", count);
		list.add(avg);
		Object[] objs = max(dao.getUniqueVisitorCountByGroup(siteId));
		count = (Integer) objs[0];
		Statistic max = new Statistic("历史最高", count);
		max.setVice((String) objs[1]);
		list.add(max);
		count = dao.getUniqueVisitorCount(siteId);
		Statistic total = new Statistic("历史累计", count);
		list.add(total);
		return list;
	}

	private List<Statistic> avgViewsStatistic(Integer siteId) {
		List<Statistic> list = new ArrayList<Statistic>();
		Date begin;
		long count, pvs, visitors;
		Calendar calendar = new GregorianCalendar();
		calendar = clearTime(calendar);
		begin = calendar.getTime();
		pvs = dao.getPvCountByTimeRange(siteId, getTimeRange(TODAY));
		visitors = dao.getUniqueVisitorCountByTimeRange(siteId, getTimeRange(TODAY));
		Statistic today = new Statistic("今日", pvs / dividend(visitors));
		today.setVice(DateFormatUtils.formatDate(begin));
		list.add(today);
		calendar.add(DATE, -1);
		begin = calendar.getTime();
		pvs = dao.getPvCountByTimeRange(siteId, getTimeRange(YESTERDAY));
		visitors = dao.getUniqueVisitorCountByTimeRange(siteId, getTimeRange(YESTERDAY));
		Statistic yesterday = new Statistic("昨日", pvs / dividend(visitors));
		yesterday.setVice(DateFormatUtils.formatDate(begin));
		list.add(yesterday);
		count = avg(dao.getPvCountByGroup(siteId), dao.getUniqueVisitorCountByGroup(siteId));
		Statistic avg = new Statistic("每日平均", count);
		list.add(avg);
		Object[] objs = max(dao.getPvCountByGroup(siteId), dao.getUniqueVisitorCountByGroup(siteId));
		count = (Integer) objs[0];
		Statistic max = new Statistic("历史最高", count);
		max.setVice((String) objs[1]);
		list.add(max);
		pvs = dao.getPvCount(siteId);
		visitors = dao.getUniqueVisitorCount(siteId);
		Statistic total = new Statistic("历史累计", pvs / dividend(visitors));
		list.add(total);
		return list;
	}

	@Override
	public List<Statistic> flowStatistic(int type, Integer siteId) {
		List<Statistic> list = new ArrayList<Statistic>();
		switch (type) {
			case PV: {
				return pvStatistic(siteId);
			}
			case UNIQUEIP: {
				return uniqueIpStatistic(siteId);
			}
			case UNIQUEVISITOR: {
				return uniqueVisitorStatistic(siteId);
			}
			case AVGVIEWS: {
				return avgViewsStatistic(siteId);
			}
		}
		return list;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Pagination flowAnalysisPage(String groupCondition, Integer siteId, Integer pageNo, Integer pageSize) {
		List<Statistic> list = new ArrayList<Statistic>();
		Pagination pagination = dao.flowAnalysisPage(groupCondition, siteId, pageNo, pageSize);
		long total = dao.flowAnalysisTotal(siteId);
		for (Object[] objArr : (List<Object[]>) pagination.getList()) {
			Statistic cmsStatistic = new Statistic((String) objArr[1], (Long) objArr[0], total);
			list.add(cmsStatistic);
		}
		pagination.setList(list);
		return pagination;
	}

	@Override
	public Map<String, List<Statistic>> getWelcomeSiteFlowData(Integer siteId) {
		Map<String, List<Statistic>> map = new HashMap<String, List<Statistic>>();
		map.put("today", getListByTimeRange(siteId, getTimeRange(TODAY)));
		map.put("yesterday", getListByTimeRange(siteId, getTimeRange(YESTERDAY)));
		map.put("thisweek", getListByTimeRange(siteId, getTimeRange(THISWEEK)));
		map.put("thismonth", getListByTimeRange(siteId, getTimeRange(THISMONTH)));
		map.put("thisyear", getListByTimeRange(siteId, getTimeRange(THISYEAR)));
		map.put("total", getListByTimeRange(siteId, getTimeRange(-1)));
		return map;
	}

	@Override
	@Transactional
	public void flowInit(Integer siteId, Date startDate, Date endDate) {
		dao.flowInit(siteId, startDate, endDate);
	}

	private List<Statistic> getListByTimeRange(Integer siteId, TimeRange timeRange) {
		List<Statistic> list = new ArrayList<Statistic>();
		list.add(new Statistic(getPvCountByTimeRange(siteId, timeRange)));
		list.add(new Statistic(getUniqueIpCountByTimeRange(siteId, timeRange)));
		list.add(new Statistic(getUniqueVisitorCountByTimeRange(siteId, timeRange)));
		return list;
	}

	private long getPvCountByTimeRange(Integer siteId, TimeRange timeRange) {
		return dao.getPvCountByTimeRange(siteId, timeRange);
	}

	private long getUniqueIpCountByTimeRange(Integer siteId, TimeRange timeRange) {
		return dao.getUniqueIpCountByTimeRange(siteId, timeRange);
	}

	private long getUniqueVisitorCountByTimeRange(Integer siteId, TimeRange timeRange) {
		return dao.getUniqueVisitorCountByTimeRange(siteId, timeRange);
	}

	private String format(int time) {
		Calendar calendar = clearTime(new GregorianCalendar());
		calendar.set(HOUR_OF_DAY, time);
		String begin, end;
		begin = org.apache.commons.lang.time.DateFormatUtils.format(calendar.getTime(), TIMEPATTERN);
		calendar.add(HOUR_OF_DAY, 1);
		end = org.apache.commons.lang.time.DateFormatUtils.format(calendar.getTime(), TIMEPATTERN);
		return begin + JOIN + end;
	}

	private int getYear(Calendar calendar) {
		return calendar.get(YEAR);
	}

	private int getMonth(Calendar calendar) {
		return calendar.get(MONTH);
	}

	private int getDay(Calendar calendar) {
		return calendar.get(DATE);
	}

	private Calendar clearTime(Calendar calendar) {
		return new GregorianCalendar(getYear(calendar), getMonth(calendar), getDay(calendar));
	}

	private void flush(Calendar calendar) {
		calendar.getTime();
	}

	private int avg(List<Object[]> list) {
		int count = 0;
		for (Object[] obj : list) {
			count += (Long) obj[0];
		}
		return count / dividend(list.size());
	}

	private int avg(List<Object[]> pvList, List<Object[]> visitorsList) {
		int count = 0;
		if (pvList.size() != visitorsList.size()) {
			return count;
		}
		for (int i = 0; i < pvList.size(); i++) {
			long pvCount = (Long) pvList.get(i)[0];
			long visitorCount = (Long) visitorsList.get(i)[0];
			count += pvCount / visitorCount;
		}
		return count / dividend((pvList.size()));
	}

	private Object[] max(List<Object[]> list) {
		int max = 0;
		String date = null;
		for (Object[] objs : list) {
			long curr = (Long) objs[0];
			if (max < curr) {
				max = (int) curr;
				date = (String) objs[1];
			}
		}
		return new Object[] { max, date };
	}

	private Object[] max(List<Object[]> pvList, List<Object[]> visitorsList) {
		int max = 0;
		String date = null;
		if (pvList.size() != visitorsList.size()) {
			return new Object[] { max, date };
		}
		for (int i = 0; i < pvList.size(); i++) {
			long pvCount = (Long) pvList.get(i)[0];
			long visitorCount = (Long) visitorsList.get(i)[0];
			long curr = pvCount / visitorCount;
			if (max < curr) {
				max = (int) curr;
				date = (String) pvList.get(i)[1];
			}
		}
		return new Object[] { max, date };
	}

	private TimeRange getTimeRange(int type) {
		return getTimeRange(type, new GregorianCalendar());
	}

	// 获取今日、昨日、本周、本月时间范围
	private TimeRange getTimeRange(int type, Calendar calendar) {
		calendar = clearTime(calendar);
		Date begin, end;
		switch (type) {
			case TODAY: {
				begin = calendar.getTime();
				calendar.add(DATE, 1);
				end = calendar.getTime();
				return TimeRange.getInstance(begin, end);
			}
			case YESTERDAY: {
				calendar.add(DATE, -1);
				begin = calendar.getTime();
				calendar.add(DATE, 1);
				end = calendar.getTime();
				return TimeRange.getInstance(begin, end);
			}
			case THISWEEK: {
				flush(calendar);
				calendar.set(DAY_OF_WEEK, 1);
				begin = calendar.getTime();
				calendar.add(DAY_OF_WEEK, 7);
				end = calendar.getTime();
				return TimeRange.getInstance(begin, end);
			}
			case THISMONTH: {
				int month = calendar.get(MONTH);
				calendar.set(DATE, 1);
				begin = calendar.getTime();
				calendar.set(MONTH, month + 1);
				end = calendar.getTime();
				return TimeRange.getInstance(begin, end);
			}
			case THISYEAR: {
				int year = calendar.get(YEAR);
				calendar.set(MONTH, 0);
				calendar.set(DATE, 1);
				begin = calendar.getTime();
				calendar.set(YEAR, year + 1);
				end = calendar.getTime();
				return TimeRange.getInstance(begin, end);
			}
		}
		return null;
	}
}

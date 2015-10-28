
package com.cc.core.service.impl;

import static com.cc.cms.statistic.Statistic.THISMONTH;
import static com.cc.cms.statistic.Statistic.THISWEEK;
import static com.cc.cms.statistic.Statistic.THISYEAR;
import static com.cc.cms.statistic.Statistic.TODAY;
import static com.cc.cms.statistic.Statistic.YESTERDAY;
import static java.util.Calendar.DATE;
import static java.util.Calendar.DAY_OF_WEEK;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.cc.common.service.BaseServiceImpl;
import com.cc.common.util.TimeRange;
import com.cc.core.service.ICoreSvc;

public class CoreSvcImpl<T extends Serializable> extends BaseServiceImpl<T> implements ICoreSvc<T> {

	protected TimeRange getTimeRange(int type) {
		return getTimeRange(type, new GregorianCalendar());
	}

	// 获取今日、昨日、本周、本月时间范围
	protected TimeRange getTimeRange(int type, Calendar calendar) {
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
}

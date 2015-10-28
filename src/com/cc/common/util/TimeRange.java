package com.cc.common.util;

import java.util.Date;

public class TimeRange {
	private final Date	begin;
	private final Date	end;

	public Date getBegin() {
		return begin;
	}

	public Date getEnd() {
		return end;
	}

	private TimeRange(Date begin, Date end) {
		this.begin = begin;
		this.end = end;
	}

	public static TimeRange getInstance(Date begin, Date end) {
		if (begin == null || end == null) {
			throw new IllegalArgumentException("Params begin and end cannot be null!");
		}
		return new TimeRange(begin, end);
	}
}

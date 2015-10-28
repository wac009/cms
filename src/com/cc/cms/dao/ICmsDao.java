package com.cc.cms.dao;

import java.io.*;

import com.cc.common.util.*;
import com.cc.core.dao.*;

public interface ICmsDao<T extends Serializable> extends ICoreDao<T> {
	public long getCountByTimeRange(Integer siteId, TimeRange timeRange);
}

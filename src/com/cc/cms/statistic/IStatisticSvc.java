
package com.cc.cms.statistic;

import java.util.*;

import com.cc.common.page.*;
import com.cc.cms.statistic.Statistic.*;

public interface IStatisticSvc {

	public List<Statistic> statisticByModel(int type, StatisticModel statisticModel, Integer year, Integer month, Integer day, Map<String, Object> restrictions);

	public List<Statistic> flowStatistic(int type, Integer siteId);

	public Pagination flowAnalysisPage(String groupCondition, Integer siteId, Integer pageNo, Integer pageSize);

	public void flowInit(Integer siteId, Date startDate, Date endDate);

	public Map<String, List<Statistic>> getWelcomeSiteFlowData(Integer siteId);
}

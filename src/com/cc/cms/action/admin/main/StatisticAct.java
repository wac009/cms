
package com.cc.cms.action.admin.main;

import static com.cc.cms.statistic.Statistic.*;
import static com.cc.common.page.SimplePage.*;

import java.util.*;

import org.apache.commons.lang.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;

import com.cc.cms.action.*;
import com.cc.cms.entity.main.*;
import com.cc.cms.service.main.*;
import com.cc.cms.statistic.*;
import com.cc.cms.statistic.Statistic.StatisticModel;
import com.cc.common.util.*;

/** @author wangcheng */
@SuppressWarnings("rawtypes")
@Scope("prototype")
@Controller("web.action.admin.statisticAct")
public class StatisticAct extends CmsAct {

	private static final long	serialVersionUID	= -5710286594588036902L;
	@Autowired
	private IChannelSvc			channelSvc;
	@Autowired
	private IUserSvc			userSvc;
	@Autowired
	private IStatisticSvc		statisticSvc;
	private List<Statistic>		list;
	private List<Channel>		topList;
	private List<Channel>		channelList;
	private Integer				year;
	private Integer				month;
	private Integer				day;
	private Long				total;
	private StatisticModel		statisticModel;
	private String				queryModel;
	private String				queryUsername;
	private Integer				channelId;
	private String				replyed;
	private Boolean				isReplyed;
	private Date				startDate;
	private Date				endDate;
	private String				startTime;
	private String				endTime;

	@Override
	public String list() {
		statisticModel = getStatisticModel(queryModel);
		list = statisticSvc.statisticByModel(MEMBER, statisticModel, year, month, day, null);
		total = getTotal(list);
		return LIST;
	}

	/** 注册会员统计 */
	public String member() {
		statisticModel = getStatisticModel(queryModel);
		list = statisticSvc.statisticByModel(MEMBER, statisticModel, year, month, day, null);
		total = getTotal(list);
		return "member";
	}

	/** 内容统计 */
	public String content() {
		Integer uid = null;
		if (!StringUtils.isBlank(queryUsername)) {
			User u = userSvc.findByUsername(queryUsername);
			if (u != null) {
				uid = u.getId();
			} else {
				// 用户名不存在，清空。
				queryUsername = null;
			}
		}
		Map<String, Object> restrictions = new HashMap<String, Object>();
		Integer siteId = getWebId();
		restrictions.put(SITEID, siteId);
		restrictions.put(USERID, uid);
		restrictions.put(CHANNELID, channelId);
		statisticModel = getStatisticModel(queryModel);
		list = statisticSvc.statisticByModel(CONTENT, statisticModel, year, month, day, restrictions);
		topList = channelSvc.getTopList(siteId, true);
		channelList = Channel.getListForSelect(topList, null, true);
		return "content";
	}

	/** 评论统计 */
	public String comment() {
		Map<String, Object> restrictions = new HashMap<String, Object>();
		restrictions.put(SITEID, getWebId());
		restrictions.put(ISREPLYED, isReplyed);
		statisticModel = getStatisticModel(queryModel);
		list = statisticSvc.statisticByModel(COMMENT, statisticModel, year, month, day, restrictions);
		return "comment";
	}

	/** 留言统计 */
	public String guestbook() {
		Map<String, Object> restrictions = new HashMap<String, Object>();
		restrictions.put(SITEID, getWebId());
		restrictions.put(ISREPLYED, isReplyed);
		statisticModel = getStatisticModel(queryModel);
		list = statisticSvc.statisticByModel(GUESTBOOK, statisticModel, year, month, day, restrictions);
		return "guestbook";
	}

	/**
	 * PV统计
	 */
	public String pv() {
		list = statisticSvc.flowStatistic(PV, getWebId());
		return "pv";
	}

	/**
	 * 独立ip访问统计
	 */
	public String uniqueIp() {
		list = statisticSvc.flowStatistic(UNIQUEIP, getWebId());
		return "uniqueIp";
	}

	/**
	 * 独立访客
	 */
	public String uniqueVisitor() {
		list = statisticSvc.flowStatistic(UNIQUEVISITOR, getWebId());
		return "uniqueVisitor";
	}

	/**
	 * 人均浏览次数统计
	 */
	public String avgViews() {
		list = statisticSvc.flowStatistic(AVGVIEWS, getWebId());
		return "avgViews";
	}

	/**
	 * 来访网站
	 */
	public String refererWebsite() {
		pagination = statisticSvc.flowAnalysisPage(REFERER_WEBSITE, getWebId(), cpn(pageNo), getCookieCount());
		return "refererWebsite";
	}

	/**
	 * 来访页面
	 */
	public String refererPage() {
		pagination = statisticSvc.flowAnalysisPage(REFERER_PAGE, getWebId(), cpn(pageNo), getCookieCount());
		return "refererPage";
	}

	/**
	 * 受访页面
	 */
	public String accessPage() {
		pagination = statisticSvc.flowAnalysisPage(ACCESS_PAGE, getWebId(), cpn(pageNo), getCookieCount());
		return "accessPage";
	}

	public String area() {
		pagination = statisticSvc.flowAnalysisPage(AREA, getWebId(), cpn(pageNo), getCookieCount());
		return "area";
	}

	public String refererKeyword() {
		pagination = statisticSvc.flowAnalysisPage(REFERER_KEYWORD, getWebId(), cpn(pageNo), getCookieCount());
		return "refererKeyword";
	}

	public String flowInit() {
		return "flowInit";
	}

	public String flowInitSubmit() {
		handleTime();
		statisticSvc.flowInit(getWebId(), startDate, endDate);
		addActionMessage("初始化完成");
		return "flowInit";
	}

	private StatisticModel getStatisticModel(String queryModel) {
		if (!StringUtils.isBlank(queryModel)) {
			return StatisticModel.valueOf(queryModel);
		}
		return StatisticModel.year;
	}

	private Long getTotal(List<Statistic> list) {
		return list.size() > 0 ? list.iterator().next().getTotal() : 0L;
	}

	@Override
	public List<Statistic> getList() {
		return list;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public StatisticModel getStatisticModel() {
		return statisticModel;
	}

	public String getQueryModel() {
		return queryModel;
	}

	public void setQueryModel(String queryModel) {
		this.queryModel = queryModel;
	}

	public List<Channel> getTopList() {
		return topList;
	}

	public void setTopList(List<Channel> topList) {
		this.topList = topList;
	}

	public List<Channel> getChannelList() {
		return channelList;
	}

	public void setChannelList(List<Channel> channelList) {
		this.channelList = channelList;
	}

	public String getQueryUsername() {
		return queryUsername;
	}

	public void setQueryUsername(String queryUsername) {
		this.queryUsername = queryUsername;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public String getReplyed() {
		return replyed;
	}

	public void setReplyed(String replyed) {
		if ("".equals(replyed)) {
			isReplyed = null;
		}
		if ("true".equals(replyed)) {
			isReplyed = true;
		}
		if ("".equals(replyed)) {
			isReplyed = false;
		}
		this.replyed = replyed;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	private void handleTime() {
		// 如果没有输入发布时间，则取系统时间；
		if (!ComUtils.nullOrBlank(startTime)) {
			startDate = ComUtils.parseString2Datetime(startTime);
		}
		if (!ComUtils.nullOrBlank(endTime)) {
			endDate = ComUtils.parseString2Datetime(endTime);
		}
	}
}

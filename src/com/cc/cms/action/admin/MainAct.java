
package com.cc.cms.action.admin;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;

import com.cc.cms.action.*;
import com.cc.cms.entity.main.*;
import com.cc.cms.service.assist.*;
import com.cc.cms.service.main.*;
import com.cc.cms.statistic.*;

/** @author wangcheng */
@SuppressWarnings({ "serial", "rawtypes" })
@Scope("prototype")
@Controller("web.action.admin.mainAct")
public class MainAct extends CmsAct {

	@Autowired
	private IStatisticSvc					statisticSvc;
	@Autowired
	private IContentSvc						contentSvc;
	@Autowired
	private IMessageSvc						messageSvc;
	@Autowired
	private IGuestbookSvc					guestbookSvc;
	public List<Permission>					quickList;
	private long							freeMemoery;
	private long							totalMemory;
	private long							usedMemory;
	private long							maxMemory;
	private long							useableMemory;
	private Properties						props;
	private String							version;
	private Map<String, List<Statistic>>	vs;
	private Map<String, List<Statistic>>	cs;
	private Map<String, List<Statistic>>	us;
	private Map<String, List<Statistic>>	ms;
	private Map<String, List<Statistic>>	gs;

	@Override
	public String main() throws Exception {
		setSrcRight("main_right");
		return super.main();
	}

	@Override
	public String left() {
		setLeftMenuType("list");
		setLeftNav("后台首页");
		return super.left();
	}

	@Override
	public String right() throws Exception {
		Runtime runtime = Runtime.getRuntime();
		setFreeMemoery(runtime.freeMemory() / 1024 / 1024);
		setTotalMemory(runtime.totalMemory() / 1024 / 1024);
		setMaxMemory(runtime.maxMemory() / 1024 / 1024);
		setUsedMemory(totalMemory - freeMemoery);
		setUseableMemory(maxMemory - totalMemory + freeMemoery);
		setProps(System.getProperties());
		setVersion(getWeb().getConfig().getVersion());
		// 内容统计
		cs = contentSvc.getWelcomeData(getWebId());
		// 访问统计
		vs = statisticSvc.getWelcomeSiteFlowData(getWebId());
		// 用户统计
		us = userSvc.getWelcomeData(getWebId());
		// 站内信
		ms = messageSvc.getWelcomeData(getWebId());
		// 留言
		gs = guestbookSvc.getWelcomeData(getWebId());
		return super.right();
	}

	public List<Permission> getQuickList() {
		return quickList;
	}

	public void setQuickList(List<Permission> quickList) {
		this.quickList = quickList;
	}

	public long getFreeMemoery() {
		return freeMemoery;
	}

	public void setFreeMemoery(long freeMemoery) {
		this.freeMemoery = freeMemoery;
	}

	public long getTotalMemory() {
		return totalMemory;
	}

	public void setTotalMemory(long totalMemory) {
		this.totalMemory = totalMemory;
	}

	public long getUsedMemory() {
		return usedMemory;
	}

	public void setUsedMemory(long usedMemory) {
		this.usedMemory = usedMemory;
	}

	public long getMaxMemory() {
		return maxMemory;
	}

	public void setMaxMemory(long maxMemory) {
		this.maxMemory = maxMemory;
	}

	public long getUseableMemory() {
		return useableMemory;
	}

	public void setUseableMemory(long useableMemory) {
		this.useableMemory = useableMemory;
	}

	public Properties getProps() {
		return props;
	}

	public void setProps(Properties props) {
		this.props = props;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Map<String, List<Statistic>> getVs() {
		return vs;
	}

	public void setVs(Map<String, List<Statistic>> vs) {
		this.vs = vs;
	}

	public Map<String, List<Statistic>> getCs() {
		return cs;
	}

	public void setCs(Map<String, List<Statistic>> cs) {
		this.cs = cs;
	}

	public Map<String, List<Statistic>> getUs() {
		return us;
	}

	public void setUs(Map<String, List<Statistic>> us) {
		this.us = us;
	}

	public Map<String, List<Statistic>> getMs() {
		return ms;
	}

	public void setMs(Map<String, List<Statistic>> ms) {
		this.ms = ms;
	}

	public Map<String, List<Statistic>> getGs() {
		return gs;
	}

	public void setGs(Map<String, List<Statistic>> gs) {
		this.gs = gs;
	}
}

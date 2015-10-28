
package com.cc.cms.action.admin.assist;

import java.io.*;
import java.util.*;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;

import com.cc.cms.action.*;
import com.cc.cms.entity.main.*;
import com.cc.cms.service.main.*;
import com.cc.cms.staticpage.service.*;
import com.cc.common.util.*;

import freemarker.template.*;

/** @author wangcheng */
@SuppressWarnings({ "rawtypes", "serial", "unchecked" })
@Scope("prototype")
@Controller("web.action.admin.staticAct")
public class StaticAct extends CmsAct {
	
	private static final Logger	log	= LoggerFactory.getLogger(StaticAct.class);
	@Autowired
	private IChannelSvc		channelSvc;
	@Autowired
	private IStaticPageSvc	staticSvc;
	private List<Channel>	channelList;
	private Integer			channelId;
	private Boolean			containChild;
	private String			startDate;

	@Override
	public String list() {
		initChnlList();
		return LIST;
	}

	public String test() {
		getJsonRoot().put("success", true);
		return JSON;
	}

	@Override
	public String index() {
		try {
			staticSvc.index(getWeb());
			getJsonRoot().put("success", true);
		} catch (IOException e) {
			log.error("static index error!", e);
			getJsonRoot().put("success", false);
			getJsonRoot().put("msg", e.toString());
		} catch (TemplateException e) {
			log.error("static index error!", e);
			getJsonRoot().put("success", false);
			getJsonRoot().put("msg", e.toString());
		} catch (Exception e) {
			log.error("static index error!", e);
			getJsonRoot().put("success", false);
			getJsonRoot().put("msg", e.toString());
		}
		return JSON;
	}

	public String deleteIndex() {
		if (staticSvc.deleteIndex(getWeb())) {
			getJsonRoot().put("success", true);
		} else {
			getJsonRoot().put("success", false);
		}
		return JSON;
	}

	public String channel() {
		if (containChild == null) {
			containChild = true;
		}
		try {
			int count = staticSvc.channel(getWebId(), channelId, containChild);
			getJsonRoot().put("success", true);
			getJsonRoot().put("count", count);
		} catch (IOException e) {
			log.error("static channel error!", e);
			getJsonRoot().put("success", false);
			getJsonRoot().put("msg", e.toString());
		} catch (TemplateException e) {
			log.error("static channel error!", e);
			getJsonRoot().put("success", false);
			getJsonRoot().put("msg", e.toString());
		}
		return JSON;
	}

	public String content() {
		try {
			Date date = null;
			if (!ComUtils.nullOrBlank(startDate)) {
				date = ComUtils.parseString2Datetime(startDate);
			}
			int count = staticSvc.content(getWebId(), channelId, date);
			getJsonRoot().put("success", true);
			getJsonRoot().put("count", count);
		} catch (IOException e) {
			log.error("static channel error!", e);
			getJsonRoot().put("success", false);
			getJsonRoot().put("msg", e.toString());
		} catch (TemplateException e) {
			log.error("static channel error!", e);
			getJsonRoot().put("success", false);
			getJsonRoot().put("msg", e.toString());
		}
		return JSON;
	}

	private void initChnlList() {
		channelList = SelectTreeUtils.webTree(SelectTreeUtils.handleTreeChild(channelSvc.getTopList(getWebId(), false)));
		if (channelList == null || channelList.size() == 0) {
			Channel root = new Channel();
			root.setSelectTree("栏目列表为空");
			channelList.add(0, root);
		} else if (channelList.size() > 1) {
			Channel root = new Channel();
			root.setSelectTree("请选择栏目");
			channelList.add(0, root);
		}
	}

	public List<Channel> getChannelList() {
		return channelList;
	}

	public void setChannelList(List<Channel> channelList) {
		this.channelList = channelList;
	}

	public Boolean getContainChild() {
		return containChild;
	}

	public void setContainChild(Boolean containChild) {
		this.containChild = containChild;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

}

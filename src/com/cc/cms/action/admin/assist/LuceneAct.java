
package com.cc.cms.action.admin.assist;

import java.io.*;
import java.util.*;

import org.apache.lucene.index.*;
import org.apache.lucene.queryParser.*;
import org.apache.lucene.store.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;

import com.cc.cms.action.*;
import com.cc.cms.entity.main.*;
import com.cc.cms.lucene.*;
import com.cc.cms.service.main.*;
import com.cc.common.util.*;

/**
 * @author wangcheng
 */
@SuppressWarnings({ "rawtypes", "serial", "unchecked" })
@Scope("prototype")
@Controller("web.action.admin.luceneAct")
public class LuceneAct extends CmsAct {

	private static final Logger	log	= LoggerFactory.getLogger(LuceneAct.class);
	@Autowired
	private LuceneContentSvc	luceneContentSvc;
	@Autowired
	private IChannelSvc			channelMng;
	private List<Channel>		channelList;
	private Integer				channelId;
	private String				startDate;
	private String				endDate;
	private Integer				startId;
	private Integer				max;

	@Override
	public String list() {
		initChnlList();
		return LIST;
	}

	public String create() {
		try {
			Date sdate = null;
			if (!ComUtils.nullOrBlank(startDate)) {
				sdate = ComUtils.parseString2Datetime(startDate);
			}
			Date edate = null;
			if (!ComUtils.nullOrBlank(startDate)) {
				edate = ComUtils.parseString2Datetime(endDate);
			}
			Integer lastId = luceneContentSvc.createIndex(getWebId(), channelId, sdate, edate, startId, max);
			getJsonRoot().put("success", true);
			getJsonRoot().put("lastId", lastId);
		} catch (CorruptIndexException e) {
			getJsonRoot().put("success", false);
			getJsonRoot().put("msg", e.getMessage());
			log.error("", e);
		} catch (LockObtainFailedException e) {
			getJsonRoot().put("success", false);
			getJsonRoot().put("msg", e.getMessage());
			log.error("", e);
		} catch (IOException e) {
			getJsonRoot().put("success", false);
			getJsonRoot().put("msg", e.getMessage());
			log.error("", e);
		} catch (ParseException e) {
			getJsonRoot().put("success", false);
			getJsonRoot().put("msg", e.getMessage());
			log.error("", e);
		}
		return JSON;
	}

	private void initChnlList() {
		channelList = SelectTreeUtils.webTree(SelectTreeUtils.handleTreeChild(channelMng.getTopList(getWebId(), false)));
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

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Integer getStartId() {
		return startId;
	}

	public void setStartId(Integer startId) {
		this.startId = startId;
	}

	public Integer getMax() {
		return max;
	}

	public void setMax(Integer max) {
		this.max = max;
	}
}

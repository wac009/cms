
package com.cc.cms.action.admin.assist;

import java.util.*;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;

import com.cc.cms.action.*;
import com.cc.cms.entity.assist.*;
import com.cc.cms.entity.main.*;
import com.cc.cms.service.assist.*;
import com.cc.cms.service.main.*;
import com.cc.common.util.*;


/** @author wangcheng */
@SuppressWarnings({ "rawtypes", "serial", "unchecked" })
@Scope("prototype")
@Controller("web.action.admin.acquisitionAct")
public class AcquisitionAct extends CmsAct {

	private static final Logger			log	= LoggerFactory.getLogger(AcquisitionAct.class);
	@Autowired
	private IContentTypeSvc				contentTypeSvc;
	@Autowired
	private IChannelSvc					channelSvc;
	@Autowired
	private IAcquisitionSvc				acquisitionSvc;
	@Autowired
	private IAcquisitionMng				acquisitionMng;
	@Autowired
	private IAcquisitionHistorySvc		cmsAcquisitionHistoryMng;
	@Autowired
	private IAcquisitionTempSvc			cmsAcquisitionTempMng;
	private Acquisition					acquisition;
	private List<Channel>				channelList;
	private List<ContentType>			contentTypeList;
	private List<AcquisitionTemp>		acquisitionTemps;
	private List<AcquisitionHistory>	acquisitionHistories;
	private Integer						percent;

	@Override
	public String list() {
		pagination = acquisitionSvc.findAll(pageNo, getCookieCount());
		return LIST;
	}

	private void initAdd() {
		initChnlList();
		initContentTypeList();
	}

	@Override
	public String add() {
		initAdd();
		return ADD;
	}

	public String save() {
		acquisition = acquisitionSvc.save(acquisition, acquisition.getChannel().getId(), acquisition.getType().getId(), getUserId(), getWebId());
		return list();
	}

	@Override
	public String edit() {
		initChnlList();
		initContentTypeList();
		acquisition = acquisitionSvc.findById(id);
		return EDIT;
	}

	public String update() {
		acquisition = acquisitionSvc.update(acquisition, acquisition.getChannel().getId(), acquisition.getType().getId());
		return list();
	}

	public String delete() {
		vldBatch();
		acquisitionSvc.deleteByIds(ids);
		return list();
	}

	public String start() {
		vldBatch();
		Integer queueNum = acquisitionSvc.hasStarted(getWebId());
		if (queueNum == 0) {
			acquisitionMng.start(ids[0]);
		}
		acquisitionSvc.addToQueue(ids, queueNum);
		log.info("start Acquisition ids={}", Arrays.toString(ids));
		return "progress";
	}

	public String pause() {
		acquisitionSvc.pause(id);
		log.info("pause Acquisition id={}", id);
		return list();
	}

	public String stop() {
		acquisitionSvc.end(id);
		Acquisition acqu = acquisitionSvc.popAcquFromQueue(getWebId());
		if (acqu != null) {
			Integer acquId = acqu.getId();
			acquisitionMng.start(acquId);
		}
		log.info("end Acquisition id={}", id);
		return list();
	}

	public String cancel() {
		acquisitionSvc.cancel(getWebId(), id);
		log.info("cancel Acquisition id={}", id);
		return list();
	}

	public String checkComplete() {
		acquisition = acquisitionSvc.getStarted(getWebId());
		getJsonRoot().put("completed", acquisition == null ? true : false);
		return JSON;
	}

	public String progressData() {
		acquisition = acquisitionSvc.getStarted(getWebId());
		acquisitionTemps = cmsAcquisitionTempMng.getList(getWebId());
		setPercent(cmsAcquisitionTempMng.getPercent(getWebId()));
		return "progressData";
	}

	public String progress() {
		acquisition = acquisitionSvc.getStarted(getWebId());
		if (acquisition == null) {
			cmsAcquisitionTempMng.clear(getWebId());
		}
		return "progress";
	}

	public String history() {
		pagination = cmsAcquisitionHistoryMng.getPage(getWebId(), id, pageNo, getCookieCount());
		return "history";
	}

	public String deleteHistory() {
		vldBatch();
		AcquisitionHistory[] beans = cmsAcquisitionHistoryMng.deleteByIds(ids);
		for (AcquisitionHistory bean : beans) {
			log.info("delete AcquisitionHistory id={}", bean.getId());
		}
		setId(null);
		return history();
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

	private void initContentTypeList() {
		contentTypeList = contentTypeSvc.getEnableList();
		if (contentTypeList == null || contentTypeList.size() == 0) {
			ContentType root = new ContentType();
			root.setName("类型为空");
			contentTypeList.add(0, root);
		}
	}

	public Acquisition getAcquisition() {
		return acquisition;
	}

	public void setAcquisition(Acquisition acquisition) {
		this.acquisition = acquisition;
	}

	public List<Channel> getChannelList() {
		return channelList;
	}

	public void setChannelList(List<Channel> channelList) {
		this.channelList = channelList;
	}

	public List<ContentType> getContentTypeList() {
		return contentTypeList;
	}

	public void setContentTypeList(List<ContentType> contentTypeList) {
		this.contentTypeList = contentTypeList;
	}

	public List<AcquisitionTemp> getAcquisitionTemps() {
		return acquisitionTemps;
	}

	public void setAcquisitionTemps(List<AcquisitionTemp> acquisitionTemps) {
		this.acquisitionTemps = acquisitionTemps;
	}

	public List<AcquisitionHistory> getAcquisitionHistories() {
		return acquisitionHistories;
	}

	public void setAcquisitionHistories(List<AcquisitionHistory> acquisitionHistories) {
		this.acquisitionHistories = acquisitionHistories;
	}

	public Integer getPercent() {
		return percent;
	}

	public void setPercent(Integer percent) {
		this.percent = percent;
	}
}
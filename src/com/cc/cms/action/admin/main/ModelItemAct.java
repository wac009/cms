
package com.cc.cms.action.admin.main;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;

import com.cc.cms.action.*;
import com.cc.cms.entity.main.*;
import com.cc.cms.service.main.*;

/** @author wangcheng */

@SuppressWarnings("rawtypes")
@Scope("prototype")
@Controller("web.action.admin.modelItemAct")
public class ModelItemAct extends CmsAct {

	private static final long	serialVersionUID	= 3616926081165313706L;
	public static final Logger	log					= LoggerFactory.getLogger(ModelItemAct.class);

	@Autowired
	private IModelItemSvc		modelItemSvc;
	@Autowired
	private IModelSvc			modelSvc;
	private Model				model;
	private Integer				modelId;
	private boolean				isChannel;

	public String listChannel() {
		model = modelSvc.findById(modelId);
		list = modelItemSvc.getList(modelId, isChannel, true);
		return "listChannel";

	}

	public String listContent() {
		model = modelSvc.findById(modelId);
		list = modelItemSvc.getList(modelId, isChannel, true);
		return "listContent";
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public Integer getModelId() {
		return modelId;
	}

	public void setModelId(Integer modelId) {
		this.modelId = modelId;
	}

	public boolean isChannel() {
		return isChannel;
	}

	public void setChannel(boolean isChannel) {
		this.isChannel = isChannel;
	}
}

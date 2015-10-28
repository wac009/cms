
package com.cc.cms.action.front;

import static com.cc.cms.Constants.*;

import javax.servlet.http.*;

import org.slf4j.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

import com.cc.cms.entity.main.*;
import com.cc.cms.web.*;
import com.cc.common.web.*;

/**
 * 自定义客户端包含模板
 * 
 * @author wangcheng
 */
@Controller
public class CsiCustomAct {

	private static final Logger	log	= LoggerFactory.getLogger(CsiCustomAct.class);

	/**
	 * 解析至自定义模板页
	 * 
	 * @param tpl
	 *            自定义模板名称
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/csi_custom.jspx")
	public String custom(String tpl, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		log.debug("visit csi custom template: {}", tpl);
		Site site = CmsUtils.getSite(request);
		// 将request中所有参数保存至model中。
		model.putAll(RequestUtils.getQueryParams(request));
		FrontUtils.frontData(request, model, site);
		return FrontUtils.getTplPath(site.getTplPath(), TPLDIR_CSI_CUSTOM, tpl);
	}
}

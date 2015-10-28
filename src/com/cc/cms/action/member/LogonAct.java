
package com.cc.cms.action.member;

import static com.cc.cms.Constants.*;

import javax.servlet.http.*;

import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

import com.cc.cms.entity.main.*;
import com.cc.cms.web.*;
import com.cc.common.web.*;

/** @author wangcheng */
@Controller
public class LogonAct {

	public static final String	LOGIN_CSI	= "tpl.loginCsi";

	/**
	 * 客户端包含
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/login_csi.jspx")
	public String csi(HttpServletRequest request, ModelMap model) {
		Site site = CmsUtils.getSite(request);
		// 将request中所有参数
		model.putAll(RequestUtils.getQueryParams(request));
		FrontUtils.frontData(request, model, site);
		return FrontUtils.getTplPath(request, site.getTplPath(), TPLDIR_CSI, LOGIN_CSI);
	}
}

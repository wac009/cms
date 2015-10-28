
package com.cc.cms.action.front;

import java.util.*;

import javax.servlet.http.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

import static com.cc.cms.Constants.*;
import com.cc.cms.entity.assist.*;
import com.cc.cms.entity.main.*;
import com.cc.cms.service.assist.*;
import com.cc.cms.web.*;

/**
 * 广告Action
 * 
 * @author wangcheng
 */
@Controller
public class AdvertisingAct {

	@Autowired
	private IAdvertisingSvc			cmsAdvertisingMng;
	@Autowired
	private IAdvertisingSpaceSvc	cmsAdvertisingSpaceMng;
	// private static final Logger log = LoggerFactory
	// .getLogger(AdvertisingAct.class);
	public static final String		TPL_AD		= "tpl.advertising";
	public static final String		TPL_ADSPACE	= "tpl.adspace";

	@RequestMapping(value = "/ad.jspx")
	public String ad(Integer id, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Site site = CmsUtils.getSite(request);
		if (id != null) {
			Advertising ad = cmsAdvertisingMng.findById(id);
			model.addAttribute("ad", ad);
		}
		FrontUtils.frontData(request, model, site);
		return FrontUtils.getTplPath(request, site.getTplPath(), TPLDIR_CSI, TPL_AD);
	}

	@RequestMapping(value = "/adspace.jspx")
	public String adspace(Integer id, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Site site = CmsUtils.getSite(request);
		if (id != null) {
			AdvertisingSpace adspace = cmsAdvertisingSpaceMng.findById(id);
			List<Advertising> adList = cmsAdvertisingMng.getList(id, true);
			model.addAttribute("adspace", adspace);
			model.addAttribute("adList", adList);
		}
		FrontUtils.frontData(request, model, site);
		return FrontUtils.getTplPath(request, site.getTplPath(), TPLDIR_CSI, TPL_ADSPACE);
	}

	@RequestMapping(value = "/ad_display.jspx")
	public void display(Integer id, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		if (id != null) {
			cmsAdvertisingMng.display(id);
		}
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
	}

	@RequestMapping(value = "/ad_click.jspx")
	public void click(Integer id, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		if (id != null) {
			cmsAdvertisingMng.click(id);
		}
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
	}
}

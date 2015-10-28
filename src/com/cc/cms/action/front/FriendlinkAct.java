
package com.cc.cms.action.front;

import javax.servlet.http.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

import com.cc.cms.service.assist.*;
import com.cc.common.web.*;

/**
 * 友情链接点击次数Action
 * 
 * @author wangcheng
 */
@Controller
public class FriendlinkAct {

	@Autowired
	private IFriendlinkSvc	cmsFriendlinkMng;

	// private static final Logger log = LoggerFactory
	// .getLogger(FriendlinkAct.class);
	@RequestMapping(value = "/friendlink_view.jspx", method = RequestMethod.GET)
	public void view(Integer id, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		if (id != null) {
			cmsFriendlinkMng.updateViews(id);
			ResponseUtils.renderJson(response, "true");
		} else {
			ResponseUtils.renderJson(response, "false");
		}
	}
}


package com.cc.cms.action.front;

import javax.servlet.http.*;

import org.apache.commons.lang.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

import static com.cc.cms.Constants.*;
import com.cc.cms.entity.main.*;
import com.cc.cms.service.main.*;
import com.cc.cms.web.*;

/** @author wangcheng */
@Controller
public class TopicAct {

	@Autowired
	private ITopicSvc			cmsTopicMng;
	@Autowired
	private IChannelSvc			channelMng;
	public static final String	TOPIC_INDEX		= "tpl.topicIndex";
	public static final String	TOPIC_CHANNEL	= "tpl.topicChannel";
	public static final String	TOPIC_DEFAULT	= "tpl.topicDefault";

	@RequestMapping(value = "/topic*.jspx", method = RequestMethod.GET)
	public String index(Integer channelId, Integer topicId, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		// 全部？按站点？按栏目？要不同模板？
		Site site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		FrontUtils.frontPageData(request, model);
		if (topicId != null) {
			Topic topic = cmsTopicMng.findById(topicId);
			if (topic == null) {
				return FrontUtils.pageNotFound(request, response, model);
			}
			model.addAttribute("topic", topic);
			String tpl = topic.getTplContent();
			if (StringUtils.isBlank(tpl)) {
				tpl = FrontUtils.getTplPath(request, site.getTplPath(), TPLDIR_TOPIC, TOPIC_DEFAULT);
			}
			return tpl;
		} else if (channelId != null) {
			Channel channel = channelMng.findById(channelId);
			if (channel != null) {
				model.addAttribute("channel", channel);
			} else {
				return FrontUtils.pageNotFound(request, response, model);
			}
			return FrontUtils.getTplPath(request, site.getTplPath(), TPLDIR_TOPIC, TOPIC_CHANNEL);
		} else {
			return FrontUtils.getTplPath(request, site.getTplPath(), TPLDIR_TOPIC, TOPIC_INDEX);
		}
	}

	@RequestMapping(value = "/topic/{id}.jspx", method = RequestMethod.GET)
	public String topic(@PathVariable Integer id, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Site site = CmsUtils.getSite(request);
		if (id == null) {
			return FrontUtils.pageNotFound(request, response, model);
		}
		Topic topic = cmsTopicMng.findById(id);
		if (topic == null) {
			return FrontUtils.pageNotFound(request, response, model);
		}
		model.addAttribute("topic", topic);
		String tpl = topic.getTplContent();
		if (StringUtils.isBlank(tpl)) {
			tpl = FrontUtils.getTplPath(request, site.getTplPath(), TPLDIR_TOPIC, TOPIC_DEFAULT);
		}
		FrontUtils.frontData(request, model, site);
		FrontUtils.frontPageData(request, model);
		return tpl;
	}
}

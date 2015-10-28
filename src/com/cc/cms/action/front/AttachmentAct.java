
package com.cc.cms.action.front;

import java.io.*;
import java.util.*;

import javax.servlet.http.*;

import org.json.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

import com.cc.cms.entity.main.*;
import com.cc.cms.service.main.*;
import com.cc.cms.web.*;
import com.cc.common.security.encoder.*;
import com.cc.common.web.*;

/** @author wangcheng */
@Controller
public class AttachmentAct {

	private static final Logger	log	= LoggerFactory.getLogger(AttachmentAct.class);
	@Autowired
	private IContentSvc			contentMng;
	@Autowired
	private IContentCountSvc	contentCountMng;
	@Autowired
	private IPwdEncoder			pwdEncoder;

	@RequestMapping(value = "/attachment.jspx", method = RequestMethod.GET)
	public void attachment(Integer cid, Integer i, Long t, String k, HttpServletRequest request, HttpServletResponse response, ModelMap model)
			throws IOException {
		Site site=CmsUtils.getSite(request);
		Config config = site.getConfig();
		String code = config.getDownloadCode();
		int h = config.getDownloadTime() * 60 * 60 * 1000;
		if (pwdEncoder.isPasswordValid(k, cid + ";" + i + ";" + t, code)) {
			long curr = System.currentTimeMillis();
			if (t + h > curr) {
				Content c = contentMng.findById(cid);
				if (c != null) {
					List<ContentAttachment> list = c.getAttachments();
					if (list.size() > i) {
						contentCountMng.downloadCount(c.getId());
						ContentAttachment ca = list.get(i);
						response.sendRedirect(site.getProtocol()+site.getDomain()+"/res/w"+ca.getPath());
						return;
					} else {
						log.info("download index is out of range: {}", i);
					}
				} else {
					log.info("Content not found: {}", cid);
				}
			} else {
				log.info("download time is expired!");
			}
		} else {
			log.info("download key error!");
		}
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
	}

	/**
	 * 获得下载key和time
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/attachment_url.jspx", method = RequestMethod.GET)
	public void url(Integer cid, Integer n, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		if (cid == null || n == null) {
			return;
		}
		Config config = CmsUtils.getSite(request).getConfig();
		String code = config.getDownloadCode();
		long t = System.currentTimeMillis();
		JSONArray arr = new JSONArray();
		String key;
		for (int i = 0; i < n; i++) {
			key = pwdEncoder.encodePassword(cid + ";" + i + ";" + t, code);
			arr.put("&t=" + t + "&k=" + key);
		}
		ResponseUtils.renderText(response, arr.toString());
	}
}

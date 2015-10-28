package com.cc.cms.staticpage.service;

import static com.cc.cms.Constants.*;
import java.io.*;
import java.util.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.*;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.*;
import org.springframework.transaction.annotation.*;
import org.springframework.util.*;
import org.springframework.web.servlet.view.freemarker.*;
import com.cc.cms.entity.main.*;
import com.cc.cms.staticpage.dao.IStaticPageDao;
import com.cc.cms.web.*;
import com.cc.common.web.springmvc.*;
import freemarker.template.*;
import freemarker.template.Template;

public class StaticPageSvcImpl implements IStaticPageSvc, InitializingBean {
	private Logger log = LoggerFactory.getLogger(StaticPageSvcImpl.class);
	@Autowired
	private RealPathResolver realPathResolver;
	@Autowired
	private IStaticPageDao iStaticPageDao;
	private MessageSource tplMessageSource;
	private Configuration conf;

	@Override
	@Transactional
	public int content(Integer siteId, Integer channelId, Date start) throws IOException, TemplateException {
		long time = System.currentTimeMillis();
		Map<String, Object> data = new HashMap<String, Object>();
		int count = iStaticPageDao.contentStatic(siteId, channelId, start, conf, data);
		time = System.currentTimeMillis() - time;
		log.info("create content page count {}, in {} ms", count, time);
		return count;
	}

	@Override
	@Transactional
	public boolean content(Content content) throws IOException, TemplateException {
		Map<String, Object> data = new HashMap<String, Object>();
		long time = System.currentTimeMillis();
		boolean generated = iStaticPageDao.contentStatic(content, conf, data);
		time = System.currentTimeMillis() - time;
		log.info("create content page in {} ms", time);
		return generated;
	}

	@Override
	@Transactional(readOnly = true)
	public void contentRelated(Content content) throws IOException, TemplateException {
		content(content);
		Channel channel = content.getChannel();
		while (channel != null) {
			channel(channel, true);
			channel = channel.getParent();
		}
		if (content.getSite().getStaticIndex()) {
			index(content.getSite());
		}
	}

	@Override
	@Transactional(readOnly = true)
	public void deleteContent(Content content) throws IOException, TemplateException {
		String real;
		File file;
		int totalPage = content.getPageCount();
		for (int pageNo = 1; pageNo <= totalPage; pageNo++) {
			real = realPathResolver.get(content.getStaticFilename(pageNo));
			file = new File(real);
			file.delete();
		}
		if (content.getSite().getStaticIndex()) {
			index(content.getSite());
		}
	}

	@Override
	@Transactional(readOnly = true)
	public int channel(Integer siteId, Integer channelId, boolean containChild) throws IOException, TemplateException {
		long time = System.currentTimeMillis();
		Map<String, Object> data = new HashMap<String, Object>();
		int count = iStaticPageDao.channelStatic(siteId, channelId, containChild, conf, data);
		time = System.currentTimeMillis() - time;
		log.info("create channel page count {}, in {} ms", count, time);
		return count;
	}

	@Override
	@Transactional(readOnly = true)
	public void channel(Channel channel, boolean firstOnly) throws IOException, TemplateException {
		Map<String, Object> data = new HashMap<String, Object>();
		long time = System.currentTimeMillis();
		iStaticPageDao.channelStatic(channel, firstOnly, conf, data);
		time = System.currentTimeMillis() - time;
		log.info("create channel page in {} ms", time);
	}

	@Override
	@Transactional(readOnly = true)
	public void deleteChannel(Channel channel) {
		// 如果是外部链接或者不需要生产静态页，则不删除
		if (!StringUtils.isBlank(channel.getLink()) || !channel.getStaticChannel()) {
			return;
		}
		// 没有内容或者有子栏目，则只删除一页 ，
		int childs = iStaticPageDao.childsOfChannel(channel.getId());
		int quantity, totalPage;
		if (!channel.getModel().getHasContent() || (!channel.getListChild() && childs > 0)) {
			totalPage = 1;
		} else {
			if (channel.getListChild()) {
				quantity = childs;
			} else {
				quantity = iStaticPageDao.contentsOfChannel(channel.getId());
			}
			if (quantity <= 0) {
				totalPage = 1;
			} else {
				totalPage = (quantity - 1) / channel.getPageSize() + 1;
			}
		}
		String real, filename;
		File f;
		for (int i = 1; i <= totalPage; i++) {
			filename = channel.getStaticFilename(i);
			real = realPathResolver.get(filename.toString());
			f = new File(real);
			f.delete();
		}
	}

	@Override
	@Transactional(readOnly = true)
	public void index(Site site) throws IOException, TemplateException {
		Map<String, Object> data = new HashMap<String, Object>();
		FrontUtils.frontData(data, site, null, site.getUrlStatic(), null);
		String tpl = FrontUtils.getTplPath(tplMessageSource, site.getLocaleAdmin(), site.getTplPath(), TPLDIR_INDEX, TPL_INDEX);
		index(site, tpl, data);
	}

	@Override
	@Transactional(readOnly = true)
	public void index(Site site, String tpl, Map<String, Object> data) throws IOException, TemplateException {
		long time = System.currentTimeMillis();
		File f = new File(getIndexPath(site));
		File parent = f.getParentFile();
		if (!parent.exists()) {
			parent.mkdirs();
		}
		Writer out = null;
		try {
			// FileWriter不能指定编码确实是个问题，只能用这个代替了。
			out = new OutputStreamWriter(new FileOutputStream(f), UTF8);
			Template template = conf.getTemplate(tpl);
			template.process(data, out);
		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
		}
		time = System.currentTimeMillis() - time;
		log.info("create index page, in {} ms", time);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean deleteIndex(Site site) {
		File f = new File(getIndexPath(site));
		return f.delete();
	}

	private String getIndexPath(Site site) {
		StringBuilder pathBuff = new StringBuilder();
		if (!site.getIndexToRoot()) {
			if (!StringUtils.isBlank(site.getStaticDir())) {
				pathBuff.append(site.getStaticDir());
			}
		}
		pathBuff.append("/").append(INDEX).append(site.getStaticSuffix());
		return realPathResolver.get(pathBuff.toString());
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(conf, "freemarker configuration cannot be null!");
		Assert.notNull(tplMessageSource, "tplMessageSource configuration cannot be null!");
	}

	public void setFreeMarkerConfigurer(FreeMarkerConfigurer freeMarkerConfigurer) {
		this.conf = freeMarkerConfigurer.getConfiguration();
	}

	public void setTplMessageSource(MessageSource tplMessageSource) {
		this.tplMessageSource = tplMessageSource;
	}
}

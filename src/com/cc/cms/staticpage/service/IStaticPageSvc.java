
package com.cc.cms.staticpage.service;

import java.io.*;
import java.util.*;

import com.cc.cms.entity.main.*;

import freemarker.template.*;

public interface IStaticPageSvc {
	public int content(Integer siteId, Integer channelId, Date start) throws IOException, TemplateException;

	public boolean content(Content content) throws IOException, TemplateException;

	public void contentRelated(Content content) throws IOException, TemplateException;

	public void deleteContent(Content content) throws IOException, TemplateException;

	public int channel(Integer siteId, Integer channelId, boolean containChild) throws IOException, TemplateException;

	public void channel(Channel channel, boolean firstOnly) throws IOException, TemplateException;

	public void deleteChannel(Channel channel);

	public void index(Site site) throws IOException, TemplateException;

	public void index(Site site, String tpl, Map<String, Object> data) throws IOException, TemplateException;

	public boolean deleteIndex(Site site);
}

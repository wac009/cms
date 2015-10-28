package com.cc.cms.staticpage.dao;

import java.io.*;
import java.util.*;
import com.cc.cms.entity.main.*;
import freemarker.template.*;

public interface IStaticPageDao {
	public int channelStatic(Integer siteId, Integer channelId, boolean containChild, Configuration conf, Map<String, Object> data) throws IOException, TemplateException;

	public void channelStatic(Channel c, boolean firstOnly, Configuration conf, Map<String, Object> data) throws IOException, TemplateException;

	public int contentStatic(Integer siteId, Integer channelId, Date start, Configuration conf, Map<String, Object> data) throws IOException, TemplateException;

	public boolean contentStatic(Content c, Configuration conf, Map<String, Object> data) throws IOException, TemplateException;

	public int contentsOfChannel(Integer channelId);

	public int childsOfChannel(Integer channelId);
}

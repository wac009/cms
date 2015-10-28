package com.cc.cms.dao.main.impl;

import org.springframework.stereotype.Repository;
import com.cc.cms.dao.CmsDaoImpl;
import com.cc.cms.dao.main.IContentCheckDao;
import com.cc.cms.entity.main.ContentCheck;


/**
 * @author wangcheng
 */
@Repository
public class ContentCheckDaoImpl extends CmsDaoImpl<ContentCheck> implements IContentCheckDao {}

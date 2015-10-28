
package com.cc.cms.dao.main.impl;

import org.springframework.stereotype.Repository;
import com.cc.cms.dao.CmsDaoImpl;
import com.cc.cms.dao.main.IConfigDao;
import com.cc.cms.entity.main.Config;

/**
 * @author wangcheng
 */
@Repository
public class ConfigDaoImpl extends CmsDaoImpl<Config> implements IConfigDao {}

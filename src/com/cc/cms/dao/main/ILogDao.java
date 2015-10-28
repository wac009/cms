
package com.cc.cms.dao.main;

import java.util.Date;
import com.cc.cms.dao.ICmsDao;
import com.cc.cms.entity.main.Log;

/**
 * @author wangcheng
 */
public interface ILogDao extends ICmsDao<Log> {

	public int deleteBatch(Integer category, Integer siteId, Date before);
}


package com.cc.cms.service.main;

import com.cc.cms.entity.main.Log;
import com.cc.cms.entity.main.Site;
import com.cc.cms.entity.main.User;
import com.cc.cms.service.ICmsSvc;

/**
 * @author wangcheng
 */
public interface ILogSvc extends ICmsSvc<Log> {

	public Log operating(String title, String content, String ip, String uri, Site site, User user);

	public Log loginFailure(String title, String content, String ip, String uri);

	public Log loginSuccess(User user, String title, String ip, String uri);

	public int deleteBatch(Integer category, Integer siteId, Integer days);
}

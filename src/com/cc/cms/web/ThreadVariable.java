package com.cc.cms.web;

import com.cc.cms.entity.main.Site;
import com.cc.cms.entity.main.User;

/**
 * CMS线程变量
 * 
 * @author wangcheng
 * 
 */
public class ThreadVariable {
	/**
	 * 当前用户线程变量
	 */
	private static ThreadLocal<User> cmsUserVariable = new ThreadLocal<User>();
	/**
	 * 当前站点线程变量
	 */
	private static ThreadLocal<Site> cmsSiteVariable = new ThreadLocal<Site>();

	/**
	 * 获得当前用户
	 * 
	 * @return
	 */
	public static User getUser() {
		return cmsUserVariable.get();
	}

	/**
	 * 设置当前用户
	 * 
	 * @param user
	 */
	public static void setUser(User user) {
		cmsUserVariable.set(user);
	}

	/**
	 * 移除当前用户
	 */
	public static void removeUser() {
		cmsUserVariable.remove();
	}

	/**
	 * 获得当前站点
	 * 
	 * @return
	 */
	public static Site getSite() {
		return cmsSiteVariable.get();
	}

	/**
	 * 设置当前站点
	 * 
	 * @param site
	 */
	public static void setSite(Site site) {
		cmsSiteVariable.set(site);
	}

	/**
	 * 移除当前站点
	 */
	public static void removeSite() {
		cmsSiteVariable.remove();
	}
}

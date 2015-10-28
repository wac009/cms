package com.cc.common.email;

/**
 * 邮件模板
 * 
 * @author wangcheng
 * 
 */
public interface MessageTemplate {
	/**
	 * 找回密码主题
	 * 
	 * @return
	 */
	public String getForgotPasswordSubject();

	/**
	 * 找回密码内容
	 * 
	 * @return
	 */
	public String getForgotPasswordText();
	
	/**
	 * 会员注册主题
	 * 
	 * @return
	 */
	public String getRegisterSubject();

	/**
	 * 会员注册内容
	 * 
	 * @return
	 */
	public String getRegisterText();
}

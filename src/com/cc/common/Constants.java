
package com.cc.common;

import java.io.*;

/** common常量
 * 
 * @author wangcheng */
public abstract class Constants {

	/** 路径分隔符 */
	public static final char	SPT				= '/';
	/** 系统路径分隔符 */
	public static final char	FILE_SPT		= File.separatorChar;
	/** UTF-8编码 */
	public static final String	UTF8			= "UTF-8";
	/** cookie中的JSESSIONID名称 */
	public static final String	JSESSION_COOKIE	= "JSESSIONID";
	/** url中的jsessionid名称 */
	public static final String	JSESSION_URL	= "jsessionid";
	/** HTTP POST请求 */
	public static final String	POST			= "POST";
	/** HTTP GET请求 */
	public static final String	GET				= "GET";
	/** 每日毫秒数 */
	public static final long	DAY_MILLIS		= 24 * 60 * 60 * 1000;
}

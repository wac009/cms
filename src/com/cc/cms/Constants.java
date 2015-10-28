
package com.cc.cms;


/** cms 常量
 * 
 * @author wangcheng */
public abstract class Constants extends com.cc.common.Constants{

	/** 索引页 */
	public static final String	INDEX					= "index";
	/** 默认模板 */
	public static final String	DEFAULT					= "default";
	/** 提示信息 */
	public static final String	MESSAGE					= "message";
	/** 首页模板名称 */
	public static final String TPL_INDEX = "tpl.index";
	/** 首页模板 */
	public static final String	TPLDIR_INDEX			= "index";
	/** 栏目页模板 */
	public static final String	TPLDIR_CHANNEL			= "channel";
	/** 内容页模板 */
	public static final String	TPLDIR_CONTENT			= "content";
	/** 单页模板 */
	public static final String	TPLDIR_ALONE			= "alone";
	/** 专题模板 */
	public static final String	TPLDIR_TOPIC			= "topic";
	/** 会员中心模板 */
	public static final String	TPLDIR_MEMBER			= "member";
	/** 专用模板 */
	public static final String	TPLDIR_SPECIAL			= "special";
	/** 公用模板 */
	public static final String	TPLDIR_COMMON			= "common";
	/** 客户端包含模板 */
	public static final String	TPLDIR_CSI				= "csi";
	/** 客户端包含用户自定义模板 */
	public static final String	TPLDIR_CSI_CUSTOM		= "csi_custom";
	/** 服务器端包含模板 */
	public static final String	TPLDIR_SSI				= "ssi";
	/** 标签模板 */
	public static final String	TPLDIR_TAG				= "tag";
	/** 评论模板 */
	public static final String	TPLDIR_COMMENT			= "comment";
	/** 留言模板 */
	public static final String	TPLDIR_GUESTBOOK		= "guestbook";
	/** 站内信模板 */
	public static final String	TPLDIR_MESSAGE			= "message";
	/** 列表样式模板 */
	public static final String	TPLDIR_STYLE_LIST		= "style_list";
	/** 列表样式模板 */
	public static final String	TPLDIR_STYLE_PAGE		= "style_page";
	/** 模板后缀 */
	public static final String	TPL_SUFFIX				= ".html";
	/** 资源路径 */
	public static final String	RES_PATH				= "/res";
	public static final String	RES_SYS					= "/res/c";
	public static final String	RES_TLP					= "/res/t";
	public static final String	RES_MNG					= "/res/m";
	public static final String	RES_FRONT				= "/res/w";
	/** 资源路径 */
	public static final String	RES						= "res";
	public static final String	COMMON					= "c";
	public static final String	TEMPLATE				= "t";
	public static final String	MANAGE					= "m";
	public static final String	FRONT					= "w";
	/** 模板路径 */
	public static final String	TPL_BASE				= "/WEB-INF/tpl";
	/** 全文检索索引路径 */
	public static final String	LUCENE_PATH				= "/WEB-INF/lucene";
	/** 列表样式模板路径 */
	public static final String	TPL_STYLE_LIST			= "/WEB-INF/ftl/cms_sys/style_list/style_";
	/** 内容分页模板路径 */
	public static final String	TPL_STYLE_PAGE_CONTENT	= "/WEB-INF/ftl/cms_sys/style_page/content_";
	/** 列表分页模板路径 */
	public static final String	TPL_STYLE_PAGE_CHANNEL	= "/WEB-INF/ftl/cms_sys/style_page/channel_";
	/** 页面访问默认的后缀 */
	public static final String	DEF_SUFFIX				= ".html";
	/** 上传的路径名 */
	public static final String	UPLOAD_PATH				= "/res/w";
	public static final String	UPLOAD_FILE				= "file";
	public static final String	UPLOAD_FLASH			= "flash";
	public static final String	UPLOAD_IMAGE			= "image";
	public static final String	UPLOAD_MEDIA			= "media";
	public static final String	USER_SESSION_KEY		= "user_session";
	public static final String	TOPMENU_INDEX_URL		= "topmenu_index_url";
	public static final String	FTL_PATH				= "freemarker";
	/** 模板目录结构中的res */
	public static final String	$_RES					= "${res}";
	/** 模板目录结构中的res */
	public static final String	$_RESTEMPLATE			= "${r_t}";
	/** 模板目录结构中的page */
	public static final String	$_PAGE					= "${page}";
	/** WEB-INF */
	public static final String	WEBINF					= "WEB-INF";
	/** 默认模板方案 */
	public static final String	TPL_DEF_SOLUTION		= "default";
	/** 标签的前缀 */
	public static final String	TAG_RPEFIX				= "tag_";
	public static final String	TAG						= "tag";
	/** 重定向result */
	public static final String	REDIRECT				= "redirect";
	/** 没有找到和域名相匹配的站点 */
	public static final String	WEBSITE_NOT_FOUND		= "websiteNotFound";
	/** 网站已关闭 */
	public static final String	WEBSITE_CLOSED			= "websiteClosed";
}

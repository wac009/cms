
package com.cc.cms.action;

import java.io.*;
import java.util.*;

import javax.servlet.http.*;

import org.apache.commons.lang.*;
import org.apache.struts2.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;

import com.cc.cms.*;
import com.cc.cms.entity.main.*;
import com.cc.cms.service.main.*;
import com.cc.common.struts2.*;
import com.cc.common.struts2.interceptor.*;
import com.cc.common.util.*;
import com.cc.core.action.*;
import com.cc.core.service.*;

/** 处理域名、用户登录、访问路径等信息 */
@SuppressWarnings({ "serial", "unused", "unchecked" })
public class CmsAct<T extends Serializable> extends CoreAction implements DomainNameAware {

	public static final Logger	log					= LoggerFactory.getLogger(CmsAct.class);
	@Autowired
	protected IContextPvd		contextPvd;
	@Autowired
	protected IWebsiteSvc		websiteSvc;
	@Autowired
	protected IUserSvc			userSvc;
	@Autowired
	protected IUnifiedUserSvc	unifiedUserSvc;
	@Autowired
	protected IUserExtSvc		userExtSvc;
	@Autowired
	protected IPermissionSvc	permissionSvc;
	@Autowired
	protected ILogSvc			logSvc;
	/** 指定记录数的cookie名称 */
	public static final String	COOKIE_COUNT		= "_countPerPage";
	/** cookie能指定的最大记录数 */
	public static final int		COOKIE_MAX_COUNT	= 200;
	/** 默认记录数 */
	public static final int		DEFAULT_COUNT		= 20;
	/** 域名。如：www.sina.com */
	protected String			domainName;
	/** 页面重定向 */
	private String				redirectUrl;
	/** 自定义返回 */
	private String				defResult;
	/** 是否启用验证码 */
	public boolean				checkCodeEnabled	= false;
	/** 动态菜单使用的顶部菜单和左边菜单 */
	private List<T>				topMenus			= new ArrayList<T>();
	private List<T>				leftMenus;
	private T					leftMenu;
	private String				srcLeft;
	private String				srcRight;
	private String				leftMenuType;
	private String				leftMenu_url;
	private String				leftMenu_durl;
	private String				leftNav;
	private String				rightNav;
	private String				actionName;
	private Map<String, Object>	params;
	private Map<String, Object>	jsonRoot			= new HashMap<String, Object>();
	private UploadRule			uploadRule;
	private int					uploadRuleId;

	@Override
	public String main() throws Exception {
		handleParams();
		contextPvd.setSessionAttr(Constants.TOPMENU_INDEX_URL, getActionName());
		return super.main();
	}

	@Override
	public String left() {
		try {
			String url = getActionName().split("_")[0] + "_main";
			Permission permission = permissionSvc.getPermission(url, getAdminId());
			permission = permissionSvc.filterMenuPermission(permission);
			if (permission != null) {
				setLeftMenu((T) permission);
				setLeftMenus((List<T>) new ArrayList<Permission>(permission.getChild()));
			}
			return super.left();
		} catch (Exception e) {
			addActionError("获取菜单出现异常！");
			log.error("获取菜单出现异常！" + e);
			e.printStackTrace();
			return LEFT;
		}
	}

	/** 判断是否是有条件查询
	 * 
	 * @param value 本页查询参数值列表
	 * @return 是查询(true) 否(false) */
	public boolean isSearch(List<Object> value) {
		for (int i = 0; i < value.size(); i++) {
			if (value.get(i) != null && StringUtils.isNotBlank(value.get(i).toString())) {
				return true;
			}
		}
		return false;
	}

	/** 获得站点ID */
	public Integer getWebId() {
		return getWeb().getId();
	}

	/** 获得站点对象 */
	public Site getWeb() {
		Site site = websiteSvc.getWebsite(domainName);
		if (site == null) {
			throw new RuntimeException("不存在与该域名匹配的站点：" + ServletActionContext.getRequest().getAttribute(DomainNameAware.DOMAIN_NAME));
		}
		return site;
	}

	/** 获得根站点ID */
	// public Integer getRootWebId() {
	// return getRootWeb().getId();
	// }
	/** 获得根站点对象 */
	// public Site getRootWeb() {
	// return getWeb().getRootWeb();
	// }
	/** 获得用户ID */
	public Integer getUserId() {
		return (Integer) contextPvd.getSessionAttr(User.USER_KEY);
	}

	/** 获得用户对象 */
	public User getUser() {
		Integer userId = getUserId();
		if (userId == null) {
			return null;
		} else {
			return userSvc.findById(userId);
		}
	}

	/** 获得管理员ID */
	public Integer getAdminId() {
		return (Integer) contextPvd.getSessionAttr(User.ADMIN_KEY);
	}

	/** 获得管理员对象 */
	public User getAdmin() {
		Integer adminId = getAdminId();
		if (adminId == null) {
			return null;
		} else {
			User user = userSvc.findById(adminId);
			if (user != null && user.isSuper()) {
				return user;
			}
			return null;
		}
	}

	/** 获得页面执行时间ms
	 * 
	 * @return 返回页面执行时间(s)。-1代表没有找到页面开始执行时间。 */
	public float getProcessedIn() {
		Long time = (Long) contextPvd.getRequestAttr(ProcessingStartInterceptor.PROCESSING_START);
		if (time != null) {
			return (System.nanoTime() - time) / 1000 / 1000000F;
		} else {
			return -1;
		}
	}

	/** 页面重定向
	 * 
	 * @param url */
	protected String redirect(String url) {
		this.redirectUrl = url;
		return Constants.REDIRECT;
	}

	/** 获得页面cookie指定的每页显示记录数 */
	protected int getCookieCount() {
		Cookie c = contextPvd.getCookie(COOKIE_COUNT);
		int count = 0;
		if (c != null) {
			try {
				count = Integer.parseInt(c.getValue());
			} catch (Exception e) {}
		}
		if (count <= 0) {
			count = DEFAULT_COUNT;
		} else if (count > 200) {
			count = COOKIE_MAX_COUNT;
		}
		return count;
	}

	public String getIp() {
		return contextPvd.getRemoteIp();
	}

	public String getUri() {
		return contextPvd.getRequestURL();
	}

	public void logOperating(String title, String content) {
		logSvc.operating(title, content, getIp(), getUri(), getWeb(), getUser());
	}

	public void logLoginSuccess(String title) {
		logSvc.loginSuccess(getUser(), title, getIp(), getUri());
	}

	public void logLoginFailure(String title, String content) {
		logSvc.loginFailure(title, content, getIp(), getUri());
	}

	private void handleParams() {
		params = contextPvd.getActionContext().getParameters();
	}

	public String getDomainName() {
		return domainName;
	}

	@Override
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public String getDefResult() {
		return defResult;
	}

	public void setDefResult(String defResult) {
		this.defResult = defResult;
	}

	public boolean isCheckCodeEnabled() {
		return checkCodeEnabled;
	}

	public void setCheckCodeEnabled(boolean checkCodeEnabled) {
		this.checkCodeEnabled = checkCodeEnabled;
	}

	public List<T> getTopMenus() {
		Permission root = permissionSvc.getRoot(getAdminId());
		if (root != null) {
			for (Permission child : root.getChild()) {
				topMenus.add((T) child);
			}
			return topMenus;
		}
		return null;
	}

	public void setTopMenus(List<T> topMenus) {
		this.topMenus = topMenus;
	}

	public List<T> getLeftMenus() {
		return leftMenus;
	}

	public void setLeftMenus(List<T> leftMenus) {
		this.leftMenus = leftMenus;
	}

	public T getLeftMenu() {
		return leftMenu;
	}

	public void setLeftMenu(T leftMenu) {
		this.leftMenu = leftMenu;
	}

	public String getLeftMenuType() {
		return leftMenuType;
	}

	public void setLeftMenuType(String leftMenuType) {
		this.leftMenuType = leftMenuType;
	}

	public String getLeftMenu_url() {
		if (leftMenu_url == null || "".equals(leftMenu_url)) {
			return "permission_edit";
		}
		return leftMenu_url;
	}

	public void setLeftMenu_url(String leftMenuUrl) {
		leftMenu_url = leftMenuUrl;
	}

	public String getLeftMenu_durl() {
		if (leftMenu_durl == null || "".equals(leftMenu_durl)) {
			return "permission_list";
		}
		return leftMenu_durl;
	}

	public void setLeftMenu_durl(String leftMenuDurl) {
		leftMenu_durl = leftMenuDurl;
	}

	public String getActionName() {
		return contextPvd.getActionContext().getName();
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getSrcLeft() {
		if (srcLeft == null || "".equals(srcLeft)) {
			return getActionName().split("_")[0] + "_left";
		}
		return srcLeft;
	}

	public void setSrcLeft(String srcLeft) {
		this.srcLeft = srcLeft;
	}

	public String getSrcRight() {
		if (srcRight == null || "".equals(srcRight)) {
			return getActionName().split("_")[0] + "_list";
		}
		return srcRight;
	}

	public void setSrcRight(String srcRight) {
		this.srcRight = srcRight;
	}

	public String getLeftNav() {
		return leftNav;
	}

	public void setLeftNav(String leftNav) {
		this.leftNav = leftNav;
	}

	public String getRightNav() {
		return rightNav;
	}

	public void setRightNav(String rightNav) {
		this.rightNav = rightNav;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public Map<String, Object> getJsonRoot() {
		return jsonRoot;
	}

	public void setJsonRoot(Map<String, Object> jsonRoot) {
		this.jsonRoot = jsonRoot;
	}

	public void addJson(String string, Object object) {
		getJsonRoot().put(string, object);
	}

	public void addUploadRule() {
		UploadRule rule = new UploadRule(getWeb().getUploadPath());
		uploadRuleId = rule.hashCode();
		contextPvd.setSessionAttr(UploadRule.KEY + uploadRuleId, rule);
	}

	public void removeUploadRule() {
		// 删除未被使用的图片
		uploadRule.clearUploadFile();
		// 清除上传规则
		contextPvd.removeSessionAttribute(UploadRule.KEY + getUploadRuleId());
	}

	public UploadRule getUploadRule() {
		return uploadRule;
	}

	public void setUploadRule(UploadRule uploadRule) {
		this.uploadRule = uploadRule;
	}

	public int getUploadRuleId() {
		return uploadRuleId;
	}

	public void setUploadRuleId(int uploadRuleId) {
		this.uploadRuleId = uploadRuleId;
	}
}

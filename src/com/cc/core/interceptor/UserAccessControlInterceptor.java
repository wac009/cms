package com.cc.core.interceptor;

import java.util.*;

import javax.servlet.*;

import org.apache.struts2.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.context.*;
import org.springframework.web.context.support.*;

import com.cc.cms.entity.main.*;
import com.cc.cms.service.main.*;
import com.opensymphony.xwork2.*;
import com.opensymphony.xwork2.interceptor.*;

/**
 * 用户权限控制拦截器，判断用户是否登录。 未登录就转到登录页面，否则接续执行请求链
 * 
 * @author wangcheng
 */
public class UserAccessControlInterceptor extends AbstractInterceptor {
	private static final long	serialVersionUID	= 6409102814637865688L;
	public static final Logger	log					= LoggerFactory.getLogger(UserAccessControlInterceptor.class);
	@Autowired
	private IUserSvc			userSvc;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		ActionContext ac = invocation.getInvocationContext();
		ServletContext servletContext = (ServletContext) ac.get(StrutsStatics.SERVLET_CONTEXT);
		WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		if (wac == null) {
			log.error("ApplicationContext could not be found.");
			return "errorPage";
		} else {
			if (ac.getSession().get(User.ADMIN_KEY) == null) {
				return "login";
			}
			// 检查访问地址是否在管理员的权限集中
			String actionName = ac.getName();
			Set<String> fiSet = userSvc.getRights((Integer) ac.getSession().get(User.ADMIN_KEY));
			if ((actionName != null) && (fiSet == null || !fiSet.contains(actionName))) {
				// 处理用户无权访问
				log.error("无权访问:{}", actionName);
				ac.getValueStack().set("interceptError", "您没有此操作权限！");
				if (actionName.endsWith("_left")) {
					return "errorPageLeft";
				}
				// 当用户在界面内操作时，在页面内部显示提示，提高用户体验
				// String[] ajaxAction= {"_add_list","_edit_list","_delete_list","_up_list","_down_list"};
				// for (String action : ajaxAction) {
				// if (actionName.endsWith(action)) {
				// AjaxMessagesJson ajaxMessagesJson = (AjaxMessagesJson) wac.getBean("ajaxMessagesJson");
				// ajaxMessagesJson.setMessage("E_NO_Rights", "您没有此操作权限！");
				// ac.getValueStack().set("ajaxMessagesJson", ajaxMessagesJson);
				// log.info("return ajax");
				// return "ajaxjson";
				// }
				// }
				return "errorPage";
			}
		}
		return invocation.invoke();
	}
}

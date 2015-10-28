<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/inc/path.inc"%>
<%@include file="/inc/taglib.inc"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <%@ include file="/inc/manage.inc"%>
    <%@ include file="/inc/prompt.inc"%>
  </head>
  
  <body id="admin_index" scroll="no">
  	<div id="container_index">
        <div id="header">
          <div class="logo"></div>
          <p id="info_bar">
          		您好 [<s:property value="user.username" />]  [<s:property value="web.name"/>]  
                <a href="javascript:void()" class="white" onclick="Confirm('确定退出吗？',null,null,null,handler_logout);">退出登录</a>
                <a href="http://<s:property value="web.domain"/>" class="white" target="_top _blank">网站首页</a>
          </p>
          <div id="menu">
            <ul>
              <s:iterator id="topMenu" value="%{topMenus}">
                <s:if test="%{!(#topMenu.url).equals(#session.topmenu_index_url)}">
	              	<li>
	              		<a class="menu" alt="<s:property value="#topMenu.name"/>" href="<s:property value="#topMenu.url"/>.jspa" target="mainFrame">
	              			<span><s:property value="#topMenu.name"/></span>
	              		</a>
	              	</li>
              	</s:if>
              	<s:else>
              		<li>
	              		<a class="menu selected" alt="<s:property value="#topMenu.name"/>" href="<s:property value="#topMenu.url"/>.jspa" target="mainFrame">
	              			<span><s:property value="#topMenu.name"/></span>
	              		</a>
	              	</li>
              	</s:else>
              </s:iterator>
            </ul>
          </div>
          <div class="right-round"></div>
       </div>
       <div id="main">
       		<div id="head_bottom"></div>
       		<iframe name="mainFrame" id="mainFrame" frameborder="0" src="${topmenu_index_url}.jspa" ></iframe>
       </div>
    </div>
  </body>
</html>
<!-- javascript脚本 -->
<script language="JavaScript">
var screen_w = document.body.scrollWidth;
window.onresize=function()
{
	var screen_w = document.body.scrollWidth;	
	var screen_h = parseInt(document.documentElement.clientHeight);

	$("#container_index").height(screen_h-6).width(screen_w-8);
	$("#main").height(screen_h-6-58).width(screen_w-8-2);
	$("#mainFrame").height(screen_h-6-58-5).width(screen_w-8-2);
}
window.onresize();
function handler_logout(click){
	if(click=='ok'){
		window.location.href="<%=baseMngPath%>login!logout.jspa";
	}
}
</script>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/inc/path.inc"%>
<%@include file="/inc/taglib.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<%@ include file="/inc/manage.inc"%>
	<meta http-equiv="refresh" content="300"> <!-- 5分钟 刷新页面一次 -->
  </head>
  
  <body>
  	 <!-- 用户信息 -->
  	 <div class="welcome1">
	      您好<span class="bold"><s:property value="user.username" /></span>！
	      <!-- 
	      您是<s:property value="web.name"/><a href="http://<s:property value="web.domain"/>" target="_top _black"> http://<s:property value="web.domain"/></a> 的
	      <s:iterator value="admin.roles" id="role" status="stat"><s:property value="#role.name"/><s:if test="!#stat.last">,</s:if></s:iterator>,
	      本次登录：<cc:datetimes format="yyyy-MM-dd HH:mm:ss" datetype="date" value="user.currentLoginTime"/>[<s:property value="user.currentLoginIp" />]
	       -->
	      【上次登录时间：<cc:datetimes format="yyyy-MM-dd HH:mm:ss" datetype="date" value="user.lastLoginTime"/>】
	      【IP：<s:property value="user.lastLoginIp" />】
	      【共登录<s:property value="user.loginCount" />次】
	  </div>
  	  <!--快捷菜单-->
  	  <!-- 
	  <div class="welcome2">
	      	<s:iterator id="quickMenu" value="#session.quickMenus">
	      		<span class="func"><a href="<s:property value="#quickMenu.url"/>.jspa" class="add"><s:property value="#quickMenu.name"/></a></span>
	      	</s:iterator>
	  </div>
	   -->
	  <!-- 系统信息 -->
  	 <div class="welcome3">
  	 <!-- 
	      【采集器状态：<span class="red bold">暂停</span> 】
	      【程序版本：全国老龄门户内容管理系统（cncacms）<span class="red bold">V1.0</span>】
	  -->
	      【系统状态】已用内存：<span class="red"><s:property value="%{usedMemory}"/>MB</span>  
						    剩余内存：<span class="green"><s:property value="%{useableMemory}"/>MB </span>  
						    全部内存：<span style="color:#ff8400"><s:property value="%{maxMemory}"/>MB </span>
	  </div>
	  <!-- 内容统计 -->
 	  <table class="stat_info" >
 	  <caption>内容统计</caption>
	      <tr>
	          <th></th><th>更新数量</th>
	      </tr>
	      <tr><td>今日</td><s:iterator id="ctoday" value="%{cs.today}"><td><s:property value="#ctoday.count"/></td></s:iterator></tr>
	      <tr><td>昨日</td><s:iterator id="cyesterday" value="%{cs.yesterday}"><td><s:property value="#cyesterday.count"/></td></s:iterator></tr>
	      <tr><td>本周</td><s:iterator id="cthisweek" value="%{cs.thisweek}"><td><s:property value="#cthisweek.count"/></td></s:iterator></tr>
	      <tr><td>本月</td><s:iterator id="cthismonth" value="%{cs.thismonth}"><td><s:property value="#cthismonth.count"/></td></s:iterator></tr>
	      <tr><td>本年</td><s:iterator id="cthisyear" value="%{cs.thisyear}"><td><s:property value="#cthisyear.count"/></td></s:iterator></tr>
	      <tr><td>累计</td><s:iterator id="ctotal" value="%{cs.total}"><td><s:property value="#ctotal.count"/></td></s:iterator></tr>
  	  </table>
  	  <!-- 访问量 
 	  <table class="stat_info" >
 	  <caption>网站访问统计</caption>
	      <tr>
	          <th></th><th>PV</th><th>IP</th><th>独立访客</th>
	      </tr>
	      <tr><td>今日</td><s:iterator id="vtoday" value="%{vs.today}"><td><s:property value="#vtoday.count"/></td></s:iterator></tr>
	      <tr><td>昨日</td><s:iterator id="vyesterday" value="%{vs.yesterday}"><td><s:property value="#vyesterday.count"/></td></s:iterator></tr>
	      <tr><td>本周</td><s:iterator id="vthisweek" value="%{vs.thisweek}"><td><s:property value="#vthisweek.count"/></td></s:iterator></tr>
	      <tr><td>本月</td><s:iterator id="vthismonth" value="%{vs.thismonth}"><td><s:property value="#vthismonth.count"/></td></s:iterator></tr>
	      <tr><td>本年</td><s:iterator id="vthisyear" value="%{vs.thisyear}"><td><s:property value="#vthisyear.count"/></td></s:iterator></tr>
	      <tr><td>累计</td><s:iterator id="vtotal" value="%{vs.total}"><td><s:property value="#vtotal.count"/></td></s:iterator></tr>
  	  </table>
  	  -->
  	  <!-- 用户注册统计
 	  <table class="stat_info" >
 	  <caption>用户注册统计</caption>
	      <tr>
	          <th></th><th>注册数量</th>
	      </tr>
	      <tr><td>今日</td><s:iterator id="utoday" value="%{us.today}"><td><s:property value="#utoday.count"/></td></s:iterator></tr>
	      <tr><td>昨日</td><s:iterator id="uyesterday" value="%{us.yesterday}"><td><s:property value="#uyesterday.count"/></td></s:iterator></tr>
	      <tr><td>本周</td><s:iterator id="uthisweek" value="%{us.thisweek}"><td><s:property value="#uthisweek.count"/></td></s:iterator></tr>
	      <tr><td>本月</td><s:iterator id="uthismonth" value="%{us.thismonth}"><td><s:property value="#uthismonth.count"/></td></s:iterator></tr>
	      <tr><td>本年</td><s:iterator id="uthisyear" value="%{us.thisyear}"><td><s:property value="#uthisyear.count"/></td></s:iterator></tr>
	      <tr><td>累计</td><s:iterator id="utotal" value="%{us.total}"><td><s:property value="#utotal.count"/></td></s:iterator></tr>
  	  </table>
  	   -->
  	  <!-- 站内信信息 
 	  <table class="stat_info"  style="clear:both;">
 	  <caption>站内信</caption>
	      <tr>
	          <th></th><th>收件箱</th><th>发件箱</th><th>草稿箱</th>
	      </tr>
	      <tr><td>今日</td><s:iterator id="mtoday" value="%{ms.today}"><td><s:property value="#mtoday.count"/></td></s:iterator></tr>
	      <tr><td>昨日</td><s:iterator id="myesterday" value="%{ms.yesterday}"><td><s:property value="#myesterday.count"/></td></s:iterator></tr>
	      <tr><td>本周</td><s:iterator id="mthisweek" value="%{ms.thisweek}"><td><s:property value="#mthisweek.count"/></td></s:iterator></tr>
		  <tr><td>本月</td><s:iterator id="mthismonth" value="%{ms.thismonth}"><td><s:property value="#mthismonth.count"/></td></s:iterator></tr>
		  <tr><td>本年</td><s:iterator id="mthisyear" value="%{ms.thisyear}"><td><s:property value="#mthisyear.count"/></td></s:iterator></tr>
		  <tr><td>累计</td><s:iterator id="mtotal" value="%{ms.total}"><td><s:property value="#mtotal.count"/></td></s:iterator></tr>
  	  </table>
  	  -->
  	  <!-- 留言信息-->
 	  <table class="stat_info" style="width:33%;">
 	  <caption>留言信息</caption>
	      <tr>
	          <th></th><th>留言数量</th><th>已审核</th>
	      </tr>
	      <tr><td>今日</td><s:iterator id="gtoday" value="%{gs.today}"><td><s:property value="#gtoday.count"/></td></s:iterator></tr>
	      <tr><td>昨日</td><s:iterator id="gyesterday" value="%{gs.yesterday}"><td><s:property value="#gyesterday.count"/></td></s:iterator></tr>
	      <tr><td>本周</td><s:iterator id="gthisweek" value="%{gs.thisweek}"><td><s:property value="#gthisweek.count"/></td></s:iterator></tr>
		  <tr><td>本月</td><s:iterator id="gthismonth" value="%{gs.thismonth}"><td><s:property value="#gthismonth.count"/></td></s:iterator></tr>
		  <tr><td>本年</td><s:iterator id="gthisyear" value="%{gs.thisyear}"><td><s:property value="#gthisyear.count"/></td></s:iterator></tr>
		  <tr><td>累计</td><s:iterator id="gtotal" value="%{gs.total}"><td><s:property value="#gtotal.count"/></td></s:iterator></tr>
  	  </table> 
  	  <!-- 论坛信息 -->
 	  <table class="stat_info" style="width:33%;">
 	  <caption>论坛信息</caption>
	      <tr>
	          <th></th><th>帖子数量</th><th>已审核</th><th>已回复</th>
	      </tr>
	      <tr><td>今日</td><td></td><td></td><td></td></tr>
	      <tr><td>昨日</td><td></td><td></td><td></td></tr>
	      <tr><td>本周</td><td></td><td></td><td></td></tr>
	      <tr><td>本月</td><td></td><td></td><td></td></tr>
	      <tr><td>本年</td><td></td><td></td><td></td></tr>
	      <tr><td>累计</td><td></td><td></td><td></td></tr>
  	  </table>
  </body>
</html>

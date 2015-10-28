<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/inc/path.inc"%>
<%@include file="/inc/taglib.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<%@ include file="/inc/manage.inc"%>
  </head>

  <body>
  	<div id="container">
  	  	<div class="navi"><span class="navi_b">当前位置:</span>
  	  		<span class="navi_h">统计 - 流量初始化</span>
  	  	</div>
  		<div class="mainData">
  		<div class="actInfo"><s:actionerror theme="ccweb"/><s:actionmessage theme="ccweb"/></div> 
  		<s:form  id="queryForm" action="statistic_flowInitSubmit_flowInit">
  			<div class="search">
	  			开始时间<s:textfield name="startTime" cssClass="input Wdate" cssStyle="padding:2px 2px 1px 2px;" 
					onclick="WdatePicker({isShowWeek:true,dateFmt:'yyyy-MM-dd'})"/>
	  			结束时间<s:textfield name="endTime" cssClass="input Wdate" cssStyle="padding:2px 2px 1px 2px;" 
					onclick="WdatePicker({isShowWeek:true,dateFmt:'yyyy-MM-dd'})"/>
	  			<input type="submit" value="提  交" class="btn" />
	  		</div>
  		</s:form>
  		</div>
  	</div>	
  </body>
</html>

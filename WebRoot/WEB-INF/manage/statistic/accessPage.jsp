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
  	  		<span class="navi_h">统计 - 受访页面</span>
  	  	</div>
  		<div class="mainData">
  		<div class="actInfo"><s:actionerror theme="ccweb"/><s:actionmessage theme="ccweb"/></div> 
  		<s:form  id="queryForm">
	  		<div class="data">
				<table class="pn-ltable">
				<thead class="pn-lthead">
				<tr>
					<th>来访网站</th><th>次数</th><th>百分比</th>
				</tr>
				</thead>
				
				<s:if test="%{list!=null||list.size>0}">
				<tbody class="pn-ltbody">
				<s:iterator id="row" value="%{list}">
				  <tr name="tr_ids" onmouseover="Pn.LTable.lineOver(this);" onmouseout="Pn.LTable.lineOut(this);" onclick="Pn.LTable.lineSelect(this);">
					<td><s:property value="#row.description"/></td>
					<td><s:property value="#row.count"/></td>
					<td>
						<s:property value="#row.percent"/>
						<img src="<%=basePath%>/res/m/img/vote_bar.gif" width="<s:property value="#row.barWidth"/>" height="10px" border="0"/>
					</td>
				</tr>
			    </s:iterator>
				</tbody>
				</s:if>
				</table>				
  			</div>
  		</s:form>
  		</div>
  	</div>	
  </body>
</html>

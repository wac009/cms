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
  	  	<div class="navi"><span class="navi_b">当前位置:</span><span class="navi_h">投票管理 - 投票结果</span></div>
  		<div class="mainData">
  		<div class="actInfo"><s:actionerror theme="ccweb"/><s:actionmessage theme="ccweb"/></div>
  		<s:form id="dataForm">
  			<s:hidden name="pageNo"/>
	  		<div class="data">
				<table class="pn-ltable">
				<s:if test="topicId!=null&&voteTopic!=null">
					<div style="width:100%;background-color:#5aadf6;">
					  <tr>
					    <td height="25" colspan="2" bgcolor="#AAD5FB"><strong>投票主题:<s:property value="voteTopic.title"/></strong></td>
					  </tr>
					  <tr>
					    <td height="25" colspan="2" bgcolor="#FFFFFF">投票总数：<s:property value="voteTopic.totalCount"/></td>
					  </tr>
					  <s:iterator value="voteTopic.items" id="item" status="status">
					  <tr>
					    <td width="10%" height="25" bgcolor="#FFFFFF"><s:property value="#status.index+1"/>、<s:property value="#item.title"/></td>
					    <td width="90%" bgcolor="#FFFFFF">
					    <img src="<%=r_m%>/img/vote_bar.gif"  width="<s:property value="#item.percent"/>%" height="10px" border="0"/>
					    <s:property value="#item.percent"/>%(<s:property value="#item.voteCount"/>)</td>
					  </tr>
					  </s:iterator>
					</div>
				</s:if>
				
				<s:if test="%{pagination.list!=null||pagination.list.size>0}">
				<tbody class="pn-ltbody">
				<s:iterator id="row" value="%{pagination.list}">
					<div style="width:100%;background-color:#5aadf6;">
					  <tr>
					    <td height="25" colspan="2" bgcolor="#AAD5FB"><strong>投票主题:<s:property value="#row.title"/></strong></td>
					  </tr>
					  <tr>
					    <td height="25" colspan="2" bgcolor="#FFFFFF">投票总数：<s:property value="#row.totalCount"/></td>
					  </tr>
					  <s:iterator value="#row.items" id="item" status="status">
					  <tr>
					    <td width="10%" height="25" bgcolor="#FFFFFF"><s:property value="#status.index+1"/>、<s:property value="#item.title"/></td>
					    <td width="90%" bgcolor="#FFFFFF">
					    <img src="<%=r_m%>/img/vote_bar.gif" 
					    width="<s:property value="#item.percent"/>%" height="10px" border="0"/>
					    <s:property value="#item.percent"/>%(<s:property value="#item.voteCount"/>)</td>
					  </tr>
					  </s:iterator>
					</div>
			    </s:iterator>
				</tbody>
				</s:if>
				<tfoot>				
					<%@ include file="/inc/page.inc"%>
				</tfoot>
				</table>				
  			</div>
  		</s:form>
  		</div>
  	</div>	
  </body>
</html>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/inc/path.inc"%>
<%@include file="/inc/taglib.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<%@ include file="/inc/manage.inc"%>
  </head>
  
  <body>
  	<div class="navi"><span class="navi_b">当前位置:</span><span class="navi_h">站内信管理 - 回复</span>
  		<span class="navi_f">
  			<input class="del btn" type="button" value="提交" onclick="$('#dataForm').submit();"/>
  			<a href='javascript:history.go(-1)' style="padding:0 5px;">返回</a>
  			<%@ include file="inc.jsp"%>
  		</span>
  	</div>
	<div class="mainData">
	<div class="actInfo"><s:actionerror theme="ccweb"/><s:actionmessage theme="ccweb"/></div>
	<s:form action="message_send_sendbox" cssClass="vldform" id="dataForm">
		<table width="100%" class="table_edit">
			<tr>
				<td class="label">收件人：</td>
				<td class="content"><s:property value="receiverMessage.msgSendUser.username"/><s:hidden name="uid" value="%{receiverMessage.msgSendUser.id}"/></td>
			</tr>
			<tr>
				<td class="label">标题：</td>
				<td class="content">
					<s:textfield name="message.msgTitle"  value="%{receiverMessage.msgTitle}" id="title" cssClass="input required" cssStyle="width:400px;"/>
				</td>
			</tr>
			<tr>
				<td class="label">内容：</td>
				<td class="content">
					<s:textarea name="message.msgContent" cssClass="input txtArea" rows="50"  cssStyle="height:200px;"/>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<div id="btn" class="btnDiv_fa">
						<input type="submit" value=" 发 送 " class="btn_fa">
						<input type="reset" value=" 重 置 " class="btn_fa">
						<input type="button" value=" 保存草稿 " class="btn_fa">	
					</div>
				</td>
			</tr>
		</table>
	</s:form>
	</div>
  </body>
</html>

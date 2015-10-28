<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/inc/path.inc"%>
<%@include file="/inc/taglib.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<%@ include file="/inc/manage.inc"%>
  </head>
  
  <body>
  	<div class="navi"><span class="navi_b">当前位置:</span><span class="navi_h">站内信管理 - 查看</span>
  		<span class="navi_f">
  			<a href='javascript:history.go(-1)' style="padding:0 5px;">返回</a>
  		</span>
  	</div>
	<div class="mainData">
	<div class="actInfo"><s:actionerror theme="ccweb"/><s:actionmessage theme="ccweb"/></div>
	<s:form action="message_send_sendbox" cssClass="vldform" id="dataForm">
		<table width="100%" class="table_edit">
			<s:if test="receiverMessage!=null">
				<tr>
					<td class="label">发件人：</td>
					<td class="content"><s:property value="receiverMessage.msgSendUser.username"/></td>
				</tr>
				<tr>
					<td class="label">收件时间：</td>
					<td class="content"><cc:datetimes format="yyyy-MM-dd HH:mm:ss" datetype="date" value="receiverMessage.sendTime"/></td>
				</tr>
				<tr>
					<td class="label">状态：</td>
					<td class="content">
						<s:if test="receiverMessage.msgStatus"><span class="green">已读</span></s:if><s:else><span class="red">未读</span></s:else>
					</td>
				</tr>
				<tr>
					<td class="label">标题：</td>
					<td class="content"><s:property value="receiverMessage.msgTitle"/></td>
				</tr>
				<tr>
					<td class="label">内容：</td>
					<td class="content"><s:property value="receiverMessage.msgContent"/></td>
				</tr>
				<tr>
					<td colspan="2">
						<div id="btn" class="btnDiv_fa">
							<a href="message_forward.jspa?id=<s:property value="receiverMessage.id"/>&mf=0">转发</a>
							<a href="message_reply.jspa?id=<s:property value="receiverMessage.id"/>">回复</a>
							<input type="button" value=" 删除 " class="btn_fa">
						</div>
					</td>
				</tr>
			</s:if>
			<s:else>
				<tr>
					<td class="label">收件人：</td>
					<td class="content"><s:property value="message.msgReceiverUser.username"/></td>
				</tr>
				<tr>
					<td class="label">发件时间：</td>
					<td class="content"><cc:datetimes format="yyyy-MM-dd HH:mm:ss" datetype="date" value="message.sendTime"/></td>
				</tr>
				<tr>
					<td class="label">状态：</td>
					<td class="content">
						<s:if test="message.msgStatus"><span class="green">已读</span></s:if><s:else><span class="red">未读</span></s:else>
					</td>
				</tr>
				<tr>
					<td class="label">标题：</td>
					<td class="content"><s:property value="message.msgTitle"/></td>
				</tr>
				<tr>
					<td class="label">内容：</td>
					<td class="content"><s:property value="message.msgContent"/></td>
				</tr>
				<tr>
					<td colspan="2">
						<div id="btn" class="btnDiv_fa">
							<a href="message_forward.jspa?id=<s:property value="message.id"/>&mf=1">转发</a>
							<input type="button" value=" 删除 " class="btn_fa">
						</div>
					</td>
				</tr>
			</s:else>
		</table>
	</s:form>
	</div>
  </body>
</html>

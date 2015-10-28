<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/inc/path.inc"%>
<%@include file="/inc/taglib.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<%@ include file="/inc/manage.inc"%>
  </head>
  
  <body>
  	<div class="navi"><span class="navi_b">当前位置:</span><span class="navi_h">站内信管理 - 转发</span>
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
				<td class="content">
					<s:select name="uid" list="users" listKey="id" listValue="username" cssClass="select focus" />
					会员组选择:
					<s:select name="gid" list="groups" listKey="id" listValue="name" cssClass="select " />
					<span class="help">请填写收件人或者选择会员组（两者必选其一，选择会员组批量发送站内信）</span>
				</td>
			</tr>
			<s:if test="mf==0">
				<tr>
					<td class="label">标题：</td>
					<td class="content">
						<s:textfield name="message.msgTitle" value="%{receiverMessage.msgTitle}" id="title" cssClass="input required" cssStyle="width:400px;"/>
					</td>
				</tr>
				<tr>
					<td class="label">内容：</td>
					<td class="content">
						<s:textarea name="message.msgContent" value="%{receiverMessage.msgContent}" cssClass="input txtArea" rows="50"  cssStyle="height:200px;"/>
					</td>
				</tr>
			</s:if>
			<s:else>
				<tr>
					<td class="label">标题：</td>
					<td class="content">
						<s:textfield name="message.msgTitle" id="title" cssClass="input required" cssStyle="width:400px;"/>
					</td>
				</tr>
				<tr>
					<td class="label">内容：</td>
					<td class="content">
						<s:textarea name="message.msgContent" cssClass="input txtArea" rows="50"  cssStyle="height:200px;"/>
					</td>
				</tr>
			</s:else>
			<tr>
				<td colspan="2">
					<div id="btn" class="btnDiv_fa">
						<input type="submit" value=" 发 送 " class="btn_fa">
						<input type="reset" value=" 重 置 " class="btn_fa">
					</div>
				</td>
			</tr>
		</table>
	</s:form>
	</div>
  </body>
</html>

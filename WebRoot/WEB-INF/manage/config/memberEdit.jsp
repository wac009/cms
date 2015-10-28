<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/inc/path.inc"%>
<%@include file="/inc/taglib.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<%@ include file="/inc/manage.inc"%>
  </head>
  
  <body>
  	<div class="navi"><span class="navi_b">当前位置:</span><span class="navi_h">配置管理 - 会员配置</span></div>
	<div class="mainData">
	<div class="actInfo"><s:actionerror theme="ccweb"/><s:actionmessage theme="ccweb"/></div> 
	<s:form action="config_memberUpdate_memberEdit" cssClass="vldform">
		<s:hidden name="config.id" />
		<table width="100%" class="table_edit">
			<tr>
				<td class="label">开启会员功能：</td>
				<td class="content">
					<s:radio name="memberConfig.memberOn" list="#{'true':'是','false':'否 '}" listKey="key" listValue="value" />
					<s:fielderror fieldName="memberConfig.memberOn" theme="ccweb"/>
					<span class="red">*</span>
				</td>
			</tr>
			<tr>
				<td class="label">开启会员注册：</td>
				<td class="content">
					<s:radio name="memberConfig.registerOn"  list="#{'true':'是','false':'否 '}" listKey="key" listValue="value" />
					<s:fielderror fieldName="memberConfig.registerOn" theme="ccweb"/>
					<span class="red">*</span>
				</td>
			</tr>
			<tr>
				<td class="label">用户名最小长度：</td>
				<td class="content">
					<s:textfield name="memberConfig.usernameMinLen" id="usernameMinLen" cssClass="input required focus" />
					<s:fielderror fieldName="memberConfig.usernameMinLen" theme="ccweb"/>
					<span class="red">*</span>
				</td>
			</tr>
			<tr>
				<td class="label">密码最小长度：</td>
				<td class="content">
					<s:textfield name="memberConfig.passwordMinLen" id="passwordMinLen" cssClass="input required" />
					<s:fielderror fieldName="memberConfig.passwordMinLen" theme="ccweb"/>
					<span class="red">*</span>
				</td>
			</tr>
			<tr>
				<td class="label">用户头像宽度：</td>
				<td class="content">
					<s:textfield name="memberConfig.userImgWidth" id="userImgWidth" cssClass="input required " />
					<s:fielderror fieldName="memberConfig.userImgWidth" theme="ccweb"/>
					<span class="red">*</span>
					<span class="help">(px)</span>
				</td>
			</tr>
			<tr>
				<td class="label">用户头像高度：</td>
				<td class="content">
					<s:textfield name="memberConfig.userImgHeight" id="userImgHeight" cssClass="input required " />
					<s:fielderror fieldName="memberConfig.userImgHeight" theme="ccweb"/>
					<span class="red">*</span>
					<span class="help">(px)</span>
				</td>
			</tr>
			<tr>
				<td class="label">用户名保留字：</td>
				<td class="content">
					<s:textarea name="memberConfig.usernameReserved" id="usernameReserved" cssClass="input txtArea" />
					<s:fielderror fieldName="memberConfig.usernameReserved" theme="ccweb"/>
					<span class="help">可以使用 * 作为通配符，如: *admin*</span>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<div id="btn" class="btnDiv_fa">
						<input type="submit" value=" 提 交 " class="btn_fa">	
						<input type="reset" value=" 重 置 " class="btn_fa">
					</div>
				</td>
			</tr>
		</table>
	</s:form>
	</div>
  </body>
</html>


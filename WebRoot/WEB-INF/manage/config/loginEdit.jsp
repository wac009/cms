<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/inc/path.inc"%>
<%@include file="/inc/taglib.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<%@ include file="/inc/manage.inc"%>
  </head>
  
  <body>
  	<div class="navi"><span class="navi_b">当前位置:</span><span class="navi_h">配置管理 - 登录配置</span></div>
	<div class="mainData">
	<div class="actInfo"><s:actionerror theme="ccweb"/><s:actionmessage theme="ccweb"/></div> 
	<s:form action="config_loginUpdate_loginEdit" cssClass="vldform">
		<s:hidden name="config.id" />
		<table width="100%" class="table_edit">
			<tr>
				<td class="label">登录错误次数：</td>
				<td class="content">
					<s:textfield name="loginConfig.errorTimes" id="errorTimes" cssClass="input required focus" />
					<s:fielderror fieldName="loginConfig.errorTimes" theme="ccweb"/>
					<span class="help">达到错误次数后显示验证码</span>
				</td>
			</tr>
			<tr>
				<td class="label">登录错误时间：</td>
				<td class="content">
					<s:textfield name="loginConfig.errorInterval" id="errorInterval" cssClass="input required" />
					<s:fielderror fieldName="loginConfig.errorInterval" theme="ccweb"/>
					<span class="help">(分钟)。超过时间重计次数</span>
				</td>
			</tr>
			<tr>
				<td class="label">邮件服务器：</td>
				<td class="content">
					<s:textfield name="emailSender.host" id="host" cssClass="input required" />
					<s:fielderror fieldName="emailSender.host" theme="ccweb"/>
					<span class="red">*</span>
				</td>
			</tr>
			<tr>
				<td class="label">邮件端口：</td>
				<td class="content">
					<s:textfield name="emailSender.port" id="port" cssClass="input " />
					<s:fielderror fieldName="emailSender.port" theme="ccweb"/>
					<span class="help">留空则为默认端口</span>
				</td>
			</tr>
			<tr>
				<td class="label">邮件用户名：</td>
				<td class="content">
					<s:textfield name="emailSender.username" id="username" cssClass="input " />
					<s:fielderror fieldName="emailSender.username" theme="ccweb"/>
				</td>
			</tr>
			<tr>
				<td class="label">邮件密码：</td>
				<td class="content">
					<s:textfield name="emailSender.password" id="password" cssClass="input " />
					<s:fielderror fieldName="emailSender.password" theme="ccweb"/>
				</td>
			</tr>
			<tr>
				<td class="label">邮件编码：</td>
				<td class="content">
					<s:textfield name="emailSender.encoding" id="encoding" cssClass="input " />
					<s:fielderror fieldName="emailSender.encoding" theme="ccweb"/>
				</td>
			</tr>
			<tr>
				<td class="label">发件人：</td>
				<td class="content">
					<s:textfield name="emailSender.personal" id="personal" cssClass="input " />
					<s:fielderror fieldName="emailSender.personal" theme="ccweb"/>
				</td>
			</tr>
			<tr>
				<td class="label">找回密码标题：</td>
				<td class="content">
					<s:textfield name="messageTemplatePassword.forgotpassword.subject" id="subjectp" cssClass="input " />
					<s:fielderror fieldName="messageTemplatePassword.forgotpassword.subject" theme="ccweb"/>
				</td>
			</tr>
			<tr>
				<td class="label">找回密码内容：</td>
				<td class="content">
					<s:textarea name="messageTemplatePassword.forgotpassword.text" id="textp" cssClass="input txtArea" />
					<s:fielderror fieldName="messageTemplatePassword.forgotpassword.text" theme="ccweb"/>
					<span class="help">用户ID：\${uid}，用户名：\${username}，重置KEY：\${resetKey}，重置密码：\${resetPwd}</span>
				</td>
			</tr>
			<tr>
				<td class="label">会员注册标题：</td>
				<td class="content">
					<s:textfield name="messageTemplateRegister.register.subject" id="subjectr" cssClass="input " />
					<s:fielderror fieldName="messageTemplateRegister.register.subject" theme="ccweb"/>
				</td>
			</tr>
			<tr>
				<td class="label">会员注册内容：</td>
				<td class="content">
					<s:textarea name="messageTemplateRegister.register.text" id="textr" cssClass="input txtArea" />
					<s:fielderror fieldName="messageTemplateRegister.register.text" theme="ccweb"/>
					<span class="help">用户名：\${username}，激活码：\${activationCode}</span>
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


<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/inc/path.inc"%>
<%@include file="/inc/taglib.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<%@ include file="/inc/manage.inc"%>
  </head>
  
  <body>
  	<div class="navi"><span class="navi_b">当前位置:</span><span class="navi_h">修改登录密码</span></div>
	<div class="mainData">
	<div class="actInfo"><s:actionerror theme="ccweb"/><s:actionmessage theme="ccweb"/></div> 
	<s:form action="index_pwdupdate_pwdedit" cssClass="vldform">
		<table width="100%" class="table_edit">
			<tr>
				<td class="label">请输入原始密码：</td>
				<td class="content"><s:password name="opassword" id="opassword" cssClass="input required" showPassword="true" />
					<s:fielderror fieldName="opassword" theme="ccweb"/>
					<span class="red">*</span>
				</td>
			</tr>
			<tr>
				<td class="label">请输入新密码：</td>
				<td class="content"><s:password name="password" id="password" cssClass="input required" showPassword="true" />
				<s:fielderror fieldName="password" theme="ccweb"/>
				<span class="red">*</span></td>
			</tr>
			<tr>
				<td class="label">请确认新密码：</td>
				<td class="content"><s:password name="confirm_password" id="confirm_password" cssClass="input required" equalTo="#password" showPassword="true" />
				<s:fielderror fieldName="confirm_password" theme="ccweb"/>
				<span class="red">*</span></td>
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


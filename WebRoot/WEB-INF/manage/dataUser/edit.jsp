<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/inc/path.inc"%>
<%@include file="/inc/taglib.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<%@ include file="/inc/manage.inc"%>
  </head>
  
  <body>
  	<div class="navi"><span class="navi_b">当前位置:</span><span class="navi_h">管理 - 修改</span></div>
	<div class="mainData">
	<div class="actInfo"><s:actionerror theme="ccweb"/><s:actionmessage theme="ccweb"/></div> 
	<s:form action="_update" cssClass="vldform">
		<s:hidden name=".id" />
		<s:hidden name="pageNo"/>		
		<s:hidden name="username" />
		<table width="100%" class="table_edit">
			<tr>
				<td class="label">用户名：</td>
				<td class="content">
					<s:textfield name="admin.user.userName" id="username" cssClass="input required focus" />
					<s:fielderror fieldName="admin.user.userName" theme="ccweb"/>
					<span class="red">*</span>
					<input id="btnCheckUsername" class="btn_check" type="button" onclick="ajaxCheck(this,'用户名','user_checkUsername','username')" value="检测用户名"/>
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


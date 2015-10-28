<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/inc/path.inc"%>
<%@include file="/inc/taglib.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<%@ include file="/inc/manage.inc"%>
  </head>
  
  <body>
  	<div class="navi"><span class="navi_b">当前位置:</span><span class="navi_h">全局参数设置</span></div>
	<div class="mainData">
	<div class="actInfo"><s:actionerror theme="ccweb"/><s:actionmessage theme="ccweb"/></div> 
	<s:form action="global_update_edit" cssClass="vldform">
		<s:hidden name="global.id"/>
		<table width="100%" class="table_edit">
			<tr>
				<td class="label">网站部署路径：</td>
				<td class="content">
					<s:textfield name="global.contentPath" id="contentPath" cssClass="input" />
					<s:fielderror fieldName="global.contentPath" theme="ccweb"/>
					<span class="help">网站在服务器上的部署路径</span>
				</td>
			</tr>
			<tr>
				<td class="label">网站端口：</td>
				<td class="content">
					<s:textfield name="global.port" id="port" cssClass="input" />
					<s:fielderror fieldName="global.port" theme="ccweb"/>
					<span class="red">*</span>
					<span class="help">网站部署服务器的端口号，默认是80</span>
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


<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/inc/path.inc"%>
<%@include file="/inc/taglib.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<%@ include file="/inc/manage.inc"%>
  </head>
  <body>
  	<div class="navi"><span class="navi_b">当前位置:</span><span class="navi_h"><a href="#">模板管理 - 添加模板文件</a></span></div>
	<div class="mainData">
	<div class="actInfo">
		<s:actionerror theme="ccweb"/><s:actionmessage theme="ccweb"/>
	</div>
	<s:form action="template_filesave_fileadd.jspa">
		<s:hidden name="uploadRuleId"/>
		<s:hidden name="relPath" id="relPath"/>
		<table width="100%" class="table_edit">
			<tr>
				<td class="label">文件名称：</td>
				<td class="content">
					<s:textfield name="tplName" id="tplName" cssClass="input" />
					<span class="help">文件全称包括后缀，系统默认的模板文件类型是.html</span>
				</td>
			</tr>
			<tr>
				<td class="content" colspan="2">
					<s:textarea name="tplContent" cssClass="input txtArea" cssStyle="width:99%;height:400px;"></s:textarea>
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

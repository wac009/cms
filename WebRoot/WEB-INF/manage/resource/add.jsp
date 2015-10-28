<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/inc/path.inc"%>
<%@include file="/inc/taglib.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<%@ include file="/inc/manage.inc"%>
  </head>
  <body>
  	<div class="navi"><span class="navi_b">当前位置:</span><span class="navi_h">资源管理 - 添加资源文件</span></div>
	<div class="mainData">
	<div class="actInfo">
		<s:actionerror theme="ccweb"/><s:actionmessage theme="ccweb"/>
	</div>
	<s:form action="resource_save_add.jspa">
		<s:hidden name="uploadRuleId"/>
		<s:hidden name="relPath" id="relPath"/>
		<table width="100%" class="table_edit">
			<tr>
				<td class="label">文件名称：</td>
				<td class="content">
					<s:textfield name="resName" id="resName" cssClass="input" />
					<span class="help">文件全称包括后缀</span>
				</td>
			</tr>
			<tr>
				<td class="content" colspan="2">
					<s:textarea name="resContent" cssClass="input txtArea" cssStyle="width:99%;height:400px;"></s:textarea>
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

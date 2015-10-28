<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/inc/path.inc"%>
<%@include file="/inc/taglib.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<%@ include file="/inc/manage.inc"%>
  </head>
  
  <body>
  	<div class="navi"><span class="navi_b">当前位置:</span><span class="navi_h">模板管理 - 导出模板</span></div>
	<div class="mainData">
	<s:form action="template_exportTplSubmit" cssClass="vldform">
		<table width="100%" class="table_edit">
			<tr>
				<td class="label">请选择要导入的模板ZIP包：</td>
				<td class="content">
					<span id="tfc"><input type="file" id="tplFile" name="tplsFile" size="30" class="input required" style="width:260px;"/></span>
					<span class="red">*</span>
					<span class="help red">必须使用ZIP格式的压缩包</span>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<div id="btn" class="btnDiv_fa">
						<input type="submit" value=" 导 出 " class="btn_fa"/>
						<input type="reset" value=" 重 置 " class="btn_fa">
					</div>
				</td>
			</tr>
		</table>
	</s:form>
	</div>
  </body>
</html>

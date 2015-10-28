<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/inc/path.inc"%>
<%@include file="/inc/taglib.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<%@ include file="/inc/manage.inc"%>
  </head>
  
  <body>
  	<div class="navi">
  		<span class="navi_b">当前位置:</span><span class="navi_h">关键词管理 - 修改</span>
  		<span class="navi_f">
  			<input class="del btn" type="button" value="提交" onclick="$('#dataForm').submit();"/>
  			<a href='javascript:history.go(-1)' style="padding:0 5px;">返回</a>
  		</span>
  	</div>
	<div class="mainData">
	<div class="actInfo"><s:actionerror theme="ccweb"/><s:actionmessage theme="ccweb"/></div> 
	<s:form action="keyword_update_edit" cssClass="vldform" id="dataForm">
		<s:hidden name="keyword.id" />
		<s:hidden name="pageNo"/>		
		<table width="100%" class="table_edit">
			<tr>
				<td class="label">关键词：</td>
				<td class="content">
					<s:textfield name="keyword.name" id="keyword.name" cssClass="input required focus" />
					<span class="red">*</span>
				</td>
			</tr>
			<tr>
				<td class="label">替换内容：</td>
				<td class="content">
					<s:textfield name="keyword.url" cssClass="input txtArea" />
				</td>
			</tr>
			<tr>
				<td class="label">状态设置：</td>
				<td class="content">
					<s:radio list="#{'0':'启用','1':'禁用'}" listKey="key" listValue="value" name="keyword.disabled" value="0"></s:radio>
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


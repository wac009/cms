<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/inc/path.inc"%>
<%@include file="/inc/taglib.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<%@ include file="/inc/manage.inc"%>
  </head>
  
  <body>
  	<div class="navi"><span class="navi_b">当前位置:</span><span class="navi_h">杂志类型管理 - 添加杂志类型</span>
  		<span class="navi_f">
  			<a href='javascript:history.go(-1)' style="padding:0 5px;">返回</a>
  		</span>
  	</div>
	<div class="mainData">
	<div class="actInfo"><s:actionerror theme="ccweb"/><s:actionmessage theme="ccweb"/></div>
	<s:form action="publicationType_save_add" cssClass="vldform">
		<s:hidden name="type.website.id" value="%{webId}"/>
		<table width="100%" class="table_edit">
			<tr>
				<td class="label">名称：</td>
				<td class="content">
					<s:textfield name="type.name" cssClass="input required focus" />
					<s:fielderror fieldName="type.name" theme="ccweb"/>
					<span class="red">*</span>
				</td>
			</tr>
			<tr>
				<td class="label">描述：</td>
				<td class="content">
					<s:textarea name="type.description" cssClass="input txtArea" />
				</td>
			</tr>
			<tr>
				<td class="label">属性设置：</td>
				<td class="content">
					<s:checkbox name="type.disabled" id="type_isdisabled"/><label for="type_isdisabled">禁用</label>
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

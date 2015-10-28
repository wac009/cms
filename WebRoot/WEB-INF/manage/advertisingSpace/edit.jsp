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
  		<span class="navi_b">当前位置:</span><span class="navi_h">广告位管理 - 广告位修改</span>
  		<span class="navi_f">
  			<input class="del btn" type="button" value="提交" onclick="$('#dataForm').submit();"/>
  			<a href='javascript:history.go(-1)' style="padding:0 5px;">返回</a>
  		</span>
  	</div>
	<div class="mainData">
	<div class="actInfo"><s:actionerror theme="ccweb"/><s:actionmessage theme="ccweb"/></div> 
	<s:form action="advertisingSpace_update_edit" cssClass="vldform" id="dataForm">
		<s:hidden name="advertisingSpace.id" />
		<s:hidden name="pageNo"/>		
		<table width="100%" class="table_edit">
			<tr>
				<td class="label">名称：</td>
				<td class="content">
					<s:textfield name="advertisingSpace.name" id="name" cssClass="input required focus" />
					<s:fielderror fieldName="advertisingSpace.name" theme="ccweb"/>
					<span class="red">*</span>
				</td>
			</tr>
			<tr>
				<td class="label">价格：</td>
				<td class="content">
					<s:textfield name="advertisingSpace.price" id="name" cssClass="input " />
					<s:fielderror fieldName="advertisingSpace.price" theme="ccweb"/>
				</td>
			</tr>
			<tr>
				<td class="label">描述：</td>
				<td class="content">
					<s:textarea name="advertisingSpace.description" cssClass="input txtArea" />
					<s:fielderror fieldName="" theme="ccweb"/>
				</td>
			</tr>
			<tr>
				<td class="label">状态设置：</td>
				<td class="content">
					<s:radio list="#{'true':'启用','false':'禁用'}" listKey="key" listValue="value" name="advertisingSpace.enabled" value="advertisingSpace.enabled"></s:radio>
					显示方式：<s:select cssStyle="width:100px;border:1px solid #CCC;" name="advertisingSpace.displayType" list="#{'1':'随机显示','2':'全部列出'}" />
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


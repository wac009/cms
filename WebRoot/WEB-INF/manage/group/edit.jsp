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
  		<span class="navi_b">当前位置:</span><span class="navi_h">会员组管理 - 修改</span>
  		<span class="navi_f">
  			<input class="del btn" type="button" value="提交" onclick="$('#dataForm').submit();"/>
  			<a href='javascript:history.go(-1)' style="padding:0 5px;">返回</a>
  		</span>
  	</div>
	<div class="mainData">
	<div class="actInfo"><s:actionerror theme="ccweb"/><s:actionmessage theme="ccweb"/></div> 
	<s:form action="group_update_edit" cssClass="vldform" id="dataForm">
		<s:hidden name="group.id" />
		<table width="100%" class="table_edit">
			<tr>
				<td class="label">名称：</td>
				<td class="content">
					<s:textfield name="group.name" id="name" cssClass="input required focus" />
					<span class="red">*</span>
				</td>
			</tr>
			<tr>
				<td class="label">每日附件大小：</td>
				<td class="content">
					<s:textfield name="group.allowPerDay" id="allowPerDay" cssClass="input required" value="0"/>
					<span class="red">*</span>
					<span class="help"> 0为不限制，单位(KB)</span>
				</td>
			</tr>
			<tr>
				<td class="label">总附件大小：</td>
				<td class="content">
					<s:textfield name="group.allowMaxFile" id="allowMaxFile" cssClass="input required" value="0"/>
					<span class="red">*</span>
					<span class="help"> 0为不限制，单位(KB)</span>
				</td>
			</tr>
			<tr>
				<td class="label">允许上传的后缀：</td>
				<td class="content">
					<s:textfield name="group.allowSuffix" id="allowSuffix" cssClass="input" style="width:500px;"/>
					<span class="help">留空则不限制，多个用","分开</span>
				</td>
			</tr>
			<tr>
				<td class="label">属性设置：</td>
				<td class="content">
					默认组：<s:radio list="#{'true':'是','false':'否'}" listKey="key" listValue="value" name="group.regDef" ></s:radio>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					评论需要审核：<s:radio list="#{'true':'需要','false':'不需要'}" listKey="key" listValue="value" name="group.needCheck" ></s:radio>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					评论需要验证码：<s:radio list="#{'true':'需要','false':'不需要'}" listKey="key" listValue="value" name="group.needCaptcha" ></s:radio>
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


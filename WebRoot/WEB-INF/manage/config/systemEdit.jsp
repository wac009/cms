<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/inc/path.inc"%>
<%@include file="/inc/taglib.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<%@ include file="/inc/manage.inc"%>
  </head>
  
  <body>
  	<div class="navi"><span class="navi_b">当前位置:</span><span class="navi_h">配置管理 - 系统配置修改</span></div>
	<div class="mainData">
	<div class="actInfo"><s:actionerror theme="ccweb"/><s:actionmessage theme="ccweb"/></div> 
	<s:form action="config_systemUpdate_systemEdit" cssClass="vldform">
		<s:hidden name="config.id" />
		<table width="100%" class="table_edit">
			<tr>
				<td class="label">部署路径：</td>
				<td class="content">
					<s:textfield name="config.contextPath" id="contextPath" cssClass="input focus" />
					<s:fielderror fieldName="config.contextPath" theme="ccweb"/>
					<span class="help">部署在根目录请留空</span>
				</td>
			</tr>
			<tr>
				<td class="label">端口号：</td>
				<td class="content">
					<s:textfield name="config.port" id="port" cssClass="input focus" />
					<s:fielderror fieldName="config.port" theme="ccweb"/>
					<span class="help">默认留空</span>
				</td>
			</tr>
			<tr>
				<td class="label">默认图片：</td>
				<td class="content">
					<s:textfield name="config.defImg" id="defImg" cssClass="input focus" />
					<s:fielderror fieldName="config.defImg" theme="ccweb"/>
					<span class="red">*</span>
					<span class="help">图片不存在时显示</span>
				</td>
			</tr>
			<tr>
				<td class="label">数据库附件：</td>
				<td class="content">
					<s:radio name="config.uploadToDb" list="#{'false':'不关闭 ','true':'关闭'}" listKey="key" listValue="value" />
					<s:fielderror fieldName="config.uploadToDb" theme="ccweb"/>
					<span class="red">*</span>&nbsp;&nbsp;&nbsp;
					附件地址
					<s:textfield name="config.dbFileUri" id="dbFileUri" cssClass="input focus" />
					<s:fielderror fieldName="config.dbFileUri" theme="ccweb"/>
					<span class="red">*</span>
					<span class="help">一般无需改动</span>
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


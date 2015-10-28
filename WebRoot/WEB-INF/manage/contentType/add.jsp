<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/inc/path.inc"%>
<%@include file="/inc/taglib.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<%@ include file="/inc/manage.inc"%>
  </head>
  
  <body>
  	<div class="navi"><span class="navi_b">当前位置:</span><span class="navi_h"><a href="#">内容类型管理 - 类型添加</a></span></div>
	<div class="mainData">
	<div class="actInfo"><s:actionerror theme="ccweb"/><s:actionmessage theme="ccweb"/></div>
	<s:form action="contentType_save_add" cssClass="vldform">
		<table width="100%" class="table_edit">
			<tr>
				<td class="label">名称：</td>
				<td class="content">
					<s:textfield name="contentType.name" id="name" cssClass="input required focus" />
					<s:fielderror fieldName="contentType.name" theme="ccweb"/>
					<span class="red">*</span>
				</td>
			</tr>
			<tr>
				<td class="label">图片设置：</td>
				<td class="content">
					有图片<s:checkbox name="contentType.hasImage" onclick="toggleByClass('imgW');toggleByClass('imgH');" />
					<span class="imgW hidden">图片宽<s:textfield name="contentType.imgWidth" cssClass="input " cssStyle="width:60px;"/></span>
					<span class="imgH hidden">图片高<s:textfield name="contentType.imgHeight" cssClass="input " cssStyle="width:60px;"/></span>					
				</td>
			</tr>
			<tr>
				<td class="label">附件设置：</td>
				<td class="content">
					有附件<s:checkbox name="contentType.hasAttachment"  />
				</td>
			</tr>
			<tr>
				<td class="label">多媒体设置：</td>
				<td class="content">
					有多媒体<s:checkbox name="contentType.hasMedia"  />
				</td>
			</tr>
			<tr>
				<td class="label">状态：</td>
				<td class="content">
					<s:checkbox name="contentType.isDisabled" id="contentType_isdisabled"/><label for="contentType_isdisabled">禁用</label>
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

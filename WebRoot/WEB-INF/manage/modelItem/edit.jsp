<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/inc/path.inc"%>
<%@include file="/inc/taglib.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<%@ include file="/inc/manage.inc"%>
  </head>
  
  <body>
  	<div class="navi"><span class="navi_b">当前位置:</span><span class="navi_h">管理 - 修改</span></div>
	<div class="mainData">
	<div class="actInfo"><s:actionerror theme="ccweb"/><s:actionmessage theme="ccweb"/></div> 
	<s:form action="model_update" cssClass="vldform">
		<s:hidden name="model.id" />
		<table width="100%" class="table_edit">
			<tr>
				<td class="label">模型名称：</td>
				<td class="content">
					<s:textfield name="model.name" id="name" cssClass="input required focus" />
					<span class="red">*</span>
				</td>
			</tr>
			<tr>
				<td class="label">模型路径：</td>
				<td class="content">
					<s:textfield name="model.path" id="path" cssClass="input required" />
					<span class="red">*</span>
				</td>
			</tr>
			<tr>
				<td class="label">栏目模板前缀：</td>
				<td class="content">
					<s:textfield name="model.tplChannelPrefix" id="tplChannelPrefix" cssClass="input required" />
					<span class="red">*</span>
				</td>
			</tr>
			<tr>
				<td class="label">内容模板前缀：</td>
				<td class="content">
					<s:textfield name="model.tplContentPrefix" id="tplContentPrefix" cssClass="input required" />
					<span class="red">*</span>
				</td>
			</tr>
			<tr>
				<td class="label">栏目标题图：</td>
				<td class="content">
					宽<s:textfield name="model.titleImgWidth" id="titleImgWidth" cssClass="input" value="139"  style="width:70px"/>px
					高<s:textfield name="model.titleImgHeight" id="titleImgHeight" cssClass="input" value="139"  style="width:70px"/>px
				</td>
			</tr>
			<tr>
				<td class="label">栏目内容图：</td>
				<td class="content">
					宽<s:textfield name="model.contentImgWidth" id="contentImgWidth" cssClass="input"  value="310" style="width:70px"/>px
					高<s:textfield name="model.contentImgHeight" id="contentImgHeight" cssClass="input"  value="310" style="width:70px"/>px
				</td>
			</tr>
			<tr>
				<td class="label">是否有内容：</td>
				<td class="content">
					<s:radio name="model.hasContent"  list="#{'true':'有','false':'无 '}" listKey="key" listValue="value" />
				</td>
			</tr>
			<tr>
				<td class="label">是否禁用：</td>
				<td class="content">
					<s:radio name="model.disabled"  list="#{'true':'是','false':'否 '}" listKey="key" listValue="value" />
				</td>
			</tr>
			<tr>
				<td class="label">是否默认：</td>
				<td class="content">
					<s:radio name="model.def"  list="#{'true':'是','false':'否 '}" listKey="key" listValue="value" />
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


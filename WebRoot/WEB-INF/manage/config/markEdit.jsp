<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/inc/path.inc"%>
<%@include file="/inc/taglib.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
 <head>
<%@ include file="/inc/manage.inc"%>
<script type="text/javascript">
$(function() {
	$("#color").colorPicker();
});
</script>
</head>
  
  <body>
  	<div class="navi"><span class="navi_b">当前位置:</span><span class="navi_h">配置管理 - 水印配置</span></div>
	<div class="mainData">
	<div class="actInfo"><s:actionerror theme="ccweb"/><s:actionmessage theme="ccweb"/></div> 
	<s:form action="config_markUpdate_markEdit" cssClass="vldform">
		<s:hidden name="config.id" />
		<table width="100%" class="table_edit">
			<tr>
				<td class="label">开启水印：</td>
				<td class="content">
					<s:radio name="markConfig.on"  list="#{'true':'是','false':'否 '}" listKey="key" listValue="value" />
				</td>
			</tr>
			<tr>
				<td class="label">图片尺寸控制：</td>
				<td class="content">
					宽<s:textfield name="markConfig.minWidth" id="minWidth" cssClass="input required" style="width:70px"/>px
					高<s:textfield name="markConfig.minHeight" id="minHeight" cssClass="input required" style="width:70px"/>px
					<span class="help">小于该尺寸的图片不添加水印</span>
				</td>
			</tr>
			<tr>
				<td class="label">水印图片：</td>
				<td class="content">
					<s:textfield name="markConfig.imagePath" id="imagePath" cssClass="input required" />
					<img id="markPreview" style="border:1px solid #ccc; vertical-align:middle" alt="" src="<%=basePath %><s:property value="markConfig.imagePath" />"/>
					<span class="help">留空则使用文字水印</span>
				</td>
			</tr>
			<tr>
				<td class="label">水印文字：</td>
				<td class="content">
					<s:textfield name="markConfig.content" id="content" cssClass="input required" />
					<span class="help">使用中文有可能出现乱码</span>
				</td>
			</tr>
			<tr>
				<td class="label">水印文字属性：</td>
				<td class="content">
					字体大小<s:textfield name="markConfig.size" id="size" cssClass="input required" style="width:70px"/>
					颜色<s:textfield name="markConfig.color" id="color" cssClass="input required" style="width:70px"/>
					透明度<s:textfield name="markConfig.alpha" id="alpha" cssClass="input required" style="width:70px"/>0-100，越小越透明 
				</td>
			</tr>
			<tr>
				<td class="label">位置：</td>
				<td class="content">
					<s:select name="markConfig.pos" list="#{'0':'随机','1':'左上','2':'右上','3':'左下','4':'右下','5':'居中'}"></s:select>
					水平偏移量<s:textfield name="markConfig.offsetX" id="offsetX" cssClass="input required" style="width:70px"/>
					垂直偏移量<s:textfield name="markConfig.offsetY" id="offsetY" cssClass="input required" style="width:70px"/>
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


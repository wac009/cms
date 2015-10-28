<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/inc/path.inc"%>
<%@include file="/inc/taglib.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<%@ include file="/inc/manage.inc"%>
  </head>
  
  <body>
  	<div class="navi"><span class="navi_b">当前位置:</span><span class="navi_h">广告管理 - 添加广告</span>
  		<span class="navi_f">
  			<input class="del btn" type="button" value="提交" onclick="$('#dataForm').submit();"/>
  			<a href='javascript:history.go(-1)' style="padding:0 5px;">返回</a>
  		</span>
  	</div>
	<div class="mainData">
	<jsp:include page="../common/imgUpload.jsp" />
	<jsp:include page="../common/flashUpload.jsp" />
	<div class="actInfo"><s:actionerror theme="ccweb"/><s:actionmessage theme="ccweb"/></div>
	<s:form action="advertising_save_add" cssClass="vldform" id="dataForm">
		<table width="100%" class="table_edit">
			<tr>
				<td class="label">名称：</td>
				<td class="content">
					<s:textfield name="advertising.name" id="name" cssClass="input required focus" />
					<s:fielderror fieldName="advertising.name" theme="ccweb"/>
					<span class="red">*</span>
				</td>
			</tr>
			<tr>
				<td class="label">版位：</td>
				<td class="content">
					<s:select name="advertising.adspace.id" list="spaces" listKey="id" listValue="name" cssClass="select" ></s:select>
					<span class="red">*</span>
				</td>
			</tr>
			<tr>
				<td class="label">类型：</td>
				<td class="content">
					<label><input type="radio" value="image" checked="checked" name="advertising.category" onclick="attrChange(this.value)"/>图片</label> 
					<label><input type="radio" value="flash" name="advertising.category" onclick="attrChange(this.value)"/>FLASH</label> 
					<label><input type="radio" value="text" name="advertising.category" onclick="attrChange(this.value)"/>文字</label> 
					<label><input type="radio" value="code" name="advertising.category" onclick="attrChange(this.value)"/>代码</label>
					<span class="red">*</span>
				</td>
			</tr>
			<tr>
				<td class="label">广告内容：</td>
				<td class="content">
					<div id="attr_image">
						<p class="attr">图片地址: <s:textfield id="uploadImgPath1" name="attr_image_url" cssClass="input" maxlength="255" style="width:320px;"/></p>
						<p class="attr">图片上传: <span id="ifc1" style="position:relative"><input type="file" id="imgFile1" class="input"  style="width:270px;"/></span>
							<input class="btn" type="button" value="上传" onclick="uploadImg(1);" style="height:24px;"/>
						</p>
						<p class="attr">图片尺寸: <input type="text" name="attr_image_width" maxlength="50" class="input"  style="width:70px;"/> * <input type="text" name="attr_image_height" maxlength="50" class="input"  style="width:70px;"/> <span class="pn-fhelp">（宽×高）</span></p>
						<p class="attr">链接地址: <input type="text" name="attr_image_link" value="http://" maxlength="255" class="input"  style="width:300px;"/></p>
						<p class="attr">链接提示: <input type="text" name="attr_image_title" maxlength="255" class="input"  style="width:300px;"/></p>
						<p class="attr">链接目标: <label><input type="radio" value="_blank" checked="checked" name="attr_image_target"/>新窗口</label> <label><input type="radio" value="_self" name="attr_image_target"/>原窗口</label></p>
					</div>
					<div id="attr_flash" style="display:none;">
						<p class="attr">FLASH地址:<input type="text" id="flashPath1" name="attr_flash_url" maxlength="255" class="input"  style="width:320px;"/></p>
						<p class="attr">FLASH上传:<span id="ffc1" style="position:relative"><input type="file" id="flashFile1" class="input" style="width:270px;"/></span>
							<input class="btn" type="button" value="上传" onclick="uploadFlash(1);" style="height:24px;"/>
						</p>
						<p class="attr">FLASH尺寸: <input type="text" name="attr_flash_width" maxlength="50" class="input"  style="width:70px;"/> * <input type="text" name="attr_flash_height" maxlength="50" class="input"  style="width:70px;"/> <span class="pn-fhelp">（宽×高）</span></p>
					</div>
					<div id="attr_text" style="display:none;">
						<p class="attr">文字内容: <input type="text" name="attr_text_title" class="input" maxlength="255" style="width:300px;"/></p>
						<p class="attr">文字链接: <input type="text" name="attr_text_link" value="http://" maxlength="255"  class="input" style="width:300px;"/></p>
						<p class="attr">文字颜色: <input type="text" id="textColor" name="attr_text_color" maxlength="50"  class="input" style="width:70px"/></p>
						<p class="attr">文字大小: <input type="text" name="attr_text_font" maxlength="50"  class="input" style="width:70px;"/> <span class="pn-fhelp">如：12px</span></p>
						<p class="attr">链接目标: <label><input type="radio" value="_blank" checked="checked" name="attr_text_target"/>新窗口</label> <label><input type="radio" value="_self" name="attr_text_target"/>原窗口</label>
					</p>
					</div>
					<div id="attr_code" style="display:none;">
						<textarea name="advertising.code" rows="7" cols="60" maxlength="655350"  class="input txtArea"></textarea>
					</div>
				</td>
			</tr>
			<tr>
				<td class="label">时间：</td>
				<td class="content">
					<s:textfield name="startTime" id="startTime" cssClass="input Wdate" cssStyle="padding:2px 2px 1px 2px;"  onclick="WdatePicker({isShowWeek:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
					至
					<s:textfield name="endTime" id="endTime" cssClass="input Wdate" cssStyle="padding:2px 2px 1px 2px;"  onclick="WdatePicker({isShowWeek:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
				</td>
			</tr>
			<tr>
				<td class="label">点击次数：</td>
				<td class="content">
					<s:textfield name="advertising.clickCount" id="advertising.clickCount" cssClass="input required"  cssStyle="width:100px;"/>
					<span class="red">*</span>
					展现次数：
					<s:textfield name="advertising.displayCount" id="advertising.displayCount" cssClass="input required"  cssStyle="width:100px;"/>
					<span class="red">*</span>
				</td>
			</tr>
			<tr>
				<td class="label">权重：</td>
				<td class="content">
					<s:textfield name="advertising.weight" id="advertising.weight" cssClass="input required"  cssStyle="width:100px;"/>
					<span class="red">*</span>
					<s:radio list="#{'true':'启用','false':'禁用'}" listKey="key" listValue="value" name="advertising.enabled" value="1"></s:radio>
				</td>
			</tr>
			<tr>
				<td class="label">客户：</td>
				<td class="content">
					<s:textfield name="advertising.customer" id="advertising.customer" cssClass="input" />
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
<script type="text/javascript">
$(function() {
	attrChange("image");
	$("#textColor").colorPicker();
});
var attr_all=["image","flash","text","code"];
function attrChange(value) {
	for(var attr in attr_all) {
		if(attr_all[attr]==value) {
			showAttr(attr_all[attr]);
		} else {
			hideAttr(attr_all[attr]);
		}
	}
}
function hideAttr(value) {
	var name = "#attr_"+value;
	$(name).hide();
	$(name+" input,"+name+" select,"+name+" textarea").each(function() {
		$(this).attr("disabled","disabled");
	});
}
function showAttr(value) {
	var name = "#attr_"+value;
	$("#attr_"+value).show();
	$(name+" input,"+name+" select,"+name+" textarea").each(function() {
		$(this).removeAttr("disabled");
	});
}
</script>
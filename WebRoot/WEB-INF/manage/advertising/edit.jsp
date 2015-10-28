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
  		<span class="navi_b">当前位置:</span><span class="navi_h">广告管理 - 修改广告</span>
  		<span class="navi_f">
  			<input class="del btn" type="button" value="提交" onclick="$('#dataForm').submit();"/>
  			<a href='javascript:history.go(-1)' style="padding:0 5px;">返回</a>
  		</span>
  	</div>
	<div class="mainData">
	<jsp:include page="../common/imgUpload.jsp" />
	<jsp:include page="../common/flashUpload.jsp" />
	<div class="actInfo"><s:actionerror theme="ccweb"/><s:actionmessage theme="ccweb"/></div> 
	<s:form action="advertising_update_edit" cssClass="vldform" id="dataForm">
		<s:hidden name="advertising.id" />
		<s:hidden name="pageNo"/>		
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
					<s:radio list="#{'image':'图片','flash':'Flash','text':'文字','code':'代码' }" listKey="key" listValue="value" name="advertising.category" value="%{advertising.category}"  onclick="attrChange(this.value)"></s:radio>
					<span class="red">*</span>
				</td>
			</tr>
			<tr>
				<td class="label">广告内容：</td>
				<td class="content">
					<div id="attr_image">
						<p class="attr">图片地址: <s:textfield id="uploadImgPath1" name="attr_image_url"  value="%{attr.image_url}" cssClass="input" maxlength="255" style="width:320px;"/></p>
						<p class="attr">图片上传: <span id="ifc1" style="position:relative"><input type="file" id="imgFile1" class="input"  style="width:270px;"/></span>
							<input class="btn" type="button" value="上传" onclick="uploadImg(1);" style="height:24px;"/>
						</p>
						<p class="attr">图片尺寸: <s:textfield name="attr_image_width" value="%{attr.image_width}"  maxlength="50" cssClass="input"  style="width:70px;"/> * <s:textfield name="attr_image_height"  value="%{attr.image_height}" maxlength="50" cssClass="input"  style="width:70px;"/> <span class="pn-fhelp">（宽×高）</span></p>
						<p class="attr">链接地址: <s:textfield name="attr_image_link" value="%{attr.image_link}"  maxlength="255" cssClass="input"  style="width:300px;"/></p>
						<p class="attr">链接提示: <s:textfield name="attr_image_title" value="%{attr.image_title}"  maxlength="255" cssClass="input"  style="width:300px;"/></p>
						<p class="attr">链接目标: <s:radio list="#{'_blank':'新窗口', '_self':'原窗口'}" listKey="key" listValue="value" name="attr_image_target"  value="%{attr.image_target}"></s:radio></p>
					</div>
					<div id="attr_flash" style="display:none;">
						<p class="attr">FLASH地址:<s:textfield id="flashPath1" name="attr_flash_url"  value="%{attr.flash_url}" maxlength="255" cssClass="input"  style="width:320px;"/></p>
						<p class="attr">FLASH上传:<span id="ffc1" style="position:relative"><input type="file" id="flashFile1" class="input" style="width:270px;"/></span>
							<input class="btn" type="button" value="上传" onclick="uploadFlash(1);" style="height:24px;"/>
						</p>
						<p class="attr">FLASH尺寸: <s:textfield name="attr_flash_width"  value="%{attr.flash_width}" maxlength="50" cssClass="input"  style="width:70px;"/> * <s:textfield name="attr_flash_height"  value="%{attr.flash_height}" maxlength="50" cssClass="input"  style="width:70px;"/> <span class="pn-fhelp">（宽×高）</span></p>
					</div>
					<div id="attr_text" style="display:none;">
						<p class="attr">文字内容: <s:textfield name="attr_text_title"  value="%{attr.text_title}" cssClass="input" maxlength="255" style="width:300px;"/></p>
						<p class="attr">文字链接: <s:textfield name="attr_text_link"  value="%{attr.text_link}" maxlength="255"  cssClass="input" style="width:300px;"/></p>
						<p class="attr">文字颜色: <s:textfield id="textColor" name="attr_text_color"  value="%{attr.text_color}" maxlength="50"  cssClass="input" style="width:70px"/></p>
						<p class="attr">文字大小: <s:textfield name="attr_text_font"  value="%{attr.text_font}" maxlength="50"  cssClass="input" style="width:70px;"/> <span class="pn-fhelp">如：12px</span></p>
						<p class="attr">链接目标: <s:radio list="#{'_blank':'新窗口', '_self':'原窗口'}" listKey="key" listValue="value"  name="attr_text_target"  value="%{attr.text_target}"></s:radio></p>
					</p>
					</div>
					<div id="attr_code" style="display:none;">
						<s:textarea name="advertising.code" rows="7" cols="60" maxlength="655350"  cssClass="input txtArea"></s:textarea>
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
	attrChange("${advertising.category}");
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

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/inc/taglib.inc"%>
<script type="text/javascript">
//上传图片
function uploadImg(n) {
	var of = $('#imgFile'+n);
	//检查是否选择了图片
	if(of.val()=='') {
		alert('请选择要上传的图片');
		return;
	}
	//将file移动至上传表单
	$('#imgContent').empty();
	$('#imgContent').append(of);
	//复制一个file放至原处
	$('#ifc'+n).append(of.clone());
	//修改属性
	of.attr('id','');
	of.attr('name','uploadFile');
	//其他表单
	if($('#zoom'+n).attr('checked')) {
		$('#ufZoom').val('true');
	} else {
		$('#ufZoom').val('false');
	}
	$('#ufWidth').val($('#zoomWidth'+n).val());
	$('#ufHeight').val($('#zoomHeight'+n).val());
	$('#imgUploadNum').val(n);
	
	$('#imgForm').submit();
}
//剪裁图片
function imgCut(n) {
	if($('#uploadImgPath'+n).val()=='') {
		alert("请先上传图片，再剪裁");
		return;
	}
	var url = "fileUpload_imgAreaSelect_imgAreaSelect.jspa?imgSrcRoot=${web.resPath}&uploadNum="+n+"&imgSrcPath="
		+$("#uploadImgPath"+n).val()+"&zoomWidth="+$("#zoomWidth"+n).val()+"&zoomHeight="+$("#zoomHeight"+n).val();
	window.open(url,"imgcut","height=550, width=1000, top=0, left=0, toolbar=no, menubar=no, scrollbars=auto, resizable=yes,location=no, status=no");
}
//清除图片
function clearImg(n) {
	$('#uploadImgPath'+n).val("");
	$('#preImg'+n).attr("src","");
}
//预览图片
function previewImg(n) {
	var img = $("#uploadImgPath"+n).val();
	if(img!="") {
		if(img.indexOf("?")==-1) {
			$("#preImg"+n).attr("src",img+"?d="+new Date()*1);
		} else {
			$("#preImg"+n).attr("src",img+"&d="+new Date()*1);
		}
		if(!$("#preImg"+n).attr("noResize")) {
			$("#preImg"+n).css("width","auto");
			$("#preImg"+n).css("height","auto");
		}
	} else {
		$("#preImg"+n).removeAttr("src");
	}
}
</script>
<s:form id="imgForm" action="fileUpload_imgUpload_imgIframe" method="post" enctype="multipart/form-data" target="imgIframe" cssStyle="display:none;width:0px;height:0px;">
<span id="imgContent"></span>
<input id="ufZoom" type="hidden" name="zoom"/>
<input id="ufWidth" type="hidden" name="zoomWidth"/>
<input id="ufHeight" type="hidden" name="zoomHeight"/>
<input id="imgUploadNum" type="hidden" name="uploadNum"/>
<s:hidden name="uploadRuleId" id="iurid"/>
</s:form>
<iframe name="imgIframe" frameborder="0" border="0" style="display:none;width:0px;height:0px;"></iframe>




<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/inc/taglib.inc"%>
<script type="text/javascript">
//上传附件
function uploadMedia() {
	var mf = $("#mediaFile");
	//检查是否选择了文件
	if(mf.val()=="") {
		alert("请选择要上传的文件");
		return;
	}
	//将file移动至上传表单
	$("#mediaContent").empty();
	$("#mediaContent").append(mf);
	//复制一个file放至原处
	$("#mfc").append(mf.clone().attr('value',''));
	//修改属性
	mf.attr("id","");
	mf.attr('name','uploadFile');
	//其他表单
	$("#mediaFilename").val($("#origFilename").val());
	$("#mediaForm").submit();
}
</script>
<s:form id="mediaForm" action="fileUpload_mediaUpload_mediaIframe" method="post" enctype="multipart/form-data" target="media_iframe" cssStyle="display:none;width:0px;height:0px;">
<span id="mediaContent"></span>
<s:hidden name="uploadRuleId" id="murid"/>
</s:form>
<iframe name="media_iframe" frameborder="0" border="0" style="display:none;width:0px;height:0px;"></iframe>

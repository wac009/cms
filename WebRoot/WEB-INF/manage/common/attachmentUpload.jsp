<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/inc/taglib.inc"%>
<script type="text/javascript">
//上传附件
function uploadAttachment(n) {
	var af = $('#attachmentFile'+n);
	//检查是否选择了文件
	if(af.val()=='') {
		alert('请选择要上传的文件');
		return;
	}
	//将file移动至上传表单
	$('#attachmentContent').empty();
	$('#attachmentContent').append(af);
	//复制一个file放至原处
	$('#afc'+n).append(af.clone().attr('value',''));
	//修改属性
	af.attr('name','uploadFile');
	//其他表单
	$('#aUploadNum').val(n);
	$('#attachmentForm').submit();
}
</script>
<s:form id="attachmentForm" action="fileUpload_attachmentUpload_attachmentIframe" method="post" enctype="multipart/form-data" target="attachmentIframe" cssStyle="display:none;width:0px;height:0px;">
<span id="attachmentContent"></span>
<input id="aUploadNum" type="hidden" name="uploadNum"/>
<s:hidden name="uploadRuleId" id="aurid"/>
</s:form>
<iframe name="attachmentIframe" frameborder="0" border="0" style="display:none;width:0px;height:0px;"></iframe>

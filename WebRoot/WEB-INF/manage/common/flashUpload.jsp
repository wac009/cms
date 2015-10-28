<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/inc/taglib.inc"%>
<script type="text/javascript">
//上传附件
function uploadFlash(n) {
	var af = $('#flashFile'+n);
	//检查是否选择了文件
	if(af.val()=='') {
		alert('请选择要上传的文件');
		return;
	}
	//将file移动至上传表单
	$('#flashContent').empty();
	$('#flashContent').append(af);
	//复制一个file放至原处
	$('#ffc'+n).append(af.clone().attr('value',''));
	//修改属性
	af.attr("id","");
	af.attr('name','uploadFile');
	//其他表单
	$('#flashNum').val(n);
	$('#flashForm').submit();
}
</script>
<s:form id="flashForm" action="fileUpload_flashUpload_flashIframe" method="post" enctype="multipart/form-data" target="flash_iframe" style="display:none;width:0px;height:0px;">
<span id="flashContent"></span>
<s:hidden name="uploadRuleId" id="iurid"/>
</s:form>
<iframe name="flash_iframe" frameborder="0" border="0" style="display:none;width:0px;height:0px;"></iframe>
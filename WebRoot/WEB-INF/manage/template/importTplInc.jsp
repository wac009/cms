<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/inc/taglib.inc"%>
<script type="text/javascript">
//上传附件
function importTpl() {
	var tf = $('#tplFile');
	//检查是否选择了文件
	if(tf.val()=='') {
		alert('请选择要上传的文件');
		return;
	}
	//将file移动至上传表单
	$('#tplContent').empty();
	$('#tplContent').append(tf);
	//复制一个file放至原处
	$('#tfc').append(tf.clone().attr('value',''));
	//修改属性
	tf.attr('id','');
	tf.attr('name','tplsFile');
	//其他表单
	$('#tplImportForm').submit();
}
</script>
<s:form id="tplImportForm" action="template_importTplSubmit" method="post" enctype="multipart/form-data" target="tplImportIframe" style="display:none;width:0px;height:0px;">
	<span id="tplContent"></span>
	<s:hidden name="uploadRuleId" id="turid"/>
</s:form>
<iframe name="tplImportIframe" frameborder="0" border="0" style="display:none;width:0px;height:0px;"></iframe>

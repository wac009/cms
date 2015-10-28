<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/inc/path.inc"%>
<%@include file="/inc/taglib.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<%@ include file="/inc/manage.inc"%>
  </head>
  <body>
  	<div class="navi"><span class="navi_b">当前位置:</span><span class="navi_h">资源管理 - 上传资源文件</span></div>
	<div class="mainData">
	<div class="actInfo">
		<s:actionerror theme="ccweb"/><s:actionmessage theme="ccweb"/>
	</div>
	<s:form action="resource_uploadSubmit_list.jspa" enctype="multipart/form-data">
		<s:hidden name="relPath" id="relPath"/>
		<table width="100%" class="table_edit">
			<tr>
				<td class="label">当前目录：</td>
				<td class="content">
					${relPath}
				</td>
			</tr>
			<tr>
				<td class="label">上传文件：</td>
				<td class="content">
					<div><input type="button" onclick="addFile();" value="增加一个文件" class="btn" style="margin-bottom:5px;" /></div>
					<div id="file0"><input type="button" onclick="$('#file0').remove();" value="删除" class="btn" />
						<input type="file" name="resFile" size="30" class="input" style="width:8cm;margin:2px 5px;"/></div>
					<div id="file1"><input type="button" onclick="$('#file1').remove();" value="删除" class="btn" />
						<input type="file" name="resFile" size="30" class="input" style="width:8cm;margin:2px 5px;"/></div>
					<div id="file2"><input type="button" onclick="$('#file2').remove();" value="删除" class="btn" />
						<input type="file" name="resFile" size="30" class="input" style="width:8cm;margin:2px 5px;"/></div>
					<div id="fileBefore" style="clear:both"></div>
					<textarea id="fileTable" style="display:none;">
						<div id="file{0}"><input type="button" onclick="$('#file{0}').remove();" value="删除" class="btn" />
						<input type="file" name="resFile" size="30" class="input" style="width:8cm;margin:2px 5px;"/></div>
					</textarea>
					<script type="text/javascript">
						var fileIndex = 3;
						var fileTpl = $.format($("#fileTable").val());
						function addFile() {
							$('#fileBefore').before(fileTpl(fileIndex++));
						}
					</script>
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

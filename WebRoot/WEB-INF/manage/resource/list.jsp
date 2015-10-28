<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/inc/path.inc"%>
<%@include file="/inc/taglib.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<%@ include file="/inc/manage.inc"%>
  </head>
  
  <body>
  	<div id="container">
  	  	<div class="navi">
  	  		<span class="navi_b">当前位置:</span><span class="navi_h"><a href="#">资源管理 - 资源文件列表</a></span>
  	  		<span class="navi_f">
  	  			<form method="post">
  	  			创建文件夹:<input type="text" name="dirName" size="10" onkeypress="if(event.keyCode==13){$('#dirButton').click();return false;}"/>
				<input id="dirButton1" class="btn" type="submit" value="新建" onclick="this.form.action='resource_createDir_list.jspa?relPath=${relPath}';"/>
				<input id="dirButton2" class="btn" type="submit" value="上传" onclick="this.form.action='resource_upload.jspa?relPath=${relPath}';"/>
  	  			<input id="dirButton3" class="btn" type="submit" value="创建资源文件" onclick="this.form.action='resource_add_list.jspa?relPath=${relPath}';"/>
  	  			</form>
  	  		</span>
  	  	</div>
  		<div class="mainData">
  		<div class="actInfo">
  			<s:actionerror theme="ccweb"/><s:actionmessage theme="ccweb"/>
  			<div class="action_okmsg hidden"><span class="actionMessage"></span></div>
  			<div class="action_errormsg hidden"><span class="actionMessage"></span></div>
  		</div>
  		<s:form id="dataForm">
	  		<input type="hidden" name="id"/>
	  		<div class="data">
				<table class="pn-ltable">
				<thead class="pn-lthead">
				<tr>
					<th width="20px"><input type="checkbox" id="allCheck" onclick="Pn.checkBox('ids',this.checked);"  title="全选/不选　本页所有数据"/></th>
					<th>文件名(可直接编辑)</th><th>大小</th><th>修改时间</th><th class="last">操作</th>
				</tr>
				</thead>
				
				<s:if test="%{subDir!=null||subDir.size>0}">
				<tbody class="pn-ltbody">
				<s:iterator id="row" value="%{subDir}" status="status">
				  <tr name="tr_ids" onmouseover="Pn.LTable.lineOver(this);" onmouseout="Pn.LTable.lineOut(this);" onclick="Pn.LTable.lineSelect(this);">
					<td><input type="checkbox" name="ids" value="file-<s:property value="#status.index"/>" /></td>
					<td>
						<img src="<%=basePath %>r/c/img/file/<s:property value="#row.ico"/>" style="vertical-align:middle;margin-right:2px;"/>
						<input type="text" id="file-<s:property value="#status.index"/>" value="<s:property value="#row.name"/>" origName="<s:property value="#row.name"/>" 
						style="border:1px solid #ddd;padding:3px 0 2px 2px;" onclick="fileModify(this);" onfocus="fileModify(this);" onblur="fileRename(this);" 
						onkeypress="if(event.keyCode==13){fileRename(this);}" size="30"/>
					</td>
					<td><s:property value="#row.size"/>KB</td>
					<td><cc:datetimes format="yyyy-MM-dd HH:mm:ss" datetype="date" value="#row.lastModified"/></td>
					<td class="pn-lopt">
						<a href="resource_delete_list.jspa" onclick="return fileDelete(this,'file-<s:property value="#status.index"/>');" class="pn-loperator">
					  		<img src="<%=r_m%>/img/delete.gif" alt="删除" />
						</a>
					</td>
				</tr>
			    </s:iterator>
			    <s:if test="%{subDir==null||subDir.size<=0}">
					<tr><td colspan="100" class="pn-lnoresult">没有相关数据！</td></tr>
				</s:if>
				</tbody>
				</s:if>
				</table>				
  			</div>
  		</s:form>
  		</div>
  	</div>
  </body>
</html>
<script type="text/javascript">
function fileModify(o) {
	if($(o).attr('isModify')!='true'){
		$(o).attr('isModify','true');
		$(o).attr('style','border:1px solid #000;padding:3px 0 2px 2px;');
		$(o).select();
	}
}
function fileRename(o){
	if($(o).attr('isModify')!='true'){
		return;
	}
	$(o).attr('isModify','false');
	if(!Pn.checkLen($(o))) {
		alert("文件名不能为空！");
		return;
	}
	//文件名没有改动
	if($(o).val()==$(o).attr('origName')) {
		$(o).attr('style','border:1px solid #ddd;padding:3px 0 2px 2px;');
		return;
	}
	$.postJson('ajax/resource_rename.jspa', {
		'resName' : $(o).val(),
		'origName' : $(o).attr('origName'),
		'relPath' : '${relPath}'
	}, function(data) {
		if (data.success) {
			$(o).attr('origName',$(o).val());
			$(o).attr('style','border:1px solid #ddd;;padding:3px 0 2px 2px;');
			$(o).blur();
			$(".action_errormsg").hide();$(".action_okmsg").show();
			$(".action_okmsg > .actionMessage").html('重命名成功');
		} else if (!data.success){
			$(o).val($(o).attr('origName'));
			$(o).select();
			$(".action_okmsg").hide();$(".action_errormsg").show();
			$(".action_errormsg > .actionMessage").html('重命名失败');
		}
	});
}
function fileDelete(o,f) {
	if(confirm('您确定删除吗？')) {
		var href = $(o).attr('href');
		$(o).attr('href',href+'?relPath=${relPath}&resName='+$('#'+f).val());
		return true;
	} else {
		return false;
	}
}
</script>
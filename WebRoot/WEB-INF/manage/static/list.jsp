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
  	  	<div class="navi"><span class="navi_b">当前位置:</span>
  	  		<span class="navi_h">静态化管理</span>
  	  	</div>
  		<div class="mainData" style="padding-top:50px;">
			<div class="actInfo"><s:actionerror theme="ccweb"/><s:actionmessage theme="ccweb"/></div>
			<table width="30%" class="table_edit"  style="margin-top:20px;width:600px;" >
				<tr><td style="padding:5px;" >
						<div id="btn" class="btnDiv_fa" style="padding:10px;width:97%;">
							<input id="createAll" type="button" value=" 整站静态化 " class="btn_fa" style="width:160px;padding:0 20px;" onclick="createAll();">
						</div>
				</td></tr>
			</table>
			<table width="30%" class="table_edit"  style="margin-top:20px;width:600px;" >
				<tr><td style="padding:5px;" >
						<div id="btn" class="btnDiv_fa" style="padding:10px;width:96%;">
							<input id="createIndex" type="button" value=" 生成首页 " class="btn_fa" onclick="createIndex();">
							<input id="deleteIndex" type="button" value=" 删除首页" class="btn_fa" onclick="deleteIndex();">
						</div>
				</td></tr>
			</table>
			<table width="30%" class="table_edit"  style="margin-top:20px;width:600px;" >
				<tr><td style="padding:15px;">
						栏目：<s:select name="channelId" id="channel"  list="channelList" listKey="id" listValue="selectTree" cssClass="select" />
						更新子栏目：<s:radio list='#{"1":"是","0":"否"}' name="containChild"  listKey="key" listValue="value" value="1"></s:radio>
				</td></tr>
				<tr><td>
						<div id="btn" class="btnDiv_fa">
							<input id="createChannel" type="button" value=" 生成栏目页 " class="btn_fa" style="width:160px;padding:0 20px;" onclick="createChannel();">
						</div>
				</td></tr>
			</table>
			<table width="30%" class="table_edit"  style="margin-top:20px;width:600px;" >
				<tr><td style="padding:15px;">
						栏目：<s:select name="channelId" id="content" list="channelList" listKey="id" listValue="selectTree" cssClass="select" />
						开始时间：<s:textfield name="startDate" id="startDate" cssClass="input Wdate" cssStyle="padding:2px 2px 1px 2px;"  onclick="WdatePicker({isShowWeek:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
				</td></tr>
				<tr><td>
						<div id="btn" class="btnDiv_fa">
							<input id="createContent" type="button" value=" 生成内容页 " class="btn_fa" style="width:160px;padding:0 20px;" onclick="createContent();">
						</div>
				</td></tr>
			</table>
		</div>
  	</div>	
  </body>
</html>
<script type="text/javascript">
function createAll(){
	var button = $("#createAll");
	var value = button.val();
	button.val("正在生成...").attr("disabled","disabled");
	$.postJson('ajax/static_index.jspa', {}, function(data) {
		if (data.success) {
			$.postJson("ajax/static_channel.jspa",{},function(data) {
				if(data.success) {
					$.postJson("ajax/static_content.jspa",{},function(data) {
						button.val(value).removeAttr("disabled");
						if(data.success) {
							alert("整站静态化完成！");
						} else {
							alert(data.msg);	
						}
					});
				} else {
					alert(data.msg);	
				}
			});
		}
	});
}
function createIndex() {
	var button = $("#createIndex");
	var value = button.val();
	button.val("正在生成...").attr("disabled","disabled");
	$.postJson("ajax/static_index.jspa",{},function(data) {
		button.val(value).removeAttr("disabled");
		if(data.success) {
			alert("首页静态化完成！");
		} else {
			alert(data.msg);	
		}
	});
}
function deleteIndex() {
	$.postJson("ajax/static_deleteIndex.jspa",{},function(data) {
		if(data.success) {
			alert("删除成功！");
		} else {
			alert("文件不存在！");
		}
	});
}
function createChannel() {
	var button = $("#createChannel");
	var value = button.val();
	button.val("正在生成...").attr("disabled","disabled");
	//'containChild':$('input[@name=items][@checked]').val()
	$.postJson("ajax/static_channel.jspa",{
		'channelId' : $("#channel").val()
	},function(data) {
		button.val(value).removeAttr("disabled");
		if(data.success) {
			alert("栏目静态化完成！共"+data.count+"个栏目。");
		} else {
			alert(data.msg);	
		}
	});
}
function createContent() {
	var button = $("#createContent");
	var value = button.val();
	button.val("正在生成...").attr("disabled","disabled");
	$.postJson("ajax/static_content.jspa",{
		'channelId' : $("#content").val(),
		'startDate' : $("#startDate").val()
	},function(data) {
		button.val(value).removeAttr("disabled");
		if(data.success) {
			alert("内容静态化完成！共"+data.count+"个内容。");
		} else {
			alert(data.msg);	
		}
	});
}
</script>
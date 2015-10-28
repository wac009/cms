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
  	  		<span class="navi_h">全文检索</span>
  	  	</div>
  		<div class="mainData" style="padding-top:50px;">
			<div class="actInfo"><s:actionerror theme="ccweb"/><s:actionmessage theme="ccweb"/></div>
			<s:hidden id="startId" name="startId" />
			<s:hidden id="stop" value="" />
			<table width="30%" class="table_edit"  style="margin-top:20px;width:600px;" >
				<tr>
					<td class="label">栏目：</td>
					<td class="content">
						<s:select name="channelId" id="content" list="channelList" listKey="id" listValue="selectTree" cssClass="select" />
					</td>
				</tr>
				<tr>
					<td class="label">开始时间：</td>
					<td class="content">
						<s:textfield name="startDate" id="startDate" cssClass="input Wdate" cssStyle="padding:2px 2px 1px 2px;"  onclick="WdatePicker({isShowWeek:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
					</td>
				</tr>
				<tr>
					<td class="label">结束时间：</td>
					<td class="content">
						<s:textfield name="endDate" id="endDate" cssClass="input Wdate" cssStyle="padding:2px 2px 1px 2px;"  onclick="WdatePicker({isShowWeek:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
					</td>
				</tr>
				<tr>
					<td class="label">每次生成数量：</td>
					<td class="content">
						<s:textfield name="max" id="max" cssClass="input" value="99999999" />
						<span class="red">*</span>
					</td>
				</tr>
				<tr><td colspan="2">
						<div id="btn" class="btnDiv_fa">
							<input id="createLucene" type="button" value=" 生成索引 " class="btn_fa"  onclick="createLucene();">
							<input id="cancelLucene" type="button" value=" 取消 " class="btn_fa" onclick="$('#stop').val('1')">
						</div>
				</td></tr>
			</table>
		</div>
  	</div>	
  </body>
</html>
<script type="text/javascript">
function luceneSubmit() {
	$.postJson("ajax/lucene_create.jspa", {
		"startDate" : $("#startDate").val(),
		"endDate" : $("#endDate").val(),
		"max" : $("#max").val(),
		"channelId" : $("#channelId").val(),
		"startId" : $("#startId").val()
	}, function(data) {
		if(data.success) {
			if(data.lastId && $("#stop").val()=="") {
				$("#startId").val(data.lastId);
				luceneSubmit();
			} else {
				$("#startId").val("");
				alert("索引生成完成！");
				$("#createLucene").removeAttr("disabled");
				$("#cancelLucene").attr("disabled","disabled");
				$("#stop").val("");
			}
		} else {
			alert(data.msg);
			$("#createLucene").removeAttr("disabled");
			$("#cancelLucene").attr("disabled","disabled");
			$("#stop").val("");
		}
	}, "json");
}

function createLucene() {
	$("#createLucene").attr("disabled","disabled");
	$("#cancelLucene").removeAttr("disabled");
	$("#stop").val("");		
	luceneSubmit();
}
</script>
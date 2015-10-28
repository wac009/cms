<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/inc/path.inc"%>
<%@include file="/inc/taglib.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<%@ include file="/inc/manage.inc"%>
  </head>
  
  <body>
  	<div class="navi"><span class="navi_b">当前位置:</span><span class="navi_h">信息采集管理 - 修改</span></div>
	<div class="mainData">
	<div class="actInfo"><s:actionerror theme="ccweb"/><s:actionmessage theme="ccweb"/></div> 
	<s:form action="acquisition_update_edit" cssClass="vldform">
		<s:hidden name="id" />
		<s:hidden name="acquisition.id" />
		<s:hidden name="acquisition.site.id" />
		<s:hidden name="acquisition.user.id" />
		<s:hidden name="pageNo"/>
		<table width="100%" class="table_edit">
			<tr>
				<td class="label">采集名称：</td>
				<td class="content">
					<s:textfield name="acquisition.name" id="name" cssClass="input required focus" />
					<s:fielderror fieldName="acquisition.name" theme="ccweb"/>
					<span class="red">*</span>
				</td>
			</tr>
			<tr>
				<td class="label">入库信息：</td>
				<td class="content">
					类型:<s:select id="contentTypeId" list="contentTypeList" name="acquisition.type.id" listKey="id" listValue="name" cssStyle="width:100px;border:1px solid #CCC;"/>
					入库到栏目:<s:select name="acquisition.channel.id" list="channelList" listKey="id" listValue="selectTree" cssClass="select" />
					<span class="help">(只能选择末级栏目)</span>
				</td>
			</tr>
			<tr>
				<td class="label">页面编码：</td>
				<td class="content">
					<s:textfield name="acquisition.pageEncoding" id="pageEncoding" cssClass="input required" />
					<s:fielderror fieldName="acquisition.pageEncoding" theme="ccweb"/>
					<span class="red">*</span>
					<span class="help">GBK或UTF-8，默认是GBK</span>
				</td>
			</tr>
			<tr>
				<td class="label">暂停时间：</td>
				<td class="content">
					<s:textfield name="acquisition.pauseTime" id="pauseTime" cssClass="input required" />
					<s:fielderror fieldName="acquisition.pauseTime" theme="ccweb"/>
					<span class="red">*</span>
					<span class="help">每采集一篇文章暂停时间。单位(毫秒)</span>
				</td>
			</tr>
			<tr>
				<td class="label">采集地址：</td>
				<td class="content">
					<s:textarea name="acquisition.planList" id="planList" rows="4" cssStyle="width:500px;" cssClass="input" ></s:textarea>
					<s:fielderror fieldName="acquisition.planList" theme="ccweb"/>
					<span class="help">一个地址一行</span>
				</td>
			</tr>
			<tr>
				<td class="label">动态地址：</td>
				<td class="content">
					<s:textfield name="acquisition.dynamicAddr" id="dynamicAddr" cssStyle="width:500px;" cssClass="input" />
					<s:fielderror fieldName="acquisition.dynamicAddr" theme="ccweb"/>
					<span class="help">分页变量用[page]代替</span><br/>页码 从 
					<s:textfield name="acquisition.dynamicStart" id="dynamicStart" cssStyle="width:50px;" cssClass="input" />到
					<s:textfield name="acquisition.dynamicEnd" id="dynamicEnd" cssStyle="width:50px;" cssClass="input" />
				</td>
			</tr>
			<tr>
				<td class="label">内容地址集：</td>
				<td class="content">
					<div style="width:99%;margin:2px 0;">
						<span style="width:49%;display:inline-block;text-align:center;">开始HTML</span>
						<span style="width:49%;display:inline-block;text-align:center;">结束HTML</span>
					</div>
					<s:textarea name="acquisition.linksetStart" id="linkset" cssStyle="width:48%;" rows="4" cssClass="input" />
					<s:textarea name="acquisition.linksetEnd" id="linkset" cssStyle="width:48%;" rows="4" cssClass="input" />
				</td>
			</tr>
			<tr>
				<td class="label">内容地址：</td>
				<td class="content">
					<s:textarea name="acquisition.linkStart" id="linkStart" cssStyle="width:48%;" rows="4" cssClass="input" />
					<s:textarea name="acquisition.linkEnd" id="linkEnd" cssStyle="width:48%;" rows="4" cssClass="input" />
				</td>
			</tr>
			<tr>
				<td class="label">标题：</td>
				<td class="content">
					<s:textarea name="acquisition.titleStart" id="titleStart" cssStyle="width:48%;" rows="4" cssClass="input" />
					<s:textarea name="acquisition.titleEnd" id="titleEnd" cssStyle="width:48%;" rows="4" cssClass="input" />
				</td>
			</tr>
			<tr>
				<td class="label">内容：</td>
				<td class="content">
					<s:textarea name="acquisition.contentStart" id="contentStart" cssStyle="width:48%;" rows="4" cssClass="input" />
					<s:textarea name="acquisition.contentEnd" id="contentEnd" cssStyle="width:48%;" rows="4" cssClass="input" />
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
	<div id="periodicalDialog" title="选择期刊栏目" style="display:none;text-align:left;">
		<div id="periodicalSelector" >
			<s:if test="publicationRoot==null">【没有可选择的期刊】</s:if>
			<s:else>
				<s:component template="tree.ftl" theme="ccweb">
					<s:param name="root" value="%{publicationRoot}"/>
					<s:param name="treeId" value="'t1'"/>
					<s:param name="isUrl" value="false"/>
					<s:param name="isRadio" value="true"/>
					<s:param name="radioName" value="'periodicalCtg'"/>
				</s:component>
			</s:else>
		</div>
	</div>
  </body>
</html>
<script type="text/javascript">
	//选择期刊栏目
	$("#periodicalDialog").dialog({
		autoOpen: false,
		modal: true,
		width: 380,
		height: 500,
		position: ["center",20],
		buttons: {
			"OK": function() {
				$("#periodicalSelector input[name='periodicalCtg']:checked").each(function(){
					appendPCtg(this);
					$(this).removeAttr("checked");
				});
				$(this).dialog("close");
			}
		}
	});
	function appendPCtg(pCtg) {
		var nodeList = eval($(pCtg).attr("nodeList"));
		var s = "<div style='padding:0;'>";
		for(var i=0,len=nodeList.length;i<len;i++) {
			s += nodeList[i];
			if(i<len-1) {
				s += " > ";
			}
		}
		s += " <a href='javascript:void(0);' onclick='$(this).parent().remove();' class='pn-opt'>删除</a>";
		s += " <input type='hidden' name='acquisition.periodicalCtg.id' value='"+$(pCtg).val()+"'/>";
		s += " </div>";
		$("#periodicalContainer").html(s);
	}
	$('#periodicalLink').click(function(){
		$('#periodicalDialog').dialog('open');
		return false;
	});
	
</script>

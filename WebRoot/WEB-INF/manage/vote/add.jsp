<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/inc/path.inc"%>
<%@include file="/inc/taglib.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<%@ include file="/inc/manage.inc"%>
  </head>
  
  <body>
  	<div class="navi"><span class="navi_b">当前位置:</span><span class="navi_h">投票管理 - 添加投票</span></div>
	<div class="mainData">
	<div class="actInfo"><s:actionerror theme="ccweb"/><s:actionmessage theme="ccweb"/></div>
	<s:form action="vote_save_add" cssClass="vldform">
		<s:hidden name="voteTopic.site.id" value="%{webId}"/>
		<s:hidden name="voteTopic.user.id" value="%{userId}"/>
		<table width="100%" class="table_edit">
			<tr>
				<td class="label">名称：</td>
				<td class="content">
					<s:textfield name="voteTopic.title" id="title" cssClass="input required focus" />
					<s:fielderror fieldName="voteTopic.title" theme="ccweb"/>
					<span class="red">*</span>
				</td>
			</tr>
			<tr>
				<td class="label">描述：</td>
				<td class="content">
					<s:textfield name="voteTopic.description" id="description" cssClass="input" />
					<s:fielderror fieldName="voteTopic.description" theme="ccweb"/>
				</td>
			</tr>
			<tr>
				<td class="label">投票时间限制：</td>
				<td class="content">
					开始时间<s:textfield name="startTime" id="startTime" cssClass="input Wdate" cssStyle="padding:2px 2px 1px 2px;" 
					onclick="WdatePicker({isShowWeek:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
					结束时间<s:textfield name="endTime" id="endTime" cssClass="input Wdate" cssStyle="padding:2px 2px 1px 2px;" 
					onclick="WdatePicker({isShowWeek:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
					<span class="help">留空则不限制</span>
				</td>
			</tr>
			<tr>
				<td class="label">限制重复投票时间：</td>
				<td class="content">
					<s:textfield name="voteTopic.repeateHour" id="repeateHour" value="24" cssClass="input" />小时
					<s:fielderror fieldName="voteTopic.repeateHour" theme="ccweb"/>
					<span class="help"> 0 为不限制。</span>
					<s:checkbox name="voteTopic.restrictIp" id="isRestrictIp"/> <label for="isRestrictIp">IP限制</label>
					<s:checkbox name="voteTopic.restrictCookie" id="isRestrictCookie"/> <label for="isRestrictCookie">Cookie限制</label>
					<s:checkbox name="voteTopic.restrictMember" id="isRestrictMember"/> <label for="isRestrictMember">用户限制</label>
				</td>
			</tr>
			<tr>
				<td class="label">最多可选择几项：</td>
				<td class="content">
					<s:textfield name="voteTopic.multiSelect" value="1" cssClass="input" />
					<s:fielderror fieldName="voteTopic.multiSelect" theme="ccweb"/>
				</td>
			</tr>
			<tr>
				<td class="label">属性设置：</td>
				<td class="content">
					<s:checkbox name="voteTopic.def" id="isCurrent"/> <label for="isCurrent">默认投票</label>
					<s:checkbox name="voteTopic.disabled" id="isDisabled"/> <label for="isDisabled">关闭投票</label>
				</td>
			</tr>
			<tr>
				<td class="label">投票选项：</td>
				<td class="content">
					<div id="dynamicDiv">
					增加<input id="addCount" type="text" value="1" size="2" class="input" style="width: 30px;"/>行&nbsp;&nbsp;
					<input type="button" value="增加" class="btn" onclick="addLine($(addCount).val());"/><br/>
					<s:iterator begin="1" end="4" status="status">
						<div id="dynamicDiv_<s:property value="#status.index"/>">
						标题：<input type="text" class="input" name="voteItems[<s:property value="#status.index"/>].title" size="50"/>&nbsp;
						票数：<input type="text" class="input" style="width: 30px;" name="voteItems[<s:property value="#status.index"/>].voteCount" value="0" size="5"/>&nbsp;
						排序：<input type="text" class="input"style="width: 30px;" name="voteItems[<s:property value="#status.index"/>].priority" value="<s:property value="#status.index+1"/>" size="5"/>&nbsp;
						<input type="button" value="删除" class="btn" onclick="$(dynamicDiv_<s:property value="#status.index"/>).remove();"/>
						</div>
					</s:iterator>
					</div>
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
<script type="text/javascript">
var lineNum = 4;
function addLine(count) {
	count = count || 1;
	for(var i=0;i<count;i++) {
		lineNum++;
		$('#dynamicDiv div:last').after(getLine(lineNum));
	}
}
function getLine(num) {
  return '<div id="dynamicDiv_'+num+'">'+
	'标题：<input type="text" class="input" name="voteItems['+num+'].title" size="50"/>&nbsp; '+
	'票数：<input type="text" class="input" style="width: 30px;" name="voteItems['+num+'].voteCount" value="0" size="5"/>&nbsp; '+
	'排序：<input type="text" class="input" style="width: 30px;" name="voteItems['+num+'].priority" value="'+(num+1)+'" size="5"/>&nbsp; '+
	'<input type="button" value="删除" class="btn" onclick="$(dynamicDiv_'+num+').remove();"/>'+
	'</div>';
}
</script>
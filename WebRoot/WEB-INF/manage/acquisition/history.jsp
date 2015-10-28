<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/inc/path.inc"%>
<%@include file="/inc/taglib.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<%@ include file="/inc/manage.inc"%>
	<script type="text/javascript">
		function getTableForm() {
			return document.getElementById('dataForm');
		}
		function checkedCount(name) {
			var batchChecks = document.getElementsByName(name);
			var count = 0;
			for (var i = 0;i < batchChecks.length; i++) {
				if (batchChecks[i].checked) {
					count++;
				}
			}
			return count;
		}
		function optDelete() {
			if(checkedCount("ids")<=0) {
				alert("请选择要删除的数据");
				return;
			}
			if(!confirm("确定删除吗?")) {
				return;
			}
			var f = getTableForm();
			f.action="acquisition_deleteHistory_history.jspa";
			f.submit();
		}
	</script>
  </head>
  
  <body>
  	<div id="container">
  	  	<div class="navi"><span class="navi_b">当前位置:</span>
	  	  	<span class="navi_h">采集管理 - 采集历史列表</span>
	  	  	<span class="navi_f">
  	  			<a href='acquisition_history.jspa' style="padding:0 5px;">刷新</a>
  	  			<input class="del btn" type="button" value="删除" onclick="optDelete();"/>
  	  		</span>
  	  	</div>
  		<div class="mainData">
  		<div class="actInfo"><s:actionerror theme="ccweb"/><s:actionmessage theme="ccweb"/></div>
  		<s:form id="dataForm">
	  		<input type="hidden" name="id"/>
	  		<s:hidden name="pageNo"/>
	  		<div class="data">
				<table class="pn-ltable">
				<thead class="pn-lthead">
				<tr>
					<th width="20px"><input type="checkbox" id="allCheck" onclick="Pn.checkBox('ids',this.checked);"  title="全选/不选　本页所有数据"/></th>
					<th>ID</th><th>标题</th><th>采集名称</th><th>所属栏目</th><th>内容类型</th><th>采集地址</th><th>内容地址</th><th>状态</th><th>描述</th><th class="last">操作</th>
				</tr>
				</thead>
				<s:if test="%{pagination.list!=null||pagination.list.size>0}">
				<tbody class="pn-ltbody">
				<s:iterator id="row" value="%{pagination.list}">
				  <tr name="tr_ids" onmouseover="Pn.LTable.lineOver(this);" onmouseout="Pn.LTable.lineOut(this);" onclick="Pn.LTable.lineSelect(this);">
					<td><input type="checkbox" name="ids" value="<s:property value="#row.id"/>" /></td>
					<td><s:property value="#row.id"/></td>
					<td><s:property value="#row.title"/></td>
					<td><s:property value="#row.acquisition.name"/></td>
					<td><s:property value="#row.acquisition.channel.name"/></td>
					<td><s:property value="#row.acquisition.type.name"/></td>
					<td>
						<a href="<s:property value='#row.channelUrl' />" target="_blank" >点击浏览</a>
					</td>
					<td>
						<a href="<s:property value='#row.contentUrl' />" target="_blank" >点击浏览</a>
					</td>
					<td>
						<s:if test="#row.description=='SUCCESS'"><span class="green">成功</span></s:if>
						<s:else><span class="red">失败</span></s:else>
					</td>
					<td>
						<s:if test="#row.description=='SUCCESS'"><span class="green">成功</span></s:if>
						<s:elseif test="#row.description=='TITLESTARTNOTFOUND'"><span class="red">标题起始点不匹配</span></s:elseif>
						<s:elseif test="#row.description=='TITLEENDNOTFOUND'"><span class="red">标题结束点不匹配</span></s:elseif>
						<s:elseif test="#row.description=='CONTENTSTARTNOTFOUND'"><span class="red">内容开始点不匹配</span></s:elseif>
						<s:elseif test="#row.description=='CONTENTENDNOTFOUND'"><span class="red">内容结束点不匹配</span></s:elseif>
						<s:else><span class="red">未知错误</span></s:else>
					</td>
					<td class="pn-lopt">
					  	<a href="acquisition_deleteHistory_history.jspa?id=<s:property value="#row.id"/>" class="pn-loperator" onclick="if(!confirm('确定删除吗？')) {return false;}" >删除</a>
					</td>
				  </tr>
			    </s:iterator>
				</tbody>
				</s:if>
				<tfoot>
					<%@ include file="/inc/page_nodelete.inc"%>
				</tfoot>
				</table>
  			</div>
  		</s:form>
  		</div>
  	</div>
  </body>
</html>

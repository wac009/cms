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
	  	  	<span class="navi_h">采集管理 - 采集项列表</span>
	  	  	<span class="navi_f">
  	  			<a href='acquisition_list.jspa' style="padding:0 5px;">刷新</a>
  	  			<a href='acquisition_add.jspa' style="padding:0 5px;">添加</a>
  	  			<input class="del btn" type="button" value="删除" onclick="javascript:_batchDel()"/>
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
					<th>ID</th><th>名称</th><th>状态</th><th>当前/总页数</th><th>开始时间</th><th>结束时间</th><th class="last">操作</th>
				</tr>
				</thead>
				<s:if test="%{pagination.list!=null||pagination.list.size>0}">
				<tbody class="pn-ltbody">
				<s:iterator id="row" value="%{pagination.list}">
				  <tr name="tr_ids" onmouseover="Pn.LTable.lineOver(this);" onmouseout="Pn.LTable.lineOut(this);" onclick="Pn.LTable.lineSelect(this);">
					<td><input type="checkbox" name="ids" value="<s:property value="#row.id"/>" /></td>
					<td><s:property value="#row.id"/></td>
					<td><s:property value="#row.name"/></td>
					<td>
						<s:if test="#row.status==0">停止</s:if>
						<s:if test="#row.status==1">运行</s:if>
						<s:if test="#row.status==2">暂停</s:if>
					</td>
					<td><s:property value="#row.currNum"/>.<s:property value="#row.currItem"/>/<s:property value="#row.totalNum"/>.<s:property value="#row.totalItem"/></td>
					<td><s:if test="#row.startTime==null">未开始</s:if><s:else>
					<cc:datetimes format="yyyy-MM-dd HH:mm:ss" datetype="date" value="#row.startTime"/></td></s:else>
					<td><s:if test="#row.endTime==null"></s:if><s:else>
					<cc:datetimes format="yyyy-MM-dd HH:mm:ss" datetype="date" value="#row.endTime"/></td></s:else>
					<td class="pn-lopt">
						<a href="acquisition_start_progress.jspa?id=<s:property value="#row.id"/>" class="pn-loperator" >开始</a>  ┆ 
					  	<a href="acquisition_pause.jspa?id=<s:property value="#row.id"/>" class="pn-loperator" >暂停</a>  ┆ 
					  	<a href="acquisition_stop.jspa?id=<s:property value="#row.id"/>" class="pn-loperator" >停止</a>  ┆ 
					    <a href="javascript:_operate(_edit,'<s:property value="#row.id"/>')" class="pn-loperator" >修改</a>  ┆ 
					  	<a href="javascript:_operate(_delete,'<s:property value="#row.id"/>')" class="pn-loperator" >删除</a>
					</td>
				  </tr>
			    </s:iterator>
				</tbody>
				</s:if>
				<tfoot>
					<%@ include file="/inc/page.inc"%>
				</tfoot>
				</table>
  			</div>
  		</s:form>
  		</div>
  	</div>
  </body>
</html>

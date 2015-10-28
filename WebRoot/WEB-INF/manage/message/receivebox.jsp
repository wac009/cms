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
  	  		<span class="navi_h">站内信管理 - 收件箱</span>
  	  		<span class="navi_f">
  	  			<a href='message_receivebox.jspa' style="padding:0 5px;">刷新</a>
  	  			<input class="del btn" type="button" value="删除" onclick="javascript:_batchDel()"/>
	  	  		<%@ include file="inc.jsp"%>
  	  		</span>
  	  	</div>
  		<div class="mainData">
  		<div class="actInfo"><s:actionerror theme="ccweb"/><s:actionmessage theme="ccweb"/></div>
  		<s:form id="dataForm">
	  		<s:hidden name="id"/>
	  		<s:hidden name="pageNo"/>
	  		<div class="data">
				<table class="pn-ltable">
				<thead class="pn-lthead">
				<tr>
					<th width="20px"><input type="checkbox" id="allCheck" onclick="Pn.checkBox('ids',this.checked);"  title="全选/不选　本页所有数据"/></th>
					<th>ID</th><th>标题</th><th>发件人</th><th>接收时间</th><th>状态</th><th class="last">操作</th>
				</tr>
				</thead>
				<s:if test="%{pagination.list!=null||pagination.list.size>0}">
				<tbody class="pn-ltbody">
					<s:iterator id="row" value="%{pagination.list}">
					  <tr name="tr_ids" onmouseover="Pn.LTable.lineOver(this);" onmouseout="Pn.LTable.lineOut(this);" onclick="Pn.LTable.lineSelect(this);">
						<td><input type="checkbox" name="ids" value="<s:property value="#row.id"/>" /></td>
						<td><s:property value="#row.id"/></td>
						<td><s:property value="#row.msgTitle"/></td>
						<td><s:property value="#row.msgSendUser.username"/></td>
						<td><cc:datetimes format="yyyy-MM-dd HH:mm:ss" datetype="date" value="#row.sendTime"/></td>
						<td><s:if test="#row.msgStatus"><span class="green">已读</span></s:if><s:else><span class="red">未读</span></s:else></td>
						<td class="pn-lopt">
						    <a href="message_read.jspa?id=<s:property value="#row.id"/>&mf=0" class="pn-loperator" >查看</a>
						    <a href="message_reply.jspa?id=<s:property value="#row.id"/>">回复</a>
						    <a href="message_forward.jspa?id=<s:property value="#row.id"/>&mf=0">转发</a>
						    <a href="message_trash.jspa?id=<s:property value="#row.id"/>">删除到垃圾箱</a>
						  	<a href="javascript:_operate(_delete,'<s:property value="#row.id"/>')" class="pn-loperator" >
							  		<img src="<%=r_m%>/img/delete.gif" alt="删除" />
							</a>
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

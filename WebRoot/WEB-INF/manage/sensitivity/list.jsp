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
  	  		<span class="navi_h">敏感词管理 - 列表</span>
  	  		<span class="navi_f">
  	  			<a href='sensitivity_list.jspa' style="padding:0 5px;">刷新</a>
  	  			<input class="del btn" type="button" value="删除" onclick="javascript:_batchDel()"/>
  	  		</span>
  	  	</div>
  		<div class="mainData">
  		<div class="actInfo">
  			<s:actionerror theme="ccweb"/><s:actionmessage theme="ccweb"/>
  			<div class="action_okmsg hidden"><span class="actionMessage"></span></div>
  			<div class="action_errormsg hidden"><span class="actionMessage"></span></div>
  		</div> 
  		<s:form id="add" action="sensitivity_save_add" cssClass="search">
  				敏感词<s:textfield name="sensitivity.search" cssClass="input" />
  				替换为<s:textfield name="sensitivity.replacement" cssClass="input" />
				<input type="submit" value=" 添 加 " class="btn_fa">	
  		</s:form>
  		<s:form id="dataForm">
  			<s:hidden name="id"/>
	  		<div class="data">
				<table class="pn-ltable">
				<thead class="pn-lthead">
				<tr>
					<th width="20px;"><input type="checkbox" id="allCheck" onclick="Pn.checkBox('ids',this.checked);"  title="全选/不选　本页所有数据"/></th>
					<th width="20px;">ID</th><th>敏感词</th><th>替换为</th><th class="last">操作</th>
				</tr>
				</thead>
				<s:if test="%{pagination.list!=null||pagination.list.size>0}">
				<tbody class="pn-ltbody">
				<s:iterator id="row" value="%{pagination.list}">
				  <tr name="tr_ids" onmouseover="Pn.LTable.lineOver(this);" onmouseout="Pn.LTable.lineOut(this);" onclick="Pn.LTable.lineSelect(this);">
					<td ><input type="checkbox" name="ids" value="<s:property value="#row.id"/>" /></td>
					<td ><s:property value="#row.id"/></td>
					<td><s:property value="#row.search"/></td>
					<td><s:property value="#row.replacement"/></td>
					<td class="pn-lopt">
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
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
  	  		<span class="navi_h"><a href="#">角色管理 - 角色列表</a></span>
  	  		<span class="navi_f">
  	  			<a href='role_list.jspa' style="padding:0 5px;">刷新</a>
  	  			<a href='role_add.jspa' style="padding:0 5px;">添加</a>
  	  			<input class="del btn" type="button" value="删除" onclick="javascript:_batchDel()"/>
  	  		</span>
  	  	</div>
  	  	<div class="mainData">
  	  	<div class="actInfo"><s:actionerror theme="ccweb"/><s:actionmessage theme="ccweb"/></div>
  		<s:form id="dataForm">
  			<input type="hidden" name="id"/>
	  		<div class="data">
				<table class="pn-ltable">
				<thead class="pn-lthead">
				<tr>
					<th width="20px"><input type="checkbox" id="allCheck" onclick="Pn.checkBox('ids',this.checked);"  title="全选/不选　本页所有数据"/></th>
					<th>名称</th><th>描述</th><th class="last">操作</th>
				</tr>
				</thead>
				
				<s:if test="%{list!=null||list.size>0}">
				<tbody class="pn-ltbody">
				<s:iterator id="row" value="%{list}">
				  <tr name="tr_ids" onmouseover="Pn.LTable.lineOver(this);" onmouseout="Pn.LTable.lineOut(this);" onclick="Pn.LTable.lineSelect(this);">
					<td><input type="checkbox" name="ids" value="<s:property value="#row.id"/>" /></td>
					<td><s:property value="#row.name"/></td>
					<td><s:property value="#row.description"/></td>
					<td class="pn-lopt">
						<a href="javascript:_operate(_edit,'<s:property value="#row.id"/>')" class="pn-loperator" >
					  		<img src="<%=r_m%>/img/edit.gif" alt="修改" />
					  	</a> 
					  	<a href="javascript:_operate(_delete,'<s:property value="#row.id"/>')" class="pn-loperator" >
					  		<img src="<%=r_m%>/img/delete.gif" alt="删除" />
					  	</a>
					</td>
				</tr>
			    </s:iterator>
			    <s:if test="%{list==null||list.size<=0}">
					<tr><td colspan="100" class="pn-lnoresult">没有相关数据！</td></tr>
				</s:if>
				</tbody>
				</s:if>
				<tfoot>				
				<s:if test="%{list!=null||list.size>0}">
				<tr class="pn-sp">
					<td colspan="100">
						<div class="pn-sp-left">
							<input class="del btn" type="button" value="删除" onclick="javascript:_batchDel()"/>
						</div>
						<div class="pn-sp-right">
						</div>
					</td>
				</tr>				
				</s:if>
				</tfoot>
				</table>				
  			</div>
  		</s:form>
  		</div>
  	</div>	
  </body>
</html>

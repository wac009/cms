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
  	  		<span class="navi_h">广告位管理 - 广告位列表</span>
  	  		<span class="navi_f">
	  	  		<a href='advertisingSpace_list.jspa' style="padding:0 5px;">刷新</a>
	  	  		<a href='advertisingSpace_add.jspa' style="padding:0 5px;">添加</a>
	  	  		<input class="del btn" type="button" value="删除" onclick="javascript:_batchDel()"/>
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
					<th>ID</th><th>名称</th><th>价格</th><th>显示方式</th><th>启用</th><th class="last">操作</th>
				</tr>
				</thead>
				<s:if test="%{pagination.list!=null||pagination.list.size>0}">
				<tbody class="pn-ltbody">
				<s:iterator id="row" value="%{pagination.list}">
				  <tr name="tr_ids" onmouseover="Pn.LTable.lineOver(this);" onmouseout="Pn.LTable.lineOut(this);" onclick="Pn.LTable.lineSelect(this);">
					<td><input type="checkbox" name="ids" value="<s:property value="#row.id"/>" /></td>
					<td><s:property value="#row.id"/></td>
					<td><s:property value="#row.name"/></td>
					<td><s:property value="#row.price"/></td>
					<td>
						<s:if test="#row.displayType==1">随机显示</s:if><s:else>全部列出</s:else>
					</td>
					<td><s:if test="%{#row.enabled==0||#row.enabled==null}"><img src="<%=r_m%>/img/remove.gif" alt="启用" />禁用</s:if>
					<s:else><img src="<%=r_m%>/img/check.gif" alt="禁用" />启用</s:else></td>
					<td class="pn-lopt">
					    <a href="javascript:_operate(_edit,'<s:property value="#row.id"/>')" class="pn-loperator" >
					  		<img src="<%=r_m%>/img/edit.gif" alt="修改" />
					  	</a> 
					  	<a href="javascript:_operate(_delete,'<s:property value="#row.id"/>')" class="pn-loperator" >
						  		<img src="<%=r_m%>/img/delete.gif" alt="删除" />
						</a>
					  	<s:if test="%{#row.enabled==0||#row.enabled==null}">
					  		<a href="javascript:_operate(_enable,'<s:property value="#row.id"/>')" class="pn-loperator" >
					  			<img src="<%=r_m%>/img/check.gif" alt="启用" />
					  		</a>
					  	</s:if>
						<s:else>
							<a href="javascript:_operate(_disable,'<s:property value="#row.id"/>')" class="pn-loperator" >
						  		<img src="<%=r_m%>/img/remove.gif" alt="禁用" />
						  	</a>
						</s:else>
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

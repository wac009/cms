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
  	  		<span class="navi_h">评论管理 - 评论列表</span>
  	  		<span class="navi_f">
	  	  		<a href='comment_list.jspa' style="padding:0 5px;">刷新</a>
	  	  		<input class="del btn" type="button" value="删除" onclick="javascript:_batchDel()"/>
  	  		</span>
  	  	</div>
  		<div class="mainData">
  		<div class="actInfo"><s:actionerror theme="ccweb"/><s:actionmessage theme="ccweb"/></div>
  		<s:form id="dataForm">
	  		<s:hidden name="pageNo"/>
	  		<div class="data">
				<table class="pn-ltable">
				<thead class="pn-lthead">
				<tr>
					<th width="20px"><input type="checkbox" id="allCheck" onclick="Pn.checkBox('ids',this.checked);"  title="全选/不选　本页所有数据"/></th>
					<th>ID</th><th>评论</th><th>文档</th><th>用户</th><th class="last">操作</th>
				</tr>
				</thead>
				<s:if test="%{pagination.list!=null||pagination.list.size>0}">
				<tbody class="pn-ltbody">
				<s:iterator id="row" value="%{pagination.list}">
				  <tr name="tr_ids" onmouseover="Pn.LTable.lineOver(this);" onmouseout="Pn.LTable.lineOut(this);" onclick="Pn.LTable.lineSelect(this);">
					<td><input type="checkbox" name="ids" value="<s:property value="#row.id"/>" /></td>
					<td><s:property value="#row.id"/></td>
					<td style="width:300px;">
						<s:if test="#row.checked!=null||#row.checked==1"><span class="red">[已审核]</span></s:if>
						<s:if test="#row.recommend!=null||#row.recommend==1"><span class="red">[推荐]</span></s:if>
						<s:property value="#row.title"/>
					</td>
					<td style="width:300px;"><s:property value="#row.content.title"/></td>
					<td>
						<s:property value="#row.user.username"/>
						<s:property value="#row.commentExt.ip"/>
						<s:property value="#row.createTime"/>
					</td>
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

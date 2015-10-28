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
  	  		<span class="navi_h">资料中心目录管理 -目录列表</span>
  	  		<span class="navi_f">
  	  			<s:if test="parentID==null">
  	  			<a href='dataCatalog_list.jspa' style="padding:0 5px;">刷新</a>
  	  			<a href='dataCatalog_add.jspa' style="padding:0 5px;">添加</a></s:if><s:else>
  	  			<a href='dataCatalog_list.jspa?parentID=<s:property value="parentID"/>' style="padding:0 5px;">刷新</a>
  	  			<a href='dataCatalog_add.jspa?parentID=<s:property value="parentID"/>' style="padding:0 5px;">添加</a>
  	  			</s:else>
  	  			<a href='javascript:history.go(-1)' style="padding:0 5px;">返回</a>
  	  		</span>
  	  	</div>
  		<div class="mainData">
  		<div class="actInfo"><s:actionerror theme="ccweb"/><s:actionmessage theme="ccweb"/></div> 
  		<s:form id="dataForm">
	  		<s:hidden name="parentID" />
	  		<s:hidden name="id" />
	  		<div class="dataCatalog_list_level"><span style="font-size:13px;font-weight: bold;">目录层次：</span>
	  			<s:property value="levellink" escapeHtml="false"/>
  			</div>
	  		<div class="data">
				<table class="pn-ltable">
				<thead class="pn-lthead">
					<tr>
						<th width="20px"><input type="checkbox" id="allCheck" onclick="Pn.checkBox('ids',this.checked);"  title="全选/不选　本页所有数据"/></th>
						<th>名称</th><th>创建时间</th><th>创建者</th><th>进入目录</th><th>共享 / 锁定</th><th>排序</th><th class="last">操作</th>
					</tr>
				</thead>
				<s:if test="%{list!=null||list.size>0}">
				<tbody class="pn-ltbody">
				<s:iterator id="row" value="%{list}" status="status">
				  <tr name="tr_ids" onmouseover="Pn.LTable.lineOver(this);" onmouseout="Pn.LTable.lineOut(this);" onclick="Pn.LTable.lineSelect(this);">
					<td><input type="checkbox" name="ids" value="<s:property value="#row.id"/>" /></td>
					<td><s:property value="#row.name"/></td>
					<td><cc:datetimes format="yyyy-MM-dd HH:mm:ss" datetype="date" value="#row.createTime"/></td>
					<td><s:property value="#row.user.admin.user.userName"/></td>
					<td><s:property value="#"/><a href="dataCatalog_list.jspa?parentID=<s:property value="#row.id"/>">进入</a></td>
					<td><s:if test="%{#row.isShare!=null&&#row.isShare!=0}">是</s:if><s:else>否</s:else> /
						<s:if test="%{#row.isLock!=null&&#row.isLock!=0}">是</s:if><s:else>否</s:else>
					</td>
					<td>
						<span class="move"><s:if test="!#status.first">
							<a href="javascript:_operate(_up,'<s:property value="#row.id"/>')" class="pn-loperator" >
								<img src="<%=r_m%>/img/move_up.gif" alt="上移" />
							</a>
						</s:if></span>
						<s:if test="!#status.last">
				  		<a href="javascript:_operate(_down,'<s:property value="#row.id"/>')" class="pn-loperator" >
							<img src="<%=r_m%>/img/move_down.gif" alt="下移" />
						</a></s:if>
					</td>
					<td class="pn-lopt">
						<a href="javascript:_operate(_add,'<s:property value="#row.id"/>')" class="pn-loperator" >
							<img src="<%=r_m%>/img/add.gif" alt="添加子目录" />
						</a>
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
				<s:if test="%{list==null||list.size>0}">
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

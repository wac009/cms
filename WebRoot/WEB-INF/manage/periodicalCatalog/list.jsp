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
  	  		<span class="navi_h">
  	  			<a href='publication_list.jspa' style="padding:0;">刊物管理</a> - 
  	  			<s:if test="periodical!=null">
  	  			<a href='periodical_list.jspa?publicationId=${periodical.publication.id}' style="padding:0;">${periodical.publication.name}</a> - 
  	  			${periodical.year}年  &nbsp;第${periodical.yearPeriod}期  &nbsp;&nbsp;总第${periodical.totalPeriod}期</s:if>
  	  			<s:elseif test="publication!=null">
  	  			<a href='periodical_list.jspa?publicationId=${publication.id}' style="padding:0;">${publication.name}</a> - 通用目录管理</s:elseif>
  	  			<s:else>通用目录管理</s:else>
  	  		</span>
  	  		<span class="navi_f">
  	  			<a href='periodicalCatalog_list.jspa?periodicalId=${periodicalId}&publicationId=${publicationId}' style="padding:0 5px;">刷新</a>
  	  			<a href='periodicalCatalog_add.jspa?periodicalId=${periodicalId}&publicationId=${publicationId}' style="padding:0 5px;">添加新目录</a>
  	  			<a href='javascript:history.go(-1)' style="padding:0 5px;">返回</a>
  	  		</span>
  	  	</div>
  		<div class="mainData">
  		<div class="actInfo"><s:actionerror theme="ccweb"/><s:actionmessage theme="ccweb"/></div> 
  		<s:form id="dataForm">
	  		<s:hidden name="id"/>
	  		<div class="search">
	  			<s:if test="periodicalId!=null">
				期数<s:select name="periodicalId" list="periodicalList" listKey="id" listValue="name" value="periodicalId" cssClass="select"
							  cssStyle="width:600px;" onchange="_search()"></s:select>
					<s:hidden name="publicationId"/>
	  			</s:if><s:else>
	  			刊物<s:select name="publicationId" list="publicationList" listKey="id" listValue="name" value="publicationId" cssClass="select" 
	  						  onchange="_search()"></s:select>
	  				<s:hidden name="periodicalId"/></s:else>
	  		</div>
	  		<div class="data">
				<table class="pn-ltable">
				<thead class="pn-lthead">
				<tr>
					<th width="20px"><input type="checkbox" id="allCheck" onclick="Pn.checkBox('ids',this.checked);"  title="全选/不选　本页所有数据"/></th>
					<th>ID</th><th>刊物</th><th>期数</th><th>目录名称</th><th>描述</th><th>内容管理</th><th>排序</th><th>是否通用</th><th>状态</th><th class="last">操作</th>
				</tr>
				</thead>
				
				<s:if test="%{list!=null||list.size>0}">
				<tbody class="pn-ltbody">
				<s:iterator id="row" value="%{list}" status="status">
				  <tr name="tr_ids" onmouseover="Pn.LTable.lineOver(this);" onmouseout="Pn.LTable.lineOut(this);" onclick="Pn.LTable.lineSelect(this);">
					<td><input type="checkbox" name="ids" value="<s:property value="#row.id"/>" /></td>
					<td><s:property value="#row.id"/></td>
					<td><s:property value="#row.publication.name"/></td>
					<td><s:property value="#row.periodical.name"/></td>
					<td><s:property value="#row.name"/></td>
					<td><s:property value="#row.description"/></td>
					<td><s:if test="#row.hasContent"><a href="content_list.jspa?queryPeriodicalCtg=<s:property value="#row.id"/>">内容管理</a></s:if></td>
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
					<td><s:if test="%{#row.common==0||#row.common==null}"></s:if><s:else>是</s:else></td>
					<td><s:if test="%{#row.disabled==0||#row.disabled==null}"><img src="<%=r_m%>/img/check.gif" alt="启用" />启用</s:if>
					<s:else><img src="<%=r_m%>/img/remove.gif" alt="禁用" />禁用</s:else></td>
					<td class="pn-lopt">
					    <a href="javascript:_operate(_edit,'<s:property value="#row.id"/>')" class="pn-loperator" >
					  		<img src="<%=r_m%>/img/edit.gif" alt="修改" />
					  	</a> 
					  	<a href="javascript:_operate(_delete,'<s:property value="#row.id"/>')" class="pn-loperator" >
						  		<img src="<%=r_m%>/img/delete.gif" alt="删除" />
						</a>
					  	<s:if test="%{#row.disabled==0||#row.disabled==null}">
					  		<a href="javascript:_operate(_disable,'<s:property value="#row.id"/>')" class="pn-loperator" >
						  		<img src="<%=r_m%>/img/remove.gif" alt="禁用" />
						  	</a>
					  	</s:if>
						<s:else>
							<a href="javascript:_operate(_enable,'<s:property value="#row.id"/>')" class="pn-loperator" >
					  			<img src="<%=r_m%>/img/check.gif" alt="启用" />
					  		</a>
						</s:else>
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

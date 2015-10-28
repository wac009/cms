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
  	  		<span class="navi_h">投票管理 - 投票主题列表</span>
  	  		<span class="navi_f">
  	  			<a href='vote_list.jspa' style="padding:0 5px;">刷新</a>
  	  			<a href='vote_add.jspa' style="padding:0 5px;">添加</a>
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
					<th>ID</th><th>投票名称</th><th>总投票数</th><th>默认投票</th><th>投票时间</th><th>重复投票限制</th><th>投票记录</th><th>状态</th><th class="last">操作</th>
				</tr>
				</thead>
				
				<s:if test="%{pagination.list!=null||pagination.list.size>0}">
				<tbody class="pn-ltbody">
				<s:iterator id="row" value="%{pagination.list}">
				  <tr name="tr_ids" onmouseover="Pn.LTable.lineOver(this);" onmouseout="Pn.LTable.lineOut(this);" onclick="Pn.LTable.lineSelect(this);">
					<td><input type="checkbox" name="ids" value="<s:property value="#row.id"/>" /></td>
					<td><s:property value="#row.id"/></td>
					<td><s:property value="#row.title"/></td>
					<td><s:property value="#row.totalCount"/></td>
					<td><s:if test="%{#row.def==0||#row.def==null}"></s:if><s:else>是</s:else></td>
					<td>
						<s:if test="#row.startTime==null||#row.endTime==null">不限时间</s:if>
						<s:else><s:property value="#row.startTime" /> 至 <s:property value="#row.endTime" /></s:else>
					</td>
					<td>
						<s:if test="#row.repeateHour==null||#row.repeateHour==0">不限制</s:if>
						<s:else>限制<s:property value="#row.repeateHour" /> 小时</s:else>
						<s:if test="#row.restrictIp!=null||#row.restrictIp==1"><span class="red">[IP限制]</span></s:if>
						<s:if test="#row.restrictCookie!=null||#row.restrictCookie==1"><span class="red">[Cookie限制]</span></s:if>
						<s:if test="#row.restrictMember!=null||#row.restrictMember==1"><span class="red">[用户限制]</span></s:if>
					</td>
					<td><a href="voteResult_list.jspa?topicId=<s:property value="#row.id"/>">查看</a></td>
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

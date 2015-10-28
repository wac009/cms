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
  	  		<span class="navi_h"><a href="#">用户管理 - 用户列表</a></span>
  	  		<span class="navi_f">
  	  			<a href='user_list.jspa' style="padding:0 5px;">刷新</a>
  	  			<a href='user_add.jspa' style="padding:0 5px;">添加</a>
  	  			<input class="del btn" type="button" value="删除" onclick="javascript:_batchDel()"/>
  	  		</span>
  	  	</div>
  	  	<div class="mainData">
  	  	<div class="actInfo"><s:actionerror theme="ccweb"/><s:actionmessage theme="ccweb"/></div>
  		<s:form id="dataForm" onsubmit="return _validateBatch()">
	  		<s:hidden name="id"/> 
	  		<s:hidden name="pageNo"/>
	  		<div class="search">
				用户名<s:textfield name="queryUsername" cssClass="input" />
				姓名<s:textfield name="queryName" cssClass="input" />
				Email<s:textfield name="queryEmail" cssClass="input" />
				注册时间<s:select list="#{'':'选择','<':'早于此时间','=':'等于此时间','>':'晚于此时间'}" listKey="key" listValue="value" name="queryTimeOps" />
				<s:textfield name="queryTime" cssClass="input Wdate" cssStyle="padding:2px 2px 1px 2px;" 	onclick="WdatePicker({isShowWeek:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
				<s:radio list="#{'0':'全部','1':'管理员' ,'2':'只管理自己数据','3':'只读管理员','4':'已禁用' ,'5':'可删除' }" listKey="key"  listValue="value" name="queryAtt" onclick="_search()"/>
				<input type="button" value="搜索" class="btn" onclick="_search()"/>
	  		</div>
	  		<div class="data">
				<table class="pn-ltable">
				<thead class="pn-lthead">
					<tr>
						<th width="20px"><input type="checkbox" id="allCheck" onclick="Pn.checkBox('ids',this.checked);"  title="全选/不选　本页所有用户"/></th>
						<th>用户名</th><th>姓名</th><th>邮箱</th><th>登录次数</th>
						<th>注册时间</th><th>最后登录</th><th>所属组</th><th>角色</th>
						<th>管理员/受限/只读</th><th>禁用</th><th class="last">操作</th>
					</tr>
				</thead>
				
				<s:if test="%{pagination.list!=null||pagination.list.size>0}">
					<tbody class="pn-ltbody">
						<s:iterator id="row" value="%{pagination.list}">
						<s:if test="0==#row.delete"><tr name="tr" onmouseover="Pn.LTable.lineOver(this);" onmouseout="Pn.LTable.lineOut(this);" onclick="Pn.LTable.lineSelect(this);"></s:if>
				      	<s:else><tr name="tr_ids" onmouseover="Pn.LTable.lineOver(this);" onmouseout="Pn.LTable.lineOut(this);" onclick="Pn.LTable.lineSelect(this);"></s:else>
							<td>
								<s:if test="0==#row.delete"><input type="checkbox" disabled="disabled" title="此记录不可删除"/></s:if>
							  	<s:else><input type="checkbox" name="ids" value="<s:property value="#row.id"/>" /></s:else>
							</td>
							<td><s:property value="#row.username"/></td>
							<td><s:property value="#row.realname"/></td>
							<td><s:property value="#row.email"/></td>
							<td><s:property value="#row.loginCount"/></td>
							<td><s:property value="#row.registerTime"/></td>
							<td><cc:datetimes format="yyyy-MM-dd HH:mm:ss" datetype="date" value="#row.lastLoginTime"/>[<s:property value="#row.lastLoginIp"/>]</td>
							<td><s:property value="#row.group.name"/></td>
							<td>
								<s:iterator id="node" value="#row.roles" status="stat">
									<span><s:property value="#node.name"/></span>
									<s:if test="!#stat.last"> | </s:if>
								</s:iterator>
							</td>
							<s:if test="%{#row.admin==0||#row.admin==null}"><td>否&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;--&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;--</td></s:if>
							<s:else><td>是&nbsp;&nbsp;/&nbsp;&nbsp;<s:if test="%{#row.selfAdmin==0||#row.selfAdmin==null}">否</s:if><s:else>是</s:else>&nbsp;&nbsp;/&nbsp;&nbsp;
							<s:if test="%{#row.viewonlyAdmin==0||#row.viewonlyAdmin==null}">否</s:if><s:else>是</s:else></td></s:else>
							<td><s:if test="%{#row.disabled==0||#row.disabled==null}">否</s:if><s:else>是</s:else></td>
							<td class="pn-lopt">
							  <a href="javascript:_operate(_edit,'<s:property value="#row.id"/>')" class="pn-loperator" >修改</a> ┆ 
							  <a href="javascript:_operate(_edit,'<s:property value="#row.id"/>')" class="pn-loperator" >重置</a> ┆ 
							  <s:if test="%{#row.delete==0||#row.delete==null}"><span class="disabled">删除</span></s:if>
							  <s:else><a href="javascript:_operate(_delete,'<s:property value="#row.id"/>')" class="pn-loperator" >删除</a></s:else>
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

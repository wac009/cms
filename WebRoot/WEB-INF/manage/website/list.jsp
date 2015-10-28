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
  	  		<span class="navi_h">站点管理 - 站点列表</span>
  	  		<span class="navi_f">
  	  			<a href='website_list.jspa' style="padding:0 5px;">刷新</a>
  	  			<a href='website_add.jspa' style="padding:0 5px;">添加</a>
  	  		</span>
  	  	</div>
  		<div class="mainData">
  		<div class="actInfo"><s:actionerror theme="ccweb"/><s:actionmessage theme="ccweb"/></div>  		
  		<s:form id="dataForm">
	  		<input type="hidden" name="id"/>
	  		<s:hidden name="pageNo"/>
	  		<div class="search">
				域名<s:textfield name="domain" cssClass="input" onkeypress="if(event.keyCode==13){_search();}"/>
				<input type="button" value="搜索" class="btn" onclick="_search()"/>
	  		</div>
	  		<div class="data">
			<table class="pn-ltable">
				<thead class="pn-lthead">
				<tr>
					<th width="20px"><input type="checkbox" id="allCheck" onclick="Pn.checkBox('ids',this.checked);"  title="全选/不选　本页所有数据"/></th>
					<th>ID</th><th>网站名称</th><th>域名</th><th>模板</th><th>级别</th><th>创建时间</th><th>联系人</th>
					<th>首页静态化</th><th>回收站</th><th>状态</th><th class="last">操作</th>
				</tr>
				</thead>
				
				<s:if test="%{list!=null||list.size>0}">
				<tbody class="pn-ltbody">
				<s:iterator id="row" value="%{list}">
				  <tr name="tr_ids" class="datarows">
				  	<td><input type="checkbox" name="ids" value="<s:property value="#row.id"/>" /></td>
				  	<td><s:property value="#row.id"/></td>
					<td><a href="website_edit.jspa?id=<s:property value="#row.id"/>"><s:property value="#row.name" escape="false"/></a></td>
					<td><a href="http://<s:property value="#row.domain"/>" target="_top _black"><s:property value="#row.domain"/></a></td>
					<td><s:property value="#row.template.name"/></td>
					<td><s:property value="#row.rank"/>级--<s:if test="#row.rank==1">[国家级]</s:if><s:if test="#row.rank==2">[省级]</s:if><s:if test="#row.rank==3">[地市级]</s:if><s:if test="#row.rank==4">[县级]</s:if></td>
					<td><cc:datetimes format="yyyy-MM-dd HH:mm:ss" datetype="date" value="#row.createTime"/></td>
					<td><s:property value="#row.ownerName"/>(<s:property value="#row.ownerTel"/>)</td>
					<td><s:if test="%{#row.staticIndex!=null&&#row.staticIndex==1}"><img src="<%=r_m%>/img/check.gif" alt="开启" /><span class="green">开启</span></s:if><s:else><span class="red">否</span></s:else>
					<td><s:if test="%{#row.resycleOn!=null&&#row.resycleOn==1}"><img src="<%=r_m%>/img/check.gif" alt="开启" /><span class="green">开启</span></s:if><s:else><span class="red">否</span></s:else>
					<td><s:if test="%{#row.close==0||#row.close==null}"><img src="<%=r_m%>/img/check.gif" alt="开启" /><span class="green">开启</span></s:if><s:else><span class="red">否</span></s:else>
					<s:else><img src="<%=r_m%>/img/remove.gif" alt="关闭" />关闭</s:else></td>
					<td class="pn-lopt">
						<a href="javascript:_operate(_add,'<s:property value="#row.id"/>')" class="pn-loperator" >
							<img src="<%=r_m%>/img/add.gif" alt="添加子站" />
						</a> 
					  	<a href="javascript:_operate(_edit,'<s:property value="#row.id"/>')" class="pn-loperator" >
					  		<img src="<%=r_m%>/img/edit.gif" alt="修改" />
					  	</a> 
					  	<a href="javascript:_operate(_delete,'<s:property value="#row.id"/>')" class="pn-loperator" >
					  		<img src="<%=r_m%>/img/delete.gif" alt="删除" />
					  	</a>
					  	<s:if test="%{#row.close==0||#row.close==null}">
					  		<a href="javascript:_operate(_disable,'<s:property value="#row.id"/>')" class="pn-loperator" >
						  		<img src="<%=r_m%>/img/remove.gif" alt="关闭" />
						  	</a>
					  	</s:if>
						<s:else>
							<a href="javascript:_operate(_enable,'<s:property value="#row.id"/>')" class="pn-loperator" >
					  			<img src="<%=r_m%>/img/check.gif" alt="开启" />
					  		</a>
						</s:else>
						<a href="http://<s:property value="#row.domain"/>" class="pn-loperator" target="_top _black">
							<img src="<%=r_m%>/img/prev.gif" alt="浏览" />
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


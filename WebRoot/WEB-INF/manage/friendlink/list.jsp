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
  	  		<span class="navi_h">友情链接管理 - 列表</span>
  	  		<span class="navi_f">
  	  			<a href='friendlink_list.jspa' style="padding:0 5px;">刷新</a>
  	  			<a href='friendlink_add.jspa' style="padding:0 5px;">添加</a>
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
					<th>ID</th><th>名称</th><th>LOGO</th><th>类别</th><th>排序</th><th>是否显示</th><th class="last">操作</th>
				</tr>
				</thead>
				
				<s:if test="%{list!=null||list.size>0}">
				<tbody class="pn-ltbody">
				<s:iterator id="row" value="%{list}">
				  <tr name="tr_ids" onmouseover="Pn.LTable.lineOver(this);" onmouseout="Pn.LTable.lineOut(this);" onclick="Pn.LTable.lineSelect(this);">
					<td><input type="checkbox" name="ids" value="<s:property value="#row.id"/>" /></td>
					<td><s:property value="#row.id"/></td>
					<td><s:property value="#row.name"/></td>
					<td>
					<img id="preImg0" alt="预览区" noResize="true" onerror="javascript:this.src='<%=r_m%>/img/defaultpic.gif'"   src="${web.uploadPath}<s:property value="#row.logo"/>" style="width:150px;height:30px;background-color:#CCCCCC;border:1px solid #333"/>
					</td>
					<td><s:property value="#row.category.name"/></td>
					<td>
						<span class="move"><s:if test="%{isUp(#row)}">
							<a href="javascript:_operate(_up,'<s:property value="#row.id"/>')" class="pn-loperator" >
								<img src="<%=r_m%>/img/move_up.gif" alt="上移" />
							</a> 
						</s:if></span>
					  	<s:if test="%{isDown(#row)}">
					  		<a href="javascript:_operate(_down,'<s:property value="#row.id"/>')" class="pn-loperator" >
								<img src="<%=r_m%>/img/move_down.gif" alt="下移" />
							</a>
					  	</s:if>
					</td>
					<td><s:if test="%{#row.enabled==0||#row.enabled==null}"><img src="<%=r_m%>/img/remove.gif" alt="禁用" />禁用</s:if>
					<s:else><img src="<%=r_m%>/img/check.gif" alt="启用" />启用</s:else></td>
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

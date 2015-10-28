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
  	  		<span class="navi_h">栏目管理 - 栏目列表</span>
  	  		<span class="navi_f">
  	  			<a href='channel_list.jspa' style="padding:0 5px;">刷新</a>
  	  			<a href='channel_add.jspa' style="padding:0 5px;">添加栏目</a>
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
						<th>ID</th><th><span class="red">[模型]</span>栏目名</th><th>访问路径</th><th>链接</th><th>显示</th><th>启用</th><th>静态化</th><th>排序</th><th class="last">操作</th>
					</tr>
				</thead>
				<s:if test="%{list!=null||list.size>0}">
				<tbody class="pn-ltbody">
				<s:iterator id="row" value="%{list}" status="status">
				  <tr name="tr_ids" onmouseover="Pn.LTable.lineOver(this);" onmouseout="Pn.LTable.lineOut(this);" onclick="Pn.LTable.lineSelect(this);">
					<td><input type="checkbox" name="ids" value="<s:property value="#row.id"/>" /></td>
					<td><s:property value="#row.id"/></td>
					<td><span class="red">[<s:property value="#row.model.name"/>]</span>
						<a href="javascript:_operate(_edit,'<s:property value="#row.id"/>')" class="pn-loperator" ><s:property value="#row.name"/></a></td>
					<td><s:property value="#row.path"/></td>
					<td><s:if test="%{#row.link==null}">否</s:if><s:else>是<a href="<s:property value="#row.link"/>" target="_top _black"> [<s:property value="#row.link"/>]</a> </s:else>
					</td>
					<td>
							<s:if test="%{#row.display==0||#row.display==null}"><img src="<%=r_m%>/img/remove.gif" alt="开启" />不显</s:if>
							<s:else><img src="<%=r_m%>/img/check.gif" alt="关闭" />显示</s:else>
					</td>
					<td>
							<s:if test="%{#row.disabled==0||#row.disabled==null}"><img src="<%=r_m%>/img/check.gif" alt="开启" />开启</s:if>
							<s:else><img src="<%=r_m%>/img/remove.gif" alt="关闭" />关闭</s:else>
					</td>
					<td>
							<s:if test="%{#row.staticChannel!=null&&#row.staticChannel==1}">栏目</s:if>
							<s:if test="%{#row.staticContent!=null&&#row.staticContent==1}">+ 内容</s:if>
					</td>
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
					<td class="pn-lopt">
					  	<a href="javascript:_operate(_add,'<s:property value="#row.id"/>')" class="pn-loperator" >
							<img src="<%=r_m%>/img/add.gif" alt="添加子栏目" />
						</a> 
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
						<s:if test="%{#row.display==0||#row.display==null}">
							<a href="channel_openDisplay.jspa?id=<s:property value="#row.id"/>" class="pn-loperator" >显示</a>
						</s:if>
						<s:else>
				  			<a href="channel_closeDisplay.jspa?id=<s:property value="#row.id"/>" class="pn-loperator" >关闭</a>
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


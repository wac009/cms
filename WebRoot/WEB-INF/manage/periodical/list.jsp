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
  	  	<div class="navi">
  	  		<span class="navi_b">当前位置:</span>
  	  		<span class="navi_h">
  	  			<a href='publication_list.jspa' style="padding:0;">刊物管理</a> - 
  	  			<s:if test="publication!=null">${publication.name}</s:if><s:else>期刊管理</s:else>
  	  		</span>
  	  		<span class="navi_f">
  	  			<s:if test="publicationId!=null">
  	  			<a href='periodical_list.jspa?publicationId=${publicationId}' style="padding:0 5px;">刷新</a>
  	  			<a href='periodical_add.jspa?publicationId=${publicationId}' style="padding:0 5px;">添加新期刊</a>
  	  			</s:if><s:else>
  	  			<a href='periodical_list.jspa' style="padding:0 5px;">刷新</a>
  	  			<a href='periodical_add.jspa' style="padding:0 5px;">添加新期刊</a>
  	  			</s:else>
  	  			<a href='javascript:history.go(-1)' style="padding:0 5px;">返回</a>
  	  		</span>
  	  	</div>
  		<div class="mainData">
  		<div class="actInfo"><s:actionerror theme="ccweb"/><s:actionmessage theme="ccweb"/></div>
  		<s:form id="dataForm">
	  		<s:hidden name="id"/>
	  		<s:hidden name="pageNo"/>
	  		<div class="search">
	  			刊物<s:select name="publicationId" list="publicationList" listKey="id" listValue="name" value="publicationId" cssClass="select" onchange="_search()"></s:select>
				年度<s:select name="year" list="yearList" listKey="key" listValue="value" cssClass="select" onchange="_search()"></s:select>
					<input type="button" value="搜索" class="btn" onclick="_search()"/>
	  		</div>
	  		<div class="data">
				<table class="pn-ltable">
				<thead class="pn-lthead">
				<tr>
					<th width="20px"><input type="checkbox" id="allCheck" onclick="Pn.checkBox('ids',this.checked);"  title="全选/不选　本页所有数据"/></th>
					<th>ID</th><th>期刊封面</th><th>期刊名称</th><th>当前期</th><th>目录管理</th><th>内容管理</th><th>添加时间</th><th>状态</th><th>锁定</th><th>排序</th><th class="last">操作</th>
				</tr>
				</thead>
				
				<s:if test="%{pagination.list!=null||pagination.list.size>0}">
				<tbody class="pn-ltbody">
				<!-- 当前期 特殊显示 -->
				<s:iterator id="row" value="%{curPeriodicals}" status="status">
				  <tr name="tr_ids" onmouseover="Pn.LTable.lineOver(this);" onmouseout="Pn.LTable.lineOut(this);" onclick="Pn.LTable.lineSelect(this);">
					<td><s:if test="%{#row.lock==0||#row.lock==null}">
					<input type="checkbox" name="ids" value="<s:property value="#row.id"/>" /></s:if>
					<s:else><input type="checkbox" disabled="disabled"/></s:else>
					</td>
					<td><s:property value="#row.id"/></td>
					<td><img alt="期刊图片" noResize="true" onerror="javascript:this.src='<%=r_m%>/img/defaultpic.gif'" srcRoot="${web.uploadPath}" src="${web.uploadPath}<s:property value="#row.imgPath"/>" style="width:25px;height:30px;background-color:#ccc;border:1px solid #666"/></td>
					<td><s:property value="#row.name"/></td>
					<td><s:if test="%{#row.current==0||#row.current==null}"></s:if><s:else>是</s:else></td>
					<td><s:if test="%{#row.lock==0||#row.lock==null}">
					<a href="periodicalCatalog_list.jspa?periodicalId=<s:property value="#row.id"/>">目录管理</a></s:if>
					<s:else></s:else>
					</td>
					<td><s:if test="%{#row.lock==0||#row.lock==null}">
					<a href="content_list.jspa?queryPeriodical=<s:property value="#row.id"/>">内容管理</a></s:if>
					<s:else></s:else>
					</td>
					<td><cc:datetimes format="yyyy-MM-dd HH:mm:ss" datetype="date" value="#row.addTime"/></td>
					<td><s:if test="%{#row.disabled==0||#row.disabled==null}"><img src="<%=r_m%>/img/check.gif" alt="启用" />启用</s:if>
					<s:else><img src="<%=r_m%>/img/remove.gif" alt="禁用" />禁用</s:else></td>
					<td><s:if test="%{#row.lock==0||#row.lock==null}">否</s:if><s:else>锁定</s:else></td>
					<td>
					</td>
					<td class="pn-lopt">
						<s:if test="%{#row.lock==0||#row.lock==null}">
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
					  		<a href="javascript:_operate(_lock,'<s:property value="#row.id"/>')" class="pn-loperator" >
						  		锁定
						  	</a>
					  	</s:if>
						<s:else>
							<a href="javascript:_operate(_unlock,'<s:property value="#row.id"/>')" class="pn-loperator" >
					  			解锁
					  		</a>
						</s:else>
					</td>
				  </tr>
				</s:iterator>
				<s:if test="%{curPeriodicals!=null&&curPeriodicals.size>0}"><tr name="tr_ids" ><td colspan="100"></td></tr></s:if> 
				<s:iterator id="row" value="%{pagination.list}" status="status">
				  <tr name="tr_ids" onmouseover="Pn.LTable.lineOver(this);" onmouseout="Pn.LTable.lineOut(this);" onclick="Pn.LTable.lineSelect(this);">
					<td>
					<s:if test="%{#row.lock==0||#row.lock==null}">
					<input type="checkbox" name="ids" value="<s:property value="#row.id"/>" />
				  	</s:if><s:else><input type="checkbox" disabled="disabled"/></s:else>
					</td>
					<td><s:property value="#row.id"/></td>
					<td><img alt="期刊图片" noResize="true" onerror="javascript:this.src='<%=r_m%>/img/defaultpic.gif'" srcRoot="${web.uploadPath}" src="${web.uploadPath}<s:property value="#row.imgPath"/>" style="width:25px;height:30px;background-color:#ccc;border:1px solid #666"/></td>
					<td><s:property value="#row.name"/></td>
					<td><s:if test="%{#row.current==0||#row.current==null}"></s:if><s:else>是</s:else></td>
					<td><s:if test="%{#row.lock==0||#row.lock==null}">
							<a href="periodicalCatalog_list.jspa?periodicalId=<s:property value="#row.id"/>">目录管理</a></s:if>
					</td>
					<td><s:if test="%{#row.lock==0||#row.lock==null}">
							<a href="content_list.jspa?queryPeriodical=<s:property value="#row.id"/>">内容管理</a></s:if>
					</td>
					<td><cc:datetimes format="yyyy-MM-dd HH:mm:ss" datetype="date" value="#row.addTime"/></td>
					<td><s:if test="%{#row.disabled==0||#row.disabled==null}"><img src="<%=r_m%>/img/check.gif" alt="启用" />启用</s:if>
					<s:else><img src="<%=r_m%>/img/remove.gif" alt="禁用" />禁用</s:else></td>
					<td><s:if test="%{#row.lock==0||#row.lock==null}">否</s:if><s:else>锁定</s:else></td>
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
						<s:if test="%{#row.lock==0||#row.lock==null}">
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
					  		<a href="javascript:_operate(_lock,'<s:property value="#row.id"/>')" class="pn-loperator" >
						  		锁定
						  	</a>
					  	</s:if>
						<s:else>
							<a href="javascript:_operate(_unlock,'<s:property value="#row.id"/>')" class="pn-loperator" >
					  			解锁
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

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
  	  		<span class="navi_h">资料文件管理 - 文件列表</span>
  	  		<span class="navi_f">
  	  			<a href='dataInfo_list.jspa' style="padding:0 5px;">刷新</a>
  	  			<a href='dataInfo_add.jspa' style="padding:0 5px;">添加</a>
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
						<th>名称</th><th>类型</th><th>目录</th><th>添加人</th><th>创建时间</th><th>内容 / 链接 / 锁定</th><th>排序</th><th class="last">操作</th>
					</tr>
				</thead>
				<s:if test="%{pagination.list!=null||pagination.list.size>0}">
					<tbody class="pn-ltbody">
						<s:iterator id="row" value="%{pagination.list}" status="status">
						  <tr name="tr_ids" onmouseover="Pn.LTable.lineOver(this);" onmouseout="Pn.LTable.lineOut(this);" onclick="Pn.LTable.lineSelect(this);">
							<td><input type="checkbox" name="ids" value="<s:property value="#row.id"/>" /></td>
							<td><s:property value="#row.name"/></td>
							<td><s:property value="#row.type.name"/></td>
							<td><s:property value="#row.catalog.name"/></td>
							<td><s:property value="#row.user.admin.user.userName"/></td>
							<td><cc:datetimes format="yyyy-MM-dd HH:mm:ss" datetype="date" value="#row.createTime"/></td>
							<td>
								<s:if test="#row.hasTxt"> 有    </s:if><s:else> 无     </s:else>  /   
								<s:if test="#row.isLink"> 是    </s:if><s:else> 否      </s:else>  /   
								<s:if test="#row.isLock"> 是    </s:if><s:else> 否      </s:else>
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
				<s:if test="%{pagination==null||pagination.list==null||pagination.list.size<=0}">
					<tr><td colspan="100" class="pn-lnoresult">没有相关数据！</td></tr>
				</s:if>
				<s:if test="%{pagination.list!=null&&pagination.list.size>0}">
				<tr class="pn-sp">
					<td colspan="100">
						<div class="pn-sp-left">
							<input class="del btn" type="button" value="删除" onclick="javascript:_batchDel()"/>
						</div>
						<div class="pn-sp-right">
							<s:if test="pageNo<=1">
								<a href="javascript:void(0)" class="txt-disabd">首页</a> &nbsp;
								<a href="javascript:void(0)" class="txt-disabd">上页</a> &nbsp;
							</s:if>
							<s:else>
								<a href="javascript:void(0)" onclick="_gotoPage(1)">首页</a> &nbsp;
								<a href="javascript:void(0)" onclick="_gotoPage(<s:property value="pagination.prePage" />)">上页</a> &nbsp;
							</s:else>
							<s:if test="pageNo>=pagination.totalPage">
								<a href="javascript:void(0)" class="txt-disabd">下页</a> &nbsp;
								<a href="javascript:void(0)" class="txt-disabd">末页</a> &nbsp;
							</s:if>
							<s:else>
								<a href="javascript:void(0)" onclick="_gotoPage(<s:property value="pagination.nextPage" />)">下页</a> &nbsp;
								<a href="javascript:void(0)" onclick="_gotoPage(<s:property value="pagination.totalPage" />)">末页</a> &nbsp;
							</s:else>
							 &nbsp; 
							共 <s:property value="pagination.totalCount"/> 条
							每页	<input type="text" value="<s:property value="pagination.pageSize"/>" size="2" onfocus="this.select();" 
								onblur="new Pn.Cookie().set(Pn.Cookie.countPerPage,this.value,10*365*24*60*60);setFormAction('list.jspa');this.form.submit();" 
								onkeypress="if(event.keyCode==13){$(this).blur();return false;}"/> 条
							
							当前  <s:property value="pagination.pageNo"/>/<s:property value="pagination.totalPage"/>  页
							<s:if test="1==pagination.totalPage">
								<span class="txt-disabd">转到第</span> 
								<input disabled="disabled" class="txt-disabd" type="text" id="_goPs" size="2"/><span class="txt-disabd">页</span>
								<input disabled="disabled" class="btn gopage txt-disabd" id="_goPage" type="button" value="转"/>
							</s:if>
							<s:else>
								转到第 
								<input type="text" id="_goPs" size="2" onfocus="this.select();"
									onkeypress="if(event.keyCode==13){$('#_goPage').click();return false;}"/> 页
								<input class="btn gopage" id="_goPage" type="button" value="转" onclick="setFormAction('list');_gotoPage($('#_goPs').val());"/>
							</s:else>
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

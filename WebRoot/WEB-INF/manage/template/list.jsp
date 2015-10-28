<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/inc/path.inc"%>
<%@include file="/inc/taglib.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<%@ include file="/inc/manage.inc"%>
	<!-- override the generic tooltip style -->
	<style>
		.tooltip {width:100px;background-color:#3d7483;padding:5px;font-size:12px;}
	</style>
  </head>
  
  <body>
  	<div id="container">
  	  	<div class="navi"><span class="navi_b">当前位置:</span><span class="navi_h"><a href="#">模板管理 - 模板列表</a></span>
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
					<th>ID</th><th>效果预览(点击查看大图)</th><th>模板名称</th><th>资源路径</th><th>作者</th><th>版本号</th><th>描述</th><th>修改时间</th><th>排序</th><th>状态</th><th class="last">操作</th>
				</tr>
				</thead>
				
				<s:if test="%{list!=null||list.size>0}">
				<tbody class="pn-ltbody">
				<s:iterator id="row" value="%{list}" status="status">
				  <tr name="tr_ids" onmouseover="Pn.LTable.lineOver(this);" onmouseout="Pn.LTable.lineOut(this);" onclick="Pn.LTable.lineSelect(this);">
					<td><input type="checkbox" name="ids" value="<s:property value="#row.id"/>" /></td>
					<td><s:property value="#row.id"/></td>
					<td>
						<img class="xtooltip" rel="#img-<s:property value="#row.id"/>" onerror="javascript:this.src='<%=r_m%>/img/defaultpic.gif'" src="${web.uploadPath}<s:property value="#row.imgPath"/>" noResize="true" style="width:50px;height:50px;margin:2px 0;"/>
						<div class="simple_overlay" id="img-<s:property value="#row.id"/>">
							<img src="${web.uploadPath}<s:property value="#row.imgPath"/>"/>
							<div class="details"> 
								<h3><s:property value="#row.name"/></h3> 
								<p><s:property value="#row.description"/></p> 
							</div>
						</div>
					</td>
					<td><s:property value="#row.name"/></td>
					<td><s:property value="#row.resPath"/></td>
					<td><s:property value="#row.author"/></td>
					<td><s:property value="#row.version"/></td>
					<td><s:property value="#row.description"/></td>
					<td><cc:datetimes format="yyyy-MM-dd HH:mm:ss" datetype="date" value="#row.createTime"/></td>
					<td>
						<span class="move"><s:if test="!#status.first">
							<a href="javascript:_operate(_up,'<s:property value="#row.id"/>')" class="pn-loperator" >
								<img src="<%=r_m%>/img/move_up.gif" alt="上移" />
							</a>
						</s:if></span>
						<s:if test="!#status.last">
					  		<a href="javascript:_operate(_down,'<s:property value="#row.id"/>')" class="pn-loperator" >
								<img src="<%=r_m%>/img/move_down.gif" alt="下移" />
							</a>
						</s:if>
					</td>
					<td><s:if test="%{#row.isDisabled==0||#row.isDisabled==null}"><img src="<%=r_m%>/img/check.gif" alt="启用" />启用</s:if>
					<s:else><img src="<%=r_m%>/img/remove.gif" alt="禁用" />禁用</s:else></td>
					<td class="pn-lopt">
						<a href="javascript:_operate(_edit,'<s:property value="#row.id"/>')" class="pn-loperator" >
					  		<img src="<%=r_m%>/img/edit.gif" alt="修改" />
					  	</a> 
					  	<a href="javascript:_operate(_delete,'<s:property value="#row.id"/>')" class="pn-loperator" >
						  		<img src="<%=r_m%>/img/delete.gif" alt="删除" />
						</a>
					  	<s:if test="%{#row.isDisabled==0||#row.isDisabled==null}">
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
  		<!-- use the same tooltip for each prev img. -->
		<div id="tooltip_img" class="tooltip">
			点击查看大图
		</div>
  	</div>
  </body>
</html>
<!-- each overlay is initialized with this single JavaScript call -->
<script>
$(function() {
	$("img[rel]").overlay({mask: '#000'});
});
//setup tooltip for a single DIV element
$(".xtooltip").tooltip({
	// each trashcan image works as a trigger
	tip: '#tooltip_img',
	// custom positioning
	position: 'center right',
	// move tooltip a little bit to the right
	offset: [0, 5],
	// there is no delay when the mouse is moved away from the trigger
	delay: 0
});
</script>

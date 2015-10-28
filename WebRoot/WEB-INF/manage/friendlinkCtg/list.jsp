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
  	  		<span class="navi_h">友情链接类型管理 - 列表</span>
  	  		<span class="navi_f">
  	  			<a href='friendlinkCtg_list.jspa' style="padding:0 5px;">刷新</a>
  	  			<input class="del btn" type="button" value="删除" onclick="javascript:_batchDel()"/>
  	  		</span>
  	  	</div>
  		<div class="mainData">
  		<div class="actInfo">
  			<s:actionerror theme="ccweb"/><s:actionmessage theme="ccweb"/>
  			<div class="action_okmsg hidden"><span class="actionMessage"></span></div>
  			<div class="action_errormsg hidden"><span class="actionMessage"></span></div>
  		</div> 
  		<s:form id="add" action="friendlinkCtg_save_add" cssClass="search">
  				名称<s:textfield name="name" cssClass="input" />
				<input type="submit" value=" 添 加 " class="btn_fa">	
  		</s:form>
  		<s:form id="dataForm">
  			<s:hidden name="id"/>
	  		<div class="data">
				<table class="pn-ltable">
				<thead class="pn-lthead">
				<tr>
					<th width="20px"><input type="checkbox" id="allCheck" onclick="Pn.checkBox('ids',this.checked);"  title="全选/不选　本页所有数据"/></th>
					<th>ID</th><th>名称</th><th>排序</th><th class="last">操作</th>
				</tr>
				</thead>
				
				<s:if test="%{list!=null||list.size>0}">
				<tbody class="pn-ltbody">
				<s:iterator id="row" value="%{list}">
				  <tr name="tr_ids" onmouseover="Pn.LTable.lineOver(this);" onmouseout="Pn.LTable.lineOut(this);" onclick="Pn.LTable.lineSelect(this);">
					<td><input type="checkbox" name="ids" value="<s:property value="#row.id"/>" /></td>
					<td><s:property value="#row.id"/></td>
					<td>
						<input type="text" id="<s:property value="#status.index"/>" value="<s:property value="#row.name"/>" origName="<s:property value="#row.name"/>" 
						style="border:1px solid #ddd;padding:3px 0 2px 2px;" onclick="v(this);" onfocus="modify(this);" onblur="rename(this,<s:property value="#row.id"/>);" 
						onkeypress="if(event.keyCode==13){rename(this,<s:property value="#row.id"/>);}" size="30"/>
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
<script type="text/javascript">
function modify(o) {
	if($(o).attr('isModify')!='true'){
		$(o).attr('isModify','true');
		$(o).attr('style','border:1px solid #000;padding:3px 0 2px 2px;');
		$(o).select();
	}
}
function rename(o,id){
	if($(o).attr('isModify')!='true'){
		return;
	}
	$(o).attr('isModify','false');
	if(!Pn.checkLen($(o))) {
		alert("文件名不能为空！");
		return;
	}
	//文件名没有改动
	if($(o).val()==$(o).attr('origName')) {
		$(o).attr('style','border:1px solid #ddd;padding:3px 0 2px 2px;');
		return;
	}
	$.postJson('ajax/friendlinkCtg_rename.jspa', {
		'name' : $(o).val(),
		'id':id
	}, function(data) {
		if (data.success) {
			$(o).attr('origName',$(o).val());
			$(o).attr('style','border:1px solid #ddd;;padding:3px 0 2px 2px;');
			$(o).blur();
			$(".action_errormsg").hide();$(".action_okmsg").show();
			$(".action_okmsg > .actionMessage").html('重命名成功');
		} else if (!data.success){
			$(o).val($(o).attr('origName'));
			$(o).select();
			$(".action_okmsg").hide();$(".action_errormsg").show();
			$(".action_errormsg > .actionMessage").html('重命名失败');
		}
	});
}
</script>
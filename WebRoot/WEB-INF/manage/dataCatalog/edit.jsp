<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/inc/path.inc"%>
<%@include file="/inc/taglib.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<%@ include file="/inc/manage.inc"%>
  </head>
  <body>
  	<div class="navi"><span class="navi_b">当前位置:</span><span class="navi_h">资料中心目录管理 -目录修改</span></div>
	<div class="mainData">
	<div class="actInfo"><s:actionerror theme="ccweb"/><s:actionmessage theme="ccweb"/></div> 
	<s:form action="dataCatalog_update_edit" cssClass="vldform">
		<s:hidden name="parentID" />
		<s:hidden name="dataCatalog.id" />
		<table width="100%" class="table_edit">
			<tr>
				<td class="label">上级目录：</td>
				<td class="content">
					<s:select name="dataCatalog.parent.id" list="parentList" listKey="id" listValue="selectTree" cssClass="select" ></s:select>
					<s:fielderror fieldName="dataCatalog.parent.id" theme="ccweb"/>
					<span class="red">*</span>
				</td>
			</tr>
			<tr>
				<td class="label">名称：</td>
				<td class="content">
					<s:textfield name="dataCatalog.name" id="name" cssClass="input required focus" />
					<s:fielderror fieldName="dataCatalog.name" theme="ccweb"/>
					<span class="red">*</span>
				</td>
			</tr>
			<tr>
				<td class="label">描述：</td>
				<td class="content">
					<s:textarea name="dataCatalog.description" cssClass="input txtArea" />
					<s:fielderror fieldName="dataCatalog.description" theme="ccweb"/>
				</td>
			</tr>
			<tr>
				<td class="label">属性设置：</td>
				<td class="content">
					<s:checkbox name="dataCatalog.isLock" id="dataCatalog_isLock"/><label for="dataCatalog_isLock">锁定</label>
					<s:checkbox name="dataCatalog.isShare" id="dataCatalog_isShare"/><label for="dataCatalog_isShare">共享</label>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<div id="btn" class="btnDiv_fa">
						<input type="submit" value=" 提 交 " class="btn_fa">	
						<input type="reset" value=" 重 置 " class="btn_fa">
					</div>
				</td>
			</tr>
		</table>
	</s:form>
	</div>
  </body>
</html>


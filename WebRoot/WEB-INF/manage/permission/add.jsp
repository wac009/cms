<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/inc/path.inc"%>
<%@include file="/inc/taglib.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<%@ include file="/inc/manage.inc"%>
  </head>
  <body>
  	<div class="navi"><span class="navi_b">当前位置:</span><span class="navi_h">菜单管理 - 添加菜单</span></div>
	<div class="mainData">
	<div class="actInfo"><s:actionerror theme="ccweb"/><s:actionmessage theme="ccweb"/></div>
	<s:form action="permission_save_add" cssClass="vldform">
		<table width="100%" class="table_edit">
			<tr>
				<td class="label">上级菜单：</td>
				<td class="content">
				<s:select name="permission.parent.id" list="parentList" listKey="id" listValue="selectTree" cssClass="select" ></s:select>
				</td>
			</tr>
			<tr>
				<td class="label">名称：</td>
				<td class="content"><s:textfield name="permission.name" cssClass="input required focus" />
				<s:fielderror fieldName="permission.name" theme="ccweb"/>
				<span class="red">*</span></td>
			</tr>
			<tr>
				<td class="label">URL：</td>
				<td class="content"><s:textfield name="permission.url" cssClass="input" /></td>
			</tr>
			<tr>
				<td class="label">菜单设置：</td>
				<td class="content">
					<s:checkbox name="permission.isMenu" id="permission_isMenu" value="true"/><label for="permission_isMenu">是菜单</label>
					<s:checkbox name="permission.isQuick" id="permission_isQuick"/><label for="permission_isQuick">是快捷菜单</label>
				</td>
			</tr>
			<tr>
				<td class="label">功能集：</td>
				<td class="content"><s:textarea name="permission.funcs" cssClass="input txtArea" cssStyle="width:600px;height:160px;"/></td>
			</tr>
			<tr>
				<td class="label">描述：</td>
				<td class="content"><s:textarea name="permission.description" cssClass="input txtArea" /></td>
			</tr>
			<tr>
				<td class="label">分配到角色：</td>
				<td class="content">
					<s:checkboxlist list="roleList" listKey="id" listValue="name" name="roles.id" value="roles.{id}" />
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

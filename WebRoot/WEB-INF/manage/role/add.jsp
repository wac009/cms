<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/inc/path.inc"%>
<%@include file="/inc/taglib.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<%@ include file="/inc/manage.inc"%>
  </head>
  
  <body>
  	<div class="navi"><span class="navi_b">当前位置:</span>
  		<span class="navi_h"><a href="#">角色管理 - 添加角色</a></span>
  		<span class="navi_f">
  			<input class="del btn" type="button" value="提交" onclick="$('#dataForm').submit();"/>
  			<a href='javascript:history.go(-1)' style="padding:0 5px;">返回</a>
  		</span>
  	</div>
	<div class="mainData">
	<div class="actInfo"><s:actionerror theme="ccweb"/><s:actionmessage theme="ccweb"/></div>
	<s:form action="role_save_add" cssClass="vldform">
		<table width="100%" class="table_edit">
			<tr>
				<td class="label">名称：</td>
				<td class="content"><s:textfield id="name" name="role.name" cssClass="input required focus" />
				<s:fielderror fieldName="role.name" theme="ccweb"/>
				<span class="red">*</span></td>
			</tr>
			<tr>
				<td class="label">描述：</td>
				<td class="content"><s:textarea id="description" name="role.description" cssClass="input txtArea" /></td>
			</tr>
			<tr>
				<td class="label">权限设置：</td>
				<td class="content-tree">
					<s:if test="root==null">【没有添加权限】</s:if>
					<s:else>
						<s:component template="tree.ftl" theme="ccweb">
							<s:param name="root" value="%{root}"/>
							<s:param name="isCheckBox" value="true"/>
							<s:param name="onlyLeafCheckBox" value="false"/>
							<s:param name="checkBoxName" value="'permissions.id'"/>
						</s:component>
					</s:else>
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
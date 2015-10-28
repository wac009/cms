<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/inc/path.inc"%>
<%@include file="/inc/taglib.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<%@ include file="/inc/manage.inc"%>
  </head>
  
  <body>
  	<div class="navi"><span class="navi_b">当前位置:</span><span class="navi_h">资料中心用户类型管理 - 添加用户类型</span>
  		<span class="navi_f">
  			<a href='javascript:history.go(-1)' style="padding:0 5px;">返回</a>
  		</span>
  	</div>
	<div class="mainData">
	<div class="actInfo"><s:actionerror theme="ccweb"/><s:actionmessage theme="ccweb"/></div>
	<s:form action="dataUserType_save_add" cssClass="vldform">
		<table width="100%" class="table_edit">
			<tr>
				<td class="label">所属部门：</td>
				<td class="content">
				<s:select name="usertype.department.id" list="departments" listKey="id" listValue="selectTree" cssClass="select" ></s:select>
				<span class="help">指定用户类型是属于哪个部门</span></td>
			</tr>
			<tr>
				<td class="label">名称：</td>
				<td class="content">
					<s:textfield name="usertype.name" id="name" cssClass="input required focus" />
					<s:fielderror fieldName="usertype.name" theme="ccweb"/>
					<span class="red">*</span>
				</td>
			</tr>
			<tr>
				<td class="label">级别：</td>
				<td class="content">
					<s:textfield name="usertype.rank" id="rank" cssClass="input" />
					<s:fielderror fieldName="usertype.rank" theme="ccweb"/>
					<span class="help">数字越高级别越低,默认1</span>
				</td>
			</tr>
			<tr>
				<td class="label">描述：</td>
				<td class="content">
					<s:textarea name="usertype.description" cssClass="input txtArea" />
					<s:fielderror fieldName="usertype.description" theme="ccweb"/>
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

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/inc/path.inc"%>
<%@include file="/inc/taglib.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<%@ include file="/inc/manage.inc"%>
  </head>
  
  <body>
  	<div class="navi">
  		<span class="navi_b">当前位置:</span><span class="navi_h">管理 - 修改</span>
  		<span class="navi_f">
  			<input class="del btn" type="button" value="提交" onclick="$('#dataForm').submit();"/>
  			<a href='javascript:history.go(-1)' style="padding:0 5px;">返回</a>
  		</span>
  	</div>
	<div class="mainData">
	<div class="actInfo"><s:actionerror theme="ccweb"/><s:actionmessage theme="ccweb"/></div> 
	<s:form action="guestbook_update_edit" cssClass="vldform" id="dataForm">
		<s:hidden name="guestbook.id" />
		<s:hidden name="pageNo"/>		
		<table width="100%" class="table_edit">
			<tr>
				<td class="label">类型：</td>
				<td class="content">
					<s:select list="guestbookCtgs" listKey="id" listValue="name" name="guestbook.ctg.id"></s:select>
					<span class="red">*</span>
				</td>
			</tr>
			<tr>
				<td class="label">标题：</td>
				<td class="content">
					<s:textfield name="guestbook.ext.title" id="title" cssClass="input required focus" />
					<span class="red">*</span>
				</td>
			</tr>
			<tr>
				<td class="label">留言内容：</td>
				<td class="content">
					<s:textarea name="guestbook.ext.content" cssClass="input txtArea" />
				</td>
			</tr>
			<tr>
				<td class="label">回复内容：</td>
				<td class="content">
					<s:textarea name="guestbook.ext.content" cssClass="input txtArea" />
				</td>
			</tr>
			<tr>
				<td class="label">邮箱：</td>
				<td class="content">
					<s:textfield name="guestbook.ext.email" id="title" cssClass="input" />
				</td>
			</tr>
			<tr>
				<td class="label">电话：</td>
				<td class="content">
					<s:textfield name="guestbook.ext.phone" id="title" cssClass="input" />
				</td>
			</tr>
			<tr>
				<td class="label">qq：</td>
				<td class="content">
					<s:textfield name="guestbook.ext.qq" id="title" cssClass="input" />
				</td>
			</tr>
			<tr>
				<td class="label">属性：</td>
				<td class="content">
					审核：
					<s:radio list="#{'true':'是','false':'否'}" listKey="key" listValue="value" name="guestbook.checked" ></s:radio>
					<span class="red">*</span>
					推荐：
					<s:radio list="#{'true':'是','false':'否'}" listKey="key" listValue="value" name="guestbook.recommend" ></s:radio>
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


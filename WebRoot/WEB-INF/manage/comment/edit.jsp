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
  		<span class="navi_b">当前位置:</span><span class="navi_h">评论管理 - 修改</span>
  		<span class="navi_f">
  			<input class="del btn" type="button" value="提交" onclick="$('#dataForm').submit();"/>
  			<a href='javascript:history.go(-1)' style="padding:0 5px;">返回</a>
  		</span>
  	</div>
	<div class="mainData">
	<div class="actInfo"><s:actionerror theme="ccweb"/><s:actionmessage theme="ccweb"/></div> 
	<s:form action="comment_update_edit" cssClass="vldform" id="dataForm">
		<s:hidden name="comment.id" />
		<s:hidden name="pageNo"/>		
		<table width="100%" class="table_edit">
			<tr>
				<td class="label">文档：</td>
				<td class="content"><s:property value="comment.content.title"/></td>
			</tr>
			<tr>
				<td class="label">会员：</td>
				<td class="content"><s:property value="comment.commentUser.username"/></td>
			</tr>
			<tr>
				<td class="label">评论时间：</td>
				<td class="content"><s:property value="comment.createTime"/><br/>
				<s:property value="comment.commentExt.ip"/></td>
			</tr>
			<tr>
				<td class="label">属性：</td>
				<td class="content">
					审核：
					<s:radio list="#{'true':'是','false':'否'}" listKey="key" listValue="value" name="comment.checked" ></s:radio>
					<span class="red">*</span>
					推荐：
					<s:radio list="#{'true':'是','false':'否'}" listKey="key" listValue="value" name="comment.recommend" ></s:radio>
				</td>
			</tr>
			<tr>
				<td class="label">评论：</td>
				<td class="content">
					<s:textarea name="comment.text" cssClass="input txtArea" />
				</td>
			</tr>
			<tr>
				<td class="label">回复：</td>
				<td class="content">
					<s:textarea name="comment.reply" cssClass="input txtArea" />
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


<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/inc/path.inc"%>
<%@include file="/inc/taglib.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<%@ include file="/inc/manage.inc"%>
  </head>
  
  <body>
  	<div class="navi"><span class="navi_b">当前位置:</span><span class="navi_h">
	  		<a href='publication_list.jspa' style="padding:0;">刊物管理</a> - 
	  		<s:if test="periodical!=null">
	  		<a href='periodical_list.jspa?publicationId=${periodical.publication.id}' style="padding:0;">${periodical.publication.name}</a> - 
	  		<a href="periodicalCatalog_list.jspa?periodicalId=${periodicalId}" style="padding:0;">
	  			${periodical.year}年  &nbsp;第${periodical.yearPeriod}期  &nbsp;&nbsp;总第${periodical.totalPeriod}期</a></s:if>
	  		<s:elseif test="publication!=null">
  	  			<a href='periodical_list.jspa?publicationId=${publication.id}' style="padding:0;">${publication.name}</a></s:elseif>
	  		 - 添加目录
  		</span>
  		<span class="navi_f">
  			<a href='javascript:history.go(-1)' style="padding:0 5px;">返回</a>
  		</span>
  	</div>
	<div class="mainData">
	<jsp:include page="../common/imgUpload.jsp" />
	<div class="actInfo"><s:actionerror theme="ccweb"/><s:actionmessage theme="ccweb"/></div>
	<s:form action="periodicalCatalog_save_add" cssClass="vldform">
		<s:hidden name="periodicalId"/>
		<s:hidden name="publicationId"/>
	  	<s:hidden name="periodicalCatalog.periodical.id" value="%{periodicalId}"/>
	  	<s:hidden name="periodicalCatalog.website.id" value="%{webId}"/>
		<table width="100%" class="table_edit">
			<tr>
				<s:if test="periodical!=null">
					<td class="label">所属期刊：</td>
					<td class="content">
						${periodical.year}年  &nbsp;第${periodical.yearPeriod}期  &nbsp;&nbsp;总第${periodical.totalPeriod}期
					</td>
				</s:if>
				<s:else>
					<td class="label">所属刊物：</td>
					<td class="content">
						<s:if test="publication!=null"><s:property value="publication.name"/><s:hidden name="periodicalCatalog.publication.id" /></s:if>
						<s:else><s:select name="periodicalCatalog.publication.id" list="publicationList" listKey="id" listValue="name" cssClass="select" ></s:select></s:else>
					</td>
				</s:else>
			</tr>
			<tr>
				<td class="label">名称：</td>
				<td class="content">
					<s:textfield name="periodicalCatalog.name" cssClass="input required focus" />
					<s:fielderror fieldName="periodicalCatalog.name" theme="ccweb"/>
					<span class="red">*</span>
					<span class="help">期刊目录名称</span>
				</td>
			</tr>
			<tr>
				<td class="label">描述：</td>
				<td class="content">
					<s:textarea name="periodicalCatalog.description" cssClass="input txtArea" />
					<span class="help">期刊描述</span>
				</td>
			</tr>
			<tr>
				<td class="label">属性设置：</td>
				<td class="content">
					<s:checkbox name="periodicalCatalog.disabled" id="periodicalCatalog_isdisabled"/><label for="periodicalCatalog_isdisabled">禁用</label>
					<s:if test="periodical!=null">
						<s:checkbox name="periodicalCatalog.common" id="periodicalCatalog_iscommon"/><label for="periodicalCatalog_iscommon">通用目录</label>
						<s:checkbox name="periodicalCatalog.hasContent" id="periodicalCatalog_hascontent" value="true"/><label for="periodicalCatalog_hascontent">允许有内容</label>
					</s:if><s:else>
						<s:checkbox name="periodicalCatalog.common" id="periodicalCatalog_iscommon" value="true"/><label for="periodicalCatalog_iscommon">通用目录</label>
						<s:checkbox name="periodicalCatalog.hasContent" id="periodicalCatalog_hascontent" disabled="true"/><label for="periodicalCatalog_hascontent">允许有内容</label>
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

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/inc/path.inc"%>
<%@include file="/inc/taglib.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<%@ include file="/inc/manage.inc"%>
  </head>
  
  <body>
  	<div class="navi"><span class="navi_b">当前位置:</span><span class="navi_h">站点管理 - 修改站点</span></div>
	<div class="mainData">
	<div class="actInfo"><s:actionerror theme="ccweb"/><s:actionmessage theme="ccweb"/></div>
	<s:form action="website_update_edit" cssClass="vldform">
		<s:hidden name="id" />
		<s:hidden name="website.id" />
		<s:hidden name="domain" />
		<table width="100%" class="table_edit">
			<tr>
				<td class="label">上级站点：</td>
				<td class="content">
					<s:if test="parentList==null||parentList.size==0">
						<s:select name="website.parent.id" disabled="true" list="parentList" cssClass="select"></s:select>
						<span class="help">此站点是根站点</span>
					</s:if>
					<s:else>
						<s:select name="website.parent.id" list="parentList" listKey="id" listValue="selectTree" cssClass="select"></s:select>
						<span class="help">指定网站的上一级站点</span>
					</s:else>
					模板：<s:select name="website.template.id" list="templateList" listKey="id" listValue="name" cssClass="select"></s:select><span class="red">*</span>
				</td>
			</tr>
			<tr>
				<td class="label">站点名称：</td>
				<td class="content">
					<s:textfield name="website.name" id="name" cssClass="input required focus" />
					<s:fielderror fieldName="website.name" theme="ccweb"/>
					<span class="red">*</span>
					简称：<s:textfield name="website.shortName" cssClass="input" />
				</td>
			</tr>
			<tr>
				<td class="label">域名：</td>
				<td class="content"><s:textfield name="website.domain" id="domain" cssClass="input required" />
					<s:fielderror fieldName="website.domain" theme="ccweb"/>
					<span class="red">*</span><span class="help">请确保域名是有效的，如www.cncaprc.gov.cn</span>
				</td>
			</tr>
			<tr>
				<td class="label">域名别名：</td>
				<td class="content"><s:textfield name="website.domainAlias" cssClass="input" />
				<span class="help">多个域名别名用","分开，使用这些域名别名访问网站将自动重定向至主域名</span></td>
			</tr>
			<tr>
				<td class="label">域名重定向：</td>
				<td class="content"><s:textfield name="website.domainRedirect" cssClass="input" />
				<span class="help">设置域名重定向，当访问此域名时会自动重定向到新地址，用","分开</span></td>
			</tr>
			<tr>
				<td class="label">资源路径：</td>
				<td class="content"><s:textfield name="website.resPath" id="respath" cssClass="input required"  value="w"/>
				<s:fielderror fieldName="website.resPath" theme="ccweb"/>
				<span class="red">*</span>
				<span class="help">资源保存的路径，全站采用统一资源路径</span></td>
			</tr>
			<tr>
				<td class="label">访问协议：</td>
				<td class="content">
					<s:select list="#{'http://':'http://','https://':'https://'}" name="website.protocol" cssClass="select" cssStyle="width:100px;" ></s:select>
					动态页后缀：<s:select list="#{'.jspa':'.jspa','.jhtml':'.jhtml','.jsp':'.jsp','.html':'.html'}" name="website.dynamicSuffix" cssClass="select" cssStyle="width:100px;" ></s:select>
					静态页后缀：<s:select list="#{'.html':'.html','.shtml':'.shtml'}" name="website.staticSuffix" cssClass="select" cssStyle="width:100px;" ></s:select>
				</td>
			</tr>
			<tr>
				<td class="label">静态页目录：</td>
				<td class="content">
					<s:textfield name="website.staticDir" id="staticDir" cssClass="input" />  
					<s:checkbox name="website.indexToRoot" label="使用根首页 " title="使用根首页 "></s:checkbox> 使用根首页&nbsp;
					后台本地化：<s:textfield name="website.localeAdmin" id="localeAdmin" cssClass="input" />  
					前台本地化：<s:textfield name="website.localeFront" id="localeFront" cssClass="input" />  
				</td>
			</tr>
			<tr>
				<td class="label">网站cookie识别码：</td>
				<td class="content"><s:textfield name="website.cookieKey" id="cookiekey" cssClass="input required" /><span class="red">*</span>
				<s:fielderror fieldName="website.cookieKey" theme="ccweb"/>
				<span class="help">根据域名填写，如_cncaprc</span></td>
			</tr>
			<tr>
				<td class="label">属性设置：</td>
				<td class="content">
					状态：<s:radio name="website.close" list="#{'false':'开启','true':'关闭'}" listKey="key" listValue="value" />&nbsp;&nbsp;<br/>
					回收站：<s:radio name="website.resycleOn" list="#{'true':'开启','false':'关闭'}" listKey="key" listValue="value"/>&nbsp;&nbsp;<br/>
					使用相对路径：<s:radio name="website.relativePath" list="#{'true':'是','false':'否'}" listKey="key" listValue="value" /><span class=" help">只适于单站点</span>&nbsp;&nbsp;<br/>
					开启静态首页：<s:radio name="website.staticIndex" list="#{'true':'开启','false':'关闭'}" listKey="key" listValue="value"/>
				</td>
			</tr>
			<tr>
				<td class="label">审核设置：</td>
				<td class="content">
					终审级别：<s:textfield name="website.finalStep" id="finalStep" cssClass="input required" />
					审核后：<s:select list="#{'1':'不能修改删除','2':'修改后退回','3':'修改后不变'}" name="website.afterCheck" cssClass="select" cssStyle="width:200px;" ></s:select>
				</td>
			</tr>
			<tr>
				<td class="label">拥有角色：</td>
				<td class="content"><s:checkboxlist list="roleList" listKey="id" listValue="name" name="roles.id" value="website.roles.{id}"/></td>
			</tr>
			<tr>
				<td class="label">联系信息：</td>
				<td class="content">
					姓名：<s:textfield name="website.ownerName" cssClass="input" />
					邮箱：<s:textfield name="website.ownerEmail" cssClass="input" /><br/>
					电话：<s:textfield name="website.ownerTel" cssClass="input" />
					手机：<s:textfield name="website.ownerMobile" cssClass="input" /><br/>
					版权：<s:textfield name="website.copyright" id="copyright" cssClass="input required" />
					备案：<s:textfield name="website.recordCode" id="recordcode" cssClass="input required" /><span class="red">*</span>
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

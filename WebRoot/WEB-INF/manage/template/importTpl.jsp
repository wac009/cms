<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/inc/path.inc"%>
<%@include file="/inc/taglib.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<%@ include file="/inc/manage.inc"%>
  </head>
  
  <body>
  	<div class="navi"><span class="navi_b">当前位置:</span><span class="navi_h">模板管理 - 导入模板</span></div>
	<div class="mainData">
	<jsp:include page="importTplInc.jsp" />
	<s:form cssClass="vldform">
		<table width="100%" class="table_edit">
			<tr>
				<td class="label">请选择要导入的模板ZIP包：</td>
				<td class="content">
					<span id="tfc"><input type="file" id="tplFile" name="tplsFile" size="30" class="input required" style="width:260px;"/></span>
					<span class="red">*</span>
					<span class="help red">必须使用ZIP格式的压缩包</span>
				</td>
			</tr>
			<tr>
				<td class="content" colspan="2">
				导入提示：
					<p class="help" style="padding-left:70px;">
						1、必须使用ZIP格式的压缩包<br/>
						2、包内文件夹组织样式:
						\${r_t}内放的是模板资源文件如样式表、图片等;\${res}内放的是网站资源文件如上传的图片视频等;\${page}内放的是页面文件;
					</p>
					<p class="help" style="padding-left:70px;">
						例：default<br/>
						<span style="padding-left:40px;">|-\${r_t}</span><br/>
						<span style="padding-left:60px;">|-css</span><br/>
						<span style="padding-left:60px;">|-img</span><br/>
						<span style="padding-left:40px;">|-\${res}</span><br/>
						<span style="padding-left:60px;">|-images</span><br/>
						<span style="padding-left:60px;">|-upload</span><br/>
						<span style="padding-left:40px;">|-\${page}</span><br/>
						<span style="padding-left:60px;">|-index.html</span><br/>
						<span style="padding-left:60px;">|-include</span><br/>
					</p>
					<p class="help" style="padding-left:70px;">
						3、可先导出模板参考
					</p>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<div id="btn" class="btnDiv_fa">
						<input type="button" value=" 导 入 " onclick="importTpl();" class="btn_fa"/>
						<input type="reset" value=" 重 置 " class="btn_fa">
					</div>
				</td>
			</tr>
		</table>
	</s:form>
	</div>
  </body>
</html>

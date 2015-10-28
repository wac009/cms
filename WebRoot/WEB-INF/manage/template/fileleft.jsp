<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/inc/path.inc"%>
<%@include file="/inc/taglib.inc"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <%@ include file="/inc/manage.inc"%>
  </head>
  <body>
	  <div id="main_left" >
	     <h4><span >模板管理</span></h4>
	     <div class="inner">
	     	<div style="height:30px;background:#ccc;">
	     		<a href="template_main.jspa" target="_parent" style="display:inline-block;width:45%;height:30px;line-height:30px;text-align:center;">数据管理</a>
	     		<span style="display:inline-block;width:45%;height:30px;line-height:30px;text-align:center;background:#e7f5fe;">文件管理</span>
	     	</div>
	     	<div class="lttop">
				<a href="javascript:location.reload()" class="inline_block" style="padding:0 5px;">刷新目录</a>
				<a href="template_importTpl.jspa" target="right" class="inline_block" style="padding:0 5px;">导入模板</a>
				<a href="template_exportTpl.jspa" target="right" class="inline_block" style="padding:0 5px;">导出模板</a>
			</div>
			<hr style="height:1px;background:#ccc;margin:5px 0;"/>
	        <div id="tree_box">
	             <div id="tree" class="tree p_a" style="top:34px;overflow:auto;overflow-x:hidden;">
	                 <s:if test="treeRoot==null||treeRoot.id==null"><ul class="leftMenu_ul" style="padding:20px 0 0 25px;"><li>【没有模板文件】</li></ul></s:if>
					 <s:else>
						<s:component template="tree.ftl" theme="ccweb">
							<s:param name="root" value="%{treeRoot}"/>
							<s:param name="url" value="'template_fileedit'"/>
							<s:param name="durl" value="'template_filelist'"/>
							<s:param name="dparams" value="#{'relPath':'relPath'}"/>
							<s:param name="params" value="#{'relPath':'relPath'}"/>
							<s:param name="showDeep" value="2"/>
						</s:component>
					 </s:else>
	             </div>
	        </div>
	   	</div>
	  </div>
  </body>
</html>

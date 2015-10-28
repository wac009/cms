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
	     		<span style="display:inline-block;width:45%;height:30px;line-height:30px;text-align:center;background:#e7f5fe;">数据管理</span>
	     		<a href="template_main.jspa?type=file" target="_parent"
	     		   style="display:inline-block;width:45%;height:30px;line-height:30px;text-align:center;">文件管理</a>
	     	 </div>
	         <div id="tree_box">
	             <div id="tree" class="tree p_a" style="top:34px;overflow:auto;overflow-x:hidden;">
	             	<ul class="leftMenu_ul">
	             		<s:iterator id="leftMenu" value="leftMenus">
							<li class="leftMenu" onmouseover="Pn.Tree.lineOver(this)" onmouseout="Pn.Tree.lineOut(this)" onclick="Pn.Tree.lineSelected(this,'t');" >
							<a href="<s:property value="#leftMenu.url"/>.jspa" target="right"><s:property value="#leftMenu.name"/></a>
							</li>
                  		</s:iterator>
                  	</ul>
	             </div>
	        </div>
	   	</div>
	  </div>
  </body>
</html>

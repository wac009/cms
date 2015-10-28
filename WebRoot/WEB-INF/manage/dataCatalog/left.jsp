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
	     <h4><span >资料中心目录管理</span></h4>
	     <div class="inner">
	     	<div class="lttop">
				<a href="javascript:location.reload()" class="inline_block" style="padding:0 5px;">刷新目录</a>
				<hr style="height:1px;background:#ccc;margin:5px 0;"/>
			</div>	     	
	        <div id="tree_box">
	             <div id="tree" class="tree p_a" style="top:34px;overflow:auto;overflow-x:hidden;">
	                 <s:if test="leftMenu==null||leftMenu.id==null"><ul class="leftMenu_ul" style="padding:20px 0 0 25px;"><li>【没有添加任何菜单】</li></ul></s:if>
					 <s:else>
				 		<ul class="leftMenu_ul">
	                  		<s:iterator id="leftMenu" value="leftMenus">
								<li class="leftMenu" onmouseover="Pn.Tree.lineOver(this)" onmouseout="Pn.Tree.lineOut(this)" onclick="Pn.Tree.lineSelected(this,'t');" >
								<a href="<s:property value="#leftMenu.url"/>.jspa" target="right"><s:property value="#leftMenu.name"/></a>
								</li>
	                  		</s:iterator>
	                  	</ul>
					 </s:else>
	             </div>
	        </div>
	   	</div>
	  </div>
  </body>
</html>

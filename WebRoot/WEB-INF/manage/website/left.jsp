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
	     <h4><span >站点管理</span></h4>
	     <div class="inner">
	         <div id="tree_box">
	             <div id="tree" class="tree p_a" style="top:34px;overflow:auto;overflow-x:hidden;">
		             <s:iterator id="row" value="%{leftRoots}"></s:iterator>
						<s:component template="tree.ftl" theme="ccweb">
							<s:param name="root" value="%{leftMenu}"/>
							<s:param name="url" value="%{leftMenu_url}"/>
							<s:param name="durl" value="%{leftMenu_durl}"/>
							<s:param name="showDeep" value="2"/>
						</s:component>
					
	             </div>
	        </div>
	   	</div>
	  </div>
  </body>
</html>

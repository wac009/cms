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
	     <div class="inner" style="top:0">
            <div class="errorPage_left">	
				<div class="action_errormsg">
					<span class="errorMessage"><s:property value="%{interceptError}"/></span>
				</div>
			</div>
	   	</div>
	  </div>
  </body>
</html>

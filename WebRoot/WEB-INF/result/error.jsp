<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/inc/path.inc"%>
<%@include file="/inc/taglib.inc"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
 <head>
   	 <%@ include file="/inc/manage.inc"%>
</head>

<body>
	<div class="msgPage">
		<div class="msgDiv">
			<div class="msgPage_title">
				系统提示 
			</div>
			<div class="msgPage_text">
				<s:property value="%{interceptError}"/>
			</div>
			<div class="msgPage_Href">
			</div>
		</div>
	</div>
</body>
</html>

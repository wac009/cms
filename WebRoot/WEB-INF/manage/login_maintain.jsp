<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/inc/path.inc"%>
<%@include file="/inc/taglib.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>网站管理员登录</title>
		<%@ include file="/inc/manage.inc"%>
		<link href="<%=r_m%>/css/login.css" rel="stylesheet" type="text/css">
	</head>
	<body class="login_bg">
    	<div class="page" id="divPage">
            <div class="login">
            	<div class="notice error">提示：管理员维护中！</div>
            </div>
            <div class="footer">
                <div class="copyright">
                    <div class="inner">全国老龄办信息中心 </div>
                </div>
            </div>
        </div>
	</body>
</html>

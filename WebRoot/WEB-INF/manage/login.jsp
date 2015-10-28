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
            	<div class="notice error">特别提醒：为正常使用后台功能，请使用IE浏览器登录！</div>
                <div class="logo">
                    <div class="img"></div>
                    <p>	全国老龄工作委员会办公室-中国老龄门户	</p>
                </div>
                <s:form action="login!login.jspa" onsubmit="return fCheck();">
                    <div class="form">
                        <p class="title">网站管理员登录</p>
                        <div class="error"><s:actionerror theme="ccweb" /></div>
                        <ul class="input_style">
                            <li>
                                <label for="userName">用户名</label>
                                <s:textfield name="username" id="username" cssClass="input_style_out" onfocus="this.className='input_style_on';this.onmouseout=''" 
                                    onblur="this.className='input_style_off';this.onmouseout=function(){this.className='input_style_out'};" 
                                    onmousemove="this.className='input_style_move'" onmouseout="this.className='input_style_out'" />
                                <span id="checkerror_username" class="checkerror"></span>
                            </li>
                            <li>
                                <label for="password">密　码</label>
                                <s:password name="password" id="password" showPassword="true" cssClass="input_style_out" onfocus="this.className='input_style_on';this.onmouseout=''" 
                                    onblur="this.className='input_style_off';this.onmouseout=function(){this.className='input_style_out'};" 
                                    onmousemove="this.className='input_style_move'" onmouseout="this.className='input_style_out'" />
                                <span id="checkerror_pwd" class="checkerror"></span>
                            </li>
                            <s:hidden id="checkCodeEnabled" name="checkCodeEnabled"/>
                            <s:if test="checkCodeEnabled">
	                            <li>
	                                <label for="checkCode">验证码</label>
	                                <s:textfield name="checkCode" id="checkCode" cssClass="input_style_out" onfocus="this.className='input_style_on';this.onmouseout=''" 
                                    onblur="this.className='input_style_off';this.onmouseout=function(){this.className='input_style_out'};" 
                                    onmousemove="this.className='input_style_move'" onmouseout="this.className='input_style_out'" />
	                                <span id="checkCodeShow"></span>
	                                <span id="checkerror_cc" class="checkerror"></span>
	                            </li>
	                        </s:if>
                        	<li>
                            	<label>&nbsp;</label>
                        		<input type="submit" value="登 录"  class="Btn SmbBtn" onMouseOver="this.className='Btn SmbBtn BtnHv'" onMouseOut="this.className='Btn SmbBtn'" onMouseDown="this.className='Btn SmbBtn BtnDw'" />
                        		<span id="checkerror" class="checkerror"></span>
                        	</li>
                        </ul>
                    </div>
                </s:form>
            </div>
            <div class="footer">
                <div class="copyright">
                    <div class="inner">
                       	全国老龄办信息中心 版权所有 &copy; 2007-2012
                    </div>
                </div>
            </div>
        </div>
        
<!-- javascript脚本 -->
<script type="text/javascript">
<!--
	window.onload = function(){
		$("#username").focus();
	};
	$(function(){
		new cnca.CheckCode2($('#checkCodeShow'),'<%=basePath%>CheckCode.svl');
	});
	function fCheck() {
		$("#checkerror_username").html("");
		$("#checkerror_pwd").html("");
		$("#checkerror_cc").html("");
		vld=true;
		if($("#checkCodeEnabled").val()=="true"){
			if( $("#checkCode").val().length =="") {
				$("#checkerror_cc").html("请输入验证码");
				$("#checkCode").focus();
				vld=false;
			}
		}
		if( $("#password").val().length =="") {
			$("#checkerror_pwd").html("请输入密码");
			$("#password").focus();
			vld=false;
		}
		if(fTrim($("#username").val()) =="") {
			$("#checkerror_username").html("请输入用户名");
			$("#username").focus();
			vld=false;
		}
		return vld;
	}
	function fTrim(str){
		return str.replace(/(^\s*)|(\s*$)/g, "");
	}
//-->
</script>
</body>
</html>

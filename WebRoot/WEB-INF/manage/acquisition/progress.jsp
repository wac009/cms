<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/inc/path.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<%@ include file="/inc/manage.inc"%>
	<script type="text/javascript">
		var messageBox;
		var percent=0;
		function checkComplete(){
			$.post("ajax/acquisition_checkComplete.jspa",{},function(data){
				if(!data.completed){
					createProgress();
				}
			},"json");
		}
		function setBar(percent,infor, message) {
			$("#bar").attr("width",8*percent);
			$("#percent").html(percent + "%");
			infor.html(message);
		}
		function createProgress() {
			$.post("acquisition_progressData.jspa",{},function(data){
				$("#progressContainer").html(data);
				messageBox = $("#messageBox");
				if (percent >= 100){
					setBar(percent,messageBox, "采集完成");
					checkComplete();
				}else {
					if(percent>0)
						setBar(percent,messageBox, "正在采集....");
					else
						setBar(percent,messageBox, "等待采集开始");
					setTimeout(createProgress, 1000);
				}
			},"html");
		}
		$(function() {
			createProgress();
		});
	</script>
  </head>
  
  <body>
	<div class="navi"><span class="navi_b">当前位置:</span><span class="navi_h">采集管理 - 采集进度</span></div>
	<div id="progressContainer" style=";text-align:left; position: absolute;top:30px;left:0;padding:10px;"></div>
	<div class="body-box"></div>
  </body>
</html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/inc/path.inc"%>
<%@include file="/inc/taglib.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<frameset rows="*" cols="209,*" framespacing="0" frameborder="no" border="0"> 
		<frame src="${srcLeft}.jspa" name="left" frameborder="no" scrolling="auto"  />
		<s:if test="params==null">
			<frame src="${srcRight}.jspa" name="right"  id="right"  frameborder="no" scrolling="auto" />
		</s:if><s:else>
			<frame src="${srcRight}.jspa?<s:iterator id="key" value="%{params.keySet}">id=1</s:iterator>" name="right" id="right" frameborder="no" scrolling="auto"  />
		</s:else>
	</frameset>
	<noframes><body>您的浏览器不支持框架frameset</body></noframes>
</html>
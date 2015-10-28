<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/inc/path.inc"%>
<%@include file="/inc/taglib.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<%@ include file="/inc/manage.inc"%>
  </head>
  
  <body>
  	<div class="navi">
  		<span class="navi_b">当前位置:</span><span class="navi_h">管理 - 修改</span>
  		<span class="navi_f">
  			<input class="del btn" type="button" value="提交" onclick="$('#dataForm').submit();"/>
  			<a href='javascript:history.go(-1)' style="padding:0 5px;">返回</a>
  		</span>
  	</div>
	<div class="mainData">
	<jsp:include page="../common/imgUpload.jsp" />
	<div class="actInfo"><s:actionerror theme="ccweb"/><s:actionmessage theme="ccweb"/></div> 
	<s:form action="friendlink_update_edit" cssClass="vldform" id="dataForm">
		<s:hidden name="friendlink.id" />
		<table width="100%" class="table_edit">
			<tr>
				<td class="label">名称：</td>
				<td class="content">
					<s:textfield name="friendlink.name" id="friendlink.name" cssClass="input required focus" />
					<span class="red">*</span>
				</td>
			</tr>
			<tr>
				<td class="label">类别：</td>
				<td class="content">
					<s:select list="ctgs" listKey="id" listValue="name" name="friendlink.category.id"></s:select>
					<span class="red">*</span>
				</td>
			</tr>
			<tr>
				<td class="label">链接地址：</td>
				<td class="content">
					<s:textfield name="friendlink.domain" id="friendlink.domain" cssClass="input required" />
					<span class="red">*</span>
				</td>
			</tr>
			<tr>
				<td class="label">站长邮箱：</td>
				<td class="content">
					<s:textfield name="friendlink.email" id="friendlink.email" cssClass="input" />
				</td>
			</tr>
			<tr>
				<td class="label">网站LOGO：</td>
				<td class="content">
					<div class="float_left">
						图片路径<s:textfield name="friendlink.logo" id="uploadImgPath0" cssClass="input" cssStyle="width:224px;" />
						<input type="button" value="清空" onclick="clearImg(0);" class="btn" style="height:24px;"/><br/>
						本地上传<span id="ifc0"><input type="file" id="imgFile0" size="25" class="input" style="width:230px;"/></span>
						<input type="button" value="上传" onclick="uploadImg(0);" class="btn" style="height:24px;"/><br/>
						宽<input type="text" id="zoomWidth0" value="139" size="5" class="input" style="width:40px"/> 
						高<input type="text" id="zoomHeight0" value="139" size="5" class="input" style="width:39px"/>
						<input type="checkbox" id="zoom0" value="true" checked="checked"/><label for="zoom0">自动压缩</label>
						<s:checkbox name="mark"  id="mark"/><label for="mark">添加水印</label>
						<input type="button" value="裁剪" onclick="imgCut(0);" class="btn" />
					</div>
					<div class="float_left" style="margin-left:15px;">
						<img id="preImg0" alt="预览区" noResize="true" onerror="javascript:this.src='<%=r_m%>/img/defaultpic.gif'" srcRoot="${web.uploadPath}" src="${web.uploadPath}<s:property value="user.ext.userImg"/>" style="width:75px;height:75px;background-color:#CCCCCC;border:1px solid #333"/>
					</div>
				</td>
			</tr>
			<tr>
				<td class="label">点击次数：</td>
				<td class="content">
					<s:textfield name="friendlink.views" id="friendlink.views" cssClass="input" />
					<s:radio list="#{'true':'显示','false':'不显示'}" listKey="key" listValue="value" name="friendlink.enabled" value="1"></s:radio>
				</td>
			</tr>
			<tr>
				<td class="label">描述：</td>
				<td class="content">
					<s:textarea name="friendlink.description" cssClass="input txtArea" />
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<div id="btn" class="btnDiv_fa">
						<input type="submit" value=" 提 交 " class="btn_fa">	
						<input type="reset" value=" 重 置 " class="btn_fa">
					</div>
				</td>
			</tr>
		</table>
	</s:form>
	</div>
  </body>
</html>


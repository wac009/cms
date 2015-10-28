<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/inc/path.inc"%>
<%@include file="/inc/taglib.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<%@ include file="/inc/manage.inc"%>
  </head>
  
  <body>
  	<div class="navi"><span class="navi_b">当前位置:</span><span class="navi_h">修改个人资料</span></div>
	<div class="mainData">
	<jsp:include page="../common/imgUpload.jsp" />
	<div class="actInfo"><s:actionerror theme="ccweb"/><s:actionmessage theme="ccweb"/></div> 
	<s:form action="index_infoupdate_infoedit" cssClass="vldform">
		<s:hidden name="id" />
		<s:hidden name="user.id"/>
		<s:hidden name="user.ext.id" />
		<table width="100%" class="table_edit">
			<tr id="titImgDiv">
				<td class="label">头像：</td>
				<td class="content">
					<div class="float_left" >
						<img id="preImg0" alt="预览区" noResize="true" srcRoot="${web.uploadPath}" src="${web.uploadPath}<s:property value="user.ext.userImg"/>" style="width:75px;height:75px;background-color:#CCCCCC;border:1px solid #333"/>
					</div>
					<div class="float_left" style="margin-left:15px;">
						图片路径<s:textfield name="user.ext.userImg" id="uploadImgPath0" cssClass="input" cssStyle="width:224px;" />
						<input type="button" value="清空" onclick="clearImg(0);" class="btn" style="height:24px;"/><br/>
						本地上传<span id="ifc0"><input type="file" id="imgFile0" size="25" class="input" style="width:230px;"/></span>
						<input type="button" value="上传" onclick="uploadImg(0);" class="btn" style="height:24px;"/><br/>
						宽<input type="text" id="zoomWidth0" value="139" size="5" class="input" style="width:40px"/> 
						高<input type="text" id="zoomHeight0" value="139" size="5" class="input" style="width:39px"/>
						<input type="checkbox" id="zoom0" value="true" checked="checked"/><label for="zoom0">自动压缩</label>
						<s:checkbox name="mark"  id="mark"/><label for="mark">添加水印</label>
						<input type="button" value="裁剪" onclick="imgCut(0);" class="btn" />
					</div>
				</td>
			</tr>
			<tr>
				<td class="label">用户名：</td>
				<td class="content">
					<s:textfield name="user.username" id="username" cssClass="input required focus" />
					<s:fielderror fieldName="user.username" theme="ccweb"/>
					<span class="red">*</span>
					<input id="btnCheckUsername" class="btn_check" type="button" onclick="ajaxCheck(this,'用户名','user_checkUsername.jspa','username')" value="检测用户名"/>
				</td>
			</tr>
			<tr>
				<td class="label">姓名：</td>
				<td class="content"><s:textfield name="user.ext.realname" id="realName" cssClass="input" /></td>
			</tr>
			<tr>
				<td class="label">地址：</td>
				<td class="content"><s:textfield name="user.ext.address" id="address" cssClass="input" /></td>
			</tr>
			<tr>
				<td class="label">邮编：</td>
				<td class="content"><s:textfield name="user.ext.zip" id="zip" cssClass="input" /></td>
			</tr>
			<tr>
				<td class="label">邮箱：</td>
				<td class="content"><s:textfield name="user.email" id="email" cssClass="input" /></td>
			</tr>
			<tr>
				<td class="label">手机：</td>
				<td class="content"><s:textfield name="user.ext.mobile" id="mobile" cssClass="input" /></td>
			</tr>
			<tr>
				<td class="label">电话：</td>
				<td class="content"><s:textfield name="user.ext.tel" id="tel" cssClass="input" /></td>
			</tr>
			<tr>
				<td class="label">传真：</td>
				<td class="content"><s:textfield name="user.ext.fax" id="fax" cssClass="input" /></td>
			</tr>
			<tr>
				<td class="label">生日：</td>
				<td class="content">
					<s:textfield name="birthday" cssClass="input Wdate" cssStyle="padding:2px 2px 1px 2px;"  onclick="WdatePicker({isShowWeek:true,dateFmt:'yyyy-MM-dd'})"/>
				</td>
			</tr>
			<tr>
				<td class="label">性别：</td>
				<td class="content"><s:radio list="#{'1':'男','2':'女','0':'保密'}" name="user.ext.gender" listKey="key" listValue="value"></s:radio></td>
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


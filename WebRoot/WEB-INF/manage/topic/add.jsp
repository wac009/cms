<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/inc/path.inc"%>
<%@include file="/inc/taglib.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<%@ include file="/inc/manage.inc"%>
  </head>
  
  <body>
  	<div class="navi"><span class="navi_b">当前位置:</span><span class="navi_h">专题管理 - 添加专题</span>
  		<span class="navi_f">
  			<input class="del btn" type="button" value="提交" onclick="$('#dataForm').submit();"/>
  			<a href='javascript:history.go(-1)' style="padding:0 5px;">返回</a>
  		</span>
  	</div>
	<div class="mainData">
	<jsp:include page="../common/imgUpload.jsp" />
	<div class="actInfo"><s:actionerror theme="ccweb"/><s:actionmessage theme="ccweb"/></div>
	<s:form action="topic_save_add" cssClass="vldform" id="dataForm">
		<table width="100%" class="table_edit">
			<tr>
				<td class="label">所属栏目：</td>
				<td class="content">
					<s:select name="topic.channel.id" list="channelList" listKey="id" listValue="selectTree" cssClass="select " />
					<span class="help">只能选择末级栏目</span>
				</td>
			</tr>
			<tr>
				<td class="label">使用模板：</td>
				<td class="content">
					<s:select name="topic.template.id" list="templateList" listKey="id" listValue="name" cssClass="select"></s:select>
					<span class="red">*</span>
				</td>
			</tr>
			<tr>
				<td class="label">专题名称：</td>
				<td class="content">
					<s:textfield name="topic.name" id="name" cssClass="input required focus" />
					<s:fielderror fieldName="topic.name" theme="ccweb"/>
					<span class="red">*</span>
				</td>
			</tr>
			<tr>
				<td class="label">简短名称：</td>
				<td class="content">
					<s:textfield name="topic.shortName" id="shortName" cssClass="input" />
					<s:fielderror fieldName="topic.shortName" theme="ccweb"/>
				</td>
			</tr>
			<tr>
				<td class="label">关键词：</td>
				<td class="content">
					<s:textfield name="topic.keywords" id="keywords" cssClass="input" />
					<s:fielderror fieldName="topic.keywords" theme="ccweb"/>
				</td>
			</tr>
			<tr id="titImgDiv">
				<td class="label">标题图：</td>
				<td class="content">
					<div class="float_left">
						图片路径<s:textfield name="topic.titleImg" id="uploadImgPath0" cssClass="input" /><input type="button" value="清空" onclick="clearImg(0);" class="btn"/><br/>
						本地上传<span id="ifc0"><input type="file" id="imgFile0" size="20" class="input"/></span>水印<s:checkbox name="mark"  value="true"/>
						<input type="button" value="上传" onclick="uploadImg(0);" class="btn"/><br/>
						图片大小：宽<input type="text" id="zoomWidth0" value="139" size="5" class="input" style="width:40px"/> 
						高<input type="text" id="zoomHeight0" value="139" size="5" class="input" style="width:40px"/>
						<input type="checkbox" id="zoom0" value="true" checked="checked"/>自动压缩图片
						<input type="button" value="裁剪" onclick="imgCut(0);" class="btn"/>
					</div>
					<div class="float_left" style="margin-left:15px;">
						<img id="preImg0" alt="预览区" onerror="javascript:this.src='<%=r_m%>/img/defaultpic.gif'" noResize="true" srcRoot="${web.uploadPath}" src="${web.uploadPath}<s:property value="content.titleImg"/>" style="width:100px;height:70px;background-color:#CCCCCC;border:1px solid #333"/>
					</div>
				</td>
			</tr>
			<tr id="contImgDiv" >
				<td class="label">内容图：</td>
				<td class="content">
					<div class="float_left">
						图片路径<s:textfield name="topic.contentImg" id="uploadImgPath1" cssClass="input" /><input type="button" value="清空" onclick="clearImg(1);" class="btn"/><br/>
						本地上传<span id="ifc1"><input type="file" id="imgFile1" size="20" class="input"/></span>水印<s:checkbox name="mark"  value="true"/>
						<input type="button" value="上传" onclick="uploadImg(1);" class="btn"/><br/>
						图片大小：宽<input type="text" id="zoomWidth1" value="310" size="5" class="input" style="width:40px"/> 
						高<input type="text" id="zoomHeight1" value="310" size="5" class="input" style="width:40px"/>
						<input type="checkbox" id="zoom1" value="true" checked="checked"/>自动压缩图片
						<input type="button" value="裁剪" onclick="imgCut(1);" class="btn"/>
					</div>
					<div class="float_left" style="margin-left:15px;">
					<img id="preImg1" alt="预览区" onerror="javascript:this.src='<%=r_m%>/img/defaultpic.gif'" noResize="true" srcRoot="${web.uploadPath}" src="${web.uploadPath}<s:property value="content.contentImg"/>" style="width:100px;height:70px;background-color:#CCCCCC;border:1px solid #333"/>
					</div>
				</td>
			</tr>
			<tr>
				<td class="label">描述：</td>
				<td class="content">
					<s:textarea name="topic.description" cssClass="input txtArea" />
					<s:fielderror fieldName="" theme="ccweb"/>
				</td>
			</tr>
			<tr>
				<td class="label">属性设置：</td>
				<td class="content">
					<s:checkbox name="topic.disabled" id="content_isComment"/><label for="content_isComment">禁用</label>
					<s:checkbox name="topic.recommend" id="content_isCommend"/><label for="content_isCommend">推荐</label>
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

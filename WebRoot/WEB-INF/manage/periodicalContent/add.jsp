<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/inc/path.inc"%>
<%@include file="/inc/taglib.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<%@ include file="/inc/manage.inc"%>
  </head>
  
  <body>
  	<div class="navi"><span class="navi_b">当前位置:</span><span class="navi_h">出版物管理 - 添加出版物</span></div>
	<div class="mainData">
	<jsp:include page="../common/imgUpload.jsp" />
	<div class="actInfo"><s:actionerror theme="ccweb"/><s:actionmessage theme="ccweb"/></div>
	<s:form action="publication_save_add" cssClass="vldform">
		<table width="100%" class="table_edit">
			<tr>
				<td class="label">所属站点：</td>
				<td class="content">
					<s:select name="publication.website.id" list="websiteList" listKey="id" listValue="selectTree" cssClass="select" ></s:select>
					<span class="help">指定出版物是属于哪个站点</span>
				</td>
			</tr>
			<tr>
				<td class="label">名称：</td>
				<td class="content">
					<s:textfield name="publication.name" cssClass="input required focus" />
					<s:fielderror fieldName="publication.name" theme="ccweb"/>
					<span class="red">*</span>
					<span class="help">出版物名称</span>
				</td>
			</tr>
			<tr>
				<td class="label">英文名称：</td>
				<td class="content">
					<s:textfield name="publication.enName" cssClass="input" />
					<s:fielderror fieldName="publication.enName" theme="ccweb"/>
					<span class="help">出版物英文名称</span>
				</td>
			</tr>
			<tr>
				<td class="label">描述：</td>
				<td class="content">
					<s:textarea name="publication.description" cssClass="input txtArea" />
					<span class="help">出版物描述</span>
				</td>
			</tr>
			<tr>
				<td class="label">显示图片：</td>
				<td class="content">
					<div class="float_right" style="margin-right:15px;">
					<img id="preImg0" alt="预览区" noResize="true" srcRoot="${web.uploadUrl}" style="width:80px;height:100px;background-color:#CCCCCC;border:1px solid #333"/>
					</div>
					图片路径<s:textfield name="publication.imgPath" id="uploadImgPath0" cssClass="input" /><input type="button" value="清空" onclick="clearImg(0);" class="btn"/><br/>
					本地上传<span id="ifc0"><input type="file" id="imgFile0" size="20" class="input"/></span>水印<s:checkbox name="mark"/>
					<input type="button" value="上传" onclick="uploadImg(0);" class="btn"/><br/>
					图片大小：宽<input type="text" id="zoomWidth0" value="139" size="5" class="input" style="width:40px"/> 
					高<input type="text" id="zoomHeight0" value="139" size="5" class="input" style="width:40px"/>
					自动压缩图片：<input type="checkbox" id="zoom0" />
					<input type="button" value="裁剪" onclick="imgCut(0);" class="btn"/>
				</td>
			</tr>
			<tr>
				<td class="label">创建时间：</td>
				<td class="content">
					<s:textfield name="createTime" cssClass="input Wdate" cssStyle="padding:2px 2px 0 2px;" 
					onclick="WdatePicker({isShowWeek:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
					<span class="help" style="margin-right:74px;">留空则为提交时间</span>
				</td>
			</tr>
			<tr>
				<td class="label">添加时间：</td>
				<td class="content">
					<s:textfield name="addTime" cssClass="input Wdate" cssStyle="padding:2px 2px 0 2px;" 
					onclick="WdatePicker({isShowWeek:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
					<span class="help" style="margin-right:74px;">留空则为提交时间</span>
				</td>
			</tr>
			<tr>
				<td class="label">属性设置：</td>
				<td class="content">
					<s:checkbox name="publication.isDisabled" id="publication_isdisabled"/><label for="publication_isdisabled">禁用</label>
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

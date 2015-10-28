<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/inc/path.inc"%>
<%@include file="/inc/taglib.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<%@ include file="/inc/manage.inc"%>
  </head>
  
  <body>
  	<div class="navi"><span class="navi_b">当前位置:</span><span class="navi_h">出版物管理 - 修改出版物</span>
  		<span class="navi_f">
  			<a href='javascript:history.go(-1)' style="padding:0 5px;">返回</a>
  		</span>
  	</div>
	<div class="mainData">
	<jsp:include page="../common/imgUpload.jsp" />
	<div class="actInfo"><s:actionerror theme="ccweb"/><s:actionmessage theme="ccweb"/></div>
	<s:form action="publication_update_edit" cssClass="vldform">
		<s:hidden name="id" />
		<s:hidden name="publication.id" />
		<table width="100%" class="table_edit">
			<tr>
				<td class="label">杂志类型：</td>
				<td class="content">
					<s:select name="publication.type.id" list="typeList" listKey="id" listValue="name" cssClass="select focus required" ></s:select>
					<a href="publicationType_add.jspa">添加类型</a>
				</td>
			</tr>
			<tr>
				<td class="label">名称：</td>
				<td class="content">
					<s:textfield name="publication.name" cssClass="input required" />
					<s:fielderror fieldName="publication.name" theme="ccweb"/>
					<span class="red">*</span>
				</td>
			</tr>
			<tr>
				<td class="label">英文名称：</td>
				<td class="content">
					<s:textfield name="publication.enName" cssClass="input" />
					<s:fielderror fieldName="publication.enName" theme="ccweb"/>
				</td>
			</tr>
			<tr>
				<td class="label">访问路径：</td>
				<td class="content">
					<s:textfield name="publication.path" cssClass="input" />
					<s:fielderror fieldName="publication.path" theme="ccweb"/>
				</td>
			</tr>
			<tr>
				<td class="label">描述：</td>
				<td class="content">
					<s:textarea name="publication.description" cssClass="input txtArea" />
				</td>
			</tr>
			<tr>
				<td class="label">杂志封面：</td>
				<td class="content">
					<div class="float_left">
						图片路径<s:textfield name="publication.imgPath" id="uploadImgPath0" cssClass="input" cssStyle="width:224px;" />
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
						<img id="preImg0" alt="预览区"  onerror="javascript:this.src='<%=r_m%>/img/defaultpic.gif'"  noResize="true" srcRoot="${web.uploadPath}" src="${web.uploadPath}<s:property value="publication.imgPath"/>" style="width:75px;height:75px;background-color:#CCCCCC;border:1px solid #333"/>
					</div>
				</td>
			</tr>
			<tr>
				<td class="label">杂志创建时间：</td>
				<td class="content">
					<s:textfield name="createTime" cssClass="input Wdate" cssStyle="padding:2px 2px 0 2px;"  onclick="WdatePicker({isShowWeek:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
					<span class="help" style="margin-right:74px;">留空则为当前时间</span>
				</td>
			</tr>
			<tr>
				<td class="label">添加到网站时间：</td>
				<td class="content">
					<s:textfield name="addTime" cssClass="input Wdate" cssStyle="padding:2px 2px 0 2px;"  onclick="WdatePicker({isShowWeek:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
					<span class="help" style="margin-right:74px;">留空则为当前时间</span>
				</td>
			</tr>
			<tr>
				<td class="label">属性设置：</td>
				<td class="content">
					<s:checkbox name="publication.disabled" id="publication_isdisabled"/><label for="publication_isdisabled">禁用</label>
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
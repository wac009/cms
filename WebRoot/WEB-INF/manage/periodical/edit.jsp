<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/inc/path.inc"%>
<%@include file="/inc/taglib.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<%@ include file="/inc/manage.inc"%>
  </head>
  
  <body>
  	<div class="navi"><span class="navi_b">当前位置:</span><span class="navi_h">
		<a href='publication_list.jspa' style="padding:0;">刊物管理</a> - 
		<a href='periodical_list.jspa?publicationId=${publicationId}' style="padding:0;"><s:property value="%{publication.name}"/></a> - 修改</span>
		<span class="navi_f">
  			<a href='javascript:history.go(-1)' style="padding:0 5px;">返回</a>
  		</span>
	</div>
	<div class="mainData">
	<jsp:include page="../common/imgUpload.jsp" />
	<jsp:include page="../common/attachmentUpload.jsp" />
	<div class="actInfo"><s:actionerror theme="ccweb"/><s:actionmessage theme="ccweb"/></div>
	<s:form action="periodical_update_edit" cssClass="vldform">
		<s:hidden name="id" />
		<s:hidden name="periodical.id" />
		<table width="100%" class="table_edit">
			<tr>
				<td class="label">所属刊物：</td>
				<td class="content">
					<s:if test="publication!=null"><s:property value="publication.name"/><s:hidden name="periodical.publication.id"/></s:if>
					<s:else><s:select name="periodical.publication.id" list="publicationList" listKey="id" listValue="name" cssClass="select" 
							id="publicationId" onchange="commCtgs($(this).val())"></s:select></s:else>
				</td>
			</tr>
			<tr>
				<td class="label">年度：</td>
				<td class="content">
					<s:textfield name="periodical.year" cssClass="input required focus" />
					<s:fielderror fieldName="periodical.year" theme="ccweb"/>
					<span class="red">*</span>
					<span class="help">此期刊所属年份</span>
				</td>
			</tr>
			<tr>
				<td class="label">年度期数：</td>
				<td class="content">
					<s:textfield name="periodical.yearPeriod" cssClass="input" />
					<s:fielderror fieldName="periodical.yearPeriod" theme="ccweb"/>
					<span class="help">此期刊的年度期数</span>
				</td>
			</tr>
			<tr>
				<td class="label">总期数：</td>
				<td class="content">
					<s:textfield name="periodical.totalPeriod" cssClass="input" />
					<span class="help">此期刊在刊物中的总期数</span>
				</td>
			</tr>
			<tr>
				<td class="label">封面图片：</td>
				<td class="content">
					<div class="float_left">
						图片路径<s:textfield name="periodical.imgPath" id="uploadImgPath0" cssClass="input" cssStyle="width:224px;" />
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
						<img id="preImg0" alt="预览区" noResize="true" onerror="javascript:this.src='<%=r_m%>/img/defaultpic.gif'"  srcRoot="${web.uploadPath}" src="${web.uploadPath}<s:property value="publication.imgPath"/>" style="width:75px;height:75px;background-color:#CCCCCC;border:1px solid #333"/>
					</div>
				</td>
			</tr>
			<tr>
				<td class="label">电子版附件：</td>
				<td class="content" >
					<table>
						<tr class="noborder">
							<td style="border:none;padding-left:5px;width:210px;">附件名称</td>
							<td style="border:none;padding-left:5px;width:210px;">附件地址</td>
							<td style="border:none;padding-left:5px;width:300px;">上传</td>
						</tr>
					</table>
					<table>
							<tr id="attachTr0">
								<td style="border:none;border-top:1px solid #ccc;"><input type="text" id="attachmentNames0" name="attachmentName" class="input"/></td>
								<td style="border:none;border-top:1px solid #ccc;"><input type="text" id="attachmentPaths0" name="attachmentPath" class="input"/></td>
								<td style="border:none;border-top:1px solid #ccc;">
									<span id="afc0"><input type="file" id="attachmentFile0" size="12" class="input" style="width:220px"/></span>
									<input type="button" value="上传" onclick="uploadAttachment(0);" class="btn"/>
									<input type="hidden" id="attachmentFilenames0" name="attachmentFilename"/>
								</td>
							</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td class="label">本期栏目管理：</td>
				<td class="content">
					<s:checkbox name="copyPrevCtg" id="copyprevctg"/><label for="copyprevctg">复制上期栏目</label><br/>
					选择通用栏目：<span id="commCtgs"><s:if test="commCtgs==null||commCtgs.size==0">&lt; 请选择刊物 &gt; </s:if>
					<s:else><s:checkboxlist name="catalogs" list="commCtgs" listKey="key" listValue="value"/></s:else></span>
				</td>
			</tr>
			<tr>
				<td class="label">描述：</td>
				<td class="content">
					<s:textarea name="periodical.description" cssClass="input txtArea" />
					<span class="help">期刊描述</span>
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
					<s:checkbox name="periodical.disabled" id="periodical_isdisabled"/><label for="periodical_isdisabled">禁用</label>
					<s:checkbox name="periodical.current" id="periodical_iscurrent"/><label for="periodical_iscurrent">是当前期</label>
					<s:checkbox name="periodical.lock" id="periodical_islock"/><label for="periodical_islock">锁定</label>
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

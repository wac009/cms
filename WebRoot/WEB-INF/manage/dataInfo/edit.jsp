<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/inc/path.inc"%>
<%@include file="/inc/taglib.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<%@ include file="/inc/manage.inc"%>
  </head>
  
  <body>
  	<div class="navi"><span class="navi_b">当前位置:</span><span class="navi_h">资料文件管理 - 修改</span>
  		<span class="navi_f">
  			<a href='javascript:history.go(-1)' style="padding:0 5px;">返回</a>
  		</span>
  	</div>
	<div class="mainData">
	<jsp:include page="../common/imgUpload.jsp" />
	<jsp:include page="../common/attachmentUpload.jsp" />
	<jsp:include page="../common/mediaUpload.jsp" />
	<div class="actInfo"><s:actionerror theme="ccweb"/><s:actionmessage theme="ccweb"/></div>
	<s:form action="dataInfo_update_edit" cssClass="vldform">
		<s:hidden name="id" />
		<s:hidden name="dataInfo.id" />
		<s:hidden name="pageNo"/>
		<table width="100%" class="table_edit">
			<tr>
				<td class="label">所属类型：</td>
				<td class="content">
					<s:select name="dataInfo.type.id" list="typeList" listKey="id" listValue="name" cssClass="select" ></s:select>
					<s:fielderror fieldName="dataInfo.type.id" theme="ccweb"/>
					<span class="red">*</span>
				</td>
			</tr>
			<tr>
				<td class="label">所属目录：</td>
				<td class="content">
					<s:select name="dataInfo.catalog.id" list="catalogList" listKey="id" listValue="selectTree" cssClass="select" ></s:select>
					<s:fielderror fieldName="dataInfo.catalog.id" theme="ccweb"/>
					<span class="red">*</span>
				</td>
			</tr>
			<tr>
				<td class="label">名称：</td>
				<td class="content">
					<s:textfield name="dataInfo.name" id="name" cssClass="input required focus" />
					<s:fielderror fieldName="dataInfo.name" theme="ccweb"/>
					<span class="red">*</span>
				</td>
			</tr>
			<tr>
				<td class="label">描述：</td>
				<td class="content">
					<s:textarea name="dataInfo.description" cssClass="input txtArea" />
					<s:fielderror fieldName="dataInfo.description" theme="ccweb"/>
				</td>
			</tr>
			<tr>
				<td class="label">附件：</td>
				<td class="content" >
					<table>
						<tr class="noborder">
							<td style="border:none;width:60px;"><input type="button" onclick="addAttachLine();" value="增加" class="btn"/></td>
							<td style="border:none;padding-left:5px;width:210px;">附件名称</td>
							<td style="border:none;padding-left:5px;width:210px;">附件地址</td>
							<td style="border:none;padding-left:5px;width:300px;">上传</td>
						</tr>
					</table>
					<script type="text/javascript">
						var aindex = 0;
					</script>
					<s:iterator id="attach" value="%{dataInfo.attachments}" status="stat">
						<table>
							<tr id="attachTr<s:property value="#stat.index"/>" class="noborder">
								<td style="border:none;border-top:1px solid #ccc;width:60px;""><input type="button" onclick="$('#attachTr<s:property value="#stat.index"/>').remove();" value="删除" class="btn"/></td>
								<td style="border:none;border-top:1px solid #ccc;"><input type="text" value="<s:property value="#attach.name"/>" id="attachmentNames<s:property value="#stat.index"/>" name="attachmentNames" class="input"/></td>
								<td style="border:none;border-top:1px solid #ccc;"><input type="text" value="<s:property value="#attach.path"/>" id="attachmentPaths<s:property value="#stat.index"/>" name="attachmentPaths" class="input"/></td>
								<td style="border:none;border-top:1px solid #ccc;">
									<s:hidden name="attachments.id" value="%{#attach.id}"/>
								</td>
							</tr>
						</table>
						<script type="text/javascript">
							var aindex = aindex+1;
						</script>
					</s:iterator>
					
					<div id="attachBefore" style="clear:both"></div>
					<textarea id="attachTable" style="display:none">
						<table>
						<tr id="attachTr{0}">
							<td style="border:none;border-top:1px solid #ccc;width:60px;""><input type="button" onclick="$('#attachTr{0}').remove();" value="删除" class="btn"/></td>
							<td style="border:none;border-top:1px solid #ccc;"><input type="text" id="attachmentNames{0}" name="attachmentNames" class="input"/></td>
							<td style="border:none;border-top:1px solid #ccc;"><input type="text" id="attachmentPaths{0}" name="attachmentPaths" class="input"/></td>
							<td style="border:none;border-top:1px solid #ccc;">
								<span id="afc{0}"><input type="file" id="attachmentFile{0}" size="12" class="input" style="width:220px"/></span>
								<input type="button" value="上传" onclick="uploadAttachment({0});" class="btn"/>
								<input type="hidden" id="attachmentFilenames{0}" name="attachmentFilenames"/>
								<s:hidden name="attachments.id" id="attachmentId{0}"/>
							</td>
						</tr>
						</table>
					</textarea>
					<script type="text/javascript">
						var attachIndex = aindex;
						var attachTpl = $.format($("#attachTable").val());
						function addAttachLine() {
							$('#attachBefore').append(attachTpl(attachIndex++));
						}
					</script>
				</td>
			</tr>
			<tr>
				<td class="label">属性设置：</td>
				<td class="content">
					<s:checkbox name="dataInfo.isLock" id="dataInfo_isLock"/><label for="dataInfo_isLock">锁定</label>
					<s:checkbox name="dataInfo.isLink" id="dataInfo_isLink"/><label for="dataInfo_isLink">链接</label>
					<s:checkbox name="dataInfo.hasTxt" id="dataInfo_hasTxt" onclick="toggleByClass('dataInfoTxt');"/><label for="dataInfo_hasTxt">内容</label>
				</td>
			</tr>
			<tr class="dataInfoTxt">
				<td class="content" colspan="2">
					<fck:editor instanceName="dataInfo.txt.txt" height="380px;">
						<jsp:attribute name="value">
							<s:property value="%{dataInfo.txt.txt}" escape="false" /> 
						</jsp:attribute>
					</fck:editor>
				</td>
			</tr>
			<s:if test="!dataInfo.hasTxt">
				<script>
					$().ready(function() {
						$(".dataInfoTxt").addClass("none");//隐藏详细信息选项
					});
				</script>
			</s:if>
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


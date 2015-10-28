<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/inc/path.inc"%>
<%@include file="/inc/taglib.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<%@ include file="/inc/manage.inc"%>
  </head>
  
  <body>
  	<div class="navi"><span class="navi_b">当前位置:</span><span class="navi_h"><a href="#">模板管理 - 创建模板</a></span></div>
	<div class="mainData">
	<jsp:include page="importTplInc.jsp" />
	<jsp:include page="../common/imgUpload.jsp" />
	<div class="actInfo"><s:actionerror theme="ccweb"/><s:actionmessage theme="ccweb"/></div>
	<s:form action="template_save_add" cssClass="vldform">
		<table width="100%" class="table_edit">
			<tr>
				<td class="label">模板名：</td>
				<td class="content">
					<s:textfield name="template.name" id="name" cssClass="input required focus" />
					<s:fielderror fieldName="template.name" theme="ccweb"/>
					<span class="red">*</span>
				</td>
			</tr>
			<tr>
				<td class="label">模板路径：</td>
				<td class="content">
					<s:textfield name="template.resPath" id="resPath" cssClass="input required" />
					<s:fielderror fieldName="template.resPath" theme="ccweb"/>
					<span class="red">*</span>
					<input id="resPathLink" type="button" class="btn" value="选择模板路径"/>
					导入模板包
					<span id="tfc"><input type="file" id="tplFile" name="tplsFile" size="30" class="input" style="width:260px;"/></span>
					<input type="button" value="导入" onclick="importTpl();" class="btn"/>
					<span class="help red">必须使用ZIP格式的压缩包</span>
				</td>
			</tr>
			<tr>
				<td class="label">预览图片：</td>
				<td class="content">
					<div class="float_left">
						图片路径<s:textfield name="template.imgPath" id="uploadImgPath0" cssClass="input" /><input type="button" value="清空" onclick="clearImg(0);" class="btn"/><br/>
						本地上传<span id="ifc0"><input type="file" id="imgFile0" size="20" class="input"/></span>
					<input type="button" value="上传" onclick="uploadImg(0);" class="btn"/><br/>
					</div>
					<div class="float_left" style="margin-left:15px;">
						<img id="preImg0" onerror="javascript:this.src='<%=r_m%>/img/defaultpic.gif'"  alt="预览区" noResize="true" srcRoot="${web.uploadPath}" style="width:100px;height:100px;background-color:#CCCCCC;border:1px solid #333"/>
					</div>
				</td>
			</tr>
			<tr>
				<td class="label">作者：</td>
				<td class="content">
					<s:textfield name="template.author" id="author" cssClass="input" />
				</td>
			</tr>
			<tr>
				<td class="label">版本：</td>
				<td class="content">
					<s:textfield name="template.version" id="version" cssClass="input" />
				</td>
			</tr>
			<tr>
				<td class="label">描述：</td>
				<td class="content">
					<s:textarea name="template.description" id="description" cssClass="input txtArea" ></s:textarea>
				</td>
			</tr>
			<tr>
				<td class="label">其他设置：</td>
				<td class="content">
					<s:checkbox name="template.isDisabled" id="template_isdisable"/><label for="template_isdisable">禁用</label>
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
			<tr>
				<td class="content" colspan="2">
				导入提示：
					<p class="help" style="padding-left:70px;">
						1、必须使用ZIP格式的压缩包<br/>
						2、包内文件夹组织样式:
						\${r_t}内放的是模板资源文件如样式表、图片等;\${res}内放的是网站资源文件如上传的图片视频等;\${page}内放的是页面文件;
					</p>
					<p class="help" style="padding-left:70px;">
						例：default<br/>
						<span style="padding-left:40px;">|-\${r_t}</span><br/>
						<span style="padding-left:60px;">|-css</span><br/>
						<span style="padding-left:60px;">|-img</span><br/>
						<span style="padding-left:40px;">|-\${res}</span><br/>
						<span style="padding-left:60px;">|-images</span><br/>
						<span style="padding-left:60px;">|-upload</span><br/>
						<span style="padding-left:40px;">|-\${page}</span><br/>
						<span style="padding-left:60px;">|-index.html</span><br/>
						<span style="padding-left:60px;">|-include</span><br/>
					</p>
					<p class="help" style="padding-left:70px;">
						3、可先导出模板参考
					</p>
				</td>
			</tr>
		</table>
	</s:form>
	</div>
	<div id="resPathDialog" title="选择模板路径" style="display:none;text-align:left;">
		<div id="resPathSelector" >
			<s:if test="tplDirList==null">【没有可选择的模板】</s:if>
			<s:else>
				<ul>
				<s:iterator id="t" value="%{tplDirList}" status="status">
					<li style="height:30px;line-height:30px;" onmouseover="Pn.Tree.lineOver(this)" onmouseout="Pn.Tree.lineOut(this)" >
						<input type="radio" id="tpl-<s:property value="#status.index"/>" name="resPath" value="<s:property value="#t.name"/>">
						<label for="tpl-<s:property value="#status.index"/>"><s:property value="#t.name"/></label>
					</li>
				</s:iterator>
				</ul>
			</s:else>
		</div>
	</div>
  </body>
</html>
<script type="text/javascript">
	$(function() {
		//选择模板路径
		$("#resPathDialog").dialog({
			autoOpen: false,
			modal: true,
			width: 280,
			height: 400,
			position: ["center",20],
			buttons: {
				"OK": function() {
					$("#resPathSelector input[name='resPath']:checked").each(function(){
						fillResPath(this);
						$(this).removeAttr("checked");
					});
					$(this).dialog("close");
				}
			}
		});
		$('#resPathLink').click(function(){
			$('#resPathDialog').dialog('open');
			return false;
		});
	});
	function fillResPath(respath) {
		$("#resPath").val($(respath).val());
	}
</script>
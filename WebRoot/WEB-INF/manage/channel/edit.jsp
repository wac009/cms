<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/inc/path.inc"%>
<%@include file="/inc/taglib.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<%@ include file="/inc/manage.inc"%>
  </head>
  
  <body>
  	<div class="navi"><span class="navi_b">当前位置:</span><span class="navi_h">栏目管理 - 栏目修改</span>
  		<span class="navi_f">
  			<input class="del btn" type="button" value="提交" onclick="$('#dataForm').submit();"/>
  			<a href='javascript:history.go(-1)' style="padding:0 5px;">返回</a>
  		</span>
  	</div>
	<div class="mainData">
	<jsp:include page="../common/imgUpload.jsp" />
	<div class="actInfo"><s:actionerror theme="ccweb"/><s:actionmessage theme="ccweb"/></div> 
	<s:form action="channel_update_edit" cssClass="vldform">
		<s:hidden name="id" />
		<s:hidden name="channel.id" />
		<s:hidden name="channel.ext.id" />
		<s:hidden name="channel.channelTxt.id" />
		<table width="100%" class="table_edit">
			<tr>
				<td class="label">上级栏目：</td>
				<td class="content">
					<s:if test="parent!=null"><s:property value="parent.name"/><s:hidden name="channel.parent.id" value="%{parent.id}"/></s:if>
					<s:else><s:select name="channel.parent.id" list="channelList" listKey="id" listValue="selectTree" cssClass="select " /></s:else>
					模型：
					<s:if test="model!=null"><s:property value="model.name"/><s:hidden name="channel.model.id" value="%{model.id}" /></s:if>
					<s:else><s:select name="channel.model.id" list="models" listKey="id" listValue="name" cssClass="select" ></s:select></s:else>
				</td>   
			</tr>
			<tr>
				<td class="label">栏目名称：</td>
				<td class="content">
					<s:textfield name="channel.ext.name" cssClass="input required focus" />
					<s:fielderror fieldName="channel.ext.name" theme="ccweb"/>
					<span class="red">*</span>
					访问路径： 
					<s:textfield name="channel.path" cssClass="input" />
					<s:fielderror fieldName="channel.path" theme="ccweb"/>
					<span class="red">*</span>
					<span class="help">使用栏目的英文名或拼音来表示访问路径</span>
				</td>
			</tr>
			<tr>
				<td class="label">meta标题：</td>
				<td class="content">
					<s:textfield name="channel.ext.title" cssClass="input" />
					<span class="help">搜索引擎优化title部分</span>
					meta关键字：
					<s:textfield name="channel.ext.keywords" cssClass="input" />
					<span class="help">搜索引擎优化keywords部分</span>
				</td>
			</tr>
			<tr>
				<td class="label">meta描述：</td>
				<td class="content">
					<s:textarea name="channel.ext.description" cssClass="input txtArea" />
					<span class="help">搜索引擎优化description部分</span>
				</td>
			</tr>
			<tr>
				<td class="label">外部链接：</td>
				<td class="content">
					<s:textfield name="channel.ext.link" id="channel.ext.link" cssClass="input" cssStyle="width:400px;"/>
					<span class="help">为栏目指定一个超链接，访问此栏目时直接访问此超链接</span>
				</td>
			</tr>
			<tr>
				<td class="label">选择模板：</td>
				<td class="content">
					栏目模板：
					<s:textfield name="channel.ext.tplChannel" id="tplChannelPath" cssClass="input" />
					<s:fielderror fieldName="channel.ext.tplChannel" theme="ccweb"/>
					<input id="tplChannelLink" type="button" class="btn" value="选择栏目模板"/>
					内容模板：
					<s:textfield name="channel.ext.tplContent" id="tplContentPath" cssClass="input" />
					<s:fielderror fieldName="channel.ext.tplContent" theme="ccweb"/>
					<input id="tplContentLink" type="button" class="btn" value="选择内容模板"/>
					<span class="help">留空则使用系统默认</span>
				</td>
			</tr>
			<tr>
				<td class="label">栏目静态化：</td>
				<td class="content">
					<s:checkbox name="channel.ext.staticChannel" id="staticChannel"/><label for="staticChannel">开启</label>
					<s:textfield name="channel.ext.channelRule" id="channelRule" cssClass="input" />
					<s:checkbox name="channel.ext.accessByDir" id="accessByDir"/><label for="accessByDir">使用目录访问</label>
					<s:checkbox name="channel.ext.listChild" id="listChild"/><label for="listChild">开启子栏目列表</label>
					每页记录数<s:textfield name="channel.ext.pageSize" id="pageSize" cssClass="input"  cssStyle="width:70px;" value="20"/>
				</td>
			</tr>
			<tr>
				<td class="label">内容静态化：</td>
				<td class="content">
					<s:checkbox name="channel.ext.staticContent" id="staticContent"/><label for="staticContent">开启</label>
					<s:textfield name="channel.ext.contentRule" id="contentRule" cssClass="input" />
				</td>
			</tr>
			<tr>
				<td class="label">终审级别：</td>
				<td class="content">
					<s:textfield name="channel.ext.finalStep" id="finalStep" cssClass="input" />
					<span class="help">留空则继承上级栏目设置</span>
					审核后<s:select  name="channel.ext.afterCheck" list="#{'':'默认','1':'不能修改删除','2':'修改后退回','3':'修改后不变'}" listKey="key" listValue="value" ></s:select>
				</td>
			</tr>
			<tr>
				<td class="label">权限：</td>
				<td class="content">
					浏览权限：<s:checkboxlist list="groups" listKey="id" listValue="name" name="viewGroups.id" value="viewGroups.{id}" />
					投稿权限：<s:checkboxlist list="groups" listKey="id" listValue="name" name="contriGroups.id" value="contriGroups.{id}" />
				</td>
			</tr>
			<tr>
				<td class="label">分配到管理员：</td>
				<td class="content">
					<s:checkboxlist list="userList" listKey="id" listValue="username" name="users.id" value="users.{id}" />
				</td>
			</tr>
			<tr>
				<td class="label">属性设置：</td>
				<td class="content">
					<s:checkbox name="channel.ext.disabled" id="channel_isdisabled" /><label for="channel_isdisabled">关闭</label>
					<s:checkbox name="channel.ext.display" id="channel_isdisplay" /><label for="channel_isdisplay">显示</label>
					<s:checkbox name="channel.ext.blank" id="channel_blank" /><label for="channel_blank">新窗口打开</label>
					评论：<s:radio name="channel.ext.commentControl"  list="#{'0':'游客评论','1':'登录评论 ','2':'关闭评论 '}" listKey="key" listValue="value"/>
					顶踩：<s:radio name="channel.ext.allowUpdown"   list="#{'true':'启用','false':'禁用 '}" listKey="key" listValue="value"  />
				</td>
			</tr>
			<tr>
				<td class="label">文档标题图：</td>
				<td class="content">
					<s:checkbox name="channel.ext.hasTitleImg" id="hasTitleImg" value="false" onclick="$('#hasTitleImg').val(this.checked);$('#ti').toggle(this.checked);"/><label for="hasTitleImg">有</label>
					<div id="ti" class="none">
						宽<s:textfield name="channel.ext.titleImgWidth" id="titleImgWidth" cssClass="input" value="139"  style="width:70px"/>px
						高<s:textfield name="channel.ext.titleImgHeight" id="titleImgHeight" cssClass="input" value="139"  style="width:70px"/>px
						<div id="titImgDiv" >
							<div class="float_left">
								图片路径<s:textfield name="channel.ext.titleImg" id="uploadImgPath0" cssClass="input" cssStyle="width:224px;" />
								<input type="button" value="清空" onclick="clearImg(0);" class="btn" style="height:24px;"/><br/>
								本地上传<span id="ifc0"><input type="file" id="imgFile0" size="25" class="input" style="width:230px;"/></span>
								<input type="button" value="上传" onclick="uploadImg(0);" class="btn" style="height:24px;"/><br/>
								宽<input type="text" id="zoomWidth0" value="139" size="5" class="input" style="width:40px"/> 
								高<input type="text" id="zoomHeight0" value="139" size="5" class="input" style="width:39px"/>
								<input type="checkbox" id="zoom0" value="false" /><label for="zoom0">自动压缩</label>
								<s:checkbox name="mark"  id="mark"/><label for="mark">添加水印</label>
								<input type="button" value="裁剪" onclick="imgCut(0);" class="btn" />
							</div>
							<div class="float_left" style="margin-left:15px;">
								<img id="preImg0" alt="预览区" onerror="javascript:this.src='<%=r_m%>/img/defaultpic.gif'"  noResize="true" srcRoot="${web.uploadPath}" src="${web.uploadPath}<s:property value="channel.ext.titleImg"/>" style="width:75px;height:75px;background-color:#CCCCCC;border:1px solid #333"/>
							</div>
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td class="label">文档内容图：</td>
				<td class="content">
					<s:checkbox name="channel.ext.hasContentImg" id="hasContentImg" value="false" onclick="$('#hasContentImg').val(this.checked);$('#ci').toggle(this.checked);"/><label for="hasContentImg">有</label>
					<div id="ci" class="none">
						宽<s:textfield name="channel.ext.contentImgWidth" id="contentImgWidth" cssClass="input" value="310"  style="width:70px"/>px
						高<s:textfield name="channel.ext.contentImgHeight" id="contentImgHeight" cssClass="input" value="310"  style="width:70px"/>px
						<div id="conImgDiv" >
							<div class="float_left">
								图片路径<s:textfield name="channel.ext.contentImg" id="uploadImgPath1" cssClass="input" /><input type="button" value="清空" onclick="clearImg(1);" class="btn"/><br/>
								本地上传<span id="ifc1"><input type="file" id="imgFile1" size="20" class="input"/></span>水印<s:checkbox name="mark" value="true"/>
								<input type="button" value="上传" onclick="uploadImg(1);" class="btn"/><br/>
								图片大小：宽<input type="text" id="zoomWidth1" value="310" size="5" class="input" style="width:40px"/> 
								高<input type="text" id="zoomHeight1" value="310" size="5" class="input" style="width:40px"/>
								自动压缩图片：<input type="checkbox" id="zoom1" value="true" checked="checked"/>
								<input type="button" value="裁剪" onclick="imgCut(1);" class="btn"/>
							</div>
							<div class="float_left" style="margin-left:15px;">
								<img id="preImg1" alt="预览区" onerror="javascript:this.src='<%=r_m%>/img/defaultpic.gif'"  noResize="true" srcRoot="${web.uploadPath}" src="${web.uploadPath}<s:property value="channel.ext.contentImg"/>" style="width:75px;height:75px;background-color:#CCCCCC;border:1px solid #333"/>
							</div>
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td class="label">内容：</td>
				<td class="content" >
					<fck:editor instanceName="channel.channelTxt.txt" height="300px;">
						<jsp:attribute name="value">
							<s:property value="%{channel.channelTxt.txt}" escape="false" /> 
						</jsp:attribute>
					</fck:editor>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<div id="btn" class="btnDiv_fa">
						<input type="submit" value=" 提 交 " class="btn_fa" />	
						<input type="reset" value=" 重 置 " class="btn_fa" />
					</div>
				</td>
			</tr>
		</table>
	</s:form>
	</div>
	<div id="tplChannelPathDialog" title="选择栏目模板" style="display:none;text-align:left;">
		<div id="tplChannelPathSelector" >
			<s:if test="tplFileRoot==null">【没有可选择的模板文件】</s:if>
			<s:else>
				<s:component template="tree.ftl" theme="ccweb">
					<s:param name="root" value="%{tplFileRoot}"/>
					<s:param name="treeId" value="'t0'"/>
					<s:param name="isUrl" value="false"/>
					<s:param name="isRadio" value="true"/>
					<s:param name="isFile" value="true"/>
					<s:param name="showDeep" value="1"/>
				</s:component>
			</s:else>
		</div>
	</div>
	<div id="tplContentPathDialog" title="选择内容模板" style="display:none;text-align:left;">
		<div id="tplContentPathSelector" >
			<s:if test="tplFileRoot==null">【没有可选择的模板文件】</s:if>
			<s:else>
				<s:component template="tree.ftl" theme="ccweb">
					<s:param name="root" value="%{tplFileRoot}"/>
					<s:param name="treeId" value="'t1'"/>
					<s:param name="isUrl" value="false"/>
					<s:param name="isRadio" value="true"/>
					<s:param name="isFile" value="true"/>
					<s:param name="showDeep" value="1"/>
				</s:component>
			</s:else>
		</div>
	</div>
  </body>
</html>
<script type="text/javascript">
	$(function() {
		//选择栏目模板
		$("#tplChannelPathDialog").dialog({
			autoOpen: false,
			modal: true,
			width: 280,
			height: 400,
			position: ["center",20],
			buttons: {
				"OK": function() {
					$("#tplChannelPathSelector input[name='id']:checked").each(function(){
						$("#tplChannelPath").val($(this).attr("relPath"));
						$(this).removeAttr("checked");
					});
					$(this).dialog("close");
				}
			}
		});
		$('#tplChannelLink').click(function(){
			$('#tplChannelPathDialog').dialog('open');
			return false;
		});
		//选择内容模板
		$("#tplContentPathDialog").dialog({
			autoOpen: false,
			modal: true,
			width: 280,
			height: 400,
			position: ["center",20],
			buttons: {
				"OK": function() {
					$("#tplContentPathSelector input[name='id']:checked").each(function(){
						$("#tplContentPath").val($(this).attr("relPath"));
						$(this).removeAttr("checked");
					});
					$(this).dialog("close");
				}
			}
		});
		$('#tplContentLink').click(function(){
			$('#tplContentPathDialog').dialog('open');
			return false;
		});
	});
</script>

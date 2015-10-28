<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/inc/path.inc"%>
<%@include file="/inc/taglib.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<%@ include file="/inc/manage.inc"%>
  </head>
  
  <body>
  	<div class="navi"><span class="navi_b">当前位置:</span><span class="navi_h">内容管理 - 添加新闻</span>
  		<span class="navi_f">
  			<input class="del btn" type="button" value="提交" onclick="$('#dataForm').submit();"/>
  			<a href='javascript:history.go(-1)' style="padding:0 5px;">返回</a>
  		</span>
  	</div>
	<div class="mainData">
	<jsp:include page="../common/imgUpload.jsp" />
	<jsp:include page="../common/attachmentUpload.jsp" />
	<jsp:include page="../common/mediaUpload.jsp" />
	<div class="actInfo"><s:actionerror theme="ccweb"/><s:actionmessage theme="ccweb"/></div>
	<s:form action="content_save_add" cssClass="vldform" id="dataForm">
		<table width="100%" class="table_edit">
			<tr>
				<td class="label">所属栏目：</td>
				<td class="content">
					<s:select name="content.channel.id" list="channelList" listKey="id" listValue="selectTree" cssClass="select required focus" /><span class="red">*</span>
					<input id="channelsLink" type="button" value="选择副栏目" class="btn" style="padding-left:10px;padding-right:10px;">
					<div id="channelsContainer"></div>
				</td>
			</tr>
			<tr>
				<td class="label">文章标题：</td>
				<td class="content">
					<s:textfield name="content.contentExt.title" id="title" cssClass="input required ruler" cssStyle="width:500px;"/>
					<s:fielderror fieldName="content.contentExt.title" theme="ccweb"/><span class="red">*</span>
					&nbsp;&nbsp;&nbsp;标题颜色<s:textfield name="content.contentExt.titleColor" id="titleColor" cssClass="input" cssStyle="width:80px;border-right:0;margin-right:0;"/>
					&nbsp;加粗显示<s:checkbox name="content.contentExt.bold"/>
				</td>
			</tr>
			<tr>
				<td class="label">副标题：</td>
				<td class="content">
					<s:textfield name="content.contentExt.subtitle" cssClass="input" cssStyle="width:500px;"/>
				</td>
			</tr>
			<tr>
				<td class="label">外部链接：</td>
				<td class="content">
					<s:textfield name="content.contentExt.link" id="link" cssClass="input" cssStyle="width:400px;"/>
					<span class="help">若设置此项，文章标题将会以超链接形式链接到此处。以下设置的内容将失效。</span>
				</td>
			</tr>
			<tr>
				<td class="label">内容摘要：</td>
				<td class="content">
					<s:textarea name="content.contentExt.description" cssClass="input txtArea" />
					<span class="help">文字请控制在1000字以内</span>
				</td>
			</tr>
			<tr>
				<td class="label">来源：</td>
				<td class="content">
					<s:textfield name="content.contentExt.origin" cssClass="input" />
					来源URL：<s:textfield name="content.contentExt.originUrl" cssClass="input" />
				</td>
			</tr>
			<tr>
				<td class="label">编辑：</td>
				<td class="content">
					<s:textfield name="content.contentExt.editorName" value="%{user.realname}" cssClass="input" />
					<span class="help" style="margin-right:76px;">默认是登陆用户的名称</span>
					作者：<s:textfield name="content.contentExt.author" cssClass="input" />
				</td>
			</tr>
			<tr>
				<td class="label">发布时间：</td>
				<td class="content">
					<s:textfield name="releaseTime" cssClass="input Wdate" cssStyle="padding:2px 2px 1px 2px;" 
					onclick="WdatePicker({isShowWeek:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
					<span class="help" style="margin-right:74px;">留空则为提交时间</span>
					点击次数：<s:textfield name="content.contentCount.views" cssClass="input" />
				</td>
			</tr>
			<tr>
				<td class="label">文章类型：</td>
				<td class="content">
					<s:select id="contentTypeId" onchange="chgType($(this).val());" list="contentTypeList" name="content.contentType.id" listKey="id" listValue="name" cssStyle="width:100px;border:1px solid #CCC;"/>
					<span class="red" id="contentTypeError"></span>
					&nbsp;&nbsp;内容模板：<s:textfield name="content.contentExt.tplContent" id="tplContent" cssClass="input" />
					<input id="tplContentLink" type="button" class="btn" value="选择模板"/>
					<span class="help">留空则使用所属栏目设置的内容显示模板</span>
				</td>
			</tr>
			<tr id="titImgDiv">
				<td class="label">标题图：</td>
				<td class="content">
					<div class="float_left">
						图片路径<s:textfield name="content.contentExt.titleImg" id="uploadImgPath0" cssClass="input" /><input type="button" value="清空" onclick="clearImg(0);" class="btn"/><br/>
						本地上传<span id="ifc0"><input type="file" id="imgFile0" size="20" class="input"/></span>水印<s:checkbox name="mark"  value="true"/>
						<input type="button" value="上传" onclick="uploadImg(0);" class="btn"/><br/>
						图片大小：宽<input type="text" id="zoomWidth0" value="139" size="5" class="input" style="width:40px"/> 
						高<input type="text" id="zoomHeight0" value="139" size="5" class="input" style="width:40px"/>
						<input type="checkbox" id="zoom0" />自动压缩图片
						<input type="button" value="裁剪" onclick="imgCut(0);" class="btn"/>
					</div>
					<div class="float_left" style="margin-left:15px;">
						<img id="preImg0" alt="预览区" noResize="true" onerror="javascript:this.src='<%=r_m%>/img/defaultpic.gif'"  srcRoot="${web.uploadPath}" src="${web.uploadPath}<s:property value="content.titleImg"/>" style="width:100px;height:70px;background-color:#CCCCCC;border:1px solid #333"/>
					</div>
				</td>
			</tr>
			<!-- 
			<tr id="contImgDiv" >
				<td class="label">内容图：</td>
				<td class="content">
					<div class="float_left">
						图片路径<s:textfield name="content.contentExt.contentImg" id="uploadImgPath1" cssClass="input" /><input type="button" value="清空" onclick="clearImg(1);" class="btn"/><br/>
						本地上传<span id="ifc1"><input type="file" id="imgFile1" size="20" class="input"/></span>水印<s:checkbox name="mark"  value="true"/>
						<input type="button" value="上传" onclick="uploadImg(1);" class="btn"/><br/>
						图片大小：宽<input type="text" id="zoomWidth1" value="310" size="5" class="input" style="width:40px"/> 
						高<input type="text" id="zoomHeight1" value="310" size="5" class="input" style="width:40px"/>
						<input type="checkbox" id="zoom1" value="true" checked="checked"/>自动压缩图片
						<input type="button" value="裁剪" onclick="imgCut(1);" class="btn"/>
					</div>
					<div class="float_left" style="margin-left:15px;">
						<img id="preImg1" alt="预览区" noResize="true" onerror="javascript:this.src='<%=r_m%>/img/defaultpic.gif'"  srcRoot="${web.uploadPath}" src="${web.uploadPath}<s:property value="content.contentImg"/>" style="width:100px;height:70px;background-color:#CCCCCC;border:1px solid #333"/>
					</div>
				</td>
			</tr>
			 -->
			<!-- 
			<tr id="picSet">
				<td class="label">图片集：</td>
				<td class="content">
					<div><input type="button" onclick="addPicLine();" value="增加一张图片" class="btn" /></div>
					<table id="picTable2" border="0" style="float:left;margin:5px 5px 0 0;padding:2px;">
							<tr>
								<td>
									<div>路径<input type="text" id="uploadImgPath2" name="picPaths" class="input" style="width:135px;margin-right:2px;"/><input type="button" onclick="$('#picTable2').remove();" value="删除" class="btn" /></div>
									<div><span id="ifc2">上传<input type="file" id="imgFile2" size="10" class="input" style="width:140px"/></span><input type="button" value="上传" onclick="uploadImg(2);" class="btn" /></div>
									<div>说明<textarea style="width:180px;height:60px;" class="input txtArea" name="picDescs"></textarea></div>
								</td>
								<td><img id="preImg2" onerror="javascript:this.src='<%=r_m%>/img/defaultpic.gif'"  srcRoot="${web.uploadPath}" alt="预览" noResize="true" style="width:110px;height:110px;background-color:#ccc;border:1px solid #333"/></td>
							</tr>
					</table>
					<div id="picBefore" style="clear:both"></div>
					<textarea id="picTable" style="display:none;">
						<table id="picTable{0}" border="0" style="float:left;margin:5px 5px 0 0;padding:2px;">
							<tr>
								<td>
									<div>路径<input type="text" id="uploadImgPath{0}" name="picPaths" class="input" style="width:135px;margin-right:2px;"/><input type="button" onclick="$('#picTable{0}').remove();" value="删除" class="btn" /></div>
									<div><span id="ifc{0}">上传<input type="file" id="imgFile{0}" size="10" class="input" style="width:140px"/></span><input type="button" value="上传" onclick="uploadImg({0});" class="btn" /></div>
									<div>说明&lt;textarea style="width:180px;height:50px;" class="input txtArea" name="picDescs"&gt;&lt;/textarea&gt;</div>
								</td>
								<td><img id="preImg{0}" onerror="javascript:this.src='<%=r_m%>/img/defaultpic.gif'"  srcRoot="${web.uploadPath}" alt="预览" noResize="true" style="width:110px;height:100px;background-color:#ccc;border:1px solid #333"/></td>
							</tr>
						</table>
					</textarea>
					<script type="text/javascript">
						var picIndex = 4;
						var picTpl = $.format($("#picTable").val());
						function addPicLine() {
							$('#picBefore').before(picTpl(picIndex++));
						}
					</script>
				</td>
			</tr>
			 -->
			<tr id="attachment">
				<td class="label">附件：</td>
				<td class="content" >
					<table>
						<tr class="noborder">
							<td style="border:none;width:60px;"><input type="button" onclick="addAttachLine();" value="增加一个附件" class="btn"/></td>
							<td style="border:none;padding-left:5px;width:210px;">附件名称</td>
							<td style="border:none;padding-left:5px;width:210px;">附件地址</td>
							<td style="border:none;padding-left:5px;width:300px;">上传</td>
						</tr>
					</table>
					<table>
							<tr id="attachTr0">
								<td style="border:none;border-top:1px solid #ccc;width:60px;"><input type="button" onclick="$('#attachTr0').remove();" value="删除" class="btn"/></td>
								<td style="border:none;border-top:1px solid #ccc;"><input type="text" id="attachmentNames0" name="attachmentNames" class="input"/></td>
								<td style="border:none;border-top:1px solid #ccc;"><input type="text" id="attachmentPaths0" name="attachmentPaths" class="input"/></td>
								<td style="border:none;border-top:1px solid #ccc;">
									<span id="afc0"><input type="file" id="attachmentFile0" size="12" class="input" style="width:220px"/></span>
									<input type="button" value="上传" onclick="uploadAttachment(0);" class="btn"/>
									<input type="hidden" id="attachmentFilenames0" name="attachmentFilenames"/>
								</td>
							</tr>
					</table>
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
								</td>
							</tr>
						</table>
					</textarea>
					<script type="text/javascript">
						var attachIndex = 1;
						var attachTpl = $.format($("#attachTable").val());
						function addAttachLine() {
							$('#attachBefore').append(attachTpl(attachIndex++));
						}
					</script>
				</td>
			</tr>
			<tr id="media">
				<td class="label">多媒体：</td>
				<td class="content">
					<div style="width:400px;float:left;">
						媒体路径<s:textfield name="content.contentExt.mediaPath" id="mediaPath" cssClass="input" /><br/>
						本地上传<span id="mfc"><input type="file" id="mediaFile" size="20" class="input"/></span>
						<input type="button" value="上传" onclick="uploadMedia();" class="btn"/><br/>
					</div>
					<div style="margin-right:15px;float:left;">
						播放器:<s:radio list="#{'WM':'WM','REAL':'REAL','FLV':'FLV','FLASH':'FLASH'}" listKey="key" listValue="value" name="content.contentExt.mediaType"></s:radio>
					</div>
				</td>
			</tr>
			<tr>
				<td class="label">其他设置：</td>
				<td class="content">
					<s:select cssStyle="width:100px;border:1px solid #CCC;" name="content.status" list="#{'2':'审核通过','0':'草稿','1':'审核中','3':'回收站'}" />
					<s:select cssStyle="width:100px;border:1px solid #CCC;" name="content.topLevel" list="#{'0':'不置顶','6':'置顶1','5':'置顶2','4':'置顶3','3':'置顶4','2':'置顶5','1':'置顶6'}" />
				</td>
			</tr>
			<tr>
				<td class="content" colspan="2">
					  <fck:editor instanceName="content.contentTxt.txt" height="400px;">
						<jsp:attribute name="value">
						</jsp:attribute>
					</fck:editor> 
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
	<div id="channelsDialog" title="选择副栏目" style="display:none;text-align:left;">
		<div id="channelsSelector" >
			<s:if test="channelRoot==null">【没有可选择的栏目】</s:if>
			<s:else>
				<s:component template="tree.ftl" theme="ccweb">
					<s:param name="root" value="%{channelRoot}"/>
					<s:param name="isUrl" value="false"/>
					<s:param name="isCheckBox" value="true"/>
					<s:param name="checkBoxName" value="'channels'"/>
				</s:component>
			</s:else>
		</div>
	</div>
	<div id="tplContentPathDialog" title="选择内容模板路径" style="display:none;text-align:left;">
		<div id="tplContentPathSelector" >
			<s:if test="tplFileRoot==null">【没有可选择的模板文件】</s:if>
			<s:else>
				<s:component template="tree.ftl" theme="ccweb">
					<s:param name="root" value="%{tplFileRoot}"/>
					<s:param name="treeId" value="'t2'"/>
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
		$("#titleColor").colorPicker();
		chgType($("#contentTypeId").val());
	});
	function chgType(id) {
		$.ajax({
			type: "post",
			url : "ajax/content_changeType.jspa",
			dataType:"json",
			data: 'typeId='+id,
		    success: function(json){
			    var type=json.type;
			    if(type.hasImage!=null&&type.hasImage){
			    	//$("#typeImgDiv").css("display","");
			    	//$("#zoomWidth3").val(type.imgWidth);
					//$("#zoomHeight3").val(type.imgHeight);
					$("#titImgDiv").css("display","");
					$("#zoomWidth0").val(type.imgWidth);
					$("#zoomHeight0").val(type.imgHeight);
					//$("#contImgDiv").css("display","");
					//$("#zoomWidth1").val(type.imgWidth);
					//$("#zoomHeight1").val(type.imgHeight);
					$("#picSet").css("display","");
				}else{
					//$("#typeImgDiv").css("display","none");
					$("#titImgDiv").css("display","none");
					//$("#contImgDiv").css("display","none");
					$("#picSet").css("display","none");
				}
				if(type.hasAttachment!=null&&type.hasAttachment){
					$("#attachment").css("display","");
				}else{
					$("#attachment").css("display","none");
				}
				if(type.hasMedia!=null&&type.hasMedia){
					$("#media").css("display","");
				}else{
					$("#media").css("display","none");
				}
		    },
		    error: function(json){
			    $("#contentTypeError").html("加载类型出错");
			}
		});
	}
	//副栏目对话框
	$("#channelsDialog").dialog({
		autoOpen: false,
		modal: true,
		width: 280,
		height: 450,
		position: ["center",20],
		buttons: {
			"OK": function() {
				$("#channelsSelector input[name='channels']:checked").each(function(){
					appendChannels(this);
					$(this).removeAttr("checked");
				});
				$(this).dialog("close");
			}
		}
	});
	$('#channelsLink').click(function(){
		$('#channelsDialog').dialog('open');
		return false;
	});
	function appendChannels(channel) {
		var hasContain = false;
		$("input[name=channelIds]").each(function() {
			if($(this).val()==$(channel).val()) {
				hasContain = true;
			}
		});
		if(hasContain) {
			return;
		}
		var nodeList = eval($(channel).attr("nodeList"));
		var s = "<div style='padding:0;'>";
		for(var i=0,len=nodeList.length;i<len;i++) {
			s += nodeList[i];
			if(i<len-1) {
				s += " > ";
			}
		}
		s += " <a href='javascript:void(0);' onclick='$(this).parent().remove();' class='pn-opt'>删除</a>";
		s += " <input type='hidden' name='channelIds' value='"+$(channel).val()+"'/>";
		s += " </div>";
		$("#channelsContainer").append(s);
	}
	$('#periodicalLink').click(function(){
		$('#periodicalDialog').dialog('open');
		return false;
	});
	//选择栏目内容模板路径
	$("#tplContentPathDialog").dialog({
		autoOpen: false,
		modal: true,
		width: 280,
		height: 450,
		position: ["center",20],
		buttons: {
			"OK": function() {
				$("#tplContentPathSelector input[name='id']:checked").each(function(){
					$("#tplContent").val($(this).attr("relPath"));
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
	
</script>

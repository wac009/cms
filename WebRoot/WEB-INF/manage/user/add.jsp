<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/inc/path.inc"%>
<%@ include file="/inc/taglib.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<%@ include file="/inc/manage.inc"%>
  </head>
  <body>
  	<div class="navi"><span class="navi_b">当前位置:</span>
  		<span class="navi_h"><a href="#">管理员管理 - 添加管理员</a></span>
  		<span class="navi_f">
  			<input class="del btn" type="button" value="提交" onclick="$('#dataForm').submit();"/>
  			<a href='javascript:history.go(-1)' style="padding:0 5px;">返回</a>
  		</span>
  	</div>
	<div class="mainData">
	<jsp:include page="../common/imgUpload.jsp" />
	<div class="actInfo"><s:actionerror theme="ccweb"/><s:actionmessage theme="ccweb"/></div>
	<s:form action="user_save_add" cssClass="vldform" id="dataForm">
		<table width="100%" class="table_edit">
			<tr><td colspan="2">用户基本资料</td></tr>
			<tr>
				<td class="label">用户名：</td>
				<td class="content">
					<s:textfield name="user.username" id="username" cssClass="input required focus" onblur="checkUsername();" />
					<s:fielderror fieldName="user.username" theme="ccweb"/>
					<span class="red">*</span>
				</td>
			</tr>
			<tr>
				<td class="label">密码：</td>
				<td class="content">
					<s:password name="password" id="password" cssClass="input required" showPassword="true" />
					<s:fielderror fieldName="password" theme="ccweb"/>
					<span class="red">*</span>
					确认密码：
					<s:password name="confirm_password" id="confirm_password" cssClass="input required" equalTo="#password" showPassword="true" />
					<s:fielderror fieldName="confirm_password" theme="ccweb"/>
					<span class="red">*</span>
				</td>
			</tr>
			<tr>
				<td class="label">姓名：</td>
				<td class="content">
					<s:textfield name="user.ext.realname" id="realName" cssClass="input" />
					生日：<s:textfield name="birthday" cssClass="input Wdate" cssStyle="padding:2px 2px 1px 2px;"  onclick="WdatePicker({isShowWeek:true,dateFmt:'yyyy-MM-dd'})"/>
					性别：<s:radio list="#{'0':'保密','1':'男','2':'女'}" name="user.ext.gender" listKey="key" listValue="value" value="0"></s:radio>
				</td>
			</tr>
			<tr>
				<td class="label">联系方式</td>
				<td class="content">
					地址：<s:textfield name="user.ext.address" id="address" cssClass="input" />
					邮编：<s:textfield name="user.ext.zip" id="zip" cssClass="input" /><br/>
					邮箱：<s:textfield name="user.email" id="email" cssClass="input" />
					手机：<s:textfield name="user.ext.mobile" id="mobile" cssClass="input" /><br/>
					电话：<s:textfield name="user.ext.tel" id="tel" cssClass="input" />
					传真：<s:textfield name="user.ext.fax" id="fax" cssClass="input" />
				</td>
			</tr>
			<tr>
				<td class="label">头像：</td>
				<td class="content">
					<div class="float_left">
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
					<div class="float_left" style="margin-left:15px;">
						<img id="preImg0" alt="预览区" onerror="javascript:this.src='<%=r_m%>/img/defaultpic.gif'"  noResize="true" srcRoot="${web.uploadPath}" src="${web.uploadPath}<s:property value="user.ext.userImg"/>" style="width:75px;height:75px;background-color:#CCCCCC;border:1px solid #333"/>
					</div>
				</td>
			</tr>
			<tr>
				<td class="label">个人简介：</td>
				<td class="content"><s:textarea name="user.ext.intro" cssClass="input txtArea"></s:textarea></td>
			</tr>
			<tr>
				<td class="label">个性签名：</td>
				<td class="content"><s:textarea name="user.ext.signature" cssClass="input txtArea"></s:textarea></td>
			</tr>
			<tr><td colspan="2">管理员配置   </td></tr>
			<tr>
				<td class="label">角色：</td>
				<td class="content">
					<s:checkboxlist list="roleList" listKey="id" listValue="name" name="roles.id" />
				</td>
			</tr>
			<tr>
				<td class="label">用户组：</td>
				<td class="content">
					<s:radio list="groupList" name="groupId" listKey="id" listValue="name"></s:radio>
				</td>
			</tr>
			<tr>
				<td class="label">级别：</td>
				<td class="content"><s:textfield name="user.rank" id="rank" cssClass="input" />
				<span class="help">0为录入员，内容不用审核时所有管理员都可为0。系统默认值为0</span></td>
			</tr>
			<tr>
				<td class="label">属性配置：</td>
				<td class="content">
					<s:checkbox name="user.admin" id="admin_admin" value="true"/><label for="admin_admin">管理员</label>
					<s:checkbox name="user.selfAdmin" id="admin_isselfonly"/><label for="admin_isselfonly">受限</label>
					<s:checkbox name="user.viewonlyAdmin" id="admin_isviewonly"/><label for="admin_isviewonly">只读</label>
					<s:checkbox name="user.delete" id="admin_isdelete" value="true"/><label for="admin_isdelete">可删除</label>
					<s:checkbox name="user.disabled" id="admin_isdisabled"/><label for="admin_isdisabled">禁用</label>
					审核级别：<s:textfield name="checkStep" cssClass="input" cssStyle="width:50px;"/>
				</td>
			</tr>
			<tr>
				<td class="label">栏目权限：</td>
				<td class="content-tree">
					<s:if test="channelRoot==null">【此站点没有添加栏目】</s:if>
					<s:else>
						<s:checkbox name="allChannel" id="allChannel" onclick="disChannels(this.checked);" value="true"/><label for="allChannel">拥有所有栏目</label>
						<s:component template="tree.ftl" theme="ccweb">
							<s:param name="root" value="%{channelRoot}"/>
							<s:param name="isUrl" value="false"/>
							<s:param name="isCheckBox" value="true"/>
							<s:param name="onlyLeafCheckBox" value="false"/>
							<s:param name="checkBoxName" value="'channels.id'"/>
						</s:component>
					</s:else>
				</td>
			</tr>
			
			<tr>
				<td colspan="2">
					<div id="btn" class="btnDiv_fa">
						<input type="submit" value=" 提 交 " class="btn_fa"/>
						<input type="reset" value=" 重 置 " class="btn_fa"/>
					</div>
				</td>
			</tr>
		</table>
	</s:form>
	</div>
  </body>
</html>
<script type="text/javascript">
$(function() {
	if($("#allChannel").val()){
		$("#t").css("display","none")
	} else {
		$("#t").css("display","block");
	}
});
function disChannels(chk) {
	if(chk) {
		$("#t").css("display","none")
	} else {
		$("#t").css("display","block");
	}
}
</script>
/**
 * 定义后台管理的js处理方法
 * 
 */

var _add			= { action : "add_list.jspa"}
var _edit 			= {	action : "edit_list.jspa"};
var _delete 		= {	action : "delete_list.jspa",	msg : "您确定删除吗？"};
var _disable 		= {	action : "disable_list.jspa"};
var _enable 		= {	action : "enable_list.jspa"};
var _up				= { action : "up_list.jspa"};
var _down			= { action : "down_list.jspa"};
var _removeTop		= { action : "removeTop_list.jspa"};
var _enableCommend	= { action : "enableCommend_list.jspa"};
var _disableCommend	= { action : "disableCommend_list.jspa"};
var _enableComment	= { action : "enableComment_list.jspa"};
var _disableComment	= { action : "disableComment_list.jspa"};
var _lock			= { action : "lock_list.jspa"};
var _unlock			= { action : "unlock_list.jspa"};

function _operate(op, id) {
	if (op.msg && !Confirm(op.msg)) {
		return;
	}
	var dataForm = document.getElementById('dataForm');
	dataForm.onsubmit = null;
	setFormAction(op.action);
	dataForm.id.value = id;
	dataForm.submit();
}

function showByClass(clazz){
	$("."+clazz).show();
}
function hideByClass(clazz){
	$("."+clazz).hide();
}
function showById(id){
	$("#"+id).show();
}
function hideById(id){
	$("#"+id).hide();
}
function toggleById(id){
	$("#"+id).toggle();
}
function toggleByClass(clazz){
	$("."+clazz).toggle();
}

function _gotoPage(pageNo,cia) {
	if (pageNo == "") {
		alert("请输入页号");
	}
	try {
		var dataForm = document.getElementById('dataForm');
		dataForm.pageNo.value = pageNo;
		if(cia!=null&&cia==0){
			setFormAction('list.jspa');
		}
		if(cia!=null&&cia==1){
			setFormAction('acqulist.jspa');
		}
		dataForm.onsubmit = null;
		dataForm.submit();
	} catch (e) {
		alert('gotoPage(pageNo)方法出错');
	}
}

 /**
  * 查询数据
  */
function _search() {
	var dataForm = document.getElementById('dataForm');
	setFormAction('list.jspa');
	dataForm.onsubmit = null;
	dataForm.submit();
}


/**
 * 批量删除数据 * 
 */
function _batchDel() {
	$("#actInfo").show();
//	Alert("22");
	
//	if (_validateBatch()) {
//		var dataForm = document.getElementById('dataForm');
//		dataForm.submit();
//	}
}

function setFormAction(action) {
	var dataForm = document.getElementById('dataForm');
	dataForm.action = dataForm.action.split('_')[0] + "_" + action;
}

function _validateBatch() {
	setFormAction("delete_list.jspa");
	var batchChecks = document.getElementsByName('ids');
	var hasChecked = false;
	for (var i = 0; i < batchChecks.length; i++) {
		if (batchChecks[i].checked) {
			hasChecked = true;
			break;
		}
	}
	if (!hasChecked) {
		//Alert('请选择要操作的数据！')
	};
	return hasChecked == true ? (confirm('确定删除吗？')) : false;
}
/***使用ajax 验证数据
 * @param {} thiz 提交验证的元素控件
 * @param {} label 验证的字段标题
 * @param {} action 提交的action
 * @param {} param 验证的字段
 * @return {Boolean} 是否合法
 */
function ajaxCheck(thiz,label,action,param){
	afterId="afterCheck_"+param;
	if($("#"+afterId).length>0){
		$("#"+afterId).html("");
	}
	var pvalue=$("#"+param).val();
	if(pvalue==""){alert('请输入'+label);$("#"+param).focus();return false;}
	if(!$("#"+afterId).length>0){
		$(thiz).after("<span id='"+afterId+"'></span>");
	}
	$("#"+afterId).html(checking);
	
	$.ajax({
	    type: "post",
	    url : action,
	    dataType:"text",
	    data: param+"="+pvalue,
	    success: function(text){
	    	if(text=='true'){
				$("#"+afterId).html(image_ok+label+"可用");
	    	}else{
	    		$("#"+afterId).html(image_error+label+"不可用");
	    		$("#"+param).focus();
		    }
	    },
	    error: function(){
	    	$("#"+afterId).html(image_error+"检测错误");
	    	$("#"+param).focus();
		}
	});
}
/**
 * 在页面加载完成后的相关绑定、处理方法
 */
$().ready(function() {
	//输入框样式绑定
	$(".input").blur(function() {
		if($(this).hasClass("error")){$(this).css( {"border":"1px dashed red","background-color":"#FFF"});}
		else{$(this).css( {"border":"1px solid #CCC","background-color":"#FFF"});}
	});
	$(".input").focus(function() {
		if($(this).hasClass("error")){$(this).css( {"border":"1px dashed red","background-color":"#FFFFCC"});}
		else{$(this).css( {"border":"1px solid #CCC","background-color":"#FFFFCC"});}
	});
	//表单动作按钮样式绑定
	$(".btn_fa").mouseover(function() {	$(this).addClass("btnOn_fa");});
	$(".btn_fa").mouseout(function() { $(this).removeClass("btnOn_fa");});
	//数据列表样式绑定
	$(".datarows").bind("mouseover", function() {Pn.LTable.lineOver(this);});
	$(".datarows").bind("mouseout", function() {Pn.LTable.lineOut(this);});
	$(".datarows").bind("click", function() {Pn.LTable.lineSelect(this);});
	//操作链接图片绑定
	$(".pn-loperator").hover(
		function () {
			img=$(this).children("img");
			img.attr("src",img.attr("src").substring(0,img.attr("src").lastIndexOf('.'))+"_hover.gif");
		},
		function () {
			img=$(this).children("img");
			img.attr("src",img.attr("src").substring(0,img.attr("src").lastIndexOf('_'))+".gif");
		}
	);
	$(".errormsg").prev(".input").addClass("error");//对fielderror的输入框绑定样式
	if($(".focus").val()=="")$(".focus").focus();//如果指定的默认焦点没有值，就获取默认焦点
	$(".detail").addClass("none");//隐藏详细信息选项
	//$(".dataConfig").addClass("none");//隐藏详细信息选项
	
	$(".vldform").validate();//使用jquery validation 验证表单
	
	$(".menu").click(function(){
		$(".menu").removeClass("selected");
		$(this).addClass("selected");
	});
});



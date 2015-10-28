/**
 * 节假日在页面顶部显示祝福背景，所有页面生效。
 * Developed by: 王成
 * Date Created: 2012-09-28
 * Copyright: 全国老龄办信息中心
 */
$().ready(function() {
	$('body').prepend(
			'<div id="festival" style="height:65px; width:1000px;display:none;margin:0 auto;">'+
			'<div style="width:60px; height:20px; font-size:12px;padding-top:10px;"><a href="javascript:closefestival();"><font color="#fff">[ 关闭 X ]</font></a></div>'+
			'</div>'
	);
	//openfestival();
});
function closefestival(){		
    $('#festival').css("display","none");
    $('#top').css("width","100%");
    $('body').css("backgroundImage","");
}
function openfestival(){
	$('#festival').css("display","block");
	$('#top').css("width","1000px");
	$('body').css("backgroundPosition","center top");
	$('body').css("backgroundRepeat","no-repeat");
	$('body').css("backgroundImage","url('/res/c/festival/festival.jpg')");
}
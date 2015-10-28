/**
 * 活动时在页面顶部显示信息，首页显示
 * Developed by: 王成
 * Date Created: 2012-09-28
 * Copyright: 全国老龄办信息中心
 */
$().ready(function() {
	$('body').prepend(
			'<div id="huodong" style="height:276px; width:100%;display:none;margin:0 auto;">'+
			//'<div style="width:60px; height:20px; font-size:12px;padding-top:10px;padding-left:880px;font-weight:bold;"><a href="javascript:closeHd();"><font color="#fff">[ 关闭 X ]</font></a></div>'+
			'<div style="width:60px; height:20px; font-size:12px;padding-top:190px;padding-left:630px;font-weight:bold;"><a target="black" href="http://www.cncaprc.cn/lnfjs/index.jhtml"><img src="/resc/huodong/transparent.gif" style="width:200px;height:60px;"/></a></div>'+
			'</div>'
	);
	//openHd();
});
function closeHd(){		
    $('#huodong').css("display","none");
    $('#top').css("width","100%");
    $('body').css("backgroundImage","");
}
function openHd(){
	$('#huodong').css("display","block");
	$('#top').css("width","1000px");
	$('body').css("backgroundPosition","center top");
	$('body').css("backgroundRepeat","no-repeat");
	$('body').css("backgroundImage","url('/res/c/huodong/js2.jpg')");
}
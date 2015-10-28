/*********************************************************************************************************/
/**
 * FCKeditor Highslide JS Plugin For Fckeditor (Author: Lajox ; Email: lajox@19www.com)
 */
/*********************************************************************************************************/

var dialog	= window.parent ;
var oEditor    = window.parent.InnerDialogLoaded(); 
var FCK        = oEditor.FCK; 
var FCKLang    = oEditor.FCKLang ;
var FCKConfig  = oEditor.FCKConfig ;

//var highslideJS      = "/editor/fckeditor/editor/plugins/highslide/js/highslide.js";
//var highslideCSS     = "/editor/fckeditor/editor/plugins/highslide/js/highslide.css";
//var highslideMainJS  = "/editor/fckeditor/editor/plugins/highslide/js/main.js";
var highslideJS      = FCKConfig.PluginsPath +"highslide/js/highslide.js";
var highslideCSS     = FCKConfig.PluginsPath +"highslide/js/highslide.css";
var highslideMainJS  = FCKConfig.PluginsPath +"highslide/js/main.js";

var JSVar ="";
JSVar  = '<scr' + 'ipt type="text/javascript" src="'+ highslideJS +'"></scr' + 'ipt>\r\n';
JSVar += '<link rel="stylesheet" type="text/css" href="'+highslideCSS+'" />\r\n';
JSVar += '<scr' + 'ipt type="text/javascript" src="'+ highslideMainJS +'"></scr' + 'ipt>\r\n';
JSVar += '<div class="highslide-gallery">\r\n';


window.onload = function()
{
	oEditor.FCKLanguageManager.TranslatePage(document) ;
	//window.parent.SetOkButton( true ) ;
};

function Ok() {	return true;}

function MakeHighSlideJS() {

 //var str = window.opener.FCK.EditorDocument.body.innerHTML;
 var str = FCK.GetXHTML();

 str = str.replace(/^|(<a\b[^>]*>)?\s*(<img\b[^/>]*(?:src=("[^"]*"|'[^']*'|\S+))[^>]*>)\s*(?:<\/a>)?/ig, function() {
    var $ = arguments;
	aaa=$[1];
    if ($[0].length) {
        return [
            '<a href=' + $[3] + ' class="highslide" onclick="return hs.expand(this)">'
            , '<img src=' + $[3] + ' alt="Highslide JS" title="点击放大图片"></a>'
            , '<div class="highslide-heading">&nbsp;</div>'
        ].join("\r\n");
    } else {
        return JSVar;
	}
});

FCK.SetHTML(str);

document.getElementById("msgok").innerHTML = "<font color=red>所有图片Highslide JS特效应用成功！</font>";
}



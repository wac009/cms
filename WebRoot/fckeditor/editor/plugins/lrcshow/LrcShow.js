/*********************************************************************************************************/
/**
 * LrcShow (Music Player with Lyrics Rolling) Plugin (Author: Lajox ; Email: lajox@19www.com)
 */
/*********************************************************************************************************/

var dialog	= window.parent ;
var oEditor    = window.parent.InnerDialogLoaded(); 
var FCK        = oEditor.FCK; 
var FCKLang    = oEditor.FCKLang ;
var FCKConfig  = oEditor.FCKConfig ;

window.onload = function()
{
	oEditor.FCKLanguageManager.TranslatePage(document) ;
	window.parent.SetOkButton( true ) ;
	loadLRCSelection();

}

function loadLRCSelection() {
	// Show/Hide according to settings
	ShowE('spanBrowse', FCKConfig.LinkBrowser);
	
}

function Ok()
{	

//处理信息
	var sMusicUrl = GetE('MusicUrl').value;
	sMusicUrl = encodeURI(sMusicUrl);
	//sMusicUrl = encodeURIComponent(sMusicUrl);
	var PlayerType = GetE('PlayerType').value;
	var IsAutoStart = GetE('AutoStart').checked;
	var IsReplay = GetE('Replay').checked;
	var LrcUrl = GetE('LrcUrl').value;

	var IsAutoST="no"; 
	var IsRP="no";
	if (IsAutoStart) { IsAutoST="yes" }
	if (IsReplay) { IsRP="yes" }

//var LrcPath="/editor/fckeditor/editor/plugins/lrcshow/lrc.php";
var LrcPath=FCKConfig.PluginsPath + "lrcshow/lrc.php";

var sInsert="";
sInsert+="<iframe ";
sInsert+="src=\""+LrcPath+"?url="+sMusicUrl+"&player="+PlayerType+"&lrc="+LrcUrl+"&auto="+IsAutoST+"&loop="+IsRP+"\" ";
sInsert+="width=\"400\" height=\"295\" scrollbar=\"no\" scrolling=\"no\"  border=\"0\" frameborder=\"0\">";
sInsert+="<font color=red>你的浏览器不支持iframe 歌词同步播放无法显示</font> ";
sInsert+="</iframe>";

	if (sMusicUrl.length!=""){
		oEditor.FCK.InsertHtml(sInsert) ;
	} else {
		alert("Please input music url !");
	}

	return true ;	
	
}



function MediaBrowseServer()
{
	OpenServerBrowser(
		'media',
		oEditor.FCKConfig.MediaBrowserURL,
		oEditor.FCKConfig.MediaBrowserWindowWidth,
		oEditor.FCKConfig.MediaBrowserWindowHeight ) ;
}
function LinkBrowseServer()
{
	OpenServerBrowser(
		'link',
		oEditor.FCKConfig.LinkBrowserURL,
		oEditor.FCKConfig.LinkBrowserWindowWidth,
		oEditor.FCKConfig.LinkBrowserWindowHeight ) ;
}

function OpenServerBrowser( type, url, width, height )
{
	sActualBrowser = type ;
	OpenFileBrowser( url, width, height ) ;
}

var sActualBrowser ;

/** Set selected URL from Browser */
function SetUrl( url ) {
	if ( sActualBrowser == 'media' ) {
		document.getElementById('MusicUrl').value = url ;
	} else if ( sActualBrowser == 'link' ) {
		document.getElementById('LrcUrl').value = url ;
	} 
}

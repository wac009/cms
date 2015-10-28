/*********************************************************************************************************/
/**
 * FCKeditor Dewplayer MP3 Plugin (Author: Lajox ; Email: lajox@19www.com)
 */
/*********************************************************************************************************/

var dialog	= window.parent ;
var oEditor    = window.parent.InnerDialogLoaded(); 
var FCK        = oEditor.FCK; 
var FCKLang    = oEditor.FCKLang ;
var FCKConfig  = oEditor.FCKConfig ;

var LSType="dewplayer";

window.onload = function()
{
	oEditor.FCKLanguageManager.TranslatePage(document) ;
	window.parent.SetOkButton( true ) ;
	loadMMSelection();

}

function loadMMSelection() {
	// Show/Hide according to settings
	ShowE('spanBrowse', FCKConfig.LinkBrowser);
	
}

/** Browse/upload a file on server */
function BrowseServer() {
 OpenFileBrowser( FCKConfig.MediaBrowserURL, FCKConfig.MediaBrowserWindowWidth, FCKConfig.MediaBrowserWindowHeight ) ;
}

function Ok()
{	

//处理信息
	var sMusicUrl = GetE('MusicUrl').value;
	var PlayerType = GetE('PlayerType').value;
	var IsAutoStart = GetE('AutoStart').checked;
	var IsAutoReplay = GetE('AutoReplay').checked;
	var Width = GetE('Width').value;
	var Height = GetE('Height').value;
	var BgColor = GetE('BgColor').value;

	var IsAutoST="0"; 
	var IsAutoRP="0";
	if (IsAutoStart) { IsAutoST="1" }
	if (IsAutoReplay) { IsAutoRP="1" }

 //var swfpath="editor/fckeditor/editor/plugins/dewplayer/";
 var swfpath= FCKConfig.PluginsPath + "dewplayer/";
 var sMusc="";

/*** 生成 ***/
if (PlayerType=="dewplayer" || PlayerType=="dewplayer-multi" || PlayerType=="dewplayer-mini" || PlayerType=="dewplayer-vol") {
sMusc+="<embed type=\"application/x-shockwave-flash\" ";
sMusc+="src=\""+swfpath+LSType+".swf\" ";
sMusc+="width=\""+Width+"\" height=\""+Height+"\" bgcolor=\""+BgColor+"\" ";
sMusc+="flashvars=\"son="+sMusicUrl+"&autoplay="+IsAutoST+"&autoreplay="+IsAutoRP+"\">";
sMusc+="</embed>";
}

if (PlayerType=="dewplayer-stream" || PlayerType=="dewplayer-bubble" ) {
sMusc+="<object classid=\"clsid:d27cdb6e-ae6d-11cf-96b8-444553540000\" codebase=\"http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,0,0\"\n  ";
sMusc+="width=\""+Width+"\" height=\""+Height+"\" id=\"dewplayer\" type=\"application/x-shockwave-flash\">\n ";
sMusc+="<param name=\"allowScriptAccess\" value=\"sameDomain\" />\n ";
sMusc+="<param name=\"movie\" value=\""+swfpath+LSType+".swf?mp3="+sMusicUrl+"\" />\n ";
sMusc+="<param name=\"quality\" value=\"high\" />\n ";
sMusc+="<param name=\"bgcolor\" value=\""+BgColor+"\" />\n ";
sMusc+="<param name=\"wmode\" value=\"transparent\" />\n ";
sMusc+="<embed type=\"application/x-shockwave-flash\" wmode=\"transparent\" quality=\"high\" ";
sMusc+="src=\""+swfpath+LSType+".swf?mp3="+sMusicUrl+"\" ";
sMusc+="width=\""+Width+"\" height=\""+Height+"\" bgcolor=\""+BgColor+"\" ";
sMusc+="pluginspage=\"http://www.macromedia.com/go/getflashplayer\">";
sMusc+="</embed>\n";
sMusc+="</object>";

}

	if (sMusicUrl.length!=""){
		oEditor.FCK.InsertHtml(sMusc) ;
	} else {
		alert("please input music url !");
	}

	return true ;	
	
}


/** Set selected URL from Browser */
function SetUrl(url) {
	GetE('MusicUrl').value = url;
}

function SelectTextColor( color )
{
	if ( color && color.length > 0 ) {
		GetE('BgColor').value = color ;
		GetE('BgColor').style.background = color ;
		//updatePreview()
	}
}
function SelectColor()
{
	oEditor.FCKDialog.OpenDialog( 'FCKDialog_Color', oEditor.FCKLang.DlgColorTitle, 'dialog/fck_colorselector.html', 400, 330, SelectTextColor, window ) ;
}

function SendType(pt){
	var wd=document.getElementById('Width');
	var ht=document.getElementById('Height');
	var as=document.getElementById('AutoStart');
	var ap=document.getElementById('AutoReplay');
	if (pt=="dewplayer") {
	LSType= "dewplayer" ;
	wd.value ="200";
	ht.value ="20";
	wd.disabled=true;
	ht.disabled=true;
	as.disabled=false;
	ap.disabled=false;
	}
	if (pt=="dewplayer-multi") {
	LSType= "dewplayer-multi" ;
	wd.value ="240";
	ht.value ="20";
	wd.disabled=true;
	ht.disabled=true;
	as.disabled=false;
	ap.disabled=false;
	}
	if (pt=="dewplayer-mini") {
	LSType= "dewplayer-mini" ;
	wd.value ="150";
	ht.value ="20";
	wd.disabled=true;
	ht.disabled=true;
	as.disabled=false;
	ap.disabled=false;
	}
	if (pt=="dewplayer-vol") {
	LSType= "dewplayer-vol" ;
	wd.value ="240";
	ht.value ="20";
	wd.disabled=true;
	ht.disabled=true;
	as.disabled=false;
	ap.disabled=false;
	}
	if (pt=="dewplayer-stream") {
	LSType= "dewplayer-stream" ;
	wd.value ="135";
	ht.value ="50";
	wd.disabled=true;
	ht.disabled=true;
	as.checked=false;
	ap.checked=false;
	as.disabled=true;
	ap.disabled=true;
	}
	if (pt=="dewplayer-bubble") {
	LSType= "dewplayer-bubble" ;
	wd.value ="250";
	ht.value ="65";
	wd.disabled=true;
	ht.disabled=true;
	as.checked=false;
	ap.checked=false;
	as.disabled=true;
	ap.disabled=true;
	}
}

function makepreview(){
	var width=document.getElementById('Width').value;
	var height=document.getElementById('Height').value;
	var mp3url = GetE('MusicUrl').value;
	var pt = GetE('PlayerType').value;
	var BgColor = GetE('BgColor').value;
	var autos = document.getElementById('AutoStart').checked;
	var autop = document.getElementById('AutoReplay').checked;
	
	var isautos="0"; var isautop="0";
	if (autos) { isautos="1" }
	if (autop) { isautop="1" }

var temphtml="";

if (pt=="dewplayer" || pt=="dewplayer-multi" || pt=="dewplayer-mini" || pt=="dewplayer-vol") {
temphtml+="<embed type=\"application/x-shockwave-flash\" ";
temphtml+="src=\""+pt+".swf\" ";
temphtml+="width=\""+width+"\" height=\""+height+"\" bgcolor=\""+BgColor+"\" ";
temphtml+="flashvars=\"son="+mp3url+"&autoplay="+isautos+"&autoreplay="+isautop+"\">";
temphtml+="</embed>";
}

if (pt=="dewplayer-stream" || pt=="dewplayer-bubble" ) {
temphtml+="<object classid=\"clsid:d27cdb6e-ae6d-11cf-96b8-444553540000\" codebase=\"http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,0,0\"\n  ";
temphtml+="width=\""+width+"\" height=\""+height+"\" id=\"dewplayer\" type=\"application/x-shockwave-flash\">\n ";
temphtml+="  <param name=\"allowScriptAccess\" value=\"sameDomain\" />\n ";
temphtml+="  <param name=\"movie\" value=\""+pt+".swf?mp3="+mp3url+"\" />\n ";
temphtml+="  <param name=\"quality\" value=\"high\" />\n ";
temphtml+="  <param name=\"bgcolor\" value=\""+BgColor+"\" />\n ";
temphtml+="  <param name=\"wmode\" value=\"transparent\" />\n ";
temphtml+="<embed type=\"application/x-shockwave-flash\" wmode=\"transparent\" quality=\"high\" ";
temphtml+="src=\""+pt+".swf?mp3="+mp3url+"\" ";
temphtml+="width=\""+width+"\" height=\""+height+"\" bgcolor=\""+BgColor+"\" ";
temphtml+="pluginspage=\"http://www.macromedia.com/go/getflashplayer\">";
temphtml+="</embed>\n";
temphtml+="</object>";
}

//alert(temphtml);
document.getElementById('preview').innerHTML=temphtml;
}


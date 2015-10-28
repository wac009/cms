/*********************************************************************************************************/
/**
 *  fmplayer Plugin For Fckeditor (Author: Lajox ; Email: lajox@19www.com)
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
	loadFMSelection();

}

function loadFMSelection() {
	// Show/Hide according to settings
	ShowE('spanBrowse', FCKConfig.LinkBrowser);
}

/** Browse/upload a file on server */
function BrowseServer() {
 OpenFileBrowser( FCKConfig.MediaBrowserURL, FCKConfig.MediaBrowserWindowWidth, FCKConfig.MediaBrowserWindowHeight ) ;
}

function Ok()
{	

/***自动播放 循环播放 begin***/
	var sPlay ="";
	var autostart ="false";
	var repeat ="false";
	
	var sAutoStart = GetE('autostart').checked; 
	if (sAutoStart==true){
		autostart = "true";
	}	
	var sLoop = GetE('repeat').checked;
	if (sLoop==true){
		repeat = "true";	
	}		
/***自动播放 循环播放 end***/	
	
//处理Mp3Url和Player Width及Song Volume信息
	var sMp3Url = GetE('Mp3Url').value;
	var PlayerWidth ="350";
	if (GetE('width').value!=""){
		PlayerWidth = GetE('width').value ;
	}
	
	var bg,fc,lg;
	bg=GetE('backcolor').value;
	fc=GetE('frontcolor').value;
	lg=GetE('lightcolor').value;

//	var PlayerPath="editor/fckeditor/editor/plugins/fmplayer/mediaplayer.swf";  //fmplayer swf path
//	var SwfObjJs="editor/fckeditor/editor/plugins/fmplayer/swfobject.js";  //swfobject.js path
	var PlayerPath=FCKConfig.PluginsPath + "fmplayer/mediaplayer.swf";  //fmplayer swf path
	var SwfObjJs=FCKConfig.PluginsPath + "fmplayer/swfobject.js";  //swfobject.js path
	
	if( sMp3Url.length> 0) { 

function makeRandomNum(){//make Random Number 
	var nowDate=new Date();
	var theNum;
	theNum=(nowDate.getMonth()+1).toString()+nowDate.getDate().toString()+nowDate.getHours().toString()+nowDate.getMinutes().toString()+nowDate.getSeconds().toString();
	return  theNum;
}
var numTemp;
numTemp=makeRandomNum(); 
sPlay+="<script type=\"text/javascript\" src=\""+SwfObjJs+"\"></script>\n" ;
sPlay+="<div id=\"fmp_"+numTemp+"\"><a href=\"http://www.macromedia.com/go/getflashplayer\">Get the Flash Player</a> to see this player.</div>\n" ;
sPlay+="<script type=\"text/javascript\">" ;
sPlay+="var so_"+numTemp+" = new SWFObject(\""+PlayerPath+"\",\"JW_Media_Player_"+numTemp+"\",\""+PlayerWidth+"\",\"20\",\"7\");\n" ;
sPlay+="so_"+numTemp+".addVariable(\"file\",\""+sMp3Url+"\");\n" ;
sPlay+="so_"+numTemp+".addVariable(\"backcolor\",\""+bg+"\");\n" ;
sPlay+="so_"+numTemp+".addVariable(\"frontcolor\",\""+fc+"\");\n" ;
sPlay+="so_"+numTemp+".addVariable(\"lightcolor\",\""+lg+"\");\n" ;
sPlay+="so_"+numTemp+".addVariable(\"displayheight\",\"0\");\n" ;
sPlay+="so_"+numTemp+".addVariable(\"base\",\".\");\n" ;
sPlay+="so_"+numTemp+".addVariable(\"autostart\",\""+autostart+"\");\n" ;
sPlay+="so_"+numTemp+".addVariable(\"repeat\",\""+repeat+"\");\n" ;
sPlay+="so_"+numTemp+".write(\"fmp_"+numTemp+"\");\n" ;
sPlay+="</script>\n" ;

		oEditor.FCK.InsertHtml(sPlay) ;
	} else {
		alert("Please input mp3 url !");
	}
	return true ;
}


/** Set selected URL from Browser */
function SetUrl(url) {
	GetE('Mp3Url').value = url;
}

function SelectTextColor1( color )
{
	if ( color && color.length > 0 ) {
		putcolor=color.replace("#","");
		GetE('backcolor').style.background = "#"+putcolor ;
		GetE('backcolor').value = "0x"+putcolor ;
		//updatePreview()
	}
}
function SelectColor1()
{
	oEditor.FCKDialog.OpenDialog( 'FCKDialog_Color', oEditor.FCKLang.DlgColorTitle, 'dialog/fck_colorselector.html', 400, 330, SelectTextColor1, window ) ;
}
function SelectTextColor2( color )
{
	if ( color && color.length > 0 ) {
		putcolor=color.replace("#","");
		GetE('frontcolor').style.background = "#"+putcolor ;
		GetE('frontcolor').value = "0x"+putcolor ;
		//updatePreview()
	}
}
function SelectColor2()
{
	oEditor.FCKDialog.OpenDialog( 'FCKDialog_Color', oEditor.FCKLang.DlgColorTitle, 'dialog/fck_colorselector.html', 400, 330, SelectTextColor2, window ) ;
}
function SelectTextColor3( color )
{
	if ( color && color.length > 0 ) {
		putcolor=color.replace("#","");
		GetE('lightcolor').style.background = "#"+putcolor ;
		GetE('lightcolor').value = "0x"+putcolor ;
		//updatePreview()
	}
}
function SelectColor3()
{
	oEditor.FCKDialog.OpenDialog( 'FCKDialog_Color', oEditor.FCKLang.DlgColorTitle, 'dialog/fck_colorselector.html', 400, 330, SelectTextColor3, window ) ;
}

function makepreview(){
	var bg,fc,lg;
	bg=GetE('backcolor').value;
	fc=GetE('frontcolor').value;
	lg=GetE('lightcolor').value;
	var sMp3Url = GetE('Mp3Url').value;
	
	var sPlay ="";
	var autostart ="false";
	var repeat ="false";
	var sAutoStart = GetE('autostart').checked; 
	if (sAutoStart==true){autostart = "true";}	
	var sLoop = GetE('repeat').checked;
	if (sLoop==true){repeat = "true";	}	
	
var sPlay="";
var temphtml;
var flashvars;
/***
sPlay+="<script type=\"text/javascript\" src=\"swfobject.js\"></script>\n" ;
sPlay+="<a href=\"http://www.macromedia.com/go/getflashplayer\">Get the Flash Player</a> to see this player.</div>\n" ;
sPlay+="<script type=\"text/javascript\">" ;
sPlay+="var so_00000 = new SWFObject(\"mediaplayer.swf\",\"JW_Media_Player_00000\",\""+GetE('width').value+"\",\"20\",\"7\");\n" ;
sPlay+="so_00000.addVariable(\"file\",\""+sMp3Url+"\");\n" ;
sPlay+="so_00000.addVariable(\"backcolor\",\""+bg+"\");\n" ;
sPlay+="so_00000.addVariable(\"frontcolor\",\""+fc+"\");\n" ;
sPlay+="so_00000.addVariable(\"lightcolor\",\""+lg+"\");\n" ;
sPlay+="so_00000.addVariable(\"displayheight\",\"0\");\n" ;
sPlay+="so_00000.addVariable(\"base\",\".\");\n" ;
sPlay+="so_00000.addVariable(\"autostart\",\""+autostart+"\");\n" ;
sPlay+="so_00000.addVariable(\"repeat\",\""+repeat+"\");\n" ;
sPlay+="so_00000.write(\"fmp_00000\");\n" ;
sPlay+="</script>\n" ;
***/
flashvars="file="+sMp3Url+"&amp;autostart="+autostart+"&amp;repeat="+repeat+"&amp;backcolor="+bg+"&amp;frontcolor="+fc+"&amp;lightcolor="+lg;
sPlay+='<embed src="mediaplayer.swf" width="'+GetE('width').value+'" height="20" bgcolor="#ffffff" allowscriptaccess="always"  flashvars="'+flashvars+'"/>' ;
temphtml=sPlay+"";
//alert(temphtml);
document.getElementById('previewing').innerHTML=temphtml;
}
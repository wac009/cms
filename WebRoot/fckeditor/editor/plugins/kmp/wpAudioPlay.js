/*********************************************************************************************************/
/**
 * FCKeditor Flash MP3 Player (AudioPlayer) Plugin For Fckeditor (Support: Lajox ; Email: lajox@19www.com)
 */
/*********************************************************************************************************/

var dialog	= window.parent ;
var oEditor    = window.parent.InnerDialogLoaded(); 
var FCK        = oEditor.FCK; 
var FCKLang    = oEditor.FCKLang ;
var FCKConfig  = oEditor.FCKConfig ;

/*****Dialog Tab Begin****/
// Set the dialog tabs.
dialog.AddTab( 'Info', oEditor.FCKLang.DlgInfoTab ) ;

//if ( !FCKConfig.MediaDlgHideAdvanced )
dialog.AddTab( 'Advanced', oEditor.FCKLang.DlgAdvancedTag ) ;

// Function called when a dialog tag is selected.
function OnDialogTabChange( tabCode )
{
	ShowE('divInfo'		, ( tabCode == 'Info' ) ) ;
	ShowE('divAdvanced'	, ( tabCode == 'Advanced' ) ) ;
}

/*****Dialog Tab End****/

window.onload = function()
{
	oEditor.FCKLanguageManager.TranslatePage(document) ;
	window.parent.SetOkButton( true ) ;
	loadKMPSelection();

}

function loadKMPSelection() {
	// Show/Hide according to settings
	ShowE('spanBrowse', FCKConfig.LinkBrowser);
}

/** Browse/upload a file on server */
function BrowseServer() {
 OpenFileBrowser( FCKConfig.MediaBrowserURL, FCKConfig.MediaBrowserWindowWidth, FCKConfig.MediaBrowserWindowHeight ) ;
}



function makeRandomNum(){//make Random Number 
	var nowDate=new Date();
	var theNum;
	theNum=(nowDate.getMonth()+1).toString()+nowDate.getDate().toString()+nowDate.getHours().toString()+nowDate.getMinutes().toString()+nowDate.getSeconds().toString();
	return  theNum;
}
function Ok()
{	
	var sMp3Url = GetE('txtUrl').value;
	var sAutioPlay="";
	
	var sAutoStart = GetE('autoStart').checked; 
	if (sAutoStart==true){
		sAutoStart="autostart=yes&amp;"
	}
	else{
		sAutoStart="autostart=no&amp;";
	}
	
	var sLoop = GetE('loop').checked;
	if (sLoop==true){
		sLoop="loop=yes&amp;";	
	}
	else{
		sLoop="loop=no&amp;";
	}
	
	var alwayOpen= GetE('alwayOpen').checked;
	if (alwayOpen==true){
		alwayOpen="animation=no&amp;";
	}
	else{
		alwayOpen="animation=yes&amp;";
	}
	//Mp3 title and Artist Information
	var musicTitles,musicArtists;
	musicTitles="";
	musicArtists="";

	if (GetE('musicTitle').value!=""){
		musicTitles="titles="+GetE('musicTitle').value+"&amp;";
	}
	if (GetE('musicArtist').value!=""){
		musicArtists="artists="+GetE('musicArtist').value+"&amp;";
	}
	
	//Player Width and Player Height Information
	var PlayerWidth,PlayerHeight;
	PlayerWidth="290";
	PlayerHeight="24";

	if (GetE('PlayerWidth').value!=""){
		PlayerWidth = GetE('PlayerWidth').value;
	}
	if (GetE('PlayerHeight').value!=""){
		PlayerHeight = GetE('PlayerHeight').value;
	}

//Advanced Information
 var AdvanceControl;
 var bg,leftbg,lefticon,rightbg,rightbghover,righticon,righticonhover,text,slider,track,border,loader;
	bg 		= "bg=0xCDDFF3&amp;";
	leftbg 		= "leftbg=0x357DCE&amp;";
	lefticon	= "lefticon=0xF2F2F2&amp;";
	rightbg		= "rightbg=0xF06A51&amp;";
	rightbghover 	= "rightbghover=0xAF2910&amp;";
	righticon	= "righticon=0xF2F2F2&amp;";
	righticonhover	= "righticonhover=0xFFFFFF&amp;";
	text		= "text=0x357DCE&amp;";
	slider		= "slider=0x357DCE&amp;";
	track		= "track=0xFFFFFF&amp;";
	border		= "border=0xFFFFFF&amp;";
	loader		= "loader=0xAF2910&amp;";

//AdvanceControl = "bg=0xCDDFF3&amp;leftbg=0x357DCE&amp;lefticon=0xF2F2F2&amp;rightbg=0xF06A51&amp;rightbghover=0xAF2910&amp;righticon=0xF2F2F2&amp;righticonhover=0xFFFFFF&amp;text=0x357DCE&amp;slider=0x357DCE&amp;track=0xFFFFFF&amp;border=0xFFFFFF&amp;loader=0xAF2910&amp;"


	if (GetE('bg').value!=""){
		bg = GetE('bg').value;
	}
	if (GetE('bg').value!=""){ bg = "bg="+GetE('bg').value+"&amp;"; }
	if (GetE('leftbg').value!=""){ leftbg = "leftbg="+GetE('leftbg').value+"&amp;"; }
	if (GetE('lefticon').value!=""){ lefticon = "lefticon="+GetE('lefticon').value+"&amp;"; }
	if (GetE('rightbg').value!=""){ rightbg = "rightbg="+GetE('rightbg').value+"&amp;"; }
	if (GetE('rightbghover').value!=""){ rightbghover = "rightbghover="+GetE('rightbghover').value+"&amp;"; }
	if (GetE('righticon').value!=""){ righticon = "righticon="+GetE('righticon').value+"&amp;"; }
	if (GetE('righticonhover').value!=""){ righticonhover = "righticonhover="+GetE('righticonhover').value+"&amp;"; }
	if (GetE('text').value!=""){ text = "text="+GetE('text').value+"&amp;"; }
	if (GetE('slider').value!=""){ slider = "slider="+GetE('slider').value+"&amp;"; }
	if (GetE('track').value!=""){ track = "track="+GetE('track').value+"&amp;"; }
	if (GetE('border').value!=""){ border = "border="+GetE('border').value+"&amp;"; }
	if (GetE('loader').value!=""){ loader = "loader="+GetE('loader').value+"&amp;"; }

        AdvanceControl = bg+leftbg+lefticon+rightbg+rightbghover+righticon+righticonhover+text+slider+track+border+loader;



//	var Mp3PlayerPath="/editor/fckeditor/editor/plugins/kmp/player.swf";  //Flash Player Path
//	var mp3img= "/editor/fckeditor/editor/plugins/kmp/mini.gif" ; //Small picture Path
	var Mp3PlayerPath=FCKConfig.PluginsPath +"kmp/player.swf";  //Flash Player Path
	var mp3img= FCKConfig.PluginsPath +"kmp/mini.gif" ; //Small picture Path
	
	var numTemp;	
	var InfoShow = FCKLang.KMPInfoShow ;
	
	if( sMp3Url.length> 0) {
		numTemp=makeRandomNum(); 
sAutioPlay="<img src=\""+ mp3img +"\" style=\"margin:0px 2px -3px 0px;\" border=\"0\"/><span style=\"font-size: smaller; \">"+InfoShow+"</span><br />\n"; 
sAutioPlay +="<object id=\"audioplayer"+numTemp+"\" data=\""+Mp3PlayerPath+"\" width=\""+PlayerWidth+"\" height=\""+PlayerHeight+"\"  type=\"application/x-shockwave-flash\">"+"<param value="+Mp3PlayerPath+" name=\"movie\" />"+"<param value=\"playerID="+numTemp+"&amp;"+AdvanceControl+musicTitles+musicArtists+sAutoStart+sLoop+alwayOpen+"soundFile="+sMp3Url+"\" name=\"FlashVars\" /><param value=\"high\" name=\"quality\" /><param value=\"false\" name=\"menu\" /><param value=\"transparent\" name=\"wmode\" />"+"</object>";
		
		oEditor.FCK.InsertHtml(sAutioPlay) ;
    	//window.parent.Cancel() ;
	} else {
		alert(FCKLang.KMPErrAlert);
	}
	return true ;
}


/** Set selected URL from Browser */
function SetUrl(url) {
	GetE('txtUrl').value = url;
}

/*******begin********/

/** Set selected URL from Browser */
function SelectTextColor1( color )
{
	if ( color && color.length > 0 ) {
		color=color.replace('#', '');
		GetE('leftbg').value = "0x"+color;
		GetE('leftbg').style.background = "#"+color ;
		//updatePreview()
	}
}
function SelectColor1()
{
	oEditor.FCKDialog.OpenDialog( 'FCKDialog_Color', oEditor.FCKLang.DlgColorTitle, 'dialog/fck_colorselector.html', 400, 

330, SelectTextColor1, window ) ;
}
function SelectTextColor2( color )
{
	if ( color && color.length > 0 ) {
		color=color.replace('#', '');
		GetE('lefticon').value = "0x"+color;
		GetE('lefticon').style.background = "#"+color ;
		//updatePreview()
	}
}
function SelectColor2()
{
	oEditor.FCKDialog.OpenDialog( 'FCKDialog_Color', oEditor.FCKLang.DlgColorTitle, 'dialog/fck_colorselector.html', 400, 

330, SelectTextColor2, window ) ;
}
function SelectTextColor3( color )
{
	if ( color && color.length > 0 ) {
		color=color.replace('#', '');
		GetE('text').value = "0x"+color;
		GetE('text').style.background = "#"+color ;
		//updatePreview()
	}
}
function SelectColor3()
{
	oEditor.FCKDialog.OpenDialog( 'FCKDialog_Color', oEditor.FCKLang.DlgColorTitle, 'dialog/fck_colorselector.html', 400, 

330, SelectTextColor3, window ) ;
}
function SelectTextColor4( color )
{
	if ( color && color.length > 0 ) {
		color=color.replace('#', '');
		GetE('loader').value = "0x"+color;
		GetE('loader').style.background = "#"+color ;
		//updatePreview()
	}
}
function SelectColor4()
{
	oEditor.FCKDialog.OpenDialog( 'FCKDialog_Color', oEditor.FCKLang.DlgColorTitle, 'dialog/fck_colorselector.html', 400, 

330, SelectTextColor4, window ) ;
}
function SelectTextColor5( color )
{
	if ( color && color.length > 0 ) {
		color=color.replace('#', '');
		GetE('bg').value = "0x"+color;
		GetE('bg').style.background = "#"+color ;
		//updatePreview()
	}
}
function SelectColor5()
{
	oEditor.FCKDialog.OpenDialog( 'FCKDialog_Color', oEditor.FCKLang.DlgColorTitle, 'dialog/fck_colorselector.html', 400, 

330, SelectTextColor5, window ) ;
}
function SelectTextColor6( color )
{
	if ( color && color.length > 0 ) {
		color=color.replace('#', '');
		GetE('slider').value = "0x"+color;
		GetE('slider').style.background = "#"+color ;
		//updatePreview()
	}
}
function SelectColor6()
{
	oEditor.FCKDialog.OpenDialog( 'FCKDialog_Color', oEditor.FCKLang.DlgColorTitle, 'dialog/fck_colorselector.html', 400, 

330, SelectTextColor6, window ) ;
}
function SelectTextColor7( color )
{
	if ( color && color.length > 0 ) {
		color=color.replace('#', '');
		GetE('track').value = "0x"+color;
		GetE('track').style.background = "#"+color ;
		//updatePreview()
	}
}
function SelectColor7()
{
	oEditor.FCKDialog.OpenDialog( 'FCKDialog_Color', oEditor.FCKLang.DlgColorTitle, 'dialog/fck_colorselector.html', 400, 

330, SelectTextColor7, window ) ;
}
function SelectTextColor8( color )
{
	if ( color && color.length > 0 ) {
		color=color.replace('#', '');
		GetE('rightbg').value = "0x"+color;
		GetE('rightbg').style.background = "#"+color ;
		//updatePreview()
	}
}
function SelectColor8()
{
	oEditor.FCKDialog.OpenDialog( 'FCKDialog_Color', oEditor.FCKLang.DlgColorTitle, 'dialog/fck_colorselector.html', 400, 

330, SelectTextColor8, window ) ;
}
function SelectTextColor9( color )
{
	if ( color && color.length > 0 ) {
		color=color.replace('#', '');
		GetE('rightbghover').value = "0x"+color;
		GetE('rightbghover').style.background = "#"+color ;
		//updatePreview()
	}
}
function SelectColor9()
{
	oEditor.FCKDialog.OpenDialog( 'FCKDialog_Color', oEditor.FCKLang.DlgColorTitle, 'dialog/fck_colorselector.html', 400, 

330, SelectTextColor9, window ) ;
}
function SelectTextColor10( color )
{
	if ( color && color.length > 0 ) {
		color=color.replace('#', '');
		GetE('righticon').value = "0x"+color;
		GetE('righticon').style.background = "#"+color ;
		//updatePreview()
	}
}
function SelectColor10()
{
	oEditor.FCKDialog.OpenDialog( 'FCKDialog_Color', oEditor.FCKLang.DlgColorTitle, 'dialog/fck_colorselector.html', 400, 

330, SelectTextColor10, window ) ;
}
function SelectTextColor11( color )
{
	if ( color && color.length > 0 ) {
		color=color.replace('#', '');
		GetE('righticonhover').value = "0x"+color;
		GetE('righticonhover').style.background = "#"+color ;
		//updatePreview()
	}
}
function SelectColor11()
{
	oEditor.FCKDialog.OpenDialog( 'FCKDialog_Color', oEditor.FCKLang.DlgColorTitle, 'dialog/fck_colorselector.html', 400, 

330, SelectTextColor11, window ) ;
}
function SelectTextColor12( color )
{
	if ( color && color.length > 0 ) {
		color=color.replace('#', '');
		GetE('border').value = "0x"+color;
		GetE('border').style.background = "#"+color ;
		//updatePreview()
	}
}
function SelectColor12()
{
	oEditor.FCKDialog.OpenDialog( 'FCKDialog_Color', oEditor.FCKLang.DlgColorTitle, 'dialog/fck_colorselector.html', 400, 

330, SelectTextColor12, window ) ;
}

/*******end********/

function makepreview(){
	
	var bg,leftbg,lefticon,rightbg,rightbghover,righticon,righticonhover,text,slider,track,border,loader;
	bg = "bg="+GetE('bg').value+"&amp;"; 
	leftbg = "leftbg="+GetE('leftbg').value+"&amp;"; 
	lefticon = "lefticon="+GetE('lefticon').value+"&amp;"; 
	rightbg = "rightbg="+GetE('rightbg').value+"&amp;"; 
	rightbghover = "rightbghover="+GetE('rightbghover').value+"&amp;"; 
	righticon = "righticon="+GetE('righticon').value+"&amp;"; 
	righticonhover = "righticonhover="+GetE('righticonhover').value+"&amp;"; 
	text = "text="+GetE('text').value+"&amp;"; 
	slider = "slider="+GetE('slider').value+"&amp;"; 
	track = "track="+GetE('track').value+"&amp;"; 
	border = "border="+GetE('border').value+"&amp;"; 
	loader = "loader="+GetE('loader').value+"&amp;"; 
	
	var Advance,titles,artists;
	Advance = bg+leftbg+lefticon+rightbg+rightbghover+righticon+righticonhover+text+slider+track+border+loader;
	titles="titles="+GetE('musicTitle').value+"&amp;";
	artists="artists="+GetE('musicArtist').value+"&amp;";
	
	var sAutoStart = GetE('autoStart').checked; 
	if (sAutoStart==true){ sAutoStart="autostart=yes&amp;" }else{ sAutoStart="autostart=no&amp;"; }	
	var sLoop = GetE('loop').checked;
	if (sLoop==true){ sLoop="loop=yes&amp;";}else{sLoop="loop=no&amp;"; }	
	var alwayOpen= GetE('alwayOpen').checked;
	if (alwayOpen==true){alwayOpen="animation=no&amp;";}else{alwayOpen="animation=yes&amp;";}
	
var temp="";
temp="<img src=\"mini.gif\" style=\"margin:0px 2px -3px 0px;\" border=\"0\"/><span style=\"font-size: smaller; \">"+FCKLang.KMPInfoShow+"</span><br />\n"; 
temp +="<object id=\"audioplayer0000\" data=\"player.swf\" width=\""+GetE('PlayerWidth').value+"\" height=\""+GetE('PlayerHeight').value+"\"  type=\"application/x-shockwave-flash\">"+"<param value=\"player.swf\" name=\"movie\" />"+"<param value=\"playerID=0000&amp;"+Advance+titles+artists+sAutoStart+sLoop+alwayOpen+"soundFile="+GetE('txtUrl').value+"\" name=\"FlashVars\" /><param value=\"high\" name=\"quality\" /><param value=\"false\" name=\"menu\" /><param value=\"transparent\" name=\"wmode\" /></object>";

//alert(temp);

document.getElementById('previewing').innerHTML=temp;
}

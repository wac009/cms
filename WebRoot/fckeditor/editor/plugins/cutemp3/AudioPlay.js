/** ****************************************************************************************************** */
/**
 * FCKeditor CuteMp3 Player Plugin For Fckeditor (Author: Lajox ; Email:
 * lajox@19www.com)
 */
/** ****************************************************************************************************** */

var dialog = window.parent;
var oEditor = window.parent.InnerDialogLoaded();
var FCK = oEditor.FCK;
var FCKLang = oEditor.FCKLang;
var FCKConfig = oEditor.FCKConfig;

window.onload = function() {
	oEditor.FCKLanguageManager.TranslatePage(document);
	window.parent.SetOkButton(true);
	loadCuteMp3Selection();

}

function loadCuteMp3Selection() {
	// Show/Hide according to settings
	ShowE('spanBrowse', FCKConfig.LinkBrowser);
}

/** Browse/upload a file on server */
function BrowseServer() {
	OpenFileBrowser(FCKConfig.MediaBrowserURL,
			FCKConfig.MediaBrowserWindowWidth,
			FCKConfig.MediaBrowserWindowHeight);
}

function Ok() {

	/** *自动播放 循环播放 显示下载 begin** */
	var sAutioPlay = "";
	var sAutoStart = GetE('autoStart').checked;
	if (sAutoStart == true) {
		sAutoStart = "&amp;autoStart=true";
	} else {
		sAutoStart = "&amp;autoStart=false";
	}

	var sLoop = GetE('repeatPlay').checked;
	if (sLoop == true) {
		sLoop = "&amp;repeatPlay=true";
	} else {
		sLoop = "&amp;repeatPlay=true";
	}

	var ShowDownload = GetE('showDownload').checked;
	if (ShowDownload == true) {
		ShowDownload = "&amp;showDownload=true";
	} else {
		ShowDownload = "&amp;showDownload=false";
	}
	/** *自动播放 循环播放 显示下载 end** */

	// 处理Mp3Url和Player Width及Song Volume信息
	var sMp3Url = GetE('Mp3Url').value;
	var PlayerWidth, PlayerVolume;
	PlayerWidth = "200";
	PlayerVolume = "100";
	if (GetE('PlayerWidth').value != "") {
		PlayerWidth = GetE('PlayerWidth').value;
	}
	if (GetE('songVolume').value != "") {
		PlayerVolume = GetE('songVolume').value;
	}

	// 处理背景颜色 和 进度条颜色
	var BackColor, FrontColor;
	BackColor = "000000";
	FrontColor = "ffffff";

	if (GetE('backColor').value != "") {
		BackColor = GetE('backColor').value;
	}
	if (GetE('frontColor').value != "") {
		FrontColor = GetE('frontColor').value;
	}

	// var
	// Mp3PlayerPath="editor/fckeditor/editor/plugins/cutemp3/mp3player.swf";
	// //CuteMp3 Player Path
	var Mp3PlayerPath = FCKConfig.PluginsPath + "cutemp3/mp3player.swf"; // CuteMp3
																			// Player
																			// Path

	if (sMp3Url.length > 0) {
		sAutioPlay = "<embed src=\""
				+ Mp3PlayerPath
				+ "?file="
				+ sMp3Url
				+ "&amp;songVolume="
				+ PlayerVolume
				+ "&amp;backColor="
				+ BackColor
				+ "&amp;frontColor="
				+ FrontColor
				+ sAutoStart
				+ sLoop
				+ ShowDownload
				+ "\" width=\""
				+ PlayerWidth
				+ "\" height=\"20\" type=\"application/x-shockwave-flash\"></embed>";

		oEditor.FCK.InsertHtml(sAutioPlay);
	} else {
		alert("Please input mp3 url !");
	}
	return true;
}

/** Set selected URL from Browser */
function SetUrl(url) {
	GetE('Mp3Url').value = url;
}

function SelectTextColor1(color) {
	if (color && color.length > 0) {
		GetE('backColor').style.background = color;
		putcolor = color.replace("#", "");
		GetE('backColor').value = putcolor;
		// updatePreview()
	}
}
function SelectColor1() {
	oEditor.FCKDialog.OpenDialog('FCKDialog_Color',
			oEditor.FCKLang.DlgColorTitle, 'dialog/fck_colorselector.html',
			400, 330, SelectTextColor1, window);
}
function SelectTextColor2(color) {
	if (color && color.length > 0) {
		GetE('frontColor').style.background = color;
		putcolor = color.replace("#", "");
		GetE('frontColor').value = putcolor;
		// updatePreview()
	}
}
function SelectColor2() {
	oEditor.FCKDialog.OpenDialog('FCKDialog_Color',
			oEditor.FCKLang.DlgColorTitle, 'dialog/fck_colorselector.html',
			400, 330, SelectTextColor2, window);
}

function makepreview() {
	var auto, loop, down;
	var sAutoStart = GetE('autoStart').checked;
	if (sAutoStart == true) {
		auto = "&amp;autoStart=true";
	} else {
		auto = "&amp;autoStart=false";
	}
	var sLoop = GetE('repeatPlay').checked;
	if (sLoop == true) {
		loop = "&amp;repeatPlay=true";
	} else {
		loop = "&amp;repeatPlay=true";
	}
	var ShowDownload = GetE('showDownload').checked;
	if (ShowDownload == true) {
		down = "&amp;showDownload=true";
	} else {
		down = "&amp;showDownload=false";
	}
	var temphtml = "";
	temphtml += "<embed src=\"mp3player.swf?file="
			+ GetE('Mp3Url').value
			+ "&amp;songVolume="
			+ GetE('songVolume').value
			+ "&amp;backColor="
			+ GetE('backColor').value
			+ "&amp;frontColor="
			+ GetE('frontColor').value
			+ auto
			+ loop
			+ down
			+ "\" width=\""
			+ GetE('PlayerWidth').value
			+ "\" height=\"20\" type=\"application/x-shockwave-flash\"></embed>";

	// alert(temphtml);
	document.getElementById('previewing').innerHTML = temphtml;
}
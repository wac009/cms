<?php
/**
 * FCKeditor保存远程图片插件 (Support: Lajox ; Email: lajox@19www.com)
 * 
 */
?>
<html>
<head>
<title>Save Remote Picture Properties</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="hiswing" content="noindex, nofollow" />
<style>
<!--
td {
font-size:10pt;
}
-->
</style>
<script type="text/javascript" src="../../dialog/common/fck_dialog_common.js"></script>
<link href="../../dialog/common/fck_dialog_common.css" type="text/css" rel="stylesheet">
<script language="javascript">
  var dialog = window.parent;
  var oEditor = dialog.InnerDialogLoaded();
  var FCKLang = oEditor.FCKLang;
  var xEditor = oEditor.FCK;
  dialog.SetOkButton(false);
  function Ok(){
	  return true;
	} 

window.onload = function()
{
	oEditor.FCKLanguageManager.TranslatePage(document) ;
	window.parent.SetOkButton( true ) ;

} 
</script>
</head> 
<body bgcolor="#E3E3C7" leftmargin="0" rightmargin="0">
<FORM METHOD=POST ACTION="save.php" name='savefiles' id='savefiles'>
<table width="100%" cellspacing="0" cellpadding="0">

<span id="x"></span>
<span id="t"></span>
<span id="y"></span>

</table>
</FORM>

<SCRIPT LANGUAGE="JavaScript">
<!--
document.getElementById("t").innerHTML="<span fckLang=\"DlgChecking\">Now Checking...</span>";

function getfiles()
{
	//var a=window.opener.FCK.EditorDocument.body.innerHTML;
	var a=xEditor.GetXHTML();
	//var re=/http:\/\/(\S*)+(net|com|cn|org|cc|tv)(\S*\/)(\S)+\.(gif|jpg|png|bmp|jpeg)/gi;
	//var re=/http:\/\/+(\S*)+(\S*\/)(\S)+\.(gif|jpg|png|bmp|jpeg)/gi;
	
	var re=/http:\/\/+(?!<?=$_SERVER['SERVER_NAME']?>)+(\S*\/)(\S)+\.(gif|jpg|png|bmp|jpeg)/gi;
	
	var url=a.match(re);
	if(url == null)
	{
		url="";
	}
	var ljflag=0;
	var s="";
	for(var i=0;i<url.length;i++)
	{
		for(var ljtemp=0;ljtemp<i;ljtemp++)
		{
			if(url[i]==url[ljtemp])
			{
				ljflag=1;
				break;
			}
			else
			{
				ljflag=0;
			}
		}
		if(!ljflag)
		{
					s=s+"<tr bgcolor=\"#F1F1E3\" height=\"20\"><td><input type=\"checkbox\" checked name=\"files[]\" value=\""+url[i]+"\"></td><td><a href=\""+url[i]+"\" target=\"_blank\">"+url[i]+"</a><br></td></tr>";
		}
	}

	document.getElementById("x").innerHTML="<tr height=\"20\"><td colspan=\"2\"><span fckLang=\"DlgPicToSave\">Choose Pictures to Save:</span> <BR></td></tr>";
	document.getElementById("t").innerHTML=s;
	document.getElementById("y").innerHTML="<tr height=\"20\"><td colspan=\"2\"><BR><input type=\"submit\" fckLang=\"DlgSaveBtn\" name=\"button1\" value=\"Save..\" style=\"color: #000000; border: 1px solid #737357; background-color: #C7C78F\"><br/><span fckLang=\"DlgMsgShow\">Saving files may take a long time.Please wait...</span></td></tr>";

	oEditor.FCKLanguageManager.TranslatePage(document) ;
}
setTimeout("getfiles()",3000);

//-->
</SCRIPT>

</body>
</html>
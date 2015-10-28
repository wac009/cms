FCKCommands.RegisterCommand('RunCode',new FCKDialogCommand( 'RunCode', FCKLang["RunCodeBtn"], FCKPlugins.Items['runcode'].Path + 'fck_runcode.html', 550, 420 ) ) ;
var oRunCode=new FCKToolbarButton('RunCode',null,FCKLang["RunCodeBtn"],null,false,true,FCKConfig.PluginsPath + 'runcode/runcode.gif');
FCKToolbarItems.RegisterItem('RunCode',oRunCode);

var FCKRunCode = new Object() ;

FCKRunCode.Add = function( cols,rows,txt )
{
var coText = FCK.CreateElement('DIV');
coText.className = 'codeText';

var tmpHtml="";

var rndnum = Math.floor((Math.random()*100000)).toString().substr(0,4);
var codeDiv = 'code_'+rndnum;

//txt = txt.replace(/ /g, '&nbsp;');
//txt = txt.replace(/\n/gm, '<br  />');
//txt = txt.replace(/<(br)\/?>/gi, '\n');

//var RunCode_Path = "editor/fckeditor/editor/plugins/runcode/";
var RunCode_Path = FCKConfig.PluginsPath + "runcode/";

var tempstr ="<link href='"+RunCode_Path+"runcode.css' type=\"text/css\" rel=\"stylesheet\"></link><script language=\"javascript\" src=\""+RunCode_Path+"runcode.js\"></script>";

	tmpHtml += "<textarea id='"+ codeDiv +"' cols=\""+cols+"\" rows=\""+rows+"\">\n"+txt+"\n</textarea>\n" + tempstr;
	tmpHtml +="<br/><input onclick=\"runCode('"+ codeDiv +"')\" type=\"button\" value=\""+FCKLang["RunCode"]+"\" class=\"codebutton\"><input type=\"button\" value=\""+FCKLang["CopyCode"]+"\" onclick=\"copyCode('"+ codeDiv +"')\" class=\"codebutton\"><input type=\"button\" value=\""+FCKLang["SaveCode"]+"\" onclick=\"saveCode('"+ codeDiv +"')\" class=\"codebutton\">&nbsp;"+FCKLang["HelpMsg"]+"";
	
	coText.innerHTML=tmpHtml;
}

/** Update Run HTML Code from Form */
function updateRunHTMLCode(e){
	e.cols = GetE('txtUrl').value;
	e.rows = GetE('txtUrl').value;
	e.innerHTML = GetE('txtUrl').value;
}


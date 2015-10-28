<?php
/*** FCKeditor保存远程图片插件 (Support: Lajox ; Email: lajox@19www.com) **/ 
?>
<?php
require_once './ServerXMLHTTP.php';

$root= $_SERVER['DOCUMENT_ROOT']; //当前运行脚本所在的文档根目录，即网站根目录的绝对路径
// $root = $root . '/blog'    //如果你的博客形式为 http://xxx.com/blog 则 去掉这行前面注释行号 // 即可

/****
可用的变量名：
  $_SERVER["DOCUMENT_ROOT"]、$_SERVER['APPL_PHYSICAL_PATH'] 均表示 网站根目录的绝对路径
  $_SERVER['REMOTE_HOST'] //当前页面用户的主机名
  $_SERVER['SCRIPT_FILENAME']  //当前执行脚本的绝对路径名。{如返回E:serverindex.php}
  $_SERVER['SCRIPT_NAME'] // 包含当前脚本的路径。这在页面需要指向自己时非常有用。
  __FILE__   //包含当前文件的绝对路径和文件名（例如包含文件）。
***/

//$saveFilePath = '../../../../../attachment/image' ;
//$displayUrl =   '/attachment/image';

//$saveFilePath = '/attachment/image' ;
//$displayUrl =   '/attachment/image';

$saveFilePath = $root . '/uploads/allimg'; //设置图片保存绝对路径
$displayUrl =   '/uploads/allimg'; //设置显示的链接地址

//$saveFilePath = 'F:/web/phpweb/attachment/image';  //如果你知道你的网站绝对路径（通过PHP探针得知）可用这种形式
?>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style>
<!--
body {
font-size:10pt;
}
-->
</style>
<body bgcolor="#E3E3C7" leftmargin="0" rightmargin="0">
<SCRIPT LANGUAGE="JavaScript">
  var dialog = window.parent;
  var oEditor = dialog.InnerDialogLoaded();
  var FCKLang = oEditor.FCKLang;
  var xEditor = oEditor.FCK;
  var a = xEditor.GetXHTML();
  dialog.SetOkButton(true);
  function Ok(){return true;}
<?php
	set_time_limit(0);
	$files=$_POST['files'];
	$fileNum=count($files);
	$realFileNum=0;
	$imgArray=array('.gif','.jpg','.png','.jpeg','.bmp');

	$typeArray=array();
	ob_start();
	for($i=0;$i<$fileNum;$i++)
	{
		$type=strrchr(trim($files[$i]),".");
		if($files[$i]!='' && in_array($type,$imgArray))
		{
			$now=time();
			srand((double)microtime()*1000000);   
			$randval  = rand(10000,99999); 
			//$randstr  =$now . $randval ; 
			$randstr  =date("YmdHis",$now). $randval;
			$filename=$randstr.strrchr(trim($files[$i]),".");
			//$filename=md5_file(trim($files[$i])).strrchr(trim($files[$i]),".");
			$savetime=SaveHTTPFile(trim($files[$i]),$saveFilePath,$filename);
?>
			a=a.replace("<?=trim($files[$i])?>","<?=$displayUrl.'/'.$filename?>");
<?php
		}
	}
	ob_end_flush();
?>
xEditor.SetHTML(a);
</script>
<font color=red>文件已经保存成功<br/>
</body>
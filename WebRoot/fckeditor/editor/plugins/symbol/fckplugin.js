/*
 * RichSpecialChar for FCKeditor
 * FCKeditor 的symbol 插件
 * 
 * Developed by  23-Feb-2007
 * 开发日期：2007年2月23日
 * 
 * 插件作用：
 * 取代FCKeditor官方SpecialChar插件，拥有非常丰富的各类特殊字符，特别是对汉语拼音的支持。
 * 
 * 插件特点：
 * 经测试，可以应用于IE7.0和Firefox2.0浏览器上，支持中英文。用户可以修改symbol.html文件，以获得个性化版本。
 * 
 * Authors:  洪汇成 (honghuicheng@gmail.com)
 * 作者：洪汇成 (honghuicheng@gmail.com)
 * 
 * INSTALLATION
 * 插件安装
 * 
 * a: unzip the files into your plugin folder.
 * 1、将压缩包解压到FCKeditor的插件文件夹里。
 *
 * b: add the following to your fckconfig.js.
 * 2、将以下代码添加到fckconfig.js文件里。
	FCKConfig.Plugins.Add( 'symbol','zh-cn,en' ) ;
 *
 * c: Then add the 'symbol' item in your toolbar.
 * 3、然后，添加symbol插件图标到FCKeditor工具栏上。
 * Example:
 * 譬如：
	FCKConfig.ToolbarSets["Default"] = [
		['Source','Preview','Templates'],
		['Copy','PasteText'],
		['Undo','Redo','-','Find','Replace','-','SelectAll','RemoveFormat','-','FitWindow'],
		['FontName','FontSize','TextColor','BGColor'],
		'/',
		['Bold','Italic','Underline','StrikeThrough','-','Subscript','Superscript'],
		['OrderedList','UnorderedList','-','Outdent','Indent'],
		['JustifyLeft','JustifyCenter','JustifyRight','JustifyFull'],
		['Link','Unlink'],
		['Image','Flash','Table','Rule','Smiley','symbol','About']
	] ;
 *
 * Good Luck.
 * 
*/

FCKCommands.RegisterCommand( 'symbol', new FCKDialogCommand( 'symbol', FCKLang.SymbolDlgTitle, FCKPlugins.Items['symbol'].Path + 'symbol.html', 480, 360 ) ) ;

var symbolObj = new FCKToolbarButton( 'symbol', FCKLang.SymbolDlgTitle, FCKLang.SymbolDlgTitle, null, null, false, true) ;
symbolObj.IconPath = FCKPlugins.Items['symbol'].Path + 'symbol.gif' ;

FCKToolbarItems.RegisterItem( 'symbol', symbolObj ) ;


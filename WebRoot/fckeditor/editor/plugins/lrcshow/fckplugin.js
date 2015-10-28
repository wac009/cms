/*********************************************************************************************************/
/**
 * LrcShow (Music Player with Lyrics Rolling) Plugin (Author: Lajox ; Email: lajox@19www.com)
 */
/*********************************************************************************************************/


FCKCommands.RegisterCommand('LrcShow', new FCKDialogCommand('LrcShow', FCKLang.LrcShow, FCKPlugins.Items['lrcshow'].Path +'LrcShow.html', 450,300)) ;
var LRCItem = new FCKToolbarButton('LrcShow', FCKLang.LrcShow) ;
LRCItem.IconPath = FCKPlugins.Items['lrcshow'].Path +'lrcshow.gif';
FCKToolbarItems.RegisterItem('LrcShow', LRCItem);

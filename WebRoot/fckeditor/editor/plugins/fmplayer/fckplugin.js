/*********************************************************************************************************/
/**
 * FCKeditor Cute MP3 Player Plugin For Fckeditor (Author: Lajox ; Email: lajox@19www.com)
 */
/*********************************************************************************************************/


FCKCommands.RegisterCommand('fmplayer', new FCKDialogCommand('fmplayer', FCKLang.fmplayer, FCKPlugins.Items['fmplayer'].Path +'flashPlayer.html', 460,280)) ;
var FMItem = new FCKToolbarButton('fmplayer', FCKLang.fmplayer) ;
FMItem.IconPath = FCKPlugins.Items['fmplayer'].Path +'mp3.gif';
FCKToolbarItems.RegisterItem('fmplayer', FMItem);

/*********************************************************************************************************/
/**
 * FCKeditor Dewplayer MP3 Plugin (Author: Lajox ; Email: lajox@19www.com)
 */
/*********************************************************************************************************/

FCKCommands.RegisterCommand('DewPlayer', new FCKDialogCommand('DewPlayer', FCKLang.DewPlayer, FCKPlugins.Items['dewplayer'].Path +'DewPlayer.html', 440,340)) ;
var DWItem = new FCKToolbarButton('DewPlayer', FCKLang.DewPlayer) ;
DWItem.IconPath = FCKPlugins.Items['dewplayer'].Path +'dewplayer.gif';
FCKToolbarItems.RegisterItem('DewPlayer', DWItem);
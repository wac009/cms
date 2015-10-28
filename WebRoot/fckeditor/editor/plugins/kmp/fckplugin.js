/*********************************************************************************************************/
/**
 * FCKeditor Flash MP3 Player (AudioPlayer) Plugin For Fckeditor (Support: Lajox ; Email: lajox@19www.com)
 */
/*********************************************************************************************************/


FCKCommands.RegisterCommand('KMP', new FCKDialogCommand('KMP', FCKLang.KMP, FCKPlugins.Items['kmp'].Path +'wpAudioPlay.html', 450,450)) ;
var KMPItem = new FCKToolbarButton('KMP', FCKLang.KMP) ;
KMPItem.IconPath = FCKPlugins.Items['kmp'].Path +'kmp.gif';
FCKToolbarItems.RegisterItem('KMP', KMPItem);

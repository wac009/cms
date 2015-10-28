/*********************************************************************************************************/
/**
 * FCKeditor Cute MP3 Player Plugin For Fckeditor (Author: Lajox ; Email: lajox@19www.com)
 */
/*********************************************************************************************************/


FCKCommands.RegisterCommand('CuteMp3', new FCKDialogCommand('CuteMp3', FCKLang.cutemp3, FCKPlugins.Items['cutemp3'].Path +'Mp3Play.html', 430,300)) ;
var CuteMP3Item = new FCKToolbarButton('CuteMp3', FCKLang.cutemp3) ;
CuteMP3Item.IconPath = FCKPlugins.Items['cutemp3'].Path +'mp3.gif';
FCKToolbarItems.RegisterItem('CuteMp3', CuteMP3Item);

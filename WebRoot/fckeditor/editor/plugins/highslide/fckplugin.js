/*********************************************************************************************************/
/**
 * FCKeditor Highslide JS Plugin For Fckeditor (Author: Lajox ; Email: lajox@19www.com)
 */
/*********************************************************************************************************/


FCKCommands.RegisterCommand('HighSlide', new FCKDialogCommand('HighSlide', FCKLang.HighSlideJS, FCKPlugins.Items['highslide'].Path +'highslide.html', 380,180)) ;
var HSJS = new FCKToolbarButton('HighSlide', FCKLang.HighSlideJS) ;
HSJS.IconPath = FCKPlugins.Items['highslide'].Path +'hs.gif';
FCKToolbarItems.RegisterItem('HighSlide', HSJS);

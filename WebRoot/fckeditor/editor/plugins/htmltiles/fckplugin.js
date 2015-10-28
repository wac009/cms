/*
 * FCKeditor - The text editor for internet
 * Copyright (C) 2003-2005 Frederico Caldeira Knabben
 * 
 * Licensed under the terms of the GNU Lesser General Public License:
 * 		http://www.opensource.org/licenses/lgpl-license.php
 * 
 * For further information visit:
 * 		http://www.fckeditor.net/
 * 
 * File Name: fckplugin.js
 * 	Plugin to insert predefined html tiles into the editor without erasing other contents.
 * 
 * File Authors:
 * 		Marc Steffen Schomberg (steffen{at}schomberg.net)
 */

/*********************************************************************************************************/
/**
 * 插入HTML模板 for Fckeditor (Support: Lajox ; Email: lajox@19www.com)
 */
/*********************************************************************************************************/


// Register the related command.
FCKCommands.RegisterCommand( 'HtmlTiles', new FCKDialogCommand( 'HtmlTiles', FCKLang.HtmlTilesDlgTitle, FCKPlugins.Items['htmltiles'].Path + 'fck_htmltiles.html', 340, 170 ) ) ;

// Create the "HtmlTiles" toolbar button.
var oHtmlTilesItem = new FCKToolbarButton( 'HtmlTiles', FCKLang.HtmlTilesBtn ) ;
oHtmlTilesItem.IconPath = FCKPlugins.Items['htmltiles'].Path + 'htmltiles.gif' ;
FCKToolbarItems.RegisterItem( 'HtmlTiles', oHtmlTilesItem ) ;

// The object used for all HtmlTiles operations.
var FCKHtmlTiles = new Object() ;

// Insert a new html block at the actual selection.
FCKHtmlTiles.Insert = function( html )
{
	FCK.InsertHtml(html);
}

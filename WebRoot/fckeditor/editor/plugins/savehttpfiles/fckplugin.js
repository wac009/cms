﻿/*
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
 * 	Plugin to insert "savehttpfiless" in the editor.
 * 
 * File Authors:
 * 		Frederico Caldeira Knabben (fredck@fckeditor.net)
 */

/*
var SaveHTTPFilesCommond = function() {this.Name = 'savehttpfiles';}
SaveHTTPFilesCommond.prototype.Execute = function () {window.open(FCKPlugins.Items['savehttpfiles'].Path+'gethttpfiles.php','openwindow','width=500,height=420,scrollbars=yes,resizable=yes');}
SaveHTTPFilesCommond.prototype.GetState = function() {return FCK_TRISTATE_OFF;}
*/
// Register the related command.
//FCKCommands.RegisterCommand( 'savehttpfiles',new SaveHTTPFilesCommond()) ;
/*
FCKCommands.RegisterCommand(
	'savehttpfiles',new FCKDialogCommand(
		'savehttpfiles',FCKConfig.PluginsPath,FCKConfig.PluginsPath+'savehttpfiles/gethttpfiles.php',500,420)
);
*/

/*********************************************************************************************************/
/**
 * FCKeditor保存远程图片插件 (Support: Lajox ; Email: lajox@19www.com)
 */
/*********************************************************************************************************/



FCKCommands.RegisterCommand(
	'savehttpfiles',new FCKDialogCommand(
	'savehttpfiles',FCKLang['SaveHttpFilesDlgTitle'],FCKPlugins.Items['savehttpfiles'].Path+'gethttpfiles.php',500,420)
);

// Create the "savehttpfiles" toolbar button.
var osavehttpfilesItem = new FCKToolbarButton( 'savehttpfiles', FCKLang['SaveHttpFilesBtn'] ) ;
osavehttpfilesItem.IconPath = FCKPlugins.Items['savehttpfiles'].Path + 'savehttpfiles.gif' ;
FCKToolbarItems.RegisterItem( 'savehttpfiles', osavehttpfilesItem ) ;


//// The object used for all savehttpfiles operations.
var FCKsavehttpfiless = new Object() ;

//// Add a new savehttpfiles at the actual selection.
FCKsavehttpfiless.Add = function( name )
{
	var oSpan = FCK.CreateElement( 'savehttpfiles' ) ;
	this.SetupSpan( oSpan, name ) ;
}

FCKsavehttpfiless.SetupSpan = function( span, name )
{
	span.innerHTML = '[[ ' + name + ' ]]' ;

	span.style.backgroundColor = '#ffff00' ;
	span.style.color = '#000000' ;

	if ( FCKBrowserInfo.IsGecko )
		span.style.cursor = 'default' ;

	span._fcksavehttpfiles = name ;
	span.contentEditable = false ;

	// To avoid it to be resized.
	span.onresizestart = function()
	{
		FCK.EditorWindow.event.returnValue = false ;
		return false ;
	}
}


/*
 * For FCKeditor 2.6
 * 
 * File Name: fckplugin.js
 * 	Add a toolbar button to Parapraph.
 * 
 * File Authors:
 * 		lurocky
 */


// Register the related commands.
FCKCommands.RegisterCommand("Paragraph", new FCKDialogCommand(FCKLang["DlgPar"], FCKLang["DlgPar"], FCKConfig.PluginsPath + "paragraph/paragraph.html", 260, 300));

// Create the "Parapraph" toolbar button.
var ParapraphItem = new FCKToolbarButton("Paragraph", FCKLang["DlgPar"]);
ParapraphItem.IconPath = FCKConfig.PluginsPath + "paragraph/paragraph.gif";
FCKToolbarItems.RegisterItem("Paragraph", ParapraphItem);			// 'Parapraph' is the name used in the Toolbar config.
function FCK_ContextMenu_GetListener_Paragraph() {
  return {AddItems:function (menu, tag, tagName) {
    var range = new FCKDomRange(FCK.EditorWindow);
    range.MoveToSelection();
    var bookmark = range.CreateBookmark();
    var iterator = new FCKDomRangeIterator(range);
    var block;
    var hasParapraph = false;
    while ((block = iterator.GetNextParagraph())) {
      hasParapraph = true;
    }
    range.MoveToBookmark(bookmark);
    range.Select();
    if (hasParapraph) {
      menu.AddSeparator();
      menu.AddItem("Paragraph", FCKLang["DlgPar"], ParapraphItem.IconPath);
    }
  }};
}
FCK.ContextMenu.RegisterListener(FCK_ContextMenu_GetListener_Paragraph());


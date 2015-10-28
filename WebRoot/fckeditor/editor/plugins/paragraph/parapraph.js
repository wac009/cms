var dialog    = window.parent ;
var oEditor   = dialog.InnerDialogLoaded() ;
var FCK     = oEditor.FCK ;
var FCKConfig = oEditor.FCKConfig ;
var FCKTools = oEditor.FCKTools ;

var Styles = new Array();

window.onload = function()
{
	oEditor.FCKLanguageManager.TranslatePage(document) ;
	LoadSelection();
	dialog.SetAutoSize( true ) ;
	dialog.SetOkButton( true ) ;
};

function LoadSelection()
{
    dialog.Selection.EnsureSelection();//for IE
    var range = new oEditor.FCKDomRange( FCK.EditorWindow ) ;
    range.MoveToSelection() ;
    var bookmark = range.CreateBookmark() ;
	var iterator = new oEditor.FCKDomRangeIterator( range ) ;
	var block ;
	var i = 0;
	while ( ( block = iterator.GetNextParagraph() ) )
	{
			Styles[Styles.length] = block.style ;
	}
    range.MoveToBookmark( bookmark ) ;
    range.Select() ;
	var sMarginTop = "";
	var sMarginBottom = "";
	var sMarginLeft = "";
	var sMarginRight = "";
	var sTextAlign = "";
	var sLineHeight = "";
	var sTextIndent = "";
	var sLetterSpacing = "";
	for ( i = 0; i < Styles.length; i = i + 1)
	{
		if ( i == 0 )
		{
			sMarginTop = Styles[i].marginTop;
			sMarginBottom = Styles[i].marginBottom;
			sMarginLeft = Styles[i].marginLeft;
			sMarginRight = Styles[i].marginRight;
			sTextAlign = Styles[i].textAlign ;
			sLineHeight = Styles[i].lineHeight;
			sTextIndent = Styles[i].textIndent;
			sLetterSpacing = Styles[i].letterSpacing;
		}
		else
		{
			if ( Styles[i].marginTop != sMarginTop ) sMarginTop = "";
			if ( Styles[i].marginBottom != sMarginBottom ) sMarginBottom = "";
			if ( Styles[i].marginLeft != sMarginLeft ) sMarginLeft = "";
			if ( Styles[i].marginRight != sMarginRight ) sMarginRight = "";
			if ( Styles[i].textAlign != sTextAlign ) sTextAlign = "";
			if ( Styles[i].lineHeight != sLineHeight ) sLineHeight = "";
			if ( Styles[i].textIndent != sTextIndent ) sTextIndent = "";
			if ( Styles[i].letterSpacing != sLetterSpacing ) sLetterSpacing = "";
		}
	}
	GetE('d_align').value    = sTextAlign ;
	GetE('d_margintop').value    = sMarginTop ;
	GetE('d_marginbottom').value    = sMarginBottom ;
	GetE('d_marginleft').value    = sMarginLeft ;
	GetE('d_marginright').value    = sMarginRight ;
	GetE('d_lineheight').value    = sLineHeight ;
	GetE('d_textindent').value    = sTextIndent ;
	GetE('d_letterspacing').value    = sLetterSpacing ;
}

//#### The OK button was hit.
function Ok()
{
	sMarginTop = GetE('d_margintop').value;
	sMarginBottom = GetE('d_marginbottom').value;
	sMarginLeft = GetE('d_marginleft').value;
	sMarginRight = GetE('d_marginright').value;
	sTextAlign = GetE('d_align').value;
	sLineHeight = GetE('d_lineheight').value;
	sTextIndent = GetE('d_textindent').value;
	sLetterSpacing = GetE('d_letterspacing').value;
	
	oEditor.FCKUndo.SaveUndoStep() ;
	for ( i = 0; i < Styles.length; i = i + 1)
	{
		Styles[i].marginTop = GetE('d_margintop').value;
		Styles[i].marginBottom = GetE('d_marginbottom').value;
		Styles[i].marginLeft = GetE('d_marginleft').value;
		Styles[i].marginRight = GetE('d_marginright').value;
		Styles[i].textAlign = GetE('d_align').value;
		Styles[i].lineHeight = GetE('d_lineheight').value;
		Styles[i].textIndent = GetE('d_textindent').value;
		Styles[i].letterSpacing = GetE('d_letterspacing').value;
	}
	return true;
}

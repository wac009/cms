
// Register the related command.
FCKCommands.RegisterCommand( 'Attachment', new FCKDialogCommand( 'Attachment', FCKLang.AttachmentDlgTitle, FCKPlugins.Items['Attachment'].Path + 'fck_attachment.html', 340, 160 ) ) ;

// Create the "Attachment" toolbar button.
var oAttachmentItem = new FCKToolbarButton( 'Attachment', FCKLang.AttachmentBtn ) ;
oAttachmentItem.IconPath = FCKPlugins.Items['Attachment'].Path + 'attachment.gif' ;

FCKToolbarItems.RegisterItem( 'Attachment', oAttachmentItem ) ;


// The object used for all Attachment operations.
var FCKAttachment = new Object() ;

// Add a new attachment at the actual selection.
FCKAttachment.Add = function( link, name )
{
	if(!name) {
		name=link;
	}
	FCK.InsertHtml("<a href='"+link+"'>"+name+"</a>") ; 
}

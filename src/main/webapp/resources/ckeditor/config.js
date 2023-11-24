/**
 * @license Copyright (c) 2003-2015, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here. For example:
	//config.language = 'ko';
	// config.uiColor = '#AADC6E';
	
	config.pasteFromWordPromptCleanup = true;
	config.pasteFromWordRemoveFontStyles = false;
	config.pasteFromWordRemoveStyles = false;


	var urlArr = location.href.split('/');
	var len = urlArr.length;
	config.filebrowserImageUploadUrl = '/file/ckeditorFileupload?path='+urlArr[len-2];
    config.skin = 'moono-lisa'
	config.height = 600;
	config.DefaultLanguage = 'ko';  
//	config.contentsLangDirection = 'ltr';
//	config.startupFocus = false;
//	config.forcePasteAsPlainText = false;
	config.removeFormatTags = 'b,big,code,del,dfn,em,font,i,ins,kbd,sciprt';
//	CKEDITOR.config.forceSimpleAmpersand = false;
//	config.tabSpaces = 0;
//	config.toolbarCanCollapse = true;
//	config.toolbarStartupExpanded = true;
//	config.ignoreEmptyParagraph = true;
//	config.baseFloatZIndex = 10000;
	//config.htmlEncodeOutput = false;
	
//	config.protectedSource.push( /<\?[\div\DIV]*?\?>/g ); 
//	config.protectedSource.push( /<\?[\(.*?)]*?\?>/g );
//
//	config.startupShowBorders = true;
//	config.templates_replaceContent = true;
////	config.toolbarLocation = 'In';  
	config.enterMode = CKEDITOR.ENTER_P;
//	
//    config.basicEntities = true;
//    config.entities = true;
//    config.entities_latin = false;
//    config.entities_greek = false;
//    config.entities_processNumerical = false;
//    config.fillEmptyBlocks = function (element) {
//            return true; // DON'T DO ANYTHING!!!!!
//    };

    CKEDITOR.config.allowedContent = true; // don't filter my data
	config.toolbar =[
						[ 'Bold', 'Italic', 'Underline', 'Strike', '-',
						  'TextColor', 'BGColor', '-',
						  'JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock', '-',
						  'Link', 'Unlink', '-',
						  'Find', 'Replace', '-',
						  'Image', 'Table', 'Smiley', 'SpecialChar'
						],
						[ 'Font', 'FontSize', '-',
						  'Source', 'Maximize', 'About'
						]
					];

	config.keystrokes = [
							[ CKEDITOR.CTRL + 65 /*A*/, true ],
							[ CKEDITOR.CTRL + 67 /*C*/, true ],
							[ CKEDITOR.CTRL + 70 /*F*/, true ],
							[ CKEDITOR.CTRL + 83 /*S*/, true ],
							[ CKEDITOR.CTRL + 84 /*T*/, true ],
							[ CKEDITOR.CTRL + 88 /*X*/, true ],
							[ CKEDITOR.CTRL + 86 /*V*/, 'Paste' ],
							[ CKEDITOR.CTRL + 45 /*INS*/, true ],
							[ CKEDITOR.SHIFT + 45 /*INS*/, 'Paste' ],
							[ CKEDITOR.CTRL + 88 /*X*/, 'Cut' ],
							[ CKEDITOR.SHIFT + 46 /*DEL*/, 'Cut' ],
							[ CKEDITOR.CTRL + 90 /*Z*/, 'Undo' ],
							[ CKEDITOR.CTRL + 89 /*Y*/, 'Redo' ],
							[ CKEDITOR.CTRL + CKEDITOR.SHIFT + 90 /*Z*/, 'Redo' ],
							[ CKEDITOR.CTRL + 76 /*L*/, 'Link' ], 
							[ CKEDITOR.CTRL + 66 /*B*/, 'Bold' ],
							[ CKEDITOR.CTRL + 73 /*I*/, 'Italic' ],
							[ CKEDITOR.CTRL + 85 /*U*/, 'Underline' ],
							[ CKEDITOR.CTRL + CKEDITOR.SHIFT + 83 /*S*/, 'Save' ],
							[ CKEDITOR.CTRL + CKEDITOR.ALT + 13 /*ENTER*/, 'FitWindow' ]
		                 ];
		config.coreStyles_bold = {element: 'strong', overrides: 'b'};
//		config.browserContextMenuOnCtrl = false;  
		config.font_names = '굴림;굴림체;궁서;궁서체;돋움;돋움체;바탕;바탕체;Arial;Comic Sans MS;Courier New;Tahoma;Times New Roman;Verdana' ;
		config.fontSize_sizes = "8pt;9pt;10pt;11pt;12pt;13pt;14pt;15pt;16pt;17pt;18pt;19pt;20pt;24pt;36pt";
//		config.resize_enabled = false;
//		config.disableNativeTableHandles = false;

		CKEDITOR.on('dialogDefinition', function(ev) {
            var editor = ev.editor;
		    var dialogName = ev.data.name;
		    var dialogDefinition = ev.data.definition;
		
		    if (dialogName == 'image') {
                dialogDefinition.dialog.on('show', function(e){
                    var dialogBox = e.sender;
		            // This code will open the Upload tab.
                    dialogBox.originalElement = editor.getSelection().getStartElement();
                    this.selectPage('Upload');
		        });
		    }
		});
};

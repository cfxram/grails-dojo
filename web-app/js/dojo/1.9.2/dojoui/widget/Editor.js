/*
  Findings: (1-26-2012) version 1.7.1 of Dojo.

  If EnterKeyHandling (EKH) is off, IE uses <p> tags. Safari uses <div>. Firefox uses <br>.
  If EKH is on, they all use <br> but IE bullets break. (not to mention browser crash issue)
  IF EKH is on and set to use <p> or <div>, then js error when hitting enter on a bullet item (.apply is not a function).

  In IE, put cursor at begging of line and hit bullet... extra <br> tag is entered. But it works fine if cursor is anywhere else on the line.

  dojox.editor.plugins.AutoUrlLink doesn't work in FF 9. Works in Safari but throws an error. ('null' is not an object (evaluating '_b.nodeType'))
 */
define(["dojo/_base/declare", "dijit/Editor", "dijit/_editor/plugins/TextColor", "dijit/_editor/plugins/LinkDialog", "dijit/_editor/plugins/ViewSource", "dijit/_editor/plugins/FontChoice", "dojox/editor/plugins/PasteFromWord", "./EnterKeyHandling", "dojox/editor/plugins/AutoUrlLink", "dojo/dom-construct", "dojo/dom-style", "dojo/sniff", "dojo/query", "dojo/dom", "dojo/_base/lang"],
function(declare, Editor, TextColor, LinkDialog, ViewSource, FontChoice, PasteFromWord, EnterKeyHandling, AutoUrlLink, domc, domStyle, has, query, dom, lang){
	return declare("dojoui.widget.Editor", Editor, {
		editorFormField:null,
		debug:false,
		type:"", // simple || intermediate || advanced

		/**
		 * Fires after widget is rendered.
		 */
		postCreate: function(){
			this.definePlugins();
			this.inherited(arguments);

			/*
			When a user type 2 spaces in a row the editor does a char(32) [space] and a char(160)[non breaking space].
			If the user's browser is not in a character encoding the understands char(160), then the character will
			get submitted as an unknown character. This code replaces char(160) to the "&nbsp;" string so this wont happen.
			 */
			this.contentPostFilters = [
				function(str){return str.replace(/\xA0/gi, '&nbsp;')}
			];	
		},
		
		startup: function(){
			this.inherited(arguments);

			// This is for the iPad. When a user clicks anywhere in on the iFrame,
			// then focus on the editor inside of the iFrame.
			this.iframe.onclick = this.focus;
		},

		/**
		 * Will get the value of the hidden form field.
		 */
		getContent:function(){
			return this.editorFormField.value;
		},


		/**
		 * Will update the hidden form field with the current value.
		 */
		updateFormField:function(){
			if(!this.editorFormField){
				this.createHiddenFormField();
			}
			this.editorFormField.value = this.get('value');
		},


		/**
		 * Will insert content at the place of the cursor
		 * @param content
		 */
		insertAtCursor:function(content){
			this.execCommand("inserthtml", content);
		},


		/**
		 * Only used for debuging.
		 */
		outputCharacters:function(){
		    console.log('Editor Characters....');
		    var value = this.get('value');
		    for (i=0; i < value.length; i++){
		      console.log(value.charCodeAt(i));
		    }
		    console.log('Field Characters....');
		    var value = this.editorFormField.value;
		    for (i=0; i < value.length; i++){
		      console.log(value.charCodeAt(i));
		    }
		},


		/**
		 * Event fired when a user changes something. Fires when user types mostly.
		 * This doesn't work for pasteing text via keyboard or mouse.
		 */
		onDisplayChanged:function(){
			this.inherited(arguments);
			this.updateFormField();
		},



		/**
		 * Need this for when a user pastes text using the keyboard or file menu.
		 * This will update the hidden form field faster than the onDisplayChanged can.
		 */
		_onBlur:function(){
			this.inherited(arguments);
			this.updateFormField();
		},


		/**
		 * Will create the hidden form field that will store the data.
		 */
		createHiddenFormField:function(){
			if(this.editorFormField){
				return;
			}
			var attrs = {id:this.id+'-formField', name:this.id};
			if(!this.debug){
				attrs['class'] = 'dijitHidden';
			} else {
				attrs['style'] = {width: '400px', height: '200px'};
			}
		    this.editorFormField = domc.create("textarea", attrs);
		    domc.place(this.editorFormField, this.domNode, "after");
		},


		/**
		 * Load a predefined set of plugins based on 3 levels:
		 * simple || intermediate || advanced.
		 *
		 * If this.type is left blank then the dojo default plugin set will
		 * be used.
		 */
		definePlugins:function(){
		    if(this.type === "simple"){
		      this.plugins = [
		        'bold', 'italic', 'underline', 'strikethrough', '|',
		        'insertOrderedList', 'insertUnorderedList', '|',
		        'indent', 'outdent', '|',
		        'justifyLeft', 'justifyRight', 'justifyCenter', '|',
		        'pastefromword',
		        {name:'dojoui.widget.EnterKeyHandling',blockNodeForEnter:'P'},
		        'dojox.editor.plugins.AutoUrlLink'
		      ];
		    }

		    else if(this.type === "intermediate"){
		      this.plugins = [
		        'bold', 'italic', 'underline', 'strikethrough', '|',
		        'insertOrderedList', 'insertUnorderedList', '|',
		        'indent', 'outdent', '|',
		        'justifyLeft', 'justifyRight', 'justifyCenter', '|',
		        'pastefromword', '|',
		        'fontName', 'fontSize', '|',
		        'createLink',
		        {name:'dojoui.widget.EnterKeyHandling',blockNodeForEnter:'P'},
		        'dojox.editor.plugins.AutoUrlLink'
		      ];
		    }

		    else if(this.type === "advanced"){
		      this.plugins = [
		        'undo', 'redo', '|',
		        'bold', 'italic', 'underline', 'strikethrough', '|',
		        'insertOrderedList', 'insertUnorderedList', '|',
		        'indent', 'outdent', '|',
		        'justifyLeft', 'justifyRight', 'justifyCenter', '|',
		        'pastefromword', '|',
		        'fontName', 'fontSize', '|',
		        'foreColor', 'hiliteColor', '|',
		        'createLink',
		        {name:'dojoui.widget.EnterKeyHandling',blockNodeForEnter:'P'},
		        'dojox.editor.plugins.AutoUrlLink'
		      ];
		    }
		},


		/**
		 * Copied from RichText.js
		 *
		 * THis is overridden so we can get the iPad working. Just had to define the height of the div area to be larger than
		 * 1 line higher.
		 */
		_getIframeDocTxt: function(){
			// summary:
			//		Generates the boilerplate text of the document inside the iframe (ie, `<html><head>...</head><body/></html>`).
			//		Editor content (if not blank) should be added afterwards.
			// tags:
			//		private
			var _cs = domStyle.getComputedStyle(this.domNode);

			// The contents inside of <body>.  The real contents are set later via a call to setValue().
			var html = "";
			var setBodyId = true;
			if(has("ie") || has("webkit") || (!this.height && !has("mozilla"))){
				// In auto-expand mode, need a wrapper div for AlwaysShowToolbar plugin to correctly
				// expand/contract the editor as the content changes.
				html = "<div id='dijitEditorBody'></div>";
				setBodyId = false;
			}else if(has("mozilla")){
				// workaround bug where can't select then delete text (until user types something
				// into the editor)... and/or issue where typing doesn't erase selected text
				this._cursorToStart = true;
				html = "&#160;";	// &nbsp;
			}

		    /** ----------------------------------------------------- **/
		    /** ----------------------------------------------------- **/
		    /** ----  Begin: iOS Customization ---- **/
		    if(has("ios")){
		      html = "<div id='dijitEditorBody' style='height:99%'></div>";
		    }
		    /** ----  End: iOS Customization ---- **/
		    /** ----------------------------------------------------- **/
		    /** ----------------------------------------------------- **/

			var font = [ _cs.fontWeight, _cs.fontSize, _cs.fontFamily ].join(" ");
			
			// line height is tricky - applying a units value will mess things up.
			// if we can't get a non-units value, bail out.
			var lineHeight = _cs.lineHeight;
			if(lineHeight.indexOf("px") >= 0){
				lineHeight = parseFloat(lineHeight) / parseFloat(_cs.fontSize);
				// console.debug(lineHeight);
			}else if(lineHeight.indexOf("em") >= 0){
				lineHeight = parseFloat(lineHeight);
			}else{
				// If we can't get a non-units value, just default
				// it to the CSS spec default of 'normal'.  Seems to
				// work better, esp on IE, than '1.0'
				lineHeight = "normal";
			}
			var userStyle = "";
			var self = this;
			this.style.replace(/(^|;)\s*(line-|font-?)[^;]+/ig, function(match){
				match = match.replace(/^;/ig, "") + ';';
				var s = match.split(":")[0];
				if(s){
					s = lang.trim(s);
					s = s.toLowerCase();
					var i;
					var sC = "";
					for(i = 0; i < s.length; i++){
						var c = s.charAt(i);
						switch(c){
							case "-":
								i++;
								c = s.charAt(i).toUpperCase();
							default:
								sC += c;
						}
					}
					domStyle.set(self.domNode, sC, "");
				}
				userStyle += match + ';';
			});

			// need to find any associated label element, aria-label, or aria-labelledby and update iframe document title
			var label = query('label[for="' + this.id + '"]');
			var title = "";
			if(label.length){
				title = label[0].innerHTML;
			}else if(this["aria-label"]){
				title = this["aria-label"];
			}else if(this["aria-labelledby"]){
				title = dom.byId(this["aria-labelledby"]).innerHTML;
			}

			// Now that we have the title, also set it as the title attribute on the iframe
			this.iframe.setAttribute("title", title);

			return [
				"<!DOCTYPE html>",
				this.isLeftToRight() ? "<html lang='" + this.lang + "'>\n<head>\n" : "<html dir='rtl' lang='" + this.lang + "'>\n<head>\n",
				//(has("mozilla") && label.length ? "<title>" + label[0].innerHTML + "</title>\n" : ""),
				title ? "<title>" + title + "</title>" : "",
				"<meta http-equiv='Content-Type' content='text/html'>\n",
				"<style>\n",
				"\tbody,html {\n",
				"\t\tbackground:transparent;\n",
				"\t\tpadding: 1px 0 0 0;\n",
				"\t\tmargin: -1px 0 0 0;\n", // remove extraneous vertical scrollbar on safari and firefox
				"\t}\n",
				"\tbody,html, #dijitEditorBody{ outline: none; }",

				// Set <body> to expand to full size of editor, so clicking anywhere will work.
				// Except in auto-expand mode, in which case the editor expands to the size of <body>.
				// Also determine how scrollers should be applied.  In autoexpand mode (height = "") no scrollers on y at all.
				// But in fixed height mode we want both x/y scrollers.
				// Scrollers go on <body> since it's been set to height: 100%.
				"html { height: 100%; width: 100%; overflow: hidden; }\n",	// scroll bar is on <body>, shouldn't be on <html>
				this.height ? "\tbody { height: 100%; width: 100%; overflow: auto; }\n" :
					"\tbody { min-height: " + this.minHeight + "; width: 100%; overflow-x: auto; overflow-y: hidden; }\n",

				// TODO: left positioning will cause contents to disappear out of view
				//	   if it gets too wide for the visible area
				"\tbody{\n",
				"\t\ttop:0px;\n",
				"\t\tleft:0px;\n",
				"\t\tright:0px;\n",
				"\t\tfont:", font, ";\n",
				((this.height || has("opera")) ? "" : "\t\tposition: fixed;\n"),
				"\t\tline-height:", lineHeight, ";\n",
				"\t}\n",
				"\tp{ margin: 1em 0; }\n",

				"\tli > ul:-moz-first-node, li > ol:-moz-first-node{ padding-top: 1.2em; }\n",
				// Can't set min-height in IE9, it puts layout on li, which puts move/resize handles.
				(!has("ie") ? "\tli{ min-height:1.2em; }\n" : ""),
				"</style>\n",
				this._applyEditingAreaStyleSheets(), "\n",
				"</head>\n<body role='main' ",
				(setBodyId ? "id='dijitEditorBody' " : ""),

				// Onload handler fills in real editor content.
				// On IE9, sometimes onload is called twice, and the first time frameElement is null (test_FullScreen.html)
				"onload='frameElement && frameElement._loadFunc(window,document)' ",
				"style='" + userStyle + "'>", html, "</body>\n</html>"
			].join(""); // String
		}
	});
});

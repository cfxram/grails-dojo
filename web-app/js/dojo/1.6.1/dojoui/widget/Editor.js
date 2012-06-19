
/*
  Findings: (1-26-2012) version 1.7.1 of Dojo.

  If EnterKeyHandling (EKH) is off, IE uses <p> tags. Safari uses <div>. Firefox uses <br>.
  If EKH is on, they all use <br> but IE bullets break. (not to mention browser crash issue)
  IF EKH is on and set to use <p> or <div>, then js error when hitting enter on a bullet item (.apply is not a function).

  In IE, put cursor at begging of line and hit bullet... extra <br> tag is entered. But it works fine if cursor is anywhere else on the line.

  dojox.editor.plugins.AutoUrlLink doesn't work in FF 9. Works in Safari but throws an error. ('null' is not an object (evaluating '_b.nodeType'))
 */


dojo.provide("dojoui.widget.Editor");
dojo.require("dijit.Editor");
dojo.require("dijit._editor.plugins.TextColor");
dojo.require("dijit._editor.plugins.LinkDialog");
dojo.require("dijit._editor.plugins.ViewSource");
dojo.require("dijit._editor.plugins.FontChoice");
dojo.require("dojox.editor.plugins.PasteFromWord");
dojo.require("dojoui.widget.EnterKeyHandling");    // uses a custom enter key handler to fix bugs in dojo.
dojo.require("dojox.editor.plugins.AutoUrlLink");


dojo.declare("dojoui.widget.Editor",dijit.Editor,{
  editorFormField:null,
  //styleSheets:'../js/dojo/dojoui/widget/resources/css/dojo-ui-editor.css',
  debug:false,
  type:"", // simple || intermediate || advanced



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
    this.editorFormField = dojo.create("textarea",{id:this.id+'-formField',name:this.id});
    if(!this.debug){
      dojo.style(this.editorFormField,"display","none");
    }
    else{
      dojo.style(this.editorFormField,"width","400px");
      dojo.style(this.editorFormField,"height","200px");
    }
    dojo.place(this.editorFormField, this.domNode, "after");
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

    // This is for the iPad. When a user clicks anywhere in on the iFrame,
    // then focus on the editor inside of the iFrame.
    this.iframe.onclick = this.focus;
  },





  /**
   * Copied from RichText.js
   *
   * THis is overridden so we can get the iPad working. Just had to define the height of the div area to be larger than
   * 1 line higher.
   */
	_getIframeDocTxt: function(){
		// summary:
		//		Generates the boilerplate text of the document inside the iframe (ie, <html><head>...</head><body/></html>).
		//		Editor content (if not blank) should be added afterwards.
		// tags:
		//		private
		var _cs = dojo.getComputedStyle(this.domNode);

		// The contents inside of <body>.  The real contents are set later via a call to setValue().
		var html = "";
		var setBodyId = true;
		if(dojo.isIE || dojo.isWebKit || (!this.height && !dojo.isMoz)){
			// In auto-expand mode, need a wrapper div for AlwaysShowToolbar plugin to correctly
			// expand/contract the editor as the content changes.
			html = "<div id='dijitEditorBody'></div>";
			setBodyId = false;
		}else if(dojo.isMoz){
			// workaround bug where can't select then delete text (until user types something
			// into the editor)... and/or issue where typing doesn't erase selected text
			this._cursorToStart = true;
			html = "&nbsp;";
		}


    /** ----------------------------------------------------- **/
    /** ----------------------------------------------------- **/
    /** ----  Begin: iOS Customization ---- **/
    var isIpad = navigator.userAgent.match(/iPad/i) != null;
    var isIphone = navigator.userAgent.match(/iPhone/i) != null;
    var isIpod = navigator.userAgent.match(/iPod/i) != null;
    var isIOS = (isIpad || isIphone || isIpod);


    if(isIOS){
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
			lineHeight = parseFloat(lineHeight)/parseFloat(_cs.fontSize);
			// console.debug(lineHeight);
		}else if(lineHeight.indexOf("em")>=0){
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
			match = match.replace(/^;/ig,"") + ';';
			var s = match.split(":")[0];
			if(s){
				s = dojo.trim(s);
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
				dojo.style(self.domNode, sC, "");
			}
			userStyle += match + ';';
		});

    /*
    
      Default created by original editor------------
      body,html {
        background: transparent;
        padding: 1px 0 0 0;
        margin: -1px 0 0 0;
        width: 100%;
        height: 100%;
      }
      body {
        top: 0px;
        left: 0px;
        right: 0px;
        font: normal 12px Arial, Verdana, Helvetica, San-Serif;
        min-height: 1em;
        line-height: normal;
      }
      p {
        margin: 1em 0;
      }
      #dijitEditorBody {
        overflow-x: auto;
        overflow-y: auto;
        outline: 0px;
      }
      li > ul:-moz-first-node, li > ol:-moz-first-node {
        padding-top: 1.2em;
      }
      li {
        min-height: 1.2em;
      }

      ****** WHAT I CHANGED *****
      p,blockquote {margin-top:0; margin-bottom:0;}
    */


		// need to find any associated label element and update iframe document title
		var label=dojo.query('label[for="'+this.id+'"]');

		return [
			this.isLeftToRight() ? "<html>\n<head>\n" : "<html dir='rtl'>\n<head>\n",
			(dojo.isMoz && label.length ? "<title>" + label[0].innerHTML + "</title>\n" : ""),
			"<meta http-equiv='Content-Type' content='text/html'>\n",
			"<style>\n",
			"\tbody,html {\n",
			"\t\tbackground:transparent;\n",
			"\t\tpadding: 1px 0 0 0;\n",
			"\t\tmargin: -1px 0 0 0;\n", // remove extraneous vertical scrollbar on safari and firefox

			// Set the html/body sizing.  Webkit always needs this, other browsers
			// only set it when height is defined (not auto-expanding), otherwise
			// scrollers do not appear.
			((dojo.isWebKit)?"\t\twidth: 100%;\n":""),
			((dojo.isWebKit)?"\t\theight: 100%;\n":""),
			"\t}\n",

			// TODO: left positioning will cause contents to disappear out of view
			//	   if it gets too wide for the visible area
			"\tbody{\n",
			"\t\ttop:0px;\n",
			"\t\tleft:0px;\n",
			"\t\tright:0px;\n",
			"\t\tfont:", font, ";\n",
				((this.height||dojo.isOpera) ? "" : "\t\tposition: fixed;\n"),
			// FIXME: IE 6 won't understand min-height?
			"\t\tmin-height:", this.minHeight, ";\n",
			"\t\tline-height:", lineHeight,";\n",
			"\t}\n",
			"\tp,blockquote{margin-top: 0;margin-bottom:0}\n",

			// Determine how scrollers should be applied.  In autoexpand mode (height = "") no scrollers on y at all.
			// But in fixed height mode we want both x/y scrollers.  Also, if it's using wrapping div and in auto-expand
			// (Mainly IE) we need to kill the y scroller on body and html.
			(!setBodyId && !this.height ? "\tbody,html {overflow-y: hidden;}\n" : ""),
			"\t#dijitEditorBody{overflow-x: auto; overflow-y:" + (this.height ? "auto;" : "hidden;") + " outline: 0px;}\n",
			"\tli > ul:-moz-first-node, li > ol:-moz-first-node{ padding-top: 1.2em; }\n",
			// Can't set min-height in IE9, it puts layout on li, which puts move/resize handles.
			(!dojo.isIE ? "\tli{ min-height:1.2em; }\n" : ""),
			"</style>\n",
			this._applyEditingAreaStyleSheets(),"\n",
			"</head>\n<body ",
			(setBodyId?"id='dijitEditorBody' ":""),
			"onload='frameElement._loadFunc(window,document)' style='"+userStyle+"'>", html, "</body>\n</html>"
		].join(""); // String
	}


});


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

  }

});

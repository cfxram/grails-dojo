dojo.provide("dojoui.widget.Editor");
dojo.require("dijit.Editor");

// Plugins
dojo.require("dijit._editor.plugins.TextColor");
dojo.require("dijit._editor.plugins.LinkDialog");
dojo.require("dijit._editor.plugins.ViewSource");
dojo.require("dijit._editor.plugins.FontChoice");
dojo.require("dojox.editor.plugins.PasteFromWord");
dojo.require("dijit._editor.plugins.EnterKeyHandling");
dojo.require("dojox.editor.plugins.AutoUrlLink");


dojo.declare("dojoui.widget.Editor",dijit.Editor,{
  editorFormField:null,
  //styleSheets:'../js/dojo/dojoui/widget/resources/css/dojo-ui-editor.css',
  debug:true,
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
   * Event fired when a user changes something.
   */
  onDisplayChanged:function(){
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
   *
   *  WARNING - RM (1-26-2012)
   *  Don't use the "dijit._editor.plugins.EnterKeyHandling" plugin with 'BR' because of these bugs:
   *  http://bugs.dojotoolkit.org/ticket/13399
   *  http://bugs.dojotoolkit.org/ticket/13744
   */
  definePlugins:function(){

    if(this.type === "simple"){
      this.plugins = [
        'bold', 'italic', 'underline', 'strikethrough', '|',
        'insertOrderedList', 'insertUnorderedList', '|',
        'indent', 'outdent', '|',
        'justifyLeft', 'justifyRight', 'justifyCenter', '|',
        'pastefromword'
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
        'createLink','viewsource'
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
        'createLink','viewsource'
      ];
    }

    //this.plugins.push('dijit._editor.plugins.EnterKeyHandling');
  },


  /**
   * Fires after widget is rendered.
   */
  postCreate: function(){
    this.definePlugins();
    this.inherited(arguments);
  }

});

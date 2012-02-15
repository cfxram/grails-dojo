dojo.provide("dojoui.widget.Dialog");
dojo.require("dojoui.layout.ContentPane")
dojo.require("dijit.Dialog");

dojo.declare("dojoui.widget.Dialog", [dojoui.layout.ContentPane, dijit._DialogBase],{


  onDownloadEnd:function(){
    this.layout();
  }

});
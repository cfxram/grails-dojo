dojo.provide("dojoui.widget.Dialog");
dojo.require("dojoui.layout.ContentPane")
dojo.require("dijit.Dialog");

dojo.declare("dojoui.widget.Dialog", [dojoui.layout.ContentPane, dijit._DialogBase],{

    

  onDownloadEnd:function(){
    this.layout();
  },

  postCreate:function(){
    this.inherited(arguments);
    // Can't close the dialog if the user is using an iPad.
    var isIpad = navigator.userAgent.match(/iPad/i) != null;   
    var isIphone = navigator.userAgent.match(/iPhone/i) != null;   
    var isIpod = navigator.userAgent.match(/iPod/i) != null;
    
    if(this.draggable){
      if(isIpad || isIphone || isIpod){
        this.draggable = false;
      }
    }
  }
  
});
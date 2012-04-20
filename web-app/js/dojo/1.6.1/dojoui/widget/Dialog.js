dojo.provide("dojoui.widget.Dialog");
dojo.require("dojoui.layout.ContentPane")
dojo.require("dijit.Dialog");

dojo.declare("dojoui.widget.Dialog", [dojoui.layout.ContentPane, dijit._DialogBase],{

  /**
   * Called when time for the dialog to position itself in the viewport.
   */
  _position:function(){
    this.inherited(arguments);
  },


  /**
   * called when it is time to resize window.
   */
  _size:function(){
    this.inherited(arguments);
  },


  /**
   * Called right before ajax call is made.
   */
  _load:function(){
    this.inherited(arguments);
    this.layout();
  },

  /**
   * Handler called after loading of ajax data has been completed.
   */
  onDownloadEnd:function(){
    //this.setLoadingMessage();
    this.inherited(arguments);
    this.layout();
  },


  /**
   * After widget has been initialized. Turn off draggable for iOS devices.
   */
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
dojo.provide("dojoui.widget.DropDownButton");
dojo.provide("dojoui.widget.DropDownButtonLink");
dojo.require("dijit.form.DropDownButton");


/**
 * As a Button
 */
dojo.declare("dojoui.widget.DropDownButton",dijit.form.DropDownButton,{
  activate:"click",
  contentDomId:'',        // put the content from this dom id into the tooltip.
  templateString: dojo.cache("dojoui", "widget/templates/DropDownButton.html"),
  btnClass:"",
  
  /**
   * Will destroy any dijit that has the same id as this one.
   * This is called before this dijit is registered in the dijit.registry.
   */
  postMixInProperties:function(){
    if(dijit.byId(this.id)){
      dijit.byId(this.id).destroy();
    }
  },
  
  postCreate:function(){
    if (this.activate == 'hover') {
      dojo.connect(this.domNode, "onmouseover", this, this.onMouseOver);
      dojo.connect(this.domNode, "onmouseout", this, this.onMouseOut);
      // TODO: style differently if hover over and type is a link
    }
    else{
      dojo.connect(this.domNode, "onclick", this, this.onClick);      
    }
    this.inherited(arguments);
  },
  
  
  openDropDown:function(){
    if(this.contentDomId && !this._replacedContent){
      var elem = dojo.byId(this.contentDomId)
      var container = this.dropDown.containerNode;
      dojo.empty(container);
      dojo.place(elem, container);
      this._replacedContent = true;
    }    
    this.inherited(arguments);
  },
  
  
  onClick:function(){},
  
  
  onMouseOver:function(){
    this.openDropDown();
  },
  
  onMouseOut:function(){
    this.closeDropDown();
  }  
}); 



/**
 * As a Link
 */
dojo.declare("dojoui.widget.DropDownButtonLink",dojoui.widget.DropDownButton,{
  templateString: dojo.cache("dojoui", "widget/templates/DropDownButtonLink.html")
}); 


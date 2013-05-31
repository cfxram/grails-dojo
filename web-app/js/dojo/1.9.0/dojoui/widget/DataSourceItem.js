define(["dojo/_base/declare",
        "dojo/text!./templates/DataSourceItem.html",
        "dijit/_Widget",
        "dojox/dtl/_DomTemplated", 
        "dojo/dom-attr"], function(declare,template,_Widget,_DomTemplated,domAttr) {
	
	/**
	 * Widget that will render templates based on a data source.
	 */
	return declare("dojoui.widget.DataSourceItem",[_Widget, _DomTemplated],{
  
	  //The data used by the template
	  node:null,
	  
	  // Default template that is then overridden
	  templateString: template,
	  
	  /**
	   * Will set a new node object and re-render the template
	   * @param {Object} node
	   */
	  updateNode:function(node){
	    this.node = node;
	    this.render();
	  },
	  
	  // This will run any code that is defined via a dojoAttachEvent property.
	  _eventHandler:function(e){
	    var elem = e.target;
	    eval(domAttr.set(elem,'eventHandler'));
	  }
	  
	}); 
});

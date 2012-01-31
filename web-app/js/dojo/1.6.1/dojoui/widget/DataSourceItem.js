dojo.provide("dojoui.widget.DataSourceItem");

dojo.require("dojox.dtl._DomTemplated");
dojo.require("dojox.dtl.tag.logic");
dojo.require("dojox.dtl.tag.loop");
dojo.require("dojox.dtl.filter.lists");

/**
 * Widget that will render templates based on a data source.
 */
dojo.declare("dojoui.widget.DataSourceItem",[dijit._Widget, dojox.dtl._DomTemplated],{
  
  //The data used by the template
  node:null,
  
  // Default template that is then overridden
  templateString: dojo.cache("dojoui", "widget/templates/DataSourceItem.html"),  
  
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
    eval(dojo.attr(elem,'eventHandler'));
  }
  
}); 

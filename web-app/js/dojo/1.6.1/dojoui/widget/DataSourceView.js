dojo.provide("dojoui.widget.DataSourceView");
dojo.require("dojoui.widget.DataSourceItem");
dojo.require("dijit._Widget");
dojo.require("dijit._Templated");
/**
 * Widget that will render templates based on a data source.
 */
dojo.declare("dojoui.widget.DataSourceView",[dijit._Widget, dijit._Templated],{
  store:null,
  childField:'',
  templateString: dojo.cache("dojoui", "widget/templates/DataSourceView.html"),  



  /**
   * This will flatten out a store item into a regular object.
   * @param {Object} item
   */
  convertItemToNode:function(item,label){
    var node = {};
    for(var i in item){
      if (i.charAt(0) != "_") {
        node[i] = (item[i]) ? item[i][0] : '';
      }
    }
    if(!node.label){
      node.label = label;
    }
    return node;  
  },


  /**
   * Gets all nodes that are in the store.
   */
  getNodeCount:function(){
    var totalItems = 0;
    var searchDone = function(items, request) {
      totalItems = items.length;  
    }
    this.store.fetch({
      query:{},
      onComplete: dojo.hitch(this,searchDone),
      queryOptions: {
          deep: true,
          ignoreCase:true
      }
    });
    return totalItems;
  },

    
  /**
   * Queries the store and returns array of nodes that match the
   * simple criteria.
   * 
   * @param {Object} nodeField
   * @param {Object} nodeValue
   */
  queryNodes:function(nodeField, nodeValue){
    var foundItems = []; 
    var searchDone = function(items, request) {
      for(var i=0; i<items.length;i++){
        foundItems.push( this.convertItemToNode(items[i]) )  
      }
    }     
    var queryValues = {};
    queryValues[nodeField] = nodeValue;
    
    this.store.fetch({
      query:queryValues,
      onComplete: dojo.hitch(this,searchDone),
      queryOptions: {
          deep: true,
          ignoreCase:true
      }
    });    
    return foundItems;
  },


  /**
   * Gets an item by id from the store.
   * @param {Object} store
   * @param {Object} id
   */
  getItemFromStoreById:function(id){
    var item = null;
    this.store.fetchItemByIdentity({
      identity:id,
      onItem: function(foundItem){
        item = foundItem;
      },
      onError: function(){console.log('failed reading id'); return null}
    });
    if(item){
        if(item.id[0] != id){
          console.log('There has been an error');
        }
    }
    return item;
  },  


  /**
   * Sets the item in the store.
   * 
   * @param {Object} id
   * @param {Object} field
   * @param {Object} value
   */
  setNodeValue:function(id,field,value){
    var dataStore = this.store;
    var item = this.getItemFromStoreById(id);
    dataStore.setValue(item,field,value);
    dataStore.save();
  },

    /**
     *  Will create an id from a node that is related to the node.
     * The dom node doesn't neccessarily exists this will just generated the id.
     * @param node
     */
  getListElementId:function(nodeId){
    return "listElem_"+this.id+"_"+nodeId;
  },


  /**
   * Will return an html string which is rendered off of a a node template.
   * @param {Object} item
   * @param {Object} position - Where to add the new element. Can be "first","last" or just null
   */
  renderNodeHTML:function(item, position){
    var node          = this.convertItemToNode(item);
    var hasChildren   = (node[this.childField]) ? true : false;  
    var nodeTemplate  = this.defaultNodeTemplate;
    var LI_elemId     = this.getListElementId(node.id);
    var templateObj   = {}

    // See if any of the field values matches a specific template.
    for(var i in this.templateForFields){
      var field = i;
      var value = node[i]
      if(this.templateForFields[field]) {
        if (this.templateForFields[field][value]) {
          templateObj = this.templateForFields[field][value];
          nodeTemplate = this.templateForFields[field][value].template;
          break;
        }
      }  
    }   
    
    if (nodeTemplate) {
      if(templateObj.renderAsDjango){
        // Render Django Template 
        var existingWidget = dijit.byId(LI_elemId);
        if(existingWidget){
          existingWidget.updateNode(node);
        }                 
        else{
          var elemNode = dojo.create("li",{id:LI_elemId, style:{"listStyle":"none"}}, this.nodeList, position);
          var dataItem = new dojoui.widget.DataSourceItem({node:node,templateString:nodeTemplate},elemNode);          
        }
      }
      else{
        // Render Simple Template
        var htmlString = dojo.replace(nodeTemplate, {"node": node,"this": "dijit.byId('" + this.id + "')"});
        if (htmlString.length) {
          var existingElem = dojo.byId(LI_elemId);
          var elemNode = (existingElem) ? existingElem : dojo.create("li",{id:LI_elemId, style:{"listStyle":"none"}}, this.nodeList, position);
          dojo.attr(elemNode,"innerHTML",htmlString);
        }
      }
    }
    else{
      // No template found for value.. Value may have changed and we need to remove the old template
      this.removeDomNode(LI_elemId);
    }

  },



  /**
   * Removes the dom node that represents the element;
   * @param {Object} id
   */
  removeDomNode:function(id){
      var existingWidget = dijit.byId(id);
      if(existingWidget){
        existingWidget.destroy();
      }
      else{
        var existingElem = dojo.byId(id);
        dojo.destroy(existingElem); 
      }
  },
  

  /**
   * Will check if the no-item-found message should be shown or hidden.
   */
  toggleNoItemFound:function(){
    // Render "No-Item-Found" template.
    if(this.nodeList.children.length < 1){
      if (this.noItemTemplate) {
        var htmlString = dojo.replace(this.noItemTemplate, {"this": "dijit.byId('" + this.id + "')"});
        dojo.create("li", {id:"no-item-found"+this.id, innerHTML: htmlString, style:{"listStyle":"none"}}, this.nodeList);
      }
    }
    else{
      dojo.destroy("no-item-found"+this.id);
    }
  },
  

  /**
   * Will fetch all records and render them. If no items are rendered (which means no <li> elements)
   * Then it will render the noItem template.
   */
  renderAllNodes:function(){
    dojo.empty(this.nodeList);
    
    var searchDone = function(items, request) {
      this.nodeList.innerHTML = '';
      for(var i=0; i<items.length; i++){
        var elemId = this.getListElementId(items[i].id[0]);
        this.removeDomNode(elemId);
        this.renderNodeHTML(items[i]);
      }  
      this.toggleNoItemFound();

    }    
    this.store.fetch({
      query:{},
      onComplete: dojo.hitch(this,searchDone),
      queryOptions: {
        deep: true,
        ignoreCase:true
      }
    });
  },


  /**
   * Will store a template to be rendered for a specific value of a field.
   * @param {Object} field
   * @param {Object} value
   * @param {Object} template
   */
  registerNodeStateTemplate:function(field,value,renderAsDjango,template){
    if (!this.templateForFields[field]) {
      this.templateForFields[field] = {};
    }
    this.templateForFields[field][value] = {"renderAsDjango": renderAsDjango, "template": template};  
  },
  

  /**
   * Establishes the listeners on the data store. Then renders all the nodes.
   */
  postCreate:function(){  
    this.templateForFields = {};
    this.defaultNodeTemplate = null;
    this.noItemTemplate = null;
    this.connects = [];    
    this.inherited(arguments);
  },
  
  
  /**
   * Starts the rendering
   */
  startup:function(){
    var store = this.store    
    if(!store.getFeatures()['dojo.data.api.Identity']){
      throw new Error("dojoui.widget.DataSourceView must support dojo.data.Identity");
    }
    // if the store supports Notification, subscribe to the notification events
    if(store.getFeatures()['dojo.data.api.Notification']){
      this.connects = this.connects.concat([
        dojo.connect(store, "onNew", this, "onNewItem"),
        dojo.connect(store, "onDelete", this, "onDeleteItem"),
        dojo.connect(store, "onSet", this, "onSetItem")
      ]);
    }
    
    this.inherited(arguments);
    this.renderAllNodes();
    this.setupChangeEvents();
  },
  
  
  /**
   * Establishes event watches for elements that want to force a change
   * in the model value.
   */
  setupChangeEvents:function(){
      dojo.connect(dojo.body(),"click",this,function(e){
        var elem = e.target;
        if(dojo.hasClass(elem,"clickChangeNode")){
          var id = dojo.attr(elem,"nodeId");
          var field = dojo.attr(elem,"changeField");
          var value = dojo.attr(elem,"changeValueTo");
          
          this.setNodeValue(id,field,value);
        }
      })    
  },  
  
  /* ************************** */
  /* dojo.data.api.Notification */
  onSetItem: function(/* item */ item, 
          /*attribute-name-string*/ attribute, 
          /*object | array*/ oldValue,
          /*object | array*/ newValue){
           
            
    
    // Check to see if this attribute has a template.
    // If true, then render, else dont bother.
    if(this.templateForFields[attribute]){
      this.renderNodeHTML(item,"first");  
      this.toggleNoItemFound();
    }
  },

  onNewItem: function(/* item */ newItem, /*object?*/ parentInfo){
    this.renderNodeHTML(newItem,"first");
	  this.toggleNoItemFound();
  },

  onDeleteItem: function(/* item */ deletedItem){
    var node = this.convertItemToNode(deletedItem);
    var elemId = this.getListElementId(node.id);
    var LI_elemId = this.getListElementId(node.id);    
	  this.removeDomNode(LI_elemId);
	  this.toggleNoItemFound();	
  }
  
}); 

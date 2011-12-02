dojo.provide("dojoui.widget.Tree");
dojo.provide("dojoui.widget.TreeNode");
dojo.require("dijit.Tree");


/**
 * Tree with Custom Render Nodes depending on State
 *
  Expects:
    var test = {
      identifier: "id",
      label:"name",
      items:[
        {
          "id":1,
          "name":"Released",
          "widgets":[
            {
              "id":5,
              "name":"Music",
              "widgets":[
                {"id":6, name: "iPod", color: "black", shape: "rectangle", category:"Music", discounted: false},
                {"id":7, name: "Zune 2", color: "brown", shape: "square", category:"Music", discounted: true}
              ]
            }
          ]
        },

        {
          "id":2,
          "name":"Prototypes",
          "widgets":[
            {
              "id":100,
              "name":"Square Prototype",
              "widgets":[
                {"id":106, name: "Prototype Widget 1", color: "Blue", shape: "Square", category:"Square Prototype", discounted: false},
                {"id":107, name: "Prototype Widget3", color: "Blue", shape: "Square", category:"Square Prototype", discounted: false}
              ]
            }
          ]
        },
      ]
    }
 */
dojo.declare("dojoui.widget.Tree",dijit.Tree,{
  
  /* Overrides the dijit.Tree Template */
  templateString: dojo.cache("dojoui", "widget/templates/Tree.html"),
  
  childField:'',
  fieldToSearch:'name',
  searchAble:true,
  treeNodeLabel:'root',
  templateForFields:{},
  defaultNodeTemplate:'{node.label}',
  defaultLeafTemplate:'{node.label}',
  defautlRootTemplate:'{node.label}',
  expandFirstChild:true,
  autoExpand:false,
  
  
  /**
   * Queries the tree store and returns array of nodes that match the
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
    
    this.model.store.fetch({
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
    this.model.store.fetchItemByIdentity({
      identity:id,
      onItem: function(foundItem){
        item = foundItem;
      },
      onError: function(){console.log('failed reading id'); return null}
    });  
    return item;
  },  
  

  /**
   * This will flatten out a store item into a regular object.
   * @param {Object} item
   */
  convertItemToNode:function(item,label){
    var node = {};
    for(i in item){
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
   * Sets the item in the store.
   * 
   * @param {Object} id
   * @param {Object} field
   * @param {Object} value
   */
  setNodeValue:function(id,field,value){
    var dataStore = this.model.store;
    var item = this.getItemFromStoreById(id);
    dataStore.setValue(item,field,value);
    dataStore.save();
  },


  /**
   * Will return an html string which is rendered off of a a node template.
   * @param {Object} item
   * @param {Object} treeNodeLabel
   */
  renderNodeHTML:function(item, treeNodeLabel){
    var node          = this.convertItemToNode(item, treeNodeLabel);
    var hasChildren   = (node[this.childField]) ? true : false;
    var isRoot        = (node.id == 'treeRoot') ? true : false;          
    var nodeTemplate  = null;

    if(hasChildren){
      nodeTemplate = this.defaultNodeTemplate;
    }
    else if(isRoot){
      nodeTemplate = this.defautlRootTemplate;
    }    
    else{
      nodeTemplate = this.defaultLeafTemplate;
      // See if any of the field values matches a specific template.
      for(i in this.templateForFields){
        var field = i;
        var value = this.templateForFields[i].value;
        if (value == node[i]) {
          nodeTemplate = this.templateForFields[i].template;
          break;
        }  
      }      
    }    
    return dojo.replace(nodeTemplate, {
      "node": node,
      "this": "dijit.byId('" + this.id + "')"
    });
  },


  /**
   * Overrides the dijit.Tree version
   * @param {Object} args
   */
  _createTreeNode:function(args){
    var tnode = new dijit._TreeNode(args);
    tnode.labelNode.innerHTML = this.renderNodeHTML(args.item, args.label);
    return tnode;
  },


  /**
   * Overrides the dijit.Tree version. Will render a node when an item in the store
   * has changed.
   * @param {Object} item
   */
  _onItemChange:function(item){
    var model = this.model;
    var identity = model.getIdentity(item);
    var tnode = this._itemNodesMap[identity];

    if (tnode) {
      tnode = tnode[0]
      tnode.labelNode.innerHTML = this.renderNodeHTML(item);
    }        
    //rerun search
    this._searchFieldOnKeyUp();
  },


  /**
   * Override the dijit.Tree version
   * @param {Object} nodeWidget
   * @param {Object} e
   */   
  _onClick:function(nodeWidget,e){
      if(nodeWidget.isExpandable){
          this._onExpandoClick({node:nodeWidget});
      }
  },

  /**
   * Will store a template to be rendered for a specific value of a field.
   * @param {Object} field
   * @param {Object} value
   * @param {Object} template
   */
  registerNodeStateTemplate:function(field,value,template){
    this.templateForFields[field] = {"value":value,"template":template};  
  },
  
  
  /**
   * Runs the actual search against the model. This will then populate the search result screen.
   * @param {Object} searchString
   */
  runSearch:function(searchString){
    this.searchResultsList.innerHTML = '';
    var model = this.model;
    
    // Show Results 
    var searchDone = function(items, request) {
      for(var i=0; i<items.length; i++){
        // Only child elements
        if(!items[i][this.childField]){
          dojo.create("li",
            { innerHTML:this.renderNodeHTML(items[i]), style:{"listStyle":"none"} },
            this.searchResultsList
          );
        }
      }  
      if(items.length < 1){
        dojo.create("li",{innerHTML:"No Results"},this.searchResultsList);
      }
    }     
    // Search Options 
    var queryValues = {};
    queryValues[this.fieldToSearch] = '*'+searchString+'*';
    model.store.fetch({
      query:queryValues,
      onComplete: dojo.hitch(this,searchDone),
      queryOptions: {
          deep: true,
          ignoreCase:true
      }
    });
  },
  
  
  /**
   * Fired when the user types into the search field.
   */
  _searchFieldOnKeyUp:function(){
    var searchString = this.searchField.value;
    if(searchString.length){
      this.showResultsPane();
      this.hideTreePane();
      this.runSearch(searchString);
    }
    else{
      this.hideResultsPane();
      this.showTreePane();
    }
  },
  
  
  /**
   * Will close the window if certain keys are pressed.
   * @param {Object} e
   */
  _searchFieldOnKeyDown:function(e){
    if( (e.keyCode==dojo.keys.ESCAPE) || (e.keyCode==dojo.keys.TAB) ){
      this.hideResultsPane();
      this.showTreePane();
    }
  },
  
  
  /**
   * Will display the search results pane. 
   */
  showResultsPane:function(){
    dojo.style(this.clearSearchBtn, 'display', 'inline-block');
    dojo.style(this.searchResultsPane,'display','block');  
  },
  
  
  /**
   * Will hide the search results pane.
   */
  hideResultsPane:function(){
    this.searchField.value = '';
    dojo.style(this.clearSearchBtn, 'display', 'none');
    dojo.style(this.searchResultsPane,'display','none');  
  },
  
  
  /**
   * Will hide the tree pane.
   */
  hideTreePane:function(){
    dojo.style(this.blocker,'display','block');  
  },
  
  
  /**
   * Will show the tree pane.
   */
  showTreePane:function(){
    dojo.style(this.blocker,'display','none');
  },
  
  
  /**
   * Fired when the clear search button is clicked
   */
  _clearSearchButtonClick:function(){
    this.hideResultsPane();
    this.showTreePane();
  },
  
  
  /**
   * Searching is enabled so show the search field.
   */
  enableSearch:function(){
    dojo.style(this.searchPane,'display','block');  
  },
  
  
  /**
   * Creates the dimensions of the widget's internal dom elements.
   */
  establishDimensions:function(){
    var widgetHeight = dojo.marginBox(this.domNode).h;
    var searchHeight = dojo.marginBox(this.searchPane).h;    
    var treeHeight = widgetHeight - searchHeight - 2;
    if (treeHeight > 100) {
      dojo.style(this.rootNode.domNode, 'height', treeHeight + 'px');
      dojo.style(this.rootNode.domNode, 'overflow', 'auto');
      dojo.style(this.searchResultsPane, 'height', (treeHeight*0.9) + 'px');
      dojo.style(this.searchResultsPane, 'overflow', 'auto');      
      
    }
    dojo.style(this.rootNode.domNode, 'padding-top', '2px');   
  },


  postCreate:function(){
    this.inherited(arguments);
    if(this.searchAble){
      this.enableSearch();
    }
    this.establishDimensions();
  },
  
  /**
   * Called when tree is finished loading and expanding.
   */
  onLoad:function(){
    
    // Expand First Child;
    if (this.expandFirstChild) {
      var firstNode = this.getNodesByItem(this.model.root.children[0]);
      if (firstNode[0]){
        this._expandNode(firstNode[0]);
      }
    }
    
  }
  
  
}); 

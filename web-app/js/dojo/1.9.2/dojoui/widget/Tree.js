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
define(["dojo/_base/declare", "dijit/Tree", "dojo/text!./templates/Tree.html", "dojo/dom-construct", "dojo/dom-style", "dojo/dom-geometry", "dojo/_base/lang", "dojo/keys", "dijit/registry", "dojo/dom-attr", "dojo/dom-class"],
function(declare, Tree, template, domc, style, domg, lang, keys, registry, doma, dclass){
	return declare("dojoui.widget.Tree", Tree, {
		/* Overrides the dijit/Tree Template */
		templateString: template,
		
		childField:'',
		fieldToSearch:'name',
		searchAble:true,
		treeNodeLabel:'root',
		templateForFields:null,
		defaultNodeTemplate:'{node.label}',
		defaultLeafTemplate:'{node.label}',
		defautlRootTemplate:'{node.label}',
		expandFirstChild:true,
		autoExpand:false,

		/**
		 * This is overridden here so that we can call the some code defined in
		 * the grails taglib.
		 */
		postMixInProperties:function(){
			this.inherited(arguments);
			this.setGrailsPluginProperties();
		},

		postCreate:function(){
			this.inherited(arguments);
			if(this.searchAble){
				this.enableSearch();
			}
		},

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
				onComplete: lang.hitch(this,searchDone),
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
				for(var i in this.templateForFields){
					var value = this.templateForFields[i].value;
					if (value == node[i]) {
						nodeTemplate = this.templateForFields[i].template;
						break;
					}
				}
			}
			return lang.replace(nodeTemplate, {
				"node": node,
				"this": "require('dijit/registry').byId('" + this.id + "')"  // hack?
			});
		},


		/**
		 * Overrides the dijit/Tree version
		 * @param {Object} args
		 */
		_createTreeNode:function(args){
			var tnode = this.inherited(arguments);
			doma.set(tnode.labelNode, 'innerHTML', this.renderNodeHTML(args.item, args.label));
			return tnode;
		},


		/**
		 * Overrides the dijit/Tree version. Will render a node when an item in the store
		 * has changed.
		 * @param {Object} item
		 */
		_onItemChange:function(item){
			var model = this.model;
			var identity = model.getIdentity(item);
			var tnode = this._itemNodesMap[identity];

			if (tnode) {
				tnode = tnode[0]
				var content = this.renderNodeHTML(item);
				domc.empty(tnode.labelNode);
				domc.place(content, tnode.labelNode)
			}
			//rerun search
			this._searchFieldOnKeyUp();
		},


		/**
		 * Override the dijit/Tree version
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
			if(!this.templateForFields){
				this.templateForFields = [];
			}
			this.templateForFields[field] = {"value":value,"template":template};
		},
		  
		  
		/**
		 * Runs the actual search against the model. This will then populate the search result screen.
		 * @param {Object} searchString
		 */
		runSearch:function(searchString){
			domc.empty(this.searchResultsList);
			var model = this.model;

			//Show Results 
			var searchDone = function(items, request) {
				for(var i=0; i<items.length; i++){
					// Only child elements
					if(!items[i][this.childField]){
						domc.create("li",
								{ innerHTML:this.renderNodeHTML(items[i]), style:{"listStyle":"none"} },
								this.searchResultsList
						);
					}
				}
				if(items.length < 1){
					domc.create("li",{innerHTML:"No Results"},this.searchResultsList);
				}
		    }
			//Search Options 
			var queryValues = {};
			queryValues[this.fieldToSearch] = '*'+searchString+'*';
			model.store.fetch({
				query:queryValues,
				onComplete: lang.hitch(this,searchDone),
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
			if( (e.keyCode==keys.ESCAPE) || (e.keyCode==keys.TAB) ){
				this.hideResultsPane();
				this.showTreePane();
			}
		},


		/**
		 * Will display the search results pane. 
		 */
		showResultsPane:function(){
			dclass.remove(this.clearSearchBtn, 'dijitHidden');
			dclass.remove(this.searchResultsPane,'dijitHidden');  
		},
		  
		  
		/**
		 * Will hide the search results pane.
		 */
		hideResultsPane:function(){
			this.searchField.value = '';
			dclass.add(this.clearSearchBtn, 'dijitHidden');
			dclass.add(this.searchResultsPane, 'dijitHidden');  
		},
		  
		  
		/**
		 * Will hide the tree pane.
		 */
		hideTreePane:function(){
			dclass.remove(this.blocker,'dijitHidden');  
		},
		  
		  
		/**
		 * Will show the tree pane.
		 */
		showTreePane:function(){
			dclass.add(this.blocker,'dijitHidden');
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
			dclass.remove(this.searchPane,'dijitHidden');  
		},
		  
		/**
		 * Creates the dimensions of the widget's internal dom elements.
		 */
		resize:function(changeSize){
			this.inherited(arguments);
			
			var widgetHeight = domg.getMarginBox(this.domNode).h;
			var searchHeight = domg.getMarginBox(this.searchPane).h;    
			var treeHeight = widgetHeight - searchHeight - 2;
			if (treeHeight > 100) {
				style.set(this.rootNode.domNode, {'height': treeHeight + 'px', 'overflow': 'auto'});
				style.set(this.searchResultsPane, {'height': (treeHeight*0.9) + 'px', 'overflow': 'auto'});
			}
			style.set(this.rootNode.domNode, 'padding-top', '2px');   
		},

		setGrailsPluginProperties:function(){

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
});

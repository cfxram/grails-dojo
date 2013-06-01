define(["dojo/_base/declare", "dijit/form/DropDownButton", "dojo/text!./templates/DropDownButton.html", 'dijit/registry', "dojo/on", "dojo/_base/lang", "dojo/dom", "dojo/dom-construct"], function(declare, DropDownButton, template, registry, on, lang, dom, domc){
	return declare("dojoui.widget.DropDownButton", [DropDownButton], {
		// summary:
		//		As a Button
		
		activate:"click",
		  
		contentDomId:'',        // put the content from this dom id into the tooltip.
		  
		templateString: template,

		/**
		 * Will destroy any dijit that has the same id as this one.
		 * This is called before this dijit is registered in the dijit.registry.
		 */
		postMixInProperties:function(){
		    if(registry.byId(this.id)){
		      registry.byId(this.id).destroy();
		    }
		    this.inherited(arguments);
		},

	  postCreate:function(){
	    if (this.activate == 'hover') {
	      on(this.domNode, "mouseover", lang.hitch(this, this.onMouseOver));
	      on(this.domNode, "mouseout", lang.hitch(this, this.onMouseOut));
	      // TODO: style differently if hover over and type is a link
	    }
	    this.inherited(arguments);
	  },

	  openDropDown:function(){
	    // If there is static content defined in a div then move it into the drop down pane.
	    if(this.contentDomId && !this._replacedContent){
	      var elem = dom.byId(this.contentDomId)
	      var container = this.dropDown.containerNode;
	      domc.empty(container);
	      domc.place(elem, container);
	      this._replacedContent = true;
	    }

	    this.inherited(arguments);
	  },

	  onMouseOver:function(){
	    this.openDropDown();
	  },

	  onMouseOut:function(){
		  this.closeDropDown();
	  }
	});
});

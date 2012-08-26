define(["dojo/_base/declare",
        "dijit/layout/TabContainer", 
        "dojo/io/iframe"], function(declare, TabContainer, iframe) {
	
	return declare("dojoui.layout.TabContainer",TabContainer,{

  
	  /**
	   * Intercept the button click events to make it possible to reload the original href
	   * on the tab. This allows us to constrain links to the tab but then have a way to 
	   * get back to the original tab content.
	   * 
	   * "onButtonClick" is called automatically by dojo when the tab container is first started.
	   * We are ignoring this "first" click.
	   */
		startup: function(){
	    
	    /* Removed 5-14-2010 as this code was not needed after updating code in dojoui.layout.ContentPane.js
	    var firstClick = true;
			dojo.connect(this.tablist,"onButtonClick",function(tab){
				if(!tab)
	        return;  
	        
	      if(firstClick){
	        firstClick = false;
	        return;
	      } 
	      console.log(tab.selected);
	      if (tab.selected) {
	        tab.refresh();
	      }    
	      
			})	
	    */
			this.inherited(arguments);
		}
	
	  	
	});	
});

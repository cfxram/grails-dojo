dojo.provide("dojoui.dojoui");
var dojoui = window.dojoui || {} 

/**
 * Will load a tab with the specified url. It will also select that tab.
 * This is good for trying to select a tab and load it from an href that is outside
 * of that tabView.
 * 
 * @param {String}    tabId
 * @param {String}    href
 * @param {String}    tabViewId
 * @param {DomEvent}  e
 */
dojoui.loadTab = function( /*String*/tabId, /*String*/ href, /*String*/tabViewId, /*DomEvent*/e){
	if(arguments.e){
		dojo.stopEvent(e);
	}
	var tabView;
	var tab;
	
	if (arguments.tabViewId) {
		tabView = dijit.byId(tabViewId);
	}
	else {
		// try to find the tabView by getting the tab.
		tab = dijit.byId(tabId);
		if (tab) {
      tabViewDomNode = tab.domNode.parentNode.parentNode;
      tabView = dijit.byNode(tabViewDomNode);
    }
	}
	
  // Highlight tab
	if (tabView) {
    // Select the Tab
    tabView.selectChild(tab);
    
    // Start loading it's href.
    if (href) {
      tab.attr("href", href);
    }
  }
  else{
    // No Tab or TabView so just load the href in the parent document.
    if (href) {
      document.location.href = href;
    }
  }
}



/**
 * Will create a spinner Object.
 */
dojoui.createSpinner = function(){
	var attrObj = {
		className:"dojoui-spinner",
		id:"dojouispinner",
		style:{
			backgroundImage:"url("+dojoUiImages+"/ajax-loader.gif)",
			backgroundRepeat:"no-repeat",
			backgroundPosition:"center"
		}
	}
	var spinner = dojo.create("div", attrObj, dojo.body(), 'first');
	return spinner;
}



/**
 * Will create the spinner object (if not created) and show it.
 */
dojoui.showSpinner = function(){
	var spinner = dojo.byId("dojouispinner");
	if(!spinner)
		spinner = dojoui.createSpinner();
	
	dojo.fadeIn({
		node:spinner,
		duration:100,
		beforeBegin: function(obj){
			dojo.style(obj, "opacity", "0");
			dojo.style(obj,"display","block");
		}
	}).play();	
}



/**
 * Will hide the spinner object.
 */
dojoui.hideSpinner = function(){
	dojo.fadeOut({
		node:"dojouispinner",
		duration:300,
		onEnd: function(obj){
			dojo.style(obj,"display","none");
		}
	}).play();		
}



/**
 * Will show or hide a panel's dialog sheet.
 * 
 * @param {Object} sheetId
 */
dojoui.toggleDialogSheet = function(sheetId){
	var sheet = dojo.byId(sheetId);
	if(dojo.style(sheet,"display") == "none"){
		dojo.fx.wipeIn({node:sheetId, duration: 200}).play();	
	}
	else{
		dojo.fx.wipeOut({node:sheetId, duration: 200}).play();	
	}			
}



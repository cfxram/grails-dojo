define(['dojo/dom-construct',
        'dojo/_base/window',
        'dojo/dom',
        'dojo/dom-style',
        'dojo/_base/fx',
        'dojo/_base/lang', "dojo/_base/config"], function(domConstruct,win,dom,style,fx,lang,config) {

	var Spinner = lang.getObject("DojoGrailsSpinner", true);
	
	lang.mixin(Spinner, {
		/**
		 * Creates the spinner object <div> and attaches it to the body as the first
		 * element.
		 */
		create:function() {
			var attrObj = {
				  id:"grailsdojospinner",
				  className:"grailsdojospinner"
			}
			var spinner = domConstruct.create("div", attrObj, win.body(), 'first');
			return spinner;
		},
	
	
		/**
		 * Will display the spinner. If it doesn't exist, will create it first.
		 */
		show:function() {
			if (!config.showSpinner){
				return;
			}
	
		    var spinner = dom.byId("grailsdojospinner");
		    if (!spinner){
		    	spinner = this.create();
			}
			fx.fadeIn({
				node:spinner,
			    duration:100,
			    beforeBegin: function(obj) {
			    	style.set(obj, "opacity", "0");
			    	style.set(obj, "display", "block");
			    }
			}).play();
		},
	
	
		/**
		 * Will hide the spinner.
		 */
		hide:function() {
			if(!config.showSpinner){
				return;
			}
		  
			fx.fadeOut({
				node:"grailsdojospinner",
			    duration:300,
			    onEnd: function(obj) {
			    	style.set(obj, "display", "none");
			    }
			}).play();
		}
	});
	
	return Spinner;
});

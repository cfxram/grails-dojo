require(['dojo/dom-construct',
        'dojo/_base/window',
        'dojo/dom',
        'dojo/dom-style',
        'dojo/_base/fx',
        'dojo/_base/kernel'], function(domConstruct,win,dom,style,fx,kernel) {

	kernel.global.DojoGrailsSpinner = {
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
			if (!dojoGrailsPluginConfig.showSpinner){
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
			if(!dojoGrailsPluginConfig.showSpinner){
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
	}
});

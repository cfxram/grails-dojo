define(["dojo/_base/lang", 'dojo/request/xhr', 'dijit/registry', 'dojo/_base/array', 'dojo/dom', 'dojo/dom-attr', 'dojo/parser', './DojoGrailsSpinner', 'dojo/dom-form', 'dijit/layout/ContentPane', 'dojo/io-query'],
function(lang, request, registry, array, dom, domAttr, parser, Spinner, domForm, ContentPane, ioQuery){
	
	function remoter(url, method, options){
		var isGet = 'GET' == method.toUpperCase();
		// #GPDOJO-20 if the loadNode is a ContentPane, just set href
		var n = null;
		if(isGet && options.loadNode && (n = registry.byId(options.loadNode)) && n instanceof ContentPane){
			var href = url;
			if(options.content){
				href += "?" + ioQuery.objectToQuery(options.content); 
			}
			n.set('href', href);
			return;
		}
		var roptions = {
			method: method.toUpperCase(),
			preventCache: options.preventCache,
			sync: options.sync
		};
		if(options.form){
			roptions[isGet ? 'query' : 'data'] = domForm.toObject(options.form);
		} else if(options.content){
			roptions[isGet ? 'query' : 'data'] = options.content; 
		}
		try{Spinner.show();}catch(e){}
		var p = request(url, roptions);
		p.always(function(){
			try{Spinner.hide();}catch(e){}
			if(p.response.status && lang.isObject(options.statusHandlers)){
				for(code in options.statusHandlers){
					if(code == this.response.status && lang.isFunction(options.statusHandlers[code])){
						options.statusHandlers[code]();
					}
				}
			}
		});		
		p.then(function(response){
			if(options.loadNode){
				var updateNode = dom.byId(options.loadNode);
				array.forEach(registry.findWidgets(updateNode), function(w){
			  		console.debug('destroying',w);
			  		w.destroyRecursive();
				});
				domAttr.set(updateNode, 'innerHTML',response);
				parser.parse(updateNode);
			}
			if(options.onLoaded){
				options.onLoaded();
			}
			if(options.onSuccess){
				options.onSuccess();
			}
		}, function(err){
			if(options.errorNode){
				domAttr.set(dom.byId(options.errorNode),'innerHTML', err.response.text);
			}
			if(options.onLoaded){
				options.onLoaded();
			}
			if(options.onFailure){
				options.onFailure();
			}
		});
		p.always(function(){
			if(options.onComplete){
				options.onComplete();
			}
		});
	};
	
	return remoter;
});
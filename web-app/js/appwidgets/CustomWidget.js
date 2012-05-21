/**
 * Widget that will render templates based on a data source.
 */
define(["dojo/_base/declare", 
        "dijit/_WidgetBase", 
        "dijit/_TemplatedMixin",
        "dojo/text!./templates/CustomWidget.html"], function(declare,_WidgetBase,_TemplatedMixin, template) {
		
		return declare([_WidgetBase, _TemplatedMixin],{
			
		   templateString: template,
		   
		   postConstruct : function(){
			   console.debug("Custom widget loaded");
		   }
		});
});
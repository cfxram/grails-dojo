dojo.provide("appwidgets.CustomWidget");
dojo.require("dijit._Widget");
dojo.require("dijit._Templated");

/**
 * Widget that will render templates based on a data source.
 */
dojo.declare("appwidgets.CustomWidget",[dijit._Widget, dijit._Templated],{

   templateString: dojo.cache("appwidgets", "templates/CustomWidget.html")


});
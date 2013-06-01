define(["dojo/_base/declare", "./DropDownButton", "dojo/text!./templates/DropDownButtonLink.html"], function(declare, DropDownButton, template){
	return declare("dojoui.widget.DropDownButtonLink", DropDownButton, {
		// summary:
		//		As a Button
		
		templateString: template
	});
});

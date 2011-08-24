dojo.provide("dojoui.widget.Wizard");
dojo.require("dijit.layout.StackContainer");
dojo.require("dijit.layout.ContentPane");
dojo.require("dijit.form.Button");


dojo.declare("dojoui.widget.Wizard",[dijit.layout.StackContainer, dijit._Templated],{
	widgetsInTemplate: true,
	templateString: dojo.cache("dojoui", "widget/templates/Wizard.html"),
	showProgress:false,
	showCancel:false,
	
	/* Button Labels */
	previousLabel:"Previous",
	nextLabel:"Next",
	completeLabel:"Save",
	cancelLabel:"Cancel",
	
	/* Height Settings */
	height:400,
	btnHeight:24,
	progressStatusHeight:16,
	contentHeight:null,
	
	
	postMixInProperties:function(){
		this.height = new Number(this.height);
		this.btnHeight = new Number(this.btnHeight);
		this.progressStatusHeight = new Number(this.progressStatusHeight);
		this.contentHeight = this.height - this.btnHeight - this.progressStatusHeight;
	},
	
	
	startup:function(){
		this.inherited(arguments);
		this._toggleButtonStatuses();
		if(this.showProgress){
			this._createProgressBar();	
		}
		
		if(this.showCancel){
			this._showBtn(this.cancelBtn);
		}
		else{
			this._hideBtn(this.cancelBtn);
		}
		
	},
	
	
	
	/*
	 * Create one box per page on the top of the wizard.
	 */
	_createProgressBar:function(){
		var children = this.getChildren();
		for(var i=0; i<children.length; i++){
			var newDiv = dojo.create("li",{className:"dojoxWizardStatusPage"},this.progressButtons);
		}
        this._updateProgressIndicator(0);			
	},
	
	
    /*
     * Updates the page indicator to show which page the person is on.
     */
    _updateProgressIndicator:function(selectedPage){
        if(!this.progressButtons.childNodes.length)
            return;

        var children = this.getChildren();        
        for(var i=0; i<children.length;i++){
            if(selectedPage == i)
                dojo.addClass(this.progressButtons.childNodes[i], "selected")
            else    
                dojo.removeClass(this.progressButtons.childNodes[i], "selected")
        }      
    },
    
    
	/*
	 * Control how the buttons look from page to page.
	 */
	_toggleButtonStatuses:function(){
		var children = this.getChildren();
		var index = dojo.indexOf(children, this.selectedChildWidget);	
        this._updateProgressIndicator(index);
        
		// Previous Button
		if(index < 1){
			this._hideBtn(this.previousBtn);
		}
		else{
			this._showBtn(this.previousBtn);
		}
		
		//Next Button
		if(index ==  children.length-1){
			this._showBtn(this.completeBtn);
			this._hideBtn(this.nextBtn);
		}
		else{
			this._hideBtn(this.completeBtn);
			this._showBtn(this.nextBtn);
		}
	},
	
	
	/*
	 * Show Button
	 */
	_showBtn:function(btn){
		dojo.style(btn.domNode, "display", "");	
	},
	
	
	/*
	 * Hide Button
	 */
	_hideBtn:function(btn){
		dojo.style(btn.domNode, "display", "none");	
	},
	
	
	
	/* Internal Button events */
	_goCancel:function(){	
	},
	_goPrevious:function(){
		this.back();
		this._toggleButtonStatuses();
	},
	_goNext:function(){
		this.forward();
		this._toggleButtonStatuses();
	},
	_goComplete:function(){
		
	},
	
	
	/* Extension Button Events  (Ok to override these) */
	onClickCancel:function(){},
	
	onClickPrevious:function(){ this._goPrevious(); },
	
	onClickNext:function(){ this._goNext(); },
	
	onClickComplete:function(){}

});




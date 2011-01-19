dojo.provide("dojoui.Bind");

dojo.declare("dojoui.Bind", dijit._Widget,{
	variable:"",
	_propertyArray:"",
	_topic:"",
	_topicQue:"",

	
	postCreate:function(){
		this._propertyArray = this.variable.split(".");
		this._topic = this._propertyArray [0];
		_topicQue = dojo.subscribe(this._topic,this,"displayValue");
		
	},	
	
	
	displayValue:function(obj){
		var val = obj;
		
		for(var i=1;i<this._propertyArray.length;i++){
			val = val[this._propertyArray[i]];
		}
		
		if (this.domNode) {
      if(!val){
        dojo.attr(this.domNode, "innerHTML", '');
      }
      else{
        dojo.attr(this.domNode, "innerHTML", val);
      }

		}	
	}
	
	
});	

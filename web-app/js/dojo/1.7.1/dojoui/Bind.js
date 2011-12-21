dojo.provide("dojoui.Bind");

dojo.declare("dojoui.Bind", dijit._Widget, {
  variable:"",
  defaultValue:"",
  _propertyArray:"",
  _topic:"",
  _topicQue:"",


  postCreate:function() {
    this._propertyArray = this.variable.split(".");
    this._topic = this._propertyArray [0];
    _topicQue = dojo.subscribe(this._topic, this, "displayValue");
  },


  displayValue:function(obj) {
    if (!obj) {
      console.log('no object')
      return;
    }
    var val;
    for (var i = 1; i < this._propertyArray.length; i++) {
      val = obj[this._propertyArray[i]];
    }
    if (this.domNode) {
      if (!val) {
        dojo.attr(this.domNode, "innerHTML", this.defaultValue);
      }
      else {
        dojo.attr(this.domNode, "innerHTML", val);
      }

    }
  }


});	

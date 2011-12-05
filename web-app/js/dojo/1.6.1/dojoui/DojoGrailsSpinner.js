DojoGrailsSpinner = window.DojoGrailsSpinner || {

  /**
   * Creates the spinner object <div> and attaches it to the body as the first
   * element.
   */
  create:function() {
    var attrObj = {
      id:"grailsdojospinner",
      className:"grailsdojospinner"
    }
    var spinner = dojo.create("div", attrObj, dojo.body(), 'first');
    return spinner;
  },


  /**
   * Will display the spinner. If it doesn't exist, will create it first.
   */
  show:function() {
    if(!dojoGrailsPluginConfig.showSpinner){
      return;
    }
    var spinner = dojo.byId("grailsdojospinner");
    if (!spinner){
      spinner = this.create();
    }
    dojo.fadeIn({
      node:spinner,
      duration:100,
      beforeBegin: function(obj) {
        dojo.style(obj, "opacity", "0");
        dojo.style(obj, "display", "block");
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
    dojo.fadeOut({
      node:"grailsdojospinner",
      duration:300,
      onEnd: function(obj) {
        dojo.style(obj, "display", "none");
      }
    }).play();
  }

}

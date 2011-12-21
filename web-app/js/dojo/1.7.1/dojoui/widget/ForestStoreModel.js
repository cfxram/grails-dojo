dojo.provide("dojoui.widget.ForestStoreModel");
dojo.require("dijit.tree.ForestStoreModel");

dojo.declare("dojoui.widget.ForestStoreModel", [dijit.tree.ForestStoreModel], {

  /**
   * Overrides the get child method of the normal ForestStoreModel
   * @param parentItem
   * @param callback
   */
  getChildren:function(parentItem, callback) {
    if (parentItem === this.root) {
      if (this.root.children) {
        callback(this.root.children);
      }
      else {
        this.store.fetch({
          query: {parent: ''},
          onComplete: dojo.hitch(this, function(items) {
            this.root.children = items;
            callback(items);
          }),
          onError: this.onError
        });
      }
    }
    else {
      this.store.fetch({
        query: {parent: this.store.getIdentity(parentItem)},
        onComplete: dojo.hitch(this, function(items) {
          callback(items);
        }),
        onError: this.onError
      });
    }
  },


  /**
   * Overriding the ForestStoreModel to not do the ._requeryTop() method call.
   * @param item
   * @param attribute
   * @param oldValue
   * @param newValue
   */
  onSetItem: function(/* item */ item,
                  /* attribute-name-string */ attribute,
                  /* object | array */ oldValue,
                  /* object | array */ newValue){

    // Copied from TreeStoreModel
    if(dojo.indexOf(this.childrenAttrs, attribute) != -1){
        // item's children list changed
        this.getChildren(item, dojo.hitch(this, function(children){
            // See comments in onNewItem() about calling getChildren()
            this.onChildrenChange(item, children);
        }));
    }else{
        // item's label/icon/etc. changed.
        this.onChange(item);
    }
  }

});
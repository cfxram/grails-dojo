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
  }

});
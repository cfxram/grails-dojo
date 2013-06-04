define(["dojo/_base/declare", "dijit/tree/ForestStoreModel", "dojo/_base/lang", "dijit/tree/TreeStoreModel"],
function(declare, ForestStoreModel, lang, TreeStoreModel){
	return declare("dojoui.widget.ForestStoreModel", ForestStoreModel, {
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
						onComplete: lang.hitch(this, function(items) {
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
					onComplete: lang.hitch(this, function(items) {
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
			TreeStoreModel.prototype.onSetItem.apply(this, arguments);
		}		
	});
});

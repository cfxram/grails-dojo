dojo.provide("dojoui.widget.DataGrid");
dojo.require("dojox.grid.DataGrid");
dojo.require("dijit.form.CheckBox");
dojo.require("dojo.data.ItemFileWriteStore");


dojo.declare("dojoui.widget.DataGrid", dojox.grid.DataGrid, {
    indirectSelection:false,
    selectedRows:{},
    selectedStore:null,
    selectionMode:'none',
    templateString: dojo.cache("dojoui", "widget/templates/DataGrid.html"),
    
    /**
     * This overrides the built in can sort routine. It will turn off sorting of the
     * selection Col if it is enabled.
     * @param col
     */
    canSort:function(sortIndex){
        if(this.indirectSelection){
            return (Math.abs(sortIndex) != 1);
        }
        else{
            return true;
        }
    },

    /**
     * Sets the default sort established the internal this.sortInfo number.
     * sortInfo is a numeric index that is 1 based (Not zero). If the number
     * is positive then the sort is ascending. If the number is negative, then
     * the sort is descending.
     *
     */
    startup:function() {
        if (!this.sortFields[0].attribute) {
            return;
        }
        var cells = this.layout.cells;
        var sortIndex = 0;
        for (var i = 0; i < cells.length; i++) {
            if (cells[i].field == this.sortFields[0].attribute) {
                sortIndex = i;
                break;
            }
        }
        sortIndex += 1;
        if (this.sortFields[0].descending) {
            sortIndex *= -1;
        }
        this.sortInfo = sortIndex;
        this.inherited(arguments);                
        this.selectedStore = new dojo.data.ItemFileWriteStore({});
    },



    /**
     * Called from the GridTagLib, this sets the column structure. The
     * last item is a nulled object and can be removed.
     */
    setGridStructure:function(inStructure) {
        var cells = inStructure[0].cells;
        var newStruct = [
            {
                cells:[]
            }
        ];
        if (this.indirectSelection) {
            this.addIndirectSelect(newStruct);
        }
        for (var i = 0; i < cells.length - 1; i++) {
            newStruct[0].cells.push(cells[i]);
        }
        this.attr('structure', newStruct);
    },



    /**
     * Creates the select box to do indirect selection.
     */
    addIndirectSelect:function(inCellStructure) {
        var selectCell = {
            name:' ',
            width:'30px;',
            sort:false,
            formatter:function(value, rowIndex, obj) {
                var item = obj.grid.getItem(rowIndex);
                var id = obj.grid.store.getIdentity(item);
                var checked = obj.grid.isSelected(id);

                var checkBox = new dijit.form.CheckBox({
                    item:item,
                    name: "checkBox",
                    value: id,
                    checked: checked,
                    onChange: dojo.hitch(obj.grid, function(checked) {
                        if (checked) {
                            this.addRowToSelected(checkBox.item);
                        }
                        else {
                            this.removeRowFromSelected(checkBox.item);
                        }
                    })
                });
                return checkBox;
            }
        }
        inCellStructure[0].cells.push(selectCell);
        return true;
    },



    /**
     * Adds an item to the selection.
     * @param id
     */
    addRowToSelected:function(item) {
      var id = this.store.getIdentity(item);
      this.selectedRows['' + id] = true;
    },



    /**
     * Checks to see if a specific id is selected.
     * @param id
     */
    isSelected:function(id) {
      return this.selectedRows['' + id];
    },



    /**
     * Removes an item from the selected collection. 
     * @param id
     */
    removeRowFromSelected:function(item) {
      var id = this.store.getIdentity(item);         
      this.selectedRows['' + id] = false;
    }

});
/**
 * All Grids use a factory. This just wraps the standard grid factory.
 */
dojoui.widget.DataGrid.markupFactory = function(props, node, ctor, cellFunc) {
    return dojox.grid._Grid.markupFactory(props, node, ctor,
            dojo.partial(dojox.grid.DataGrid.cell_markupFactory, cellFunc));
};
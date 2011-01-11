dojo.provide("dojoui.widget.DataGrid");
dojo.require("dojox.grid.DataGrid");
dojo.require("dojox.grid.cells.dijit");

/**
 * Custom Grid Component to encapsulate customizations for grails specific behavior.
 */
dojo.declare("dojoui.widget.DataGrid", dojox.grid.DataGrid, {
  indirectSelection:true,

  
  /**
   * Sets the default sort established the internal this.sortInfo number.
   * sortInfo is a numeric index that is 1 based (Not zero). If the number
   * is positive then the sort is ascending. If the number is negative, then
   * the sort is descending.
   *  
   */
  postCreate:function(){
    if(!this.sortFields[0].attribute){
      return;
    }  
    var cells = this.layout.cells;
    var sortIndex = 0;              
    for(var i=0;i<cells.length;i++){
      if(cells[i].field == this.sortFields[0].attribute){
        sortIndex = i;
        break;
      }
    }
    sortIndex += 2;
    if(this.sortFields[0].descending){
      sortIndex *= -1;
    }
    this.sortInfo = sortIndex;  
    this.inherited(arguments);  
  },
  
  
  
  /**
   * Called from the GridTagLib, this sets the column structure. The
   * last item is a nulled object and can be removed.
   */
  setGridStructure:function(inStructure){
    var cells = inStructure[0].cells;
    var newStruct = [{                
      cells:[]
    }];
    if(this.indirectSelection){
      this.addIndirectSelect(newStruct);    
    }
    for(var i=0; i<cells.length-1; i++){
      newStruct[0].cells.push(cells[i]);
    }    
    this.attr('structure', newStruct);
  },
  
  
  
  /**
   * Creates the select box to do indirect selection.
   */
  addIndirectSelect:function(inCellStructure){
    var selectCell = {
        cellType:dojox.grid.cells.CheckBox,
        name:' ',
        editable:true,
        field:'select',
        width:'50px;'

        /*
        formatter:function(value,rowIndex,obj){
          var item = obj.grid.getItem(rowIndex);
          var idFieldName = obj.grid.store.getIdentityAttributes()[0];
          return "hi"
        } 
        */      
    }    
    
    inCellStructure[0].cells.push(selectCell);
    return true;
  }
  
});


/**
 * All Grids use a factory. This just wraps the standard grid factory.
 */
dojoui.widget.DataGrid.markupFactory = function(props, node, ctor, cellFunc){
	return dojox.grid._Grid.markupFactory(props, node, ctor, 
					dojo.partial(dojox.grid.DataGrid.cell_markupFactory, cellFunc));
};

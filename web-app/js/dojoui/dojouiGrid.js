dojo.provide("dojoui.dojouiGrid");

var dojoui = window.dojoui || {} 
dojoui.grid = {}


/**
 *  Will move a row element from one grid to another.
 * 
 * @param {String}  fromGridId
 * @param {String}  toGridId
 */
dojoui.grid.moveGridToGrid = function(/*String*/fromGridId, /*String*/toGridId){
	var fromGrid = dijit.byId(fromGridId);
	var toGrid = dijit.byId(toGridId);
	if(!fromGrid || !toGrid)
		return;

  var rows = fromGrid.selection.getSelected();

	dojo.forEach(rows, function(row){
		var newItem = toGrid.store.newItem(dojoui.grid.itemToObj(row))
		
		if (toGrid.delItems["elem_" + newItem.id]) {
			// It was marked as deleted but added back in so remove deletion
			toGrid.delItems["elem_" + newItem.id] = null;
		}
		else {
			// it's new... mark it as such.
			toGrid.newItems["elem_" + newItem.id] = true;
		}	
		if (!fromGrid.newItems["elem_" + newItem.id]) {
			// mark as deleted.
			fromGrid.delItems["elem_" + newItem.id] = true;
		}
		else {
			// just remove from newly added
			fromGrid.newItems["elem_" + newItem.id] = null;
		}
	});
	// Update display and commit changes to datastores.
	fromGrid.removeSelectedRows();
	fromGrid.store.save();
	toGrid.store.save();	

	// Serialize values to form elements.
	dojoui.grid.serializeToForm(dojo.byId(toGrid.id+"_added"), toGrid.newItems);
	dojoui.grid.serializeToForm(dojo.byId(toGrid.id+"_deleted"), toGrid.delItems);
	dojoui.grid.serializeToForm(dojo.byId(fromGrid.id+"_added"), fromGrid.newItems);
	dojoui.grid.serializeToForm(dojo.byId(fromGrid.id+"_deleted"), fromGrid.delItems);
  
   //Publish Changes
  var toGridNewCount = 0;
  var toGridRemovedCount = 0;
  var fromGridNewCount = 0;
  var fromGridRemovedCount = 0;
  for (var i in toGrid.newItems) {toGridNewCount++}
  for (var i in toGrid.delItems) {toGridRemovedCount++}
  for (var i in fromGrid.newItems) {fromGridNewCount++}
  for (var i in fromGrid.delItems) {fromGridRemovedCount++}
  dojo.publish(toGridId,[{added:toGridNewCount, removed:toGridRemovedCount}]);
  dojo.publish(fromGridId,[{added:fromGridNewCount, removed:fromGridRemovedCount}]); 
}



/**
 * Will serialize a form element. Called by dojoui.grid.moveGridToGrid.
 * 
 * @param {Object} elem
 * @param {Object} obj
 * 
 * @see dojoui.grid.moveGridToGrid
 */
dojoui.grid.serializeToForm = function(elem,obj){
	var valArray = [];
	for(var i in obj){
		if(obj[i]){
			valArray.push( i.replace("elem_","") );
		}
	}	
	elem.value = valArray.join(",");
}	
	

		
/**
 * Takes a data store item and returns an object without the array elements and hidden
 * properties. This is mostly used by moveGridToGrid.
 * 
 * @param {Object} row - Datastore Item
 */	
dojoui.grid.itemToObj = function(/*Datastore Item*/row){
	var obj = {};
	for(var i in row){
		if (i.charAt(0) != '_') {
			obj[i] = row[i][0];
		}	
	}	
	return obj;
}



/**
 * Used by rendered form elements in the grid to update the grid store.
 * 
 * @param {Object} gridId
 * @param {Object} rowIndex
 * @param {Object} field
 * @param {Object} formElem
 */
dojoui.grid.updateGridValue = function(gridId, rowIndex, field, formElem){
  var grid = dijit.byId(gridId)
  var item = grid.getItem(rowIndex);
  var newValue = "";
  if(formElem.tagName == 'INPUT'){
    if(formElem.type == 'checkbox'){
      newValue = formElem.checked;  
    }
    else{
      newValue = formElem.value;
    }
  }
  else if(formElem.tagName == 'SELECT'){
    //TODO: get value from select element
  }
  grid.store.setValue(item, field, newValue);
}

/**
 * Event handler applied to the form submission. This handler is added by 
 * dojoui.grid.createHiddenFormFieldsForGrid(). It will serialize all changes done
 * to a grid's client-side store and ready it to be transfered to the server.
 * 
 * @param {Object} gridId
 */
dojoui.grid.serializeGridChanges = function(gridId){
  var grid = dijit.byId(gridId);
  var cellArray = grid.structure[0].cells;  
  var vals = {}
  
  dojo.forEach(cellArray,function(cell){
    if (cell.field) {
      vals[cell.field] = null;
    }
  });
  var loopThrouhItems = function(items){
    for(var i=0;i<items.length;i++){
      if (grid.store.isDirty(items[i])) {
        for (h in vals) {
          var fieldName = gridId + '.' + h;
          dojo.create("input",{type:"hidden",name:fieldName,value:items[i][h][0]},gridId+'_formFields');
        }
      }    
    }
    
    // Make sure there is always more than 1 record so that grails will recognize this as an array.
      for (h in vals) {
        var fieldName = gridId + '.' + h;
        dojo.create("input",{type:"hidden",name:fieldName,value:""},gridId+'_formFields');
      }
    
  }
  grid.store.fetch({
    onComplete: loopThrouhItems
  });
}


/**
 * Creates hidden form fields associated to the grid. These will be updated with 
 * changed items and used to send changes back to the server in an easy format.
 * 
 * The hidden form fields will be appended to the DOM right after the grid element
 * so if the grid sits inside of a form element then these will be transmitted to 
 * the server. They are generated from the grid's cell structure and will take the
 * name of [gridId]_[fieldName].
 * 
 * @param {String}  gridId = The id of the grid object. 
 */
dojoui.grid.attachFormHandler = function(gridId){
  var grid = dijit.byId(gridId);

  var formElem = dojo.query(grid.domNode).parents("form").first();
  if(formElem[0]){
    dojo.connect(formElem[0],"onsubmit",function(e){
      dojoui.grid.serializeGridChanges(gridId);
    })
  }
} 



/**
 * This is a grid cell formatter to render a checkbox in a grid.
 * 
 * @param {Object} name
 * @param {Object} value
 * @param {Object} gridId
 * @param {Object} index
 * @param {Object} field
 */    
dojoui.grid.renderCheckbox = function(name, value, gridId, index, field){
  var checked = "";
  if(value)
    checked = "checked";
    
  var htmlString = '' +
  '<div style="float:left; width:100%; text-align:center">' + 
    '<label for="check_'+name+field+'">' + 
      '<input type="checkbox" value="'+value+'" id="check_'+name+field+'" '+checked+' class="dojoui-datagrid-center-column" ' +
        'onclick="dojoui.grid.updateGridValue(\''+gridId+'\','+index+', \''+field+'\', this)">' + 
    '</label>' +
  '</div>';
  return htmlString;
}
 
/**
 * This is a grid cell formatter to render a text box in a grid.
 * 
 * @param {Object} name
 * @param {Object} value
 * @param {Object} gridId
 * @param {Object} index
 * @param {Object} field
 */    
dojoui.grid.renderTextInput = function(name, value, gridId, index, field){
  // TODO: determine form field naming conventions.
  
  var htmlString = '' +
    '<input type="text" value="'+value+'" id="text_'+name+'"  '+
      'onchange="dojoui.grid.updateGridValue(\''+gridId+'\','+index+', \''+field+'\', this)">';
  return htmlString;
}

/**
 * This is a grid cell formatter to render a select box. (NOT COMPLETED) 
 * 
 * @param {Object} name
 * @param {Object} value
 * @param {Object} gridId
 * @param {Object} index
 * @param {Object} field
 * @param {Object} optionObj
 */    
dojoui.grid.renderSelectInput = function(name, value, gridId, index, field, optionObj){
  //TODO: complete the select box cell renderer.
  
  var htmlString = '' +
  '<select name="'+name+'" id="'+name+'" class="dojoui-datagrid-center-column" ' +
    'onchange="dojoui.grid.updateGridValue(\''+gridId+'\','+index+', \''+field+'\', this)">'; 
  
  htmlString += '' +
  '</select>'
  return htmlString;
}      


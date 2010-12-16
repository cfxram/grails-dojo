package org.dojotoolkit

class GridTagLib {
  static namespace = 'dojo'


  private String htmlProperties(params) {
    def paramString = ""
    params.each {k, v ->
      paramString += " ${k}=\"${v}\""
    }
    return paramString
  }


  private String randomId() {
    return "${Math.round(Math.random() * 100000)}"
  }

  /**
   * Creates a dataGrid on the page. The data for the data grid can come from
   * a remote dataset defined in json.
   */
  def grid = {attrs, body ->
    def id = attrs.remove("id") ?: "dojo_ui_grid${randomId()}"
    def storeId = attrs.remove("storeId") ?: "${id}_store"
    def onRowClick = attrs.remove("onRowClick") ?: ""
    def onRowDblClick = attrs.remove("onRowDblClick") ?: ""
    def href = attrs.remove("href") ?: g.createLink(attrs)

    out << """
        <div dojoType="dojo.data.ItemFileWriteStore" jsid="${storeId}" url="${href}" urlPreventCache="yes"></div>

        <table dojoType="dojox.grid.DataGrid" id="${id}" store="${storeId}" ${htmlProperties(attrs)}>
            <script type="dojo/method">
                var gridStruct = [{
                  cells:[
                        ${body()}
                    {hidden:true}
                  ]
                }]
                this.setStructure(gridStruct);
            </script>
            <script type="dojo/connect" event="startup">
                //dojoui.grid.attachFormHandler(this.id);
                this.newItems = {};
                this.delItems = {};
            </script>
            <script type="dojo/connect" event="onRowClick" args="e">
                var row = e.grid.getItem(e.rowIndex);
                // TODO: Destroy the publish que as it may previously exist. (This will prevent memory leaks)
                dojo.publish(this.id,[{"selected":row}]);
                dojo.publish(this.id,[e.grid]);
                ${onRowClick}
            </script>
            <script type="dojo/connect" event="onRowDblClick" args="e">
                var row = e.grid.getItem(e.rowIndex);
                ${onRowDblClick}
            </script>
        </table>
        <div id="${id}_formFields">
            <input type="hidden" name="${id}_added" id="${id}_added" value=""/>
            <input type="hidden" name="${id}_deleted" id="${id}_deleted" value=""/>
        </div>
    """

  }

  /**
   * A child tag of <dojo:grid>, this defines a column.
   */
  def col = {attrs, body ->
    def field = attrs.field ?: 'id'
    def width = attrs.width ?: '100'
    def name = attrs.name ?: ''
    def code = attrs.code ?: ''
    def renderType = attrs.renderType ?: ''    // can be checkBox, textInput, or select
    def cleanedString = body().encodeAsJavaScript()
    def formatter = "null"

    if (code.length()) {
      name = message(code: code);
    }
    // Manually Defined - Custom Formatting Found
    if (cleanedString.length()) {
      formatter = """
                function(value,rowIndex,obj){
                    var item = obj.grid.getItem(rowIndex);
                    var row = {}
                    for(i in item){
                      row[i] = (item[i]) ? item[i][0] : '';
                    }
                    var formatTemplate = ' ${cleanedString} ';
                    return dojo.replace(formatTemplate, {"row":row});
                }
            """
    }
    // Checkbox Render Type Found
    else if (renderType == "checkBox") {
      formatter = """
                function(value,rowIndex,obj){
                    var item = obj.grid.getItem(rowIndex);
                    var idFieldName = obj.grid.store.getIdentityAttributes()[0];
                    return dojoui.grid.renderCheckbox(item[idFieldName][0], value, obj.grid.id, rowIndex, '${field}');
                }
            """
    }
    // Text Render Type Found
    else if (renderType == "textInput") {
      formatter = """
                function(value,rowIndex,obj){
                    var item = obj.grid.getItem(rowIndex);
                    var idFieldName = obj.grid.store.getIdentityAttributes()[0];
                    return dojoui.grid.renderTextInput(item[idFieldName][0], value, obj.grid.id, rowIndex, '${field}');
                }
            """
    }

    out << """
            {
                name:"${name}",
                field:"${field}",
                width:"${width}",
                formatter:${formatter}
            },
        """
  }

}

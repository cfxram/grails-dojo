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
    def href = attrs.remove("href") ?: g.createLink(attrs)
    def max = attrs.remove("max") ?: 1000
    def sort = attrs.remove("sort") ?: ""
    def order = attrs.remove("order") ?: "asc" // asc or desc    
    def descending = (order == "desc") ? "true" : "false"
    def selectable = attrs.remove("selectable") ?: "false"
    def header = attrs.remove("header") ?: null

    out << dojo.require(modules:['dojoui.data.GrailsQueryReadStore','dojoui.widget.DataGrid','dijit.layout.BorderContainer','dijit.layout.ContentPane','dojoui.Bind'])    
    out << dojo.css(file:"dojox/grid/resources/Grid.css")
    out << dojo.css(file:"dojox/grid/resources/tundraGrid.css") 
    out << dojo.css(file:"dojoui/widget/resources/dataGrid.css") 
    // Data Source 
    out << """
      <div dojoType="dojoui.data.GrailsQueryReadStore" jsid="${storeId}" url="${href}" max="${max}"></div>
    """
      
    // Grid  
    out << """
      <div dojoType="dijit.layout.BorderContainer" class="${attrs.remove('class')}" style="${attrs.remove('style')}; padding:2px" gutters="false">
        <div dojoType="dijit.layout.ContentPane" region="top" class="grails-dojo-grid-header" style="height:30px">        
          ${(header && (header instanceof Closure)) ? header.call() : header}
        </div>
        <div dojoType="dijit.layout.ContentPane" region="center" style="height:95%; padding:0">      
          <div dojoType="dojoui.widget.DataGrid" id="${id}" store="${storeId}" ${htmlProperties(attrs)} rowsPerPage="${max}" style="border:1px solid #ccc"
            sortFields="[{attribute:'${sort}',descending:${descending}}]" selectable="${selectable}">
            <script type="dojo/method">
                var gridStruct = [{                
                  cells:[
                    ${body()}
                    {hidden:true}
                  ]
                }]
                this.setGridStructure(gridStruct);                
            </script>
          </div>
          
        </div>
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
    // Custom Formatting Found so create a formatter.
    if (cleanedString.length()) {
      formatter = """
        function(value,rowIndex,obj){
            var item = obj.grid.getItem(rowIndex).i; 
            var formatTemplate = ' ${cleanedString} ';
            return dojo.replace(formatTemplate, {"row":item});
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

  
  
  /**
   * Binds a span tag's content to a data property.
   */
   def bind = {attrs, body ->
     out << """
       <span dojoType="dojoui.Bind" ${htmlProperties(attrs)}></span>
     """
   }  
   
   
}

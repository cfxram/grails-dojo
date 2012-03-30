package org.dojotoolkit
import org.dojotoolkit.TagLibUtil as Util

class GridTagLib {
  static namespace = 'dojo'
  

  /**
   * Outputs the css and javascript files required for the grid.
   */
  def gridResources = {attrs,body ->
	def theme = attrs?.remove("theme") ?: "tundra"
    out << dojo.require(modules:['dojoui.data.GrailsQueryReadStore','dojoui.widget.DataGrid','dijit.layout.BorderContainer','dijit.layout.ContentPane','dojoui.Bind'])    
    out << dojo.css(file:"dojox/grid/resources/Grid.css")
    out << dojo.css(file:"dojox/grid/resources/${theme}Grid.css") 
    out << dojo.css(file:"dojoui/widget/resources/dataGrid.css")
  }



  /**
   * Creates a dataGrid on the page. The data for the data grid can come from
   * a remote dataset defined in json.
   */
  def grid = {attrs, body ->
    def name = attrs.remove("name") ?: "dojo_ui_grid${Util.randomId()}"
    def storeId = attrs.remove("storeId") ?: "${name}_store"
    def href = attrs.remove("href") ?: g.createLink(attrs)
    def max = attrs.remove("max") ?: 1000
    def sort = attrs.remove("sort") ?: ""
    def order = attrs.remove("order") ?: "asc" // asc or desc    
    def descending = (order == "desc") ? "true" : "false"
    def selectable = attrs.remove("selectable") ?: "false"
    def header = attrs.remove("header") ?: null

    // Clean up the properties added.
    attrs.remove("action")
    attrs.remove("controller")
    attrs.remove("params")
    attrs.remove("id")


    // Data Source 
    out << """
      <div dojoType="dojoui.data.GrailsQueryReadStore" jsid="${storeId}" url="${href}" max="${max}"></div>
    """
      
    // Grid
    if(header){
      out << """
        <div dojoType="dijit.layout.BorderContainer" class="${attrs.remove('class')}" style="${attrs.remove('style')}; padding:2px" gutters="false">
          <div dojoType="dijit.layout.ContentPane" region="top" class="grails-dojo-grid-header" style="height:30px">
            ${(header && (header instanceof Closure)) ? header.call() : header}
          </div>
          <div dojoType="dijit.layout.ContentPane" region="center" style="height:95%; padding:0">
            <div dojoType="dojoui.widget.DataGrid" id="${name}" store="${storeId}" ${Util.htmlProperties(attrs)} rowsPerPage="${max}" style="border:1px solid #ccc"
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
    else{
      out << """
        <div dojoType="dojoui.widget.DataGrid" id="${name}" store="${storeId}" ${Util.htmlProperties(attrs)} rowsPerPage="${max}"
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
      """
    }
  }




  /**
   * A child tag of <dojo:grid>, this defines a column.
   */
  def col = {attrs, body ->
    def field = attrs.field ?: 'id'
    def width = attrs.width ?: '100'
    def label = attrs.label ?: ''
    def code = attrs.code ?: ''
    def cleanedString = body().encodeAsJavaScript()
    def formatter = attrs.formatter ?: "null"

    if (code.length()) {
      label = message(code: code);
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
          name:"${label}",
          field:"${field}",
          width:"${width}",
          formatter:${formatter}
      },
    """
  }

  
  
  /**
   * Binds a span tag's content to a data property.
   * @param attrs.variable
   * @param attrs.default
   */
   def bind = {attrs, body ->
     attrs.defaultValue = attrs.remove("default") ?: ""
     out << """
       <span dojoType="dojoui.Bind" ${Util.htmlProperties(attrs)}>${attrs.defaultValue}</span>
     """
   }  



   /**
    * Creates a button with the correct javascript to reload the grid
    * data. Good for reseting a grid after a form has been run.
    */
   def gridLoadDataButton = {attrs,body ->
     attrs.onclick = attrs?.onclick ?: ''
     def grid = attrs.remove('grid')
     def code = attrs.remove('code')
     def label = attrs.remove('label')
     def form = attrs.remove('form') ?: ''     
     def btnClick = "dijit.byId('${grid}').query('${form}')"
          
     if(code?.length()){
       label = message(code:code)
     }     
     
     attrs?.onclick = btnClick + "; " + attrs.onclick;     
     out << """
      <button ${Util.htmlProperties(attrs)}>${label}</button>
     """
   }
   
   
   
}

package org.dojotoolkit
import org.dojotoolkit.TagLibUtil as Util

class GridTagLib {
  static namespace = 'dojo'
  

  /**
   * Outputs the css and javascript files required for the grid.
   */
  def gridResources = {attrs,body ->
	def theme = attrs?.remove("theme") ?: "tundra"
    out << dojo.require(modules:['dojoui/data/GrailsQueryReadStore','dojoui/widget/DataGrid','dijit/layout/BorderContainer','dijit/layout/ContentPane','dojoui/Bind'])    
    out << dojo.css(file:"dojox/grid/resources/Grid.css")
    out << dojo.css(file:"dojox/grid/resources/${theme}Grid.css") 
    out << dojo.css(file:"dojoui/widget/resources/dataGrid.css")
  }



  /**
   * Creates a dataGrid on the page. The data for the data grid can come from
   * a remote dataset defined in json.
   * 
   * Advanced note: Any attributes that are specified on this tag that match an
   * HTML5 attribute will be used directly on the tag. Any other attributes will be passed
   * as settings to the Dojo Widget.
   * 
   * @attr header Either a string or {@link Closure} for the Header label
   */
  def grid = {attrs, body ->
    def name = attrs.remove("name") ?: "dojo_ui_grid${Util.randomId()}"
    def storeId = attrs.remove("storeId") ?: "${name}_store"
    def href = attrs.remove("href") ?: g.createLink(attrs)
    def max = attrs.remove("max") ?: 1000
    def sort = attrs.remove("sort") ?: ""
    def order = attrs.remove("order") ?: "asc" // asc or desc    
    def descending = (order == "desc") ? "true" : "false"
    def selectable = Util.toBoolean(attrs.remove("selectable"))
    def header = attrs.remove("header") ?: null

    // Clean up the properties added.
    attrs.remove("action")
    attrs.remove("controller")
    attrs.remove("params")
    attrs.remove("id")


    // Data Source 
    out << """
      <div data-dojo-type="dojoui/data/GrailsQueryReadStore" data-dojo-id="${storeId}" data-dojo-props="url: '${href}', max: ${max}"></div>
    """
	attrs['data-dojo-props'] = "store: ${storeId}, rowsPerPage: ${max}, sortFields: [{attribute:'${sort}',descending:${descending}}], selectable: ${selectable}"
	      
    // Grid
    if(header){
      out << """
        <div data-dojo-type="dijit/layout/BorderContainer" class="${attrs.remove('class')}" style="${attrs.remove('style')}; padding:2px" data-dojo-props="gutters: false">
          <div data-dojo-type="dijit/layout/ContentPane" class="grails-dojo-grid-header" style="height:30px" data-dojo-props="region: 'top'">
            ${(header && (header instanceof Closure)) ? header.call() : header}
          </div>
          <div data-dojo-type="dijit/layout/ContentPane" style="height:95%; padding:0" data-dojo-props="region: 'center'">
            <div id="${name}" style="border:1px solid #ccc" ${Util.htmlProperties(attrs)} data-dojo-type="dojoui/widget/DataGrid" data-dojo-props="${Util.dataDojoProps(attrs).encodeAsHTML()}">
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
        <div id="${name}" ${Util.htmlProperties(attrs)} data-dojo-type="dojoui/widget/DataGrid" data-dojo-props="${Util.dataDojoProps(attrs).encodeAsHTML()}">
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
          var templateValue = ""
          require(['dojo/_base/lang'], function(lang){
            templateValue = lang.replace(formatTemplate, {"row":item});
          });
          return templateValue
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
   * 
   * Advanced note: Any attributes that are specified on this tag that match an
   * HTML5 attribute will be used directly on the tag. Any other attributes will be passed
   * as settings to the Dojo Widget.
   * 
   * @attr variable
   * @attr default
   */
   def bind = {attrs, body ->
     attrs.defaultValue = attrs.remove("default") ?: ""
     out << """
       <span ${Util.htmlProperties(attrs)} data-dojo-type="dojoui/Bind" data-dojo-props="${Util.dataDojoProps(attrs).encodeAsHTML()}">${attrs.defaultValue}</span>
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
     def btnClick = "require(['dijit/registry'], function(registry){registry.byId('${grid}').query('${form}');});"
          
     if(code?.length()){
       label = message(code:code)
     }     
     
     attrs?.onclick = btnClick + "; " + attrs.onclick;     
     out << """
      <button type="${attrs.remove('type')}" ${Util.htmlProperties(attrs)}>${label}</button>
     """
   }
   
   
   
}

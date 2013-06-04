package org.dojotoolkit
import org.dojotoolkit.TagLibUtil as Util

class DataSourceViewTagLib {
  static namespace = 'dojo'


  /**
   * 
   * @deprecated Dojo now automatically imports required classes for parsed widgets from data-dojo-type.  
   */
  @Deprecated
  def dataSourceViewResources = {attrs,body ->
        out << dojo.require(modules:['dojoui/widget/DataSourceView', 'dojo/data/ItemFileWriteStore'])
  }
  
  

  /**
   * Will render content based on data defined in a dojo data source like dojo/data/ItemFileWriteStore.
   * 
   * Advanced note: Any attributes that are specified on this tag that match an
   * HTML5 attribute will be used directly on the tag. Any other attributes will be passed
   * as settings to the Dojo Widget.
   * 
   * @attr name - The id of the dataSourceView
   * @attr store - The data store name to watch. This can be a store from a dojo:tree or a dojo:grid.
   * @attr data - This is Json formatted data that will be used as the store instead of the params.store.
   */
  def dataSourceView = {attrs, body ->
    def name = attrs.remove("name") ?: "dojo_ui_dataSourceView${Util.randomId()}"
    def store = attrs.remove("store") ?: "${name}_store"
	
	attrs['data-dojo-props'] = "store: ${store}"
	
    // Define empty data store json string is attached
    if(attrs?.data){
	  // TODO use the new dojo/store API in the future
      out << """
        <div data-dojo-type="dojo/data/ItemFileWriteStore" data-dojo-id="${store}" data-dojo-props="urlPreventCache: 'yes'">
          <script type="dojo/method">
            var myData = ${attrs.remove("data")};
            this.data = myData;
          </script>
        </div>
      """
    }

    out << """
      <div id="${name}" ${Util.htmlProperties(attrs)} data-dojo-type="dojoui/widget/DataSourceView" data-dojo-props="${Util.dataDojoProps(attrs).encodeAsHTML()}">
        <script type="dojo/aspect" data-dojo-advice="after" data-dojo-method="postCreate" data-dojo-args="args">
          this.store = ${store}
        </script>
        ${body()}
      </div>
    """
  }



  /**
   * Child tag of dataSource. Allows customization of what is displayed when no items are
   * displayed.
   */
  def noItemTemplate = {attrs, body ->
    out << """
      <script type="dojo/method">
        this.noItemTemplate = '${Util.jsSafeString(body()) }';
      </script>
    """
  }



  /**
   * Child tag of dataSource. Allows customization of what is displayed when custom template is not found
   */
  def nodeDefaultTemplate = {attrs, body ->
    out << """
      <script type="dojo/method">
        this.defaultNodeTemplate = '${Util.jsSafeString(body()) }';
      </script>
    """
  }



  /**
   * Child tag of dataSourceView. Allows customization of how the data elements
   * will display.
   *
   * @attr field (Optional) if defined, then value must be present.
   * @attr value (Optional) if present will define a template view for an item that has the field with a specific value.
   * @attr django (Optional) If true then this template will be rendered via the dojox.dtl package. You can use limited django syntax.
   */
  def nodeTemplate = {attrs, body ->
    def field = attrs.field ?: ''
    def value = attrs.value ?: ''
    def django = attrs.django ?: 'false'
    if (field.length() && value.length()) {
      out << """
        <script type="dojo/method">
          this.registerNodeStateTemplate('${field}','${value}',${django},'${Util.jsSafeString(body()) }');
        </script>
      """
    }
    else {
      out << """
        <script type="dojo/method">
          this.defaultNodeTemplate = '${Util.jsSafeString(body()) }';
        </script>
      """
    }
  }

}
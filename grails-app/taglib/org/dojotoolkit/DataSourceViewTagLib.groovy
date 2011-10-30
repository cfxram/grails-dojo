package org.dojotoolkit
import org.dojotoolkit.TagLibUtil as Util

class DataSourceViewTagLib {
  static namespace = 'dojo'



  def dataSourceViewResources = {attrs,body ->
        out << dojo.require(modules:['dojoui.widget.DataSourceView'])    
  }
  
  

  /**
   * Will render content based on data defined in a dojo data source like dojo.data.ItemFileWriteStore.
   */
  def dataSourceView = {attrs, body ->
    def id = attrs.remove("id") ?: "dojo_ui_dataSourceView${Util.randomId()}"
    def store = attrs.remove("store") ?: "${id}_store"

    out << """
      <div dojoType="dojoui.widget.DataSourceView" id="${id}" ${Util.htmlProperties(attrs)}>
        <script type="dojo/connect" method="postCreate" args="args">
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
   * @param field (Optional) if defined, then value must be present.
   * @param value (Optional) if present will define a template view for an item that has the field with a specific value.
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
package org.dojotoolkit

class DataSourceViewTagLib {
  static namespace = 'dojo'


  def jsSafeString(text) {
    def cleanedString = text.replaceAll(/\s+/, " ")  //replace all internal whitespace
    cleanedString = cleanedString.replaceAll(/(^\s)|(\s$)/, "") // trim whitespace
    cleanedString = cleanedString.replaceAll(/\'/, /\\\'/)  // escape single quotes
    return cleanedString
  }
  

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
   * Will render content based on data defined in a dojo data source like dojo.data.ItemFileWriteStore.
   * Will create the datastore if you specify the href or controller/action params.
   */
  def dataSourceView = {attrs, body ->
    def id = attrs.remove("id") ?: "dojo_ui_dataSourceView${randomId()}"
    def store = attrs.remove("store") ?: "${id}_store"
    def href = attrs.remove("href") ?: g.createLink(attrs)
    
    out << dojo.require(modules:['dojoui.widget.DataSourceView'])    
    out << """
      <div dojoType="dojoui.widget.DataSourceView" id="${id}" ${htmlProperties(attrs)}>
        <script type="dojo/connect" method="postCreate" args="args">
          this.store = ${store}
          console.log(${store});
          console.log(this.store);
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
        this.noItemTemplate = '${jsSafeString(body()) }';
      </script>
    """
  }



  /**
   * Child tag of dataSource. Allows customization of what is displayed when custom template is not found
   */
  def nodeDefaultTemplate = {attrs, body ->
    out << """
      <script type="dojo/method">
        this.defaultNodeTemplate = '${jsSafeString(body()) }';
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
          this.registerNodeStateTemplate('${field}','${value}',${django},'${jsSafeString(body()) }');
        </script>
      """
    }
    else {
      out << """
        <script type="dojo/method">
          this.defaultNodeTemplate = '${jsSafeString(body()) }';
        </script>
      """
    }
  }

}
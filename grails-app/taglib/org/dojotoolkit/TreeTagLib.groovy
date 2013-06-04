package org.dojotoolkit
import org.dojotoolkit.TagLibUtil as Util

class TreeTagLib {
  static namespace = 'dojo'

  /**
   * This will bring in all the resources required by the dialog tag.
   */
  def treeResources = {attrs, body ->
    out << dojo.require(modules: ['dojoui/widget/Tree','dojoui/widget/ForestStoreModel','dojo/data/ItemFileWriteStore'])
  }


  /**
   * This will create a client-side tree structure that allows any type of
   * html into the treenodes. It requires a href that points to JSON formated
   * output.
   */
  def tree = {attrs, body ->
    attrs.childField = attrs?.childField ?: ''
    attrs.showRoot = "true".equalsIgnoreCase(attrs.showRoot ?: 'false')
    attrs.rootLabel = attrs.rootLabel ?: 'TreeRoot'
    attrs.autoExpand = "true".equalsIgnoreCase(attrs.autoExpand ?: 'false')
    attrs.style = attrs.style ?: ''
    attrs.searchAble = "true".equalsIgnoreCase(attrs.searchAble ?: 'false')
    attrs.expandFirstChild = "true".equalsIgnoreCase(attrs.expandFirstChild ?: 'false')
    attrs.persist = "true".equalsIgnoreCase(attrs.persist ?: 'false')

    if (attrs?.controller || attrs?.action) {
      attrs.href = createLink(attrs)
      attrs.remove('controller')
      attrs.remove('action')
      attrs.remove('params')
      attrs.remove('id')
    }
    def id = attrs.remove("name") ?: "dojo_ui_tree${Util.randomId()}"
    def url = attrs.remove("href")


    // Define empty data store json string is attached
    if(attrs?.data){
      out << """
        <div data-dojo-type="dojo/data/ItemFileWriteStore" data-dojo-id="${id}_store" data-dojo-props="urlPreventCache: 'yes'">
          <script type="dojo/method">
            var myData = ${attrs.remove("data")};
            this.data = myData;
          </script>
        </div>
      """
    }
    else{
      out << """
        <div data-dojo-type="dojo/data/ItemFileWriteStore" data-dojo-id="${id}_store" data-dojo-props="url: '${url}', urlPreventCache: 'yes'"></div>
      """
    }

	attrs['data-dojo-props'] = "model: ${id}_forestStore"
	
    out << """
      <div data-dojo-id="${id}_forestStore" data-dojo-type="dojoui/widget/ForestStoreModel" data-dojo-props="rootLabel: '${attrs.rootLabel}', rootId: 'treeRoot', store: ${id}_store, childrenAttrs: ['${attrs.childField}']"></div>
      <div id="${id}" ${Util.htmlProperties(attrs)} data-dojo-type="dojoui/widget/Tree" data-dojo-props="${Util.dataDojoProps(attrs).encodeAsHTML()}">
        <script type="dojo/method" data-dojo-event="setGrailsPluginProperties">
          ${body()}
        </script>
      </div>
    """
  }


  /**
   * Child tag of tree. Allows customization of how the node elements
   * will display.
   */
  def treeNode = {attrs, body ->
    out << """
      this.defaultNodeTemplate = '${Util.jsSafeString(body()) }';
    """
  }


  /**
   * Child tag of tree. Allows customization of how the tree elements
   * will display.
   *
   * @param field (Optional) if defined, then value must be present.
   * @param value (Optional) if present will define a template view for an item that has the field with a specific value.
   */
  def treeLeaf = {attrs, body ->
    def field = attrs.field ?: ''
    def value = attrs.value ?: ''
    if (field.length() && value.length()) {
      out << """
        this.registerNodeStateTemplate('${field}','${value}','${Util.jsSafeString(body()) }');
      """
    }
    else {
      out << """
        this.defaultLeafTemplate = '${Util.jsSafeString(body()) }';
      """
    }
  }

}
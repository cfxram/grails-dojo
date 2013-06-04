package org.dojotoolkit
import org.dojotoolkit.TagLibUtil as Util

class TreeTagLib {
  static namespace = 'dojo'

  /**
   * This will bring in all the resources required by the dialog tag.
   * 
   * @deprecated Dojo now automatically imports required classes for parsed widgets from data-dojo-type.  
   */
  @Deprecated
  def treeResources = {attrs, body ->
    out << dojo.require(modules: ['dojoui/widget/Tree','dojoui/widget/ForestStoreModel','dojo/data/ItemFileWriteStore'])
  }


  /**
   * This will create a client-side tree structure that allows any type of
   * html into the treenodes. It requires a href that points to JSON formated
   * output.
   * 
   * Advanced note: Any attributes that are specified on this tag that match an
   * HTML5 attribute will be used directly on the tag. Any other attributes will be passed
   * as settings to the Dojo Widget.
   * 
   * @attr childField The name of the field on each data item to return children
   * @attr showRoot Whether to show the root node
   * @attr rootLabel The root node's label
   * @attr autoExpand Whether the tree should automatically expand nodes
   * @attr searchAble Whether the tree should show its search bar
   * @attr expandFirstChild Whether the tree should expand its first node
   * @attr persist Whether the tree should store its settings between page views
   * @attr href URL for store JSON, can be used instead of controller + action + params + id
   * @attr controller Passed to Grails to resolve URL for store JSON
   * @attr action Passed to Grails to resolve URL for store JSON
   * @attr params Passed to Grails to resolve URL for store JSON
   * @attr id Passed to Grails to resolve URL for store JSON
   */
  def tree = {attrs, body ->
    attrs.childField = attrs?.childField ?: ''
    attrs.showRoot = Util.toBoolean(attrs.showRoot)
    attrs.rootLabel = attrs.rootLabel ?: 'TreeRoot'
    attrs.autoExpand = Util.toBoolean(attrs.autoExpand)
    attrs.style = attrs.style ?: ''
    attrs.searchAble = Util.toBoolean(attrs.searchAble)
    attrs.expandFirstChild = Util.toBoolean(attrs.expandFirstChild)
    attrs.persist = Util.toBoolean(attrs.persist)

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
   * @attr field (Optional) if defined, then value must be present.
   * @attr value (Optional) if present will define a template view for an item that has the field with a specific value.
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
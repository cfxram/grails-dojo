package org.dojotoolkit
import org.dojotoolkit.TagLibUtil as Util

class TreeTagLib {
  static namespace = 'dojo'

  /**
   * This will bring in all the resources required by the dialog tag.
   */
  def treeResources = {attrs, body ->
    out << dojo.require(modules: ['dojoui.widget.Tree','dojoui.widget.ForestStoreModel','dojo.data.ItemFileWriteStore'])
  }


  /**
   * This will create a client-side tree structure that allows any type of
   * html into the treenodes. It requires a href that points to JSON formated
   * output.
   */
  def tree = {attrs, body ->
    attrs.children = attrs?.children ?: ''
    attrs.showRoot = attrs.showRoot ?: 'false'
    attrs.rootLabel = attrs.rootLabel ?: 'Tree Root'
    attrs.autoExpand = attrs.autoExpand ?: 'false'
    attrs.style = attrs.style ?: ''
    attrs.searchAble = attrs.searchAble ?: 'false'
    attrs.expandFirstChild = attrs.expandFirstChild ?: 'true'

    if (attrs?.controller || attrs?.action) {
      attrs.href = createLink(attrs)
      attrs.remove('controller')
      attrs.remove('action')
      attrs.remove('params')
      attrs.remove('id')
    }
    def id = attrs.remove("name") ?: "dojo_ui_tree${Util.randomId()}"
    def url = attrs.remove("href")

    out << """
      <div dojoType="dojo.data.ItemFileWriteStore" jsid="${id}_store" url="${url}" urlPreventCache="yes"></div>
      <div dojoType="dojoui.widget.ForestStoreModel" rootLabel="${attrs.rootLabel}" rootId="treeRoot"
          store="${id}_store" jsid="${id}_forestStore" childrenAttrs="${attrs.children}"></div>
      <div dojoType="dojoui.widget.Tree" model="${id}_forestStore" id="${id}" ${Util.htmlProperties(attrs)}>
        <script type="dojo/method">${body()}</script>
      </div>
    """
  }


  /**
   * Child tag of tree. Allows customization of how the node elements
   * will display.
   */
  def node = {attrs, body ->
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
  def leaf = {attrs, body ->
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
package org.dojotoolkit
import org.dojotoolkit.TagLibUtil as Util


class EditorTagLib {
  static namespace = 'dojo'

  /**
   * Outputs the required javascript dojo libraries
   */
  def editorResources = {attrs,body ->
    out << dojo.require(modules:['dojoui.widget.Editor'])
    out << dojo.css(file:"dojox/editor/plugins/resources/css/PasteFromWord.css")
  }



  /**
   * Creates a dojo rich text editor with default values chosen.
   */
  def editor = {attrs, body ->
    attrs.type = attrs?.type ?: ""   // Can be simple || intermediate || advanced
    attrs.height = attrs?.height ?: "150px" // IE 6 doesn't recognize the height style attribute.
    attrs.class =  (attrs?.class) ? "${attrs?.class} DojoUiEditor" : "DojoUiEditor"
    attrs.name = attrs?.name ?: attrs?.id ?: "dojo_editor_${Util.randomId()}"
    attrs.id = attrs?.id ?: attrs.remove("elementId") ?: attrs.name
    def style = attrs.remove("style") ?: ""
    def value = attrs.remove("value") ?: body()

    out << """
      <fieldset class="dojo-ui-editor" style="${style}">
        <div dojoType="dojoui.widget.Editor" ${Util.htmlProperties(attrs)}>${value}</div>
      </fieldset>
    """

  }

}

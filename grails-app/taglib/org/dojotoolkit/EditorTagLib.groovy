package org.dojotoolkit
import org.dojotoolkit.TagLibUtil as Util


class EditorTagLib {
  static namespace = 'dojo'

  /**
   * Outputs the required javascript dojo libraries
   */
  def editorResources = {attrs,body ->
    out << dojo.require(modules:['dijit.Editor','dijit._editor.plugins.LinkDialog','dijit._editor.plugins.FontChoice'])
  }



  /**
   * Creates a dojo rich text editor with default values chosen.
   */
  def editor = {attrs, body ->
    attrs.name = attrs?.name ?: "dojo_editor_${Util.randomId()}"
    attrs.height = attrs?.height ?: "150px"
    attrs.dojoType = "dijit.Editor"
    attrs.class = attrs?.class + " DojoUiEditor"
    attrs?.id = attrs?.elementId ?: attrs?.id ?: attrs?.name
    def value = attrs.remove("value") ?: body()

    def defaultPlugins = """
      ['bold','italic','underline','|',
      'insertOrderedList','insertUnorderedList','indent','outdent','|',
      'justifyLeft','justifyCenter', '|',
      'createLink', 'dijit._editor.plugins.EnterKeyHandling','|', {name:'fontSize', plainText: true}]
    """
    attrs.plugins = attrs?.plugins ?: defaultPlugins

    out << """
      <fieldset class="dojo-ui-editor">
        <div ${Util.htmlProperties(attrs)}>
          <script type="dojo/connect" event="onDisplayChanged">
            dojo.byId('${attrs?.name}').value = this.attr('value')
          </script>
          ${value}
        </div>
      </fieldset>
    """
  }

}

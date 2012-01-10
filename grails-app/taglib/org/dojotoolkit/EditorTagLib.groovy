package org.dojotoolkit
import org.dojotoolkit.TagLibUtil as Util


class EditorTagLib {
  static namespace = 'dojo'

  /**
   * Outputs the required javascript dojo libraries
   */
  def editorResources = {attrs,body ->
    out << dojo.require(modules:[
            'dijit.Editor',
            'dijit._editor.plugins.TextColor',
            'dijit._editor.plugins.LinkDialog',
            'dijit._editor.plugins.ViewSource',
            'dijit._editor.plugins.FontChoice',
            'dojox.editor.plugins.PasteFromWord'
          ])
    out << dojo.css(file:"dojox/editor/plugins/resources/css/PasteFromWord.css")
  }



  /**
   * Creates a dojo rich text editor with default values chosen.
   */
  def editor = {attrs, body ->
    attrs.type = attrs?.type ?: "simple"   // Can be simple || intermediate || advanced
    attrs.height = attrs?.height ?: "150px"
    attrs.class =  (attrs?.class) ? "${attrs?.class} DojoUiEditor" : "DojoUiEditor"
    attrs.name = attrs?.name ?: attrs?.id ?: "dojo_editor_${Util.randomId()}"
    attrs.id = attrs?.id ?: attrs.remove("elementId") ?: attrs.name
    def value = attrs.remove("value") ?: body()

    def undo        = " 'undo', 'redo' "
    def msPaste     = " 'pastefromword' "
    def fontSimple  = " 'bold', 'italic', 'underline', 'strikethrough' "
    def lists       = " 'insertOrderedList', 'insertUnorderedList' "
    def indent      = " 'indent', 'outdent' "
    def justify     = " 'justifyLeft', 'justifyRight', 'justifyCenter'"
    def fontExtra   = " 'fontName', 'fontSize' "
    def color       = " 'foreColor', 'hiliteColor' "

    def defaultPlugins = " ${fontSimple}, '|', ${lists}, '|',  ${indent}, '|', ${justify}, '|', ${msPaste}"
     /*
      WARNING - RM (12-20-2011)
      Don't use the "dijit._editor.plugins.EnterKeyHandling" plugin because of these bugs:
      http://bugs.dojotoolkit.org/ticket/13399
      http://bugs.dojotoolkit.org/ticket/13744
     */

    // Define default sets of plugins
    if(attrs?.type == "simple"){

    }
    else if(attrs?.type == "intermediate"){
      defaultPlugins += ", '|', ${fontExtra}, '|','createLink','viewsource'"
    }
    else if(attrs?.type == "advanced"){
      defaultPlugins = "${undo}, '|', ${defaultPlugins}, '|', ${color}, '|', 'createLink','viewsource' "
    }

    def plugins = attrs.remove("plugins") ?: defaultPlugins

    out << """
      <fieldset class="dojo-ui-editor">
        ${g.hiddenField([name: attrs?.name, value: value])}
        <div dojoType="dijit.Editor" ${Util.htmlProperties(attrs)} data-dojo-props="plugins:[${plugins}]">
            <script type="dojo/connect" event="onDisplayChanged">
              dojo.byId('${attrs?.name}').value = this.attr('value')
            </script>
            ${value}
        </div>
      </fieldset>
    """

  }

}

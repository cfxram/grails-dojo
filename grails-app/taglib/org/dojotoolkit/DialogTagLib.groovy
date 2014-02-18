package org.dojotoolkit

import org.dojotoolkit.TagLibUtil as Util

/**
 * Provides support for the dialog function within the Dojo toolkit.  It allows for dialogs that
 * can be populated with the current body or can build the body from a template rendered by a controller action.
 */
class DialogTagLib {

  static namespace = 'dojo'

  /**
   * Brings in all the resources required by the dialog tag.
   */
  def dialogResources = {attrs ->
    out << dojo.require(modules: ['dojoui/widget/Dialog','dojoui/layout/ContentPane'])
  }

  /**
   * Creates a dialog with a body or a body created by a template rendered within a action.  All the normal
   * dojo dialog tags are supported in addition to the following:
   * @param controller - The controller to use to render the body of the dialog
   * @param action - The action within the controller that will render the body of the dialog
   * @param params - Any parameters that must be passed to the action to complete the rendering of the body of the dialog.
   * @params id - id of a domain object which will used to load remote content.
   * @param code - This can be passed in instead of the title attibute and will use the localization system to locale the code
   *                into a localized title for the dialog.
   * @param closable - (false) If true, the x control will not display for the user to close the window.
   * @param visible  - (false) If true, the dialog will display automatically.
   * @param modeless - (false) If
   * @param name - if an id is not passed one will be generated for you.
   * @param onOpen - javascript that is executed once the dialog is displayed.
   */
  def dialog = {attrs, body ->
    attrs.title = (attrs?.code?.length()) ? message(code: attrs.remove('code')) : attrs?.title ?: ""
    if (attrs?.controller || attrs?.action) {
      attrs.href = createLink(attrs)
      attrs.remove('controller')
      attrs.remove('action')
      attrs.remove('params')
      attrs.remove('id')
    }
    attrs.name = attrs?.name ?: "dojo_ui_dialog${Util.randomId()}"
    attrs.id = attrs?.id ?: attrs.remove("name")
    def onOpen = attrs.remove("onOpen") ?: ""
    attrs.preventCache = "true"

    // Defines whether the closable x in the upper right shows.
    def closableScript = ""
    if (attrs.remove("closable") == "false") {
      closableScript = """
	  	require(["dojo/dom-style","dojo/keys], function(domStyle,keys){
	        domStyle.set(this.closeButtonNode,"display","none");
	        this._onKey = function(evt){
	            if(evt.charOrCode == keys.ESCAPE) return;
	        }
	  	}
      """
    }

    // Helps hide info before it's rendered.
    attrs.style = (attrs?.style) ? "${attrs?.style}; display:none" : "display:none"

    // Defines whether this dialog should show automatically
    def visibleScript = ""
    if (attrs.remove("visible") == 'true') {
      visibleScript = "this.show();"
    }

    // Defines if the user can interact with content while the window is open.
    def modelessTxt = ""
    if (attrs.remove("modeless") == "true") {
      modelessTxt = 'display:none'
    }

    out << """
      <style>#${attrs?.id}_underlay{background:gray; ${modelessTxt};}</style>
      <div data-dojo-type="dojoui.widget.Dialog" ${Util.htmlProperties(attrs)}>
        <script type="dojo/connect" data-dojo-event="show">${onOpen}</script>
        <script type="dojo/connect" data-dojo-event="startup">
          ${visibleScript}
          ${closableScript}
        </script>
        ${body()}
      </div>
   """
  }

  /**
   * Generates the javascript needed to open a dialog box.
   */
  def openDialogScript = {attrs ->
    def onclick = attrs?.onclick ?: ''
    out << "require(['dijit/registry'],function(registry){registry.byId('${attrs?.dialogId}').show(); ${onclick}; return false;});"
  }

  /**
   * Generates the javascript needed to close a dialog window.
   */
  def closeDialogScript = {attrs->
    def onclick = attrs?.onclick ?: ''
    out << "require(['dijit/registry'],function(registry){registry.byId('${attrs?.dialogId}').hide(); ${onclick}; return false;});"
  }

  /**
   * Generates a link to open a dialog.  Supports all the normal dojo and html attributes in addition to.
   * @param dialogId - The id of the dialog to close via this link
   * @param name - The if of the link that is generated and if one is not passed in one will be generated.
   */
  def openDialog = {attrs, body ->
    attrs.onclick = openDialogScript(attrs)
    attrs.name = attrs?.name ?: "dojo_ui_dialog_open${Util.randomId()}"
    attrs.id = attrs?.id ?: attrs.name
    attrs.remove('dialogId')
    out << """ <a href="#" ${Util.htmlProperties(attrs)}>${body()}</a> """
  }

  /**
   * Generates a link to open a dialog.  Supports all the normal dojo and html attributes in addition to.
   * @param dialogId - The id of the dialog to close via this link
   * @param name - The if of the link that is generated and if one is not passed in one will be generated.
   */
  def closeDialog = {attrs, body ->
    attrs.onclick = closeDialogScript(attrs)
    attrs.name = attrs?.name ?: "dojo_ui_dialog_open${Util.randomId()}"
    attrs.id = attrs?.id ?: attrs.name
    attrs.remove('dialogId')
    out << """ <a href="#" ${Util.htmlProperties(attrs)}>${body()}</a> """
  }

  /**
   * DEPRECATED!! (October 30, 2011) - Generates a link to close the dialog indicated by the dialogId.
   * takes all the normal dojo attributes and in addition support for localization.
   *
   * @param code - rendered to a localized string and used as the label for the link.
   * @param dialogId - The id of the dialog to close via this link
   * @param id - The if of the link that is generated and if one is not passed in one will be generated.
   * @deprecated - This is deprecated. Use <dojo:closeDialog> instead.
   */
  def closeDialogButton = {attrs, body ->
    // Remove the onclick so we can place it at the correct spot
    def onclick = attrs?.onclick ?: ''
    def label = (attrs?.code?.length()) ? message(code: attrs.remove('code')) : attrs.remove('label')
    def dialogId = attrs.remove('dialogId')// id of the dialog to close
    def id = attrs.remove("id") ?: "dojo_ui_dialog_close${Util.randomId()}"
    out << """ <a target="_self" id="${id}" ${Util.htmlProperties(attrs)} onclick="require(['dijit/registry'],function(registry){registry.byId('${dialogId}').hide(); ${onclick}});">${label}</a> """
  }
}

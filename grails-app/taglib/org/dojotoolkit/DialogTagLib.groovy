package org.dojotoolkit

import org.dojotoolkit.TagLibUtil as Util
/**
 * This class provides support for the dialog function within the Dojo toolkit.  It allows for dialogs that
 * can be populated with the current body or can build the body from a template rendered by a controller action.
 */
class DialogTagLib {
  static namespace = 'dojo'



  /**
   * This will bring in all the resources required by the dialog tag.
   * 
   * @deprecated Dojo now automatically imports required classes for parsed widgets from data-dojo-type.  
   */
  @Deprecated
  def dialogResources = {attrs, body ->
    out << dojo.require(modules: ['dojoui/widget/Dialog','dojoui/layout/ContentPane'])
  }



  /**
   * This will create a dialog with a body or a body created by a template rendered within a action.  All the normal
   * dojo dialog tags are supported in addition to the following.
   * 
   * Advanced note: Any attributes that are specified on this tag that match an
   * HTML5 attribute will be used directly on the tag. Any other attributes will be passed
   * as settings to the Dojo Widget.
   * 
   * @attr controller - The controller to use to render the body of the dialog
   * @attr action - The action within the controller that will render the body of the dialog
   * @attr params - Any parameters that must be passed to the action to complete the rendering of the body of the dialog.
   * @attr id - id of a domain object which will used to load remote content.
   * @attr code - This can be passed in instead of the title attibute and will use the localization system to locale the code
   *                into a localized title for the dialog.
   * @attr closable - (false) If true, the x control will not display for the user to close the window.
   * @attr visible  - (false) If true, the dialog will display automatically.
   * @attr modeless - (false) If
   * @attr name - if an id is not passed one will be generated for you.
   * @attr onOpen - javascript that is executed once the dialog is displayed.
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
    attrs.preventCache = true

    // Defines whether the closable x in the upper right shows.
    def closableScript = ""
    if (attrs.closable && !Util.toBoolean(attrs.remove("closable"))) {
      closableScript = """
	  	var self = this;
	  	require(["dojo/dom-style","dojo/keys"], function(domStyle,keys){
	        domStyle.set(self.closeButtonNode,"display","none");
	        self._onKey = function(evt){
	            if(evt.charOrCode == keys.ESCAPE) return;
	        }
	  	});
      """
    }

    // Helps hide info before it's rendered.
    attrs.style = (attrs?.style) ? "${attrs?.style}; display:none" : "display:none"

    // Defines whether this dialog should show automatically
    def visibleScript = ""
    if (Util.toBoolean(attrs.remove("visible"))) {
      visibleScript = "this.show();"
    }

    // Defines if the user can interact with content while the window is open.
    def modelessTxt = ""
    if (Util.toBoolean(attrs.remove("modeless"))) {
      modelessTxt = 'display:none'
    }

    out << """
      <style>#${attrs?.id}_underlay{background:gray; ${modelessTxt};}</style>
      <div ${Util.htmlProperties(attrs)} data-dojo-type="dojoui/widget/Dialog" data-dojo-props="${Util.dataDojoProps(attrs).encodeAsHTML()}">
	  """
	if(onOpen){
		out << """<script type="dojo/aspect" data-dojo-advice="after" data-dojo-method="show">${onOpen}</script>"""
	}
	if(visibleScript || closableScript){
		out << """
        <script type="dojo/aspect" data-dojo-advice="after" data-dojo-method="startup">
          ${visibleScript}
          ${closableScript}
        </script>
		"""
	}
      out << """
        ${body()}
		</div>
		"""
  }



  /**
   * Will generate the javascript needed to open a dialog box.
   */
  def openDialogScript = {attrs, body ->
    def onclick = attrs?.onclick ?: ''
    out << "require(['dijit/registry'],function(registry){registry.byId('${attrs?.dialogId}').show(); ${onclick}; return false;});"
  }



  /**
   * Will generate the javascript needed to close a dialog window.
   */
  def closeDialogScript = {attrs,body->
    def onclick = attrs?.onclick ?: ''
    out << "require(['dijit/registry'],function(registry){registry.byId('${attrs?.dialogId}').hide(); ${onclick}; return false;});"
  }



  /**
   * This will generate a link to open a dialog.  It will support all the normal dojo and html attributes in addition to.
   * @attr dialogId - The id of the dialog to open via this link
   * @attr name - The if of the link that is generated and if one is not passed in one will be generated.
   */
  def openDialog = {attrs, body ->
    attrs.onclick = openDialogScript(attrs)
    attrs.name = attrs?.name ?: "dojo_ui_dialog_open${Util.randomId()}"
    attrs.id = attrs?.id ?: attrs.name
    attrs.remove('dialogId')
    out << """ <a href="#" ${Util.htmlProperties(attrs)}>${body()}</a> """
  }



  /**
   * This will generate a link to open a dialog.  It will support all the normal dojo and html attributes in addition to.
   * @attr dialogId - The id of the dialog to close via this link
   * @attr name - The if of the link that is generated and if one is not passed in one will be generated.
   */
  def closeDialog = {attrs, body ->
    attrs.onclick = closeDialogScript(attrs)
    attrs.name = attrs?.name ?: "dojo_ui_dialog_open${Util.randomId()}"
    attrs.id = attrs?.id ?: attrs.name
    attrs.remove('dialogId')
    out << """ <a href="#" ${Util.htmlProperties(attrs)}>${body()}</a> """
  }




  /**
   *
   *
   * DEPRECATED!! (October 30, 2011) - This will generate a link to close the dialog indicated by the dialogId.
   * it will take all the normal dojo attributes and in addition support for localization.
   *
   *
   *
   * @attr code - This will be render to a localized string and used as the label for the link.
   * @attr dialogId - The id of the dialog to close via this link
   * @attr id - The if of the link that is generated and if one is not passed in one will be generated.
   * @deprecated - This is deprecated. Use <dojo:closeDialog> instead.
   */
  @Deprecated
  def closeDialogButton = {attrs, body ->
    // Remove the onclick so we can place it at the correct spot
    def onclick = attrs?.onclick ?: ''
    def label = (attrs?.code?.length()) ? message(code: attrs.remove('code')) : attrs.remove('label')
    def dialogId = attrs.remove('dialogId')// id of the dialog to close
    def id = attrs.remove("id") ?: "dojo_ui_dialog_close${Util.randomId()}"
    out << """ <a target="_self" id="${id}" ${Util.htmlProperties(attrs)} onclick="require(['dijit/registry'],function(registry){registry.byId('${dialogId}').hide(); ${onclick}});">${label}</a> """
  }
}
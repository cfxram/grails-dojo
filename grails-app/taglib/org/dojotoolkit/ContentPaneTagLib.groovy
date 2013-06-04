package org.dojotoolkit

import org.dojotoolkit.TagLibUtil as Util
/**
 * This will create content panes for display either in the page or within other items like a tab container.
 */
class ContentPaneTagLib {
  static namespace = 'dojo'

  /**
   * This will bring in all the resources required by the tab and its related components.
   * 
   * @deprecated Dojo now automatically imports required classes for parsed widgets from data-dojo-type.  
   */
  @Deprecated
  def contentPaneResources = {attrs, body ->
      out << dojo.require(modules: ['dijit/layout/ContentPane'])
  }



  /**
   * This will create a content pane item.
   * 
   * Advanced note: Any attributes that are specified on this tag that match an
   * HTML5 attribute will be used directly on the tag. Any other attributes will be passed
   * as settings to the Dojo Widget.
   * 
   * @attr code This can be passed in instead of the title attribute and will use the localization system to locale the code
   * 	into a localized title for the dialog.
   * @attr id If an id is not passed one will be generated for you.
   * @attr controller The controller to use to render the body of the dialog
   * @attr action The action within the controller that will render the body of the dialog
   * @attr params Any parameters that must be passed to the action to complete the rendering of the body of the dialog.
   * @attr refreshOnShow This will be set to true if not passed in and insures that panels linked to controllers and actions
   * 	are kept current.
   */
  def contentPane = {attrs, body ->
    attrs.refreshOnShow = Util.toBoolean(attrs.refreshOnShow ?: true)
    if(attrs?.code?.length()){
      attrs.title = message(code: attrs.remove('code'))
    }
    if(attrs?.controller || attrs?.action){
      attrs.href = createLink(attrs)
      attrs.remove('controller')
      attrs.remove('action')
      attrs.remove('params')
      attrs.remove('id')
    }
    attrs.name = attrs?.name ?: "dojo_ui_contentPane${Util.randomId()}"
    attrs.id = attrs?.id ?: attrs.remove("name")

    out << """ <div ${Util.htmlProperties(attrs)} data-dojo-type="dijit/layout/ContentPane" data-dojo-props="${Util.dataDojoProps(attrs).encodeAsHTML()}">${body()}</div> """
  }
}

package org.dojotoolkit

import org.dojotoolkit.TagLibUtil as Util

class TooltipTagLib {
  static namespace = 'dojo'



  /**
   * This will bring in all the resources required by the tab and its related components.
   * 
   * @deprecated Dojo now automatically imports required classes for parsed widgets from data-dojo-type.  
   */
  @Deprecated
  def tooltipResources = {attrs, body ->
    out << dojo.require(modules: ['dijit/Tooltip'])
  }


  /**
   * This will bring in all the resources required by the tab and its related components.
   */
  def helpResources = {attrs, body ->
    out << tooltipResources()
  }



  /**
   * Creates a help icon with content that will display when the user hovers over
   * the icon.
   */
  def help = {attrs, body ->
    def style = attrs.style ?: ''
    def id = attrs.id ?: "dojo_ui_help${Util.randomId()}"
    style = """style="display:inline-block; ${style};" """

    out << """
      <div class="dojo-ui-panel-help" id="${id}" ${style}></div>
      <div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId: '${id}'">${body()}</div>
    """
  }



  /**
   * Attaches a tooltip to any element with an ID
   * 
   * @attr id REQUIRED ID of the DOM node to connect the tooltip
   */
  def tooltip = {attrs, body ->
    def style = attrs.style ?: ''
    def id = attrs.id ?: ''

    if (!id.length()) {
      throwTagError("Id is required for the tooltip tag.")
    }
    if (style.length()) {
      style = """style="${style}" """
    }
    out << """
      <div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId: '${id}'">${body()}</div>
    """
  }

}

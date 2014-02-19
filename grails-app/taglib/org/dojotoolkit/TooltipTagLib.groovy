package org.dojotoolkit

import org.dojotoolkit.TagLibUtil as Util

class TooltipTagLib {

  static namespace = 'dojo'

  /**
   * Brings in all the resources required by the tab and its related components.
   */
  def tooltipResources = {attrs ->
    out << dojo.require(modules: ['dijit/Tooltip'])
  }

  /**
   * Brings in all the resources required by the tab and its related components.
   */
  def helpResources = {attrs ->
    out << tooltipResources()
  }

  /**
   * Creates a help icon with content that displays when the user hovers over the icon.
   */
  def help = {attrs, body ->
    def style = attrs.style ?: ''
    def id = attrs.id ?: "dojo_ui_help${Util.randomId()}"
    style = """style="display:inline-block; ${style};" """

    out << """
      <div class="dojo-ui-panel-help" id="${id}" ${style}></div>
      <div data-dojo-type="dijit.Tooltip" connectId="${id}">${body()}</div>
    """
  }

  /**
   * Attaches a tooltip to any element with an ID
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
      <div data-dojo-type="dijit.Tooltip" connectId="${id}">${body()}</div>
    """
  }

}

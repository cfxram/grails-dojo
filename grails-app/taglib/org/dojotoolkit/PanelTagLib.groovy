package org.dojotoolkit

import org.dojotoolkit.TagLibUtil as Util

class PanelTagLib {

  static namespace = 'dojo'

  /**
   * Brings in all the resources required by the dialog tag.
   */
  def panelResources = {attrs ->
    out << dojo.require(modules: ['dijit/Tooltip'])
  }

  /**
   * This defines a titled panel that can be styled like a normal div. It is
   * more light-weight than a dojo.dijit.TitlePane. If you wish to have a closed
   * panel then a dojo.dijit.TitlePane should be used.
   */
  def panel = {attrs, body ->
    def style = attrs.style ?: ''
    def id = attrs.id ?: "dojo_ui_panel${Util.randomId()}"
    def title = attrs.title ?: ''
    def code = attrs.code ?: ''

    if (title.length() || code.length()) {  // simple version of the panel.
      // blank these attributes out so as not to pass these on to the panelBody
      attrs.style = null
      out << """
        <table class="dojo-ui-panel" style="${style}" id="${id}">
            ${panelHeader(attrs)}
            ${panelBody(attrs, body)}
        </table>
      """
    }
    else { // advanced version using <ui:panelHeader>, <ui:panelBody> and <ui:panelFooter>
      out << """
        <table class="dojo-ui-panel" style="${style}" id="${id}">
            ${body()}
        </table>
      """
    }
  }

  /**
   * Child tag of panel Creates the header region.
   */
  def panelHeader = {attrs, body ->
    def help = attrs.help ?: ''
    def title = attrs.title ?: ''
    def code = attrs.code ?: ''
    def helpCode = attrs.helpCode ?: ''
    def id = attrs.id ?: "dojo_ui_panel${Util.randomId()}"
    def deleteLink = attrs.deleteLink ?: ''
    def deleteOnClick = attrs.deleteOnClick ?: ''
    def helpImage = ""
    def helpText = ""
    def deleteHtml = ""

    id = id + "_panelHeader"

    if (code.length()) {
      title = message(code: code)
    }
    if (helpCode.length()) {
      help = message(code: helpCode)
    }
    if (deleteLink.length() || deleteOnClick.length()) {
      deleteHtml = """
        <a class="dojo-ui-panel-delete-icon" href="${deleteLink}" onclick="${deleteOnClick}"></a>
      """
    }

    if (help.length()) {
      helpImage = """
        <div class="dojo-ui-panel-help" id="${id}"></div>
      """
      helpText = """
        <div data-dojo-type="dijit.Tooltip" connectId="${id}">${help}</div>
      """
    }

    out << """
      <tr>
        <td class="dojo-ui-panel-title dijitTitlePaneTitle">
            ${deleteHtml}
            ${helpText}
            <span class="dojo-ui-title-text">${title}</span> ${helpImage}
            ${body()}
        </td>
      </tr>
    """
  }

  /**
   * Child tag of panel. Creates the body region.
   */
  def panelBody = {attrs, body ->
    def style = attrs.style ?: ''
    def id = attrs.id ?: "dojo_ui_panel${Util.randomId()}"
    attrs?.id = id + "_contentPane"

    if (attrs?.containLinks || attrs?.action) {
      out << """
        <tr>
          <td class="dojo-ui-panel-body" valign="top" style="${style}">
            ${pane(attrs, body)}
          </td>
        </tr>
      """
    }
    else {
      out << """
        <tr>
          <td class="dojo-ui-panel-body" valign="top" style="${style}">${body()}</td>
        </tr>
      """
    }
  }

  /**
   * A Child tag of panel. Creates a footer region.
   */
  def panelFooter = {attrs, body ->
    out << """
      <tr>
        <td class="dojo-ui-panel-footer dijitTitlePaneTitle">${body()}</td>
      </tr>
    """
  }
}

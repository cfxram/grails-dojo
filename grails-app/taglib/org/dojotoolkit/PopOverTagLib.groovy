package org.dojotoolkit
import org.dojotoolkit.TagLibUtil as Util

class PopOverTagLib {
  static namespace = 'dojo'

  /**
   * Outputs the required dojo modules needed for the popOver. This is not required.
   */
  def popOverResources = {attrs, body ->
    out << dojo.require(modules:['dojoui/widget/TooltipDialog','dojoui/widget/DropDownButton','dojoui/widget/DropDownButtonLink','dojoui/layout/ContentPane'])
  }

  /**
   * Creates will create a link or button that when clicked will show a pop over. 
   * The content of the popover can be defined inline or loaded from a controller action.
   * If you specify containLinks="true", then a <dojo:frame> will be loaded inside of the popOver.
   * This will give you the ability to have links that stay inside the popOver.
   */
  def popOver = {attrs, body ->
    def name = attrs.remove("name") ?: "dojo_ui_popover${Util.randomId()}"
    def href = attrs.remove("href") ?: ''
    def onOpen = attrs.remove("onOpen") ?: ''
    def activate = attrs.remove("activate") ?: 'click'   // can be click or hover
    def btnClass = attrs.remove("btnClass") ?: ''
    def type = attrs.remove("type") ?: 'button'   // can be button or link    
    def dojoWidget = (type == 'link') ? "dojoui.widget.DropDownButtonLink" : "dojoui.widget.DropDownButton"
    def label = (attrs?.code?.length()) ? message(code:attrs.remove('code')) : attrs.remove('label')    
    def containLinks = attrs.remove("containLinks") ?: 'false'
    
    if(attrs?.controller || attrs?.action){
      href = createLink(attrs)
      attrs.remove('controller')
      attrs.remove('action')
      attrs.remove('params')
      attrs.remove('id')
    }

    out << """
      <div data-dojo-type="${dojoWidget}" id="${name}" activate="${activate}" btnClass="${btnClass}" ${Util.htmlProperties(attrs)}>
          <script type="dojo/method" event="onClick" args="evt">${onOpen}</script>
          <span>${(label && (label instanceof Closure)) ? label.call() : label}</span>
          <div class="dojoui-popover-tooltipDialog" data-dojo-type="dojoui.widget.TooltipDialog" style="display:none" autoFocus="false" id="${name}_TooltipDialog" containLinks="${containLinks}" preventCache="true" href="${href}">
            <script type="dojo/connect" data-dojo-event="onDownloadEnd">require(['dijit/registry'], function(regisry){registry.byId('${name}').openDropDown();});</script>
            ${body()}
          </div>
      </div>
    """
  }



  /**
   * Will generate javascript that will close the popover.
   */
  def closePopOverScript = {attrs, body ->
    def onclick = attrs?.onclick ?: ''
    out << "dijit.byId('${attrs?.popOver}').closeDropDown(); ${onclick}"
  }



  /**
   * Creates an inline section that is put into a popover using innerHTML.
   * This is used for rare instances when the content doesn't render correctly inside of a popover.
   */
  def popOverContent = {attrs, body ->
    out << """
      <div style="display:none">
        <div ${Util.htmlProperties(attrs)}>${body()}</div>      
      </div>
    """
  }



  /**
   * Creates an anchor tag that will close the popover when clicked.
   */
  def closePopOver = {attrs, body ->
    def onclick = closePopOverScript(attrs)
    attrs.remove('popOver');    
    out << """
      <a href="#" onclick="${onclick}; return false;" ${Util.htmlProperties(attrs)}>${body()}</a>
    """
  }
  

}
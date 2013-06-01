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
   * 
   * Advanced note: Any attributes that are specified on this tag that match an
   * HTML5 attribute will be used directly on the tag. Any other attributes will be passed
   * as settings to the Dojo Button Widget.
   * 
   * @attr name The ID to use for the widget
   * @attr href The URL to load inside the popover
   * @attr onOpen JavaScript code that's executed as the popover opens
   * @attr type Either "button" or "link"
   * @attr code The i18n messages code for the button label
   * @attr label Can either be a string or a Groovy {@link Closure}
   * @attr activate Either "hover" or "click" for the mode of popover activation
   * @attr controller Passed to Grails to resolve URL for popover contents
   * @attr action Passed to Grails to resolve URL for popover contents
   * @attr params Passed to Grails to resolve URL for popover contents
   * @attr id Passed to Grails to resolve URL for popover contents
   */
  def popOver = {attrs, body ->
    def name = attrs.remove("name") ?: "dojo_ui_popover${Util.randomId()}"
    def href = attrs.remove("href") ?: ''
    def onOpen = attrs.remove("onOpen") ?: ''
    def btnClass = attrs.remove("btnClass") ?: ''
	if(btnClass){
		attrs['class'] = btnClass
	}
    def type = attrs.remove("type") ?: 'button'   // can be button or link    
    def dojoWidget = (type == 'link') ? "dojoui/widget/DropDownButtonLink" : "dojoui/widget/DropDownButton"
    def label = (attrs?.code?.length()) ? message(code:attrs.remove('code')) : attrs.remove('label')    
    def containLinks = attrs.remove("containLinks") ?: 'false'
	if("hover" != attrs['activate']){
		attrs['activate'] = "click"
	}
    
    if(attrs?.controller || attrs?.action){
      href = createLink(attrs)
      attrs.remove('controller')
      attrs.remove('action')
      attrs.remove('params')
      attrs.remove('id')
    }

    out << """
      <div id="${name}" ${Util.htmlProperties(attrs)} data-dojo-type="${dojoWidget}" data-dojo-props="${Util.dataDojoProps(attrs).encodeAsHTML()}">
          <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">${onOpen}</script>
          <span>${(label && (label instanceof Closure)) ? label.call() : label}</span>
          <div id="${name}_TooltipDialog" class="dojoui-popover-tooltipDialog" style="display:none" data-dojo-type="dojoui/widget/TooltipDialog" data-dojo-props="autoFocus: false, containLinks: ${containLinks}, preventCache: true, href: '${href}'">
		  	<script type="dojo/aspect" data-dojo-advice="after" data-dojo-event="onDownloadEnd">require(['dijit/registry'], function(regisry){registry.byId('${name}').openDropDown();});</script>
            ${body()}
          </div>
      </div>
    """
  }



  /**
   * Will generate JavaScript that will close the popover.
   * 
   * @attr popOver REQUIRED the ID of the widget to close
   * @attr onclick JavaScript code to execute  
   */
  def closePopOverScript = {attrs, body ->
    def onclick = attrs?.onclick ?: ''
    out << "require(['dijit/registry'], function(registry){registry.byId('${attrs?.popOver}').closeDropDown(); ${onclick}});"
  }



  /**
   * Creates an inline section that is put into a popover using innerHTML.
   * This is used for rare instances when the content doesn't render correctly inside of a popover.
   * 
   * Any attributes will be placed on the region tag directly.
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
   * 
   * Any extra attributes will be placed on the anchor tag directly.
   * 
   * @attr popOver REQUIRED The ID of the widget to close
   */
  def closePopOver = {attrs, body ->
    def onclick = closePopOverScript(attrs)
    attrs.remove('popOver');    
    out << """
      <a href="#" onclick="${onclick}; return false;" ${Util.htmlProperties(attrs)}>${body()}</a>
    """
  }
  

}
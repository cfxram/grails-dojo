package org.dojotoolkit
import org.dojotoolkit.TagLibUtil as Util

class PopOverTagLib {
  static namespace = 'dojo'

  /**
   * Outputs the required dojo modules needed for the popOver. This is not required.
   */
  def popOverResources = {attrs, body ->
    out << dojo.require(modules:['dojoui.widget.DropDownButton','dijit.TooltipDialog', 'dojoui.layout.ContentPane'])
    //out << dojo.css(file:"dojoui/widget/resources/dropDownButton.css")
  }

  /**
   * Creates will create a link or button that when clicked will show a pop over. 
   * The content of the popover can be defined inline or loaded from a controller action.
   * If you specify containLinks="true", then a <dojo:pane> will be loaded inside of the popOver.
   * This will give you the ability to have links that stay inside the popOver.
   */
  def popOver = {attrs, body ->
    def id = attrs.remove("id") ?: "dojo_ui_popover${Util.randomId()}"
    def href = attrs.remove("href") ?: ''
    def onOpen = attrs.remove("onOpen") ?: ''
    def activate = attrs.remove("activate") ?: 'click'   // can be click or hover
    def btnClass = attrs.remove("btnClass") ?: ''
    def type = attrs.remove("type") ?: 'button'   // can be button or link    
    def dojoWidget = (type == 'link') ? "dojoui.widget.DropDownButtonLink" : "dojoui.widget.DropDownButton"
    def label = (attrs?.code?.length()) ? message(code:attrs.remove('code')) : attrs.remove('label')    
    def containLinks = attrs.remove("containLinks") ?: 'false'
    //def contentDomId = attrs.remove('contentDomId') ?: '' // specify content defined in a <dojo:popOverContent> tag.
    def bodyContent = body()
    
    if(attrs?.controller || attrs?.action){
      href = createLink(attrs)
      attrs.remove('controller')
      attrs.remove('action')
      attrs.remove('params')
    }
    
    if(containLinks == 'true'){
      out << """
        <div dojoType="${dojoWidget}" id="${id}" activate="${activate}" btnClass="${btnClass}" ${Util.htmlProperties(attrs)}>
            <script type="dojo/method" event="onClick" args="evt">${onOpen}</script>
            <span>${label}</span>
            <div class="dojo-grails" dojoType="dijit.TooltipDialog" style="display:none" autoFocus="false" id="${id}_TooltipDialog">
              <div dojoType="dojoui.layout.ContentPane" id="${id}_content" containLinks="${containLinks}" preventCache="true" href="${href}">
                <script type="dojo/connect" event="onDownloadEnd">
                  dijit.byId('${id}').openDropDown();                
                </script>
                ${body()}
              </div>
            </div>
        </div>
      """    
    }
    else{
      out << """
        <div dojoType="${dojoWidget}" id="${id}" activate="${activate}" btnClass="${btnClass}" ${Util.htmlProperties(attrs)}>
            <script type="dojo/method" event="onClick" args="evt">${onOpen}</script>
            <span>${label}</span>
            <div class="dojo-grails" dojoType="dijit.TooltipDialog" style="display:none" autoFocus="false" id="${id}_TooltipDialog">
              ${body()}
            </div>
        </div>
      """
    }
  }

  def closePopOverScript = {attrs, body ->
    def onclick = attrs?.onclick ?: ''
    out << "dijit.byId('${attrs?.popOver}').closeDropDown(); ${onclick}"
  }


  def popOverContent = {attrs, body ->
    out << """
      <div style="display:none">
        <div ${Util.htmlProperties(attrs)}>${body()}</div>      
      </div>
    """
  }
  
  
  def closePopOver = {attrs, body ->
    def onclick = closePopOverScript(attrs)
    attrs.remove('popOver');    
    out << """
      <a href="#" onclick="${onclick}" ${Util.htmlProperties(attrs)}>${body()}</a>
    """
  }
  

}
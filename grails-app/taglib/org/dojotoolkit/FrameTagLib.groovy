package org.dojotoolkit
import org.dojotoolkit.TagLibUtil as Util

class FrameTagLib {
  static namespace = 'dojo'

  /**
   * Outputs the required dojo modules needed for the frame. This is not required.
   */
  def frameResources = {attrs, body ->
    out << dojo.require(modules:['dojoui.layout.ContentPane'])
  }
  
  
  
  /**
   * This creates an extended Dojo ContentPane (dijit.layout.ContentPane). When the containLinks property
   * is set to true(default), then it behaves very much like an <iframe> tag (without the memory overhead).
   *   
   * When containLinks is turned on, then all <a href> and <g:link> tags load inside the parent content pane.
   * Also, form submissions stay inside of the frame. The frame can also handle multi-part forms so ajax-style file 
   * uploads can be done.
   * 
   * This special frame can be used to define a tab instead of the <dojo:ContentPane> (dijit.layout.ContentPane).
   */ 
  def frame = {attrs, body ->
    def id = attrs.remove("id") ?: "dojo_ui_frame${Util.randomId()}"
    def href = attrs.remove("href") ?: ""
    def containLinks = attrs.remove("containLinks") ?: 'true'
    attrs.preventCache = "true"
    if(attrs?.code?.length()){
      attrs.title = message(code: attrs.remove('code')) 
    }        
    if(attrs?.controller || attrs?.action){
      href = createLink(attrs)
      attrs.remove('controller')
      attrs.remove('action')
      attrs.remove('params')
    }       
    
    out << """ <div dojoType="dojoui.layout.ContentPane" id="${id}" href="${href}" containLinks="${containLinks}" ${Util.htmlProperties(attrs)}>${body()}</div> """
  }
 
 
 
  /**
   * Creates a link that when clicked will load the content in a <dojo:frame>. 
   * 
   * Implementation Note:
   * The onclick handler will first clear the ioArgs.content so that previous
   * form submissions are cleared from the XHR object.
   * 
   * @params frame - The id of the <dojo:frame> (required)
   */ 
  def frameLink = {attrs, body ->
    def frame = attrs.remove("frame");
    def href = attrs.remove("href") ?: ""    
    if(frame){      
      if(attrs?.controller || attrs?.action){
        href = createLink(attrs)
        attrs.remove('controller')
        attrs.remove('action')
        attrs.remove('params')
      }
      attrs.onclick="dijit.byId('${frame}').ioArgs.content=null; dijit.byId('${frame}').attr('href','${href}')"      
    }
    out << """ <a href="#" ${Util.htmlProperties(attrs)}>${body()}</a> """
  }
 
  
}  

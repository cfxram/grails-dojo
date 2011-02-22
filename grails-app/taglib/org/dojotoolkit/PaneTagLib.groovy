package org.dojotoolkit
import org.dojotoolkit.TagLibUtil as Util

class PaneTagLib {
  static namespace = 'dojo'

  /**
   * Outputs the required dojo modules needed for the pane. This is not required.
   */
  def paneResources = {attrs, body ->
    out << dojo.require(modules:['dojoui.layout.ContentPane'])
  }
  
  
  
  /**
   * This creates an extended Dojo ContentPane (dijit.layout.ContentPane). When the containLinks property
   * is set to true(default), then it behaves very much like an <iframe> tag (without the memory overhead).
   *   
   * When containLinks is turned on, then all <a href> and <g:link> tags load inside the parent content pane.
   * Also, form submissions stay inside of the pane. The pane can also handle multi-part forms so ajax-style file 
   * uploads can be done.
   * 
   * This special pane can be used to define a tab instead of the <dojo:ContentPane> (dijit.layout.ContentPane).
   */ 
  def pane = {attrs, body ->
    def id = attrs.remove("id") ?: "dojo_ui_pane${Util.randomId()}"
    def href = attrs.remove("href") ?: ""
    def containLinks = attrs.remove("containLinks") ?: 'true'
    attrs.title = (attrs?.code?.length()) ? message(code: attrs.remove('code')) : attrs?.title
    attrs.preventCache = "true"
    
    if(attrs?.controller || attrs?.action){
      href = createLink(attrs)
      attrs.remove('controller')
      attrs.remove('action')
      attrs.remove('params')
    }       
    out << """
      <div dojoType = "dojoui.layout.ContentPane" id="${id}" ${Util.htmlProperties(attrs)} containLinks="${containLinks}">
        ${body()}
      </div>
    """
  }
 
 
  /**
   * Creates a link that when clicked will load the content in a <dojo:pane>. 
   * @params pane - The id of the <dojo:pane> (required)
   */ 
  def paneLink = {attrs, body ->
    def pane = attrs.remove("pane");
    def href = attrs.remove("href") ?: ""    
    if(pane){      
      if(attrs?.controller || attrs?.action){
        href = createLink(attrs)
        attrs.remove('controller')
        attrs.remove('action')
        attrs.remove('params')
      }
      attrs.onclick="dijit.byId('${pane}').attr('href','${href}')"      
    }
    out << """
      <a href="#" ${Util.htmlProperties(attrs)}>${body()}</a>
    """
  }
 
  
}  

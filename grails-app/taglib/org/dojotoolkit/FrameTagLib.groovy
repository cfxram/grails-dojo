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
    attrs.containLinks = attrs.containLinks ?: 'true'
    attrs.preventCache = "true"
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
    attrs.name = attrs?.name ?: "dojo_ui_frame${Util.randomId()}"
    attrs.id = attrs?.id ?: attrs.remove("name")
    out << """ <div dojoType="dojoui.layout.ContentPane" ${Util.htmlProperties(attrs)}>${body()}</div> """
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
    def onclick = attrs.remove("onclick") ?: ""

    if(frame){      
      if(attrs?.controller || attrs?.action){
        href = createLink(attrs)
        attrs.remove('controller')
        attrs.remove('action')
        attrs.remove('params')
        attrs.remove('id')
      }      
      attrs.onclick="${onclick}; dijit.byId('${frame}').ioArgs.content=null; dijit.byId('${frame}').attr('href','${href}'); return false;"
    }
    
    // If using a g:link elementId.. then make it the id.
    if( attrs?.elementId?.length() ){
      attrs.name=attrs.remove("elementId")
    }
    if(attrs?.name){
      attrs.id = attrs.name
    }
    
    out << """ <a href="#" ${Util.htmlProperties(attrs)}>${body()}</a> """
  }




  /**
   * Used to run javascript after content has been loaded when it is inside of
   * a dijit.layout.ContentPane (or <dojo:frame>). The dojo parser will parse
   * this content pane and then run any javascript inside of the start up
   * event.
   *
   * Examples:
   *
   * If loading inside of a tab:
   *   <dojo:onload>
   *       console.log('This is run from inside tab 1');
   *   </dojo:onload>
   *
   * If loading from outside of a tab:
   *   <dojo:onload insideTab="false">
   *       console.log('this is from the outside');
   *   </dojo:onload>
   *
   * Known issues:
   * 1. doesn't allow dojo.require("").
   * 2. This is not a true onLoad event.
   */
  def onload = {attrs, body ->
    def insideTab = attrs.insideTab ?: 'true'
    def selectParentTabId = attrs.selectParentTabId ?: ''
    def selectTabScript = ""
    def id = attrs.remove("id") ?: ""

    if (selectParentTabId.length()) {
      selectTabScript = """
        var parentTabViewDom = this.domNode.parentNode.parentNode.parentNode;
        var parentTab = this.domNode.parentNode;
        if( (dojo.hasClass(parentTabViewDom,"dijitTabContainer")) && (dojo.attr(parentTab, "dojoType") == "dijit.layout.ContentPane") ){
            var parentTabView = dijit.byNode(parentTabViewDom);
            parentTabView.selectChild("${selectParentTabId}");
        }
      """
    }
    /*
      HACK!! (rm 1-6-2011)
      The &nbsp; is so that IE 6-8 will recognize the ajax content and run the code.
      Strangely, IE will not render the content pane if this is not there.
     */
    if (insideTab == 'true') {
      out << """
        <div dojoType="dijit.layout.ContentPane" style="display:none" id="${id}">&nbsp;
            <script type="dojo/method" event="startup">
                ${selectTabScript}
                ${body()}
                window[this.id] = this;
            </script>
        </div>
      """
    }
    else {
      out << """
        <script type="text/javascript">
            dojo.ready(function(){
                ${body()}
            });
        </script>
      """
    }
  }
}  

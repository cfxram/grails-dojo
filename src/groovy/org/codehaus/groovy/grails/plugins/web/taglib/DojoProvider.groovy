package org.codehaus.groovy.grails.plugins.web.taglib;

/**
 * Big List of things to do.
 * 1. Make sure errors shows up if failure.
 * 2. Make sure to handle on404 or on202 etc.
 * 3. Evaluate inline javascript in the xhr response (works in prototype).
 *
 *
 */

/**
 * Dojo implmentation of JavascriptProvider.
 *
 */
class DojoProvider implements JavascriptProvider {

  /**
   * 
   * @param method
   * @param url
   * @param updateDomElem
   * @param errorDomElem
   * @param async
   * @param onSuccess
   * @param onFailure
   * @param onLoading
   * @param onLoaded
   * @param onComplete
   * @param httpErrorHandlers
   * @param syncForm
   * @param preventCache
   * @return
   */

  def private getDojoXhrString(Map props){
    def method              = props?.method ?: "Get"
    def url                 = props?.url ?: ""
    def updateDomElem       = props?.updateDomElem ?: ""
    def errorDomElem        = props?.errorDomElem ?: ""
    def async               = props?.async ?: true
    def onSuccess           = props?.onSuccess+";" ?: ""
    def onFailure           = props?.onFailure+";" ?: ""
    def onLoading           = props?.onLoading+";" ?: ""
    def onLoaded            = props?.onLoaded+";" ?: ""
    def onComplete          = props?.onComplete+";" ?: ""
    def statusCodeHandlers  = props?.statusCodeHandlers ?: ""
    def syncForm            = props?.syncForm ?: false
    def preventCache        = props?.preventCache ?: false

    def form = "null"
    if (syncForm) {
      form = "dojo.byId(this.form)"
    }
    def dojoString =
    " ${onLoading} " +
    "dojo.xhr('${method}',{" +
        (!async ? "sync:${async}, ": "") +
        "preventCache:${preventCache}, " +
        "form:${form}, " +
        "url:'${url}', " +
        "load:function(response){" +
            "dojo.attr(dojo.byId('${updateDomElem}'),'innerHTML',response); " +
            "if(dojo.parser){dojo.parser.parse(dojo.byId('${updateDomElem}'))} " +
            "${onLoaded} " +
            "${onSuccess} " +
        "}, " +
        "handle:function(response,ioargs){" +
          "${statusCodeHandlers}" +
          "${onComplete} " +          
        "}, " +
        "error:function(error,ioargs){" +
          "dojo.attr(dojo.byId('${errorDomElem}'),'innerHTML',ioargs.xhr.responseText); " +
          "${onLoaded} " +
          "${onFailure} " +
        "} " +
    "});"
    return dojoString
  }




  def doRemoteFunction(taglib, attrs, out) {
    def allowedMethods = ["Get", "Post", "Put", "Delete"]
    def method = "Get"
    if (attrs.method) {
      def tmpMethod = attrs.method[0].toUpperCase() + attrs.method.substring(1).toLowerCase()
      if (allowedMethods.contains(tmpMethod)) {
        method = tmpMethod
      }
    }
    def sync          = attrs.sync && attrs.sync == "true" ?: "false"
    def onSuccess     = attrs?.onSuccess
    def onFailure     = attrs?.onFailure
    def onLoading     = attrs?.onLoading
    def onLoaded      = attrs?.onLoaded
    def onComplete    = attrs?.onComplete
    def preventCache  = attrs?.preventCache
    ['method','sync','params','options','onSuccess','onFailure','onLoading','onLoaded','onComplete','preventCache'].each{attrs.remove(it)}

    
    // Http Status Code Handlers
    def statusCodes = attrs.findAll { k,v ->
        k ==~ /on(\p{Upper}|\d){1}\w+/
    }
    def statusCodeHandlers = ""
    statusCodes.each{k,v ->
      statusCodeHandlers+="if(ioargs.xhr.status===${k.replaceAll(/on/,'')}){${v}}; "
      attrs.remove(k)
    }

    // Generate XHR Output
    out << getDojoXhrString([
      method: method,
      url: taglib.createLink(attrs),
      updateDomElem: (attrs.update instanceof Map ? attrs.update.success : attrs.update),
      errorDomElem: (attrs.update instanceof Map ? attrs.update.failure : attrs.update),
      async: sync,
      onSuccess: onSuccess,
      onFailure: onFailure,
      onLoading: onLoading,
      onLoaded: onLoaded,
      onComplete: onComplete,
      statusCodeHandlers: statusCodeHandlers,                  
      syncForm: null,
      preventCache: preventCache])
  }



  
  def prepareAjaxForm(attrs) {
    if (attrs.options) {
      attrs.options.formNode = "dojo.byId('${attrs.name}')"
    }
    else {
      attrs.options = [formNode: "dojo.byId('${attrs.name}')"]
    }
  }
}
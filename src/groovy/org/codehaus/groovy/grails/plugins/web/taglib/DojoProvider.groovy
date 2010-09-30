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
    def onSuccess           = (props?.onSuccess) ? "${props?.onSuccess};" : ""
    def onFailure           = (props?.onFailure) ? "${props?.onFailure};" : ""
    def onLoading           = (props?.onLoading) ? "${props?.onLoading};" : ""
    def onLoaded            = (props?.onLoaded) ? "${props?.onLoaded};" : ""
    def onComplete          = (props?.onComplete) ? "${props?.onComplete};" : ""
    def statusCodeHandlers  = props?.statusCodeHandlers ?: ""
    def formName            = props?.formName ?: ""
    def preventCache        = props?.preventCache ?: false

    def dojoString =
    "${onLoading}" +
    "dojo.xhr('${method}',{" +
        (!async ? "sync:${async}, ": "") +
        "preventCache:${preventCache}, " +
        (formName?.length() ? "form:${formName}, " : "") + 
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
    def url           = taglib.createLink(attrs)
    def updateDomElem = (attrs.update instanceof Map ? attrs.update.success : attrs.update)
    def errorDomElem  = (attrs.update instanceof Map ? attrs.update.failure : attrs.update)
    def sync          = attrs.sync && attrs.sync == "true" ?: "false"
    def onSuccess     = attrs?.onSuccess
    def onFailure     = attrs?.onFailure
    def onLoading     = attrs?.onLoading
    def onLoaded      = attrs?.onLoaded
    def onComplete    = attrs?.onComplete
    def formName      = attrs?.formName
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
      url: url,
      updateDomElem: updateDomElem,
      errorDomElem: errorDomElem,
      async: sync,
      onSuccess: onSuccess,
      onFailure: onFailure,
      onLoading: onLoading,
      onLoaded: onLoaded,
      onComplete: onComplete,
      statusCodeHandlers: statusCodeHandlers,                  
      formName: formName,
      preventCache: preventCache])
  }



  
  def prepareAjaxForm(attrs) {
    if(!attrs.method){
      attrs.method = "Post"
    }

    if(attrs?.forSubmitTag){
      // This is for <g:submitToRemote>
      attrs.formName = "this.form"  
    }
    else{
      // This is for <g:remoteForm>
      attrs.formName = "'${attrs.name}'"
    }
  }
}
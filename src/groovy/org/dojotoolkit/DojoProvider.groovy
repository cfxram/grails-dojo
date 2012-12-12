package org.dojotoolkit
import org.codehaus.groovy.grails.plugins.web.taglib.JavascriptProvider

/**
 * Dojo implmentation of JavascriptProvider.
 *
 */
class DojoProvider implements JavascriptProvider {


  
  /**
   * This will convert remoteXXX tag params to a dojo friendly format. Most params should be passed as a regular map but
   * there are some exceptions.
   *
   * The remoteField tag passes the params as a GString. So we deal with that differently.
   *
   * If the user wishes to pass in javascript values as part of the params attribute, the best way is to pass a string
   * that is structured like this: params="'myVar1':myJsValue, 'myVar2':myJsValue2". 
   *
   * @param params
   * @return String
   */
  private def convertParamsToDojoJson(params) {
    def paramString = ""
    if(params instanceof Map){
      def paramList = []      
      params.each{k, v ->
        if(v instanceof String){
          v = "'${v}'"
        }
        paramList.add("'${k}':${v}")
      }
      paramString = "{${paramList.join(",")} }"
    }
    // Used ONLY for <g:remoteField>
    else if(params instanceof GString){
      //<g:remoteField> passes in the param like this: 'color='+this.value. So lets convert to 'color':this.value
      paramString = params.replaceAll(/\+/,":").replaceAll(/\=/,"")
      paramString = "{${paramString}}"
    }
    else if(params instanceof String){
      paramString = "{${params}}"      
    }
    return paramString
  }




   /**
    * Will generate the Ajax javascript to be applied to the html dom node.
    *
    * @param props
    * @return
    */
  def private getDojoXhrString(Map props){
    def method              = props?.method ?: "Get"
    def url                 = props?.url ?: ""
    def parameters          = props?.parameters ?: ""
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
    def updateDomElemScript = ""
    def errorDomElemScript  = ""
    def dojoString          = ""


    /**
     * NOTE:
     * When using dojo.place, if the response from server is NOT an html node, it will think it's a DOM node id
     * and try to find it. This is defined here: http://dojotoolkit.org/reference-guide/1.6/dojo/place.html.
     *
     * So if a dev specifies "only" then just use regular innerHTML syntax. Only when a position is specified,
     * then use dojo.place.
     */

    // Update property for <g:remoteFunction> is optional so don't run js code if empty    
    if(updateDomElem?.length()){
      def successPlacementCode = (props.position == "only") ? "dojo.attr(dojo.byId('${updateDomElem}'),'innerHTML',response); " : "dojo.place(response,'${updateDomElem}','${props.position}'); "
      updateDomElemScript = 
      "if(dijit.findWidgets){dojo.forEach(dijit.findWidgets(dojo.byId('${updateDomElem}')), function(w){w.destroyRecursive()});} " +
      "${successPlacementCode}" +
      "if(dojo.parser){dojo.parser.parse(dojo.byId('${updateDomElem}'))} "          
    }

    // Error dom element is optional so don't run js code if empty
    if(errorDomElem?.length()){
      def errorPlacementCode = (props.position == "only") ? "dojo.attr(dojo.byId('${errorDomElem}'),'innerHTML',ioargs.xhr.responseText); " : "dojo.place(ioargs.xhr.responseText,'${errorDomElem}','${props.position}'); "
      errorDomElemScript =
      "if(dijit.findWidgets){dojo.forEach(dijit.findWidgets(dojo.byId('${errorDomElem}')), function(w){w.destroyRecursive()});} " +
      "${errorPlacementCode} " +
      "if(dojo.parser){dojo.parser.parse(dojo.byId('${errorDomElem}'))} "
    }

    // If form is a enctype="multipart/form-data" then this is a <g:formRemote>
    // we must use an iframe to do the transport
    if(props.enctype == "multipart/form-data" || props.uploadFile == "true"){
      dojoString =
      "${onLoading}" +
      "try{DojoGrailsSpinner.show();}catch(e){} " +
      "dojo.io.iframe.send({" +
          "url:'${url}', " +
          "method:'${method}', " +
          "form:dojo.byId(${formName}), " +
          "content:{'dojoIoIframeTransport':'true'}," +
          "multiPart:true, " +
          "handleAs:'text', " +
          "load:function(response,ioargs){" +
            "ioargs.xhr = {status:Number(dojo.io.iframe.doc(dojo.io.iframe._frame).getElementsByTagName('textarea')[0].getAttribute('status')), responseText:response};" +
            "if(ioargs.xhr.status!=200){this.error(ioargs); return true}; " +
            "${updateDomElemScript} " +
            "${onLoaded} " +
            "${onSuccess} " +
          "}, " +
          "handle:function(response,ioargs){" +
            "${statusCodeHandlers}" +
            "try{DojoGrailsSpinner.hide();}catch(e){}" +
            "${onComplete} " +
          "}, " +
          "error:function(ioargs){" +
            "try{DojoGrailsSpinner.hide();}catch(e){}" +
            "${errorDomElemScript}" +
            "${onLoaded} " +
            "${onFailure} " +
          "} " +
      "});"
    }
    else{
      dojoString =
      "${onLoading}" +
      "try{DojoGrailsSpinner.show();}catch(e){} " +
      "dojo.xhr('${method}',{" +
          (!async ? "sync:${async}, ": "") +
          (parameters?.length() ? "content:${parameters}, " : "") +
          (formName?.length() ? "form:${formName}, " : "") +
          "preventCache:${preventCache}, " +
          "url:'${url}', " +
          "load:function(response){" +
              "${updateDomElemScript} " +
              "${onLoaded} " +
              "${onSuccess} " +
          "}, " +
          "handle:function(response,ioargs){" +
            "${statusCodeHandlers}" +
            "try{DojoGrailsSpinner.hide();}catch(e){}" +
            "${onComplete} " +
          "}, " +
          "error:function(error,ioargs){" +
            "try{DojoGrailsSpinner.hide();}catch(e){}" +
            "${errorDomElemScript}" +
            "${onLoaded} " +
            "${onFailure} " +
          "} " +
      "});"
    }

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
    def parameters    = convertParamsToDojoJson(attrs?.params)
    attrs.remove('params') // to not duplicate these params on the url.

    def url           = (attrs?.url instanceof String) ? attrs?.url : taglib.createLink(attrs)    
    def updateDomElem = (attrs?.update instanceof Map ? attrs.update.success : attrs.update)
    def errorDomElem  = (attrs?.update instanceof Map ? attrs.update.failure : attrs.update)
    def sync          = attrs.sync && attrs.sync == "true" ?: "false"
    def onSuccess     = attrs?.onSuccess
    def onFailure     = attrs?.onFailure
    def onLoading     = attrs?.onLoading
    def onLoaded      = attrs?.onLoaded
    def onComplete    = attrs?.onComplete
    def preventCache  = attrs?.preventCache ?: 'true'
    def enctype       = attrs?.enctype   // Used to add file upload capabilities
    def uploadFile    = attrs.uploadFile // Used to add file upload capabilities
    def position      = attrs?.position  ?: 'only'
    def formName      = attrs?.formName
    if(formName){
      if(!formName.contains("this.form")){
        formName = "'${formName}'"
      }
    }

    ['method','sync','params','options','onSuccess','onFailure','onLoading','onLoaded','onComplete','preventCache','formName', 'preventCache', 'uploadFile','position'].each{attrs.remove(it)}

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
            parameters: parameters,
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
            preventCache: preventCache,
            enctype: enctype,
            position: position,
            uploadFile:uploadFile])
  }




  def prepareAjaxForm(attrs) {
    if(!attrs.method){
      attrs.method = "Post"
    }

    if(attrs?.forSubmitTag){
      // This is for <g:submitToRemote>
      attrs.formName = attrs?.formName ?: "this.form"
    }
    else{
      // This is for <g:formRemote>
      attrs.formName = attrs.name
    }
  }
}
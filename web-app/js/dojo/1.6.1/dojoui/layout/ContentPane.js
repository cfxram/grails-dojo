dojo.provide("dojoui.layout.ContentPane");
dojo.require("dijit.layout.ContentPane");
dojo.require("dojo.io.iframe");

dojo.declare("dojoui.layout.ContentPane", dijit.layout.ContentPane,{
  containLinks:false,
  baseIoArgs:null,
  originalHref:"",
  preventCache:true,  


  postCreate:function(){
    var pane = this;
    pane.originalHref = pane.get("originalHref") || pane.get("href");
    
    pane._selectTabIfTabNameInUrl()
    
    // If ContentPane has static content then still constrain the form submissions.
    if (!pane.get("href").length) {
      pane._constrainFormSubmissions();
    }         
    
    dojo.connect(pane.domNode, "onclick", this, this._onClick);
    
    pane.baseIoArgs = {
      content:null,
      error: function(err, args){
        var errText = args.xhr.responseText;
        pane._setContent(errText, true);
      }
    };
    pane.ioArgs = dojo.clone(pane.baseIoArgs);
  },


  /**
   * Convenience method to load new href in a window. But cleans up previous
   * data from old ajax call.
   *
   * This will also close all popups and dialogs before loading content;
   *
   * @param href
   */
  loadHref:function(urlString){
    dojo.forEach(this.getChildren(), function(widget){
      if(widget.closeDropDown){
        widget.closeDropDown();
      }
      if(widget.hide){
        widget.hide()
      }
		});
    this.ioMethod = dojo.xhrGet;
    this.ioArgs = dojo.clone(this.baseIoArgs);
    this.ioArgs.content = null;
    this.set("content","");
    this.set("href", urlString);
  },

  
  /**
   * Overrides built in refresh so we can control it. This is called when the tab is made visible or 
   * for initial loading. This will check if we should load the original content.
   */
  refresh:function(){
    this.cancel();
    this.onLoadDeferred = new dojo.Deferred(dojo.hitch(this, "cancel"));
    this.onLoadDeferred.addCallback(dojo.hitch(this, "onLoad"));
    var pane = this;

    if(!this.isLoaded){
      this._load();      
    }
    else if((this.originalHref.length) && (this.href != this.originalHref) ){
      this.loadOriginalHref();
    }      
    else{
      this._load();
    }
    
    return this.onLoadDeferred;
  },
  
  
  /**
   * Will check the url for a tabName value. If this is the same as the current tabName,
   * then the select property will be set to true.
   */
  _selectTabIfTabNameInUrl:function(){
    if (window.location.search) {
      var queryParams = dojo.queryToObject(window.location.search.slice(1));
      var tabName = queryParams["tabName"];
      
      if(tabName == this.id){
        this.selected = true;
      }
    }
  },
  
  
  /**
   * Called by this.refresh() to load the original href
   */
  loadOriginalHref:function(){
    var pane = this;
    if(!pane.originalHref.length){
      return;
    }
    this.loadHref(pane.originalHref);
  },
  
  
  /**
   * Overrides the default _onError so that we can display error messages from 
   * the server in the case of a 500 HTTP status code.
   * 
   * @param {Object} type
   * @param {Object} err
   * @param {Object} consoleText
   */
  _onError: function(type, err, consoleText){
    // don't do anything.
  },
    
  
  
  /**
   * Fires when an href has finished downloading content.
   * 
   * @param {Object} e
   */
  onDownloadEnd:function(e){
    this._constrainFormSubmissions();
  },
  
  
  /**
   * Intercepts a click event on the contentpane. Sees if the click event originated from an 
   * href link and then will load the href inside of the ContentPane's href attribute. This
   * keeps links inside of the pane.
   * 
   * If the anchor already has an onclick event associated with it, then don't do anything.
   *
   * Note: This doesn't work if more than 1 html element is inside of an <a> element.
   * 
   * @param {Object} e
   */
  _onClick:function(e){
    if (!this.containLinks) 
      return;  
    if(!e.target){
      return;
    } 
    var elem = e.target;
    var parent = elem.parentNode; 

    if((elem.tagName == "A") || ((parent) && (parent.tagName == "A")) ){
      var pane = this;
      if((parent) && (parent.tagName == "A")){
        elem = parent;
      }
      var ignore = (dojo.hasAttr(elem,"target") || dojo.hasAttr(elem,"onclick") || dojo.hasClass(elem,"dialogPopUpLink"));
      if (!ignore) {
        if(e.altKey){
          window.open(elem.href);
        }   
        else{
          this.loadHref(elem.href);
        }
        dojo.stopEvent(e);
      }
    }   
  },
  
  
  /**
   * This will make sure that all form submissions just refresh the ContentPane's href attribute.
   * This makes the form submission stay in the ContentPane and allow it to act like an iFrame.
   * Important Notes:
   *    This will manually stuff the form into xhr ioArgs object because the content pane will
   *    destroy the form and in IE 6 before the form content is actually sent.
   *    
   * File Uploads:
   *    This will detect a multipart form and use a dojo.io.iframe element if it is found.
   *    If this happens the response from the server MUST BE WRAPPED in a <textarea> tag
   *    or this code will throw an error.
   *    
   *    Example of Server code: 
   *    if(request.format == "multipartForm"){
   *        render("""
   *            <textarea>
   *                ${g.render(template:"home_content")}
   *            </textarea>                 
   *        """);
   *    }
   *    else{
   *        render(template:"home_content")
   *    }
   * 
   */
  _constrainFormSubmissions:function(){
    if (!this.containLinks) 
        return;     
    var pane = this;
    
    // Add click handlers to submit elements so the submit button values get submitted.
    // This is needed for get g:actionSubmit working inside of a ContentPane.
    dojo.query('input[type="submit"][name^="_action"]', pane.domNode).connect("onclick",function(){
      var hiddenElem = dojo.create("input",{"type":"hidden", "value":this.value, "name":this.name});
      dojo.place(hiddenElem,this,"before");   
    });
  
    // Add form submit handler to content
    var allForms = dojo.query("form", pane.domNode);
    for(var i=0; i<allForms.length; i++){
      var thisForm = allForms[i];
      var beenChanged = dojo.attr(thisForm,"newSubmitApplied");
      var ignore = dojo.hasAttr(thisForm,"target");
      var oldOnSubmit = dojo.attr(thisForm,"onsubmit") || null;
      
      if(!beenChanged && !ignore){
        dojo.attr(thisForm,"newSubmitApplied","true");
        dojo.attr(thisForm,"onsubmit","");
                
        dojo.connect(thisForm, "onsubmit", pane, function(e){
          dojo.stopEvent(e);          
          var thisForm = e.target;
          var currentPane = this;
          var formHref = dojo.attr(thisForm, "action");
          var encType = dojo.attr(thisForm, "enctype");
          
          // Run any user supplied onsubmit code.
          if(oldOnSubmit && !oldOnSubmit.apply()){
            return false;
          }          
          
          if (encType == 'multipart/form-data'){ // Send via iFrame
            var ioArgs = {
              url:formHref,
              method:"POST",
              form:thisForm,
              content:{"dojoIoIframeTransport":"true"},
              multiPart:true,
              handleAs:"text",
              error: function(err, args){
                console.log(args);
                var errorText = "<h3>Server Error</h3> Make sure your rendered response is wrapped in a <b>&lt;ui:ajaxUploadResponse&gt;</b> tag."
                currentPane._setContent(errorText, true);
              },
              load:function(response,ioArgs){
                currentPane._setContent(response,true);
                currentPane._constrainFormSubmissions();
              }        
            }
            dojo.io.iframe.send(ioArgs);
          }
          else { // Send via posted xhr.
            currentPane.ioArgs = dojo.clone(currentPane.baseIoArgs);
            currentPane.ioMethod = dojo.xhrPost;
            currentPane.ioArgs.content = dojo.formToObject(thisForm);
            currentPane.set("href", formHref);
          }
        }); 
      } 
    }
    
  } 
    
    

      
}); 

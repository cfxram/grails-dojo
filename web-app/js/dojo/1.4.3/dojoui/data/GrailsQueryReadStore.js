dojo.provide("dojoui.data.GrailsQueryReadStore");
dojo.require("dojox.data.QueryReadStore");

dojo.declare("dojoui.data.GrailsQueryReadStore", dojox.data.QueryReadStore,{
  urlPreventCache:true,
  max: 10,
  defaultGrailsSort: "",  
  
  
  /**
   *
      {
        query: {name: "A*"}, 
        queryOptions: {ignoreCase: true}, 
        sort: [{attribute:"name", descending:false}], 
        start: 100, 
        count: 10
      }
      
      to 
        offset:10, max:20, sort:"title", order:"asc"
        
   */
   fetch:function(request){
     console.log('fetch');
     var order = "asc";
     var sort = [];            
     if(request.sort){
       order = (request.sort[0].descending) ? "desc" : "asc",
       sort = [request.sort[0].attribute];
     } 
     else{                
       if(this.defaultGrailsSort.length > 0){
         sort = [this.defaultGrailsSort];
       }
     }

     request.serverQuery = {
       order: order,
       sort: sort,
       max: this.max, 
       offset: request.start
     };
     request.count = null;
     request.sort = sort;      
     return this.inherited(arguments);               
   },    
  
  
  
  
  
  _fetchItems: function(request, fetchHandler, errorHandler){
    var serverQuery = request.serverQuery || request.query || {};
    if(!this.doClientPaging){
      serverQuery.start = request.start || 0;
      if(request.count){
        serverQuery.count = request.count;
      }
    }
    if(!this.doClientSorting){
      if(request.sort){
        var sort = request.sort[0];
        if(sort && sort.attribute){
          serverQuery.sort = sort.attribute
        }
      }
    }
    if(this.doClientPaging && this._lastServerQuery !== null &&
      dojo.toJson(serverQuery) == dojo.toJson(this._lastServerQuery)){
      this._numRows = (this._numRows === -1) ? this._items.length : this._numRows;
      fetchHandler(this._items, request, this._numRows);
    }
    else{
      var xhrFunc = this.requestMethod.toLowerCase() == "post" ? dojo.xhrPost : dojo.xhrGet;
      var xhrHandler = xhrFunc({url:this.url, handleAs:"json-comment-optional", content:serverQuery});
      xhrHandler.addCallback(dojo.hitch(this, function(data){
        this._xhrFetchHandler(data, request, fetchHandler, errorHandler);
        }));
        xhrHandler.addErrback(function(error){
          errorHandler(error, request);
        });
        this.lastRequestHash = new Date().getTime()+"-"+String(Math.random()).substring(2);
        this._lastServerQuery = dojo.mixin({}, serverQuery);
      }
    }  

});
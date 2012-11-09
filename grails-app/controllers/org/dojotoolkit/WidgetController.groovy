package org.dojotoolkit

import grails.converters.JSON

class WidgetController {

  def index = {
    render(view:"index")
  }

  def fileUpload = {}

  def plainRemote = {}

  def testRemoteLink = {
    Thread.currentThread().sleep(1000);
    render {
      div(style:"border:3px solid orange; background:#eee; padding:5em;", "Remote Link - Passed.")
    }
  }

  def remotePostFromLink = {
    render {
      div(style:"border:3px solid blue; background:#eee; padding:5em;", "Remote Link with URL Map -  Passed.")
    }    
  }
  
  def remoteRequestWithUrlMap = {
    render {
      div(style:"border:3px solid red; background:#eee; padding:5em;", "Remote Request With Url Map - Passed.")
    }    
  }
  
  def synchronizedRemoteLink = {
    render {
      div(style:"border:3px solid green; background:#eee; padding:5em;", "Synchronized Remote Link - Passed.")
    } 
  }
  
  def remoteFunctionAction = {
    println "In Remote Function Action"
    println params
    render {
      div(style:"border:3px solid ${params?.color}; background:#eee; padding:5em;", "Remote Function - Passed.")
    }    
  }  
  
  def remoteFunctionWithParams = {
    render {
      div(style:"border:3px solid ${params?.color}; background:#eee; padding:5em;", "${params.pluginName} - Remote Function With Params - Passed.")
    }
  }
  
  def remoteField = {
    render {
      div(style:"border:3px solid ${params?.color}; background:#eee; padding:5em;", "Remote Field - Passed.")
    }
  }  

  def remoteJsFunction = {
    println "This was passed from the browser: ${params.name}"
    render {
      div(style:"border:3px solid pink; background:#eee; padding:5em;", "${params.name} Remote Function (from JS) - Passed.")
    }
  }  

  def remoteFormSubmit = {
    render {
      div(style:"border:3px solid green; background:#eee; padding:1em;", "${params} Remote Form Submit - Passed.")
    }
  }

  def remoteFormSubmitAsGet = {
    render {
      div(style:"border:3px solid red; background:#eee; padding:1em;", "${params} Remote Form Get - Passed.")
    }
  }

  def submitToRemoteButton = {
    render {
      div(style:"border:3px solid blue; background:#eee; padding:1em;", "${params} Submit To Remote Button - Passed.")
    }
  }

  def remoteDialogContent = {
     render(view:"remoteDialogContent")
  }

  def saveFileWithAjax = {
    def f = request.getFile('myFile')

    if(f.empty){
      render(status: '503', view:'saveFileWithAjax',model: [results:"File content was not found - FAILED.", color: "red"])
      return
    }
    else{
      render(view:'saveFileWithAjax',model: [results:"Submit File via Ajax - <strong>${f.getOriginalFilename()}</strong>", color: "green"])
      return
    }

  }


  def remoteDialogContentWithLinks = {
    Thread.currentThread().sleep(3000);
    render(view:'remoteDialogContentWithLinks')
  }

  def remoteDialogContentWithForm = {}


  def remoteDijitContent = {}

  def popOverForm = {}

  def dialogAndPopovers = {}


  def list = {
    Thread.currentThread().sleep(1000);
    if (!params.max) params.max = 10
    [widgetList: Widget.list(params), total: Widget.count()]
  }

  def listFragment = {
    if (!params.max) params.max = 10
    render(template: "list", model: [widgetList: Widget.list(params), total: Widget.count()])
  }

  def dijits = {}
  def grid = {}
  def popOver = {}
  def dialog = {}
  def editor = {}
  def editorFeatures = {}
  def dateTime = {}
  def panel = {}
  def treeCombo = {}


  /**
   * Test saving of the dojo editor. Just look at the console of the outputed values.
   */
  def saveAllEditors = {
    println params
    redirect(action:editor)
  }

  /**
   * Will display examples of the <dojo:tree> object. This will also created a static JSON string to
   * test out static data populating a tree object.
   */
  def tree = {
    // Generate the JSON
    List releasedWidgets = Widget.findAllByReleased(true);
    List protoTypeWidgets = Widget.findAllByReleased(false);

    def jsonMap = [
      identifier: "id",
      label:"name",
      items:[]
    ]

    def releasedWidgetMap = [
      parent:"",
      id:999998,
      name:"Released Widgets",
      hasChildNodes:true
    ]
    def unreleased = [
      parent:"",
      id:999999,
      name:"Protottypes",
      hasChildNodes:true
    ]
    jsonMap.items.add(releasedWidgetMap)
    jsonMap.items.add(unreleased)
    releasedWidgets.each{w->
      jsonMap.items.add([parent:999998, id:w?.id, name:w?.name, category:w?.category, discounted:w?.discounted.toString() ])
    }
    protoTypeWidgets.each{w->
      jsonMap.items.add([parent:999999, id:w?.id, name:w?.name, category:w?.category, discounted:w?.discounted.toString()])
    }
    render(view:"tree", model:[jsonString:jsonMap as JSON])
  }

  /**
   * Will get the widgets nested as a tree structure to demo the tree component
   */
  def treeJson = {
    List releasedWidgets = Widget.findAllByReleased(true);
    List protoTypeWidgets = Widget.findAllByReleased(false);

    def jsonMap = [
      identifier: "id",
      label:"name",
      items:[]
    ]

    def releasedWidgetMap = [
      parent:"",
      id:999998,
      name:"Released Widgets",
      hasChildNodes:true
    ]
    def unreleased = [
      parent:"",
      id:999999,
      name:"Protottypes",
      hasChildNodes:true
    ]
    jsonMap.items.add(releasedWidgetMap)
    jsonMap.items.add(unreleased)
    releasedWidgets.each{w->
      jsonMap.items.add([parent:999998, id:w?.id, name:w?.name, category:w?.category, discounted:w?.discounted.toString() ])
    }
    protoTypeWidgets.each{w->
      jsonMap.items.add([parent:999999, id:w?.id, name:w?.name, category:w?.category, discounted:w?.discounted.toString()])
    }
    render jsonMap as JSON
  }


  /**
   * Will display widgets as JSON to be consumed by the dojo grid component
   */
  def listJson = {
    println params
    def widgets,widgetsTotal   
      
    if(params?.shape){
      widgets = Widget.findAllByShapeLike("%${params?.shape}%");
      widgetsTotal = widgets.size();
      widgets = Widget.findAllByShapeLike("%${params?.shape}%",params);
      println widgets.size();
    }
    else{
      widgetsTotal = Widget.list().size();      
      widgets = Widget.list(params)
    }

    def jsonMap = [
      identifier: "id",
      numRows:widgetsTotal,
      items:[]
    ]
    widgets.each {w ->
      jsonMap.items.add([
        id:w.id,
        name:w.name,
        color:w.color,
        shape:w.shape
      ])
    }

    render jsonMap as JSON

  }

}

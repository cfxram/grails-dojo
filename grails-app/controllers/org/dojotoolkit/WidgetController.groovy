package org.dojotoolkit


class WidgetController {

  def index = { }

  def remotePage = {

  }
  def remotePage2 = {
    render(view: 'remotePage')
  }

  def remoteFormSubmit = {
    println "In Remote Form Submit"
    println params
  }

  def remoteFunctionAction = {
    println "In Remote Function Action"
    println params
  }

  def list = {
    if (!params.max) params.max = 10
    [widgetList: Widget.list(params), total: Widget.count()]
  }

  def listFragment = {
    if (!params.max) params.max = 10
    render(template: "list", model: [widgetList: Widget.list(params), total: Widget.count()])
  }

  def dijits = {

  }

  def grid = {

  }

  def listJson = {
    println params  
    def widgetsTotal = Widget.list().size();      
    def widgets = Widget.list(params)
    
    render(contentType: "text/json") {
      identifier("id")
      numRows(widgetsTotal)
      items {
        widgets.each {w ->
          item(
            id:w.id,
            selectRow:1,      
            name:w.name,
            color:w.color,
            shape:w.shape
          )  
        }
      }
    }
    
  }
}

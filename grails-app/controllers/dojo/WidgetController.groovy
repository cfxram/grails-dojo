package dojo

class WidgetController {

    def index = { }

    def remotePage = {
      
    }

    def remoteFormSubmit = {
      println "In Remote Form Submit"
      println params
    }

    def remoteFunctionAction = {
      println "In Remote Function Action"
      println params
    }
}

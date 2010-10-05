package org.dojotoolkit


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


    def list = {
		if(!params.max) params.max = 10
		[widgetList:Widget.list( params ),total:Widget.count() ]
    }

    def listFragment = {
		if(!params.max) params.max = 10
        render(template:"list", model:[widgetList:Widget.list( params ),total:Widget.count() ])
    }
}

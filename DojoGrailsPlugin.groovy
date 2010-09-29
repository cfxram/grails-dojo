import org.codehaus.groovy.grails.plugins.web.taglib.*

class DojoGrailsPlugin {
  def version = "1.4.3"
  def grailsVersion = "1.3.4 > *"
  def dependsOn = [:]
  def pluginExcludes = [
          "grails-app/views/*"
  ]
  def author = "Rob Meidal"
  def authorEmail = "cfxram at gmail"
  def title = "Dojo for Grails"
  def description = "Adds the Dojo Toolkit as a Javascript Provider for Grails."
  def documentation = "http://grails.org/plugin/grails-dojo"

  def doWithApplicationContext = { applicationContext ->
    JavascriptTagLib.PROVIDER_MAPPINGS.dojo = DojoProvider.class
    // expects /js/dojo/1.4.3/dojo/dojo.js
    JavascriptTagLib.LIBRARY_MAPPINGS.dojo = ["dojo/${version}/dojo/dojo"]
  }
}

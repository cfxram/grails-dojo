import org.dojotoolkit.*
import org.codehaus.groovy.grails.plugins.web.taglib.JavascriptTagLib
// Uncompressed 
class DojoGrailsPlugin {
  def version = "1.6.1.6-DEV"
  def grailsVersion = "1.3.0 > *"
  def dependsOn = [:]
  def pluginExcludes = [
          "grails-app/conf/Config.groovy",
          "grails-app/conf/BootStrap.groovy",
          "grails-app/controllers/**",
          "grails-app/domain/**",
          "grails-app/views/**",
          "web-app/js/prototype/**",
          "web-app/js/appwidgets/**"
  ]
  def author = "Rob Meidal"
  def authorEmail = "cfxram@codehaus.org"
  def title = "Dojo 1.6.1 for Grails"
  def description = """
    The Dojo Plugin adds the Dojo toolkit to your application. This javascript library provides
    a complete collection of user interface controls, giving you the power to create web
    applications that are highly optimized for usability, performance, internationalization,
    accessibility, but above all deliver an incredible user experience. This plugin provides full
    support for <g:formRemote>, <g:remoteField>, <g:remoteFunction>, <g:remoteLink> and
    <g:submitToRemote>.

    Also adds these two tags: <dojo:paginate> and <dojo:sortableColumn>. These do the same thing
    that the grails versions of the tags but will do them via ajax calls.

    Adds these convenient widget tags:
      <dojo:header>, <dojo:require>, <dojo:css>,
      <dojo:grid> (<dojo:col>, <dojo:bind>),
      <dojo:dataSourceView> (<dojo:nodeTemplate>, <dojo:nodeDefaultTemplate>),
      <dojo:popOver> (<dojo:popOverContent>, <dojo:closePopOver>),
      <dojo:frame> (<dojo:frameLink> <dojo:onload>),
      <dojo:tree> (<dojo:treeNode>, <dojo:treeLeaf>),
      <dojo:help>,
      <dojo:toolip>,
      <dojo:editor>,
      <dojo:datePicker>,
      <dojo:timePicker>,
      <dojo:dateTimePicker>,
      <dojo:numberPicker>

    For more information about the Dojo Toolkit please visit http://www.dojotoolkit.org/.
  """  
  def documentation = "http://grails.org/plugin/dojo"

  def doWithApplicationContext = { applicationContext ->
    JavascriptTagLib.PROVIDER_MAPPINGS.dojo = DojoProvider.class
    JavascriptTagLib.LIBRARY_MAPPINGS.dojo = ["dojo/${Dojo.version}/dojo/dojo"]
  }
}

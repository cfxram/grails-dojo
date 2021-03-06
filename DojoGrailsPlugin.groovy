import org.codehaus.groovy.grails.plugins.web.taglib.JavascriptTagLib
import org.dojotoolkit.Dojo
import org.dojotoolkit.DojoProvider

class DojoGrailsPlugin {
  def version = "1.7.2.0"
  def grailsVersion = "1.3.0 > *"
  def pluginExcludes = [
          "grails-app/controllers/**",
          "grails-app/domain/**",
          "grails-app/views/**",
          "web-app/js/appwidgets/**"
  ]
  def title = "Dojo 1.7.2 for Grails"
  def description = """
    The Dojo Plugin adds the Dojo toolkit to your application. This javascript library provides
    a complete collection of user interface controls, giving you the power to create web
    applications that are highly optimized for usability, performance, internationalization,
    accessibility, but above all deliver an incredible user experience. This plugin provides full
    support for <g:formRemote>, <g:remoteField>, <g:remoteFunction>, <g:remoteLink> and
    <g:submitToRemote>.

    Also adds these two tags: <dojo:paginate> and <dojo:sortableColumn>. These do the same thing
    that the Grails versions of the tags but does them via Ajax calls.

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

  def license = 'APACHE'
  def developers = [
    [name: 'Rob Meidal', email: 'cfxram@codehaus.org']
  ]
  def issueManagement = [system: 'JIRA', url: 'http://jira.grails.org/browse/GPDOJO']
  def scm = [url: 'https://github.com/cfxram/grails-dojo']

  def doWithApplicationContext = { applicationContext ->
    JavascriptTagLib.PROVIDER_MAPPINGS.dojo = DojoProvider
    JavascriptTagLib.LIBRARY_MAPPINGS.dojo = ["dojo/${Dojo.version}/dojo/dojo"]
  }
}

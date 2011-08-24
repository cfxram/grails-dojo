import org.dojotoolkit.*
import org.codehaus.groovy.grails.plugins.web.taglib.JavascriptTagLib
// Uncompressed 
class DojoGrailsPlugin {
  def version = "1.6.1"
  def grailsVersion = "1.3.0 > *"
  def dependsOn = [:]
  def pluginExcludes = [
          "grails-app/conf/Config.groovy",
          "grails-app/conf/BootStrap.groovy",
          "grails-app/controllers/**",
          "grails-app/domain/**",
          "grails-app/views/**",
          "web-app/js/prototype/**"    
  ]
  def author = "Rob Meidal"
  def authorEmail = "cfxram@codehaus.org"
  def title = "Dojo 1.6.1 for Grails"
  def description = """
    Adds Dojo Base as a Javascript Provider for Grails. This provides full support for
    <g:formRemote>, <g:remoteField>, <g:remoteFunction>, <g:remoteLink> and <g:submitToRemote>.

    Also adds two dojo tags: <dojo:paginate> and <dojo:sortableColumn>. These do the same thing
    that the grails versions of the tags but will do them via ajax calls. 

    For more information about the Dojo Toolkit please visit http://www.dojotoolkit.org/.
  """  
  def documentation = "http://grails.org/plugin/dojo"

  def doWithApplicationContext = { applicationContext ->
    JavascriptTagLib.PROVIDER_MAPPINGS.dojo = DojoProvider.class
    JavascriptTagLib.LIBRARY_MAPPINGS.dojo = ["dojo/${Dojo.version}/dojo/dojo"]
  }
}

//
// This script is executed by Grails when the plugin is uninstalled from project.
// Use this script if you intend to do any additional clean-up on uninstall, but
// beware of messing up SVN directories!
//
Ant.property(environment: "env")
grailsHome = Ant.antProject.properties."env.GRAILS_HOME"
def dojoDir = "/web-app/js/dojo"

Ant.sequential {
  delete(dir: "${basedir}${dojoDir}")
}
event("StatusFinal", ["Dojo Core 1.4.3 has been removed."])
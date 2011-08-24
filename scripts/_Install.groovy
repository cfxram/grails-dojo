//
// This script is executed by Grails after plugin was installed to project.
// This script is a Gant script so you can use all special variables provided
// by Gant (such as 'baseDir' which points on project base dir). You can
// use 'ant' to access a global instance of AntBuilder
//
// For example you can create directory under project tree:
//
//    ant.mkdir(dir:"${basedir}/grails-app/jobs")
//

Ant.property(environment: "env")
grailsHome = Ant.antProject.properties."env.GRAILS_HOME"
def dojoDir = "/web-app/js/dojo/1.6.1/dojo"

Ant.sequential {
  event("StatusUpdate", ["Copying dojo.js file into the application"])

  mkdir(dir: "${basedir}${dojoDir}")
  copy(todir: "${basedir}${dojoDir}") {
    fileset(dir: "${pluginBasedir}${dojoDir}") {
      include(name: "dojo.js")
      include(name: "LICENSE")
    }
  }
}
event("StatusFinal", ["\nDone.'${dojoDir}/dojo.js' has been copied into the application.\n"])

Ant.property(environment:"env")
grailsHome = Ant.antProject.properties."env.GRAILS_HOME"

/*
 Link to compressed (but not zipped):
 http://download.dojotoolkit.org/release-1.4.3/dojo-release-1.4.3/
 
 Link to the source (zipped):
 http://download.dojotoolkit.org/release-1.4.3/dojo-release-1.4.3-src.zip

 Link to compressed (zipped):
 http://download.dojotoolkit.org/release-1.4.3/dojo-release-1.4.3.zip
*/

def dojoVersion = "1.4.3"
def destinationDir = "${basedir}/web-app/js/dojo/${dojoVersion}"

includeTargets << grailsScript("Init")

target(main: "This will download and install the full dojo toolkit into the application.") {
    Ant.sequential {
      mkdir(dir:"${grailsHome}/downloads")

      event("StatusUpdate", ["Downloading Dojo ${dojoVersion}"])

      get(dest:"${grailsHome}/downloads/dojo-release-${dojoVersion}.zip",
              src:"http://download.dojotoolkit.org/release-${dojoVersion}/dojo-release-${dojoVersion}.zip",
              verbose:true,
              usetimestamp:true)
      unzip(dest:"${grailsHome}/downloads",
              src:"${grailsHome}/downloads/dojo-release-${dojoVersion}.zip")

      mkdir(dir:destinationDir)

      copy(todir:destinationDir) {
        fileset(dir:"${grailsHome}/downloads/dojo-release-${dojoVersion}/", includes:"**/**")
      }
    }
    event("StatusFinal", ["Dojo ${dojoVersion} was downloaded and copied to the application."])
}

setDefaultTarget(main)

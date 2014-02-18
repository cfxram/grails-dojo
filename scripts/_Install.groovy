// Load Dojo static class
GroovyClassLoader classLoader = new GroovyClassLoader()
Class Dojo = classLoader.parseClass(new File(dojoPluginDir, "src/groovy/org/dojotoolkit/Dojo.groovy"))
def dojoDir = "/web-app/js/dojo/${Dojo.version}/dojo"

event("StatusUpdate", ["Copying dojo.js file into the application"])
mkdir(dir: "${basedir}${dojoDir}")
copy(todir: "${basedir}${dojoDir}") {
  fileset(dir: "${pluginBasedir}${dojoDir}") {
    include(name: "dojo.js")
    include(name: "LICENSE")
  }
}
event("StatusFinal", ["\nDone.'${dojoDir}/dojo.js' has been copied into the application.\n"])

import grails.converters.deep.JSON
import grails.util.Environment

// Load Dojo static class
GroovyClassLoader classLoader = new GroovyClassLoader()
Class Dojo = classLoader.parseClass(new File(dojoPluginDir, "src/groovy/org/dojotoolkit/Dojo.groovy"))

def srcHref = "http://download.dojotoolkit.org/release-${Dojo.version}/dojo-release-${Dojo.version}-src.zip"
def downloadDir = "${grailsWorkDir}/download"
def tmpWorkingDir = "${basedir}/web-app/js/dojoTmp"
def dojoUtilDir = "${tmpWorkingDir}/util/"
def dojoReleaseDir = "${tmpWorkingDir}/release"
def dojoCssBuildFile = "${tmpWorkingDir}/css/custom-dojo.css"
def dojoProfile = "${tmpWorkingDir}/dojo.profile.js"
def dojoUiDir = "${dojoPluginDir}/web-app/js/dojo/${Dojo.version}/dojoui"
def config = new ConfigSlurper(Environment.current.name).parse(new File(basedir, "grails-app/conf/Config.groovy").toURI().toURL())


target(createOptimizedDojoBuild: "This is the main target for this script."){
  println "Creating an Optimized Dojo build..."
  depends(downloadDojoSource, createDojoCssBuild, createDojoProfile, buildDojo, copyDojoToStage ,cleanup)
}


/**
 * DownloadSource - Installs the source version of Dojo. (About 25MB)
 * Copies the src to the application. From there it builds the
 * customized version.
 *
 * By copying the source to the application, we can include app specific
 * extensions to our custom release.
 */
target(downloadDojoSource: "Downloads the source version of Dojo.") {
  println "Downloading Dojo ${Dojo.version} source files..."

  mkdir(dir: downloadDir)
  mkdir(dir: tmpWorkingDir)
  mkdir(dir: dojoReleaseDir)
  get(dest: "${downloadDir}/dojo-src-${Dojo.version}.zip", src: "${srcHref}", verbose: true, usetimestamp: true)
  unzip(dest: downloadDir, src: "${downloadDir}/dojo-src-${Dojo.version}.zip")
  move(todir: tmpWorkingDir) {
    fileset(dir: "${downloadDir}/dojo-release-${Dojo.version}-src", includes: "**/**")
  }
  copy(todir: "${tmpWorkingDir}/dojoui/") {
    fileset(dir: dojoUiDir, includes: "**/**")
  }
}

/**
 * Creates a css file and add @import statements that the build process can use.
 */
target(createDojoCssBuild: "Creates a css build file.") {
  println "Creating Custom Dojo CSS build file..."
  def jsonString = config.dojo.profile
  def jsonObj = JSON.parse("{${jsonString}}").dependencies;
  if (jsonObj?.css) {
    mkdir(dir: "${tmpWorkingDir}/css");
    echo(file: dojoCssBuildFile, append: false, message: "/* This is generated by the dojo plugin build script */\n\n")
    jsonObj?.css?.dependencies?.each {
      echo(file: dojoCssBuildFile, append: true, message: "@import '${it}';\n")
    }
  }
}

/**
 * Creates the dojo profile script file. Reading the Config.groovy statically since
 * it hasn't been compiled. Then it creates the dojo.profile.js file.
 */
target(createDojoProfile: "Creates the dojo profile js file") {
  println "Creating dojo profile js file..."
  echo(file: dojoProfile, append: false, message: "/* This is generated by the dojo plugin build script */\n\n")
  echo(file: dojoProfile, append: true, message: config.dojo.profile)
}

/**
 * Build Dojo - Does the same as call the shell script to create the optimized
 *              version of dojo. Uses the Rhino version to create the build.
 *
 * Example of original build. (Assumes that it's being called from the buildscripts directory) :
 * java -Xms256m -Xmx256m -cp ./../shrinksafe/js.jar:./../closureCompiler/compiler.jar
 * :./../shrinksafe/shrinksafe.jar org.mozilla.javascript.tools.shell.Main
 * ./../../dojo/dojo.js baseUrl=./../../dojo load=build  action=release profile=../../../../../grails-app/conf/dojo.profile.js
 *
 * Furthur notes about Dojo 1.7 custom builds:
 * http://livedocs.dojotoolkit.org/releasenotes/1.7#considerations-for-custom-builds
 *
 */
target(buildDojo: "Runs shrinksafe to create an optimized version of dojo") {
  println "Runnning shrinksafe to create an optimized dojo..."
  def build_classpath = ant.path {
    pathelement(location: "${dojoUtilDir}/shrinksafe/js.jar")
    pathelement(location: "${dojoUtilDir}/shrinksafe/shrinksafe.jar")
  }
  java(classname: "org.mozilla.javascript.tools.shell.Main", fork: true,
          dir: "${dojoUtilDir}/buildscripts", classpath: build_classpath) {
    arg(value: "${dojoUtilDir}/buildscripts/build.js")
    arg(value: "profileFile=${dojoProfile}")
    arg(value: "action=release")
    arg(value: "optimize=shrinksafe,comments")
    arg(value: "copyTests=off")
    arg(value: "cssOptimize=comments,keepLines")
  }
  delete(includeemptydirs: true) {
    fileset(dir: dojoReleaseDir, includes: "**/tests/**/")
    fileset(dir: dojoReleaseDir, includes: "**/demos/**/")
    fileset(dir: dojoReleaseDir, includes: "**/themeTester*")
    fileset(dir: dojoReleaseDir, includes: "**/*.psd")
    fileset(dir: dojoReleaseDir, includes: "**/*.fla")
    fileset(dir: dojoReleaseDir, includes: "**/*.svg")
    fileset(dir: dojoReleaseDir, includes: "**/*.as")
    fileset(dir: dojoReleaseDir, includes: "**/*.swf")
    fileset(dir: dojoReleaseDir, includes: "**/*.uncompressed.js")
  }
}

/**
 * Copies the customized dojo release to the staging directory during war
 * creation. This is called from _Events.groovy.
 */
target(copyDojoToStage: "Copies the optimized dojo release to stage.") {
  println "Copying optimized dojo release to the staging area..."
  def destinationDir = "${stagingDir}/js/dojo/${Dojo.pluginVersion}"
  copy(todir: "${destinationDir}-custom") {
    fileset(dir: dojoReleaseDir)
  }
}

/**
 * Copies the customized dojo release to the application. This is called
 * from InstallDojo.groovy.
 */
target(copyDojoToApp: "Copies the optimized dojo release to application.") {
  println "Copying custom dojo build to the application..."
  def destinationDir = "${basedir}/web-app/js/dojo/${Dojo.pluginVersion}"
  copy(todir: "${destinationDir}-custom") {
    fileset(dir: dojoReleaseDir)
  }
}

/**
 * Deletes the tmp Directory.
 */
target(cleanup: "Copies the optimized dojo release to application.") {
  println "Cleaning up custom dojo build tmp files..."
  delete(dir: tmpWorkingDir)
  delete(file: dojoProfile)
}

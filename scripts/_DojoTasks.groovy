
/**
 * Download the Dojo Release: Install the release version of dojo. (About 4MB)
 */
target(downloadDojoRelease: "This will download and install the full dojo toolkit into the application.") {
  def Dojo = classLoader.loadClass("org.dojotoolkit.Dojo")
  def destinationDir  = "${basedir}/web-app/js/dojo/${Dojo.version}"
  def downloadDir     = "${grailsWorkDir}/download"
  event("StatusUpdate", ["\nDownloading Dojo ${Dojo.version} release.\n"])
  Ant.sequential {
    mkdir(dir: downloadDir)
    get(dest: "${downloadDir}/dojo-release-${Dojo.version}.zip", src: Dojo.releaseHref, verbose: true, usetimestamp: true)
    unzip(dest: downloadDir, src: "${downloadDir}/dojo-release-${Dojo.version}.zip")
    mkdir(dir: destinationDir)
    copy(todir: destinationDir) {
      fileset(dir: "${downloadDir}/dojo-release-${Dojo.version}/", includes: "**/**")
    }
  }
  event("StatusFinal", ["\nDojo ${Dojo.version} was downloaded and copied to the application.\n"])
}

/**
 * DownloadSource - This will install the source version of Dojo. (About 25MB)
 */
target(downloadDojoSource: "This will download the source version of Dojo.") {
  def Dojo = classLoader.loadClass("org.dojotoolkit.Dojo")
  def downloadDir     = "${grailsWorkDir}/download"  
  event("StatusUpdate", ["\nDownloading Dojo ${Dojo.version} source files.\n"])
  Ant.sequential {
    mkdir(dir: downloadDir)
    get(dest: "${downloadDir}/dojo-src-${Dojo.version}.zip", src: "${Dojo.srcHref}", verbose: true, usetimestamp: true)
    unzip(dest: downloadDir, src: "${downloadDir}/dojo-src-${Dojo.version}.zip")
  }
  event("StatusFinal", ["\nDojo ${Dojo.version} source was downloaded and copied to the application.\n"])
}



/**
 * Build Dojo - This will do the same as call the shell script to create the optimized
 *              version of dojo. Thanks to Kevin Haverlock's article:
 *              http://www.ibm.com/developerworks/websphere/techjournal/1003_col_haverlock/1003_col_haverlock.html
 */
target(buildDojo: "This will run shrinksafe to create an optimized version of dojo") {
  println stagingDir
  depends(downloadDojoSource)
  def Dojo = classLoader.loadClass("org.dojotoolkit.Dojo")
  def destinationDir  = "${stagingDir}/web-app/js/dojo/${Dojo.version}"
  def downloadDir     = "${grailsWorkDir}/download" 
  def dojoUtilDir     = "${downloadDir}/dojo-release-${Dojo.version}-src/util/"
  def dojoReleaseDir  = "${downloadDir}/dojo-release-${Dojo.version}-src/release/dojo"
  def dojoProfile     = "${basedir}/grails-app/conf/dojo.profile.js"
  def shrinksafe_classpath = Ant.path {
    pathelement(location: "${dojoUtilDir}/shrinksafe/js.jar")
    pathelement(location: "${dojoUtilDir}/shrinksafe/shrinksafe.jar")
  }

  java(classname: "org.mozilla.javascript.tools.shell.Main", fork: true, dir: "${dojoUtilDir}/buildscripts", classpath: shrinksafe_classpath) {
    arg(value: "${dojoUtilDir}/buildscripts/build.js")
    arg(value: "profileFile=${dojoProfile}")
    arg(value: "action=release")
    arg(value: "optimize=shrinksafe")
    arg(value: "copyTests=off")
  }
  delete(includeemptydirs:true){
    fileset(dir:dojoReleaseDir, includes:"**/tests/**/")
    fileset(dir:dojoReleaseDir, includes:"**/demos/**/")
    fileset(dir:dojoReleaseDir, includes:"**/themeTester*")
    fileset(dir:dojoReleaseDir, includes:"**/*.psd")
    fileset(dir:dojoReleaseDir, includes:"**/*.fla")
    fileset(dir:dojoReleaseDir, includes:"**/*.svg")
    fileset(dir:dojoReleaseDir, includes:"**/*.as")
    fileset(dir:dojoReleaseDir, includes:"**/*.swf")
    fileset(dir:dojoReleaseDir, includes:"**/*.uncompressed.js")    
  }
  copy(todir: "${destinationDir}-custom") {
    fileset(dir: dojoReleaseDir, includes: "**/**")
  }

  event("StatusFinal", ["\n The customized version of dojo has been created.\n"])
}


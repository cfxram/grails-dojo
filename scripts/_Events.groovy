import grails.util.GrailsUtil

includeTargets << new File("${basedir}/scripts/_DojoTasks.groovy")

/**
 * In 1.1+ this is called after the staging dir is prepared but before the war is packaged.
 */
eventCreateWarStart = { name, stagingDir ->
  def classLoader = Thread.currentThread().contextClassLoader
  classLoader.addURL(new File(classesDirPath).toURL())
  def config = new ConfigSlurper(GrailsUtil.environment).parse(classLoader.loadClass('Config')).dojo
  def customBuild = config.customBuild ?: false

  if (customBuild) {
    println "\nCreating an Optimized Dojo build.\n"
    buildDojo()
  }
}




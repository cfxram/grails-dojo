import grails.util.GrailsUtil

includeTargets << new File("${basedir}/scripts/_DojoTasks.groovy")

eventCreateWarStart = { name, stagingDir ->
  def classLoader = Thread.currentThread().contextClassLoader
  classLoader.addURL(new File(classesDirPath).toURL())
  def config = new ConfigSlurper(GrailsUtil.environment).parse(classLoader.loadClass('Config')).dojo
  def customBuild = config.customBuild ?: false

  if (customBuild) {
    println "\nCreating an Optimized Dojo build.\n"
    buildDojo()
    copyDojoToStage()
    cleanup()
  }
}




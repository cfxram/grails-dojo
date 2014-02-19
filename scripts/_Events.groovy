import grails.util.Environment

includeTargets << new File(dojoPluginDir, "scripts/_DojoTasks.groovy")

eventCreateWarStart = { name, stagingDir ->
  def classLoader = Thread.currentThread().contextClassLoader
  classLoader.addURL(new File(classesDirPath).toURI().toURL())
  def config = new ConfigSlurper(Environment.current.name).parse(classLoader.loadClass('Config')).dojo
  def createCustomBuild = config.optimize.during.build ?: false

  if (createCustomBuild) {
    createOptimizedDojoBuild()
    event("StatusUpdate", ["Done creating an optimized dojo build."])
  }
}





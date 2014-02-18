grails.servlet.version = "2.5" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.6
grails.project.source.level = 1.6

grails.project.dependency.resolution = {
  // inherit Grails' default dependencies
  inherits("global") {
    // specify dependency exclusions here; for example, uncomment this to disable ehcache:
    // excludes 'ehcache'
  }
  log "error" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
  checksums true // Whether to verify checksums on resolve
  legacyResolve false // whether to do a secondary resolve on plugin installation, not advised and here for backwards compatibility

  repositories {
    inherits true
    grailsPlugins()
    grailsHome()
    grailsCentral()
    mavenLocal()
    mavenCentral()
  }

  dependencies {
  }

  plugins {
    // plugins for the build system only
    build ":tomcat:7.0.50"

    // plugins needed at runtime but not for compilation
    runtime ":hibernate:3.6.10.7" // or ":hibernate4:4.1.11.6"
    runtime ":database-migration:1.3.8"

    build ":release:3.0.1"
  }
}



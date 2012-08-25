grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
//grails.project.war.file = "target/${appName}-${appVersion}.war"
grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // uncomment to disable ehcache
        // excludes 'ehcache'
    }
    log "warn" 
    repositories {
        grailsPlugins()
        grailsHome()
        grailsCentral()
    }
    dependencies {}
    plugins {
      build   ":tomcat:$grailsVersion"
      compile ":hibernate:$grailsVersion"
      build ":release:2.0.4"      
    }  
}

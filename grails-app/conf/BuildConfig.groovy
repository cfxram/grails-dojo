grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"

grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // uncomment to disable ehcache
        // excludes 'ehcache'
    }
    log "warn" 
	legacyResolve false
	pom true
    repositories {
		mavenLocal()
        grailsPlugins()
        grailsHome()
        grailsCentral()
    }
    dependencies {}
    plugins {
    }  
}

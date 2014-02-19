grails.project.work.dir = 'target'

grails.project.dependency.resolution = {

    inherits 'global'
    log 'warn'

    repositories {
        grailsCentral()
        mavenLocal()
        mavenCentral()
    }

    plugins {

        build ":tomcat:$grailsVersion", {
            export = false
        }

        compile ":hibernate:$grailsVersion", {
            export = false
        }

        build ':release:2.2.1', ':rest-client-builder:1.0.3', {
            export = false
        }
    }
}

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
    build ":tomcat:7.0.50"
    runtime ":hibernate:3.6.10.7"
    build ":release:3.0.1"
  }
}

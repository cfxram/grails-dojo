includeTargets << grailsScript("_GrailsSettings")
includeTargets << grailsScript("_GrailsEvents")

includeTargets << new File("${basedir}/scripts/_DojoTasks.groovy")

target(main: "Will create the optimized dojo version and copy to the application.") {
  depends(downloadDojoSource, createDojoCssBuild, createDojoProfile, buildDojo, copyDojoToApp ,cleanup)
}

setDefaultTarget(main)

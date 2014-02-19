includeTargets << grailsScript("_GrailsSettings")
includeTargets << grailsScript("_GrailsEvents")

includeTargets << new File(dojoPluginDir, "scripts/_DojoTasks.groovy")

target(installCustomDojo: "Creates the optimized dojo version and copies to the application.") {
  depends(downloadDojoSource, createDojoCssBuild, createDojoProfile, buildDojo, copyDojoToApp ,cleanup)
}

setDefaultTarget(installCustomDojo)

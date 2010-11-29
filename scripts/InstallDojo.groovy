includeTargets << grailsScript("Init")
includeTargets << new File("${basedir}/scripts/_DojoTasks.groovy")


target(main: "The description of the script goes here!") {
    depends(buildDojo)
}

setDefaultTarget(main)

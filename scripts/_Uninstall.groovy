def dojoDir = "/web-app/js/dojo"

delete(dir: "${basedir}${dojoDir}")

event("StatusFinal", ["Dojo Plugin has been removed."])

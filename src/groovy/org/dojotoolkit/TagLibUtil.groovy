package org.dojotoolkit

class TagLibUtil {

  static String htmlProperties(params) {
    StringBuilder paramString = new StringBuilder()
    params.each {k, v ->
      paramString << " ${k}=\"${v}\""
    }
    return paramString
  }

  static String randomId() {
    return "${Math.round(Math.random() * 100000)}"
  }

  static String jsSafeString(inText) {
    def cleanedString = inText.replaceAll(/\s+/, " ")  //replace all internal whitespace
    cleanedString = cleanedString.replaceAll(/(^\s)|(\s$)/, "") // trim whitespace
    cleanedString = cleanedString.replaceAll(/\'/, /\\\'/)  // escape single quotes
    return cleanedString
  }
}

package org.dojotoolkit

class TagLibUtil {

  static String htmlProperties(params) {
    def paramString = ""
    params.each {k, v ->
      paramString += " ${k}=\"${v}\""
    }
    return paramString
  }



  static String randomId() {
    return "${Math.round(Math.random() * 100000)}"
  }
  
  
  static String jsSafeString(String inText) {
    def cleanedString = inText.replaceAll(/\s+/, " ")  //replace all internal whitespace
    cleanedString = cleanedString.replaceAll(/(^\s)|(\s$)/, "") // trim whitespace
    cleanedString = cleanedString.replaceAll(/\'/, /\\\'/)  // escape single quotes
    return cleanedString
  }  


}
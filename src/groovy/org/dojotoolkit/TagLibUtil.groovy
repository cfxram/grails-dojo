package org.dojotoolkit

import grails.converters.JSON

class TagLibUtil {

  // these are mostly HTML5 attributes, minus the ones that don't really make sense for dijits
  static final String[] CORE_ATTRIBS = ["accesskey", "class", "dir", "hidden", "id", "lang", "spellcheck", "style", "tabindex", "title"]
  static final String DATA_DOJO_TYPE = "data-dojo-type"
  static final String DATA_DOJO_PROPS = "data-dojo-props"
  
  /**
   * Gets all HTML5 attributes in a string
   * 
   * @param params Params passed to taglib
   * @return Attributes to place in HTML tag
   */
  static String htmlProperties(params) {
    def paramString = new StringBuilder()
    params.each {k, v ->
		if(isHtmlAttribute(k)){
			paramString << " ${k}=\"${v.encodeAsHTML()}\""
		}
    }
    paramString.toString()
  }
  
  /**
   * Gets all non-HTML5 attributes in a JSON-style string
   * 
   * @param params Params passed to taglib
   * @return JSON-style string (without curly-brace start and end)
   */
  static String dataDojoProps(params){
	  if(!params){
		  return ""
	  }
	  def json = (params.findAll{!isHtmlAttribute(it.key)} as JSON).toString()
	  json.length() > 2 ? json.substring(1, json.length() - 1) : "" +
	  	(params[DATA_DOJO_PROPS] ? "," + params[DATA_DOJO_PROPS] : "")
  }

  static protected boolean isHtmlAttribute(String attribute){
	  attribute in CORE_ATTRIBS ||
	  	(attribute.startsWith("data-") && DATA_DOJO_TYPE != attribute && DATA_DOJO_PROPS != attribute)
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
package org.dojotoolkit

import org.apache.commons.lang.StringUtils;

import grails.converters.JSON

class TagLibUtil {

  // these are mostly HTML5 attributes, minus the ones that don't really make sense for dijits
  static final String[] CORE_ATTRIBS = [
	  "accesskey", "class", "dir", "hidden", "id", "lang", "spellcheck", "style", "tabindex", "title",
	  "onabort", "onblur", "onchange", "onclick", "ondblclick",
	  "onerror", "onfocus", "oninput", "oninvalid",
	  "onkeydown", "onkeypress", "onkeyup", "onload",
	  "onmousedown", "onmousemove", "onmouseout", "onmouseover", "onmouseup", "onmousewheel",
	  "onreadystatechange", "onreset", "onscroll", "onselect", "onshow", "onsubmit"
  ]
  static final String DATA_DOJO_TYPE = "data-dojo-type"
  static final String DATA_DOJO_PROPS = "data-dojo-props"
  
  /**
   * Turns any value into a boolean.
   * 
   * Null is false, a boolean stays as-is, everything else is turned into a string,
   * and if it isn't {@code "false"} (case insensitive), it's {@code true}. 
   * 
   * @param value The value
   * @return The value as a boolean
   */
  static final boolean toBoolean(value){
	  if(value == null){
		  return Boolean.FALSE
	  } else if(value instanceof Boolean){
	  	  return Boolean.TRUE == value
	  } else {
	  	  return StringUtils.isBlank(value) ?
			false : !"false".equalsIgnoreCase(value.toString())
	  }
  }
  
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
	  def props = params.remove(DATA_DOJO_PROPS)
	  params.each{k, v -> if(v instanceof String && ("true".equalsIgnoreCase(v) || "false".equalsIgnoreCase(v))){
		  params[k] = "true".equalsIgnoreCase(v)
	  }}
	  def json = (params.findAll{!isHtmlAttribute(it.key)} as JSON).toString()
	  (json.length() > 2 ? json.substring(1, json.length() - 1) : "") +
	  	(props ? (json.length() > 2 ? "," : "") + props : "")
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
package org.dojotoolkit

class DojoTagLib {
  static namespace = 'dojo'
  def DOJO_HOME = "${g.resource(dir:pluginContextPath)}/js/dojo/${Dojo.version}"
  
  /**
   * Alternative to <g:javascript library="dojo"/>. This will include the dojo.js file,
   * adds the standard dojo headers., and sets the theme.
   *
   * @params attrs.require = This is a map of components to include
   * @params attrs.theme = (optional) Will include the theme if it is provided
   */
  def header = {attrs ->
    def debug = attrs.remove("debug") ?: "false"
    def parseOnLoad = attrs.remove("parseOnLoad") ?: "true"
    if(attrs?.theme){
      out << stylesheets(attrs)  
    }
    out << "<script type='text/javascript' src='${DOJO_HOME}/dojo/dojo.js' djConfig='isDebug:${debug}, parseOnLoad:${parseOnLoad}'></script>"
    if(attrs?.require){
      attrs.modules = attrs?.require
      out << require(attrs)
    }
  }

  
  /**
   * Will setup the base css and themes.User still needs to define <body class="${theme}">
   *
   * @params attrs.theme  = (Tundra), Soria, Nihilio. The theme to bring in.
   */
  def stylesheets = {attrs ->
    def theme = attrs.remove("theme") ?: "tundra"
    out << """
      <link rel="stylesheet" type="text/css" href="${DOJO_HOME}/dojo/resources/dojo.css" />
      <link rel="stylesheet" type="text/css" href="${DOJO_HOME}/dijit/themes/dijit.css" />
      <link rel="stylesheet" type="text/css" href="${DOJO_HOME}/dijit/themes/${theme}/${theme}.css" />
    """
  }

  
  /**
   * Will include dojo modules via the dojo loader.
   * @params attrs.modules = This is a map of components to include 
   */
  def require = {attrs ->
    // Add Required attributes
    if(attrs.modules){
      out << "<script type='text/javascript'>"
    }
    attrs.modules.each{
      out << "dojo.require('${it}');"
    }
    if(attrs.modules){
      out << "</script>"
    }
  }
}

package org.dojotoolkit

import grails.converters.deep.JSON


class DojoTagLib {
  static namespace = 'dojo'
  def DOJO_HOME = "${g.resource(dir: pluginContextPath)}/js/dojo/${Dojo.version}"
  def CUSTOM_DOJO = "${g.resource()}/js/dojo/${Dojo.version}-custom"

  /**
   * Returns the dojo.customBuild value from Config.groovy
   * @return
   */
  private boolean useCustomDojoJsBuild() {
    def includeCustomScripts = grailsApplication.config.dojo.use.customBuild.js ?: false 
    return includeCustomScripts
  }

  /**
   * Returns the dojo.customBuild value from Config.groovy
   * @return
   */
  private boolean useCustomDojoCssBuild() {
    def includeCustomCss = grailsApplication.config.dojo.use.customBuild.css ?: false 
    return includeCustomCss
  }


  /**
   * Reads the dojo.profile.js file and converts into a grails object
   * @return JSONObject
   */
  private Map getDojoCustomProfile() {
    def jsonString = grailsApplication.config.dojo.profile
    def jsonObj = JSON.parse("{${jsonString}}");
    return jsonObj.dependencies
  }

  /**
   * Will return the dojo home based on if it has a custom build
   * @return String
   */
  def dojoHome() {
    if (useCustomDojoJsBuild()) {
      return CUSTOM_DOJO
    }
    else {
      return DOJO_HOME
    }
  }

  /**
   * Will output custom js scripts that were created as part of the custom dojo build.
   * Will check if dojo.include.custombuild.inHeader is true.
   */
  def customDojoScripts = {
    if (useCustomDojoJsBuild()) {
      def dependencies = getDojoCustomProfile()
      dependencies.layers.each {
        out << "<script type='text/javascript' src='${dojoHome()}/dojo/${it.name}'></script>"
      }
    }
  }

  /**
   * Alternative to <g:javascript library="dojo"/>. This will include the dojo.js file,
   * adds the standard dojo headers., and sets the theme.
   *
   * @params attrs.require = This is a map of components to include
   * @params attrs.theme = (optional) Will include the theme if it is provided
   * @params attrs.includeCustomBuild = (true) Will include the js files(layers) defined in dojo.profile.js.
   *                                    It is recommended you leave this to true. Setting to false, you will
   *                                    have to manually include the generated files yourself but it give more
   *                                    fine grain control on when the files get included. 
   */
  def header = {attrs ->
    def debug = attrs.remove("debug") ?: "false"
    def parseOnLoad = attrs.remove("parseOnLoad") ?: "true"
    def includeCustomBuild = attrs.remove("includeCustomBuild") ?: "true"
    
    if (attrs?.theme) {
      out << stylesheets(attrs)
    }

    out << """
      <script type='text/javascript' src='${dojoHome()}/dojo/dojo.js' djConfig='isDebug:${debug}, parseOnLoad:${parseOnLoad}'></script>
      <script type='text/javascript' src='${dojoHome()}/dojoui/dojoui.js'></script>
      <script type="text/javascript">dojo.registerModulePath("dojoui", "../dojoui")</script>
    """

    // if custom build then include released js files
    if(includeCustomBuild == "true"){
      out << customDojoScripts()
    }
    
    if (attrs?.modules) {
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
    if(useCustomDojoCssBuild()){
        out << """
            <link rel="stylesheet" type="text/css" href="${dojoHome()}/css/custom-dojo.css" />
            <!--[if lt IE 8]>
              <link rel="stylesheet" type="text/css" href="${dojoHome()}/dojoui/resources/css/dojo-ui-ie.css" />
            <![endif]-->            
        """        
    }
    else{
        out << """
          <link rel="stylesheet" type="text/css" href="${dojoHome()}/dojo/resources/dojo.css" />
          <link rel="stylesheet" type="text/css" href="${dojoHome()}/dijit/themes/dijit.css" />
          <link rel="stylesheet" type="text/css" href="${dojoHome()}/dijit/themes/${theme}/${theme}.css" />
          <link rel="stylesheet" type="text/css" href="${dojoHome()}/dojoui/resources/css/dojo-ui.css" />
          <!--[if lt IE 8]>
            <link rel="stylesheet" type="text/css" href="${dojoHome()}/dojoui/resources/css/dojo-ui-ie.css" />
          <![endif]-->          
        """
    }   
  }

  /**
   * Includes a dojo specific css file. This is used mostly for extended css files in dojox.
   * Please use <dojo:header> or <dojo:stylesheets> for the standard files.
   */
  def css = {attrs ->
    out << "<link rel='stylesheet' type='text/css' href='${dojoHome()}/${attrs?.file}'/>" 
  }


  /**
   * Will include dojo modules via the dojo loader.
   * @params attrs.modules = This is a map of components to include 
   */
  def require = {attrs ->
    // Add Required attributes
    if (attrs.modules) {
      out << "<script type='text/javascript'>"
    }
    attrs.modules.each {
      out << "dojo.require('${it}');"
    }
    if (attrs.modules) {
      out << "</script>"
    }
  }
}

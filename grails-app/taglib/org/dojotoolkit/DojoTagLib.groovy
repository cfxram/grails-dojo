package org.dojotoolkit

import grails.converters.deep.JSON


class DojoTagLib {
  static namespace = "dojo"

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
    def includeCustomCss = (grailsApplication?.config?.css?.size()) ?: (grailsApplication?.config?.dojo?.use?.customBuild?.css) ?: false
    return includeCustomCss
  }


  /**
   * Reads the dojo.profile.js file and converts into a grails object
   * @return JSONObject
   */
  private Map getDojoCustomProfile() {
    def jsonString = grailsApplication.config.dojo.profile
    def jsonObj = JSON.parse("{${jsonString}}");
    return jsonObj
  }

  /**
   * Will return the dojo home based on if it has a custom build
   * @return String
   */
  def dojoHome() {
    def dojoHome = "${g.resource(dir: pluginContextPath)}/js/dojo/${Dojo.version}"
    def customDojo = "${g.resource(dir:'')}/js/dojo/${Dojo.pluginVersion}-custom"
    
    if (useCustomDojoJsBuild()) {
      return customDojo
    }
    else {
      return dojoHome
    }
  }

  /**
   * Will allow other tags to get the dojo home.
   */
  def home = {
    out << dojoHome()
  }


  /**
   * Will output custom js scripts that were created as part of the custom dojo build.
   * Will check if dojo.include.custombuild.inHeader is true.
   */
  def customDojoScripts = {
    if (useCustomDojoJsBuild()) {
      def profileObj = getDojoCustomProfile()

      // For Dojo 1.7 profiles (AMD Module Based)
      // Example http://svn.dojotoolkit.org/src/util/trunk/buildscripts/profiles/amd.profile.js
      profileObj["var profile"]?.layers?.each{k,v->
        out << "<script type='text/javascript' src='${dojoHome()}/${k}.js'></script>"
      }

      // For Dojo 1.6 profiles
      // Example: http://livedocs.dojotoolkit.org/build/pre17/build#profiles
      profileObj?.dependencies?.layers?.each {
        out << "<script type='text/javascript' src='${dojoHome()}/dojo/${it.name}'></script>"
      }
    }
  }

  /**
   * Alternative to <g:javascript library="dojo"/>. This will include the dojo.js file,
   * adds the standard dojo headers., and sets the theme.
   *
   * @param attrs.require = This is a map of components to include
   * @param attrs.theme = (optional) Will include the theme if it is provided
   * @param attrs.includeCustomBuild = (true) Will include the js files(layers) defined in dojo.profile.js.
   *                                    It is recommended you leave this to true. Setting to false, you will
   *                                    have to manually include the generated files yourself but it give more
   *                                    fine grain control on when the files get included.
   *
   * @param attrs.async = Boolean (false) If true will use the AMD loader.
   * @param attrs.modules = List (optional) A list of required modules to be included. Just calles require().
   * @param attrs.modulePaths = List (optional) A list of paths to search for required modules.
   */
  def header = {attrs ->
    def debug = attrs.remove("debug") ?: "false"
    def parseOnLoad = attrs.remove("parseOnLoad") ?: "true"
    attrs.modulePaths = attrs?.modulePaths ?: [:]
    def includeCustomBuild = attrs.remove("includeCustomBuild") ?: "true"
    def showSpinner = attrs.remove("showSpinner") ?: "true"
    attrs.async = attrs?.async ?: "false"
    attrs.modules = attrs.modules ?: []

    // Add custom tags space to modulePath (Append new path to be relative to plugin's version of dojo.js
    def moduleStringList = []
    def jsRoot = "${resource()}/js"
    attrs.modulePaths?.each{k,v->
      moduleStringList.add("'${k}':'${jsRoot}/${v}'")
    }

    //Add DojoUI Module Path
    attrs.modulePaths += ["dojoui": "../dojoui"]
    moduleStringList.add("'dojoui':'../dojoui'")


    if (attrs?.theme) {
      out << stylesheets(attrs)
    }

    out << """
      <script>
        var dojoConfig = {async:${attrs?.async}, isDebug:${debug}, parseOnLoad:${parseOnLoad}, modulePaths:{ ${moduleStringList.join(',')}} };
        dojoGrailsPluginConfig = {showSpinner:${showSpinner} };
      </script>
      <script type='text/javascript' src='${dojoHome()}/dojo/dojo.js'></script>
      <script type='text/javascript' src='${dojoHome()}/dojoui/DojoGrailsSpinner.js'></script>
    """

    // if custom build then include released js files
    if(includeCustomBuild == "true"){
      out << customDojoScripts()
    }

    if (attrs.modules?.size()) {
      out << require(attrs)
    }
  }

  /**
   * Will setup the base css and themes.User still needs to define <body class="${theme}">
   *
   * @param attrs.theme  = (Tundra), Soria, Nihilio. The theme to bring in.
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
   * @param attrs.modules = This is a map of components to include
   * @param attrs.async = If true will use the Dojo AMD compatible way to bring in modules.
   */
  def require = {attrs ->
    attrs.async = attrs?.async ?: "false"
    if (attrs.modules) {
      if(attrs?.async == "true"){
        out << amdRequire(attrs)
      }
      else{
        def modules = attrs.modules.collect{"dojo.require('${it}')"}
        out << "<script type='text/javascript'>${modules.join(';')}</script>"
      }
    }
  }


  /**
   * Will bring in modules using the new amd async approach.
   * @param attrs.modules - The modules to include
   * @param attrs.modulePaths - Defines custom paths to look for those modules.
   */
  def amdRequire = {attrs->
    def modules = attrs.modules.collect{"dojo.require('${it}')"}

    out << """
      <script type='text/javascript'>
        require(['dojo/_base/kernel', 'dojo/_base/loader'],
        function(dojo){
    """
      attrs?.modulePaths?.each{k,v->
        out << " dojo.registerModulePath('${k}','${v}'); "
      }

    out << """
        ${modules.join(';')}
        });
      </script>
    """
  }




  /**
   * This will wrap a response in a text area element if a flash var is set.
   * This is set by a dojo.io.iframe call and intercepted by the BaseController.
   * The end result is that a user is able to do a file upload via an ajax call
   * inside of a ContentPane.
   *
   * Add this code as a controller filter:
      def filters = {
        parameterFilter(controller: '*', action: '*') {
          before = {
            if (params?.dojoIoIframeTransport) { flash.dojoIoIframeTransport = true }
          }
        }
      }
   *
   */
  def ajaxUploadResponse = {attrs, body ->
    if (flash?.dojoIoIframeTransport || request.getParameter("dojoIoIframeTransport")) {
      out << """<html><body><textarea status="${response.getStatus()}">${body()}</textarea></body></html>"""
      flash.remove("dojoIoIframeTransport")
    }
    else {
      out << body()
    }
  }

}

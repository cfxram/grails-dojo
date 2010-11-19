package org.dojotoolkit

class DojoTagLib {
  static namespace = 'dojo'



  /**
   * Alternative to <g:javascript library="dojo"/>. This will include the dojo.js file, and
   * add the standard dojo headers, and sets the theme.
   *
   * @params attrs.theme  = (Tundra), Soria, Nihilio. The theme to bring in.
   */
  def header = {attrs ->

    
  }


  
  /**
   * Will define a dojo.require() section. This is used to bring in the dependancies
   * for optional components
   *
   * @params attrs.modules  = This is a map of components to include
   */
  def require = {attrs ->
    
  }

}

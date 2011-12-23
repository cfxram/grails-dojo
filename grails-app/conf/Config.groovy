// configuration for plugin testing - will not be included in the plugin zip

grails.views.javascript.library="dojo"

log4j = {
    // Example of changing the log pattern for the default console
    // appender:
    //
    //appenders {
    //    console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
    //}

    error  'org.codehaus.groovy.grails.web.servlet',  //  controllers
           'org.codehaus.groovy.grails.web.pages', //  GSP
           'org.codehaus.groovy.grails.web.sitemesh', //  layouts
           'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
           'org.codehaus.groovy.grails.web.mapping', // URL mapping
           'org.codehaus.groovy.grails.commons', // core / classloading
           'org.codehaus.groovy.grails.plugins', // plugins
           'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
           'org.springframework',
           'org.hibernate',
           'net.sf.ehcache.hibernate'

    warn   'org.mortbay.log'
}


// Dojo Plugin Settings
dojo.optimize.during.build = true;
dojo.use.customBuild.js = true;
dojo.use.customBuild.css = true;
dojo.profile = """

  var profile = {
    basePath: "../../web-app/js/dojoTmp",  //relative to grails-app/conf/
    releaseDir:"release",
    layerOptimize: "shrinksafe.keepLines",

    packages: [
      {name: "dojo", location: "dojo"},
      {name: "dijit", location: "dijit"},
      {name: "dojox", location: "dojox"},
      {name: "dojoui", location: "dojoui"}
    ],

    layers: {
      "dojo/custom-dojo": {include: [
          "dijit/layout/TabContainer",
          "dijit/layout/ContentPane",
          "dojo/io/iframe",
          "dijit/Tooltip",
          "dijit/Dialog",
          "dijit/TooltipDialog",
          "dojoui/layout/ContentPane",
          "dojoui/layout/TabContainer",
          "dojoui/Bind",
          "dojoui/widget/DropDownButton",
          "dojoui/widget/DataSourceView",
          "dojoui/widget/Tree"
      ]}
    }
  };


"""
/*
dojo.oldProfile = """
dependencies = {
    layers:  [
        {
            name: "custom-dojo.js",
            dependencies: [
                "dijit.layout.TabContainer",
                "dijit.layout.ContentPane",
                "dojo.io.iframe",
                "dijit.Tooltip",
                "dijit.Dialog",
                "dijit.TooltipDialog",
                "dojoui.layout.ContentPane",
                "dojoui.layout.TabContainer",
                "dojoui.Bind",
                "dojoui.widget.DropDownButton",
                "dojoui.widget.DataSourceView",
                "dojoui.widget.Tree"
            ]
        }
    ],

    prefixes: [
        [ "dijit", "../dijit" ],
        [ "dojox", "../dojox" ],
        [ "css", "../css" ],
        [ "dojoui", "../dojoui" ]
    ],

    // This is a customization to the standard dojo build profile. Only the
    // dojo plugin build process will understand this. It will just be
    // ignored by the standard dojo build.
    css: {
        dependencies: [
            "../dojo/resources/dojo.css",
            "../dijit/themes/dijit.css",
            "../dijit/themes/tundra/tundra.css",
            "../dojox/grid/resources/tundraGrid.css",
            "../dojoui/resources/css/dojo-ui.css"
        ]
    }
}
"""
*/




environments {
  development {
    dojo.optimize.during.build = false;
    dojo.use.customBuild.js = false;
    dojo.use.customBuild.css = false;
  }
}

grails.views.default.codec="none" // none, html, base64
grails.views.gsp.encoding="UTF-8"

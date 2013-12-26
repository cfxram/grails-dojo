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

/**
 * Use for Dojo 1.7 and up...
 * Only Dojo Grails Plugin understand this. It will create a single css file from these
 * files. Each of these files will be @import'ed so that the dojo build process will
 * detect and combine them.
 */
dojo.css = """
  dependencies: [
    "../dojo/resources/dojo.css",
    "../dijit/themes/dijit.css",
    "../dijit/themes/tundra/tundra.css",
    "../dojoui/resources/css/dojo-ui.css",
    "../dojox/grid/resources/Grid.css",
    "../dojox/grid/resources/tundraGrid.css"
  ]
"""

/**
 * Use for Dojo 1.7 and up...
 * New profile file format. See the livedocs for more information:
 * http://livedocs.dojotoolkit.org/build/index
 */
dojo.profile = """
  var profile = {
    releaseDir:"release",
    layerOptimize: "shrinksafe.keepLines",
    cssOptimize: "comments.keepLines",

    packages: [
      {name: "css", location: "css"},
      {name: "dojo", location: "dojo"},
      {name: "dijit", location: "dijit"},
      {name: "dojox", location: "dojox"},
      {name: "dojoui", location: "dojoui"}
    ],

    layers: {
      "dojo/dojo-all": {
        exclude: [ "dojo/_firebug" ],
        include: [

        // Dojo
        "dojo/main",
        "dojo/require",
        "dojo/NodeList-traverse",
        "dojo/dnd/Moveable",
        "dojo/dnd/TimedMoveable",
        "dojo/dnd/AutoSource",
        "dojo/dnd/Target",
        "dojo/io/iframe",
        "dojo/data/ItemFileReadStore",
        "dojo/data/ItemFileWriteStore",
        "dojo/cldr/nls/en/gregorian",
        "dojo/cldr/nls/en/number",
        "dojo/fx/Toggler",

        // Dijit
        "dijit/main",
        "dijit/_base",
        "dijit/_Widget",
        "dijit/Tooltip",
        "dijit/Dialog",
        "dijit/TooltipDialog",
        "dijit/ProgressBar",
        "dijit/Tree",
        "dijit/MenuBar",
        "dijit/Menu",
        "dijit/MenuItem",
        "dijit/PopupMenuBarItem",
        "dijit/PopupMenuItem",
        "dijit/MenuSeparator",
        "dijit/Editor",
        "dijit/_editor/plugins/TextColor",
        "dijit/_editor/plugins/ViewSource",
        "dijit/_editor/plugins/LinkDialog",
        "dijit/_editor/plugins/FontChoice",
        "dijit/layout/TabContainer",
        "dijit/layout/ContentPane",
        "dijit/form/DateTextBox",
        "dijit/form/TimeTextBox",
        "dijit/form/NumberSpinner",
        "dijit/form/ComboButton",
        "dijit/form/MultiSelect",

        // Dojox
        "dojox/main",
        "dojox/grid/DataGrid",
        "dojox/editor/plugins/PasteFromWord",

        //DojoUI
        "dojoui/Bind",
        "dojoui/DojoGrailsSpinner",
        "dojoui/layout/ContentPane",
        "dojoui/layout/TabContainer",
        "dojoui/widget/DropDownButton",
        "dojoui/widget/DropDownButtonLink",
        "dojoui/widget/ForestStoreModel",
        "dojoui/widget/DataSourceView",
        "dojoui/widget/Tree"
      ]}
    }
  };
"""


environments {
  development {
    dojo.optimize.during.build = false;
    dojo.use.customBuild.js = false;
    // dojo.use.customBuild.css = false;  /* [DEPRICATED] use dojo.profile.css  */
  }
}

grails.release.scm.enabled = false


grails.views.default.codec="none" // none, html, base64
grails.views.gsp.encoding="UTF-8"

// Uncomment and edit the following lines to start using Grails encoding & escaping improvements

/* remove this line 
// GSP settings
grails {
    views {
        gsp {
            encoding = 'UTF-8'
            htmlcodec = 'xml' // use xml escaping instead of HTML4 escaping
            codecs {
                expression = 'html' // escapes values inside null
                scriptlet = 'none' // escapes output from scriptlets in GSPs
                taglib = 'none' // escapes output from taglibs
                staticparts = 'none' // escapes output from static template parts
            }
        }
        // escapes all not-encoded output at final stage of outputting
        filteringCodecForContentType {
            //'text/html' = 'html'
        }
    }
}
remove this line */

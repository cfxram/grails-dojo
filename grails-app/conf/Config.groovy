grails.views.javascript.library="dojo"
grails.views.default.codec="none" // none, html, base64
grails.views.gsp.encoding="UTF-8"
log4j = {
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



// Dojo Plugin Properties
dojo.optimize.during.build = true;
dojo.use.customBuild.js = true;
dojo.use.customBuild.css = true;
dojo.profile = """
dependencies = {
    layers:  [
        {
            name: "dojo-all.js",
            dependencies: [
                "dijit.layout.TabContainer",
                "dijit.layout.ContentPane",
                "dojo.io.iframe",
                "dijit.Tooltip",
                "dijit.Dialog",
                "dijit.ProgressBar",
                "dijit.TooltipDialog",
                "dojo.data.ItemFileReadStore",
                "dojo.data.ItemFileWriteStore",
                "dojox.grid.DataGrid",
                "dijit.Tree",
                "dijit.MenuBar",
                "dijit.PopupMenuBarItem",
                "dijit.Menu",
                "dijit.MenuItem",
                "dijit.PopupMenuItem",
                "dijit.form.DateTextBox",
                "dijit.form.TimeTextBox",
                "dijit.form.NumberSpinner",
                "dojo.fx",
                "dojo.NodeList-traverse",
                "dijit.TooltipDialog",
                "dijit.Editor",
                "dijit._editor.plugins.LinkDialog",
                "dijit._editor.plugins.FontChoice",
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
        [ "css", "../css"],
        [ "dojoui", "../dojoui" ]

    ],
    css: {
        dependencies: [
            "../dojo/resources/dojo.css",
            "../dijit/themes/dijit.css",
            "../dijit/themes/tundra/tundra.css",
            "../dojoui/resources/css/dojo-ui.css",
            "../dojox/grid/resources/Grid.css",
            "../dojox/grid/resources/tundraGrid.css"
        ]
    }

}
"""

environments {
  development {
    dojo.optimize.during.build = false;
    dojo.use.customBuild.js = false;
    dojo.use.customBuild.css = false;
  }
}


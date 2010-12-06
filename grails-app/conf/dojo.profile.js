dependencies = {
    layers:  [
        {
            name: "custom-dojo.js",     // Will be included via <dojo:header>
            dependencies: [
                "dijit.layout.TabContainer",
                "dijit.layout.ContentPane",
                "dojo.io.iframe",
                "dijit.Tooltip",
                "dijit.Dialog",
                "dijit.TooltipDialog",
                "dojoui.dojoui",
                "dojoui.layout.ContentPane",
                "dojoui.dojouiGrid",
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

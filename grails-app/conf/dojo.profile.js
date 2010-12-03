dependencies = {
    layers:  [
        {
            name: "custom-dojo.js",     // Will include this file via <dojo:header>
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
        [ "dojoui", "../../dojoui" ]
    ]
};
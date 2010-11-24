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
                "dijit.TooltipDialog"
            ]
        }
    ],

    prefixes: [
        [ "dijit", "../dijit" ],
        [ "dojox", "../dojox" ]
    ]
};
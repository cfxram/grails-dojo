package org.dojotoolkit


import org.dojotoolkit.TagLibUtil as Util

/**
 * This will create tabs and their related components.
 */
class TabTagLib {
    static namespace = 'dojo'
    /**
     * This will bring in all the resources required by the tab and its related components.
     * 
     * @deprecated Dojo now automatically imports required classes for parsed widgets from data-dojo-type.  
     */
    @Deprecated
    def tabResources = {attrs, body ->
        out << dojo.require(modules: ['dijit/layout/TabContainer'])
    }
    /**
     * This will create a tab container item which can then be filled with content pane. <br/>
     * NOTE:The tab container must have a height or
     * min-height attribute otherwise it will not display at all.  This can be included as height or in the style attribute. <br/>
     *
     * Advanced note: Any attributes that are specified on this tag that match an
     * HTML5 attribute will be used directly on the tag. Any other attributes will be passed
     * as settings to the Dojo Widget.
     * 
     * @attr id if an id is not passed one will be generated for you.
     */
    def tabContainer = {attrs, body ->
        def id = attrs.remove("id") ?: "dojo_ui_dialog${Util.randomId()}"
        out << """
            <div ${Util.htmlProperties(attrs)} data-dojo-type="dijit/layout/TabContainer" data-dojo-props="${Util.dataDojoProps(attrs).encodeAsHTML()}">
                ${body()}
            </div>
        """
    }
}

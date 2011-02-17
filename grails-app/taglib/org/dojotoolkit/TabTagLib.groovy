package org.dojotoolkit


import org.dojotoolkit.TagLibUtil as Util

/**
 * This will create tabs and their related components.
 */
class TabTagLib {
    static namespace = 'dojo'
    /**
     * This will bring in all the resources required by the tab and its related components.
     */
    def tabResources = {attrs, body ->
        out << dojo.require(modules: ['dijit.layout.TabContainer'])
    }
    /**
     * This will create a tab container item which can then be filled with content pane. <br/>
     * NOTE:The tab container must have a height or
     * min-height attribute otherwise it will not display at all.  This can be included as height or in the style attribute. <br/>
     * @param code - This can be passed in instead of the title attribute and will use the localization system to locale the code
     * into a localized title for the dialog.
     * @param id - if an id is not passed one will be generated for you.
     */
    def tabContainer = {attrs, body ->
        def id = attrs.remove("id") ?: "dojo_ui_dialog${Util.randomId()}"
        out << """
            <div dojoType="dijit.layout.TabContainer" ${Util.htmlProperties(attrs)}>
                ${body()}
            </div>
        """
    }
}

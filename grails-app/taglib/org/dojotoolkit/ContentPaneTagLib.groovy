package org.dojotoolkit

import org.dojotoolkit.TagLibUtil as Util

/**
 * Creates content panes for display either in the page or within other items like a tab container.
 */
class ContentPaneTagLib {
    static namespace = 'dojo'
    /**
     * This will bring in all the resources required by the tab and its related components.
     */
    def contentPaneResources = {attrs, body ->
        out << dojo.require(modules: ['dijit.layout.ContentPane'])
    }
    /**
     * This will create a content pane item.
     * @param code - This can be passed in instead of the title attribute and will use the localization system to locale the code
     * into a localized title for the dialog.
     * @param id - if an id is not passed one will be generated for you.
     * @param controller - The controller to use to render the body of the dialog
     * @param action - The action within the controller that will render the body of the dialog
     * @param params - Any parameters that must be passed to the action to complete the rendering of the body of the dialog.
     * @param refreshOnShow - This will be set to true if not passed in and insures that panels linked to controllers and actions
     * are kept current.
     */
    def contentPane = {attrs, body ->
        def id = attrs.remove("id") ?: "dojo_ui_dialog${Util.randomId()}"
        def refreshOnShow = attrs.remove("refreshOnShow") ?: 'true'
        def title = (attrs?.code?.length()) ? message(code: attrs.remove('code')) : attrs.remove('title')
        if (attrs?.controller || attrs?.action) {
            def href = createLink(attrs)
            attrs.remove('controller')
            attrs.remove('action')
            attrs.remove('params')
            out << """  <div dojoType="dijit.layout.ContentPane" title="${title}" href="${href}" preventCache="true" refreshOnShow="${refreshOnShow}" ${Util.htmlProperties(attrs)}>
                          ${body()}
                       </div>
                   """
        }
        else {
            out << """  <div dojoType="dijit.layout.ContentPane" title="${title}" refreshOnShow="${refreshOnShow}" preventCache="true" ${Util.htmlProperties(attrs)}>
                          ${body()}
                        </div>
                   """
        }
    }
}

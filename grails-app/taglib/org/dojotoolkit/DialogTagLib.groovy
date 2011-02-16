package org.dojotoolkit

import org.dojotoolkit.TagLibUtil as Util
/**
 * This class provides support for the dialog function within the Dojo toolkit.  It allows for dialogs that
 * can be populated with the current body or can build the body from a template rendered by a controller action.
 */
class DialogTagLib {
    static namespace = 'dojo'
    /**
     * This will bring in all the resources required by the dialog tag.
     */
    def dialogResources = {attrs, body ->
        out << dojo.require(modules: ['dijit.Dialog'])
    }
    /**
     * This will create a dialog with a body or a body created by a template rendered within a action.  All the normal
     * dojo dialog tags are supported in addition to the following:
     * @param controller - The controller to use to render the body of the dialog
     * @param action - The action within the controller that will render the body of the dialog
     * @param params - Any parameters that must be passed to the action to complete the rendering of the body of the dialog.
     * @param code - This can be passed in instead of the title attibute and will use the localization system to locale the code
     * into a localized title for the dialog.
     * @param id - if an id is not passed one will be generated for you.
     */

    def dialog = {attrs, body ->
        def id = attrs.remove("id") ?: "dojo_ui_dialog${Util.randomId()}"
        def title = (attrs?.code?.length()) ? message(code: attrs.remove('code')) : attrs.remove('title')
        if (attrs?.controller || attrs?.action) {
            def href = createLink(attrs)
            attrs.remove('controller')
            attrs.remove('action')
            attrs.remove('params')
            out << """ <div dojoType="dijit.Dialog" href=${href} id="${id}" title="${title}" ${Util.htmlProperties(attrs)}></div> """
        }
        else {
            out << """ <div dojoType="dijit.Dialog" id="${id}" title="${title}" ${Util.htmlProperties(attrs)}>${body()}</div> """
        }
    }
    /**
     * This will generate a link to close the dialog indicated by the dialogId. it will take all the normal dojo attributes and
     * in addition support for localization.
     * @param code - This will be render to a localized string and used as the label for the link.
     * @param dialogId - The id of the dialog to close via this link
     * @param id - The if of the link that is generated and if one is not passed in one will be generated.
     */
    def closeDialogButton = {attrs, body ->
        // Remove the onclick so we can place it at the correct spot
        def onclick = attrs?.onclick ?: ''
        def label = (attrs?.code?.length()) ? message(code: attrs.remove('code')) : attrs.remove('label')
        def dialogId = attrs.remove('dialogId')// id of the dialog to close
        def id = attrs.remove("id") ?: "dojo_ui_dialog_close${Util.randomId()}"
        out << """ <a target="_self" id="${id}" ${Util.htmlProperties(attrs)} onclick="dijit.byId('${dialogId}').hide(); ${onclick}">${label}</a> """
    }
    /**
     * This will generate a link to open a dialog.  It will support all the normal dojo and html attributes in addition to.
     * @param dialogId - The id of the dialog to close via this link
     * @param id - The if of the link that is generated and if one is not passed in one will be generated.
     */
    def openDialog = {attrs, body ->
        def dialogId = attrs.remove('dialogId')// id of the dialog to open
        def id = attrs.remove("id") ?: "dojo_ui_dialog_open${Util.randomId()}"
        out << """ <a href="#" id="${id}" ${Util.htmlProperties(attrs)} onclick="dijit.byId('${dialogId}').show()">${body()}</a> """
    }
}
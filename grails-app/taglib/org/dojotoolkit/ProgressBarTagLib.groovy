package org.dojotoolkit

import org.dojotoolkit.TagLibUtil as Util

/**
 * Creates a progress bar for display of progress.
 */
class ProgressBarTagLib {

    static namespace = 'dojo'

    /**
     * Bring in all the resources required by the progressBar tag.
     */
    def progressBarResources = {attrs ->
        out << dojo.require(modules: ['dijit/ProgressBar'])
    }

    /**
     * Defines a progress bar object.  It supports all the dojo attributes.
     * @param id - The unique id for this object and one will be supplied if not provided
     * @param code - This can be passed in instead of the title attribute and will use the localization system to locale the code
     * into a localized title for the dialog.
     */
    def progressBar = {attrs ->
        def id = attrs.remove("id") ?: "dojo_ui_dialog${Util.randomId()}"
        def title = (attrs?.code?.length()) ? message(code: attrs.remove('code')) : attrs.remove('title')
        out << """ <div data-dojo-type="dijit.ProgressBar" id="${id}" title="${title}" ${Util.htmlProperties(attrs)}></div> """
    }
}

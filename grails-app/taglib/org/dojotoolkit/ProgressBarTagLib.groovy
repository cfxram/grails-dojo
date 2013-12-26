package org.dojotoolkit

import org.dojotoolkit.TagLibUtil as Util

/**
 * This will create a progress bar for display of progress.
 */
class ProgressBarTagLib {
    static namespace = 'dojo'
    /**
     * This will bring in all the resources required by the progressBar tag.
     * 
     * @deprecated Dojo now automatically imports required classes for parsed widgets from data-dojo-type.  
     */
    @Deprecated
    def progressBarResources = {attrs, body ->
        out << dojo.require(modules: ['dijit/ProgressBar'])
    }
    /**
     * This will define a progress bar object.  It supports all the dojo attributes.
     * 
     * Advanced note: Any attributes that are specified on this tag that match an
     * HTML5 attribute will be used directly on the tag. Any other attributes will be passed
     * as settings to the Dojo Widget.
     * 
     * @attr id - The unique id for this object and one will be supplied if not provided
     * @attr title The tite to be used for the widget
     * @attr code - This can be passed in instead of the title attribute and will use the localization system to locale the code
     * into a localized title for the dialog.
     */
    def progressBar = {attrs, body ->
        def id = attrs.remove("id") ?: "dojo_ui_dialog${Util.randomId()}"
        def title = (attrs?.code?.length()) ? message(code: attrs.remove('code')) : attrs.remove('title')
        out << """ <div id="${id}" title="${title}" ${Util.htmlProperties(attrs)} data-dojo-type="dijit/ProgressBar" data-dojo-props="${Util.dataDojoProps(attrs).encodeAsHTML()}"></div> """
    }
}

package org.dojotoolkit

import org.dojotoolkit.TagLibUtil as Util
/**
 * This will support creating menus of many kinds.  It supports the bar type menu as well slide out menus and right click
 * menus.
 */
class MenuTagLib {
    static namespace = 'dojo'
    /**
     * This will bring in all the resources required by the menu tag.
     */
    def menuResources = {attrs, body ->
        out << dojo.require(modules: ['dijit.Menu', 'dijit.MenuItem', 'dijit.MenuBar', 'dijit.MenuBarItem', 'dijit.MenuSeparator', 'dijit.PopupMenuBarItem', 'dijit.PopupMenuItem'])
    }
    /**
     * This will create the base menu item either a menu bar or a popup menu/right click(context) or sidenav.  The menu is then filled with
     * other menu items and embeded popups, etc.
     * @param id - The unique id for this item or one will be generated.
     * @param type bar, popup, barpopup, context , sidenav
     * @param code - If the menu label is to be localized use code rather than label.
     */
    def menu = {attrs, body ->
        def id = attrs.remove("id") ?: "dojo_menuItem_${Util.randomId()}"
        def type = attrs.remove("type") ?: 'bar'  // bar || popup || barpopup || context || sidenav
        def label = (attrs?.code?.length()) ? message(code: attrs.remove('code')) : attrs.remove('label')
        if (type == 'bar') {
            out << """ <div dojoType="dijit.MenuBar" id="${id}" ${Util.htmlProperties(attrs)}>${body()}</div> """
        }
        else if (type == 'barpopup') {
            out << """ <div dojoType="dijit.PopupMenuBarItem" id="${id}" ${Util.htmlProperties(attrs)} ><span>${label}</span>
                    <div dojoType="dijit.Menu">
                        ${body()}
                    </div>
                </div>
            """
        }
        else if (type == 'popup') {
            out << """ <div dojoType="dijit.PopupMenuItem" id="${id}" ${Util.htmlProperties(attrs)}>
                          <span>${label}</span>
                          <div dojoType="dijit.Menu">
                              ${body()}
                          </div>
                       </div>
            """
        }
        else if (type == 'sidenav') {
            out << """ <div dojoType="dijit.Menu" id="${id}" ${Util.htmlProperties(attrs)}>
                          ${body()}
                       </div>
            """
        }
        else if (type == 'context') {
            // right click must remain hidden at first
            def style = attrs.remove('style') ?: ''
            style = """ style="${attrs.style}; display:none;" """
            out << """ <div dojoType="dijit.Menu" id="${id}" style="${style}" ${Util.htmlProperties(attrs)} contextMenuForWindow="true">
                          ${body()}
                       </div>
            """
        }
    }
    /**
     * This will create a menu separator object.
     */
    def menuSeparator = {attrs, body ->
        out << """ <div dojoType="dijit.MenuSeparator" ${Util.htmlProperties(attrs)}></div> """
    }

    /**
     * This will create an item within another menu structure.  This must be contained inside an existing menu structure
     * as defined in the menu tag but this helps you nest items within the base menu item.  This item can be a menu item
     * or another bar within the menu tag.  This item supports all the documented dojo parameters as well as the following:
     * @param type - The type of item to create either item, bar, popup or popupBar
     * @patam controller - The controller to reference from this menu item  (The controller action params will replace any href passed in)
     * @param action - The action within the controller to reference from this menuItem
     * @param params - Any parameters that go with the controller and action.
     * @param id - The id of this item
     * @param onClick - Any actions you want to perform before the menu is migrated to its destination
     * @param code - If the menu label is to be localized use code rather than label.
     */
    def menuItem = {attrs, body ->
        def id = attrs.remove("id") ?: "dojo_menuItem_${Util.randomId()}"
        def type = attrs.remove("type") ?: 'item'  // bar || item
        def label = (attrs?.code?.length()) ? message(code: attrs.remove('code')) : attrs.remove('label')
        def href = attrs.remove("href") ?: ''
        // If there is an action or controller replace the href with this
        if (attrs?.controller || attrs?.action) {
            href = createLink(attrs)
            attrs.remove('controller')
            attrs.remove('action')
            attrs.remove('params')
        }
        // If onClicks were passed in the prepend them to the href action
        def onClick = attrs.onClick ? "onclick=\"${attrs.remove('onClick')}\"" : ''
        if (!onClick && href) {
            onClick = "onclick=\"window.location.href = \"${href}\";\""
        }
        if (type == 'bar') {
            out << """ <div dojoType="dijit.MenuBarItem" id="${id}" ${onClick} label="${label}" ${Util.htmlProperties(attrs)}></div> """
        } else if (type == 'item') {
            out << """<div dojoType="dijit.MenuItem" id="${id}" ${onClick} label="${label}" ${Util.htmlProperties(attrs)}>
${body()}
</div>"""
        } else if (type == 'popup') {
            out << """<div dojoType="dijit.PopupMenuItem" label="${label}" ${onClick} ${Util.htmlProperties(attrs)}>
<div dojoType="dijit.Menu" id="${id}_submenu">
${body()}
</div></div>"""
	} else if (type =='popupBar') {
            out << """<div dojoType="dijit.PopupMenuBarItem" label="${label}" ${onClick} ${Util.htmlProperties(attrs)}>
<div dojoType="dijit.Menu" id="${id}_submenu">
${body()}
</div></div>"""
	}
    }
}

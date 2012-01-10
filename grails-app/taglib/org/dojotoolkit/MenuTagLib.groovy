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
          // This prevents the menu from flickering by hidding it before it renders.
          attrs.style = (attrs?.style) ? "${attrs.style}; display:none;" : "display:none;"
          out << """
            <div dojoType="dijit.MenuBar" id="${id}" ${Util.htmlProperties(attrs)}>
              <script type="dojo/connect" method="postCreate">
                  dojo.style(this.domNode,'display','block');
              </script>
              ${body()}
            </div>
          """
        }
        else if (type == 'barpopup') {
          out << """
            <div dojoType="dijit.PopupMenuBarItem" id="${id}" ${Util.htmlProperties(attrs)}>
              <span>${label}</span>
              <div dojoType="dijit.Menu">
                  ${body()}
              </div>
            </div>
          """
        }
        else if (type == 'popup') {
          out << """
            <div dojoType="dijit.PopupMenuItem" id="${id}" ${Util.htmlProperties(attrs)}>
              <span>${label}</span>
              <div dojoType="dijit.Menu">
                ${body()}
              </div>
            </div>
          """
        }
        else if (type == 'sidenav') {
          out << """
            <div dojoType="dijit.Menu" id="${id}" ${Util.htmlProperties(attrs)}>
                ${body()}
            </div>
          """
        }
        else if (type == 'context') {
          // right click must remain hidden at first
          def style = attrs.remove('style') ?: ''
          style = """ style="${attrs.style}; display:none;" """
          out << """
            <div dojoType="dijit.Menu" id="${id}" style="${style}" ${Util.htmlProperties(attrs)} contextMenuForWindow="true">
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
     * @param name - The id of this item
     * @param onclick - Any actions you want to perform before the menu is migrated to its destination
     * @param label - You may specify the displayed label here or in param.code or specify it as text in-between the tags.
     * @param iconClass - The css class to use to display an icon.
     * @param code - If the menu label is to be localized use code rather than label.
     */
    def menuItem = {attrs, body ->
      def id = attrs.remove("id") ?: attrs.remove("name") ?: "dojo_menuItem_${Util.randomId()}"
      def type = attrs.remove("type") ?: 'item'  // bar || item
      attrs.label = attrs?.label ?: attrs.remove("code") ?: ""
      attrs.onclick = attrs?.onclick ?: ""
      attrs.iconClass = attrs?.iconClass ?: ""

      if(attrs?.controller || attrs?.action){
        attrs.onclick += """ document.location.href='${createLink(attrs)}'; """
        attrs.remove('controller')
        attrs.remove('action')
        attrs.remove('params')
        attrs.remove('id')
      }

      // Combine Label and Body together.
      def label = message(code: attrs.remove("label"))
      if(body().size()){
        label += body()
      }

      if (type == 'bar') {
        out << """ <div dojoType="dijit.MenuBarItem" id="${id}" ${Util.htmlProperties(attrs)}>${label}</div> """
      }

      else if (type == 'item') {
        out << """<div dojoType="dijit.MenuItem" id="${id}" ${Util.htmlProperties(attrs)}>${label}</div>"""
      }

      else if (type == 'popup') {
        out << """
          <div dojoType="dijit.PopupMenuItem" ${Util.htmlProperties(attrs)}>
          <div dojoType="dijit.Menu" id="${id}_submenu">${label}</div></div>
        """
      }

      else if (type =='popupBar') {
        out << """
          <div dojoType="dijit.PopupMenuBarItem" ${onClick} ${Util.htmlProperties(attrs)}>
          <div dojoType="dijit.Menu" id="${id}_submenu">${label}</div></div>
        """
      }
    }
}

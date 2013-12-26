package org.dojotoolkit
import org.springframework.web.servlet.support.RequestContextUtils

class SortableColumnTagLib {
  static namespace = 'dojo'

  /**
   * Copied from grails code source code.
   * This was customized to change all links to remoteLinks.
   * 
   * @attr property REQUIRED domain property to sort
   * @attr update REQUIRED ID of region to replace
   * @attr title Must specify either title or titleKey
   * @attr titleKey Must specify either title or titleKey 
   */
  def sortableColumn = { attrs ->
    def writer = out
    if (!attrs.property) {
      throwTagError("Tag [sortableColumn] is missing required attribute [property]")
    }
    if (!attrs.title && !attrs.titleKey) {
      throwTagError("Tag [sortableColumn] is missing required attribute [title] or [titleKey]")
    }
    if (!attrs.update) {
      throwTagError("Tag [dojo:sortableColumn] is missing required attribute [update]")
    }

    def property = attrs.remove("property")
    def action = attrs.action ? attrs.remove("action") : (actionName ?: "list")

    def defaultOrder = attrs.remove("defaultOrder")
    if (defaultOrder != "desc") defaultOrder = "asc"

    // current sorting property and order
    def sort = params.sort
    def order = params.order


    // add sorting property and params to link params
    def linkParams = [:]
    if (params.id) linkParams.put("id", params.id)
    if (attrs.params) linkParams.putAll(attrs.remove("params"))
    linkParams.sort = property

    // determine and add sorting order for this column to link params
    attrs.class = (attrs.class ? "${attrs.class} sortable" : "sortable")
    if (property == sort) {
      attrs.class = attrs.class + " sorted " + order
      if (order == "asc") {
        linkParams.order = "desc"
      }
      else {
        linkParams.order = "asc"
      }
    }
    else {
      linkParams.order = defaultOrder
    }


    // determine column title
    def title = attrs.remove("title")
    def titleKey = attrs.remove("titleKey")
    if (titleKey) {
      if (!title) title = titleKey
      def messageSource = grailsAttributes.messageSource
      def locale = RequestContextUtils.getLocale(request)
      title = messageSource.getMessage(titleKey, null, title, locale)
    }

    attrs.action = action;
    attrs.params = linkParams;

    writer << "<th "
    // process remaining attributes
    attrs.each { k, v ->
      writer << "${k}=\"${v.encodeAsHTML()}\" "
    }
    writer << ">${remoteLink(attrs) { title }}</th>"
  }
}

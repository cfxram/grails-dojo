package org.dojotoolkit
import org.springframework.web.servlet.support.RequestContextUtils

class PaginateTagLib {
  static namespace = 'dojo'

  /**
   * Copied from grails code source code.
   * This was customized to change all links to remoteLinks.
   */
  def paginate = { attrs ->
    def writer = out
    if (attrs.total == null) {
      throwTagError("Tag [paginate] is missing required attribute [total]")
    }
    def messageSource = grailsAttributes.messageSource
    def locale = RequestContextUtils.getLocale(request)

    def total = attrs.int('total') ?: 0
    def action = (attrs.action ? attrs.action : (params.action ? params.action : "list"))
    def offset = params.int('offset') ?: 0
    def max = params.int('max')
    def maxsteps = (attrs.int('maxsteps') ?: 10)

    if (!offset) offset = (attrs.int('offset') ?: 0)
    if (!max) max = (attrs.int('max') ?: 10)

    def linkParams = [:]
    if (attrs.params) linkParams.putAll(attrs.params)
    linkParams.offset = offset - max
    linkParams.max = max
    if (params.sort) linkParams.sort = params.sort
    if (params.order) linkParams.order = params.order

    def linkTagAttrs = [action: action]
    if (attrs.controller) {
      linkTagAttrs.controller = attrs.controller
    }
    if (attrs.id != null) {
      linkTagAttrs.id = attrs.id
    }
    linkTagAttrs.params = linkParams

    // Add remoteLink specific attrs. (and formName)
    ['method', 'sync', 'onSuccess', 'onFailure', 'onLoading', 'onLoaded', 'onComplete', 'preventCache', 'update', 'formName'].each {
      if (attrs[it]) {
        linkTagAttrs[it] = attrs[it];
      }
    }

    // determine paging variables
    def steps = maxsteps > 0
    int currentstep = (offset / max) + 1
    int firststep = 1
    int laststep = Math.round(Math.ceil(total / max))

    // display previous link when not on firststep
    if (currentstep > firststep) {
      linkTagAttrs.class = 'prevLink'
      linkParams.offset = offset - max
      writer << remoteLink(linkTagAttrs.clone()) {
        (attrs.prev ? attrs.prev : messageSource.getMessage('paginate.prev', null, messageSource.getMessage('default.paginate.prev', null, 'Previous', locale), locale))
      }
    }

    // display steps when steps are enabled and laststep is not firststep
    if (steps && laststep > firststep) {
      linkTagAttrs.class = 'step'

      // determine begin and endstep paging variables
      int beginstep = currentstep - Math.round(maxsteps / 2) + (maxsteps % 2)
      int endstep = currentstep + Math.round(maxsteps / 2) - 1

      if (beginstep < firststep) {
        beginstep = firststep
        endstep = maxsteps
      }
      if (endstep > laststep) {
        beginstep = laststep - maxsteps + 1
        if (beginstep < firststep) {
          beginstep = firststep
        }
        endstep = laststep
      }

      // display firststep link when beginstep is not firststep
      if (beginstep > firststep) {
        linkParams.offset = 0
        writer << remoteLink(linkTagAttrs.clone()) {firststep.toString()}
        writer << '<span class="step">..</span>'
      }

      // display paginate steps
      (beginstep..endstep).each { i ->
        if (currentstep == i) {
          writer << "<span class=\"currentStep\">${i}</span>"
        }
        else {
          linkParams.offset = (i - 1) * max
          writer << remoteLink(linkTagAttrs.clone()) {i.toString()}
        }
      }

      // display laststep link when endstep is not laststep
      if (endstep < laststep) {
        writer << '<span class="step">..</span>'
        linkParams.offset = (laststep - 1) * max
        writer << remoteLink(linkTagAttrs.clone()) { laststep.toString() }
      }
    }

    // display next link when not on laststep
    if (currentstep < laststep) {
      linkTagAttrs.class = 'nextLink'
      linkParams.offset = offset + max
      writer << remoteLink(linkTagAttrs.clone()) {
        (attrs.next ? attrs.next : messageSource.getMessage('paginate.next', null, messageSource.getMessage('default.paginate.next', null, 'Next', locale), locale))
      }
    }
  }


  /**
   * Pagination by showing more like facebook and yammer.
   */
  def showMore = { attrs ->
    def writer = out
    if (attrs.total == null) {
      throwTagError("Tag [showMore] is missing required attribute [total]")
    }
    def messageSource = grailsAttributes.messageSource
    def locale = RequestContextUtils.getLocale(request)

    def total = attrs.int('total') ?: 0
    def action = (attrs.action ? attrs.action : (params.action ? params.action : "list"))
    def offset = params.int('offset') ?: 0
    def max = params.int('max')
    def elementId = "showMore_${attrs.update}_${TagLibUtil.randomId()}"
    attrs.position = attrs.position ?: 'last'
    attrs.onComplete = "dojo.destroy(dojo.byId('${elementId}')); ${attrs.onComplete}"

    if (!offset) offset = (attrs.int('offset') ?: 0)
    if (!max) max = (attrs.int('max') ?: 10)

    def linkParams = [:]
    if (attrs.params) linkParams.putAll(attrs.params)
    linkParams.offset = offset - max
    linkParams.max = max
    if (params.sort) linkParams.sort = params.sort
    if (params.order) linkParams.order = params.order

    def linkTagAttrs = [action: action]
    if (attrs.controller) {
      linkTagAttrs.controller = attrs.controller
    }
    if (attrs.id != null) {
      linkTagAttrs.id = attrs.id
    }
    linkTagAttrs.params = linkParams
    linkTagAttrs.elementId = elementId

    // Add remoteLink specific attrs.
    ['method', 'sync', 'onSuccess', 'onFailure', 'onLoading', 'onLoaded', 'onComplete', 'preventCache', 'update', 'formName', 'position', 'style'].each {
      if (attrs[it]) {
        linkTagAttrs[it] = attrs[it];
      }
    }

    // determine paging variables
    int currentstep = (offset / max) + 1
    int laststep = Math.round(Math.ceil(total / max))


    // display next link when not on laststep
    if (currentstep < laststep) {
      linkTagAttrs.class = "showMoreLink ${attrs.class}"
      linkParams.offset = offset + max
      writer << remoteLink(linkTagAttrs.clone()) {
        (attrs.label ? attrs.label : messageSource.getMessage('paginate.showMore', null, messageSource.getMessage('default.paginate.showMore', null, 'Show More', locale), locale))
      }
    }
  }

}

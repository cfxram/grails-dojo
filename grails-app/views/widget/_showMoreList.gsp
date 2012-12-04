<g:each var="widget" in="${widgetList}">
  <div style="border: 1px solid gray; background: yellow; margin: 5px;">
      ${widget.name}, ${widget.color}
  </div>
</g:each>

<dojo:showMore total="${total}" update="myUpdateRegion" action="showMoreListFragment" />

<g:each var="widget" in="${widgetList}">
  <div style="border: 1px solid gray; background: #ccc; margin: 5px;">
      ${widget.name}, ${widget.color}
  </div>
</g:each>

<dojo:showMore label="Show 10 inner" total="${total}" update="myUpdateRegion2" action="showInnerList" />
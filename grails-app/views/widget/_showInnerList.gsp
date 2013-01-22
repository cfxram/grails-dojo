<g:each var="widget" in="${widgetList}">
  <div style="border: 1px solid gray; background: #ccc; margin: 5px; padding:1em 0 1em 0">
      ${widget.name}, ${widget.color}
      <dojo:timerPicker style="float:right"/>
      <dojo:datePicker style="float:right"/>
      <dojo:help>Test</dojo:help>
  </div>
</g:each>

<dojo:showMore label="Show 10 inner" total="${total}" update="myUpdateRegion2" action="showInnerList" />
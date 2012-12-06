<style type="text/css">
  .myStyle {
    border: 2px solid red;
  }
</style>

<g:each var="widget" in="${widgetList}">
  <div style="border: 1px solid gray; background: yellow; margin: 5px;">
      ${widget.name}, ${widget.color}
  </div>
</g:each>

<dojo:showMore style="" class="" label="Show 10 more" total="${total}" update="myUpdateRegion" action="showMoreListFragment" />
<table border="1">
  <thead>
  <tr>
    <dojo:sortableColumn elementID="nameSort" action="listFragment" property="name" title="Name" update="[success:'myUpdateRegion',failure:'myUpdateRegion2']"
            before="beforeEvent()"
            after="after()"
            onSuccess="onSuccess()"
            onFailure="onFailure()"
            onLoading="onLoading()"
            onLoaded="onLoaded()"
            onComplete="onComplete()"
            on404="onFailure404()"
            on200="onFailure200()"/>

    <dojo:sortableColumn elementID="colorSort" action="listFragment" property="color" title="Color" update="myUpdateRegion"/>
  </tr>
  </thead>
  <tbody>
  <g:each var="widget" in="${widgetList}">
    <tr>
      <td>${widget.name}</td>
      <td>${widget.color}</td>
    </tr>
  </g:each>
  </tbody>
</table>
<div class="paginate">
  <dojo:paginate total="${total}" update="myUpdateRegion" next="Next Page" prev="Prev Page" action="listFragment"/>
</div>
<dojo:onload>
  console.log('An example of < dojo:onload > Loading when the list is shown');
</dojo:onload>

<table border="1">
  <thead>
  <tr>
    <dojo:sortableColumn elementID="nameSort" action="listFragment" property="name" title="Name" update="[success:'myUpdateRegion',failure:'myUpdateRegion2']"/>

    <dojo:sortableColumn action="listFragment" property="color" title="Color" update="myUpdateRegion"/>
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
<div class="paginateBar">
  <dojo:paginate total="${total}" update="myUpdateRegion" next="Next Page" prev="Prev Page" action="listFragment"/>
</div>
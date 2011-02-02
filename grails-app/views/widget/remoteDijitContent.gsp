<dojo:grid controller="widget" action="listJson" id="myGrid2" max="20" sort="name" 
	style="height:300px;" header="Grid loaded via Ajax" selectable="true">
	<dojo:col width="50%" name="Name" field="name">{row.name} ({row.id})</dojo:col>
	<dojo:col width="15%" name="Color" field="color"/>
	<dojo:col width="15%" name="Shape" field="shape"/>
</dojo:grid>
<html>
<head>
  <title>Grid Component</title>
  <meta name="layout" content="main"/>
  <dojo:require modules="['dijit.layout.TabContainer','dijit.layout.ContentPane']"/>
  <dojo:require modules="['dojoui.layout.TabContainer','dojoui.layout.ContentPane']"/>
  <dojo:gridResources theme="tundra"/>
  <dojo:dataSourceViewResources/>

  <script type="text/javascript">
      function myTest(){
      var allGrid = dijit.byId('myGrid');
      console.log('All Users Grid');
      console.log(allGrid.selectedRows);

      var progGrid = dijit.byId('myGrid2');
      console.log('Program Users Grid');
      console.log(progGrid.selectedRows);
      }
  </script>
	
  <style type="text/css">

	  .tundra .dojoxGridCell,
	  .soria .dojoxGridCell {
	    font-size: 12px;
	  }
  </style>
</head>
<body>

	<div class="dojoGridInGrails" style="padding:1em; background:#eee; float:right; margin:2em; border:1px solid gray">		
	<g:form name="gridForm">
		<h2 style="margin-top:0; color:gray">Search Test</h2>
		Name: <input type="text" name="name"><br/>
		Color: <input type="text" name="color"><br/>
		Shape: <input type="text" name="shape"><br/>

		<dojo:gridLoadDataButton label="Search" type="button" grid="myGrid" form="gridForm"/>
		<dojo:gridLoadDataButton label="Clear" type="reset" grid="myGrid"/>
		
		<p>
		Selected: <dojo:bind variable="myGrid.selected" id="asdasd" default="None"/>Selected<br/>
		Total Records <dojo:bind variable="myGrid.rowCount"/>
		</p>
	</g:form>
	<button onclick="myTest()">Test</button>
	</div>
	
	
	<%
		header = {
			return """
				<table style="width:100%; background:#eee; height:30px;">
				<tr style="font-size:16px; font-weight:bold">
					<td style="width:33%;">
						<button>
							Selected:
							${dojo.bind(variable:'myGrid.selected', default:'0')}
						</button>
					</td>
					<td style="width:33%; text-align:center;">My Widgets ( 	${dojo.bind(variable:'myGrid.rowCount')}  )</td>
					<td style="width:33%; text-align:right">
					</td>
				</tr>
				</table>
			"""
		}
	%>

  <div dojoType="dojoui.layout.TabContainer" style="width:750px; height:300px; margin:2em">
    <div dojoType="dojoui.layout.ContentPane" title="First Grid" containLinks="true">	
		  <dojo:grid controller="widget" action="listJson" name="myGrid" max="20" sort="name" header="${header}" selectable="true">
				<dojo:col width="50%" name="Name" field="name">{row.name} ({row.id})</dojo:col>
				<dojo:col width="15%" name="Color" field="color"/>
				<dojo:col width="15%" name="Shape" field="shape"/>
		  </dojo:grid>		
    </div>
    <div dojoType="dojoui.layout.ContentPane" title="My second Grid" containLinks="true">	
		  <dojo:grid controller="widget" action="listJson" name="myGrid2" max="20" sort="color"
				style="height:200px" header="This is the second grid." selectable="true">
				<dojo:col width="50%" name="Name" field="name">{row.name} ({row.id})</dojo:col>
				<dojo:col width="15%" name="Color" field="color"/>
				<dojo:col width="15%" name="Shape" field="shape"/>
		  </dojo:grid>
    </div>
  </div>
	
	
	<div  style="margin:2em; background:#eee; border:1px solid #ccc; width:750px">
		<h2 style="margin-top:0">Selected Items (using dojo:bind):</h2>
		<dojo:bind variable="myGrid.selectedRow.name"/>
		<dojo:dataSourceView store="dijit.byId('myGrid').selectedStore" class="dojoGridInGrails">
			<dojo:nodeDefaultTemplate>
			
				<span style="padding:1em; font-weight:bold">*{node.name}</span> 
				Color: <span style="color:{node.color}">{node.color}</span>
				Shape: {node.shape}
			</dojo:nodeDefaultTemplate>
			<dojo:noItemTemplate>
			  <h3 style="color:gray; padding:1em">Click on one of the checkboxes in the grid above.<h3>
			</dojo:noItemTemplate>
		</dojo:dataSourceView>		
	</div>	

  <dojo:grid controller="widget" action="listJson" name="myGrid3" max="20" sort="color" 
		style="height:200px; width:750px; margin:2em; border:1px solid silver" 
		header="Third Grid: Make sure to specify a height style.">
		<dojo:col width="50%" name="Name" field="name">{row.name} ({row.id})</dojo:col>
		<dojo:col width="15%" name="Color" field="color"/>
		<dojo:col width="15%" name="Shape" field="shape"/>
  </dojo:grid>
	

  <div style="margin:2em; background:#eee; border:1px solid #ccc; width:750px">
    <h2 style="margin-top:0">Notes</h2>
    <ul>
      <li>You may combine fields in one cell but you must specify the field if you wish to sort correctly.</li>
      <li>To sort by a column, just specify the sort property.</li>
      <li>Max property is optional. It will default to 1000.</li>
      <li>Sort property is optional. No Default</li>
      <li>Order property is optional. Defaults to asc.</li>
      <li>ColumnReordering is optional. Defaults to true.</li>
			<li>You must specify a height style or the grid will disappear. Specifying the height in a css class doesn't work though.</li>
      <li>IE 6 behaves weirdly if you specify the rowHeight property.</li>
      <li>
				Use .tundra .dojoxGridCell{height:30px} to style the cells. 
				(there is a weird scrolling issue that puts spaces when fetching from db.)
				</li>
    </ul>
  </div>


  <dojo:dataSourceView name="shoppingCart" data="{identifier:'id',items:[]}" style="height:100px; overflow:auto; position:relative; margin-top:1em;">
    <dojo:nodeDefaultTemplate>
        {node.id}
    </dojo:nodeDefaultTemplate>
    <dojo:noItemTemplate>
      <h3 style="color:gray; padding:1em">Example of an empty data source view.<h3>
    </dojo:noItemTemplate>
  </dojo:dataSourceView>

</body>
</html>





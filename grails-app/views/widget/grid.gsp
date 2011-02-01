<html>
<head>
  <link rel="shortcut icon" href="${resource()}/images/favicon.ico" type="image/x-icon"/>
  <dojo:header theme="tundra"/>
  <dojo:require modules="['dijit.layout.TabContainer','dijit.layout.ContentPane']"/>
  <dojo:require modules="['dojoui.layout.TabContainer','dojoui.layout.ContentPane']"/>
	<dojo:gridResources/>
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

	  .tundra .dojoxGridCell {
	    font-size: 12px;
	  }
    /*
    .tundra .dojoxGridHeader,
    .soria .dojoxGridHeader{
      height:30px;
    }

    .tundra .dojoxGridRowOver .dojoxGridCell {
      background:transparent;
      color:black;
    }
    .tundra .dojoxGridCellFocus {

    }
    */
  </style>
</head>
<body class="tundra">

	<div class="dojoGridInGrails" style="padding:1em; background:#eee;">		
	<g:form name="gridForm">
		Name: <input type="text" name="name"><br/>
		Color: <input type="text" name="color"><br/>
		Shape: <input type="text" name="shape"><br/>

		<dojo:gridLoadDataButton label="Search" type="button" grid="myGrid" form="gridForm"/>
		<dojo:gridLoadDataButton label="Clear" type="reset" grid="myGrid"/>
		
		
		Selected: <dojo:bind variable="myGrid.selected" id="asdasd"/>
		Total Records <dojo:bind variable="myGrid.rowCount"/>
	</g:form>
	</div>
	<button onclick="myTest()">Test</button>
	
	<%
		header = {
			return """
				<table style="width:100%; background:#eee; height:30px">
				<tr style="font-size:16px; font-weight:bold">
					<td style="width:33%;">
						<button>
							Selected:
							${dojo.bind(variable:'myGrid.selected')} 
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

  <div dojoType="dojoui.layout.TabContainer" style="width:750px; height:400px; margin:2em">
    <div dojoType="dojoui.layout.ContentPane" title="First Grid" containLinks="true">	
		  <dojo:grid controller="widget" action="listJson" id="myGrid" max="20" sort="name" 
				style="height:200px" header="${header}" selectable="true">
				<dojo:col width="50%" name="Name" field="name">{row.name} ({row.id})</dojo:col>
				<dojo:col width="15%" name="Color" field="color"/>
				<dojo:col width="15%" name="Shape" field="shape"/>
		  </dojo:grid>		
    </div>
    <div dojoType="dojoui.layout.ContentPane" title="My second Grid" containLinks="true">	
		  <dojo:grid controller="widget" action="listJson" id="myGrid2" max="20" sort="name" 
				style="height:200px" header="This is the second grid." selectable="true">
				<dojo:col width="50%" name="Name" field="name">{row.name} ({row.id})</dojo:col>
				<dojo:col width="15%" name="Color" field="color"/>
				<dojo:col width="15%" name="Shape" field="shape"/>
		  </dojo:grid>
    </div>
  </div>
	
	
	
	<div  style="margin:2em; background:#eee; border:1px solid #ccc; width:750px">
		Selected: <dojo:bind variable="myGrid.selectedRow.name"/>
		<dojo:dataSourceView store="dijit.byId('myGrid').selectedStore" class="dojoGridInGrails">
			<dojo:nodeDefaultTemplate>
				{node.name}, {node.color}, {node.shape}
			</dojo:nodeDefaultTemplate>
		</dojo:dataSourceView>		
	</div>	


  <div style="margin:2em; background:#eee; border:1px solid #ccc; width:750px">
    <h2 style="margin-top:0">Notes</h2>
    <ul>
      <li>You may combine fields in one cell but you must specify the field if you wish to sort correctly.</li>
      <li>To sort by a column, just specify the sort property.</li>
      <li>Max property is optional. It will default to 1000.</li>
      <li>Sort property is optional. No Default</li>
      <li>Order property is optional. Defaults to asc.</li>
      <li>ColumnReordering is optional. Defaults to true.</li>
      <li>It's a good idea to specify a rowHeight. Suggestions: rowHeight="30". ( IE 6 behaves better when this is not set.)</li>
      <li>
				Use .tundra .dojoxGridCell{height:30px} to style the cells. 
				(there is a weird scrolling issue that puts spaces when fetching from db.)
				</li>
      <li>To control the height, specify it in the style property of the dom element. (css class doesn't work.)</li>
    </ul>
  </div>

</body>
</html>






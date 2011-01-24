<html>
<head>
  <link rel="shortcut icon" href="${resource()}/images/favicon.ico" type="image/x-icon"/>
  <dojo:header theme="tundra"/>
	<script type="text/javascript">
		function myTest(){
			var selectStore = dijit.byId('myGrid').selectedStore;
			console.log(selectStore);
		}
	</script>
	
  <style type="text/css">
	  .dojoGridInGrails {
	    width: 700px;
			margin:2em;
	    border: 1px solid gray;
	  }

	  .dojoGridInGrails .dojoxGridCell {
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
		<button type="button" onclick="dijit.byId('myGrid').query('gridForm')">Search</button>
		<button type="reset" onclick="dijit.byId('myGrid').query();">Clear</button>
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

  <dojo:grid class="dojoGridInGrails" controller="widget" action="listJson" id="myGrid" max="200" sort="name" 
		style="height:300px" header="${header}" selectable="true">
		<dojo:col width="50%" name="Name" field="name">{row.name} ({row.id})</dojo:col>
		<dojo:col width="15%" name="Color" field="color"/>
		<dojo:col width="15%" name="Shape" field="shape"/>
  </dojo:grid>
	
	<div>
		Selected: <dojo:bind variable="myGrid.selectedRow.name"/>
	</div>	

	<dojo:dataSourceView store="dijit.byId('myGrid').selectedStore" class="dojoGridInGrails">
		<dojo:nodeDefaultTemplate>
			{node.name}, {node.color}, {node.shape}
		</dojo:nodeDefaultTemplate>
	</dojo:dataSourceView>


  <div class="dojoGridInGrails" style="padding:1em; background:#eee;">
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






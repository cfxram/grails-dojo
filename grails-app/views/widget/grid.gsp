<html>
<head>
  <link rel="shortcut icon" href="${resource()}/images/favicon.ico" type="image/x-icon"/>
  <dojo:header theme="tundra"/>

  <style type="text/css">
  .dojoGridInGrails {
    width: 700px;
		margin:2em;
    border: 1px solid gray;
  }

  .dojoGridInGrails .dojoxGridCell {
    font-size: 12px;
  }
	.grails-dojo-grid-title{
		font-weight:bold;
		font-size:16px;
		padding:2px;
		display:inline-block;
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

  <dojo:grid class="dojoGridInGrails" controller="widget" action="listJson" id="myGrid" max="40" sort="name" style="height:400px" select="true" title="New Widgets">
    <dojo:col width="50%" name="Name" field="name">{row.name} ({row.id})</dojo:col>
    <dojo:col width="25%" name="Color" field="color"/>
    <dojo:col width="25%" name="Shape" field="shape"/>
  </dojo:grid>


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






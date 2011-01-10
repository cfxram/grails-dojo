<html>
<head>
	<link rel="shortcut icon" href="${resource()}/images/favicon.ico" type="image/x-icon" />
  <dojo:header theme="tundra"/>

  <style type="text/css">
    .dojoGridInGrails{
      width:600px;
      height:250px;
      margin:2em;
      border:1px solid gray;
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


  <dojo:grid class="dojoGridInGrails" controller="widget" action="listJson" id="myGrid" max="200" sort="name">
    <dojo:col width="40%" name="Name" field="name">{row.name} ({row.id})</dojo:col>
    <dojo:col width="30%" name="Color" field="color"/>
    <dojo:col width="30%" name="Shape" field="shape"/>
  </dojo:grid>

	<h4>Notes</h4>
	<ul>
		<li>You may combine fields in one cell but you must specify the field if you wish to sort correctly.</li>
		<li>To sort by a column, just specify the sort property.</li>
		<li>Max property is optional. It will default to 1000.</li>
		<li>Sort property is optional. No Default</li>
		<li>Order property is optional. Defaults to asc.</li>
	</ul>	
</body>
</html>






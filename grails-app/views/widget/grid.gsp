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


  <dojo:grid class="dojoGridInGrails" controller="widget" action="listJson" id="myGrid" sort="name">
    <dojo:col width="40%" name="Name" field="name"/>
    <dojo:col width="30%" name="Color" field="color"/>
    <dojo:col width="30%" name="Shape" field="shape"/>
  </dojo:grid>


</body>
</html>






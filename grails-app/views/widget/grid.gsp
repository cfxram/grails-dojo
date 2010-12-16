<html>
<head>
  <dojo:header theme="tundra" modules="['dojo.data.ItemFileWriteStore','dojoui.dojouiGrid', 'dojox.grid.DataGrid']"/>
  <dojo:css file="dojox/grid/resources/Grid.css"/>
  <dojo:css file="dojox/grid/resources/tundraGrid.css"/>
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


  <dojo:grid class="dojoGridInGrails" controller="widget" action="listJson" id="myGrid">
    <dojo:col width="40%" name="Name">{row.name} ({row.id}) </dojo:col>
    <dojo:col width="30%" name="Color" field="color"/>
    <dojo:col width="30%" name="Shape" field="shape"/>
  </dojo:grid>


</body>
</html>






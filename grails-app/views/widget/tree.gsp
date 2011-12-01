<html>
<head>
  <title>Tree Component</title>
  <meta name="layout" content="main"/>
  <dojo:treeResources/>
  <style type="text/css">
    .widgetBrowser{
      border: 1px solid #ccc;
      width:300px;
      height:400px;
      margin: 3em;
    }
    .category{
      height:2em;
      background: #eee;
    }
    .regularWidget{

    }

    .discountWidget{

    }
  </style>
</head>
<body>


  <dojo:tree controller="widget" action="treeJson" name="widgetTree" class="widgetBrowser" children="hasChilrend">
    <dojo:node>
      <div class="category">{node.name} (Category)</div>
    </dojo:node>

    <dojo:leaf>
      <div class="regularWidget">{node.name}</div>
    </dojo:leaf>
  </dojo:tree>



</body>
</html>
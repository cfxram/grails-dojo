<html>
<head>
  <title>Tree Component</title>
  <meta name="layout" content="main"/>
  <dojo:treeResources/>
  <style type="text/css">
    .category{

    }
    .regularWidget{

    }

    .discountWidget{

    }
  </style>
</head>
<body>


  <dojo:tree controller="widget" action="treeJson" name="widgetTree">
    <dojo:node>
      <div class="category">{node.name}</div>
    </dojo:node>

    <dojo:leaf>
      <div class="regularWidget">{node.name}</div>
    </dojo:leaf>

    <dojo:leaf field="discounted" value="true">
      <div class="discountWidget">{node.name}</div>
    </dojo:leaf>
  </dojo:tree>



</body>
</html>
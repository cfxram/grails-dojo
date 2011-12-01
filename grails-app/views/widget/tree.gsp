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
    }
    .category{
      padding: 5px;
      font-size:14px;
      color:navy;
      background: #eee;
      border: 1px solid #ccc;
    }
    .regularWidget{
      padding: 2px;
      background: url(${resource(dir:'images')}/add.png) no-repeat;
      padding-left:2em;
    }

    .discountWidget{
      padding: 2px;
      color:gray;
      font-weight: bold;
      background: url(${resource(dir:'images')}/add_disabled.png) no-repeat;
      padding-left:2em;
    }
  </style>
</head>
<body>

<div style="width:300px;margin: 3em;">
  <h2 style="text-align: center; margin:.5em 0 3px 0;">Mark Widgets to be discounted</h2>
  <dojo:tree action="treeJson" name="widgetTree" class="widgetBrowser" childField="hasChildNodes">
    <dojo:node>
      <div class="category">{node.name}</div>
    </dojo:node>

    <dojo:leaf>
      <div class="regularWidget"onclick="{this}.setNodeValue({node.id},'discounted','true')">{node.name}</div>
    </dojo:leaf>

    <dojo:leaf field="discounted" value="true">
      <div class="discountWidget" onclick="{this}.setNodeValue({node.id},'discounted','false')">{node.name} (discounted)</div>
    </dojo:leaf>
  </dojo:tree>
</div>

</body>
</html>
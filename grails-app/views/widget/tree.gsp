<html>
<head>
  <title>Tree Component</title>
  <meta name="layout" content="main"/>
  <dojo:treeResources/>
  <dojo:dataSourceViewResources/>
  <style type="text/css">
    .widgetBrowser{
      border: 1px solid #ccc;
      width:300px;
      height:300px;
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
    .discountWidget2{
      font-weight: bold;
      background: url(${resource(dir:'images')}/delete.png) no-repeat;
      padding-left:2em;
      margin: 4px;
      border-bottom: 1px solid #eee;
      cursor: pointer;
    }

    /* Fix the dojo tree layout */
    #widgets .dijitTreeIcon{
        background-image:none;
        height:0;
        width:0;
        display:none;
    }
    #widgets .dijitTreeContent {
      display: block;
      width: 90%;
      margin: 0;
      margin-left: 2em;
      padding: 0;
      margin-top: -15px;
    }
    #widgets .dijitTreeLabel{
      display:block;
      margin:0;
    }
  </style>
</head>
<body>

<table style="margin: 1em; font-weight: bold; margin: 1em auto 0 auto">
  <tr style="text-align: center; font-size: 14px">
    <td>Not Styled</td>
    <td>Custom Templates and Styled</td>
  </tr>
  <tr>
    <td style="padding-right:3em">
      <dojo:tree action="treeJson" name="widgetTree" childField="hasChildNodes" class="widgetBrowser">
        <dojo:treeNode>{node.name}</dojo:treeNode>
        <dojo:treeLeaf>{node.name}</dojo:treeLeaf>
      </dojo:tree>
    </td>
    <td id="widgets">
      <dojo:tree action="treeJson" name="widgetTreeStyled" class="widgetBrowser" childField="hasChildNodes">
        <dojo:treeNode>
          <div class="category">{node.name}</div>
        </dojo:treeNode>
        <dojo:treeLeaf>
          <div class="regularWidget" onclick="{this}.setNodeValue('{node.id}','discounted','true')">{node.name}</div>
        </dojo:treeLeaf>
        <dojo:treeLeaf field="discounted" value="true">
          <div class="discountWidget" onclick="{this}.setNodeValue('{node.id}','discounted','false')">{node.name} (discounted)</div>
        </dojo:treeLeaf>
      </dojo:tree>
    </td>

  </tr>
</table>


<table style="margin: 1em; font-weight: bold; margin: 2em auto 0 auto">
  <tr style="text-align: center; font-size: 14px">
    <td>With Search</td>
    <td>Selected Values With datasource view</td>
  </tr>
  <tr>
    <td id="widgets" style="padding-right:3em">
      <dojo:tree action="treeJson" name="widgetTreeStyledWithSearch" class="widgetBrowser" childField="hasChildNodes" searchAble="true">
        <dojo:treeNode>
          <div class="category">{node.name}</div>
        </dojo:treeNode>
        <dojo:treeLeaf>
          <div class="regularWidget" onclick="{this}.setNodeValue('{node.id}','discounted','true')">{node.name}</div>
        </dojo:treeLeaf>
        <dojo:treeLeaf field="discounted" value="true">
          <div class="discountWidget" onclick="{this}.setNodeValue('{node.id}','discounted','false')">{node.name} (discounted)</div>
        </dojo:treeLeaf>
      </dojo:tree>
    </td>
    <td>

      <dojo:dataSourceView store="widgetTreeStyledWithSearch_store" class="widgetBrowser">
        <dojo:nodeTemplate field="discounted" value="true">
          <div class="discountWidget2" onclick="{this}.setNodeValue('{node.id}','discounted','false')">{node.name} (discounted)</div>
        </dojo:nodeTemplate>
        <dojo:noItemTemplate>
          <h1 style="color:gray; text-align: center">
            Click the plus button to
            choose a widget to discount.
          </h1>
        </dojo:noItemTemplate>
      </dojo:dataSourceView>
    </td>
  </tr>
</table>


<table style="margin: 1em; font-weight: bold; margin: 1em auto 1em auto">
  <tr style="text-align: center; font-size: 14px">
    <td>Not Styled - Static Content (With just One level)</td>
  </tr>
  <tr>
    <td id="widgets"  style="padding-right:3em">

      <dojo:tree name="widgetTreeStaticData" childField="hasChildNodes" class="widgetBrowser" data="${jsonString}" expandFirstChild="true">
        <dojo:treeNode>
          <div class="category">{node.name}</div>
        </dojo:treeNode>
        <dojo:treeLeaf>
          <div class="regularWidget" onclick="{this}.setNodeValue( '{node.id}' ,'discounted','true')">{node.name}</div>
        </dojo:treeLeaf>
        <dojo:treeLeaf field="discounted" value="true">
          <div class="discountWidget" onclick="{this}.setNodeValue('{node.id}' ,'discounted','false')">{node.name} (discounted)</div>
        </dojo:treeLeaf>
      </dojo:tree>

    </td>
  </tr>
</table>


</body>
</html>
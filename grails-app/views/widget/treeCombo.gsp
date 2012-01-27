<html>
<head>
  <title>Tree Component</title>
  <meta name="layout" content="main"/>
  <dojo:require  modules="['dijit.layout.StackContainer','dijit.layout.ContentPane']"/>
  <dojo:treeResources/>
</head>
<body>

<div style="margin: 1em">
  <button onclick="dijit.byId('myContainer').selectChild('One');">One</button>
  <button onclick="dijit.byId('myContainer').selectChild('Two');">Two</button>
  <button onclick="dijit.byId('myContainer').selectChild('Three');">Three</button>

  <div dojoType="dijit.layout.StackContainer" id="myContainer" style="width:400px; border: 1px solid gray">
    <div dojoType="dijit.layout.ContentPane" style="height:100px; background: #eee" id="One">
      Pane 1
    </div>
    <div dojoType="dijit.layout.ContentPane" style="height:100px; background: #ddd" id="Two">
      Pane 2
    </div>
    <div dojoType="dijit.layout.ContentPane" style="height:100px; background: #ccc" id="Three">
      Pane 3
    </div>
  </div>
</div>

  <dojo:tree action="treeJson" name="widgetTree" childField="hasChildNodes" style="width:400px; border: 1px solid gray; margin: 1em; height:300px;">
    <dojo:treeNode>{node.name}</dojo:treeNode>
    <dojo:treeLeaf>{node.name}</dojo:treeLeaf>
  </dojo:tree>



<%--
<div style="margin: 1em">
  <button onclick="dijit.byId('myContainer').selectChild('One');">One</button>
  <button onclick="dijit.byId('myContainer').selectChild('Two');">Two</button>
  <button onclick="dijit.byId('myContainer').selectChild('Three');">Three</button>

  <div dojoType="dijit.layout.StackContainer" id="myContainer" style="width:400px; border: 1px solid gray">
    <div dojoType="dijit.layout.ContentPane" style="height:100px; background: #eee" id="One">
      Pane 1
    </div>
    <div dojoType="dijit.layout.ContentPane" style="height:100px; background: #ddd" id="Two">
      Pane 2
    </div>
    <div dojoType="dijit.layout.ContentPane" style="height:100px; background: #ccc" id="Three">
      Pane 3
    </div>
  </div>
</div>

<dojo:tree controller="user" action="generateCompetencyJSON" params="[userId:userId]" childField="comps" showRoot="false"
    style="width:400px; border: 1px solid gray; margin: 1em; height:300px;">
  <dojo:treeNode>{node.name}</dojo:treeNode>
  <dojo:treeLeaf>{node.name}</dojo:treeLeaf>
</dojo:tree>

--%>


</body>
</html>
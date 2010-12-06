<html>
<head>
  <title>Various Dijit Components</title>
  <meta name="layout" content="main"/>
  <dojo:require modules="['dojoui.layout.TabContainer','dojoui.layout.ContentPane']"/>
</head>
<body>
  <div dojoType="dojoui.layout.TabContainer" style="width:600px; height:300px">
    <div dojoType="dojoui.layout.ContentPane" title="Red">
      Red
    </div>
    <div dojoType="dojoui.layout.ContentPane" title="Blue">
      Blue
    </div>    
  </div>
</body>
</html>
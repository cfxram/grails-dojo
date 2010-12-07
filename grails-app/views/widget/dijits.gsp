<html>
<head>
  <title>Various Dijit Components</title>
  <meta name="layout" content="main"/>
  <dojo:require modules="['dojo.layout.TabContainer','dojo.layout.ContentPane']"/>
</head>
<body class="tundra">

  <div dojoType="dojo.layout.TabContainer" style="width:650px; height:300px">
    <div dojoType="dojo.layout.ContentPane" title="My First Tab">
      This is content in my first tab.
    </div>
    <div dojoType="dojoui.layout.ContentPane" title="My second tab.">
		This is content in my second tab.
    </div>    
  </div>


</body>
</html>
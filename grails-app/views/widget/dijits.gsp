<html>
<head>
  <title>Various Dijit Components</title>
  <meta name="layout" content="main"/>
  <dojo:require modules="['dojoui.layout.TabContainer','dojoui.layout.ContentPane']"/>
</head>
<body>
	<div class="dojo-ui-panel-delete-icon"></div>	
	
  <div dojoType="dojoui.layout.TabContainer" style="width:650px; height:300px">
    <div dojoType="dojoui.layout.ContentPane" title="Red">
      Red
    </div>
    <div dojoType="dojoui.layout.ContentPane" title="Blue" containLinks="true">
		<g:link controller="widget" action="list">Click Me.</g:link>
    </div>    
  </div>
</body>
</html>
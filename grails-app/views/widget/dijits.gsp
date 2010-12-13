<html>
<head>
  <title>Various Dijit Components</title>
  <meta name="layout" content="main"/>
  <dojo:require modules="['dijit.layout.TabContainer','dijit.layout.ContentPane']"/>
  <dojo:require modules="['dojoui.layout.TabContainer','dojoui.layout.ContentPane']"/>
</head>
<body class="tundra">

  <div dojoType="dijit.layout.TabContainer" style="width:650px; height:200px">
    <div dojoType="dijit.layout.ContentPane" title="My First Tab">
      This is content in my first tab.
    </div>
    <div dojoType="dijit.layout.ContentPane" title="My second tab.">
		<g:link action="list">This will open in the parent.</g:link> 
    </div>    
  </div>


  <div dojoType="dojoui.layout.TabContainer" style="width:650px; height:200px; margin-top:1em">
    <div dojoType="dojoui.layout.ContentPane" title="My First Tab">
      This is content in my first tab.
    </div>
    <div dojoType="dojoui.layout.ContentPane" title="My second tab." containLinks="true">
		<g:link action="list">This will stay in the tab.</g:link> 
    </div>
  </div>

</body>
</html>
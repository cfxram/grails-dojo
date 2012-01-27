<html>
<head>
  <title>Tab and Pane Components</title>
  <meta name="layout" content="main"/>
	<dojo:tabResources/>
	<dojo:frameResources/>
    <dojo:require modules="['dijit.layout.StackContainer']" />
  <style type="text/css">
    .stackPane{
      height:200px;
      background: #eee;
    }
  </style>

</head>
<body class="tundra">

<div style="padding:2em">
  <h2 style="margin-top:0">Tab Container with &lt;dojo:contentPane&gt;</h2>
  <dojo:tabContainer style="width:650px; height:200px">
    <dojo:contentPane title="My First Tab">
      This is content in my first tab.<br/>
      <g:link action="list">This will open in the parent.</g:link>
    </dojo:contentPane>
    <dojo:contentPane title="My second tab.">
      <g:link action="list">This will open in the parent.</g:link>
    </dojo:contentPane>
  </dojo:tabContainer>

  <h2>Tab Container with &lt;dojo:frame&gt; (links stay in the tabs)</h2>
  <dojo:tabContainer style="width:650px; height:200px; margin-top:1em">
    <dojo:frame title="My First Tab" style="padding:1em">
      This is content in my first tab.<br/>
      <g:link action="list">This will stay in the tab.</g:link>
    </dojo:frame>
    <dojo:frame title="My second tab">
      <g:link action="list">This will stay in the tab.</g:link>
    </dojo:frame>
  </dojo:tabContainer>


<div style="margin:1em;">
  <button onclick="dijit.byId('genericStack').selectChild('stackOne');">One</button>
  <button onclick="dijit.byId('genericStack').selectChild('stackTwo');">Two</button>
  <button onclick="dijit.byId('genericStack').selectChild('stackThree');">Three</button>
  <div dojoType="dijit.layout.StackContainer" id="genericStack" doLayout="false" style="border: 1px solid gray; width:400px;">
    <div dojoType="dijit.layout.ContentPane" class="stackPane" id="stackOne" style="height:100px; background: #eee;">
      Stack Pane One
    </div>
    <div dojoType="dijit.layout.ContentPane" class="stackPane"  id="stackTwo" style="height:100px; background: #ddd;">
      Stack Pane Two
    </div>
    <div dojoType="dijit.layout.ContentPane" class="stackPane"  id="stackThree" style="height:100px; background: #ccc;">
      Stack Pane Two
    </div>
  </div>
</div>

</div>
</body>
</html>
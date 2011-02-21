<html>
<head>
  <title>Tab and Pane Components</title>
  <meta name="layout" content="main"/>
	<dojo:tabResources/>
	<dojo:paneResources/>
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

	<h2>Tab Container with &lt;dojo:pane&gt; (links stay in the tabs)</h2>
  <dojo:tabContainer style="width:650px; height:200px; margin-top:1em">
    <dojo:pane title="My First Tab" style="padding:1em">
      This is content in my first tab.<br/>
			<g:link action="list">This will stay in the tab.</g:link> 
    </dojo:pane>
    <dojo:pane title="My second tab." style="padding:1em">
			<g:link action="list">This will stay in the tab.</g:link> 
    </dojo:pane>
  </dojo:tabContainer>
	
	</div>
</body>
</html>
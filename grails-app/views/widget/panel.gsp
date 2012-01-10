<html>
<head>
  <title>Editor Component</title>
  <meta name="layout" content="main"/>
  <dojo:panelResources/>
  <dojo:frameResources/>
</head>
<body>


  <dojo:panel title="This is a simple Panel" style="width:500px; height:300px; margin: 1em">
    This is some Content
  </dojo:panel>


  <dojo:panel style="width:500px; height:300px; margin: 1em" >
    <dojo:panelHeader help="You can show help related to a panel.">This is a panel with a frame inside of it.</dojo:panelHeader>
    <dojo:panelBody>
    <dojo:frame>

      <g:link action="list">Click to load some content</g:link>
    </dojo:frame>
    </dojo:panelBody>
  </dojo:panel>


</body>
</html>
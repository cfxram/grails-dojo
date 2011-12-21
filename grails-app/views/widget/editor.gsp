<html>
<head>
  <title>Editor Component</title>
  <meta name="layout" content="main"/>
  <dojo:editorResources/>
</head>
<body>

<div style="float:right; margin-right:2em">
  <h2>Simple (small):</h2>
  <dojo:editor name="simpleExample-Small" style="width:400px; margin:0 0 0 2em ">
    <div>Test</div><p>Test</p>
  </dojo:editor>
</div>

<div  style="float:left; margin-left:1em">
  <h2>Simple:</h2>
  <dojo:editor name="simpleExample" style="width:600px; margin:0 0 0 2em ">
    <div>Test</div><p>Test</p>
  </dojo:editor>
</div>


<div style="float:right; margin-right:2em; clear:both">
  <h2>Intermediate (small):</h2>
  <dojo:editor type="intermediate" name="intermediateExampleSmall" style="width:400px; margin:0 0 0 2em ">
    <div>Test</div><p>Test</p>
  </dojo:editor>
</div>

<div style="float:left; margin-left:1em;">
  <h2>Intermediate:</h2>
  <dojo:editor type="intermediate" name="intermediateExample" style="width:600px; margin:0 0 0 2em ">
    <div>Test</div><p>Test</p>
  </dojo:editor>
</div>


<div style="float:left; margin-left:2em; clear:both">
  <h2>Advanced:</h2>
  <dojo:editor type="advanced" name="advanced" style="width:800px; margin:0 0 0 2em ">
    <div>Test</div><p>Test</p>
  </dojo:editor>
</div>


</body>
</html>
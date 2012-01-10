<html>
<head>
  <title>Editor Component</title>
  <meta name="layout" content="main"/>
  <dojo:editorResources/>
  <script type="text/javascript">
    Page = {
      prefillData:function(id){
        dijit.byId(id).set("value","Content<p>This is prefilled data.</p>");
      }
    }
  </script>
</head>
<body>

<g:form action="saveAllEditors" name="editor save">

  <div style="float:right; margin-right:2em">
    <h2>Simple (small):<button onclick="Page.prefillData('simpleExampleSmall');" type="button">PreFill Data</button></h2>
    <dojo:editor id="simpleExampleSmall" style="width:400px; margin:0 0 0 2em ">
      ID was only specified
    </dojo:editor>
  </div>

  <div  style="float:left; margin-left:1em">
    <h2>Simple: <button onclick="Page.prefillData('simpleExample');" type="button">PreFill Data</button></h2>
    <dojo:editor name="simpleExample" style="width:600px; margin:0 0 0 2em ">
      Only name was specified
    </dojo:editor>
  </div>


  <div style="float:right; margin-right:2em; clear:both">
    <h2>Intermediate (small): <button onclick="Page.prefillData('intermediateExampleSmallTest');" type="button">PreFill Data</button></h2>
    <dojo:editor id="intermediateExampleSmallTest" type="intermediate" name="intermediateExampleSmall" style="width:400px; margin:0 0 0 2em ">
      ID and name are different.
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


  <div style="clear:both; margin: 1em; background: #eee; padding: 1em; border: 1px solid #ccc">
    <g:submitButton name="save" value="Save Data"/>
  </div>

</g:form>

</body>
</html>
<html>
<head>
  <title>Widget Show More Page</title>
  <dojo:header theme="tundra"/>
  <dojo:dateTimeResources/>
  <dojo:helpResources/>
</head>
<body class="tundra">

  <div id="myUpdateRegion" style="width:800px; border:1px solid #ccc; margin:2em">
    <div id="myUpdateRegion2" style="width:800px; background: #cfcfcf; border:1px solid #ccc; margin:2em 0 2em 0">
      <g:include action="showInnerList" />
    </div>
    <g:render template="showMoreList" />
  </div>

</body>
</html>
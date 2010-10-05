<html>
<head>
  <title>Widget Tester Page</title>
  <g:javascript library="dojo"/>
  <g:javascript>
    function beforeEvent() {
      console.log("Before");
    }

    function after() {
      console.log("After");
    }

    function onSuccess() {
      console.log("On Success");
    }

    function onFailure() {
      console.log("On Failure");
    }

    function onLoading() {
      console.log("Loading");
    }

    function onLoaded() {
      console.log("Loaded");
    }

    function onComplete() {
      console.log("Complete");
    }
    function onFailure404() {
      console.log("Had a 404 failure.")
    }
    function onFailure200() {
      console.log("Had a 200 failure.")
    }
  </g:javascript>
  <style type="text/css">
    .paginate{
      
    }
    .nextLink{
      
    }
    .prevLink{
      
    }
    .step{
      
    }
  </style>
</head>


<body>


<div id="myUpdateRegion" style="width:600px; border:1px solid gray; margin:2em">
  <g:render template="list"/>
</div>

<div id="myUpdateRegion2" style="width:600px; height:200px; border:1px solid gray; margin:2em"></div>


</body>
</html>
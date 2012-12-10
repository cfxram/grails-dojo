<html>
<head>
  <title>Widget Show More Page</title>
  <dojo:header theme="tundra"/>
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
    .paginateBar{
        padding-bottom:0.5em;
    }

    .paginateBar .step,
    .paginateBar .nextLink,
    .paginateBar .prevLink,
    .paginateBar .currentStep{
        padding: 0.3em 0.7em 0.3em 0.7em;
        border: 1px solid rgb(192, 192, 192);
        margin:2px;
    }
    .paginateBar .step{
        background:#eee;
        color:gray;
    }
    .paginateBar .currentStep{
        font-weight:bold;
        border:1px solid gray;
    }
    .paginateBar .nextLink, .paginateBar .prevLink{
        padding: 0.3em 0.7em 0.3em 0.7em;
        border: 1px solid rgb(192, 192, 192);
        border-bottom: 1px solid rgb(155, 155, 155);
        font-weight: normal;
        line-height: 38px;
        color: black;
        outline: none;
    }

    body div.grailsdojospinner{
      background-image:url(${resource(dir:'images')}/my_custom_spinner.gif);
    }
  </style>
</head>


<body>


<div id="myUpdateRegion" style="width:800px; border:1px solid #ccc; margin:2em">

  <div id="myUpdateRegion2" style="width:800px; background: #cfcfcf; border:1px solid #ccc; margin:2em">
      <g:include action="showInnerList" />
    </div>

  <g:render template="showMoreList" />


</div>





</body>
</html>
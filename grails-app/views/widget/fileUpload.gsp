<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>Widget Tester Page</title>
  <meta name="layout" content="main"/>
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
</head>
<body>

<div style="float:left">
	<ul>
	  <li>
      <g:formRemote name="saveFile" url="[action:'saveFileWithAjax']" update="[success:'myUpdateRegion',failure:'myUpdateRegion2']"
                    enctype="multipart/form-data"
                    before="beforeEvent()"
       	            after="after()"
       	            onSuccess="onSuccess()"
       	            onFailure="onFailure()"
       	            onLoading="onLoading()"
       	            onLoaded="onLoaded()"
       	            onComplete="onComplete()"
                    on503="console.log('error 503')"
                    on200="console.log('Status is 200');">
        <h3>Form Remote with multipart/form-data.</h3>
        <g:hiddenField name="fileName" value="This is a simple name for a file."/>
        <div>
          <label for="myFile">File:</label>
          <input type="file" id="myFile" name="myFile"/><br/>
        </div>
        <g:submitButton name="save" value="Upload"/>
      </g:formRemote>
    </li>

    <li>
      Testing ajaxUploadResponse: There should be no text box here:
      <dojo:ajaxUploadResponse></dojo:ajaxUploadResponse>
    </li>

    <li>
      <h3>SubmitToRemote with g:uploadForm</h3>
      <g:uploadForm name="saveFile2">
        <g:hiddenField name="fileName" value="This is a simple name for a file."/>
        <div>
          <label for="myFile2">File:</label>
          <input type="file" id="myFile2" name="myFile"/><br/>
        </div>
        <g:submitToRemote action="saveFileWithAjax" update="[success:'myUpdateRegion',failure:'myUpdateRegion2']" value="Click Me" uploadFile="true"
                          before="beforeEvent()"
             	            after="after()"
             	            onSuccess="onSuccess()"
             	            onFailure="onFailure()"
             	            onLoading="onLoading()"
             	            onLoaded="onLoaded()"
             	            onComplete="onComplete()"
                          on503="console.log('error 503')"
                          on200="console.log('Status is 200');"/>
      </g:uploadForm>
    </li>

  </ul>

  <div id="myUpdateRegion" style=" width:500px; border:1px solid gray; margin:2em; padding:1em"></div>
 	<div id="myUpdateRegion2" style=" width:500px; border:1px solid gray; margin:2em; padding:1em"></div>
</div>

<div style="width:480px; height:500px; float:right; ">

</div>

</body>

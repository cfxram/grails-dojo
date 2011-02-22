<html>
<head>
  <title>Just Remoting</title>
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

    /* The params attribute changes depending on if you are using prototype or dojo.
    function renameForPrototype(name, myId){
      var renamedString= prompt ("Enter new name:", name);
      ${remoteFunction(action:'remoteFunctionAction', update:'myUpdateRegion',params:"'name='+renamedString+'&myId='+myId")}
    }
    */

    function renameForDojo(name, myId){
      var renamedString= prompt ("Enter new name:", name);
      ${remoteFunction(action:'remoteJsFunction', update:'myUpdateRegion',params:"'name':renamedString,'myId':myId")}
    }    
  </g:javascript>
</head>
<body>

	<div style="float:left">
		<ul>
		  <li>
		    <g:remoteLink name="robsLink" action="testRemoteLink" update="[success:'myUpdateRegion',failure:'myUpdateRegion2']"
		            before="beforeEvent()"
		            after="after()"
		            onSuccess="onSuccess()"
		            onFailure="onFailure()"
		            onLoading="onLoading()"
		            onLoaded="onLoaded()"
		            onComplete="onComplete()"
		            on404="onFailure404()"
		            on200="onFailure200()" params="[userid:1, myName:'Rob']">
		      Click Here to Test
		    </g:remoteLink>
		  </li>
		</ul>
		<div id="myUpdateRegion" style=" width:500px; border:1px solid gray; margin:2em; padding:1em"></div>
		<div id="myUpdateRegion2" style=" width:500px; border:1px solid gray; margin:2em; padding:1em"></div>		
	</div>	
	
</body>	
</html>
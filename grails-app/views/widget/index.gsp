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

    function renameForDojoWithDomElem(name, myId){
      var renamedString= prompt ("Enter new name:", name);
      ${remoteFunction(action:'remoteJsFunction',params:"'name':renamedString,'myId':myId")}
    }

    function testRemoteFuncWithForm(){
      ${remoteFunction(action:'remoteJsFunction', formName:'searchWidgets')}
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
	            on200="onFailure200()" params="[userid:1, myName:'Rob']" position="only">
	      Click Here to Test
	    </g:remoteLink>
	  </li>

	  <li>
	    <g:remoteLink action="unknownAction" update="[success:'myUpdateRegion',failure:'myUpdateRegion2']"
	            before="beforeEvent()"
	            after="after()"
	            onSuccess="onSuccess()"
	            onFailure="onFailure()"
	            onLoading="onLoading()"
	            onLoaded="onLoaded()"
	            onComplete="onComplete()"
	            on404="onFailure404()">
	      Click to Fail 
	    </g:remoteLink>(fails in lower region)
	  </li>

	  <li>
	    <g:remoteLink action="unknownAction" update="myUpdateRegion" >
	      Fail without Handlers
	    </g:remoteLink> (fails in upper region)
	  </li>
	  <li>
	    <g:remoteLink action="remotePostFromLink" update="myUpdateRegion" method="post">
	      Do Post Request
	    </g:remoteLink> (from a remoteLink)
	  </li>
	  <li>
	    %{-- Only works in dojo. not prototype.
	    <g:remoteLink url="remotePage.gsp" update="myUpdateRegion" method="post">
	      Do Request with url as string
	    </g:remoteLink>
	    --}%
			<span style="color:silver"> This test was made for Prototype Lib</span>
	  </li>
	  <li>
	    <g:remoteLink url="[action:'remoteRequestWithUrlMap']" update="myUpdateRegion" method="post">
	      Do Request with url as map
	    </g:remoteLink> (instead of being defined in controller and action)
	  </li>
	  <li>
	    <g:remoteLink  action="synchronizedRemoteLink" update="myUpdateRegion" asynchronous="false">
	      Synchronized Link
	    </g:remoteLink> (async set to false)
	  </li>
	  <li>
	    Remote Function
	    <select onchange="${remoteFunction(action:'remoteFunctionAction',update:[success:'myUpdateRegion', failure:'myUpdateRegion2'], params:"'color':this.value")}">
	      <option value="">Choose a Color:</option>
	      <option value="red">Red</option>
	      <option value="green">Green</option>
	      <option value="blue">Blue</option>
	      <option value="transparent">No Color</option>
	    </select>
	  </li>
	  <li>
	    Remote Function with Map Params
	    <select onchange="${remoteFunction(action:'remoteFunctionWithParams',update:[success:'myUpdateRegion', failure:'myUpdateRegion2'], params:"'color':this.value,'pluginName':'Dojo Plugin'")}">
	      <option value="">Choose a Color:</option>
	      <option value="red">Red</option>
	      <option value="green">Green</option>
	      <option value="blue">Blue</option>
	      <option value="transparent">No Color</option>
	    </select>
	  </li>  
	  <li>
	    Remote Field:
	    <g:remoteField name="remoteFieldName" action="remoteFunctionAction" update="myUpdateRegion2" paramName="color"/>
	  </li>
	  <li>
			Remote Function: 
			<button onclick="renameForDojo('DojoPlugin',34)">Click Me to Activate</button>
		</li>
	  <li>
			Remote Function (Without Dom Elem Update): 
			<button onclick="renameForDojoWithDomElem('DojoPlugin',34)">Click Me to Activate</button>
		</li>
    <li>
      Remote Function (with Form Value Serialization)
      <button onclick="testRemoteFuncWithForm()">Click Me to Submit Form</button>

    </li>
	</ul>
	<div id="myUpdateRegion" style=" width:500px; border:1px solid gray; margin:2em; padding:1em"></div>
	<div id="myUpdateRegion2" style=" width:500px; border:1px solid gray; margin:2em; padding:1em"></div>
</div>



<div style="width:480px; height:500px; float:right; ">

  <g:form name="searchWidgets">
    <h3>Search Form (for the last submit to remote button):</h3>
    <div>
      <label for="keyword">Keyword</label>
      <g:textField name="keyword"/>
    </div>
    <div>
      <label for="widgetName">Widget Name:</label>
      <g:textField name="widgetName"/>
    </div>
  </g:form>

  <g:formRemote name="myForm" update="formUpdateRegion" url="[action:'remoteFormSubmit']">
    <h3>FormRemote:</h3>
    <div>
      <label for="firstName">First Name:</label>
      <g:textField name="firstName"/>
    </div>
    <div>
      <label for="lastName">Last Name:</label>
      <g:textField name="lastName"/>
    </div>
    <g:submitButton name="save" value="Send Form"/>
  </g:formRemote>

  
  <g:formRemote name="myForm2" update="formUpdateRegion" url="[action:'remoteFormSubmitAsGet']" method="Get" on200="onFailure200()">
    <h3>FormRemote with Get:</h3>
    <div>
      <label for="firstName">First Name:</label>
      <g:textField name="firstName"/>
    </div>
    <div>
      <label for="lastName">Last Name:</label>
      <g:textField name="lastName"/>
    </div>
    <g:submitButton name="save" value="Save Form"/>
  </g:formRemote>
   <br/>


  <g:form name="submitToRemoteFrom">
    <h3>Submit To Remote Button</h3>
    <div>
      <label for="state">State:</label>
      <g:textField name="state"/>
    </div>
    <div>
      <label for="zipCode">Zip Code:</label>
      <g:textField name="zipCode"/>
    </div>

    <g:submitToRemote action="submitToRemoteButton" value="Save Data" update="formUpdateRegion"/>
  </g:form>




  <h3>Submit To Remote Button (with a different form)</h3>
  <g:submitToRemote action="submitToRemoteButton" value="Save Data From Search Form" update="formUpdateRegion" formName="searchWidgets"/>


  <div id="formUpdateRegion" style="width:370px; border:1px solid gray; margin-top:2em; padding:1em"></div>
</div>

<fieldset style="clear:both; margin:2em; padding:1em; border:1px solid #ccc; width:80%">
  <legend><h2>Test Dijit Destruction</h2></legend>
  <g:remoteLink action="remoteDijitContent" update="dojoContent" method="post">
    Load Remote Content that has Dojo Components
  </g:remoteLink>

  <dojo:gridResources/>
  <div id="dojoContent" style="border:1px solid blue; margin-top:1em"></div>
</fieldset>

</body>
</html>
<html>
<head>
  <title>Dojo Popover</title>
  <meta name="layout" content="main"/>
	<style>
		li{
			padding:1em;
		}
	</style>

	<dojo:popOverResources/>
  <dojo:helpResources/>
  <script type="text/javascript">
    function showDimensions(){
      var pane = dijit.byId('brokenPopover_content');
      console.log( dojo.style(pane,"height") );
    }

  </script>
</head>
<body class="soria">

	<div style="float:right; margin-right:1em">

		<dojo:frame id="popOverExamples" style="border:1px solid gray">
			<div style="width:400px;">
				<ul>
					<li>
                      <%
                        def myVar = "popOver (label is closure)"
                        def labelClosure = {
                          return "Click to Activate ${myVar}"
                        }
                      %>
						<dojo:popOver label="${labelClosure}" type="button">
							<div style="padding:2em">This is a popover that was activated by a click from a button.</div>
						</dojo:popOver>
                      <dojo:help>Examples of the dojo popOver.</dojo:help>
					</li>
					<li>
						<dojo:popOver label="Load the action" type="button" controller="widget" action="popOverForm" containLinks="true"/>
					</li>
					<li>
						<g:link action="list">Ajax Call (without using a grails tag)</g:link>
					</li>	
					<li>		
						<dojo:popOver label="Open" type="button">
							<dojo:frameLink name="myButton" action="list" frame="popOverExamples">Ajax Call (without using a grails tag)</dojo:frameLink>
						</dojo:popOver>
					</li>
          <li>
            <button type="button" onclick="showDimensions()">Test</button>
          </li>
          <li>
            <dojo:popOver name="brokenPopover" label="Load the action in popOver" type="button" controller="widget" action="popOverForm" containLinks="true"/>
          </li>
				</ul>
			</div>
		</dojo:frame>
		<dojo:frameLink elementId="reloadButton" action="list" frame="popOverExamples">Reload the Pane</dojo:frameLink><br/>

    <dojo:frameLink action="list" frame="popOverExamples" onclick="alert(1)">Reload the Pane (with onclick)</dojo:frameLink><br/>


	</div>
	
	<ul style="float:left">
		<li>		
			<dojo:popOver label="Click to Activate PopOver" type="button">
				<div style="padding:2em">This is a popover that was activated by a click from a button.</div>
			</dojo:popOver>
		</li>
		<li>		
			<dojo:popOver label="Hover to Activate PopOver" type="button" activate="hover">
				<div style="padding:2em">This is a popover that was activated by a mousing over a button.</div>
			</dojo:popOver>
		</li>
		<li>	
			<dojo:popOver label="Click to Activate PopOver" type="link">
				<div style="padding:2em">This is a popover that was activated by a click from a link.</div>
			</dojo:popOver>
		</li>		
		<li>
			<dojo:popOver label="Hover to Activate PopOver" type="link" activate="hover">
				<div style="padding:2em">This is a popover that was activated by a mousing over a link.</div>
			</dojo:popOver>
		</li>	
		<li>	
			<dojo:popOver label="Click to see content." type="link" contentDomId="popOverContentRegion" name="myCustomWindow">
				This content should be replaced with the content inside the dojo:popOverContent tag.
			</dojo:popOver>
			<dojo:popOverContent id="popOverContentRegion">
				<div style="background:#eee; padding:2em; border:1px solid #ccc">
					This is some external content
				</div>	
				<dojo:closePopOver popOver="myCustomWindow">Close</dojo:closePopOver>
				<button onclick="${dojo.closePopOverScript(popOver:'myCustomWindow')}">Close Button</button>
			</dojo:popOverContent>			
		</li>			
		<li>
			<dojo:popOver label="Load the action in popOver" type="button" controller="widget" action="popOverForm" containLinks="true"/>
		</li>
	</ul>
	

<div style="clear:both; margin-top:5em">

</div>
	
	
</body>
</html>
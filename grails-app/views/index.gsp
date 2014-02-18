<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN""http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
  <link rel="shortcut icon" href="${resource()}/images/favicon.ico" type="image/x-icon" />
  <title>Welcome to the Dojo Plugin</title>
  <dojo:header theme="tundra" showSpinner="false"/>
</head>
<body style="background:#ccc;" class="tundra">

	<div style="background:#fff; width:40%;margin:3em auto 0 auto; border:1px solid gray; padding:2em">
		<h2 style="margin-top:0">Welcome to the Dojo Plugin - ${g.meta(name:'app.version')}</h2>
		<h3>
		  <g:link controller="widget" action="index">Try out the Remoting Tags</g:link>
		</h3>
		<h3>
		  <g:link controller="widget" action="plainRemote">Just Remoting Tags (without other dojo dependancies)</g:link>
		</h3>
		<h3>
		  <g:link controller="widget" action="list">Try out the Paginate and Sortable tags</g:link>
		</h3>

		<h3>
		  <g:link controller="widget" action="dijits">Try out the Tab Container</g:link>
		</h3>

		<h3>
		  <g:link controller="widget" action="grid">Check out the Grid and Data Source View</g:link>
		</h3>

		<h3>
		  <g:link controller="widget" action="popOver">Demo the PopOver</g:link>
		</h3>

		<h3>
		  <g:link controller="widget" action="dialog">Demo the Dialog</g:link>
		</h3>

		<h3>
		  <g:link controller="widget" action="editor">Demo the Rich Text Editor</g:link>
		</h3>

		<h3>
		  <g:link controller="widget" action="dateTime">Examples of the Date Time Fields</g:link>
		</h3>

		<h3>
		  <g:link controller="widget" action="tree">Try out the Tree Component</g:link>
		</h3>
		<div style="text-align:center; margin-top:3em">
			<img src="${resource()}/images/dojo.png"/><br/>
			<img src="${resource()}/images/grails.png">
		</div>
	</div>

</body>
</html>

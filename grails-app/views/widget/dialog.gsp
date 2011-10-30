<html>
<head>
  <title>Test the Dialog Screen</title>
  <meta name="layout" content="main"/>
  <dojo:dialogResources/>
  <dojo:frameResources/>
	<dojo:gridResources theme="tundra"/>
	<dojo:dataSourceViewResources/>
</head>

<body>

  <h2>Dialog Content</h2>
  <ul>
    <li>
      <dojo:openDialog dialogId="DialogOne">Open Dialog One (Just static content)</dojo:openDialog>
    </li>
    <li>
      <dojo:openDialog dialogId="DialogTwo">Open Dialog Two (Plain Remote content)</dojo:openDialog>
    </li>
    <li>
      <dojo:openDialog dialogId="DialogThree">Open Dialog Three (Remote Content with ContainLinks)</dojo:openDialog>
    </li>
    <li>
      <dojo:openDialog dialogId="DialogFour">Open Dialog DialogFour (Dojo Grid)</dojo:openDialog>
    </li>
  </ul>
  <h2>Dialog Settings</h2>
  <ul>
    <li>
      <dojo:openDialog dialogId="DialogFive">Open Modeless Dialog</dojo:openDialog>
    </li>
    <li>
      <dojo:openDialog dialogId="DialogSix">Open Dialog that has not close button</dojo:openDialog>
    </li>
  </ul>


  <%-- Dialog One --%>
  <dojo:dialog title="Dialog One" id="DialogOne">
    <h1 style="padding: 3em">Hello. This is dialog One.</h1>
    <dojo:closeDialog dialogId="DialogOne" style="float:right">Close with link</dojo:closeDialog>
    <button onclick="${dojo.closeDialogScript(dialogId: 'DialogOne')}">Close with a button</button>
  </dojo:dialog>

  <%-- Dialog Two --%>
  <dojo:dialog title="Dialog Two" name="DialogTwo" action="getRemoteDialogContent"/>

  <%-- Dialog Three --%>
  <dojo:dialog title="Dialog Three" name="DialogThree" action="getRemoteDialogContentWithLinks" containLinks="true"/>

  <%-- Dialog Four --%>
  <dojo:dialog name="DialogFour" title="Complex grid in a dialog">
    <dojo:grid controller="widget" action="listJson" name="myGrid3" max="20" sort="color"
          style="height:200px; width:750px; border: 1px solid #eee" header="Widgets">
          <dojo:col width="50%" name="Name" field="name">{row.name} ({row.id})</dojo:col>
          <dojo:col width="15%" name="Color" field="color"/>
          <dojo:col width="15%" name="Shape" field="shape"/>
    </dojo:grid>
    <div style="padding: 1em">
      <dojo:closeDialog dialogId="DialogFour" style="float:right">Close</dojo:closeDialog>
    </div>
  </dojo:dialog>

  <%-- Dialog Five --%>
  <dojo:dialog title="Dialog Five - Modeless" name="DialogFive" modeless="true">
    <div style="margin: 5em">
      This content is modeless.<br/>
      You can click on another link to open another window.
    </div>
  </dojo:dialog>

  <%-- Dialog Six --%>
  <dojo:dialog title="Dialog Six - " name="DialogSix" closable="false">
    <div style="margin: 5em">
      This window can't be closed by the little x in the upper right.<br/>
      We have to manually add a close link.
    </div>
    <div style="padding: 1em">
      <dojo:closeDialog dialogId="DialogSix" style="float:right">Close</dojo:closeDialog>
    </div>
  </dojo:dialog>

  <%-- Dialog Six --%>
  <dojo:dialog title="Dialog Seven - Auto Open" name="DialogSeven" visible="true">
    <div style="margin: 5em">
      This window is set to open automatically.
    </div>
    <div style="padding: 1em">
      <dojo:closeDialog dialogId="DialogSeven" style="float:right">Close</dojo:closeDialog>
    </div>
  </dojo:dialog>
</body>
</html>
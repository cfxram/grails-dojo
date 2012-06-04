
<div style="padding: 1em">

  <dojo:popOver label="Open Me" name="openMe">
    <dojo:frameLink frame="remoteTabData" action="dialogAndPopovers" style="margin: 1em">Reload Tab</dojo:frameLink>
    <dojo:closePopOver popOver="openMe" style="margin: 1em">Close</dojo:closePopOver>
  </dojo:popOver>
  <dojo:openDialog dialogId="testDialog">Open Dialog</dojo:openDialog>

  <dojo:dialog name="testDialog" title="Special Dialog">
    <div style="padding: 4em">
      <dojo:frameLink frame="remoteTabData" action="dialogAndPopovers" style="margin: 1em">Reload Tab</dojo:frameLink>
      <dojo:closeDialog dialogId="testDialog">close</dojo:closeDialog>
    </div>
  </dojo:dialog>

  <p>
    <dojo:editor/>
  </p>
</div>
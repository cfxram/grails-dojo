<dojo:onload>
  console.log('An example of < dojo:onload > This is being run from inside of a dialog.');
</dojo:onload>


<div style="margin: 3em; text-align: center">
  <h1 style="margin-top: 0">Content From a remote gsp.</h1>
  <p>
    <g:link action="list" elementId="">This will load new content in the dialog</g:link>
  </p>
  <p>
    <g:link action="list" target="_self">This will not</g:link>
  </p>
</div>



<html>
<head>
  <title>Editor Component</title>
  <meta name="layout" content="main"/>
  <dojo:editorResources/>
  <script type="text/javascript">
    Page = {
      editor:function(){
        return dijit.byId('contentEditor');
      },

      setContent:function(){
        this.editor().set("value","<div>This has double&nbsp;&nbsp;spaces.</div>");
      },

      showContents:function(){
        alert( this.editor().get("value") );
      },

      insertAtCursor:function(){
        this.editor().insertAtCursor("Hello World");
      },

      showHiddenForm:function(){
        alert( this.editor().getContent() );
      }
    }
  </script>
</head>
<body class="tundra">

  <div style="margin: 2em">
    <dojo:editor type="advanced" name="contentEditor" style="width:800px; margin-bottom:1em" debug="true"></dojo:editor>

    <p>
      <button onclick="Page.showContents()" style="margin: 2px">Show Contents</button>
      <button onclick="Page.setContent()" style="margin: 2px">Set Content</button>
      <button onclick="Page.insertAtCursor()" style="margin: 2px">Insert at Cursor</button>
      <button onclick="Page.showHiddenForm()" style="margin: 2px">Show form value</button>
    </p>
    <%--
    <div dojoType="dijit.Editor" style="width:800px; margin-bottom:1em" id="testEditor"></div>
    --%>
  </div>
</body>
</html>
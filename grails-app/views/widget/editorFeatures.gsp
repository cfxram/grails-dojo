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
      },

      showChars:function(){
        this.editor().outputCharacters()
      }
    }
  </script>
</head>
<body class="tundra">

  Content: ${params?.contentEditor}<br/>
  <g:each in="${params?.contentEditor}" var="i">${Character.codePointAt(i?.chars,0)}<br/></g:each>


  <div style="margin: 2em">
    <g:form action="saveEditorFeatures">
      <dojo:editor type="intermediate" name="contentEditor" style="width:800px; margin-bottom:1em;" debug="true" height="150px"></dojo:editor>
      <p>

        <button onclick="Page.showContents()" style="margin: 2px" type="button">Show Contents</button>
        <button onclick="Page.setContent()" style="margin: 2px" type="button">Set Content</button>
        <button onclick="Page.insertAtCursor()" style="margin: 2px" type="button">Insert at Cursor</button>
        <button onclick="Page.showHiddenForm()" style="margin: 2px" type="button">Show form value</button>
        <button onclick="Page.showChars()" type="button">SHow Characters...</button>
      </p>
      <g:submitButton name="save" value="Save Changes"/>
    </g:form>
  </div>
</body>
</html>
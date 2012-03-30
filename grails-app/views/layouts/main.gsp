<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN""http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	  <link rel="shortcut icon" href="${resource()}/images/favicon.ico" type="image/x-icon" />
    <title><g:layoutTitle/></title>
    <meta name="apple-mobile-web-app-capable" content="yes" />
    
    <!--
    <meta name="viewport" content="user-scalable=no, width=device-width" />
    <link rel="apple-touch-icon" href="bookmark-icon.png" />
    -->    
  
    <dojo:header theme="tundra" locale="'en-us'" modulePaths="['appwidgets':'appwidgets']" modules="['dijit.layout.TabContainer','dijit.layout.ContentPane','appwidgets.CustomWidget']" showSpinner="true"/>
    <dojo:menuResources/>

    <g:layoutHead/>

    <style type="text/css">
      .calendar{
        background-image: url(${resource()}/images/date.png);
      }
      .tree{
        background-image: url(${resource()}/images/application_side_tree.png);
      }
      .remoting{
        background-image: url(${resource()}/images/cog.png);
      }
      .editor{
        background-image: url(${resource()}/images/page_white_text.png);
      }
      .grid{
        background-image: url(${resource()}/images/table.png);
      }
      .sort{
        background-image: url(${resource()}/images/style_go.png);
      }
      .tab{
        background-image: url(${resource()}/images/tab.png);
      }
      .dialog{
        background-image: url(${resource()}/images/application_double.png);
      }
      .popUps{
        background-image: url(${resource()}/images/comments.png);
      }
      .home{
        background-image: url(${resource()}/images/house.png);
      }
    </style>
</head>
<body class="tundra">
  <div style="height:30px; border:1px solid #ccc; background:#eee; color:gray">
    <div dojoType="appwidgets.CustomWidget" style="display:inline-block; padding: 8px; float:left"></div>

    <div style="float:right; border-left: 1px solid #ccc; height:30px;">

      <dojo:menu id="menubar" type="bar" style="text-align:right; border:0; padding: 3px;">

        <dojo:menuItem type="bar" onclick="document.location.href='${resource()}'" label="Home"/>

        <dojo:menu id="menu_user" type="barpopup" label="Dojo Widget Tester">
          <dojo:menuItem onclick="document.location.href='${resource()}'" label="Home" iconClass="home"/>
          <dojo:menuSeparator/>

          <dojo:menuItem controller="widget" action="index" label="Try out the Remoting Tags" iconClass="remoting"/>
          <dojo:menuItem controller="widget" action="list" label="Try out the Paginate and Sortable tags" iconClass="sort"/>
          <dojo:menuItem controller="widget" action="dijits" label="Try out the Tab Container" iconClass="tab"/>
          <dojo:menuItem controller="widget" action="popOver" label="Demo the PopOver" iconClass="popUps"/>
          <dojo:menuItem controller="widget" action="dialog" label="Demo the Dialog" iconClass="dialog"/>
          <dojo:menuItem controller="widget" action="editor" label="Demo the Rich Text Editor" iconClass="editor"/>
          <dojo:menuItem controller="widget" action="editorFeatures" label="Editor Features" iconClass="editor"/>
          <dojo:menuItem controller="widget" action="dateTime" label="Examples of the Date Time Fields" iconClass="calendar"/>
          <dojo:menuItem controller="widget" action="panel" label="View the Panel" iconClass="remoting"/>
          <dojo:menu type="popup" label="Advanced Components">
            <dojo:menuItem controller="widget" action="grid" label="Check out the Grid and Data Source View" iconClass="grid"/>
            <dojo:menuItem controller="widget" action="tree" label="Try out the Tree Component" iconClass="tree"/>
          </dojo:menu>
        </dojo:menu>
      </dojo:menu>
      <%--
      <h2 style="margin:4px 1em 0 1em;">Dojo Widget Tester</h2>
      --%>
    </div>
  </div>




  <g:layoutBody/>
</body>
</html>
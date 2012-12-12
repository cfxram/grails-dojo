<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>Remote tags with Positioning</title>
  <meta name="layout" content="main"/>
  <style type="text/css">
    .item{
      border:1px solid gray;
      margin:4px;
    }
    .item-top{
      border:1px solid red;
      margin:4px;
    }

    .item-bottom{
      border:1px solid blue;
      margin:4px;
    }

    .item-before{
      border:1px solid green;
      margin:4px;
    }

    .item-after{
      border:1px solid orange;
      margin:4px;
    }

    .item-insert{
      border:2px solid yellow;
      margin:4px;
    }

    .item-replace{
      border:1px solid pink;
      margin:4px;
      margin:1em; padding:1em;
      width:500px; height:100px;
    }
  </style>
</head>
<body>


<div id="updateDiv" style="margin:1em; padding:1em; width:500px; height:200px; border:1px solid black">
  <div class="item">This is the original item.</div>
</div>

<div id="replaceDiv" style="margin:1em; padding:1em; width:500px; height:100px; border:1px solid black">
  This div will be replaced
</div>

<ul>
  <li>
    <g:remoteLink action="remotePositionTop" update="updateDiv" position="first">Add item above</g:remoteLink>
  </li>
  <li>
    <g:remoteLink action="remotePositionBottom" update="updateDiv" position="last">Add item below</g:remoteLink>
  </li>
  <li>
    <g:remoteLink action="remotePositionBefore" update="updateDiv" position="before">Add item before</g:remoteLink>
  </li>
  <li>
    <g:remoteLink action="remotePositionAfter" update="updateDiv" position="after">Add item after</g:remoteLink>
  </li>
  <li>
    <g:remoteLink action="remotePositionReplace" update="replaceDiv" position="replace">Replace Div</g:remoteLink>
  </li>

  <li>
    <g:remoteLink action="remotePositionFail" update="updateDiv" position="replace">THIS SHOULD FAIL</g:remoteLink>
  </li>
</ul>





</body>
</html>
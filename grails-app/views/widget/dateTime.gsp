<html>
<head>
  <title>Editor Component</title>
  <meta name="layout" content="main"/>
  <dojo:dateTimeResources/>
  <style type="text/css">
    fieldset{
      border: 1px solid #ccc;
      margin: 2em;
      padding: 1em;
      width:400px;
    }
  </style>
</head>
<body>

<fieldset>
  <legend>Date Field:</legend>
  <label for="startDate">End Date:</label>
  <dojo:datePicker name="startDate"/>
</fieldset>

<fieldset>
  <legend>Date Field: (No Earlier date)</legend>
  <label for="startDate">Start Date:</label>
  <dojo:datePicker name="startDate" pickEarlierDate="false"/><br/>
</fieldset>

<fieldset>
  <legend>Date Field (with onChange script):</legend>
  <label for="onChangeDate">Date:</label>
  <dojo:datePicker name="onChangeDate" onChange="alert('Date Field onChange script.');"/>
</fieldset>


<fieldset>
  <legend>Date & Time Field:</legend>
  <label for="closeDate">Close Date:</label>
  <dojo:dateTimePicker name="closeDate"/>
</fieldset>

<fieldset>
  <legend>Date & Time Field: (No earlier date)</legend>
  <label for="closeDate">Close Date:</label>
  <dojo:dateTimePicker name="closeDate"  pickEarlierDate="false"/>
</fieldset>

<fieldset>
  <legend>Date & Time Field (with onChange script):</legend>
  <label for="dateTime">Date:</label>
  <dojo:dateTimePicker name="dateTime" onChange="alert('Date & Time Field onChange script.')"/>
</fieldset>

<fieldset>
  <legend>Time Field:</legend>
  <label for="closeTime">Closing Time:</label>
  <dojo:timerPicker name="closeTime"/>
</fieldset>

<fieldset>
  <legend>Time Field: (with onChange script):</legend>
  <label for="Time">Time:</label>
  <dojo:timerPicker name="Time" onChange="alert('Time Field onChange script.');"/>
</fieldset>


<fieldset>
  <legend>Number Field:</legend>
  <label for="totalDays">How many days?:</label>
  <dojo:numberPicker name="totalDays"/>
</fieldset>

</body>
</html>
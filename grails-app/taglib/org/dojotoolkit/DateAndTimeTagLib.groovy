package org.dojotoolkit

import org.dojotoolkit.TagLibUtil as Util

class DateAndTimeTagLib {
  static namespace = 'dojo'



  /**
   * Outputs the required javascript dojo libraries
   */
  def dateTimeResources = {attrs, body ->
    out << dojo.require(modules: ['dijit.form.DateTextBox', 'dijit.form.TimeTextBox', 'dijit.form.NumberSpinner'])
  }



  /**
   * Creates a grails compatible date picker. This date picker can be used
   * like the <g:datePicker> in the controller.
   */
  def datePicker = {attrs, body ->
    def style = attrs.style ?: ''
    def className = attrs.class ?: ''
    def id = attrs.id ?: "dojo_ui_date${Util.randomId()}"
    def name = attrs.name ?: attrs.id
    def value = attrs.value ?: null
    def defaultToday = attrs.defaultToday ?: 'true'
    def pickEarlierDate = attrs.pickEarlierDate ?: 'true'
    def onChange = attrs.onChange ?: ''

    def c = null
    def day = ""
    def month = ""
    def year = ""
    def dojoDay = ""
    def dojoMonth = ""
    def minDate = ""

    if ((defaultToday == 'true') && (attrs?.value == null)) {
      value = new Date();
    }
    if (value instanceof Calendar) {
      c = value
    }
    else if (value != null) {
      c = new GregorianCalendar();
      c.setTime(value)
    }
    if (c != null) {
      day = c.get(GregorianCalendar.DAY_OF_MONTH).toString()
      month = (c.get(GregorianCalendar.MONTH) + 1).toString()
      year = c.get(GregorianCalendar.YEAR).toString()
      dojoDay = (day.length() == 2) ? day : "0${day}"
      dojoMonth = (month.length() == 2) ? month : "0${month}"
    }

    if (pickEarlierDate == 'false') {
      GregorianCalendar today = new GregorianCalendar();
      def minYear = today.get(GregorianCalendar.YEAR).toString();
      def minMonth =  (today.get(GregorianCalendar.MONTH)+1).toString();
      def minDay = today.get(GregorianCalendar.DAY_OF_MONTH).toString();
      def minMonthFormat = (minMonth.length() == 2) ? minMonth : "0${minMonth}"
      def minDayFormat = (minDay.length() == 2) ? minDay : "0${minDay}"
      minDate = minYear + "-" + minMonthFormat + "-" + minDayFormat
    }

    if (style.length()) {
      style = """ style="${style}" """
    }
    if (className.length()) {
      className = """ class="${className}"  """
    }

    out << """
        <input type="hidden" name="${name}" id="${id}" value="date.struct">
        <input type="hidden" name="${name}_day" id="${id}_day" value="${day}"/>
        <input type="hidden" name="${name}_month" id="${id}_month" value="${month}"/>
        <input type="hidden" name="${name}_year" id="${id}_year" value="${year}"/>
        <div type="text" name="${name}-DateChooser" id="${id}-DateChooser" value="${year}-${dojoMonth}-${dojoDay}" dojoType="dijit.form.DateTextBox" ${style} ${className} constraints="{min:'${minDate}'}">
            <script type="dojo/connect" event="onChange" args="d">
                if(d){
                    dojo.byId("${id}_day").value = d.getDate();
                    dojo.byId("${id}_month").value = d.getMonth()+1;
                    dojo.byId("${id}_year").value = d.getFullYear();
                }
                else{
                    dojo.byId("${id}_day").value = "";
                    dojo.byId("${id}_month").value = "";
                    dojo.byId("${id}_year").value = "";
                }
                $onChange
            </script>
        </div>
    """
  }



  /**
   * Creates a grails compatible time picker. This time picker can be used
   * like the <g:datePicker> in the controller.
   */
  def timerPicker = {attrs, body ->
    def style = attrs.style ?: ''
    def className = attrs.class ?: ''
    def id = attrs.id ?: "dojo_ui_time${Util.randomId()}"
    def name = attrs.name ?: attrs.id
    def value = attrs.value ?: new Date()
    def onChange = attrs.onChange ?: ''

    def c = null
    def minute = ""
    def hour = ""
    def day = ""
    def month = ""
    def year = ""
    def dojoMinute = ""
    def dojoHour = ""

    if (value instanceof Calendar) {
      c = value
    }
    else if (value != null) {
      c = new GregorianCalendar();
      c.setTime(value)
    }
    if (c != null) {
      minute = c.get(GregorianCalendar.MINUTE).toString()
      hour = c.get(GregorianCalendar.HOUR_OF_DAY).toString()
      day = c.get(GregorianCalendar.DAY_OF_MONTH).toString()
      month = (c.get(GregorianCalendar.MONTH) + 1).toString()
      year = c.get(GregorianCalendar.YEAR).toString()
      dojoMinute = (minute.length() == 2) ? minute : "0${minute}"
      dojoHour = (hour.length() == 2) ? hour : "0${hour}"
    }

    if (style.length()) {
      style = """ style="${style}" """
    }
    if (className.length()) {
      className = """ class="${className}"  """
    }
    out << """
        <input type="hidden" name="${name}" id="${id}" value="date.struct">
        <input type="hidden" name="${name}_minute" id="${id}_minute" value="${minute}"/>
        <input type="hidden" name="${name}_hour" id="${id}_hour" value="${hour}"/>
        <input type="hidden" name="${name}_day" id="${id}_day" value="${day}"/>
        <input type="hidden" name="${name}_month" id="${id}_month" value="${month}"/>
        <input type="hidden" name="${name}_year" id="${id}_year" value="${year}"/>
        <div type="text" name="${name}-TimePicker" id="${id}-TimePicker" value="T${dojoHour}:${dojoMinute}:00" dojoType="dijit.form.TimeTextBox" ${style} ${className}>
            <script type="dojo/connect" event="onChange" args="d">
                dojo.byId("${id}_hour").value = d.getHours();
                dojo.byId("${id}_minute").value = d.getMinutes();
                $onChange
            </script>
        </div>
    """
  }

  /**
   * Creates a grails compatible time picker. This date and time picker can be
   * used like the <g:datePicker> in the controller.
   */
  def dateTimePicker = {attrs, body ->
    def style = attrs.style ?: ''
    def className = attrs.class ?: ''
    def id = attrs.id ?: "dojo_ui_dateTime${Util.randomId()}"
    def name = attrs.name ?: attrs.id
    def defaultToday = attrs.defaultToday ?: 'true'
    def pickEarlierDate = attrs.pickEarlierDate ?: 'true'
    def value = attrs.value ?: null
    def onChange = attrs.onChange ?: ''

    def c = null
    def minute = ""
    def hour = ""
    def day = ""
    def month = ""
    def year = ""
    def dojoMinute = ""
    def dojoHour = ""
    def dojoDay = ""
    def dojoMonth = ""
    def minDate = ""


    if ((defaultToday == 'true') && (attrs?.value == null)) {
      value = new Date();
    }
    if (value instanceof Calendar) {
      c = value
    }
    else if (value != null) {
      c = new GregorianCalendar();
      c.setTime(value)
    }
    if (c != null) {
      minute = c.get(GregorianCalendar.MINUTE).toString()
      hour = c.get(GregorianCalendar.HOUR_OF_DAY).toString()
      day = c.get(GregorianCalendar.DAY_OF_MONTH).toString()
      month = (c.get(GregorianCalendar.MONTH) + 1).toString()
      year = c.get(GregorianCalendar.YEAR).toString()
      dojoDay = (day.length() == 2) ? day : "0${day}"
      dojoMonth = (month.length() == 2) ? month : "0${month}"
      dojoMinute = (minute.length() == 2) ? minute : "0${minute}"
      dojoHour = (hour.length() == 2) ? hour : "0${hour}"
    }
    if (pickEarlierDate == 'false') {
      GregorianCalendar today = new GregorianCalendar();
      def minYear = today.get(GregorianCalendar.YEAR).toString();
      def minMonth =  (today.get(GregorianCalendar.MONTH)+1).toString();
      def minDay = today.get(GregorianCalendar.DAY_OF_MONTH).toString();
      def minMonthFormat = (minMonth.length() == 2) ? minMonth : "0${minMonth}"
      def minDayFormat = (minDay.length() == 2) ? minDay : "0${minDay}"
      minDate = minYear + "-" + minMonthFormat + "-" + minDayFormat
    }


    if (style.length()) {
      style = """ style="${style}" """
    }
    if (className.length()) {
      className = """ class="${className}"  """
    }
    out << """
        <input type="hidden" name="${name}" id="${id}" value="date.struct">
        <input type="hidden" name="${name}_minute" id="${id}_minute" value="${minute}"/>
        <input type="hidden" name="${name}_hour" id="${id}_hour" value="${hour}"/>
        <input type="hidden" name="${name}_day" id="${id}_day" value="${day}"/>
        <input type="hidden" name="${name}_month" id="${id}_month" value="${month}"/>
        <input type="hidden" name="${name}_year" id="${id}_year" value="${year}"/>

        <div type="text" name="${name}-DateChooser" id="${id}-DateChooser" value="${year}-${dojoMonth}-${dojoDay}" dojoType="dijit.form.DateTextBox" ${style} ${className} constraints="{min:'${minDate}'}">
            <script type="dojo/connect" event="onChange" args="d">
                dojo.byId("${id}_day").value = d.getDate();
                dojo.byId("${id}_month").value = d.getMonth()+1;
                dojo.byId("${id}_year").value = d.getFullYear();
                $onChange
            </script>
        </div>
        <div type="text" name="${name}-TimeChooser" id="${id}-TimeChooser" value="T${dojoHour}:${dojoMinute}:00" dojoType="dijit.form.TimeTextBox" ${style} ${className}>
            <script type="dojo/connect" event="onChange" args="d">
                dojo.byId("${id}_hour").value = d.getHours();
                dojo.byId("${id}_minute").value = d.getMinutes();
                $onChange
            </script>
        </div>
    """
  }

  /**
   * Creates a number spinner that is also a text field.
   */
  def numberPicker = {attrs, body ->
    def style = attrs.style ?: ''
    def className = attrs.class ?: ''
    def id = attrs.id ?: "dojo_ui_date${Util.randomId()}"
    def name = attrs.name ?: attrs.id
    def value = attrs.value ?: ""
    def disabled = attrs.disabled ?: false
    def disabledText = ""

    if (style.length()) {
      style = """ style="${style}" """
    }
    if (className.length()) {
      className = """ class="${className}"  """
    }
    if (disabled) {
      disabledText = "disabled";
    }
    out << """
        <input id="${id}" ${style} ${className} dojoType="dijit.form.NumberSpinner" value="${value}" constraints="{min:0}" id="${id}" name="${name}" ${disabledText}/>
    """
  }


}

package org.dojotoolkit

import org.dojotoolkit.TagLibUtil as Util

class DateAndTimeTagLib {
  static namespace = 'dojo'



  /**
   * Outputs the required javascript dojo libraries
   * 
   * @deprecated Dojo now automatically imports required classes for parsed widgets from data-dojo-type.  
   */
  @Deprecated
  def dateTimeResources = {attrs, body ->
    out << dojo.require(modules: ['dijit/form/DateTextBox', 'dijit/form/TimeTextBox', 'dijit/form/NumberSpinner'])
  }



  /**
   * Creates a grails compatible date picker. This date picker can be used
   * like the <g:datePicker> in the controller.
   * 
   * Advanced note: Any attributes that are specified on this tag that match an
   * HTML5 attribute will be used directly on the tag. Any other attributes will be passed
   * as settings to the Dojo Widget.
   */
  def datePicker = {attrs, body ->
    def style = attrs.style ?: ''
    def className = attrs.class ?: ''
    def id = attrs.id ?: "dojo_ui_date${Util.randomId()}"
    def name = attrs.name ?: attrs.id
    def value = attrs.value ?: null
    def defaultToday = Util.toBoolean(attrs.defaultToday ?: true);
    def pickEarlierDate = Util.toBoolean(attrs.pickEarlierDate ?: true);
    def c = null
    def day = ""
    def month = ""
    def year = ""
    def dojoDay = ""
    def dojoMonth = ""
    def minDate = ""

    if (defaultToday && (attrs?.value == null)) {
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

    if (!pickEarlierDate) {
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
        <div type="text" name="${name}-DateChooser" id="${id}-DateChooser" value="${year}-${dojoMonth}-${dojoDay}" data-dojo-type="dijit/form/DateTextBox" ${style} ${className} constraints="{min:'${minDate}'}">
            <script type="dojo/aspect" data-dojo-advice="after" data-dojo-method="onChange" data-dojo-args="d">
				require(['dojo/dom'],function(dom) {
	                if(d){
	                    dom.byId("${id}_day").value = d.getDate();
	                    dom.byId("${id}_month").value = d.getMonth()+1;
	                    dom.byId("${id}_year").value = d.getFullYear();
	                }
	                else{
	                    dom.byId("${id}_day").value = "";
	                    dom.byId("${id}_month").value = "";
	                    dom.byId("${id}_year").value = "";
	                }
				});
            </script>
        </div>
    """
  }



  /**
   * Creates a grails compatible time picker. This time picker can be used
   * like the <g:datePicker> in the controller.
   * 
   * Advanced note: Any attributes that are specified on this tag that match an
   * HTML5 attribute will be used directly on the tag. Any other attributes will be passed
   * as settings to the Dojo Widget.
   */
  def timerPicker = {attrs, body ->
    def style = attrs.style ?: ''
    def className = attrs.class ?: ''
    def id = attrs.id ?: "dojo_ui_time${Util.randomId()}"
    def name = attrs.name ?: attrs.id
    def value = attrs.value ?: new Date()
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
        <div type="text" name="${name}-TimePicker" id="${id}-TimePicker" value="T${dojoHour}:${dojoMinute}:00" data-dojo-type="dijit/form/TimeTextBox" ${style} ${className}>
            <script type="dojo/aspect" data-dojo-advice="after" data-dojo-method="onChange" data-dojo-args="d">
				require(['dojo/dom'], function(dom){
                	dom.byId("${id}_hour").value = d.getHours();
					dom.byId("${id}_minute").value = d.getMinutes();
				});
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
    def defaultToday = Util.toBoolean(attrs.defaultToday ?: true);
    def pickEarlierDate = Util.toBoolean(attrs.pickEarlierDate ?: true);
    def value = attrs.value ?: null
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


    if (defaultToday && (attrs?.value == null)) {
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
    if (!pickEarlierDate) {
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

        <div type="text" name="${name}-DateChooser" id="${id}-DateChooser" value="${year}-${dojoMonth}-${dojoDay}" data-dojo-type="dijit/form/DateTextBox" ${style} ${className} data-dojo-props="constraints: {min:'${minDate}'}">
            <script type="dojo/aspect" data-dojo-advice="after" data-dojo-method="onChange" data-dojo-args="d">
				require(['dojo/dom'], function(dom){
					dom.byId("${id}_day").value = d.getDate();
	                dom.byId("${id}_month").value = d.getMonth()+1;
	                dom.byId("${id}_year").value = d.getFullYear();					
				});
            </script>
        </div>
        <div type="text" name="${name}-TimeChooser" id="${id}-TimeChooser" value="T${dojoHour}:${dojoMinute}:00" data-dojo-type="dijit/form/TimeTextBox" ${style} ${className}>
            <script type="dojo/aspect" data-dojo-advice="after" data-dojo-method="onChange" data-dojo-args="d">
				require(['dojo/dom'], function(dom){
                	dom.byId("${id}_hour").value = d.getHours();
					dom.byId("${id}_minute").value = d.getMinutes();
				});
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
        <input id="${id}" ${style} ${className} data-dojo-type="dijit/form/NumberSpinner" value="${value}" data-dojo-props="constraints: {min:0}" id="${id}" name="${name}" ${disabledText}/>
    """
  }


}

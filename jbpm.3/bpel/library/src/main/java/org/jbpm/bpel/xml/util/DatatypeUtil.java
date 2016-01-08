/*
 * JBoss, Home of Professional Open Source
 * Copyright 2005, JBoss Inc., and individual contributors as indicated
 * by the @authors tag.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the JBPM BPEL PUBLIC LICENSE AGREEMENT as
 * published by JBoss Inc.; either version 1.0 of the License, or
 * (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 */
package org.jbpm.bpel.xml.util;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jaxen.dom.DocumentNavigator;
import org.jaxen.function.BooleanFunction;
import org.jaxen.function.StringFunction;
import org.w3c.dom.Node;

/**
 * Provides data type conversion utilities.
 * @author Alejandro Guízar
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:05:59 $
 */
public class DatatypeUtil {

  private static final Pattern dateTimePattern = Pattern.compile("(-?\\p{Digit}{4,})"
      + // year
      "-(\\p{Digit}{2})" + // month
      "-(\\p{Digit}{2})" + // day
      "(?:T" + // begin-time
      "(\\p{Digit}{2})" + // hour
      ":(\\p{Digit}{2})" + // minute
      ":(\\p{Digit}{2})" + // whole seconds
      "(?:\\." + // begin-fractional seconds
      "(\\p{Digit}{1,3})" + // milliseconds
      "\\p{Digit}*" + // time below milliseconds
      ")?" + // end-fractional seconds
      ")?" + // end-time
      "((?:(?:\\+|-)\\p{Digit}{2}:\\p{Digit}{2})|(Z))?"); // timezone

  private static final Log log = LogFactory.getLog(DatatypeUtil.class);

  /* Suppresses default constructor, ensuring non-instantiability */
  private DatatypeUtil() {
  }

  /**
   * Interprets the given value as a string.
   * @param value
   * @return
   */
  public static String toString(Object value) {
    String string;
    if (value instanceof javax.xml.soap.Node) {
      // string value of soap element
      string = ((javax.xml.soap.Node) value).getValue();
    }
    else {
      // string representation of primitive value
      string = StringFunction.evaluate(value, DocumentNavigator.getInstance());
    }
    return string;
  }

  /**
   * Interprets the given value as a Boolean.
   * @see "WS-BPEL 2.0 &sect;8.3.1"
   */
  public static boolean toBoolean(Object value) {
    Boolean bool = null;
    // value is already a Boolean?
    if (value instanceof Boolean) {
      bool = (Boolean) value;
    }
    else {
      // try to interpret as XML Schema boolean
      if (value instanceof Node) {
        bool = parseBoolean(XmlUtil.getStringValue((Node) value));
      }
      else if (value instanceof String) {
        bool = parseBoolean((String) value);
      }
      // if XML Schema interpretation failed, fall back to XPath interpretation
      if (bool == null) {
        bool = BooleanFunction.evaluate(value, DocumentNavigator.getInstance());
      }
    }
    return bool.booleanValue();
  }

  /**
   * Interprets the given value as a deadline.
   * @see "WS-BPEL 2.0 &sect;8.3.2"
   */
  public static Calendar toDateTime(Object value) {
    Calendar dateTime = null;
    if (value instanceof Calendar) {
      dateTime = (Calendar) value;
    }
    else if (value instanceof Date) {
      dateTime = Calendar.getInstance();
      dateTime.setTime((Date) value);
    }
    else {
      dateTime = parseDateTime(toString(value));
    }
    return dateTime;
  }

  /**
   * Interprets the given value as a duration.
   * @see "WS-BPEL 2.0 &sect;8.3.3"
   */
  public static Duration toDuration(Object value) {
    Duration duration = null;
    if (value instanceof Node) {
      duration = Duration.parseDuration(XmlUtil.getStringValue((Node) value));
    }
    else if (value instanceof String) {
      duration = Duration.parseDuration((String) value);
    }
    else if (value instanceof Duration) {
      duration = (Duration) value;
    }
    else {
      log.debug("cannot interpret value as duration: " + value);
    }
    return duration;
  }

  /**
   * Parses the lexical representation of a Boolean as per the XML Schema
   * recommendation.
   * @param text the lexical representation of a boolean
   * @return the constant {@link Boolean#TRUE} if the text corresponds to the
   *         representation of the value <code>true</code>,
   *         {@link Boolean#FALSE} if it corresponds to the value
   *         <code>false</code>, and <code>null</code> in any other case
   * @see <a href="http://www.w3.org/TR/xmlschema-2/#boolean"> XML Schema Part
   *      2: Datatypes &sect;3.2.2</a>
   */
  public static Boolean parseBoolean(String text) {
    Boolean value = null;
    if (text.length() == 1) {
      switch (text.charAt(0)) {
      case '0':
        value = Boolean.FALSE;
        break;
      case '1':
        value = Boolean.TRUE;
        break;
      default:
        log.debug("invalid boolean lexical representation: " + text);
      }
    }
    else if ("true".equals(text)) {
      value = Boolean.TRUE;
    }
    else if ("false".equals(text)) {
      value = Boolean.FALSE;
    }
    else {
      log.debug("invalid boolean lexical representation: " + text);
    }
    return value;
  }

  /**
   * Parses the lexical representation of a date/dateTime as per the XML Schema
   * recommendation.
   * @param text the lexical representation of a date/dateTime
   * @return the date/dateTime the text represents
   * @see <a href="http://www.w3.org/TR/xmlschema-2/#dateTime"> XML Schema Part
   *      2: Datatypes &sect;3.2.7</a>
   */
  public static Calendar parseDateTime(String text) {
    Calendar dateTime = null;

    Matcher matcher = dateTimePattern.matcher(text);
    if (matcher.matches()) {
      dateTime = Calendar.getInstance();
      // calendar is initialized with the current time; must be cleared
      dateTime.clear();
      // group 1: year
      dateTime.set(Calendar.YEAR, Integer.parseInt(matcher.group(1)));
      // group 2: month; the month field is zero-based
      dateTime.set(Calendar.MONTH, Integer.parseInt(matcher.group(2)) - 1);
      // group 3: day
      dateTime.set(Calendar.DAY_OF_MONTH, Integer.parseInt(matcher.group(3)));
      // group 4: hour
      String group = matcher.group(4);
      if (group != null) {
        // if the hour is present, so are minutes and seconds
        dateTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(group));
        // group 5: minute
        dateTime.set(Calendar.MINUTE, Integer.parseInt(matcher.group(5)));
        // group 6: second
        dateTime.set(Calendar.SECOND, Integer.parseInt(matcher.group(6)));
        // group 7: milliseconds
        group = matcher.group(7);
        if (group != null) {
          dateTime.set(Calendar.MILLISECOND, Integer.parseInt(group));
        }
      }
      // group 8: timezone
      group = matcher.group(8);
      if (group != null) {
        TimeZone timeZone;
        if (group.equals("Z")) {
          // "Z" means "the zero-length duration timezone"
          timeZone = TimeZone.getTimeZone("GMT+00:00");
        }
        else {
          timeZone = TimeZone.getTimeZone("GMT" + group);
        }
        dateTime.setTimeZone(timeZone);
      }
      // the schema recommendation specifies strict date interpretation
      dateTime.setLenient(false);
    }
    else {
      log.debug("invalid dateTime lexical representation: " + text);
    }
    return dateTime;
  }
}
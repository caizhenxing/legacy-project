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

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * @author Alejandro Guízar
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:05:59 $
 * @see <a href="http://www.w3.org/TR/xmlschema-2/#duration"> XML Schema Part 2:
 *      Datatypes &sect;3.2.6</a>
 */
public class Duration implements Serializable {

  private static final long serialVersionUID = 1L;

  private short year;
  private short month;
  private short day;
  private short hour;
  private short minute;
  private short second;
  private short millis;

  private boolean negative;

  private static final Pattern durationPattern = Pattern.compile("(-)?" + // negative
                                                                          // sign
      "P" + // designator
      "(?:(\\p{Digit}+)Y)?" + // years
      "(?:(\\p{Digit}+)M)?" + // months
      "(?:(\\p{Digit}+)D)?" + // days
      "(?:T" + // begin-time
      "(?:(\\p{Digit}+)H)?" + // hours
      "(?:(\\p{Digit}+)M)?" + // minutes
      "(?:" + // begin-seconds
      "(\\p{Digit}+)" + // whole seconds
      "(?:\\." + // begin-fractional seconds
      "(\\p{Digit}{1,3})" + // milliseconds
      "\\p{Digit}*" + // duration below milliseconds
      ")?" + // end-fractional seconds
      "S)?" + // end-seconds
      ")?"); // end-time

  public Duration() {
  }

  public Duration(int year, int month, int day, int hour, int minute,
      int second, int millis) {
    setYear((short) year);
    setMonth((short) month);
    setDay((short) day);
    setHour((short) hour);
    setMinute((short) minute);
    setSecond((short) second);
    setMillis((short) millis);
  }

  public boolean isNegative() {
    return negative;
  }

  public void setNegative(boolean negative) {
    this.negative = negative;
  }

  public short getYear() {
    return year;
  }

  public void setYear(short year) {
    this.year = year;
  }

  public short getMonth() {
    return month;
  }

  public void setMonth(short month) {
    this.month = month;
  }

  public short getDay() {
    return day;
  }

  public void setDay(short day) {
    this.day = day;
  }

  public short getHour() {
    return hour;
  }

  public void setHour(short hour) {
    this.hour = hour;
  }

  public short getMinute() {
    return minute;
  }

  public void setMinute(short minute) {
    this.minute = minute;
  }

  public short getSecond() {
    return second;
  }

  public void setSecond(short second) {
    this.second = second;
  }

  public short getMillis() {
    return millis;
  }

  public void setMillis(short millis) {
    this.millis = millis;
  }

  public void addTo(Date dateTime) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(dateTime);
    addTo(calendar);
    dateTime.setTime(calendar.getTimeInMillis());
  }

  public void addTo(Calendar calendar) {
    calendar.add(Calendar.YEAR, year);
    calendar.add(Calendar.MONTH, month);
    calendar.add(Calendar.DAY_OF_MONTH, day);
    calendar.add(Calendar.HOUR_OF_DAY, hour);
    calendar.add(Calendar.MINUTE, minute);
    calendar.add(Calendar.SECOND, second);
    calendar.add(Calendar.MILLISECOND, millis);
  }

  static final long SECOND = 1000;
  static final long MINUTE = 60 * SECOND;
  static final long HOUR = 60 * MINUTE;
  static final long DAY = 24 * HOUR;
  static final long WEEK = 7 * DAY;
  static final long MONTH = 30 * DAY;
  static final long YEAR = 365 * DAY;

  /**
   * Returns the length of this duration in milliseconds. The length of a
   * month/year varies. This method simply assumes a month is 30 days long and a
   * year is 365 days long.
   * @return the number of milliseconds in this duration
   */
  public long getTimeInMillis() {
    long timeInMillis = millis;
    timeInMillis += SECOND * second;
    timeInMillis += MINUTE * minute;
    timeInMillis += HOUR * hour;
    timeInMillis += DAY * day;
    timeInMillis += MONTH * month;
    timeInMillis += YEAR * year;
    return timeInMillis;
  }

  public boolean equals(Object obj) {
    boolean equals = false;
    if (obj instanceof Duration) {
      Duration d = (Duration) obj;
      equals = year == d.year && month == d.month && day == d.day
          && hour == d.hour && minute == d.minute && second == d.second
          && millis == d.millis && negative == d.negative;
    }
    return equals;
  }

  public int hashCode() {
    return new HashCodeBuilder().append(year)
        .append(month)
        .append(day)
        .append(hour)
        .append(minute)
        .append(second)
        .append(millis)
        .append(negative)
        .toHashCode();
  }

  public String toString() {
    StringBuffer literal = new StringBuffer();

    if (negative) literal.append('-');
    literal.append('P');

    if (year != 0) literal.append(year).append('Y');
    if (month != 0) literal.append(month).append('M');
    if (day != 0) literal.append(day).append('D');

    if (hour != 0 || minute != 0 || second != 0 || millis != 0) {
      literal.append('T');

      if (hour != 0) literal.append(hour).append('H');
      if (minute != 0) literal.append(minute).append('M');

      if (second != 0) {
        literal.append(second);

        if (millis != 0) literal.append('.').append(formatDecimal(millis, 3));

        literal.append('S');
      }
      else if (millis != 0)
        literal.append('0').append(formatDecimal(millis, 3)).append('S');
    }
    return literal.toString();
  }

  public static Duration parseDuration(String literal) {
    Matcher matcher = durationPattern.matcher(literal);
    Duration duration = null;
    if (matcher.matches()) {
      duration = new Duration();
      // group 1: negative sign
      if (matcher.group(1) != null) duration.setNegative(true);
      // group 2: years
      String group = matcher.group(2);
      if (group != null) duration.setYear(Short.parseShort(group));
      // group 3: months
      group = matcher.group(3);
      if (group != null) duration.setMonth(Short.parseShort(group));
      // group 4: days
      group = matcher.group(4);
      if (group != null) duration.setDay(Short.parseShort(group));
      // group 5: hours
      group = matcher.group(5);
      if (group != null) duration.setHour(Short.parseShort(group));
      // group 6: minutes
      group = matcher.group(6);
      if (group != null) duration.setMinute(Short.parseShort(group));
      // group 7: seconds
      group = matcher.group(7);
      if (group != null) duration.setSecond(Short.parseShort(group));
      // group 8: milliseconds
      group = matcher.group(8);
      if (group != null) duration.setMillis(parseDecimal(group, 3));
    }
    return duration;
  }

  private static short parseDecimal(String literal, int digitCount) {
    short number = Short.parseShort(literal);

    for (int i = 0, n = digitCount - literal.length(); i < n; i++)
      number *= 10;

    return number;
  }

  private static String formatDecimal(short number, int digitCount) {
    String literal = Short.toString(number);

    if (literal.length() < digitCount) {
      StringBuffer buffer = new StringBuffer();

      for (int i = 0, n = digitCount - literal.length(); i < n; i++) {
        buffer.append('0');
      }
      literal = buffer.append(literal).toString();
    }

    int i = digitCount;
    while (--i > 0 && literal.charAt(i) == '0')
      ; // just decrease the index

    return literal.substring(0, i + 1);
  }
}

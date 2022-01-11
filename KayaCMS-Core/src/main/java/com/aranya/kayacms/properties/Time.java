package com.aranya.kayacms.properties;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.TimeZone;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Time implements Serializable, Comparable<Time> {

  private static final long serialVersionUID = 8046061615034986088L;

  public static final String DATE_FORMAT = "h:mm:ss a";

  private static final String[] ALTERNATIVE_FORMATS =
      new String[] {
        "h:mm:ss a",
        "h:mm:ssa",
        "H:mm:ss",
        "h:mm:ss",
        "h:mm a",
        "h:mma",
        "H:mm",
        "h:mm",
        "h a",
        "ha",
        "H",
        "h",
        "h a",
        "ha",
        "H",
        "h"
      };

  private Calendar cal = null;

  public Time(int second, int minute, int hour) {
    this(second, minute, hour, TimeZone.getDefault());
  }

  public Time(int second, int minute, int hour, TimeZone timeZone) {
    cal = Calendar.getInstance(timeZone);
    cal.set(Calendar.MILLISECOND, 0);
    cal.set(Calendar.SECOND, second);
    cal.set(Calendar.MINUTE, minute);
    cal.set(Calendar.HOUR_OF_DAY, hour);
    cal.set(Calendar.DAY_OF_MONTH, 0);
    cal.set(Calendar.MONTH, 0);
    cal.set(Calendar.YEAR, 0);
  }

  public Time() {
    this(System.currentTimeMillis(), TimeZone.getDefault());
  }

  public Time(TimeZone timeZone) {
    this(System.currentTimeMillis(), timeZone);
  }

  public Time(Time time) {
    this(time.cal.getTimeInMillis(), time.cal.getTimeZone());
  }

  public Time(Instant date) {
    this(date.toEpochMilli(), TimeZone.getDefault());
  }

  public Time(Instant date, TimeZone timeZone) {
    this(date.toEpochMilli(), timeZone);
  }

  public Time(long time) {
    this(time, TimeZone.getDefault());
  }

  public Time(long time, TimeZone timeZone) {
    cal = Calendar.getInstance(timeZone);
    cal.setTimeInMillis(time);
    cal.set(Calendar.MILLISECOND, 0);
    cal.set(Calendar.DAY_OF_MONTH, 0);
    cal.set(Calendar.MONTH, 0);
    cal.set(Calendar.YEAR, 0);
  }

  public Time(String time) throws ParseException {
    this(time, TimeZone.getDefault());
  }

  public Time(String time, TimeZone timeZone) throws ParseException {
    cal = Calendar.getInstance(timeZone);
    fromString(time);
  }

  private void fromString(String time) throws ParseException {
    Instant date = null;
    try {
      SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
      date = Instant.ofEpochMilli(formatter.parse(time).getTime());
      if (log.isInfoEnabled()) {
        log.info("Parsed by " + DATE_FORMAT);
      }
    } catch (ParseException e) {
      // Ignore and try the alternative formatters
      for (int i = 0; i < ALTERNATIVE_FORMATS.length; ++i) {
        SimpleDateFormat formatter = new SimpleDateFormat(ALTERNATIVE_FORMATS[i]);
        try {
          date = Instant.ofEpochMilli(formatter.parse(time).getTime());
          if (log.isInfoEnabled()) {
            log.info("Parsed by " + ALTERNATIVE_FORMATS[i]);
          }
          break;
        } catch (ParseException e2) {
          log.debug(
              "Parse using format {}  of {} failed: {}",
              ALTERNATIVE_FORMATS[i],
              time,
              e.getMessage());
        }
      }
    }
    if (date == null) {
      throw new ParseException("Unable to parse date " + time, 0);
    } else {
      Calendar newCal = Calendar.getInstance();
      newCal.setTimeInMillis(date.toEpochMilli());

      cal.set(Calendar.MILLISECOND, 0);
      cal.set(Calendar.SECOND, newCal.get(Calendar.SECOND));
      cal.set(Calendar.MINUTE, newCal.get(Calendar.MINUTE));
      cal.set(Calendar.HOUR_OF_DAY, newCal.get(Calendar.HOUR_OF_DAY));
      cal.set(Calendar.DAY_OF_MONTH, 0);
      cal.set(Calendar.MONTH, 0);
      cal.set(Calendar.YEAR, 0);
    }
  }

  public Time add(int seconds, int minutes, int hours) {
    Calendar newCal = Calendar.getInstance(cal.getTimeZone());
    newCal.setTime(cal.getTime());
    newCal.add(Calendar.SECOND, seconds);
    newCal.add(Calendar.MINUTE, minutes);
    newCal.add(Calendar.HOUR_OF_DAY, hours);
    return new Time(newCal.getTimeInMillis(), cal.getTimeZone());
  }

  public Time getSeconds(int seconds) {
    Calendar newCal = Calendar.getInstance(cal.getTimeZone());
    newCal.setTime(cal.getTime());
    newCal.set(Calendar.SECOND, seconds);
    return new Time(newCal.getTimeInMillis(), cal.getTimeZone());
  }

  public int getSeconds() {
    return cal.get(Calendar.SECOND);
  }

  public Time getMinutes(int minutes) {
    Calendar newCal = Calendar.getInstance(cal.getTimeZone());
    newCal.setTime(cal.getTime());
    newCal.set(Calendar.MINUTE, minutes);
    return new Time(newCal.getTimeInMillis(), cal.getTimeZone());
  }

  public int getMinutes() {
    return cal.get(Calendar.MINUTE);
  }

  public Time getHours(int hours) {
    Calendar newCal = Calendar.getInstance(cal.getTimeZone());
    newCal.setTime(cal.getTime());
    newCal.set(Calendar.HOUR_OF_DAY, hours);
    return new Time(newCal.getTimeInMillis(), cal.getTimeZone());
  }

  public int getHours() {
    return cal.get(Calendar.HOUR_OF_DAY);
  }

  public Instant getDate() {
    return Instant.ofEpochMilli(cal.getTimeInMillis());
  }

  @Override
  public String toString() {
    return toString("");
  }

  public String toString(String indent) {
    SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
    formatter.setCalendar(cal);
    return indent + formatter.format(cal.getTime());
  }

  public String getFormattedString(String format) {
    SimpleDateFormat formatter = new SimpleDateFormat(format);
    formatter.setCalendar(cal);
    return formatter.format(cal.getTime());
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || !(o instanceof Time)) {
      return false;
    }
    return cal.getTimeInMillis() == ((Time) o).cal.getTimeInMillis();
  }

  @Override
  public int hashCode() {
    return cal.hashCode();
  }

  @Override
  public int compareTo(Time o) {
    if (o == null) {
      return 1;
    }
    long val = cal.getTimeInMillis() - ((Time) o).cal.getTimeInMillis();
    return val < 0 ? -1 : (val > 0) ? 1 : 0;
  }

  public static void main(String... args) {
    for (String arg : args) {
      try {
        Time time = new Time(arg);
        System.out.println(arg + " = " + time);
      } catch (Exception e) {
        e.printStackTrace(System.err);
      }
    }
  }
}

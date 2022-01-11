package com.aranya.kayacms.properties;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.TimeZone;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DayAndTime implements Serializable, Comparable<DayAndTime> {

  private static final long serialVersionUID = 2644957484371943193L;

  public static final String DATE_FORMAT = "M/d/yyyy h:mm:ss a";

  private static final String[] ALTERNATIVE_FORMATS =
      new String[] {
        "M-d-yyyy h:mm:ss a",
        "M-d-yy h:mm:ss a",
        "M/d/yyyy h:mm:ss a",
        "M/d/yy h:mm:ss a",
        "M-d-yyyy h:mm:ssa",
        "M-d-yy h:mm:ssa",
        "M/d/yyyy h:mm:ssa",
        "M/d/yy h:mm:ssa",
        "M-d-yyyy h:mm:ss",
        "M-d-yy h:mm:ss",
        "M/d/yyyy h:mm:ss",
        "M/d/yy h:mm:ss",
        "M-d-yyyy h:mm a",
        "M-d-yy h:mm a",
        "M/d/yyyy h:mm a",
        "M/d/yy h:mm a",
        "M-d-yyyy h:mma",
        "M-d-yy h:mma",
        "M/d/yyyy h:mma",
        "M/d/yy h:mma",
        "M-d-yyyy h:mm",
        "M-d-yy h:mm",
        "M/d/yyyy h:mm",
        "M/d/yy h:mm",
        "M-d-yyyy",
        "M-d-yy",
        "M/d/yyyy",
        "M/d/yy"
      };

  private Calendar cal = null;

  public DayAndTime(int day, int month, int year) {
    this(0, 0, 0, day, month, year, TimeZone.getDefault());
  }

  public DayAndTime(int day, int month, int year, TimeZone timeZone) {
    this(0, 0, 0, day, month, year, timeZone);
  }

  public DayAndTime(int second, int minute, int hour, int day, int month, int year) {
    this(second, minute, hour, day, month, year, TimeZone.getDefault());
  }

  public DayAndTime(
      int second, int minute, int hour, int day, int month, int year, TimeZone timeZone) {
    cal = Calendar.getInstance(timeZone);

    cal.set(Calendar.MILLISECOND, 0);
    cal.set(Calendar.SECOND, second);
    cal.set(Calendar.MINUTE, minute);
    cal.set(Calendar.HOUR_OF_DAY, hour);
    cal.set(Calendar.DAY_OF_MONTH, day);
    cal.set(Calendar.MONTH, month - 1);
    cal.set(Calendar.YEAR, year);
  }

  public DayAndTime() {
    this(System.currentTimeMillis(), TimeZone.getDefault());
  }

  public DayAndTime(TimeZone timeZone) {
    this(System.currentTimeMillis(), timeZone);
  }

  public DayAndTime(DayAndTime day) {
    this(day.cal.getTimeInMillis(), TimeZone.getDefault());
  }

  public DayAndTime(DayAndTime day, TimeZone timeZone) {
    this(day.cal.getTimeInMillis(), timeZone);
  }

  public DayAndTime(Instant date) {
    this(date.toEpochMilli(), TimeZone.getDefault());
  }

  public DayAndTime(Instant date, TimeZone timeZone) {
    this(date.toEpochMilli(), timeZone);
  }

  public DayAndTime(long time) {
    this(time, TimeZone.getDefault());
  }

  public DayAndTime(long time, TimeZone timeZone) {
    cal = Calendar.getInstance(timeZone);
    cal.setTimeInMillis(time);
    cal.set(Calendar.MILLISECOND, 0);
  }

  public DayAndTime(String dayAndTime) throws ParseException {
    this(dayAndTime, TimeZone.getDefault());
  }

  public DayAndTime(String dayAndTime, TimeZone timeZone) throws ParseException {
    cal = Calendar.getInstance(timeZone);
    fromString(dayAndTime);
  }

  private void fromString(String dayAndTime) throws ParseException {
    boolean parsed = false;
    long timeMillis = 0;
    try {
      SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
      timeMillis = formatter.parse(dayAndTime).getTime();
      if (log.isInfoEnabled()) {
        log.info("Parsed by " + DATE_FORMAT);
      }
      parsed = true;
    } catch (ParseException e) {
      // Ignore and try the alternative formatters
      for (int i = 0; i < ALTERNATIVE_FORMATS.length; ++i) {
        SimpleDateFormat formatter = new SimpleDateFormat(ALTERNATIVE_FORMATS[i]);
        try {
          timeMillis = formatter.parse(dayAndTime).getTime();
          if (log.isInfoEnabled()) {
            log.info("Parsed by " + ALTERNATIVE_FORMATS[i]);
          }
          parsed = true;
          break;
        } catch (ParseException e2) {
          log.debug(
              "Parse using format {}  of {} failed: {}",
              ALTERNATIVE_FORMATS[i],
              dayAndTime,
              e.getMessage());
        }
      }
    }
    if (!parsed) {
      throw new ParseException("Unable to parse date " + dayAndTime, 0);
    }

    // Now, if the current year is less than 100, assume they typed in a 2
    // digit year
    if (cal == null) {
      cal = Calendar.getInstance();
    }
    cal.setTimeInMillis(timeMillis);
    if (cal.get(Calendar.YEAR) < 100) {
      Calendar cal2 = Calendar.getInstance();
      int currentCentury = cal2.get(Calendar.YEAR);
      // Strip off the last two digits to just get the century
      currentCentury = currentCentury - (currentCentury % 100);
      cal.add(Calendar.YEAR, currentCentury);
    }
  }

  public DayAndTime getBeginningOfDay() {
    Calendar cal = Calendar.getInstance();
    cal.setTimeInMillis(this.cal.getTimeInMillis());
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);
    return new DayAndTime(cal.getTimeInMillis());
  }

  public DayAndTime getEndOfDay() {
    Calendar cal = Calendar.getInstance();
    cal.setTimeInMillis(this.cal.getTimeInMillis());
    cal.set(Calendar.HOUR_OF_DAY, 23);
    cal.set(Calendar.MINUTE, 59);
    cal.set(Calendar.SECOND, 59);
    cal.set(Calendar.MILLISECOND, 0);
    return new DayAndTime(cal.getTimeInMillis());
  }

  public DayAndTime getBeginningOfMonth() {
    Calendar cal = Calendar.getInstance();
    cal.setTimeInMillis(this.cal.getTimeInMillis());
    cal.set(Calendar.DAY_OF_MONTH, 1);
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);
    return new DayAndTime(cal.getTimeInMillis());
  }

  public DayAndTime getEndOfMonth() {
    Calendar cal = Calendar.getInstance();
    cal.setTimeInMillis(this.cal.getTimeInMillis());
    cal.add(Calendar.MONTH, 1);
    cal.set(Calendar.DAY_OF_MONTH, 1);
    cal.add(Calendar.DAY_OF_MONTH, -1);
    cal.set(Calendar.HOUR_OF_DAY, 23);
    cal.set(Calendar.MINUTE, 59);
    cal.set(Calendar.SECOND, 59);
    cal.set(Calendar.MILLISECOND, 0);
    return new DayAndTime(cal.getTimeInMillis());
  }

  public DayAndTime add(int seconds, int minutes, int hours, int days, int months, int years) {
    Calendar cal = Calendar.getInstance();
    cal.setTimeInMillis(this.cal.getTimeInMillis());
    cal.add(Calendar.SECOND, seconds);
    cal.add(Calendar.MINUTE, minutes);
    cal.add(Calendar.HOUR_OF_DAY, hours);
    cal.add(Calendar.DAY_OF_MONTH, days);
    cal.add(Calendar.MONTH, months);
    cal.add(Calendar.YEAR, years);
    return new DayAndTime(cal.getTimeInMillis());
  }

  public DayAndTime getSeconds(int seconds) {
    Calendar cal = Calendar.getInstance();
    cal.setTimeInMillis(this.cal.getTimeInMillis());
    cal.set(Calendar.SECOND, seconds);
    return new DayAndTime(cal.getTimeInMillis());
  }

  public int getSeconds() {
    return cal.get(Calendar.SECOND);
  }

  public DayAndTime getMinutes(int minutes) {
    Calendar cal = Calendar.getInstance();
    cal.setTimeInMillis(this.cal.getTimeInMillis());
    cal.set(Calendar.MINUTE, minutes);
    return new DayAndTime(cal.getTimeInMillis());
  }

  public int getMinutes() {
    return cal.get(Calendar.MINUTE);
  }

  public DayAndTime getHours(int hours) {
    Calendar cal = Calendar.getInstance();
    cal.setTimeInMillis(this.cal.getTimeInMillis());
    cal.set(Calendar.HOUR_OF_DAY, hours);
    return new DayAndTime(cal.getTimeInMillis());
  }

  public int getHours() {
    return cal.get(Calendar.HOUR_OF_DAY);
  }

  public DayAndTime getDay(int day) {
    Calendar cal = Calendar.getInstance();
    cal.setTimeInMillis(this.cal.getTimeInMillis());
    cal.set(Calendar.DAY_OF_MONTH, day);
    return new DayAndTime(cal.getTimeInMillis());
  }

  public int getDay() {
    return cal.get(Calendar.DAY_OF_MONTH);
  }

  public DayAndTime getMonth(int month) {
    Calendar cal = Calendar.getInstance();
    cal.setTimeInMillis(this.cal.getTimeInMillis());
    cal.set(Calendar.MONTH, month - 1);
    return new DayAndTime(cal.getTimeInMillis());
  }

  public int getMonth() {
    return cal.get(Calendar.MONTH) + 1;
  }

  public DayAndTime getYear(int year) {
    Calendar cal = Calendar.getInstance();
    cal.setTimeInMillis(this.cal.getTimeInMillis());
    cal.set(Calendar.YEAR, year);
    return new DayAndTime(cal.getTimeInMillis());
  }

  public int getYear() {
    return cal.get(Calendar.YEAR);
  }

  public Instant getDate() {
    return Instant.ofEpochMilli(cal.getTimeInMillis());
  }

  public Day getDayObject() {
    return new Day(getDate());
  }

  public Time getTimeObject() {
    return new Time(getDate());
  }

  @Override
  public String toString() {
    return toString("");
  }

  public String toString(String indent) {
    SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
    return indent + formatter.format(cal);
  }

  public String getFormattedString(String format) {
    SimpleDateFormat formatter = new SimpleDateFormat(format);
    return formatter.format(cal);
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || !(o instanceof DayAndTime)) {
      return false;
    }
    return ((DayAndTime) o).cal.getTimeInMillis() == cal.getTimeInMillis();
  }

  @Override
  public int hashCode() {
    return cal.hashCode();
  }

  @Override
  public int compareTo(DayAndTime o) {
    if (o == null) {
      return 1;
    }
    long diff = cal.getTimeInMillis() - o.cal.getTimeInMillis();
    return diff > 0 ? 1 : diff < 0 ? -1 : 0;
  }
}

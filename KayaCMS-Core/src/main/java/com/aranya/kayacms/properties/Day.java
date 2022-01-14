package com.aranya.kayacms.properties;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Aaron
 *     <p>This is a wrapper class to make it easier to deal with and manipulate dates. The class has
 *     been constructed to be immutable.
 */
@Slf4j
public class Day implements Serializable, Comparable<Day> {

  private static final long serialVersionUID = -2657042024453279745L;

  public static final String DATE_FORMAT = "M/d/yyyy";

  private static final String[] ALTERNATIVE_FORMATS =
      new String[] {"M-d-yyyy", "M-d-yy", "M/d/yyyy", "M/d/yy"};

  private Calendar cal = null;

  public Day(int day, int month, int year) {
    cal = Calendar.getInstance();

    cal.set(Calendar.MILLISECOND, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.DAY_OF_MONTH, day);
    cal.set(Calendar.MONTH, month - 1);
    cal.set(Calendar.YEAR, year);

    setToBeginningOfDay();
  }

  public Day() {
    this(System.currentTimeMillis());
  }

  public Day(Day day) {
    this(day.cal.getTimeInMillis());
  }

  public Day(Instant date) {
    this(date.toEpochMilli());
  }

  public Day(long time) {
    cal = Calendar.getInstance();
    cal.setTimeInMillis(time);
    setToBeginningOfDay();
  }

  public Day(String day) throws ParseException {
    fromString(day);
    setToBeginningOfDay();
  }

  private void fromString(String day) throws ParseException {
    boolean parsed = false;
    long timeMillis = 0;
    try {
      SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
      timeMillis = formatter.parse(day).getTime();
      if (log.isInfoEnabled()) {
        log.info("Parsed by " + DATE_FORMAT);
      }
      parsed = true;
    } catch (ParseException e) {
      // Ignore and try the alternative formatters
      for (int i = 0; i < ALTERNATIVE_FORMATS.length; ++i) {
        SimpleDateFormat formatter = new SimpleDateFormat(ALTERNATIVE_FORMATS[i]);
        try {
          timeMillis = formatter.parse(day).getTime();
          if (log.isInfoEnabled()) {
            log.info("Parsed by " + ALTERNATIVE_FORMATS[i]);
          }
          parsed = true;
          break;
        } catch (ParseException e2) {
          log.debug(
              "Parse using format {}  of {} failed: {}",
              ALTERNATIVE_FORMATS[i],
              day,
              e.getMessage());
        }
      }
    }
    if (!parsed) {
      throw new ParseException("Unable to parse date " + day, 0);
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

  private void setToBeginningOfDay() {
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);
  }

  public Day getBeginningOfMonth() {
    Calendar cal = Calendar.getInstance();
    cal.setTimeInMillis(this.cal.getTimeInMillis());
    cal.set(Calendar.DAY_OF_MONTH, 1);
    return new Day(cal.getTimeInMillis());
  }

  public Day getEndOfMonth() {
    Calendar cal = Calendar.getInstance();
    cal.setTimeInMillis(this.cal.getTimeInMillis());
    cal.add(Calendar.MONTH, 1);
    cal.set(Calendar.DAY_OF_MONTH, 1);
    cal.add(Calendar.DAY_OF_MONTH, -1);
    return new Day(cal.getTimeInMillis());
  }

  /**
   * Gets the today plus the number of days we are adding to this day and put into new object.
   *
   * @param days
   * @return
   */
  public Day add(int days, int months, int years) {
    Calendar cal = Calendar.getInstance();
    cal.setTimeInMillis(this.cal.getTimeInMillis());
    cal.add(Calendar.DAY_OF_MONTH, days);
    cal.add(Calendar.MONTH, months);
    cal.add(Calendar.YEAR, years);
    return new Day(cal.getTimeInMillis());
  }

  public Day getDay(int day) {
    Calendar cal = Calendar.getInstance();
    cal.setTimeInMillis(this.cal.getTimeInMillis());
    cal.set(Calendar.DAY_OF_MONTH, day);
    return new Day(cal.getTimeInMillis());
  }

  public int getDay() {
    return cal.get(Calendar.DAY_OF_MONTH);
  }

  public Day getMonth(int month) {
    Calendar cal = Calendar.getInstance();
    cal.setTimeInMillis(this.cal.getTimeInMillis());
    cal.set(Calendar.MONTH, month - 1);
    return new Day(cal.getTimeInMillis());
  }

  public int getMonth() {
    return cal.get(Calendar.MONTH) + 1;
  }

  public Day getYear(int year) {
    Calendar cal = Calendar.getInstance();
    cal.setTimeInMillis(this.cal.getTimeInMillis());
    cal.set(Calendar.YEAR, year);
    return new Day(cal.getTimeInMillis());
  }

  public int getYear() {
    return cal.get(Calendar.YEAR);
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
    return indent + formatter.format(cal.getTime());
  }

  public String getFormattedString(String format) {
    SimpleDateFormat formatter = new SimpleDateFormat(format);
    return formatter.format(cal.getTime());
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || !(o instanceof Day)) {
      return false;
    }
    return ((Day) o).cal.getTimeInMillis() == cal.getTimeInMillis();
  }

  @Override
  public int hashCode() {
    return cal.hashCode();
  }

  @Override
  public int compareTo(Day o) {
    if (o == null) {
      return 1;
    }
    long diff = cal.getTimeInMillis() - o.cal.getTimeInMillis();
    return diff > 0 ? 1 : diff < 0 ? -1 : 0;
  }
}

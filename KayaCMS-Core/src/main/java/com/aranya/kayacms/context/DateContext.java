package com.aranya.kayacms.context;

import com.aranya.kayacms.properties.Day;
import java.text.ParseException;

public class DateContext {

  public Day today() {
    return new Day(System.currentTimeMillis());
  }

  public boolean isToday(String dayString) throws ParseException {
    Day day = new Day(dayString);
    Day now = today();
    return now.compareTo(day) == 0;
  }

  public boolean isBeforeDay(String dayString) throws ParseException {
    Day day = new Day(dayString);
    Day now = today();
    return now.compareTo(day) < 0;
  }

  public boolean isOnOrBeforeDay(String dayString) throws ParseException {
    Day day = new Day(dayString);
    Day now = today();
    return now.compareTo(day) <= 0;
  }

  public boolean isOnOrAfterDay(String dayString) throws ParseException {
    Day day = new Day(dayString);
    Day now = today();
    return now.compareTo(day) >= 0;
  }

  public boolean isAfterDay(String dayString) throws ParseException {
    Day day = new Day(dayString);
    Day now = today();
    return now.compareTo(day) > 0;
  }
}

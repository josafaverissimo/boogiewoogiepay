package com.josafaverissimo.boogiewoogiepay.shared;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public final class Utils {
  private Utils() {
  }

  public static boolean isStrEmpty(String str) {
    return str == null || str == "";
  }

  public static String nowIsoFormat() {
    return ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
  }
}

/*
 * coffeetvshow
 * Copyright (C) 2018  DMSec - @douglasmsi
 * 
 */


package br.com.dmsec.coffeetvshow.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.TimeZone;

import static java.time.LocalDateTime.ofInstant;

public class DateUtils {
    public static LocalDateTime getDateTimeFromTimestamp(long timestamp) {
        if (timestamp == 0) {
            return null;
        }

        return ofInstant(Instant.ofEpochSecond(timestamp), TimeZone.getDefault().toZoneId());
    }

    public static LocalDateTime getDateTime() {
        return ofInstant(Instant.now(), TimeZone.getDefault().toZoneId());
    }

    public static Boolean isTodayOrYesterday(LocalDateTime date) {
        return date.toLocalDate().equals(getDateTime().toLocalDate()) || date.toLocalDate().equals(getDateTime().toLocalDate().minusDays(1));
    }
}

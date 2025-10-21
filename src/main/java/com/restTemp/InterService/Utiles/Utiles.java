package com.restTemp.InterService.Utiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Utiles {

    public BigDecimal parseBigDecimal(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }

        try {
            // Remove commas and parse
            String cleanValue = value.replace(",", "").trim();
            return new BigDecimal(cleanValue);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public LocalDateTime parseDateTime(String dateTimeString) {
        if (dateTimeString == null || dateTimeString.trim().isEmpty()) {
            return null;
        }

        try {
            // Format: "2024-01-31 00:00:00.000 -0800"
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS Z");
            return LocalDateTime.parse(dateTimeString, formatter);
        } catch (DateTimeParseException e) {
            try {
                // Format: "2024-01-31 00:00:00"
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                return LocalDateTime.parse(dateTimeString, formatter);
            } catch (DateTimeParseException e2) {

                return null;
            }
        }
    }
}

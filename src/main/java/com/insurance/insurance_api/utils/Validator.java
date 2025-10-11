package com.insurance.insurance_api.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

public class Validator
{
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );

    private static final Pattern PHONE_PATTERN = Pattern.compile(
            "^\\+\\d{2,3}[\\s\\d]{6,14}$"
    );

    public static void isValidISODate(String date) {
        if (date == null || date.trim().isEmpty()) {
            throw new IllegalArgumentException("Date cannot be null or empty");
        }

        try {
            LocalDate.parse(date.trim(), DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Date must be in ISO 8601 format (yyyy-MM-dd)");
        }
    }

    public static void isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        } else if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Email isn't valid");
        }
    }

    public static void isValidPhoneNumber(String phone) {
        if (phone == null || phone.isEmpty()) {
            throw new IllegalArgumentException("Phone cannot be null or empty");
        } else if (!PHONE_PATTERN.matcher(phone).matches()) {
            throw new IllegalArgumentException("Phone number isn't valid (Use country indicator)");
        }
    }



}

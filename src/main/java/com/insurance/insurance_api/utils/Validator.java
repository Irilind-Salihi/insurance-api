package com.insurance.insurance_api.utils;

import java.math.BigDecimal;
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

    private static final Pattern AMOUNT_PATTERN = Pattern.compile("^\\d+(\\.\\d{1,2})?$");

    public static void isValidISODate(LocalDate date) {
        String dateToString = String.valueOf(date);
        if (dateToString == null || dateToString.trim().isEmpty() || dateToString.trim().equalsIgnoreCase("null")) {
            throw new IllegalArgumentException("Date cannot be null or empty");
        }
        try {
            LocalDate.parse(dateToString.trim(), DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Date must be in ISO 8601 format (yyyy-MM-dd)");
        }
    }

    public static void isValidName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("name cannot be null or empty");
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
            throw new IllegalArgumentException("Phone number cannot be null or empty");
        } else if (!PHONE_PATTERN.matcher(phone).matches()) {
            throw new IllegalArgumentException("Phone number isn't valid (Use country indicator)");
        }
    }

    public static void isValidAmount(BigDecimal costAmount) {
        if (costAmount == null || costAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount cannot negative or null");
        }
        else if (!AMOUNT_PATTERN.matcher(costAmount.toString()).matches()) {
            throw new IllegalArgumentException("Amount is not valid");
        }
    }

    public static void isValidEndDate(LocalDate startDate, LocalDate endDate) {
        if (endDate == null && startDate == null) {
            throw new IllegalArgumentException("startDate and endDate cannot be null");
        }
        else if (endDate != null && !endDate.isAfter(startDate)) {
            throw new IllegalArgumentException("end date must be after start date");
        }
    }



}

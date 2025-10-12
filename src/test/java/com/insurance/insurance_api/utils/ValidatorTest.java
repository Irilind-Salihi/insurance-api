package com.insurance.insurance_api.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ValidatorTest {

    @Test
    void nullDateShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> Validator.isValidISODate(null));
    }

    @Test
    void dateWithIsoFormatShouldNotThrowException() {
        LocalDate localDate = LocalDate.now();
        assertDoesNotThrow(() -> Validator.isValidISODate(localDate));

    }

    @Test
    void nullNameShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> Validator.isValidName(null));
    }

    @Test
    void emptyNameShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> Validator.isValidName(""));
    }

    @Test
    void correctNameShouldNotThrowException() {
        assertDoesNotThrow(() -> Validator.isValidName("Charles"));
    }

    @Test
    void nullEmailShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> Validator.isValidEmail(null));
    }

    @Test
    void emptyEmailShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> Validator.isValidEmail(""));
    }


    @ParameterizedTest
    @ValueSource(strings = {"charles", "charles@gmailcom", "charlesgmail.com","cha/*/*rles@gmail.com", "charles @gmail.com"})
    void invalidEmailFormatShouldThrowException(String email) {
        assertThrows(IllegalArgumentException.class, () -> Validator.isValidEmail(email));
    }
    @Test
    void correctEmailShouldNotThrowException() {
        assertDoesNotThrow(() -> Validator.isValidEmail("charles@mail.ch"));
    }

    @Test
    void nullPhoneShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> Validator.isValidPhoneNumber(null));
    }

    @Test
    void emptyPhoneShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> Validator.isValidPhoneNumber(""));
    }


    @ParameterizedTest
    @ValueSource(strings = {"004195654189156", "+qsd545489", "+","+*/*/", "+11111111111111111111111111111111111111"})
    void invalidPhoneFormatShouldThrowException(String phone) {
        assertThrows(IllegalArgumentException.class, () -> Validator.isValidPhoneNumber(phone));
    }
    @Test
    void correctPhoneShouldNotThrowException() {
        assertDoesNotThrow(() -> Validator.isValidPhoneNumber("+41954548265"));
    }

    @Test
    void nullAmountShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> Validator.isValidAmount(null));
    }



    static Stream<BigDecimal> invalidBigDecimal() {
        return Stream.of(
                new BigDecimal("-548"),
                new BigDecimal("1574.9595"),
                new BigDecimal("-174.9595")
        );
    }

    @ParameterizedTest
    @MethodSource("invalidBigDecimal")
    void invalidAmountFormatShouldThrowException(BigDecimal amount) {
        assertThrows(IllegalArgumentException.class, () -> Validator.isValidAmount(amount));
    }

    @Test
    void correctAmountShouldNotThrowException() {
        BigDecimal amount = new BigDecimal("594.59");
        System.out.println(amount);
        assertDoesNotThrow(() -> Validator.isValidAmount(amount));
    }

    @Test
    void nullStartDateAndEndDateShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> Validator.isValidEndDate(null,null));
    }

    @Test
    void nullStartDateAfterEndDateShouldThrowException() {
        LocalDate startDate = LocalDate.of(2025, 10, 12);
        LocalDate endDate = LocalDate.of(2025, 10, 12);
        assertThrows(IllegalArgumentException.class, () -> Validator.isValidEndDate(endDate, startDate));
    }

    @Test
    void correctDateShouldNotThrowException() {
        LocalDate startDate = LocalDate.of(2025, 10, 12);
        LocalDate endDate = LocalDate.of(20295, 10, 12);

        assertDoesNotThrow(() -> Validator.isValidEndDate(startDate, endDate));    }

}

package com.br.joaoptgaino.loans.validation;

import com.br.joaoptgaino.loans.validation.annotation.CpfValidation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CpfValidationImpl implements ConstraintValidator<CpfValidation,String> {

    @Override
    public void initialize(CpfValidation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.length() != 11 || value.chars().allMatch(x -> x == value.charAt(0))) {
            return false;
        }

        String num = value.substring(0, 9);
        return calculateDigit(num).equals(value.substring(9, 11));
    }

    private static String calculateDigit(String num) {
        int remain;
        int sum = 0;
        String digit;

        for (int i = 0; i < num.length(); i++) {
            sum += Character.getNumericValue(num.charAt(i)) * (10 - i);
        }

        remain = sum % 11;
        digit = (remain < 2) ? "0" : Integer.toString(11 - remain);

        sum = 0;
        num += digit;

        for (int i = 0; i < num.length(); i++) {
            sum += Character.getNumericValue(num.charAt(i)) * (11 - i);
        }

        remain = sum % 11;
        digit += (remain < 2) ? "0" : Integer.toString(11 - remain);

        return digit;
    }
}

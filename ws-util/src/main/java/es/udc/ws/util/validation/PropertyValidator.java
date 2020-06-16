package es.udc.ws.util.validation;

import java.math.BigInteger;

import es.udc.ws.util.exceptions.InputValidationException;

public final class PropertyValidator {

    private PropertyValidator() {}

    public static void validateLong(String propertyName,
            long value, int lowerValidLimit, int upperValidLimit)
            throws InputValidationException {

        if ( (value < lowerValidLimit) || (value > upperValidLimit) ) {
            throw new InputValidationException("Invalid " + propertyName +
                    " value (it must be greater than " + lowerValidLimit +
                    " and lower than " + upperValidLimit + "): " + value);
        }

    }

    public static void validateNotNegativeLong(String propertyName,
            long longValue) throws InputValidationException {

        if (longValue < 0) {
            throw new InputValidationException("Invalid " + propertyName +
                    " value (it must be greater than 0): " +	longValue);
        }

    }

    public static void validateDouble(String propertyName,
            double doubleValue, double lowerValidLimit, double upperValidLimit)
            throws InputValidationException {

        if ((doubleValue < lowerValidLimit) ||
                (doubleValue > upperValidLimit)) {
            throw new InputValidationException("Invalid " + propertyName +
                    " value (it must be gtrater than " + lowerValidLimit +
                    " and lower than " + upperValidLimit + "): " +
                    doubleValue);
        }

    }

    public static void validateMandatoryString(String propertyName,
            String stringValue) throws InputValidationException {

        if ( (stringValue == null) || (stringValue.trim().length() == 0) ) {
            throw new InputValidationException("Invalid " + propertyName +
                    " value (it cannot be null neither empty): " +
                    stringValue);
        }

    }

    public static void validateCreditCard(String propertyValue)
            throws InputValidationException {

        boolean validCreditCard = true;
        if ( (propertyValue != null) && (propertyValue.length() == 16) ) {
            try {
                new BigInteger(propertyValue);
            } catch (NumberFormatException e) {
                validCreditCard = false;
            }
        } else {
            validCreditCard = false;
        }
        if (!validCreditCard) {
            throw new InputValidationException("Invalid credit card number" +
                    " (it should be a sequence of 16 numeric digits): " +
                    propertyValue);
        }

    }

}

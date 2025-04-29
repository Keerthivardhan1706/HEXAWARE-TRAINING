package com.hexaware.studentinformationsystem.exceptions;

public class InsufficientFundsException extends Exception {
    public InsufficientFundsException() {
        super("Insufficient funds to enroll in the course.");
    }

    public InsufficientFundsException(String message) {
        super(message);
    }
}
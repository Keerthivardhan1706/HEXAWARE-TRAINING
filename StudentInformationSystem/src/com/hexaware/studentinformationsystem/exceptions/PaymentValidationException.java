package com.hexaware.studentinformationsystem.exceptions;
public class PaymentValidationException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PaymentValidationException() {
        super("Payment validation failed.");
    }

    public PaymentValidationException(String message) {
        super(message);
    }
}
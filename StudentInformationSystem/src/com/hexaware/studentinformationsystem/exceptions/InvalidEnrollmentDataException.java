package com.hexaware.studentinformationsystem.exceptions;
public class InvalidEnrollmentDataException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidEnrollmentDataException() {
        super("Invalid data provided for enrollment.");
    }

    public InvalidEnrollmentDataException(String message) {
        super(message);
    }
}
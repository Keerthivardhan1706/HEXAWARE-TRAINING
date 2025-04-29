package com.hexaware.studentinformationsystem.exceptions;
public class DuplicateEnrollmentException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DuplicateEnrollmentException() {
        super("Student is already enrolled in this course.");
    }

    public DuplicateEnrollmentException(String message) {
        super(message);
    }
}
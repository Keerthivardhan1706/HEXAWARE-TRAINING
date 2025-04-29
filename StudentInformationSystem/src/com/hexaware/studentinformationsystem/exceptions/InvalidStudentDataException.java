package com.hexaware.studentinformationsystem.exceptions;

public class InvalidStudentDataException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidStudentDataException() {
        super("Invalid data provided for the student.");
    }

    public InvalidStudentDataException(String message) {
        super(message);
    }
}
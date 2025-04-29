package com.hexaware.studentinformationsystem.exceptions;

public class InvalidCourseDataException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidCourseDataException() {
        super("Invalid data provided for the course.");
    }

    public InvalidCourseDataException(String message) {
        super(message);
    }
}
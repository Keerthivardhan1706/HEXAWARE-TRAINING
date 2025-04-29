package com.hexaware.studentinformationsystem.exceptions;

public class CourseNotFoundException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CourseNotFoundException() {
        super("The specified course was not found.");
    }

    public CourseNotFoundException(String message) {
        super(message);
    }
}
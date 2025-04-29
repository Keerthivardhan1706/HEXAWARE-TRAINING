package com.hexaware.studentinformationsystem.exceptions;

public class StudentNotFoundException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public StudentNotFoundException() {
        super("The specified student was not found.");
    }

    public StudentNotFoundException(String message) {
        super(message);
    }
}
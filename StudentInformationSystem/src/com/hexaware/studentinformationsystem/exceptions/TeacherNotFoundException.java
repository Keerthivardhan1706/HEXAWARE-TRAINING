package com.hexaware.studentinformationsystem.exceptions;

public class TeacherNotFoundException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TeacherNotFoundException() {
        super("The specified teacher was not found.");
    }

    public TeacherNotFoundException(String message) {
        super(message);
    }
}
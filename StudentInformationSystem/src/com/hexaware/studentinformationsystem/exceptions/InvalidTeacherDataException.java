package com.hexaware.studentinformationsystem.exceptions;

public class InvalidTeacherDataException extends Exception {
    public InvalidTeacherDataException() {
        super("Invalid data provided for the teacher.");
    }

    public InvalidTeacherDataException(String message) {
        super(message);
    }
}
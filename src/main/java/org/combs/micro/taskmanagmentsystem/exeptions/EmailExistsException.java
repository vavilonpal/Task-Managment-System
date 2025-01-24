package org.combs.micro.taskmanagmentsystem.exeptions;

public class EmailExistsException extends RuntimeException{
    public EmailExistsException(String message) {
        super(message);
    }
}

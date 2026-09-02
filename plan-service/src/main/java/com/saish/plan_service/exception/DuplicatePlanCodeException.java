package com.saish.plan_service.exception;

public class DuplicatePlanCodeException extends RuntimeException {

    public DuplicatePlanCodeException(String message) {
        super(message);
    }
}
package com.Hindol.LinkedIn.Post_Service.Exception;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}

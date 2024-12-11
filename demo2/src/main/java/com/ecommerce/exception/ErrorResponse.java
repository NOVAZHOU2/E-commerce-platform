package com.ecommerce.exception;

public class ErrorResponse {

    private String code;
    private String errorMessage;

    // Constructors
    public ErrorResponse() {}

    public ErrorResponse(String errorCode, String errorMessage) {
        this.code = errorCode;
        this.errorMessage = errorMessage;
    }

    // Getters and Setters
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}

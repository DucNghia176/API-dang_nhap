package com.example.API.exception;

public enum ErrorCode {
    UNCATEGORIZED(404, "Uncategorized error"),
    USER_EXISTED(200, "User existed"),
    USER_NOT_EXISTED(402, "User not exited"),
    UNAUTHENTIC(402,"Unauthenticated")
    ;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

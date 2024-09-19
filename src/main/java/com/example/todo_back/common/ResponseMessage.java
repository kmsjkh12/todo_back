package com.example.todo_back.common;

public interface ResponseMessage {
    String SUCCESS = "Success.";
    String VALIDATION_FAIL = "Validation failed.";
    String RESOURCE_NOT_FOUND = "Resource not found.";
    String DATABASE_ERROR = "Database error.";
    String INTERNAL_SERVER_ERROR = "Internal server error occurred";
    String NOT_MODIFIED = "Resource not modified.";
    String USER_NOT_FOUND = "User not found";
}

package com.example.todo_back.exception;

import com.example.todo_back.dto.ResponseDto;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class, HttpMessageNotReadableException.class, MultipartException.class, MissingServletRequestPartException.class})
    public ResponseEntity<ResponseDto> handleValidationException(Exception e) {
        return ResponseDto.validationFail(e.getMessage());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseDto> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ResponseDto.notFound(ex.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ResponseDto> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        return ResponseDto.databaseError(e.getMessage());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ResponseDto> handleNoHandlerFoundException(NoHandlerFoundException e) {
        return ResponseDto.notFound(e.getMessage());
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ResponseDto> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseDto.userNotFound(ex.getMessage());
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto> handleGenericException(Exception e) {
        e.printStackTrace();
        return ResponseDto.internalServerError(e.getMessage());
    }


}
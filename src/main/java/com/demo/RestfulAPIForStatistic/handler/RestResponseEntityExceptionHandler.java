package com.demo.RestfulAPIForStatistic.handler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author tymofiivoitenko
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler {

    // Handle situation when any of the fields is not parsable
    @ExceptionHandler(InvalidFormatException.class)
    protected ResponseEntity handleFieldsAreInvalid() {
        return new ResponseEntity("", HttpStatus.UNPROCESSABLE_ENTITY);
    }
    
    // Handle situation for null argument
    @ExceptionHandler(JsonMappingException.class)
    protected ResponseEntity handleNullArgument() {
        return new ResponseEntity("", HttpStatus.UNPROCESSABLE_ENTITY);
    }
}

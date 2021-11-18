package com.goudscode.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(value = "com.goudscode.controller")
@Slf4j
public class HandleExceptions {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeExceptions(RuntimeException ex) {
        if (ex instanceof NotFoundException)
           return handleNotFound((NotFoundException) ex);
        else if (ex instanceof InvalidDataException)
            return handleInvalidData((InvalidDataException) ex);
        else if (ex instanceof RoomAlreadyBookedException)
            return handleRoomAlreadyBooked((RoomAlreadyBookedException) ex);
        else
            return handleOtherException(ex);
    }

    private ResponseEntity<Object> handleOtherException(RuntimeException ex) {
        log.error("Exception occurred, reason : {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ex.getMessage());
    }

    public ResponseEntity<Object> handleNotFound(NotFoundException ex) {
        log.error("Resource not found, reason : {}", ex.getMessage());
        return ResponseEntity.status(ex.getStatus())
                .body(ex.toString());
    }

    public ResponseEntity<Object> handleInvalidData(InvalidDataException ex) {
        log.error("Invalid data entered, reason : {}", ex.getMessage());
        return ResponseEntity.status(ex.getStatus())
                .body(ex.toString());
    }

    public ResponseEntity<Object> handleRoomAlreadyBooked(RoomAlreadyBookedException ex) {
        log.error("Exception occurred while booking room, reason : {}", ex.getMessage());
        return ResponseEntity.status(ex.getStatus())
                .body(ex.toString());
    }
}

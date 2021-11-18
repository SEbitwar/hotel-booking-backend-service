package com.goudscode.error;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class InvalidDataException extends RuntimeException{
    private HttpStatus status;
    public InvalidDataException(String message) {
        super(message);
        this.status = HttpStatus.BAD_REQUEST;
    }

    @Override
    public String toString() {
        return "{" +
                "status=" + status +
                ", message='" + this.getMessage() + '\'' +
                '}';
    }
}

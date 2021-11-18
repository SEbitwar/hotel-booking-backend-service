package com.goudscode.error;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class RoomAlreadyBookedException extends RuntimeException{
    private HttpStatus status;
    public RoomAlreadyBookedException(String message) {
        super(message);
        this.status = HttpStatus.BAD_REQUEST;
    }

    @Override
    public String toString() {
        return "{" +
                "status=" + status +
                ", message='" + super.getMessage() + '\'' +
                '}';
    }
}

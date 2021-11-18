package com.goudscode.error;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class NotFoundException extends RuntimeException {

    private HttpStatus status;
    public NotFoundException(String message) {
        super(message);
        this.status = HttpStatus.NOT_FOUND;
    }

    @Override
    public String toString() {
        return "{" +
                "status=" + status +
                ", message='" + super.getMessage() + '\'' +
                '}';
    }
}

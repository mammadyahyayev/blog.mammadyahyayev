package az.blog.resource.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

    private String field;

    public BadRequestException() {
        super();
    }

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String field, String message) {
        super(message);
        this.field = field;
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getField() {
        return field;
    }
}

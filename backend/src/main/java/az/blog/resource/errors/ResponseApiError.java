package az.blog.resource.errors;

import org.springframework.http.HttpStatus;

import java.util.List;

public class ResponseApiError {
    private final double apiVersion;
    private final HttpStatus status;
    private final String message;
    private List<FieldError> fieldErrors;

    public ResponseApiError(double apiVersion, HttpStatus status, String message) {
        this.apiVersion = apiVersion;
        this.status = status;
        this.message = message;
    }

    public ResponseApiError(double apiVersion, HttpStatus status, String message, List<FieldError> fieldErrors) {
        this(apiVersion, status, message);
        this.fieldErrors = fieldErrors;
    }

    public double getApiVersion() {
        return apiVersion;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<FieldError> getFieldErrors() {
        return fieldErrors;
    }
}

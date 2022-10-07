package az.blog.resource.errors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionTranslator extends ResponseEntityExceptionHandler {

    @Value("${api.version}")
    private double apiVersion;

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ResponseApiError> handleBookNotFoundException(BadRequestException e, WebRequest request) {
        ResponseApiError apiError = new ResponseApiError(apiVersion, HttpStatus.BAD_REQUEST, e.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

}

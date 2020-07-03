package br.com.vfs.integrationdynamodb.config;

import br.com.vfs.integrationdynamodb.errors.ConflictHostException;
import br.com.vfs.integrationdynamodb.errors.HostNotFoundException;
import br.com.vfs.integrationdynamodb.model.ErrorMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
@RequiredArgsConstructor
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { HostNotFoundException.class })
    protected ResponseEntity<Object> handleNotFound(final HostNotFoundException ex, final WebRequest request) {
        return handleExceptionInternal(ex, bodyOfResponse(ex.getMessage()), new HttpHeaders(), NOT_FOUND, request);
    }

    @ExceptionHandler(value = { ConflictHostException.class })
    protected ResponseEntity<Object> handleConflict(final ConflictHostException ex, final WebRequest request) {
        return handleExceptionInternal(ex, bodyOfResponse(ex.getMessage()), new HttpHeaders(), CONFLICT, request);
    }

    private ErrorMessage bodyOfResponse(final String message){
        return ErrorMessage.builder().message(message).timestamp(now()).build();
    }
}

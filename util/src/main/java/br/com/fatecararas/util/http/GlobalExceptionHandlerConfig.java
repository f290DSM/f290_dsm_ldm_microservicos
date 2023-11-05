package br.com.fatecararas.util.http;


import br.com.fatecararas.util.http.exceptions.InvalidInputException;
import br.com.fatecararas.util.http.exceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandlerConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandlerConfig.class);

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({NotFoundException.class})
    public HttpError handleNotFoundException(ServerHttpRequest request, NotFoundException exception) {
        return createHttpError(HttpStatus.NOT_FOUND, request, exception);
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler({InvalidInputException.class})
    public HttpError handleInavalidInputException(ServerHttpRequest request, NotFoundException exception) {
        return createHttpError(HttpStatus.UNPROCESSABLE_ENTITY, request, exception);
    }

    private HttpError createHttpError(HttpStatus status, ServerHttpRequest request, Exception exception) {
        final String path = request.getURI().getPath();
        final String message = exception.getMessage();

        LOGGER.error("Return Status HTTP: {}, path: {}, message: {}",status, path, message);

        return new HttpError(status, path, message);
    }
}

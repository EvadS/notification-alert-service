package com.se.service.notification.handler;

import com.se.service.notification.handler.exception.*;
import com.se.service.notification.handler.model.ErrorResponse;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

import static org.springframework.http.HttpStatus.BAD_REQUEST;


/**
 * Created by Evgeniy Skiba on 21.04.21
 */
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(ApiExceptionHandler.class);

    public static final String TRACE = "trace";
    public static final String UNKNOWN_ERROR_OCCURRED = "Unknown error occurred";
    public static final String COMMUNICATION_ERROR_OCCURRED = "Communication error occurred";
    public static final String CONFLICT_MESSAGE = "Conflict in case of concurrent modification";

    @Value("${reflectoring.trace:false}")
    private boolean printStackTrace;

    // VALIDATION ERRORS
    @Override
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Validation error. Check 'errors' field for details.");
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errorResponse.addValidationError(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return ResponseEntity.unprocessableEntity().body(errorResponse);
    }

    // INCORRECT JSON
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Malformed JSON Request");
        return ResponseEntity.unprocessableEntity().body(errorResponse);
    }


    // THERE IS ANY CONTROLLER
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
                                                                   HttpStatus status, WebRequest request) {
        return buildErrorResponse(ex, "No Handler Found", BAD_REQUEST, request);
    }


    // INCORRECT PATH PARAM
    @ExceptionHandler({MethodArgumentTypeMismatchException.class
    })
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                                      WebRequest request) {
        String message = String.format("The parameter '%s' of value '%s' could not be converted to type '%s'",
                ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());
        return buildErrorResponse(ex, message, HttpStatus.NOT_FOUND, request);
    }

    //API ---------------------------------------------------------------------------------------------------------------

    /*******************************************************
     *  NOT FOUND BLOCK
     */
    @ExceptionHandler({ResourceNotFoundException.class,
            ResourceNotFoundException.class,
            NotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleNoSuchElementFoundException(Exception itemNotFoundException, WebRequest request) {
        log.error("Failed to find the requested element", itemNotFoundException);
        return buildErrorResponse(itemNotFoundException, HttpStatus.NOT_FOUND, request);
    }

    /*******************************************************
     * 400 BAD REQUEST BLOCK
     */
    @ExceptionHandler({
            ConstraintViolationException.class,
            IncorrectTemplateException.class, BindTemplateException.class
    })
    public ResponseEntity<Object> handleBadRequest(Exception ex, WebRequest request) {
        return buildErrorResponse(ex, HttpStatus.NOT_FOUND, request);
    }


    /**
     * Handle failures commonly thrown from code
     */
    @ExceptionHandler({InvocationTargetException.class, IllegalArgumentException.class, ClassCastException.class,
            ConversionFailedException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleMiscFailures(Throwable t, WebRequest request) {
        log.error("Unknown error occurred", t);
        return buildErrorResponse(new Exception(t), UNKNOWN_ERROR_OCCURRED, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    /**
     * Send a 409 Conflict in case of concurrent modification
     */
    @ExceptionHandler({
            ObjectOptimisticLockingFailureException.class,
            OptimisticLockingFailureException.class,
            DataIntegrityViolationException.class, NotificationGroupException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity handleUnknownConflict(Exception ex, WebRequest request) {
        log.error("Conflict in case of concurrent modification, {}", ex.getMessage());
        return buildErrorResponse(ex, UNKNOWN_ERROR_OCCURRED, HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler({
            AlreadyExistException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity handleConflict(Exception ex, WebRequest request) {
        log.error(CONFLICT_MESSAGE , ex.getMessage());

        return buildErrorResponse(ex, HttpStatus.CONFLICT, request);
    }


    /**
     * Catch all for any other exceptions...
     */
    @ExceptionHandler({UserComponentException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleCustomServerErrorException(
            RuntimeException exception,
            WebRequest request) {
        log.error(COMMUNICATION_ERROR_OCCURRED, exception);
        return buildErrorResponse(exception, exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler({RuntimeException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleAllUncaughtException(
            RuntimeException exception,
            WebRequest request) {
        log.error("Unknown error occurred, {}", exception);
        return buildErrorResponse(exception, UNKNOWN_ERROR_OCCURRED, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity handleAnyException(Exception e, WebRequest request) {
        log.error("Unknown error occurred, {}", e);
        return buildErrorResponse(e, UNKNOWN_ERROR_OCCURRED, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @Override
    public ResponseEntity<Object> handleExceptionInternal(
            Exception ex,
            Object body,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        return buildErrorResponse(ex, status, request);
    }


    /**************************************
     * buildErrorResponse Block
     **************************************/
    private ResponseEntity<Object> buildErrorResponse(Exception exception, HttpStatus httpStatus,
                                                      WebRequest request) {
        return buildErrorResponse(exception, exception.getMessage(), httpStatus, request);
    }

    private ResponseEntity<Object> buildErrorResponse(Exception exception, String message, HttpStatus httpStatus,
                                                      WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(httpStatus.value(), message);
        if (printStackTrace && isTraceOn(request)) {
            errorResponse.setStackTrace(ExceptionUtils.getStackTrace(exception));
        }
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }


    private boolean isTraceOn(WebRequest request) {
        String[] value = request.getParameterValues(TRACE);
        return Objects.nonNull(value)
                && value.length > 0
                && value[0].contentEquals("true");
    }
}

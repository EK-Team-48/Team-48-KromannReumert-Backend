package com.example.kromannreumert.exception.controller;


import com.example.kromannreumert.exception.customException.ClientNotFoundException;
import com.example.kromannreumert.exception.customException.ForbiddenException;
import com.example.kromannreumert.exception.customException.NotFoundException;
import com.example.kromannreumert.exception.entity.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@Slf4j
@ControllerAdvice
public class ControllerExecptionHandler {


    /**
     * Handle a NotFoundException by returning an HTTP 404 response containing a standardized error body.
     *
     * @param ex  the NotFoundException that was thrown
     * @param req the current WebRequest; its description is included in the error body
     * @return a ResponseEntity containing an ErrorMessage populated with status 404 and details from the exception and request
     */

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorMessage> handleNotFound(NotFoundException ex, WebRequest req) {
        return buildResponse(404, ex, req);
    }

    /**
     * Handle a ForbiddenException and produce an HTTP 403 error response.
     *
     * @param ex  the ForbiddenException that triggered this handler
     * @param req the current web request used to populate error details
     * @return    a ResponseEntity<ErrorMessage> with HTTP status 403 and a standardized error payload
     */
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorMessage> forbiddenAccess(ForbiddenException ex, WebRequest req) {
        return buildResponse(403, ex, req);
    }

    /**
     * Builds a standardized error payload from the provided exception and request, and wraps it in a ResponseEntity
     * with the supplied HTTP status.
     *
     * Constructs an ErrorMessage containing the status code, current timestamp, the exception message, and the
     * request description derived from the WebRequest.
     *
     * @param status the HTTP status code to use for the response
     * @param ex the exception whose message will be included in the error payload
     * @param req the web request used to obtain request-specific description (e.g., path)
     * @return a ResponseEntity whose body is the constructed ErrorMessage and whose status is the provided code
     */
    private ResponseEntity<ErrorMessage> buildResponse(int status, Exception ex, WebRequest req) {
        ErrorMessage msg = new ErrorMessage(
                status,
                new Date(),
                ex.getMessage(),
                req.getDescription(false)
        );
        return ResponseEntity.status(status).body(msg);
    }
}
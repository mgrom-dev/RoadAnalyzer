package ru.mgrom.roadanalyzer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import jakarta.servlet.http.HttpServletRequest;
import ru.mgrom.roadanalyzer.service.SessionUtils;

@ControllerAdvice
public class HttpStatusException {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String notReadableException(HttpMessageNotReadableException ex, HttpServletRequest request) {
        consoleLogSystemInformation(ex, request);
        return "400";
    }

    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFound(NoResourceFoundException ex, HttpServletRequest request) {
        consoleLogSystemInformation(ex, request);
        return "404"; // return 404 error page
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public String methodNotAllowed(HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {
        consoleLogSystemInformation(ex, request);
        return "405";
    }

    private void consoleLogSystemInformation(Exception ex, HttpServletRequest request) {
        System.out.println("Requested URL: " + request.getRequestURL());
        System.out.println("HTTP Method: " + request.getMethod());
        System.out.println("Query Parameters: " + request.getQueryString());
        System.out.println("Session id: " + SessionUtils.getSessionId(request));
        System.out.println(SessionUtils.getUser(request));
    }
}

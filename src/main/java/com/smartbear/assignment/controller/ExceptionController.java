package com.smartbear.assignment.controller;

import com.smartbear.assignment.model.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

@ControllerAdvice
public class ExceptionController {

    private Log logger = LogFactory.getLog(ExceptionController.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> generalException(HttpServletRequest request, Exception e) throws Exception {
        Response eR = new Response();
        eR.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        eR.setDescrition(e.getMessage());
        logger.error("Request " + request.getRequestURI() + " Threw an Exception", e);
        return new ResponseEntity<Response>(eR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({SQLException.class, DataAccessException.class})
    public ResponseEntity<Response> databaseException(HttpServletRequest request, Exception e)  throws Exception{
        Response eR = new Response();
        eR.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        eR.setDescrition("Invalid or Duplicate Data! Please Enter Valid Data");
        logger.error("Request " + request.getRequestURI() + " Threw an Exception", e);
        return new ResponseEntity<Response>(eR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class,IllegalArgumentException.class})
    public ResponseEntity<Response> validationException(HttpServletRequest request, Exception e)  throws Exception{
        Response eR = new Response();
        eR.setCode(HttpStatus.BAD_REQUEST.value());
        eR.setDescrition("Invalid Input Data Format! Please Enter valid Email/Phone Number.");
        logger.error("Request " + request.getRequestURI() + " Threw an Exception", e);
        return new ResponseEntity<Response>(eR, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Response> jsonParseException(HttpServletRequest request, Exception e)  throws Exception{
        Response eR = new Response();
        eR.setCode(HttpStatus.BAD_REQUEST.value());
        eR.setDescrition("Invalid JSON Format!");
        logger.error("Request " + request.getRequestURI() + " Threw an Exception", e);
        return new ResponseEntity<Response>(eR, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Response> noHandlerFoundException(HttpServletRequest request, Exception e)  throws Exception{
        Response eR = new Response();
        eR.setCode(HttpStatus.NOT_FOUND.value());
        eR.setDescrition("Unable to resolve request! No valid Handler Exists");
        logger.error("Request " + request.getRequestURI() + " Threw an Exception", e);
        return new ResponseEntity<Response>(eR, HttpStatus.NOT_FOUND);
    }
}

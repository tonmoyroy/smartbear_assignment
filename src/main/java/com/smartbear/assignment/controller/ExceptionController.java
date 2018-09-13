package com.smartbear.assignment.controller;

import com.smartbear.assignment.model.ExceptionResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

@ControllerAdvice
public class ExceptionController {

    private Log logger = LogFactory.getLog(ExceptionController.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> generalException(HttpServletRequest request, Exception e) throws Exception {
        ExceptionResponse eR = new ExceptionResponse();
        eR.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        eR.setDescrition(e.getMessage());
        logger.error("Request " + request.getRequestURI() + " Threw an Exception", e);
        return new ResponseEntity<ExceptionResponse>(eR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({SQLException.class, DataAccessException.class})
    public ResponseEntity<ExceptionResponse> databaseException(HttpServletRequest request, Exception e)  throws Exception{
        ExceptionResponse eR = new ExceptionResponse();
        eR.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        eR.setDescrition("Invalid or Duplicate Data! Please Enter Valid Data");
        logger.error("Request " + request.getRequestURI() + " Threw an Exception", e);
        return new ResponseEntity<ExceptionResponse>(eR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class,IllegalArgumentException.class})
    public ResponseEntity<ExceptionResponse> ValidationException(HttpServletRequest request, Exception e)  throws Exception{
        ExceptionResponse eR = new ExceptionResponse();
        eR.setCode(HttpStatus.BAD_REQUEST.value());
        eR.setDescrition("Invalid Input Data Format! Please Enter valid Email/Phone Number.");
        logger.error("Request " + request.getRequestURI() + " Threw an Exception", e);
        return new ResponseEntity<ExceptionResponse>(eR, HttpStatus.BAD_REQUEST);
    }
}

package uk.mcb.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class AdapterAdvice {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleException(Exception ex) {
    log.warn("Handler for Exception triggered with message: {}", ex.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
  }
}

package com.VirtualCoffee.orders.config.exceptionhandler;

import com.VirtualCoffee.orders.exception.BeverageUnavailableException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(BeverageUnavailableException.class)
    public ResponseEntity<String> handleBeverageUnavailableException(BeverageUnavailableException ex) {
        return ResponseEntity.status(503).body("Beverage is unavailable");
    }

}

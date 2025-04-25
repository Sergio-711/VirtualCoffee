package com.VirtualCoffee.orders.exception;

public class BeverageUnavailableException extends RuntimeException {
    public BeverageUnavailableException() {
        super("Beverage is unavailable");
    }
}

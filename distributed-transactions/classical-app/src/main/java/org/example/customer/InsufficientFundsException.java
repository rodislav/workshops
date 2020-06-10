package org.example.customer;

public class InsufficientFundsException extends RuntimeException {

    public InsufficientFundsException() {
        super("Insufficient funds");
    }
}

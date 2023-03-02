package com.FlooringMastery.dao;

public class FlooringMasteryPersistenceException extends Exception{

    public FlooringMasteryPersistenceException  (String message) {
        super(message);
    }

    public FlooringMasteryPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }

}

package com.FlooringMastery.service;

public class FlooringMasteryBusinessRulesException extends  Exception{

    public FlooringMasteryBusinessRulesException(String message) {
        super(message);
    }

    public FlooringMasteryBusinessRulesException(String message,
                                              Throwable cause) {
        super(message, cause);
    }
}

package com.FlooringMastery.service;

import com.FlooringMastery.dao.FlooringMasteryPersistenceException;
import com.FlooringMastery.dto.*;



public interface FlooringMasteryService {

    /**will open the Taxes and Products files,
     * then stores them in a map each     *
     * @throws FlooringMasteryPersistenceException
     */
    void readTaxAndProduct() throws FlooringMasteryPersistenceException;

    /**
     * Creates an empty Order Object
     * @return empty Order Object
     */
    Order createEmptyOrder();

    /**Adds a given Order to the order File of its date, only if
     * the given order passes all the business rules.
     * @param addOrder Order object to be added
     */
    void createOrder(Order addOrder) throws FlooringMasteryPersistenceException;

    /**Configures an Order object, to be added to file, to have all the values calculated
     * @return the new Order Object with calculated values
     */
    void configAddOrder(Order order);



    /** It will test the given Order on Order Test rules:
     * Order Date - must be in the future.
     * If any fail, an exception is given
     * @param order Order object to be tested */
    void validateNewOrderDate(Order order) throws FlooringMasteryBusinessRulesException;

    /** It will test the given Order on Customer Test rules:
     * Customer Name - Not blank and is limited to characters [a-z][0-9][,][.]
     * If any fail, an exception is given
     * @param order Order object to be tested */
    void validateNewOrderName(Order order) throws FlooringMasteryBusinessRulesException;

    /** It will test the given Order on State Test rules:
     * State - in given Taxes.txt
     * If any fail, an exception is given
     * @param order Order object to be tested */
    void validateNewOrderState(Order order) throws FlooringMasteryBusinessRulesException;

    /** It will test the given Order on Product Test rules:
     * Product Type - in given Products.txt
     * If any fail, an exception is given
     * @param order Order object to be tested */
    void validateNewOrderProduct(Order order) throws FlooringMasteryBusinessRulesException;

    /** It will test the given Order on Area Test rules:
     * Area - greater than 100 sq ft
     * If any fail, an exception is given
     * @param order Order object to be tested */
    void validateNewOrderArea(Order order) throws FlooringMasteryBusinessRulesException;



}

package com.FlooringMastery.service;

import com.FlooringMastery.dao.FlooringMasteryPersistenceException;
import com.FlooringMastery.dto.*;

import java.time.LocalDate;
import java.util.List;


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

    /**Adds a given Order to the order File of its date
     * @param addOrder Order object to be added
     */
    void createNewOrder(Order addOrder) throws FlooringMasteryPersistenceException;

    /**Configures an Order object, to be added to file, to have all the values calculated
     * @return the new Order Object with calculated values
     */
    void configOrder(Order order);


    /**Reads the order file of the given Order Date into the orderMap then unmarshells it to the Map
     * If the Order doesn't exist, it will throw an exception instead.
     * Use for
     * @param orderDate date of the Order to display
     * @throws FlooringMasteryPersistenceException missing file
     */
    void SearchOrderDateFile(LocalDate orderDate) throws FlooringMasteryPersistenceException;


    /**Returns a List Array of all the Orders in orderMap
     * @return List Array of all the Orders in orderMap
     */
    List<Order> getListOrders();


    /**Finds an Order in the Order Map based on its Order Number.
     * If there is no Order Number in Map, it will return Null
     * @param orderNumber order number of Order
     * @return Order object */
    Order getOrderFromNumber(int orderNumber) throws FlooringMasteryPersistenceException;

    /**Adds new order to the Order map, then writes the Map into the corresponding Order file
     * @param order order object that is being added to Map
     */
    void addOrderToFile(Order order) throws FlooringMasteryPersistenceException;

    /** It will test the given Order on Order Test rules:
     * Order Date - must be in the future.
     * If any fail, an exception is given
     * @param order Order object to be tested */
    void validateNewOrderDate(Order order) throws FlooringMasteryBusinessRulesException;

    /** It will test the given Order on Customer Test rules:
     * Customer Name - is limited to characters [a-z][0-9][,][.].
     * If any fail, an exception is given
     * @param order Order object to be tested */
    void validateNewOrderNameCharacters(Order order) throws FlooringMasteryBusinessRulesException;

    /** It will test the given Order on Customer Test rules:
     * Customer Name - is not Blank
     * If any fail, an exception is given
     * @param order Order object to be tested */
    void validateNewOrderNameBlank(Order order) throws FlooringMasteryBusinessRulesException;

    /** It will test the given Order on State Test rules:
     * State - in given Taxes.txt.
     * If any fail, an exception is given
     * @param order Order object to be tested */
    void validateNewOrderState(Order order) throws FlooringMasteryBusinessRulesException;

    /** It will test the given Order on Product Test rules:
     * Product Type - in given Products.txt.
     * If any fail, an exception is given
     * @param order Order object to be tested */
    void validateNewOrderProduct(Order order) throws FlooringMasteryBusinessRulesException;

    /** It will test the given Order on Area Test rules:
     * Area - greater than 100 sq ft.
     * If any fail, an exception is given
     * @param order Order object to be tested */
    void validateNewOrderArea(Order order) throws FlooringMasteryBusinessRulesException;



}

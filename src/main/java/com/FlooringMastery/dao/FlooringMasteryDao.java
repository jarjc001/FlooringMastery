package com.FlooringMastery.dao;

import com.FlooringMastery.dto.*;
import com.FlooringMastery.dto.FileHeaders;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface FlooringMasteryDao {

    /**Method to get stateTaxMap out of DAO
     * @return stateTaxMap*/
    Map<String, Tax> getStateTaxMap ();

    /**Method to get productTypeMap out of DAO
     * @return productTypeMap*/
    Map<String, Product> getProductTypeMap ();


    /**Finds the next available Order number in the current Order Map*/
    void findNextOrderNumber();

    /**Assigns the next available order number to order object */
    void assignOrderNumber(Order order);

    /**Gets the Order file name from its order date
     *
     * @param orderDate file name as Orders_MMddyyyy.txt
     */
    String getOrderFileName(LocalDate orderDate);


    /**Opens fileName.txt file. It then transfers info into a ArrayList to hold in memory.
     * The file name only needs the name of the file, not the directory path
     * @param fileName name of the file being read (not the directory path)
     * @param fileType the type of file being read (Tax, Product or Order)
     * @return ArrayList of String Arrays of the split up file lines
     */
    List<String[]> readFile(String fileName, FileHeaders fileType)throws FlooringMasteryPersistenceException;

    /**Converts the Info from a Tax file into a Tax map.
     * It clears it before writing in it.
     * @param fileName name of the file being read
     */
    void readTaxFile(String fileName) throws FlooringMasteryPersistenceException;

    /**
     * Converts the Info from a Product file into a Product map.
     * It clears it before writing in it.
     * @param fileName name of the file being read
     */
    void readProductFile(String fileName) throws FlooringMasteryPersistenceException;

    /**
     * Converts the Info from an Order file into an Order map.
     * It clears it before writing in it.
     * @param fileName name of the file being read
     * @param date date of the order
     */
    void readOrderFile(String fileName, LocalDate date) throws FlooringMasteryPersistenceException;


    /**Used to write the Order Map to an Order .txt file
     * @param fileName name of the file being written
     */
    void writeOrderFile(String fileName) throws FlooringMasteryPersistenceException;


    /**Used to write all the orders the Order Map to an Order .txt file
     */
    void writeBackupOrderFile() throws FlooringMasteryPersistenceException;

    /**Used to write a ListArray from an Order file into a Backup.txt file.
     * Appending it, not overwriting it.
     * @param splitLineList ListArray created from readFile method
     */
    void appendToBackupOrderFile(List<String[]> splitLineList, LocalDate date);


    /**Adds a given Order to the order File of its date, only if
     * the given order passes all the business rules.
     * @param addOrder Order object to be added
     */
    void createNewOrder(Order addOrder) throws FlooringMasteryPersistenceException;

    /**Configures an Order object, to be added to file, to have all the values calculated
     * @return the new Order Object with calculated values
     */
    void configOrder(Order order);


    /**Adds new order to the Order map, then writes the Map into the corresponding Order file
     * @param order order object that is being added to Map
     */
    void addOrderToFile(Order order) throws FlooringMasteryPersistenceException;


    /**Reads the order file of the given Order Date into the orderMap then unmarshells it to the Map
     * If the Order doesn't exist, it will throw an exception instead.
     * Use for
     * @param orderDate date of the Order to display
     * @throws FlooringMasteryPersistenceException missing file
     */
    void SearchOrderDateFile(LocalDate orderDate) throws FlooringMasteryPersistenceException;


    /**
     * Returns a List Array of all orders for the current orderMap
     * @return List Array of all orders for the current orderMap
     */
    List<Order> getAllOrders();

    /**Finds an Order in the Order Map based on its Order Number.
     * If there is no Order Number in Map, it will return Null
     * @param orderNumber order number of Order
     * @return Order object
     */
    Order findOrderNumber(int orderNumber) throws FlooringMasteryPersistenceException;


    /**Removes an order from Order Map, then writes the Map onto its
     * corresponding Order file
     * @param order order being removed
     */
    void removeOrderToFile(Order order) throws FlooringMasteryPersistenceException;
}

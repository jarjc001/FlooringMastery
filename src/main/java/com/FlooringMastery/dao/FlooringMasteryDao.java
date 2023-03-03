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


    /**Adds new order to the Order map, then writes the Map into the corresponding Order file
     * @param order order object that is being added to Map
     * @param fileName name of file being written in
     * @return
     */
    Order addOrder(Order order, String fileName) throws FlooringMasteryPersistenceException;



    //public Order addOrder();




    //public abstract










}

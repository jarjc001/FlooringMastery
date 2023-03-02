package com.FlooringMastery.dao;

import com.FlooringMastery.dto.Order;
import com.FlooringMastery.dto.FileHeaders;
import com.FlooringMastery.dto.Product;
import com.FlooringMastery.dto.Tax;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface FlooringMasteryDao {


    /**Opens fileName.txt file. It then transfers info into a ArrayList to hold in memory.
     * The types pf file  on which file is being read:
     * @param fileName name of the file being read (only needed by Order files)
     * @param fileType the type of file being read (Tax, Product or Order)
     * @return ArrayList of String Arrays of the split up file lines
     */
    List<String[]> readFile(String fileName, FileHeaders fileType)throws FlooringMasteryPersistenceException;

    /**Converts the Info from a ListArray into a Tax map
     * @param splitLineList ListArray created from readFile method
     */
    void unmarshallTax(List<String[]> splitLineList);

    /**Converts the Info from a ListArray into a Product map
     * @param splitLineList ListArray created from readFile method
     */
    void unmarshallProduct(List<String[]> splitLineList);

    /**Converts the Info from a ListArray into a Order map
     * @param splitLineList ListArray created from readFile method
     * @param date date of the order
     */
    void unmarshallOrder(List<String[]> splitLineList, LocalDate date);


    /**Used to write an Order Map to an Order .txt file
     * @param fileName name of the file being written
     * @param map contating the info of the orders of that date
     */
    void writeOrderFile(String fileName, Map<Integer, Order> map);

    /**Used to amend a ListArray from an Order file into a Backup.txt file
     * @param splitLineList ListArray created from readFile method
     */
    void writeBackupFile(List<String[]> splitLineList, LocalDate date);







    //public abstract Order addOrder();




    //public abstract










}

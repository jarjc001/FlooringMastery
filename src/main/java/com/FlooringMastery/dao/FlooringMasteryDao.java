package com.FlooringMastery.dao;

import java.util.List;

public interface FlooringMasteryDao {


    /**Opens fileName.txt file. It then transferes info into a Map to hold in memory.
     * The Map depends on which file is being read:
     * stateTaxMap for Taxes.txt,
     * productTypeMap for Products,
     * and any orders file into orderMap.
     * @param fileName name of the file being read
     * @return ArrayList of String Arrays of the split up file lines
     */
    public List<String[]> readFile(String fileName)throws FlooringMasteryPersistenceException;


    //public abstract Order addOrder();



   // public abstract List<Order> getListOrders (LocalDate date, int orderNumber);




    //public abstract










}

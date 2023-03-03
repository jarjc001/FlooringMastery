package com.FlooringMastery.dao;

import com.FlooringMastery.dto.*;
import org.springframework.stereotype.Component;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FlooringMasteryDaoFileImpl implements FlooringMasteryDao{

    /**
     * Constructor
     */
    public FlooringMasteryDaoFileImpl() {
    }

    /** Delimiter of the DVD Library file*/
    private final String DELIMITER = ",";


    /**Map that will temporarily hold Order info from an order .txt file.
     * It will allow the info to be displayed and edited. Then the Map will be
     * used to overwrite the same order .txt file.
     * The key is the order code as an int,
     * The Value is the Order object
     */
    private Map<Integer, Order> orderMap = new HashMap<>();


    /**Map will hold the State Tax info from the "Taxes.txt" file.
     * It will be read at the startup of App.
     * The key is the State's Abbreviation as a String,
     *.The Value is the Tax object containing the state's name and tax rate*/
    private Map<String, Tax> stateTaxMap = new HashMap<>();

    @Override
    public Map<String, Tax> getStateTaxMap (){
        return this.stateTaxMap;
    }

    /**Map will hold the Product type info from the "Products.txt" file.
     * It will be read at the startup of App.
     * The key is the ProductType's name as a String
     *.The Value is the Product object containing its name, cost and labour per square ft*/
    private Map<String, Product> productTypeMap = new HashMap<>();

    @Override
    public Map<String, Product> getProductTypeMap (){
        return this.productTypeMap;
    }


    /** Used to keep track of next available order number*/ //available
    private static int nextavailableOrderNumber= 1;

    @Override
    public void findNextOrderNumber(){
        //restarts at 1
        nextavailableOrderNumber = 1;

        //checks all orders in the map
        for (int orderNumber : orderMap.keySet()) {
            if (orderNumber >= nextavailableOrderNumber) {
                nextavailableOrderNumber = orderNumber+1;
            }
        }

    }


    //<<<<read files>>>>>

    @Override
    public List<String[]> readFile(String fileName, FileHeaders fileType) throws FlooringMasteryPersistenceException {
        List<String[]> splitLineList = new ArrayList<String[]>();
        File dataIn;
        //choose the file being read based on file type
        switch (fileType){
            case TAX:
            case PRODUCT:
                dataIn = new File("./Files/Data/"+fileName);
                break;
            case ORDER:
                dataIn = new File("./Files/Orders/"+fileName);
                break;
            default:
                return null;
        }
        FileReader fileReader;
        String[] lineArray;
        try{
            fileReader = new FileReader(dataIn);
            BufferedReader br = new BufferedReader(fileReader);
            br.readLine();                      //to skip header
            String lineFromLine;
            do{
                lineFromLine = br.readLine();
                if(lineFromLine != null){
                    //Adds the split line to the ArrayList
                    splitLineList.add(lineFromLine.split(DELIMITER));
                }
            }while(lineFromLine != null);

        } catch (FileNotFoundException e) {
            throw new FlooringMasteryPersistenceException("Could not find File - Check name of "+fileName,e);
        } catch (IOException e) {
            throw new FlooringMasteryPersistenceException("I/O error",e);
        }
        return splitLineList;
    }


    @Override
    public void readTaxFile(String fileName) throws FlooringMasteryPersistenceException {
        //Clears the map so only incoming info is in it
        List<String[]> splitLineList = readFile(fileName,FileHeaders.TAX);
        stateTaxMap.clear();

        for(String[] state: splitLineList) {
            //goes through every entry in the List Array and adds them to the Map
            String stateAbbreviation = state[0];
            String stateName = state[1];
            BigDecimal taxRate = new BigDecimal(state[2]);

            Tax inputStateTax = new Tax(stateAbbreviation,stateName,taxRate);

            stateTaxMap.put(stateAbbreviation,inputStateTax);
        }
    }

    @Override
    public void readProductFile(String fileName) throws FlooringMasteryPersistenceException {
        //Clears the map so only incoming info is in it
        List<String[]> splitLineList = readFile(fileName,FileHeaders.PRODUCT);
        productTypeMap.clear();

        for(String[] product: splitLineList) {
            //goes through every entry in the List Array and adds them to the Map
            String productType = product[0];
            BigDecimal costPerSquareFoot = new BigDecimal(product[1]);
            BigDecimal laborCostPerSquareFoot = new BigDecimal(product[2]);

            Product inputProductType = new Product(productType,costPerSquareFoot,laborCostPerSquareFoot);

            productTypeMap.put(productType,inputProductType);
        }
    }

    @Override
    public void readOrderFile(String fileName, LocalDate date) throws FlooringMasteryPersistenceException {
        //Clears the map so only incoming info is in it
        List<String[]> splitLineList = readFile(fileName,FileHeaders.ORDER);
        orderMap.clear();

        for(String[] order: splitLineList) {
            //goes through every entry in the List Array and adds them to the Map
            int orderNumber = Integer.parseInt(order[0]);
            String customerName = order[1];
            BigDecimal area = new BigDecimal(order[5]);

            ///get state object
            String state = order[2];
            Tax inputStateTax = stateTaxMap.get(state);

            //create product object
            String productType = order[4];
            Product inputProductType = productTypeMap.get(productType);

            //creates order Object
            Order inputOrder = new Order(orderNumber,customerName,inputStateTax,inputProductType,area,date);

            //calculates the rest of the Order Info
            inputOrder.configOrderCal();

            orderMap.put(orderNumber,inputOrder);
        }
    }



    //<<<<write files>>>>>


    @Override
    public void writeOrderFile(String fileName) throws FlooringMasteryPersistenceException {
        File dataOut = new File("./Files/Orders/"+fileName);

        FileWriter fileWriter;

        try{
            fileWriter = new FileWriter(dataOut);
            PrintWriter pr = new PrintWriter(fileWriter);

            //Writes header of order file
            pr.println(FileHeaders.ORDER.getHeader());

            for(Order order:orderMap.values()){
                //writes each Order Object into its own line
                pr.println(order.getOrderNumber()+DELIMITER
                        +order.getCustomerName()+DELIMITER
                        +order.getState().getStateName()+DELIMITER
                        +order.getState().getTaxRate()+DELIMITER
                        +order.getProductType().getProductType()+DELIMITER
                        +order.getArea()+DELIMITER
                        +order.getProductType().getCostPerSquareFoot()+DELIMITER
                        +order.getProductType().getLaborCostPerSquareFoot()+DELIMITER
                        +order.getOrderCal().getMaterialCost()+DELIMITER
                        +order.getOrderCal().getLaborCost()+DELIMITER
                        +order.getOrderCal().getTax()+DELIMITER
                        +order.getOrderCal().getTotal());
            }
            pr.flush();
            pr.close();
        } catch (IOException e) {
            throw new FlooringMasteryPersistenceException("Can't write File",e);
        }
    }


    @Override
    public void writeBackupOrderFile() throws FlooringMasteryPersistenceException {
//
//        //loop for every read oreders file here
//
//        File dataOut = new File("./Files/Backup/DataExport.txt");
//
//        FileWriter fileWriter;
//
//        try{
//            fileWriter = new FileWriter(dataOut);
//            PrintWriter pr = new PrintWriter(fileWriter);
//
//            //Writes header of order file
//            pr.println(FileHeaders.BACKUP);
//
//
//        } catch (IOException e) {
//            throw new FlooringMasteryPersistenceException("file not found",e);
//        }
//

    }


    //do later

    @Override
    public void appendToBackupOrderFile(List<String[]> splitLineList, LocalDate date){

        File dataOut = new File("./Files/Backup/DataExport.txt");

        FileWriter fileWriter;

        try{
            fileWriter = new FileWriter(dataOut,true);
            PrintWriter pr = new PrintWriter(fileWriter);

            for(String[] order:splitLineList){
                //writes each Order Object into its own line
                pr.println(order[0]+DELIMITER   //OrderNumber
                        +order[1]+DELIMITER     //CustomerName
                        +order[2]+DELIMITER     //State
                        +order[3]+DELIMITER     //TaxRate
                        +order[4]+DELIMITER     //ProductType
                        +order[5]+DELIMITER     //Area
                        +order[6]+DELIMITER     //CostPerSquareFoot
                        +order[7]+DELIMITER     //LaborCostPerSquareFoot
                        +order[8]+DELIMITER     //MaterialCost
                        +order[9]+DELIMITER     //LaborCost
                        +order[10]+DELIMITER     //Tax
                        +order[11]+DELIMITER     //Total
                        +date.getMonthValue()+"-"+ //date in MMddyyyy
                        date.getDayOfMonth()+"-"+
                        date.getYear());
            }
            pr.flush();
            pr.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }




    @Override
    public Order addOrder(Order order, String fileName) throws FlooringMasteryPersistenceException {
        //sets order number
        findNextOrderNumber();
        order.setOrderNumber(nextavailableOrderNumber);
        //places new order into map
        orderMap.put(order.getOrderNumber(),order);
        //writes order map into file
        writeOrderFile(fileName);
        return order;
    }






}











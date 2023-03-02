package com.FlooringMastery.dao;

import com.FlooringMastery.dto.*;
import org.springframework.core.OrderComparator;
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
     */
    private Map<Integer, Order> orderMap = new HashMap<>();


    /**Map will hold the State Tax info from the "Taxes.txt" file.
     * It will be read at the startup of App.
     * The key is the State's Abbreviation as a String
     *.The Value is the Tax object containing the state's name and tax rate*/
    private Map<String, Tax> stateTaxMap = new HashMap<>();

    /**Map will hold the Product type info from the "Products.txt" file.
     * It will be read at the startup of App.
     * The key is the ProductType's name as a String
     *.The Value is the Product object containing its name, cost and labour per square ft*/
    private Map<String, Product> productTypeMap = new HashMap<>();


    @Override
    public List<String[]> readFile(String fileName, FileHeaders fileType) throws FlooringMasteryPersistenceException{
        List<String[]> splitLineList = new ArrayList<String[]>();
            /// add an if to get the location of the files
        File dataIn = new File(fileName);
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
                    //Adds the split file line to the List
                    splitLineList.add(lineFromLine.split(DELIMITER));


                    //add a switch here?


                }
            }while(lineFromLine != null);

        } catch (FileNotFoundException e) {
            throw new FlooringMasteryPersistenceException("Could not find File",e);
        } catch (IOException e) {
            throw new FlooringMasteryPersistenceException("errer",e);
        }
        return splitLineList;
    }


    @Override
    public void unmarshallTax(List<String[]> splitLineList){
        //Clears the map so only incoming info is in it
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
    public void unmarshallProduct(List<String[]> splitLineList){
        //Clears the map so only incoming info is in it
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
    public void unmarshallOrder(List<String[]> splitLineList, LocalDate date){
        //Clears the map so only incoming info is in it
        orderMap.clear();
        Order.resetOverallOrderNumber();

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


            orderMap.put(orderNumber,inputOrder);
        }
    }








    @Override
    public void writeOrderFile(String fileName, Map<Integer, Order> map){
        File dataOut = new File(fileName);
        FileWriter fileWriter;

        try{
            fileWriter = new FileWriter(dataOut);
            PrintWriter pr = new PrintWriter(fileWriter);

            //Writes header of order file
            pr.println(FileHeaders.ORDER);

            for(Order order:map.values()){
                //writes each Order Object into its own line
                pr.println(order.getOrderNumber()+DELIMITER
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
            throw new RuntimeException(e);
        }
    }


    public void writeBackupFile(String fileName, Map<Integer, Order> map){

    }









}

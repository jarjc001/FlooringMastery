package com.FlooringMastery.dao;

import com.FlooringMastery.dto.*;
import org.springframework.stereotype.Component;

import java.io.*;
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
    public List<String[]> readFile(String fileName) throws FlooringMasteryPersistenceException{
        List<String[]> splitLineList = new ArrayList<String[]>();
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
                }
            }while(lineFromLine != null);

        } catch (FileNotFoundException e) {
            throw new FlooringMasteryPersistenceException("Could not find File",e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return splitLineList;
    }



   // unmarshall for product, tax, order  use enum



    @Override
    public void writeFile(String fileName, Map<> map){
        File dataOut = new File(fileName);
        FileWriter fileWriter;

        try{
            fileWriter = new FileWriter(dataOut);
            PrintWriter pr = new PrintWriter(fileWriter);

            for(Dvd dvd:dvdLibrary.values()){
                pr.println(dvd.getTitle()+DELIMITER
                        +dvd.getDvdInfo().getReleaseDate()+DELIMITER
                        +dvd.getDvdInfo().getRatingMPAA()+DELIMITER
                        +dvd.getDvdInfo().getDirector()+DELIMITER
                        +dvd.getDvdInfo().getStudio()+DELIMITER
                        +dvd.getDvdInfo().getUserNotes()+DELIMITER);
            }
            pr.flush();
            pr.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }










    }






}

package com.FlooringMastery.service;

import com.FlooringMastery.dao.FlooringMasteryPersistenceException;
import com.FlooringMastery.dto.*;

import java.util.Arrays;


import com.FlooringMastery.dao.FlooringMasteryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
public class FlooringMasteryServiceImpl implements FlooringMasteryService{

    /**Declaration of the Dao*/
    private FlooringMasteryDao dao;

    /**
     * Constructor
     * @param dao FlooringMasteryDaoFileImpl
     */
    @Autowired
    public FlooringMasteryServiceImpl(FlooringMasteryDao dao) {
        this.dao = dao;
    }

    @Override
    public void readTaxAndProduct() throws FlooringMasteryPersistenceException{
        dao.readTaxFile("Taxes.txt");
        dao.readProductFile("Products.txt");
    }




   // <<Add order>>

    @Override
    public Order createEmptyOrder(){
        return new Order();
    }


    @Override
    public void createNewOrder(Order order) throws FlooringMasteryPersistenceException {
        dao.createNewOrder(order);
    }


    @Override
    public void configOrder(Order order){
        dao.configOrder(order);
    }



    //<<search Order date file>>

    @Override
    public void SearchOrderDateFile(LocalDate orderDate) throws FlooringMasteryPersistenceException {

        dao.SearchOrderDateFile(orderDate);
    }


    //<<display orders>>

    @Override
    public List<Order> getListOrders(){
        return dao.getAllOrders();
    }




    //<<edit>>

    @Override
    public Order getOrderFromNumber(int orderNumber) throws FlooringMasteryPersistenceException {
        return dao.findOrderNumber(orderNumber);
    }


    @Override
    public void addOrderToFile(Order order) throws FlooringMasteryPersistenceException{
        dao.addOrderToFile(order);
    }





















    //<<Business rules>>


    @Override
    public void validateNewOrderDate(Order order) throws FlooringMasteryBusinessRulesException {
        LocalDate orderDate = order.getOrderDate();
        LocalDate todayDate = LocalDate.now();

        //Days different between order date and today's date
        int daysDiff = orderDate.compareTo(todayDate);

        if(!(daysDiff > 0)) { //if order date is today or in past
            throw new FlooringMasteryBusinessRulesException(
                    "ERROR - Order Date must be in the future.");
        }
    }

    @Override
    public void validateNewOrderNameCharacters(Order order) throws FlooringMasteryBusinessRulesException {
        boolean failTest = false;

        //removes space from name
        String nameNoSpace = order.getCustomerName().replace(" ", "");
        //chars that are allowed
        char[] allowedChars = {'.', ','};
        Arrays.sort(allowedChars); //In order to use BinarySearch
        for(int i =0; i<nameNoSpace.length();i++) {
            char x = nameNoSpace.charAt(i);
            if (Arrays.binarySearch(allowedChars, x) != -1) {   //is  , or .
                failTest = false;
            } else if (Character.isAlphabetic(x)) { //is [a-zA-Z]
                failTest = false;
            } else if (!Character.isDigit(x)) { //is not [0-9]
                failTest = true;
                break;
            }
        }

        //Name is empty,
        if ( failTest ){
            throw new FlooringMasteryBusinessRulesException(
                    "ERROR - Customer Name is limited to characters [a-z][0-9][,][.].");
        }
    }


    @Override
    public void validateNewOrderNameBlank(Order order) throws FlooringMasteryBusinessRulesException {
        //removes space from name
        String nameNoSpace = order.getCustomerName().replace(" ", "");

        //Name is empty,
        if (nameNoSpace.length() <1){
            throw new FlooringMasteryBusinessRulesException(
                    "ERROR - Customer Name must not be blank.");
        }
    }

    @Override
    public void validateNewOrderState(Order order) throws FlooringMasteryBusinessRulesException {
        //tracks if the name fails the test
        boolean failTest = true;

        //checks each State in Tax file
        for (Tax state: dao.getStateTaxMap().values()){
            if(order.getState().getStateAbbreviation().equals(state.getStateAbbreviation())){
                failTest = false;

            }
        }

        if (failTest){ // state in state file
            throw new FlooringMasteryBusinessRulesException(
                    "ERROR - State not in Taxes.txt file.");
        }
    }

    @Override
    public void validateNewOrderProduct(Order order) throws FlooringMasteryBusinessRulesException {
        //tracks if the name fails the test
        boolean failTest = true;

        //checks each Product type in product file
        for (Product product: dao.getProductTypeMap().values()){
            if(order.getProductType().getProductType().equals(product.getProductType())){
                failTest = false;

            }
        }

        if (failTest){ // Products in Products file
            throw new FlooringMasteryBusinessRulesException(
                    "ERROR - Product type not in Products.txt file.");
        }
    }

    @Override
    public void validateNewOrderArea(Order order) throws FlooringMasteryBusinessRulesException {
        if(order.getArea().compareTo(BigDecimal.valueOf(100)) == -1){ //Area less than 100 sq ft
            throw new FlooringMasteryBusinessRulesException(
                    "ERROR - Area must be 100 sq ft or more.");
        }

    }


}

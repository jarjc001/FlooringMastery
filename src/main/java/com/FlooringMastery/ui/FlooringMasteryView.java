package com.FlooringMastery.ui;

import com.FlooringMastery.dto.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class FlooringMasteryView {

    /**Declaration of the UserIO*/
    private UserIO io;

    /**Constructor
     * @param io UserIOConsoleImpl */
    @Autowired
    public FlooringMasteryView(UserIO io) {
        this.io = io;
    }


    /**
     * Prints the Main menu's options on console,
     * then returns an int of the user's choice
     * @return - an int of the user's choice
     */
    public int printMainMenu(){
        io.print("");
        io.print("-+*+-+*+-+*+-+*+-+*+-+*+-+*+-+*+-+*+-+*+-+*+-+*+-");
        io.print("<<Flooring Program>>");
        io.print("1. Display Orders");
        io.print("2. Add an Order");
        io.print("3. Edit an Order");
        io.print("4. Remove an Order");
        io.print("5. Export All Data");
        io.print("6. Quit");
        io.print("");
        io.print("-+*+-+*+-+*+-+*+-+*+-+*+-+*+-+*+-+*+-+*+-+*+-+*+-");

        return io.readInt("", 1, 6);
    }


    public void getAddOrderBanner(){
        io.print("-+*+-+*+-+*+-   Add an Order   -+*+-+*+-+*+-");
    }

   /**Prompts the user to fill in the Date of the Order they want to add,
    * Then sets info to an empty Order object*/
    public void getNewOrderDate(Order order){
        order.setOrderDate(io.readLocalDate("Please enter Order Date"));
    }

    /**Prompts the user to fill in the Customer Name of the Order they want to add,
     * Then sets info to an empty Order object*/
    public void getNewOrderName(Order order){
        order.setCustomerName(io.readString("Please enter Customer Name:"));

    }

    /**Prompts the user to fill in the State  of the Order they want to add,
     * Then sets info to an empty Order object*/
    public void getNewOrderState(Order order){
        order.getState().setStateAbbreviation(io.readString("Please enter State:").toUpperCase());
    }

    /**Prompts the user to fill in the Product Type of the Order they want to add,
     * Then sets info to an empty Order object*/
    public void getNewOrderProduct(Order order){
        order.getProductType().setProductType(io.readString("Please enter Product Type:"));
    }

    /**Prompts the user to fill in the Area of the Order they want to add,
     * Then sets info to an empty Order object*/
    public void getNewOrderArea(Order order){
        order.setArea(io.readBigDecimal("Please enter Area in sq ft:"));
    }


    /**
     * Prompts user if they want to add the Order to File
     * @return true if yes, false if no
     */
    public boolean wantToAddOrder(){
        return io.readYesOrNo("Do you want to place order (Y/N):");
    }




    /**Prints the error message of the exception being thrown     *
     * @param errorMsg Error message from exception
     */
    public void displayErrorMessage(String errorMsg) {
        io.print("=== ERROR ===");
        io.print(errorMsg);
    }







}

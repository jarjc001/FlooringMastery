package com.FlooringMastery.controller;

import com.FlooringMastery.dao.FlooringMasteryPersistenceException;
import com.FlooringMastery.dto.Order;
import com.FlooringMastery.service.FlooringMasteryBusinessRulesException;
import com.FlooringMastery.service.FlooringMasteryService;
import com.FlooringMastery.ui.FlooringMasteryView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class FlooringMasteryController {

    /** Declaration of the FlooringMasteryView */
    private FlooringMasteryView view;

    /** Declaration of the FlooringMasteryService */
    private FlooringMasteryService service;

    /**
     * Constructor
     * @param view FlooringMasteryView
     * @param service FlooringMasteryServiceImpl
     */
    @Autowired
    public FlooringMasteryController(FlooringMasteryView view,
                                     FlooringMasteryService service) {
        this.view = view;
        this.service = service;
    }



    /**Method to run the App, it starts the main menu*/
    public void runApp() {
        startApp();



        boolean keepRunning = true;     //switch for repeating main menu

        while (keepRunning) {           //will loop until user exits

            int menuSelect = getMainMenu(); //main menu loaded

            switch (menuSelect){
                case 1:
                    displayOrders();
                    break;
                case 2:
                    createOrder();  //undo somehow
                    break;
                case 3:
                    editOrder();
                    break;
                case 4:
                    removeOrder();
                    break;
                case 5:
                    backupOrderFile();  //optional
                    break;
                case 6:
                    keepRunning=false;
                    break;
                default:
                    System.out.println("unknownCommand");
            }

        }
        System.out.println("Good Bye!");

    }

    /**At startup of App, will open the Taxes and Products files,
     * then stores them in a map */
    private void startApp(){
        try {
            service.readTaxAndProduct();
        } catch (FlooringMasteryPersistenceException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }





    /**Prints the Main menu's options on console,
     * then returns an int of the user's choice
     * @return - an int of the user's choice
     */
    private int getMainMenu(){
        return view.printMainMenu();
    }


    /**Methods asks User to add pieces of order info.
     * Then an order is created using info.
     * If the info passes the business rules, it will prompt for whether to save the new Order.
     * If yes, it will be added to the corresponding Order file based on it's date
     */
    private void createOrder() {
        view.getAddOrderBanner();
        boolean hasErrors = false;

        //creates an empty order
        Order newOrder = service.createEmptyOrder();

        do {
            try {
                //Tests Date
                view.getNewOrderDate(newOrder);
                service.validateNewOrderDate(newOrder);
                //Tests Name
                view.getNewOrderName(newOrder);
                service.validateNewOrderNameCharacters(newOrder);
                service.validateNewOrderNameBlank(newOrder);
                //Name State
                view.getNewOrderState(newOrder);
                service.validateNewOrderState(newOrder);
                //Name Product
                view.getNewOrderProduct(newOrder);
                service.validateNewOrderProduct(newOrder);
                //Name Area
                view.getNewOrderArea(newOrder);
                service.validateNewOrderArea(newOrder);

                //configs the order info
                service.configOrder(newOrder);

                view.displaySingleOrderInfoHeader();
                view.displaySingleOrderInfo(newOrder);

                if(view.wantToAddOrder()){
                    //create an actual order
                    service.createNewOrder(newOrder);
                }
                hasErrors = false;

            } catch ( FlooringMasteryBusinessRulesException | FlooringMasteryPersistenceException e) {
                hasErrors = true;
                view.displayErrorMessage(e.getMessage());
            }
        }while(hasErrors);
    }


    /**Asks user for a date,
     * then will print the order details of all the orders of the date.
     * If no orders exist for that date, it will display an error message
     * and return user to main menu
     */
    private void displayOrders(){
        view.getDisplayOrderBanner();
        LocalDate date = view.askOrderDate();
        try {
            service.SearchOrderDateFile(date);
            view.diplayOrderForDate(service.getListOrders());
        } catch (FlooringMasteryPersistenceException e) {
            view.displayErrorMessage(e.getMessage());
        }





    }


    /**Asks the user what the date and the order number of the Order they want to edit.
     * If it exists, it will then prompt user for each piece of order data but display the existing data.
     * If the user hits Enter without entering data, it will leave the existing data in place.
     * If the info passes the business rules, it will display the newly edited order details.
     * Then it will prompt for whether the edit should be saved.
     * If yes, it will be saved to its order file
     */
    private void editOrder(){
        view.getEditOrderBanner();
        boolean hasErrors = false;
        Order orderToEdit = service.createEmptyOrder();
        Order orderEdited = service.createEmptyOrder();

            try {
                //gets order to edit
                service.SearchOrderDateFile(view.askOrderDate());
                orderToEdit = service.getOrderFromNumber(view.askOrderNumber());

                do{
                    try {

                        //Order number
                        orderEdited.setOrderNumber(orderToEdit.getOrderNumber());
                        orderEdited.setOrderDate(orderToEdit.getOrderDate());
                        //Tests Name
                        view.editOrderName((orderToEdit),orderEdited);
                        service.validateNewOrderNameCharacters((orderEdited));
                        //Name State
                        view.editOrderState((orderToEdit),orderEdited);
                        service.validateNewOrderState((orderEdited));
                        //Name Product
                        view.editOrderProduct((orderToEdit),orderEdited);
                        service.validateNewOrderProduct((orderEdited));
                        //Name Area
                        view.editOrderArea((orderToEdit),orderEdited);
                        service.validateNewOrderArea((orderEdited));

                        //configs the order info
                        service.configOrder(orderEdited);

                        view.displaySingleOrderInfoHeader();
                        view.displaySingleOrderInfo((orderEdited));
                        if(view.wantToEditOrder()){
                            //create an actual order
                            service.addOrderToFile((orderEdited));
                        }
                        hasErrors = false;


                    }catch (FlooringMasteryBusinessRulesException  e){  //if business rules are broken
                        hasErrors = true;
                        view.displayErrorMessage(e.getMessage());
                    }

                }while (hasErrors);

            } catch (FlooringMasteryPersistenceException e) {   //if the date or order number is wrong
                view.displayErrorMessage(e.getMessage());
            }

    }


    /**Asks the user what the date and the order number of the Order they want to remove.
     * If it exists, it will show the details of the order,
     * it will prompt for whether to remove the order.
     * If yes, it will remove it from its order file
     */
    private void removeOrder(){
        view.getRemoveOrderBanner();
        Order orderToRemove = service.createEmptyOrder();

        try {
            //gets order to edit
            service.SearchOrderDateFile(view.askOrderDate());
            orderToRemove = service.getOrderFromNumber(view.askOrderNumber());

            view.displaySingleOrderInfoHeader();
            view.displaySingleOrderNumber(orderToRemove);
            view.displaySingleOrderInfo((orderToRemove));

            if(view.wantToRemoveOrder()){
                service.removeOrderToFile(orderToRemove);
            }

        } catch (FlooringMasteryPersistenceException e) {
            view.displayErrorMessage(e.getMessage());
        }


    }


    /**Creates a backup txt file of all orders in the Files/Orders dir.
     */
    private void backupOrderFile(){

        try {
            view.getBackupBanner();
            service.writeBackupOrderFile();
            view.backupSuccess();
        } catch (FlooringMasteryPersistenceException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

}

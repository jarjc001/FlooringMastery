package com.FlooringMastery.controller;

import com.FlooringMastery.dao.FlooringMasteryPersistenceException;
import com.FlooringMastery.dto.Order;
import com.FlooringMastery.service.FlooringMasteryBusinessRulesException;
import com.FlooringMastery.service.FlooringMasteryService;
import com.FlooringMastery.ui.FlooringMasteryView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
                    System.out.println("1.Display Orders");
                    break;
                case 2:
                    createStudent();  //undo somehow
                    break;
                case 3:
                    System.out.println("3.Edit an Order");
                    break;
                case 4:
                    System.out.println("Remove an Order");
                    break;
                case 5:
                    System.out.println("Export All Data");  //optional
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
     * If the info passes the business rules,
     * it will be added to the corresponding Order file based on it's date
     */
    private void createStudent() {
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
                service.validateNewOrderName(newOrder);
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
                service.configAddOrder(newOrder);

                view.displaySingleOrder(newOrder);

                if(view.wantToAddOrder()){
                    //create an actual order
                    service.createOrder(newOrder);
                }
                hasErrors = false;

            } catch ( FlooringMasteryBusinessRulesException | FlooringMasteryPersistenceException e) {
                hasErrors = true;
                view.displayErrorMessage(e.getMessage());
            }
        }while(hasErrors);
    }





}

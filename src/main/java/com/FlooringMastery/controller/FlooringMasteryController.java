package com.FlooringMastery.controller;

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
    public void runApp(){

        boolean keepRunning = true;     //switch for repeating main menu

        while (keepRunning) {           //will loop until user exits

            int menuSelect = getMainMenu(); //main menu loaded

            switch (menuSelect){
                case 1:
                    System.out.println("1.Display Orders");
                    break;
                case 2:
                    System.out.println("2.Add an Order");
                    break;
                case 3:
                    System.out.println("3.Edit an Order");
                    break;
                case 4:
                    System.out.println("Remove an Order");
                    break;
                case 5:
                    System.out.println("Export All Data");
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



    /**Prints the Main menu's options on console,
     * then returns an int of the user's choice
     * @return - an int of the user's choice
     */
    private int getMainMenu(){
        return view.printMainMenu();
    }





}

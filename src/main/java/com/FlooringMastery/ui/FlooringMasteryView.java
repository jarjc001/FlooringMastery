package com.FlooringMastery.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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


}
